package bg.sofia.uni.fmi.mjt.itinerary;

import bg.sofia.uni.fmi.mjt.itinerary.exception.CityNotKnownException;
import bg.sofia.uni.fmi.mjt.itinerary.exception.NoPathToDestinationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SequencedCollection;

public class RideRight implements ItineraryPlanner {
    private List<Journey> schedule;
    private Map<Integer, Journey> intToJourney;
    private Map<Journey, Integer> journeyToInt;
    private record PathNode(List<Integer> path, BigDecimal cost, BigDecimal distance) {
        private static final BigDecimal METRES_IN_KILOMETRE = new BigDecimal("1000");
        private static final BigDecimal COST_OF_ONE_KILOMETRE = new BigDecimal("20");
        BigDecimal getSum() {
            return cost.add(distance.divide(METRES_IN_KILOMETRE).multiply(COST_OF_ONE_KILOMETRE));
        }
    }

    private class JourneyByPathComparator implements Comparator<PathNode> {
        @Override
        public int compare(PathNode p1, PathNode p2) {
            int ans = p1.getSum().compareTo(p2.getSum());
            if (ans == 0) {
                ans = intToJourney.get(p1.path.getLast()).to().name()
                        .compareTo(intToJourney.get(p2.path.getLast()).to().name());
            }
            return ans;
        }
    }

    public RideRight(List<Journey> schedule) {
        this.schedule = schedule;
        intToJourney = new HashMap<>();
        journeyToInt = new HashMap<>();
        int iter = 0;
        for (Journey j : schedule) {
            intToJourney.put(iter, j);
            journeyToInt.put(j, iter++);
        }
    }

    private SequencedCollection<Journey> getJourneysByStartCity(String start) {
        SequencedCollection<Journey> ans = new ArrayList<>();
        for (Journey jour : schedule) {
            if (jour.from().name().equals(start)) {
                ans.add(jour);
            }
        }
        return ans;
    }

    private BigDecimal getDistance(Location start, Location end) {
        return BigDecimal.valueOf(start.x() - end.x()).abs().add(BigDecimal.valueOf(start.y() - end.y()).abs());
    }

    private void planningPathFromBestPos(PriorityQueue<PathNode> openNodes, HashSet<String> visited,
                                         City dest) throws NoPathToDestinationException {
        PathNode curr = openNodes.poll();
        while (visited.contains(intToJourney.get(curr.path.getLast()).to().name())) {
            curr = openNodes.poll();
            if (curr == null) {
                throw new NoPathToDestinationException(dest.name());
            }
        }
        SequencedCollection<Journey> journeys =
                getJourneysByStartCity(intToJourney.get(curr.path.getLast()).to().name());
        visited.add(intToJourney.get(curr.path.getLast()).to().name());
        for (Journey j : journeys) {
            if (!visited.contains(j.to().name())) {
                List<Integer> temp = new ArrayList<>(curr.path);
                temp.add(journeyToInt.get(j));
                openNodes.add(new PathNode(temp, curr.cost.add(j.price()),
                        getDistance(j.to().location(), dest.location())));
            }
        }
    }

    private SequencedCollection<Journey> findCheapestPathWithOneNode(SequencedCollection<Journey> firstJourneys,
        City destination) throws NoPathToDestinationException {
        Journey res = null;
        for (Journey journey : firstJourneys) {
            if (journey.to().name().equals(destination.name())) {
                if (res == null) {
                    res = journey;
                } else {
                    if (res.price().compareTo(journey.price()) > 0) {
                        res = journey;
                    }
                }
            }
        }
        if (res != null) {
            return List.of(res);
        }
        throw new NoPathToDestinationException(destination.name());
    }

    private SequencedCollection<Journey> arrIntToJourney(SequencedCollection<Integer> arr) {
        SequencedCollection<Journey> res = new ArrayList<>();
        for (Integer num : arr) {
            res.add(intToJourney.get(num));
        }
        return res;
    }

    private void checkCites(City start, City destination) throws CityNotKnownException {
        boolean city1 = false;
        boolean city2 = false;
        for (Journey j : schedule) {
            if (j.to().name().equals(start.name()) || j.from().name().equals(start.name())) {
                city1 = true;
            }
            if (j.to().name().equals(destination.name()) || j.from().name().equals(destination.name())) {
                city2 = true;
            }
        }
        if (!(city1 & city2)) {
            throw new CityNotKnownException();
        }
    }

    @Override
    public SequencedCollection<Journey> findCheapestPath(City start, City destination, boolean allowTransfer)
            throws CityNotKnownException, NoPathToDestinationException {
        checkCites(start, destination);

        HashSet<String> visited = new HashSet<>();
        PriorityQueue<PathNode> openNodes = new PriorityQueue<>(new JourneyByPathComparator());
        SequencedCollection<Journey> firstJourneys = getJourneysByStartCity(start.name());

        if (!allowTransfer) {
            return findCheapestPathWithOneNode(firstJourneys, destination);
        } else {
            for (Journey j : firstJourneys) {
                List<Integer> temp = new ArrayList<>();
                temp.add(journeyToInt.get(j));
                openNodes.add(new PathNode(temp, j.price(),
                        getDistance(j.to().location(), destination.location())));
            }
            while (!openNodes.isEmpty()) {
                if (intToJourney.get(openNodes.peek().path.getLast()).to().equals(destination)) {
                    return arrIntToJourney(openNodes.peek().path);
                }
                planningPathFromBestPos(openNodes, visited, destination);
            }
        }
        throw new NoPathToDestinationException(destination.name());
    }
}
