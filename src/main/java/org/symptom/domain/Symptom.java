package org.symptom.domain;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "symptoms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Symptom {
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "source")
    private String source;
    @Column(name = "codeListCode")
    private String codeListCode;
    @Column(name = "displayValue")
    private String displayValue;

    @Column(name = "longDescription")
    private String longDescription;

    @Column(name = "fromDate")
    private LocalDate fromDate;

    @Column(name = "toDate")
    private LocalDate toDate;

    @Column(name = "sortingPriority")
    private Integer sortingPriority;


}
