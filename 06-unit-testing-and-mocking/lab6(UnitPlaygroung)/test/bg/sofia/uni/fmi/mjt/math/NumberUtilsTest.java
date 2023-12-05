package bg.sofia.uni.fmi.mjt.math;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumberUtilsTest {

    @Test
    @Disabled
    void testIsPrimeTwo() {
        assertTrue(NumberUtils.isPrime(2));
    }

    @Test
    void testIsPrimeOne() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.isPrime(1), "Prime is undefined for 1");
    }

    @Test
    void testIsPrimeZero() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.isPrime(0), "Prime is undefined for 0");
    }

    @Test
    void testIsPrimeNegativePrime() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.isPrime(-7), "Prime is undefined for negative numbers");
    }

    @Test
    void testIsPrimeNegativeNotPrime() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.isPrime(-16), "Prime is undefined for 0");
    }

    @Test
    void testIsPrimeNotPrimeBig() {
        assertFalse(NumberUtils.isPrime(123), "Testing for prime for big non-prime numbers should work correctly: 123");
    }

    @Test
    void testIsPrimePrimeBig() {
        assertTrue(NumberUtils.isPrime(8191), "Testing for prime for big non-prime numbers should work correctly: 8191");
    }

    @Test
    void testIsPrimeNotPrimeBiggestInt() {
        assertTrue(NumberUtils.isPrime(Integer.MAX_VALUE), "Integer.MAX_VALUE is prime according to Somebody");
    }

    @Test
    void testIsPrimeNotPrimeEven() {
        assertFalse(NumberUtils.isPrime(32), "Even number can't be prime number");
    }

    @Test
    void testIsPrimeNotPrimeSquare() {
        assertFalse(NumberUtils.isPrime(25), "A square cannot be prime number");
    }
}