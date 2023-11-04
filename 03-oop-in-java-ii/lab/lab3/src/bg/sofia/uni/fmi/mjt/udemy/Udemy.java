package bg.sofia.uni.fmi.mjt.udemy;

import bg.sofia.uni.fmi.mjt.udemy.account.Account;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.duration.CourseDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.AccountNotFoundException;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseNotFoundException;

import java.util.Arrays;

public class Udemy implements LearningPlatform {
    Account[] accounts;
    Course[] courses;
    public Udemy(Account[] accounts, Course[] courses) {
        this.accounts = accounts;
        this.courses = courses;
    }

    @Override
    public Course findByName(String name) throws CourseNotFoundException {
        for (Course course : courses)
            if (course.getName().equals(name))
                return course;
        throw new CourseNotFoundException();
    }

    @Override
    public Course[] findByKeyword(String keyword) {
        int counter = 0;
        keyword = keyword.toLowerCase();
        int[] arrIndex = new int[courses.length];
        for (int i = 0; i < courses.length; i++)
            if (courses[i].getName().toLowerCase().contains(keyword) ||
                    courses[i].getDescription().toLowerCase().contains(keyword))
                arrIndex[counter++] = i;
        Course[] res = new Course[counter];
        for (int i = 0; i < counter; i++)
            res[i] = courses[arrIndex[i]];
        return res;
    }

    @Override
    public Course[] getAllCoursesByCategory(Category category) {
        int counter = 0;
        int[] arrIndex = new int[courses.length];
        for (int i = 0; i < courses.length; i++)
            if (courses[i].getCategory().equals(category))
                arrIndex[counter++] = i;
        Course[] res = new Course[counter];
        for (int i = 0; i < counter; i++)
            res[i] = courses[arrIndex[i]];
        return res;
    }

    @Override
    public Account getAccount(String name) throws AccountNotFoundException {
        for (Account account : accounts)
            if (account.getUsername().equals(name))
                return account;
        throw new AccountNotFoundException();
    }

    private boolean compare(CourseDuration d1, CourseDuration d2) {
        if (d1.hours() > d2.hours())
            return true;
        if (d1.minutes() > d2.minutes())
            return true;
        return false;
    }

    @Override
    public Course getLongestCourse() {
        if (courses.length == 0)
            return null;
        int longest = 0;
        for (int i = 1; i < courses.length; i++)
            if (compare(courses[longest].getTotalTime(), courses[i].getTotalTime()))
                longest = i;
        return courses[longest];
    }

    @Override
    public Course getCheapestByCategory(Category category) {
        if (courses.length == 0)
            return null;
        int cheapest = 0;
        for (int i = 1; i < courses.length; i++)
            if (courses[cheapest].getPrice() > courses[i].getPrice())
                cheapest = i;
        return courses[cheapest];
    }
}
