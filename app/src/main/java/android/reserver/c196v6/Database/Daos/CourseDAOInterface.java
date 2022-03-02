package android.reserver.c196v6.Database.Daos;

import android.reserver.c196v6.Models.Course;

import java.util.List;

public interface CourseDAOInterface {

    boolean addCourse(Course course);

    Course getCourseById(int courseId);

    List<Course> getCoursesByTerm(int termId);

    int getCourseCount();

    List<Course> getCourses();

    boolean removeCourse(Course course);
    boolean updateCourse(Course course);
}
