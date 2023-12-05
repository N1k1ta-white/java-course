package bg.sofia.uni.fmi.mjt.gym.member;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public record Address(double longitude, double latitude) {
    public double getDistanceTo(Address other) {
        return sqrt(pow(longitude - other.longitude, 2) + pow(latitude - other.latitude, 2));
    }
}
