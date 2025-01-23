package com.pluralsight.courseinfo.cli.service;

public record PluralSightCourse(String id,
                                String title,
                                String duration,
                                String contentUrl,
                                boolean isRetired) {
}
