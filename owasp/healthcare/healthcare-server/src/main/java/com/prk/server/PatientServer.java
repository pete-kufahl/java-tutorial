package com.prk.server;

import com.prk.repository.PatientRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class PatientServer {
    private static final Logger LOG = LoggerFactory.getLogger(PatientServer.class);
    private static final String BASE_URI = "http://localhost:8080";

    public static void main(String... args) {
        LOG.info("Starting HTTP server");
        PatientRepository repo = PatientRepository.openPatientRepository("./patients.db");

        ResourceConfig config = new ResourceConfig()
                .register(PatientResource.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(repo).to(PatientRepository.class);
                    }
                })
                .register(CorsFilter.class);

        LOG.info("Patient count = {}", repo.getAllPatients().size());

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }
}