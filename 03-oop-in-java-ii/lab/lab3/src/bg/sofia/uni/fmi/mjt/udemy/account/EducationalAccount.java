package bg.sofia.uni.fmi.mjt.udemy.account;

import bg.sofia.uni.fmi.mjt.udemy.account.type.AccountType;
import bg.sofia.uni.fmi.mjt.udemy.course.Course;
import bg.sofia.uni.fmi.mjt.udemy.exception.*;

public class EducationalAccount extends AccountBase {
    private static final double minGrade = 4.5;
    private static final int count = 5;
    private int counter;
    private double gradeSum;
    public EducationalAccount(String username, double balance) {
        super(username, balance);
        type = AccountType.EDUCATION;
        counter = 0;
        gradeSum = 0;
    }

    @Override
    public void completeCourse(Course course, double grade) throws CourseNotPurchasedException, CourseNotCompletedException {
        super.completeCourse(course, grade);
        counter++;
        gradeSum += grade;
    }

    @Override
    public void buyCourse(Course course) throws InsufficientBalanceException,
            CourseAlreadyPurchasedException, MaxCourseCapacityReachedException {
        if (counter == count) {
            if (gradeSum / counter >= 4.5) {
                super.buyCourse(course);
                counter = 0;
                return;
            }
            gradeSum = 0;
            counter = 0;
        }
        type = AccountType.STANDARD;
        super.buyCourse(course);
        type = AccountType.EDUCATION;
    }
}
