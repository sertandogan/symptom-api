package org.symptom.controller;


import lombok.RequiredArgsConstructor;
import org.symptom.model.payload.SymptomDTO;
import org.symptom.service.SymptomService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/symptoms")
@RequiredArgsConstructor
public class SymptomController {
    private final SymptomService symptomService;

    @GetMapping("/{code}")
    public SymptomDTO fetchByCode(@PathVariable @Valid @Min(value = 1, message = "api.validation.constraints.code.message") String code) {
        return symptomService.getByCode(code);
    }

    @GetMapping()
    public PageImpl<SymptomDTO> fetchAll(@RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) Integer pageNumber) {
        return symptomService.getAll(pageSize, pageNumber);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<SymptomDTO> importCsv(@RequestPart("file") MultipartFile inputFile) {
        return symptomService.importCsv(inputFile);
    }

    @GetMapping(value = "/export-to-csv", produces = "text/csv")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        String filename = "export-file-" + LocalDate.now();
        response.addHeader("Content-Disposition", String.format("attachment; filename=\"%s\"",filename));
        symptomService.exportCsv(response.getWriter());
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void delete() {
        symptomService.deleteAll();
    }
}
