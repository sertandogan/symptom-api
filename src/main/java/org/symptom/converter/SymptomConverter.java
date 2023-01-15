package org.symptom.converter;


import org.symptom.domain.Symptom;
import org.symptom.model.payload.SymptomDTO;


public class SymptomConverter {

    public static Symptom fromDTO(SymptomDTO symptomDTO) {
        return Symptom.
                builder().
                code(symptomDTO.getCode()).
                codeListCode(symptomDTO.getCodeListCode()).
                source(symptomDTO.getSource()).
                toDate(symptomDTO.getToDate()).
                fromDate(symptomDTO.getFromDate()).
                longDescription(symptomDTO.getLongDescription()).
                displayValue(symptomDTO.getDisplayValue()).
                sortingPriority(symptomDTO.getSortingPriority()).
                build();
    }
    public static SymptomDTO fromEntity(Symptom symptom) {
        return SymptomDTO.
                builder().
                code(symptom.getCode()).
                codeListCode(symptom.getCodeListCode()).
                source(symptom.getSource()).
                toDate(symptom.getToDate()).
                fromDate(symptom.getFromDate()).
                longDescription(symptom.getLongDescription()).
                displayValue(symptom.getDisplayValue()).
                sortingPriority(symptom.getSortingPriority()).
                build();
    }
}
