package com.pluralsight.courseinfo.cli.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PluralSightCourse(String id,
                                String title,
                                String duration,
                                String contentUrl,
                                boolean isRetired) {
}
