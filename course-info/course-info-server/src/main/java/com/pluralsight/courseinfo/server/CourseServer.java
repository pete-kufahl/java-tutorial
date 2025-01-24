package com.pluralsight.courseinfo.server;

import com.pluralsight.courseinfo.repository.CourseRepository;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import java.util.logging.LogManager;

public class CourseServer {
    /* unify loggers: jdk handler --> slf4j */
    static {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
    }

    private static final Logger LOG = LoggerFactory.getLogger(CourseServer.class);
    private static final String BASE_URI = "http://localhost:8080/";

    /*
    remake jar
    > mvn clean verify
    run standalone .jar with all dependencies:
    > java -jar course-info-server/target/course-info-server-1.0-SNAPSHOT.jar
     */
    public static void main(String... args) {
        String databaseFilename = loadDatabaseFilename();
        LOG.info("Starting Http Server with database {}", databaseFilename);
        CourseRepository courseRepository = CourseRepository.openCourseRepository(databaseFilename);

        // manual form of dependency injection, here
        ResourceConfig config = new ResourceConfig()
                .register(new CourseResource(courseRepository));

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

    private static String loadDatabaseFilename() {
        try (InputStream propertiesStream = CourseServer.class.getResourceAsStream("/server.properties")){
            Properties properties = new Properties();
            properties.load(propertiesStream);
            return properties.getProperty("course-info.database");
        } catch (IOException e) {
            throw new IllegalStateException("Could not load database filename from properties file");
        }
    }
}
