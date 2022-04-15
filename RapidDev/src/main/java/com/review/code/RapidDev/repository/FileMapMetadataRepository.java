package com.review.code.RapidDev.repository;

import com.review.code.RapidDev.model.FileMapMetadataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileMapMetadataRepository extends JpaRepository<FileMapMetadataModel, Long> {
    List<FileMapMetadataModel> findByOperationname(String operation);
    List<FileMapMetadataModel> findByOperationnameAndExtensiontypename(String operation, String extensiontypename);
}
