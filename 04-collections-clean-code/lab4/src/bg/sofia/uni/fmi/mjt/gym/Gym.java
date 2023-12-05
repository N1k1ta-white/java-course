package bg.sofia.uni.fmi.mjt.gym;

import bg.sofia.uni.fmi.mjt.gym.member.Address;
import bg.sofia.uni.fmi.mjt.gym.member.GymMember;

import java.time.DayOfWeek;
import java.util.*;

import static bg.sofia.uni.fmi.mjt.gym.ArgumentChecker.*;

public class Gym implements GymAPI {
    private final Address address;
    private final int capacity;

    private int currentCountOfMembers;
    private Set<GymMember> members;


    public Gym(int capacity, Address address) {
        this.address = address;
        this.capacity = capacity;
        currentCountOfMembers = 0;
        members = new HashSet<GymMember>();
    }

    private static class GymMembersByName implements Comparator<GymMember> {
        @Override
        public int compare(GymMember first, GymMember second) {
            return first.getName().compareTo(second.getName());
        }
    }

    private class GymMembersByProximity implements Comparator<GymMember> {
        @Override
        public int compare(GymMember first, GymMember second) {
            return Double.compare(first.getAddress().getDistanceTo(address),
                    second.getAddress().getDistanceTo(address));
        }
    }

    private void countChecking(int countToAdd) throws GymCapacityExceededException {
        if (capacity < currentCountOfMembers + countToAdd)
            throw new GymCapacityExceededException(currentCountOfMembers + countToAdd, capacity);
    }



    @Override
    public SortedSet<GymMember> getMembers() {
        SortedSet<GymMember> res = new TreeSet<GymMember>(new GymMembersByName());
        res.addAll(members);
        return Collections.unmodifiableSortedSet(res);
    }

    @Override
    public SortedSet<GymMember> getMembersSortedByName() {
        return getMembers();
    }

    @Override
    public SortedSet<GymMember> getMembersSortedByProximityToGym() {
        SortedSet<GymMember> res = new TreeSet<GymMember>(new GymMembersByProximity());
        res.addAll(members);
        return Collections.unmodifiableSortedSet(res);
    }

    @Override
    public void addMember(GymMember member) throws GymCapacityExceededException {
        argumentChecking(member);
        countChecking(1);
        members.add(member);
        currentCountOfMembers++;
    }

    @Override
    public void addMembers(Collection<GymMember> members) throws GymCapacityExceededException {
        argumentChecking(members);
        if (members.isEmpty()) {
            throw new IllegalArgumentException("Empty collection!");
        }
        for (GymMember member : members) {
            argumentChecking(member);
        }
        countChecking(members.size());
        this.members.addAll(members);
        currentCountOfMembers += members.size();
    }

    @Override
    public boolean isMember(GymMember member) {
        argumentChecking(member);
        return members.contains(member);
    }

    @Override
    public boolean isExerciseTrainedOnDay(String exerciseName, DayOfWeek day) {
        argumentChecking(exerciseName);
        emptyStringChecker(exerciseName);
        argumentChecking(day);
        for (GymMember member: members) {
            if (member.getTrainingProgram().containsKey(day)) {
                if (member.getTrainingProgram().get(day).containExercise(exerciseName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Map<DayOfWeek, List<String>> getDailyListOfMembersForExercise(String exerciseName) {
        argumentChecking(exerciseName);
        emptyStringChecker(exerciseName);
        Map<DayOfWeek, List<String>> res = new HashMap<DayOfWeek, List<String>>();
        for (DayOfWeek day : DayOfWeek.values()) {
            for (GymMember member : members) {
                if (member.getTrainingProgram().containsKey(day)) {
                    if (member.getTrainingProgram().get(day).containExercise(exerciseName)) {
                        res.putIfAbsent(day, new ArrayList<String>());
                        res.get(day).add(member.getName());
                    }
                }
            }
        }
        return res;
    }
}
