package bg.sofia.uni.fmi.mjt.udemy.course;

import bg.sofia.uni.fmi.mjt.udemy.course.duration.CourseDuration;
import bg.sofia.uni.fmi.mjt.udemy.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Objects;

public class Course implements Purchasable, Completable {
    private String name;
    private String description;
    private double price;
    private Resource[] content;
    private Category category;
    private CourseDuration totalTime;
    private boolean purchased;

    private double grade;

    public Course(String name, String description, double price, Resource[] content, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.content = content;
        this.category = category;
        int time = 0;
        for (Resource elem : content)
            time += elem.getDuration().minutes();
        totalTime = new CourseDuration(time / 60, time % 60);
        grade = 0;
    }

    public void setGrade(double grade) {
        if (grade < 2.0 || grade > 6.0)
            throw new IllegalArgumentException("Invalid input!");
        this.grade = grade;
    }

    /**
     *   Returns the grade of the course
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Returns the name of the course.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the course.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the price of the course.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the category of the course.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Returns the content of the course.
     */
    public Resource[] getContent() {
        return content;
    }

    /**
     * Returns the total duration of the course.
     */
    public CourseDuration getTotalTime() {
        return totalTime;
    }

    /**
     * Completes a resource from the course.
     *
     * @param resourceToComplete the resource which will be completed.
     * @throws ResourceNotFoundException if the resource could not be found in the course.
     */
    public void completeResource(Resource resourceToComplete) throws ResourceNotFoundException {
        boolean found = false;
        for (Resource resource : content)
            if (resourceToComplete.equals(resource)) {
                resource.complete();
                found = true;
                break;
            }
        if (!found)
            throw new ResourceNotFoundException();
    }

    @Override
    public boolean isCompleted() {
        for (Resource elem : content) {
            if (!elem.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getCompletionPercentage() {
        int completed = 0, all = content.length;
        if (all == 0)
            return 0;
        for (Resource res : content)
            if (res.isCompleted())
                completed++;
        double res = ((double)completed / all) * 100;
        if (res - (int)res >= 0.5)
            res++;
        return (int)res;
    }

    @Override
    public void purchase() {
        purchased = true;
    }

    @Override
    public boolean isPurchased() {
        return purchased;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setContent(Resource[] content) {
        this.content = content;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (o instanceof Course course)
            return Double.compare(price, course.price) == 0 && Objects.equals(name, course.name)
                    && Objects.equals(description, course.description) && Arrays.equals(content, course.content)
                    && category == course.category && Objects.equals(totalTime, course.totalTime);
        return false;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, price, category, totalTime);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}
