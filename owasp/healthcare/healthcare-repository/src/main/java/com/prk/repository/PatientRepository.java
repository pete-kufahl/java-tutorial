package com.prk.repository;

import com.prk.domain.Patient;

import java.util.List;

public interface PatientRepository {

    static PatientRepository openPatientRepository(String databaseFile) {
        return new PatientJdbcRepository(databaseFile);
    }

    void savePatient(Patient patient);

    void addNotes(String id, String notes);

    List<Patient> getAllPatients();
}
