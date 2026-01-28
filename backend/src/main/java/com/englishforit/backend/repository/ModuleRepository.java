package com.englishforit.backend.repository;

import com.englishforit.backend.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {
}
