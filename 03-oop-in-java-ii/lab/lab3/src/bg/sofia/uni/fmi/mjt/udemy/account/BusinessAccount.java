package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Category;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.exception.CourseAlreadyPurchasedException;
import bg.sofia.uni.fmi.mjt.udemy.exception.InsufficientBalanceException;
import bg.sofia.uni.fmi.mjt.udemy.exception.MaxCourseCapacityReachedException;

import java.util.Arrays;

public class BusinessAccount extends AccountBase {
    private Category[] allowedCategories;
    public BusinessAccount(String username, double balance, Category[] allowedCategories) {
        super(username, balance);
        type = AccountType.BUSINESS;
        this.allowedCategories = allowedCategories;
    }

    @Override
    public void buyCourse(Course course) throws InsufficientBalanceException,
            CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {
        boolean allowed = false;
        for (Category allowedCategory : allowedCategories) {
            allowed = course.getCategory().equals(allowedCategory);
            if (allowed)
                break;
        }
        if (!allowed)
            throw new IllegalArgumentException("Invalid input!");
        super.buyCourse(course);
    }
}
