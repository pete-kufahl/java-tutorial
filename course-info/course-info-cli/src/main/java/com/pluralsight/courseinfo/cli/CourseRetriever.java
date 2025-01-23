package com.pluralsight.courseinfo.cli;

import com.pluralsight.courseinfo.cli.service.CourseRetrievalService;
import com.pluralsight.courseinfo.cli.service.PluralSightCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class CourseRetriever {
    private static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);

    public static void main(String... args) {
        LOG.info("CourseRetriever started ...");
        if (args.length == 0) {
            LOG.warn("Provide author as first argument.");
            return;
        }
        try {
            retrieveCourses(args[0]);
        } catch (Exception e) {
            LOG.error("Unexpected error", e);
        }
    }

    private static void retrieveCourses(String authorId) {
        LOG.info("Looking up course for author: '{}'", authorId);
        CourseRetrievalService courseRetrievalService = new CourseRetrievalService();
        List<PluralSightCourse> coursesToStore = courseRetrievalService.getCoursesFor(authorId)
                .stream()
                .filter(course -> !course.isRetired())
                .toList();
        LOG.info("Retrieved the following {} courses: {}", coursesToStore.size(), coursesToStore);
    }
}
