package com.pluralsight.courseinfo.cli;

public class CourseRetriever {
    public static void main(String... args) {
        System.out.println("CourseRetriever started ...");
        if (args.length == 0) {
            System.out.println("Provide author as first argument.");
            return;
        }
        try {
            retrieveCourses(args[0]);
        } catch (Exception e) {
            System.out.println("Unexpected error");
            e.printStackTrace();
        }
    }

    private static void retrieveCourses(String authorId) {
        System.out.println("Looking up course for author: " + authorId);
    }
}
