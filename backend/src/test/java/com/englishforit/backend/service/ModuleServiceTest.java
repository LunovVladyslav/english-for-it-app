package com.englishforit.backend.service;

import com.englishforit.backend.model.Module;
import com.englishforit.backend.repository.ModuleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModuleServiceTest {

    @Mock
    private ModuleRepository moduleRepository;

    @InjectMocks
    private ModuleService moduleService;

    @Test
    void getModuleExport_ShouldReturnModule_WhenFound() {
        UUID moduleId = UUID.randomUUID();
        Module module = new Module();
        module.setId(moduleId);
        module.setTitle("Test Module");

        when(moduleRepository.findById(moduleId)).thenReturn(Optional.of(module));

        Module result = moduleService.getModuleExport(moduleId);

        assertNotNull(result);
        assertEquals(moduleId, result.getId());
        assertEquals("Test Module", result.getTitle());
    }

    @Test
    void getModuleExport_ShouldThrowException_WhenNotFound() {
        UUID moduleId = UUID.randomUUID();
        when(moduleRepository.findById(moduleId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> moduleService.getModuleExport(moduleId));
    }

    @Test
    void getAllModules_ShouldReturnList() {
        Module module = new Module();
        module.setTitle("Test");
        when(moduleRepository.findAll()).thenReturn(Collections.singletonList(module));

        List<Module> result = moduleService.getAllModules();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
