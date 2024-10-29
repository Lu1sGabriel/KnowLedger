package com.knowledger.knowledger.commom.services;

import com.knowledger.knowledger.infra.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FileUploadService implements IFileUploadService {
    private final Path imagesFolderPath;
    private static final int BUFFER_SIZE = 1024;
    private static final int MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final DateTimeFormatter FILE_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final List<String> ALLOWED_EXTENSIONS = List.of("png", "jpeg", "jpg");

    public FileUploadService(@Value("${imagesFolder}") String imagesFolder) {
        if (imagesFolder == null || imagesFolder.isBlank()) {
            throw new IllegalArgumentException("A pasta de imagens não pode ser nula ou vazia.");
        }
        this.imagesFolderPath = Paths.get(imagesFolder);
    }

    @Override
    public String upload(InputStream inputStream, String originalFileName) {
        return saveFile(inputStream, originalFileName, null, LocalDateTime.now());
    }

    @Override
    public String upload(String prefix, InputStream inputStream, String originalFileName, LocalDateTime localDateTime) {
        return saveFile(inputStream, originalFileName, prefix, localDateTime);
    }

    @Override
    public File getFile(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new BusinessException("Arquivo não encontrado no caminho especificado.", HttpStatus.NOT_FOUND);
        }
        return path.toFile();
    }

    private String saveFile(InputStream inputStream, String originalFileName, String prefix, LocalDateTime localDateTime) {
        validateImageFormat(originalFileName);

        String fileName = createFileName(originalFileName, prefix, localDateTime);
        Path filePath = imagesFolderPath.resolve(fileName);

        try {
            Files.createDirectories(filePath.getParent());
            copyFileWithLimit(inputStream, filePath);
        } catch (IOException e) {
            throw new BusinessException("Erro ao salvar a nova imagem.", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return filePath.toString();
    }

    private void validateImageFormat(String fileName) {
        String fileExtension = getFileExtension(fileName);
        if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new BusinessException("Formato de imagem não suportado. Use PNG ou JPEG.", HttpStatus.BAD_REQUEST);
        }
    }

    private static String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1 || lastIndexOfDot == fileName.length() - 1) {
            throw new BusinessException("O nome do arquivo deve conter uma extensão válida.", HttpStatus.BAD_REQUEST);
        }
        return fileName.substring(lastIndexOfDot + 1).toLowerCase();
    }

    private String createFileName(String originalFileName, String prefix, LocalDateTime localDateTime) {
        StringBuilder fileNameBuilder = new StringBuilder();
        if (prefix != null && !prefix.isBlank()) {
            fileNameBuilder.append(prefix).append("_");
        }
        fileNameBuilder.append(localDateTime.format(FILE_TIMESTAMP_FORMAT))
                .append("_")
                .append(originalFileName);
        return fileNameBuilder.toString();
    }

    private void copyFileWithLimit(InputStream inputStream, Path filePath) throws IOException {
        int totalBytesRead = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        try (var outputStream = Files.newOutputStream(filePath)) {
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                totalBytesRead += bytesRead;
                if (totalBytesRead > MAX_FILE_SIZE) {
                    throw new BusinessException("O arquivo excede o tamanho máximo permitido de 5 MB.", HttpStatus.BAD_REQUEST);
                }
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

}