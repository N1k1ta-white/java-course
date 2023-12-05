package bg.sofia.uni.fmi.mjt.simcity.service;

import bg.sofia.uni.fmi.mjt.simcity.property.billable.Billable;
import bg.sofia.uni.fmi.mjt.simcity.property.buildable.Buildable;

public class Service {
    public static void checkString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Your argument is null!");
        } else if (str.isBlank()) {
            throw new IllegalArgumentException("Your argument is blank!");
        }
    }

    public static void checkBuildable(Buildable obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Your argument is null!");
        }
    }

    public static void checkBillable(Billable obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Your argument is null!");
        }
    }
}
