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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService implements IFileUploadService {

    private final Path imagesFolderPath;
    private static final int MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB
    private static final int MAX_FILENAME_LENGTH = 50;
    private static final DateTimeFormatter FILE_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
    private static final List<String> ALLOWED_EXTENSIONS = List.of("png", "jpeg", "jpg");

    public FileUploadService(@Value("${imagesFolder}") String imagesFolder) {
        this.imagesFolderPath = Paths.get(imagesFolder);
        if (Files.notExists(this.imagesFolderPath)) {
            throw new IllegalArgumentException("A pasta de imagens não foi encontrada.");
        }
    }

    @Override
    public String upload(InputStream inputStream, String originalFileName) {
        return saveFile(inputStream, originalFileName, UUID.randomUUID().toString(), LocalDateTime.now());
    }

    @Override
    public String upload(String uuid, InputStream inputStream, String originalFileName, LocalDateTime localDateTime) {
        return saveFile(inputStream, originalFileName, uuid == null ? UUID.randomUUID().toString() : uuid, localDateTime);
    }

    @Override
    public File getFile(String uuid) {
        Path directoryPath = imagesFolderPath.resolve(uuid);

        if (Files.notExists(directoryPath) || !Files.isDirectory(directoryPath)) {
            throw new BusinessException("Pasta com o UUID especificado não encontrada.", HttpStatus.NOT_FOUND);
        }

        try (var files = Files.list(directoryPath)) {
            return files.filter(Files::isRegularFile)
                    .findFirst()
                    .map(Path::toFile)
                    .orElseThrow(() -> new BusinessException("Nenhum arquivo encontrado na pasta especificada.", HttpStatus.NOT_FOUND));
        } catch (IOException e) {
            throw new BusinessException("Erro ao acessar a pasta do UUID especificado.", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String saveFile(InputStream inputStream, String originalFileName, String uuid, LocalDateTime localDateTime) {
        // Validações iniciais
        validateFileNameLength(originalFileName);
        String fileExtension = getFileExtension(originalFileName);
        validateImageFormat(fileExtension);
        BufferedImage bufferedImage = readImage(inputStream);
        byte[] imageBytes = convertToBytesAndValidateSize(bufferedImage, fileExtension);

        // Criação do diretório e substituição do arquivo
        Path directoryPath = imagesFolderPath.resolve(uuid);
        Path filePath = directoryPath.resolve(createFileName(sanitizeFileName(originalFileName), fileExtension, localDateTime));

        try {
            Files.createDirectories(directoryPath);
            deleteExistingFiles(directoryPath);
            Files.write(filePath, imageBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new BusinessException("Erro ao salvar a nova imagem.", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return filePath.toString();
    }

    private BufferedImage readImage(InputStream inputStream) {
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            if (bufferedImage == null) {
                throw new BusinessException("O arquivo não é uma imagem válida.", HttpStatus.BAD_REQUEST);
            }
            return bufferedImage;
        } catch (IOException e) {
            throw new BusinessException("Erro ao ler a imagem do InputStream.", e, HttpStatus.BAD_REQUEST);
        }
    }

    private byte[] convertToBytesAndValidateSize(BufferedImage bufferedImage, String fileExtension) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, fileExtension, outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            if (imageBytes.length > MAX_FILE_SIZE) {
                throw new BusinessException("Arquivo excede o limite de 5 MB.", HttpStatus.BAD_REQUEST);
            }
            return imageBytes;
        } catch (IOException e) {
            throw new BusinessException("Erro ao converter a imagem para bytes.", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void deleteExistingFiles(Path directoryPath) {
        try (var files = Files.list(directoryPath)) {
            files.filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            throw new BusinessException("Erro ao excluir a imagem existente.", e, HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    });
        } catch (IOException e) {
            throw new BusinessException("Erro ao acessar a pasta para exclusão de arquivos existentes.", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateFileNameLength(String fileName) {
        if (fileName.length() > MAX_FILENAME_LENGTH) {
            throw new BusinessException("Nome do arquivo excede o limite de 50 caracteres.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateImageFormat(String fileExtension) {
        if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new BusinessException("Formato de imagem não suportado. Use PNG ou JPEG.", HttpStatus.BAD_REQUEST);
        }
    }

    private static String createFileName(String sanitizedFileName, String fileExtension, LocalDateTime localDateTime) {
        String baseFileName = sanitizedFileName.replaceAll("\\." + fileExtension + "$", "");
        return localDateTime.format(FILE_TIMESTAMP_FORMAT) + "_" + baseFileName + "." + fileExtension;
    }

    private static String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.-]", "_").replace(" ", "_");
    }

    private static String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1 || lastIndexOfDot == fileName.length() - 1) {
            throw new BusinessException("Nome do arquivo sem extensão válida.", HttpStatus.BAD_REQUEST);
        }
        return fileName.substring(lastIndexOfDot + 1).toLowerCase();
    }

}