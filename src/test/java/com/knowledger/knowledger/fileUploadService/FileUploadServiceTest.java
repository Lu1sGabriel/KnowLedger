package com.knowledger.knowledger.fileUploadService;

import com.knowledger.knowledger.commom.services.FileUploadService;
import com.knowledger.knowledger.infra.exceptions.BusinessException;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileUploadServiceTest {

    @Value("${imagesFolder}")
    private String imagesFolderPath;

    private FileUploadService fileUploadService;

    private static final String TEST_IMAGES_FOLDER_PATH = "C:/Users/luisb/Downloads/images_test";
    private static final String ORIGINAL_IMAGE_NAME = "A serene natural landscape at sunrise.jpg";
    private static final String REPLACEMENT_IMAGE_NAME = "Carro a noite na chuva.png";
    private static final String TEST_UUID = UUID.randomUUID().toString();
    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        fileUploadService = new FileUploadService(imagesFolderPath);
    }

    @Test
    @DisplayName("Test upload and replacement of image file")
    void testUploadAndReplaceImage() throws IOException {
        // Upload original image
        uploadImage(TEST_UUID, ORIGINAL_IMAGE_NAME);

        // Replace with a new image
        uploadImage(TEST_UUID, REPLACEMENT_IMAGE_NAME);

        // Attempt to retrieve the replaced image
        File retrievedFile = fileUploadService.getFile(TEST_UUID);

        // Assertions to verify replacement
        assertNotNull(retrievedFile, "The retrieved file should not be null after replacement.");
        assertTrue(retrievedFile.exists(), "The retrieved file should exist after replacement.");
        assertTrue(retrievedFile.getName().contains("Carro"), "The retrieved file should be the replacement image.");
    }

    @Test
    @DisplayName("Test upload of a single image file")
    void testUploadSingleImage() throws IOException {
        // Upload image without specifying a UUID (auto-generated)
        try (InputStream inputStream = Files.newInputStream(Paths.get(TEST_IMAGES_FOLDER_PATH, ORIGINAL_IMAGE_NAME))) {
            String uploadedFilePath = fileUploadService.upload(inputStream, ORIGINAL_IMAGE_NAME);
            assertNotNull(uploadedFilePath, "The upload file path should not be null.");
        }
    }

    @Test
    @DisplayName("Test retrieval of uploaded image")
    void testRetrieveUploadedFile() throws IOException {
        // Upload an image to retrieve
        uploadImage(TEST_UUID, ORIGINAL_IMAGE_NAME);

        // Retrieve the uploaded file
        File retrievedFile = fileUploadService.getFile(TEST_UUID);

        // Assertions to verify retrieval
        assertNotNull(retrievedFile, "The retrieved file should not be null.");
        assertTrue(retrievedFile.exists(), "The retrieved file should exist.");
    }

    @Test
    @DisplayName("Test retrieval of non-existent UUID throws exception")
    void testRetrieveNonExistentFile() {
        String nonExistentUUID = UUID.randomUUID().toString();

        BusinessException exception = assertThrows(BusinessException.class, () -> fileUploadService.getFile(nonExistentUUID), "Expected a BusinessException when retrieving a non-existent UUID");

        assertEquals("Pasta com o UUID especificado não encontrada.", exception.getMessage());
    }

    // Auxiliary method to handle image upload with UUID
    private void uploadImage(String uuid, String imageName) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(TEST_IMAGES_FOLDER_PATH, imageName))) {
            fileUploadService.upload(uuid, inputStream, imageName, TIMESTAMP);
        }
    }

}
