package com.englishforit.backend.service;

import com.englishforit.backend.model.Module;
import com.englishforit.backend.repository.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Transactional(readOnly = true)
    public Module getModuleExport(UUID moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found: " + moduleId));
    }

    public java.util.List<Module> getAllModules() {
        return moduleRepository.findAll();
    }
}
