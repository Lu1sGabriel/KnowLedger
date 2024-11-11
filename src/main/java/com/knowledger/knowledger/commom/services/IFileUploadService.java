package com.knowledger.knowledger.commom.services;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;

public interface IFileUploadService {
    String upload(InputStream inputStream, String fileName, String prefix, LocalDateTime localDateTime);

    File getFile(String path);
}