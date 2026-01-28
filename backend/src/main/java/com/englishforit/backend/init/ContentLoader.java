package com.englishforit.backend.init;

import com.englishforit.backend.service.ContentIngestionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ContentLoader implements CommandLineRunner {

    private final ContentIngestionService contentIngestionService;

    @Value("${app.content.path:../content}")
    private String contentPath;

    public ContentLoader(ContentIngestionService contentIngestionService) {
        this.contentIngestionService = contentIngestionService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting Content Ingestion from Classpath Resources...");
        contentIngestionService.ingestContent();
        System.out.println("Content Ingestion Completed.");
    }
}
