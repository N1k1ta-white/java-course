package bg.sofia.uni.fmi.mjt.udemy.course;

import bg.sofia.uni.fmi.mjt.udemy.course.duration.ResourceDuration;

import java.util.Objects;

public class Resource implements Completable {
    private String name;
    private ResourceDuration duration;
    private boolean isComplete;


    public Resource(String name, ResourceDuration duration) {
        this.name = name;
        this.duration = duration;
        isComplete = false;
    }
    /**
     * Returns the resource name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the total duration of the resource.
     */
    public ResourceDuration getDuration() {
        return duration;
    }

    /**
     * Marks the resource as completed.
     */
    public void complete() {
        isComplete = true;
    }

    @Override
    public boolean isCompleted() {
        return isComplete;
    }

    @Override
    public int getCompletionPercentage() {
        return (isComplete ? 1 : 0) * 100;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof Resource resource)
            return name.equals(resource.name)
                    && duration.equals(resource.duration);
        return false;
    }
}
