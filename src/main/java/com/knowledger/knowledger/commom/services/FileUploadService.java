package com.knowledger.knowledger.commom.services;

import com.knowledger.knowledger.infra.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
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
    private static final int BUFFER_SIZE = 8192; // Buffer para I/O

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
        // Verifica se a extensão do arquivo é permitida
        validateFileExtension(fileName);

        Path directoryPath = filesFolderPath.resolve(folderPrefix);
        prepareDirectory(directoryPath);

        String sanitizedFileName = generateFileName(fileName, localDateTime);
        Path filePath = directoryPath.resolve(sanitizedFileName);

        try {
            // Otimização: Salvar o arquivo diretamente sem processamento desnecessário
            writeFile(inputStream, filePath);
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
        return fileName.replaceAll("[^\\p{L}0-9.\\-]", "_").replace(" ", "_");
    }

    private void writeFile(InputStream inputStream, Path filePath) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING), BUFFER_SIZE)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private void validateFileExtension(String fileName) {
        String fileExtension = getFileExtension(fileName);
        if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new BusinessException("O formato de arquivo '" + fileExtension + "' não é suportado. Por favor, envie um arquivo nos formatos: png, jpeg, jpg ou pdf.", HttpStatus.BAD_REQUEST);
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            throw new BusinessException("O arquivo não possui uma extensão válida. Certifique-se de que o nome do arquivo inclua a extensão.", HttpStatus.BAD_REQUEST);
        }
        return fileName.substring(lastDotIndex + 1);
    }

}