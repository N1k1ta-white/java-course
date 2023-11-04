package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.course.Resource;
import bg.sofia.uni.fmi.mjt.udemy.exception.*;

public abstract class AccountBase implements Account {
    private static final int MAX = 100;
    private String username;
    private double balance;
    protected int countCourses;
    protected Course[] courses;
    protected AccountType type;

    public AccountBase(String username, double balance) {
        this.username = username;
        this.balance = balance;
        courses = new Course[MAX];
        countCourses = 0;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void addToBalance(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Negative transaction!");
        balance += amount;
    }

    @Override
    public void buyCourse(Course course) throws InsufficientBalanceException,
            CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {
        if (balance < course.getPrice())
            throw new InsufficientBalanceException();
        if (course.isPurchased())
            throw new CourseAlreadyPurchasedException();
        if (countCourses >= 100)
            throw new MaxCourseCapacityReachedException();
        balance = balance - (course.getPrice() - ((course.getPrice() / 100) * type.getDiscount() * 100));
        course.purchase();
        courses[countCourses++] = course;
    }

    @Override
    public void completeResourcesFromCourse(Course course, Resource[] resourcesToComplete)
            throws CourseNotPurchasedException, ResourceNotFoundException {
        if (!course.isPurchased())
            throw new CourseNotPurchasedException();
        for (Resource resource : resourcesToComplete)
            course.completeResource(resource);
    }

    @Override
    public void completeCourse(Course course, double grade) throws CourseNotPurchasedException,
            CourseNotCompletedException {
        if (!course.isPurchased())
            throw new CourseNotPurchasedException();
        if (!course.isCompleted())
            throw new CourseNotCompletedException();
        course.setGrade(grade);
    }

    @Override
    public Course getLeastCompletedCourse() {
        int min = 100;
        int ind = -1;
        for (int i = 0; i < countCourses; i++)
            if (courses[i].isPurchased())
                if (min > courses[i].getCompletionPercentage()) {
                    min = courses[i].getCompletionPercentage();
                    ind = i;
                }
        return (ind == -1 ? null : courses[ind]);
    }
}
