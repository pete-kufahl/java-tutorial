package com.pluralsight.courseinfo.cli.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PluralSightCourseTest {

    @Test
    void durationInMinutes() {
        PluralSightCourse course = new PluralSightCourse("id",
                "test course", "00:05:37", "https://url", false);
        assertEquals(5, course.durationInMinutes());
    }

    @Test
    void durationInMinutesOverHour() {
        PluralSightCourse course = new PluralSightCourse("id",
                "test course", "01:08:55.34999", "https://url", false);
        assertEquals(68, course.durationInMinutes());
    }

    @Test
    void durationInMinutesZeros() {
        PluralSightCourse course = new PluralSightCourse("id",
                "test course", "00:00:00", "https://url", false);
        assertEquals(0, course.durationInMinutes());
    }
}