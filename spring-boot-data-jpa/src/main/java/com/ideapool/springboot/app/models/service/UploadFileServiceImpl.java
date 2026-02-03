package com.ideapool.springboot.app.models.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public Resource load(String filename) {
        try {
            Path filePath = getPath(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Error: file could not be loaded: " + filePath);
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: malformed URL for file " + filename, e);
        }
    }

    @Override
    public String copy(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        try {
            Files.createDirectories(Paths.get(uploadDir));

            String safeOriginal = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename().replace(" ", "_");
            String uniqueFilename = UUID.randomUUID().toString() + "_" + safeOriginal;

            Path rootPath = getPath(uniqueFilename);
            Files.copy(file.getInputStream(), rootPath);

            return uniqueFilename;
        } catch (IOException e) {
            throw new RuntimeException("Error: could not store file", e);
        }
    }

    @Override
    public boolean delete(String filename) {
        if (filename == null || filename.isBlank()) return false;

        try {
            Path filePath = getPath(filename);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.warn("Could not delete file: {}", filename, e);
            return false;
        }
    }

    private Path getPath(String filename) {
        return Paths.get(uploadDir).resolve(filename).toAbsolutePath().normalize();
    }

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(uploadDir).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(uploadDir));
	}
}
