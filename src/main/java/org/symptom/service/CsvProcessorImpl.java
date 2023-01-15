package org.symptom.service;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.symptom.exception.IllegalDataFormatException;
import org.symptom.exception.IllegalFileFormatException;
import org.symptom.model.constant.CsvHeader;
import org.symptom.model.payload.SymptomDTO;
import org.symptom.util.DateUtils;
import org.symptom.util.IntegerUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvProcessorImpl implements CsvProcessor {
    private final static String CSV_DATA_TYPE = "text/csv";
    private final static String DATE_FORMAT = "dd-MM-yyyy";
    private final static String[] HEADERS = {CsvHeader.SOURCE_FIELD, CsvHeader.CODE_LIST_CODE_FIELD, CsvHeader.CODE_FIELD,
            CsvHeader.DISPLAY_VALUE_FIELD, CsvHeader.LONG_DESCRIPTION_FIELD, CsvHeader.FROM_DATE_FIELD,
            CsvHeader.TO_DATE_FIELD, CsvHeader.SORTING_PRIORITY_FIELD};

    public List<SymptomDTO> csvToDto(MultipartFile file) {
        if (!fileFormatValid(file)) throw new IllegalFileFormatException();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            return createModel(csvRecords);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while parsing CSV file: " + e.getMessage(), e);
        }
    }

    public void dtoToCsv(List<SymptomDTO> symptomDTOList, Writer writer) {
        try (CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            for (SymptomDTO symptom : symptomDTOList) {
                printer.printRecord(symptom.getSource(),
                        symptom.getCodeListCode(),
                        symptom.getCode(),
                        symptom.getDisplayValue(),
                        symptom.getLongDescription(),
                        symptom.getFromDate(),
                        symptom.getToDate(),
                        symptom.getSortingPriority());
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while exporting CSV file: " + e.getMessage(), e);
        }
    }


    private List<SymptomDTO> createModel(Iterable<CSVRecord> csvRecords) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        List<SymptomDTO> csvDataList = new ArrayList<>();
        int rowCounter = 1;
        try {
            for (CSVRecord csvRecord : csvRecords) {
                rowCounter++;
                SymptomDTO symptomDTO = SymptomDTO.
                        builder().
                        source(csvRecord.get(CsvHeader.SOURCE_FIELD)).
                        codeListCode(csvRecord.get(CsvHeader.CODE_LIST_CODE_FIELD)).
                        code(csvRecord.get(CsvHeader.CODE_FIELD)).
                        displayValue(csvRecord.get(CsvHeader.DISPLAY_VALUE_FIELD)).
                        longDescription(csvRecord.get(CsvHeader.LONG_DESCRIPTION_FIELD)).
                        fromDate(DateUtils.convert(csvRecord.get(CsvHeader.FROM_DATE_FIELD), dateTimeFormatter)).
                        toDate(DateUtils.convert(csvRecord.get(CsvHeader.TO_DATE_FIELD), dateTimeFormatter)).
                        sortingPriority(IntegerUtils.convert(csvRecord.get(CsvHeader.SORTING_PRIORITY_FIELD))).
                        build();

                csvDataList.add(symptomDTO);
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new IllegalDataFormatException(String.format("Your data is invalid. Please check the line %d", rowCounter));
        }
        return csvDataList;
    }

    private boolean fileFormatValid(MultipartFile file) {
        return CSV_DATA_TYPE.equals(file.getContentType());
    }
}
