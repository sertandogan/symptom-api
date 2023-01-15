package org.symptom.service;

import lombok.RequiredArgsConstructor;
import org.symptom.converter.SymptomConverter;
import org.symptom.exception.InputDataEmptyException;
import org.symptom.exception.SymptomNotFoundException;
import org.symptom.model.payload.SymptomDTO;
import org.symptom.repository.SymptomRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SymptomServiceImpl implements SymptomService {
    private final SymptomRepository symptomRepository;
    private final CsvProcessor csvProcessor;

    private final static int DEFAULT_PAGE_SIZE = 20;

    public List<SymptomDTO> importCsv(MultipartFile inputCsv) {
        var codeList = csvProcessor.csvToDto(inputCsv);
        if (codeList.isEmpty()) {
            throw new InputDataEmptyException();
        }
        var entityList = symptomRepository.saveAll(codeList.stream().map(SymptomConverter::fromDTO).collect(Collectors.toList()));
        return entityList.stream().map(SymptomConverter::fromEntity)
                .collect(Collectors.toList());
    }

    public PageImpl<SymptomDTO> getAll(Integer pageSize, Integer pageNumber) {
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        pageNumber = pageNumber == null ? 0 : pageNumber;
        var pageOptions = PageRequest.of(pageNumber, pageSize, Sort.by("code").and(Sort.by("sortingPriority")));
        var entityList = symptomRepository.findAll(pageOptions);
        var responseList = entityList.stream().map(SymptomConverter::fromEntity)
                .collect(Collectors.toList());
        var totalCount = symptomRepository.count();
        return new PageImpl<>(responseList, pageOptions, totalCount);
    }

    public void exportCsv(Writer writer) {
        var entityList = symptomRepository.findAll();
        var responseList = entityList.stream().map(SymptomConverter::fromEntity)
                .collect(Collectors.toList());
        csvProcessor.dtoToCsv(responseList, writer);
    }

    public SymptomDTO getByCode(String code) {
        return SymptomConverter.fromEntity(symptomRepository.findByCode(code).orElseThrow(SymptomNotFoundException::new));
    }

    public void deleteAll() {
        symptomRepository.deleteAll();
    }
}
