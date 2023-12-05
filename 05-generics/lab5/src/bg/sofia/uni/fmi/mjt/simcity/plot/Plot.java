package bg.sofia.uni.fmi.mjt.simcity.plot;

import bg.sofia.uni.fmi.mjt.simcity.exception.InsufficientPlotAreaException;
import bg.sofia.uni.fmi.mjt.simcity.exception.BuildableAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.simcity.exception.BuildableNotFoundException;
import bg.sofia.uni.fmi.mjt.simcity.property.buildable.Buildable;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import static bg.sofia.uni.fmi.mjt.simcity.service.Service.checkBuildable;
import static bg.sofia.uni.fmi.mjt.simcity.service.Service.checkString;

public class Plot<E extends Buildable> implements PlotAPI {
    private int buildableArea;
    private Map<String, E> buildings;

    public Plot(int buildableArea) {
        this.buildableArea = buildableArea;
        buildings = new HashMap<>();
    }

    @Override
    public void construct(String address, Buildable buildable) {
        checkString(address);
        checkBuildable(buildable);
        if (buildings.containsKey(address))
            throw new BuildableAlreadyExistsException(address);
        if (buildableArea - buildable.getArea() < 0)
            throw new InsufficientPlotAreaException(buildable.getArea());
        buildableArea -= buildable.getArea();
        buildings.put(address, (E)buildable);
    }

    @Override
    public void demolish(String address) {
        checkString(address);
        if (!buildings.containsKey(address)) {
            throw new BuildableNotFoundException(address);
        }
        buildableArea += buildings.get(address).getArea();
        buildings.remove(address);
    }

    @Override
    public void demolishAll() {
        Set<String> keys = buildings.keySet();
        for (String key : keys) {
            buildableArea += buildings.get(key).getArea();
        }
        buildings.clear();
    }

    @Override
    public Map<String, E> getAllBuildables() {
        return Map.copyOf(buildings);
    }

    @Override
    public int getRemainingBuildableArea() {
        return buildableArea;
    }

    @Override
    public void constructAll(Map buildables) {
        if (buildables == null) {
            throw new IllegalArgumentException("Your argument is null!");
        } else if (buildables.isEmpty()) {
            throw new IllegalArgumentException("Your map is empty!");
        }
        buildables.forEach((k, v) -> construct((String) k, (E) v));
    }
}
