package com.englishforit.backend.service;

import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocumentService {

    private final VectorStore vectorStore;

    public DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void ingestDocument(MultipartFile file) throws IOException {
        // Read PDF
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(
                new ByteArrayResource(file.getBytes()));

        List<Document> documents = pdfReader.get();

        // Split text
        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> splitDocuments = splitter.apply(documents);

        // Store embeddings
        vectorStore.add(splitDocuments);
    }

    public List<Document> search(String query) {
        return vectorStore.similaritySearch(query);
    }
}
