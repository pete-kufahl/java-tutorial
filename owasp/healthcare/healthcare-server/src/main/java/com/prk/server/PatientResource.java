package com.prk.server;

import com.prk.domain.Patient;
import com.prk.repository.PatientRepository;
import com.prk.repository.RepositoryException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import jakarta.inject.Inject;

@Path("/patients")
public class PatientResource {
    private static final Logger LOG = LoggerFactory.getLogger(PatientResource.class);

    private final PatientRepository patientRepository;

    @Inject
    public PatientResource(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getPatients() {
        try {
            return patientRepository
                    .getAllPatients()
                    .stream()
                    .sorted(Comparator.comparing(Patient::id))
                    .toList();
        } catch (RepositoryException e) {
            LOG.error("Could not retrieve patients from the database.", e);
            throw new NotFoundException();
        }
    }

    @POST
    @Path("/{id}/notes")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response addNotes(@PathParam("id") String id, String notes) {

        // use a whitelist
        // return AddNotes.addNotesWhiteList(patientRepository, id, notes);

        // use boundary checking
        // return AddNotes.addNotesBoundaryChecking(patientRepository, id, notes);

        // use character escaping
        // return AddNotes.addNotesEscapeCharacters(patientRepository, id, notes);

        // notes can only be a valid number
        // return AddNotes.addNotesNumericValidation(patientRepository, id, notes);

        // notes cannot contain null bytes
        // return AddNotes.addNotesNullBytesChecking(patientRepository, id, notes);

        // notes cannot have path-alteration characters
        return AddNotes.addNotesFilterPathAlterationChars(patientRepository, id, notes);
    }
}