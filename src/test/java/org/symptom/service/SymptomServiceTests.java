package org.symptom.service;

import org.symptom.domain.Symptom;
import org.symptom.exception.InputDataEmptyException;
import org.symptom.exception.SymptomNotFoundException;
import org.symptom.repository.SymptomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SymptomServiceTests {
    @InjectMocks
    private SymptomServiceImpl symptomService;

    @Mock
    private SymptomRepository symptomRepository;

    @Mock
    private CsvProcessor csvProcessor;

    @Test
    void it_should_get_symptom_by_code_successfully() {
        String code = "abc";
        var symptomEntity = new Symptom();

        when(symptomRepository.findByCode(code)).thenReturn(Optional.of(symptomEntity));

        symptomService.getByCode(code);

        verify(symptomRepository, times(1)).findByCode(code);
    }

    @Test
    void it_should_throws_exception_when_symptom_not_found() {
        String code = "abc";

        when(symptomRepository.findByCode(code)).thenReturn(Optional.empty());
        assertThrows(SymptomNotFoundException.class, () -> symptomService.getByCode(code));

        verify(symptomRepository, times(1)).findByCode(code);
    }

    @Test
    void it_should_remove_all_symptoms_successfully() {
        doNothing().when(symptomRepository).deleteAll();
        symptomService.deleteAll();

        verify(symptomRepository, times(1)).deleteAll();
    }

    @Test
    void it_should_throws_exception_when_input_csv_is_empty() {
        MultipartFile file = new MockMultipartFile("test", new byte[]{});

        when(csvProcessor.csvToDto(any())).thenReturn(List.of());
        assertThrows(InputDataEmptyException.class, () -> symptomService.importCsv(file));

        verifyNoInteractions(symptomRepository);
    }
}
