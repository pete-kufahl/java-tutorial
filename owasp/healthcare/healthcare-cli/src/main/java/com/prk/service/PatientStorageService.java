package com.prk.service;

import com.prk.domain.Patient;
import com.prk.repository.PatientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PatientStorageService {
    private final PatientRepository patientRepository;

    public PatientStorageService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void storePatients(List<Result> results) {
        for (Result result : results) {
            Patient patient = new Patient(String.valueOf(UUID.randomUUID()),
                    result.name().first(),
                    result.name().last(),
                    result.gender(),
                    result.phone(),
                    result.nat(),
                    result.email(),
                    Optional.empty());
            patientRepository.savePatient(patient);
        }
    }
}