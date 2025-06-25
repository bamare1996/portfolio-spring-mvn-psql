package com.example.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "cv_metadata") // db table name
public class CvMetadata {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String language; // e.g., "en", "de"

    @Override
    public String toString() {
        return "CvMetadata{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", language='" + language + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", uploadedBy='" + uploadedBy + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }

    private String url; // MinIO object URL or key

    private String description;

    private String uploadedBy;

    private Long size; // file size in bytes

    private String contentType; // e.g., "application/pdf"

    private java.time.LocalDateTime uploadedAt;
}
