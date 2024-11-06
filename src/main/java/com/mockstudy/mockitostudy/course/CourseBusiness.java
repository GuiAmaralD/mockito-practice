package com.mockstudy.mockitostudy.course;


import java.util.ArrayList;
import java.util.List;

//this class is our system under test
public class CourseBusiness {

    //courseservice is a dependency
    private CourseService courseService;

    public CourseBusiness(CourseService courseService) {
        this.courseService = courseService;
    }

    public List<String> retrieveCoursesRelatedToSpring(String student){

        if(student == null){
            throw new IllegalArgumentException("Student cannot be null");
        }

        var filteredCourses = new ArrayList<String>();
        var allCourses = courseService.retriveCourses(student);

        for (String course : allCourses) {
            if (course.contains("Spring")) {
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    public void deleteCoursesNotRelatedToSpring(String student){
        var allCourses = courseService.retriveCourses(student);

        for(String course : allCourses){
            if(!course.contains("Spring")){
                courseService.deleteCourse(course);
            }
        }
    }
}
