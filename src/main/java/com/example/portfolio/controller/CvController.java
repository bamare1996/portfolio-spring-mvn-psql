package com.example.portfolio.controller;

import com.example.portfolio.model.CvMetadata;
import com.example.portfolio.service.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/cv")
public class CvController {

    @Autowired
    private CvService cvService;

    @PostMapping("/upload")
    public ResponseEntity<CvMetadata> uploadCv(
            @RequestParam("file") MultipartFile file,
            @RequestParam("language") String language,
            @RequestParam(value = "description", required = false) String description
    ) {
        CvMetadata metadata = cvService.uploadCv(file, language, description);
        return ResponseEntity.ok(metadata);
    }

    @GetMapping
    public ResponseEntity<List<CvMetadata>> getAllCvs() {
        return ResponseEntity.ok(cvService.getAllCvs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CvMetadata> getCv(@PathVariable Long id) {
        return ResponseEntity.ok(cvService.getCvMetadata(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadCv(@PathVariable Long id) {
        return cvService.downloadCv(id);
    }
}
