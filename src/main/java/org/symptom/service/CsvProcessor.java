package org.symptom.service;

import org.symptom.model.payload.SymptomDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.Writer;
import java.util.List;

public interface CsvProcessor {
    List<SymptomDTO> csvToDto(MultipartFile csvFile);
    void dtoToCsv(List<SymptomDTO> symptomDTOList, Writer writer);
}
