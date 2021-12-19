package com.example.pirmasnamudarbas.dao.course;

import com.example.pirmasnamudarbas.dao.CRUDdao;
import com.example.pirmasnamudarbas.entities.Course;
import com.example.pirmasnamudarbas.entities.User;
import java.util.List;
import java.util.Locale.Category;

public interface CourseDao extends CRUDdao<Course> {

    public Course getByName(String name);

    public List<Course> getUserCourses(User user, Category category);

    public List<Course> getByCategory(Category category);
}
