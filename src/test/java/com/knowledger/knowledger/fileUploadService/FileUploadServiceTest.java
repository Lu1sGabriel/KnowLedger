package com.knowledger.knowledger.fileUploadService;

import com.knowledger.knowledger.commom.services.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FileUploadServiceTest {

    @Value("${postFilesFolder}")
    private String imagesFolderPath;

    private FileUploadService fileUploadService;

    private static final String TEST_IMAGES_FOLDER_PATH = "src/test/java/com/knowledger/knowledger/fileUploadService/images";
    private static final String ORIGINAL_IMAGE_NAME = "A serene natural landscape at sunrise.jpg";
    private static final String REPLACEMENT_IMAGE_NAME = "Carro a noite na chuva.png";
    private static final String ORIGINAL_PDF_NAME = "pdf test.pdf";

    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        fileUploadService = new FileUploadService(imagesFolderPath);
    }

    @Test
    @DisplayName("Teste de upload e substituição de arquivo de imagem")
    void testUploadAndReplaceImage() throws IOException {
        String uuid = UUID.randomUUID().toString();

        // Upload da imagem original
        uploadImage(uuid, ORIGINAL_IMAGE_NAME);

        // Substituição por uma nova imagem
        uploadImage(uuid, REPLACEMENT_IMAGE_NAME);

        // Tentativa de recuperar a imagem substituída
        File retrievedFile = fileUploadService.getFile(uuid);

        // Assertivas para verificar a substituição
        assertNotNull(retrievedFile, "O arquivo recuperado não deve ser nulo após a substituição.");
        assertTrue(retrievedFile.exists(), "O arquivo recuperado deve existir após a substituição.");
        assertTrue(retrievedFile.getName().contains("Carro"), "O arquivo recuperado deve ser a imagem de substituição.");
    }

    @Test
    @DisplayName("Teste de upload de um único arquivo de imagem")
    void testUploadSingleImage() throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(TEST_IMAGES_FOLDER_PATH, ORIGINAL_IMAGE_NAME))) {
            String uploadedFilePath = fileUploadService.upload(inputStream, ORIGINAL_IMAGE_NAME, UUID.randomUUID().toString(), TIMESTAMP);
            assertNotNull(uploadedFilePath, "O caminho do arquivo carregado não deve ser nulo.");
        }
    }

    @Test
    @DisplayName("Teste de upload de um único arquivo PDF")
    void testUploadSinglePdf() throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(TEST_IMAGES_FOLDER_PATH, ORIGINAL_PDF_NAME))) {
            String uploadedFilePath = fileUploadService.upload(inputStream, ORIGINAL_PDF_NAME, UUID.randomUUID().toString(), TIMESTAMP);
            assertNotNull(uploadedFilePath, "O caminho do arquivo carregado não deve ser nulo.");
        }
    }

    @Test
    @DisplayName("Teste de recuperação de imagem carregada")
    void testRetrieveUploadedFile() throws IOException {
        String uuid = UUID.randomUUID().toString();

        // Upload de uma imagem para recuperação
        uploadImage(uuid, ORIGINAL_IMAGE_NAME);

        // Recuperação do arquivo carregado
        File retrievedFile = fileUploadService.getFile(uuid);

        // Assertivas para verificar a recuperação
        assertNotNull(retrievedFile, "O arquivo recuperado não deve ser nulo.");
        assertTrue(retrievedFile.exists(), "O arquivo recuperado deve existir.");
    }

    private void uploadImage(String uuid, String imageName) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(TEST_IMAGES_FOLDER_PATH, imageName))) {
            fileUploadService.upload(inputStream, imageName, uuid, TIMESTAMP);
        }
    }

}