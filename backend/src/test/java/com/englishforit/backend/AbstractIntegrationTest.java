package com.englishforit.backend;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public abstract class AbstractIntegrationTest {
    @org.springframework.boot.test.mock.mockito.MockBean
    protected org.springframework.ai.vectorstore.VectorStore vectorStore;
}
