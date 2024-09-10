package bg.sofia.uni.fmi.mjt.cookingcompass;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.Collection;

public interface Compass<E> {
    void setNewSearch(HttpRequest request) throws IOException, InterruptedException;

    Collection<E> getPage(int number) throws URISyntaxException, IOException, InterruptedException;
}
