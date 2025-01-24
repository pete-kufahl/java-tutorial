package com.pluralsight.courseinfo.server;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;
import com.pluralsight.courseinfo.repository.RepositoryException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.stream.Stream;

@Path("/courses")
public class CourseResource {
    private static final Logger LOG = LoggerFactory.getLogger(CourseResource.class);

    private final CourseRepository courseRepository;

    public CourseResource(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Stream<Course> getCourses() {
        try {
            return courseRepository.getAllCourses()
                    .stream()
                    .sorted(Comparator.comparing(Course::id));
        } catch (RepositoryException e) {
            LOG.error("Could not retrieve courses", e);
            throw new NotFoundException();  // returns 404
        }
    }

    @POST
    @Path("/{id}/notes")
    @Consumes(MediaType.TEXT_PLAIN)
    public void addNotes(@PathParam("id") String id, String notes) {
        /*
        test with something like:
        > curl -X POST -H "Content-Type: text/plain" --data "completed as relative beginner" http://localhost:8080/courses/3514597d-487c-41ec-9a30-d55ba28e3089/notes
         */
        courseRepository.addNotes(id, notes);
    }
}
