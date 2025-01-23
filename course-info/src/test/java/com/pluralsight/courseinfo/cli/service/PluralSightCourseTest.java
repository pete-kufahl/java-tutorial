package com.pluralsight.courseinfo.cli.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PluralSightCourseTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            00:05:37, 5
            01:08:55.34999, 68
            00:00:00, 0
            """)
    void durationInMinutes(String input, long expected) {
        PluralSightCourse course = new PluralSightCourse("id",
                "test course", input, "https://url", false);
        assertEquals(expected, course.durationInMinutes());
    }
}