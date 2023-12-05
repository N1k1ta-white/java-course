package bg.sofia.uni.fmi.mjt.gym;
public class ArgumentChecker {
    public static void argumentChecking(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Argument is null!");
        }
    }
    public static void emptyStringChecker(String str) {
        if (str.isEmpty())
            throw new IllegalArgumentException("String is empty!");
    }
}
