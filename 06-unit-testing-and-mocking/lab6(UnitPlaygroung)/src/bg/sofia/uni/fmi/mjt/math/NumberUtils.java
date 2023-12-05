package bg.sofia.uni.fmi.mjt.math;
public class NumberUtils {

    public static boolean isPrime(int naturalNumberToCheck) {
        if (naturalNumberToCheck < 2) {
            throw new IllegalArgumentException("Prime is undefined for numbers < 2");
        }

        if (naturalNumberToCheck == 2) {
            return true;
        }

        if (naturalNumberToCheck % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(naturalNumberToCheck); i += 2) {
            if (naturalNumberToCheck % i == 0) {
                return false;
            }
        }

        return true;
    }

}