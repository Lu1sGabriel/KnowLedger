package com.knowledger.knowledger.commom.services;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;

public interface IFileUploadService {
    String upload(InputStream inputStream, String filePath);

    String upload(String aux, InputStream inputStream, String filePath, LocalDateTime localDateTime);

    File getFile(String path);

}
