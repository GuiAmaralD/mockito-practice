package com.mockstudy.mockitostudy.course;

import java.util.List;

public interface CourseService {

    public List<String> retriveCourses(String student);
    public void deleteCourse(String course);

}
