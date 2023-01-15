package org.symptom.repository;

import org.symptom.domain.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, String> {
    Optional<Symptom> findByCode(String id);
}
