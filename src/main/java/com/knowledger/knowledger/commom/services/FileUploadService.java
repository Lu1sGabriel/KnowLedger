package com.knowledger.knowledger.commom.services;

import com.knowledger.knowledger.infra.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

@Service
public class FileUploadService implements IFileUploadService {

    private static final DateTimeFormatter FILE_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("png", "jpeg", "jpg", "pdf");

    private final Path filesFolderPath;

    public FileUploadService(@Value("${postFilesFolder}") String postFilesFolder) {
        this.filesFolderPath = Paths.get(postFilesFolder);
        initializeFilesFolder();
    }

    @Override
    public String upload(InputStream inputStream, String fileName, String prefix, LocalDateTime localDateTime) {
        String folderPrefix = (prefix != null && !prefix.isBlank()) ? prefix : UUID.randomUUID().toString();
        LocalDateTime dateTime = (localDateTime != null) ? localDateTime : LocalDateTime.now();
        return saveFile(inputStream, fileName, folderPrefix, dateTime);
    }

    @Override
    public File getFile(String folderPrefix) {
        Path directoryPath = filesFolderPath.resolve(folderPrefix);
        validateDirectoryExists(directoryPath);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    return entry.toFile();
                }
            }
            throw new BusinessException("Nenhum arquivo encontrado na pasta especificada.", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            throw new BusinessException("Erro ao acessar a pasta especificada.", e, HttpStatus.BAD_REQUEST);
        }
    }

    private void initializeFilesFolder() {
        if (Files.notExists(filesFolderPath)) {
            try {
                Files.createDirectories(filesFolderPath);
            } catch (IOException e) {
                throw new BusinessException("Não foi possível criar a pasta de arquivos. Por favor, entre em contato com o suporte.", e, HttpStatus.BAD_REQUEST);
            }
        }
    }

    private String saveFile(InputStream inputStream, String fileName, String folderPrefix, LocalDateTime localDateTime) {
        Path directoryPath = filesFolderPath.resolve(folderPrefix);
        prepareDirectory(directoryPath);

        String sanitizedFileName = generateFileName(fileName, localDateTime);
        Path filePath = directoryPath.resolve(sanitizedFileName);

        try {
            byte[] processedFile = processFile(inputStream, fileName);
            Files.write(filePath, processedFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new BusinessException("Não foi possível salvar o arquivo. Por favor, tente novamente.", e, HttpStatus.BAD_REQUEST);
        }
    }

    private void prepareDirectory(Path directoryPath) {
        try {
            if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            clearDirectory(directoryPath);
        } catch (IOException e) {
            throw new BusinessException("Erro ao preparar o diretório para salvar o arquivo.", e, HttpStatus.BAD_REQUEST);
        }
    }

    private void clearDirectory(Path directoryPath) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
            for (Path file : stream) {
                if (Files.isRegularFile(file)) {
                    Files.deleteIfExists(file);
                }
            }
        } catch (IOException e) {
            throw new BusinessException("Não foi possível limpar o diretório antes de salvar o arquivo.", e, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateDirectoryExists(Path directoryPath) {
        if (Files.notExists(directoryPath) || !Files.isDirectory(directoryPath)) {
            throw new BusinessException("A pasta especificada não foi encontrada.", HttpStatus.NOT_FOUND);
        }
    }

    private String generateFileName(String fileName, LocalDateTime localDateTime) {
        String timestamp = localDateTime.format(FILE_TIMESTAMP_FORMAT);
        String sanitizedFileName = sanitizeFileName(fileName);
        return timestamp + "_" + sanitizedFileName;
    }

    private static String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.\\-]", "_").replace(" ", "_");
    }

    private byte[] processFile(InputStream inputStream, String fileName) {
        validateFileName(fileName);
        String fileExtension = getFileExtension(fileName).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
            throw new BusinessException("O formato de arquivo '" + fileExtension + "' não é suportado. Por favor, envie um arquivo nos formatos: png, jpeg, jpg ou pdf.", HttpStatus.BAD_REQUEST);
        }

        return switch (fileExtension) {
            case "png", "jpeg", "jpg" -> convertToBytes(inputStream, fileExtension);
            case "pdf" -> convertToBytes(inputStream);
            default -> throw new BusinessException("Formato de arquivo não suportado.", HttpStatus.BAD_REQUEST);
        };
    }


    private void validateFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new BusinessException("O nome do arquivo não pode ser vazio.", HttpStatus.BAD_REQUEST);
        }
        if (fileName.length() >= 50) {
            throw new BusinessException("O nome do arquivo deve ter menos de 50 caracteres.", HttpStatus.BAD_REQUEST);
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            throw new BusinessException("O arquivo não possui uma extensão válida. Certifique-se de que o nome do arquivo inclua a extensão.", HttpStatus.BAD_REQUEST);
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

    private byte[] convertToBytes(InputStream inputStream, String fileExtension) {
        final int MAX_IMAGE_SIZE = 10 * 1024 * 1024;

        try {
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                throw new BusinessException("O arquivo enviado não é uma imagem válida. Por favor, envie uma imagem nos formatos PNG ou JPEG.", HttpStatus.BAD_REQUEST);
            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                boolean success = ImageIO.write(image, fileExtension, outputStream);
                if (!success) {
                    throw new BusinessException("Não foi possível processar a imagem no formato '" + fileExtension + "'. Por favor, envie a imagem em um formato suportado.", HttpStatus.BAD_REQUEST);
                }
                byte[] imageBytes = outputStream.toByteArray();

                if (imageBytes.length > MAX_IMAGE_SIZE) {
                    throw new BusinessException("O arquivo de imagem excede o tamanho máximo permitido de " + (MAX_IMAGE_SIZE / (1024 * 1024)) + " MB.", HttpStatus.BAD_REQUEST);
                }

                return imageBytes;
            }
        } catch (IOException e) {
            throw new BusinessException("Erro ao processar a imagem. Por favor, tente novamente.", e, HttpStatus.BAD_REQUEST);
        }
    }

    private byte[] convertToBytes(InputStream inputStream) {
        final int MAX_PDF_SIZE = 5 * 1024 * 1024;

        try {
            byte[] fileBytes = inputStream.readAllBytes();

            if (fileBytes.length > MAX_PDF_SIZE) {
                throw new BusinessException("O arquivo PDF excede o tamanho máximo permitido de 5 MB.", HttpStatus.BAD_REQUEST);
            }

            return fileBytes;
        } catch (IOException e) {
            throw new BusinessException("Não foi possível ler o arquivo PDF. Verifique se o arquivo está corrompido e tente novamente.", e, HttpStatus.BAD_REQUEST);
        }
    }

}