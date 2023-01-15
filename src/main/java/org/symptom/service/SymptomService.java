package org.symptom.service;

import org.symptom.model.payload.SymptomDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.Writer;
import java.util.List;

public interface SymptomService {
    List<SymptomDTO> importCsv(MultipartFile file);
    void exportCsv(Writer writer);
    PageImpl<SymptomDTO> getAll(Integer pageSize, Integer pageNumber);
    SymptomDTO getByCode(String code);
    void deleteAll();
}
