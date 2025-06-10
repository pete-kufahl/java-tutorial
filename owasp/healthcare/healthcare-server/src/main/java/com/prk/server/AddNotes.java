package com.prk.server;


import com.prk.repository.PatientRepository;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.StringEscapeUtils;

// demonstrate addNotes() with different security strategies
public class AddNotes {

    public static Response addNotesWhiteList(PatientRepository patientRepository, String id, String notes) {
        // Define the whitelist of acceptable notes patterns
        List<String> whitelist = Arrays.asList("Admitted", "Reviewed", "Discharged");

        // Check if the notes contain any of the patterns in the whitelist
        boolean isValid = whitelist.stream()
                .anyMatch(pattern -> {
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(notes);
                    return m.find();
                });

        if (!isValid) {
            // Return a 400 Bad Request response with an error message
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid input for notes: format of the notes payload is invalid.")
                    .build();
        }

        // Proceed with adding notes if validation passes
        patientRepository.addNotes(id, notes);

        // Return a 200 OK response
        return Response.ok().build();
    }

    public static Response addNotesBoundaryChecking(PatientRepository patientRepository, String id, String notes) {
        // Define the maximum and minimum acceptable lengths for notes
        int minLength = 10;
        int maxLength = 500;

        // Check if the notes are within the defined boundaries
        if (notes.length() < minLength || notes.length() > maxLength) {
            // Return a 400 Bad Request response if validation fails
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The format of the notes payload is invalid. It must be between "
                            + minLength + " and " + maxLength + " characters.")
                    .build();
        }

        // Proceed with adding notes if validation passes
        patientRepository.addNotes(id, notes);

        // Return a 200 OK response
        return Response.ok().build();
    }

    public static Response addNotesEscapeCharacters(PatientRepository patientRepository, String id, String notes) {
        // sanitize the notes to escape special characters
        String sanitized = StringEscapeUtils.escapeJson(notes);

        patientRepository.addNotes(id, notes);
        return Response.ok().build();
    }

    public static Response addNotesNumericValidation(PatientRepository patientRepository, String id, String notes) {
        // validate that the notes parameter is a valid integer
        try {
            // invalid input --> NumberFormatException
            Integer.parseInt(notes);
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Notes must be a valid integer.")
                    .build();
        }
        // proceed if validation passes
        patientRepository.addNotes(id, String.valueOf(Integer.parseInt(notes)));
        return Response.ok().build();
    }

    public static Response addNotesNullBytesChecking(PatientRepository patientRepository, String id, String notes) {
        // check for null bytes in the notes
        if (notes.contains("\\0")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid input: notes contain NULL bytes")
                    .build();
        }
        // proceed if validation passes
        patientRepository.addNotes(id, notes);
        return Response.ok().build();
    }

    public static Response addNotesFilterPathAlterationChars(PatientRepository patientRepository, String id, String notes) {
        // filter out path-alteration characters (this one is pretty blunt)
        String filteredNotes = notes.replaceAll("[.\\\\/]", "");
        patientRepository.addNotes(id, filteredNotes);
        return Response.ok().build();
    }

    public static Response addNotesSanitizeUTF8(PatientRepository patientRepository, String id, String notes) {
        // enforce a range of character values (ASCII range)
        StringBuilder sanitizedNotes = new StringBuilder();
        for (char ch : notes.toCharArray()) {
            // also disallows emojis, apparently
            if (ch <= 127) {
                sanitizedNotes.append(ch);
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Non-UTF-8 character detected.")
                        .build();
            }
        }
        patientRepository.addNotes(id, sanitizedNotes.toString());
        return Response.ok().build();
    }
}
