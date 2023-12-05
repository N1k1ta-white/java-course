package bg.sofia.uni.fmi.mjt.gym.member;

import bg.sofia.uni.fmi.mjt.gym.workout.Exercise;
import bg.sofia.uni.fmi.mjt.gym.workout.Workout;

import java.time.DayOfWeek;
import java.util.*;

import static bg.sofia.uni.fmi.mjt.gym.ArgumentChecker.argumentChecking;

public class Member implements GymMember {
    private final Address address;
    private final String name;
    private final int age;
    private final String personalIdNumber;
    private final Gender gender;
    private Map<DayOfWeek, Workout> trainingProgram;
    public Member(Address address, String name, int age, String personalIdNumber, Gender gender) {
        this.address = address;
        this.name = name;
        this.age = age;
        this.personalIdNumber = personalIdNumber;
        this.gender = gender;
        trainingProgram = new HashMap<DayOfWeek, Workout>();
    }

    private void dayOffChecking(DayOfWeek day) {
        if (!trainingProgram.containsKey(day)) {
            throw new DayOffException(day);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getPersonalIdNumber() {
        return personalIdNumber;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Map<DayOfWeek, Workout> getTrainingProgram() {
        return Collections.unmodifiableMap(trainingProgram);
    }

    @Override
    public void setWorkout(DayOfWeek day, Workout workout) {
        argumentChecking(day);
        argumentChecking(workout);
        trainingProgram.putIfAbsent(day, workout);
    }

    @Override
    public Collection<DayOfWeek> getDaysFinishingWith(String exerciseName) {
        argumentChecking(exerciseName);
        if (exerciseName.isEmpty())
            throw new IllegalArgumentException("Exercise's name can't be empty!");
        List<DayOfWeek> res = new ArrayList<DayOfWeek>();
        for (DayOfWeek day : DayOfWeek.values()) {
            if (trainingProgram.containsKey(day)) {
                if (trainingProgram.get(day).exercises().getLast().name().equals(exerciseName)) {
                    res.add(day);
                }
            }
        }
        return res;
    }

    @Override
    public void addExercise(DayOfWeek day, Exercise exercise) {
        argumentChecking(day);
        argumentChecking(exercise);
        dayOffChecking(day);
        trainingProgram.get(day).exercises().add(exercise);
    }

    @Override
    public void addExercises(DayOfWeek day, List<Exercise> exercises) {
        argumentChecking(day);
        argumentChecking(exercises);
        if (exercises.isEmpty()) {
            throw new IllegalArgumentException("Empty collection!");
        }
        dayOffChecking(day);
        trainingProgram.get(day).exercises().addAll(exercises);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(personalIdNumber, member.personalIdNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalIdNumber);
    }
}
