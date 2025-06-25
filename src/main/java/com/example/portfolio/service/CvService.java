package com.example.portfolio.service;

import com.example.portfolio.model.CvMetadata;
import com.example.portfolio.repository.CvMetadataRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CvService {

    private final MinioClient minioClient;
    private final CvMetadataRepository cvMetadataRepository;

    @Value("${minio.bucket}")
    private String bucket;

    public CvService(MinioClient minioClient, CvMetadataRepository cvMetadataRepository) {
        this.minioClient = minioClient;
        this.cvMetadataRepository = cvMetadataRepository;
    }

    public CvMetadata uploadCv(MultipartFile file, String language, String description) {
        try {
            String objectName = "Uploads/cv/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Upload to MinIO
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );

            // Save metadata
            CvMetadata metadata = new CvMetadata();
            metadata.setFilename(file.getOriginalFilename());
            metadata.setLanguage(language);
            metadata.setUrl(objectName);
            metadata.setDescription(description);
            metadata.setUploadedBy("Biruk"); // or get from security context
            metadata.setSize(file.getSize());
            metadata.setContentType(file.getContentType());
            metadata.setUploadedAt(LocalDateTime.now());

            return cvMetadataRepository.save(metadata);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload CV", e);
        }
    }

    public List<CvMetadata> getAllCvs() {
        return cvMetadataRepository.findAll();
    }

    public CvMetadata getCvMetadata(Long id) {
        return cvMetadataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV not found"));
    }

    public ResponseEntity<byte[]> downloadCv(Long id) {
        CvMetadata metadata = getCvMetadata(id);
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(metadata.getUrl())
                        .build())) {
            byte[] bytes = stream.readAllBytes();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getFilename() + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download CV", e);
        }
    }
}
