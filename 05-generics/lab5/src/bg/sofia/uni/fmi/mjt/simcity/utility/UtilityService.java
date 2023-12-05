package bg.sofia.uni.fmi.mjt.simcity.utility;

import bg.sofia.uni.fmi.mjt.simcity.property.billable.Billable;
import static bg.sofia.uni.fmi.mjt.simcity.service.Service.checkBillable;

import java.util.HashMap;
import java.util.Map;

public class UtilityService implements UtilityServiceAPI {
    private static Map<UtilityType, Double> info;
    public UtilityService(Map<UtilityType, Double> taxRates) {
        info = taxRates;
    }

    @Override
    public <T extends Billable> double getUtilityCosts(UtilityType utilityType, T billable) {
        checkBillable(billable);
        if (utilityType == null)
            throw new IllegalArgumentException("NULL TYPE!!!");
        return switch (utilityType) {
            case WATER -> (billable.getWaterConsumption() * info.get(utilityType));
            case NATURAL_GAS -> (billable.getNaturalGasConsumption() * info.get(utilityType));
            case ELECTRICITY -> (billable.getElectricityConsumption() * info.get(utilityType));
        };
    }

    @Override
    public <T extends Billable> double getTotalUtilityCosts(T billable) {
        checkBillable(billable);
        return (getUtilityCosts(UtilityType.WATER, billable) + getUtilityCosts(UtilityType.ELECTRICITY, billable)
                + getUtilityCosts(UtilityType.NATURAL_GAS, billable));
    }

    @Override
    public <T extends Billable> Map<UtilityType, Double> computeCostsDifference(T firstBillable, T secondBillable) {
        checkBillable(firstBillable);
        checkBillable(secondBillable);
        Map<UtilityType, Double> diff = new HashMap<>();
        for (UtilityType elem : UtilityType.values())
            diff.put(elem, Math.abs(getUtilityCosts(elem, firstBillable) - getUtilityCosts(elem, secondBillable)));
        return Map.copyOf(diff);
    }
}
