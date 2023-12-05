package bg.sofia.uni.fmi.mjt.simcity.plot;

import bg.sofia.uni.fmi.mjt.simcity.property.billable.Billable;
import bg.sofia.uni.fmi.mjt.simcity.property.buildable.Buildable;
import bg.sofia.uni.fmi.mjt.simcity.property.buildable.BuildableType;

public record Building(int area, BuildableType type, double waterConsum, double elecConsum,
                       double gasConsum) implements Billable, Buildable {
    @Override
    public double getWaterConsumption() {
        return waterConsum;
    }

    @Override
    public double getElectricityConsumption() {
        return elecConsum;
    }

    @Override
    public double getNaturalGasConsumption() {
        return gasConsum;
    }

    @Override
    public BuildableType getType() {
        return type;
    }

    @Override
    public int getArea() {
        return area;
    }
}
