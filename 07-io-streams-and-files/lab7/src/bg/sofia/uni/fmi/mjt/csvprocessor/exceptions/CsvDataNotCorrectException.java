package bg.sofia.uni.fmi.mjt.csvprocessor.exceptions;

public class CsvDataNotCorrectException extends Exception {
    public CsvDataNotCorrectException() {
        super("Inputted data from csv file is invalid!");
    }
}
