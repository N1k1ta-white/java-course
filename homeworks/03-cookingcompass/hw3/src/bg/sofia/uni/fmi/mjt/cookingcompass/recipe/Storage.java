package bg.sofia.uni.fmi.mjt.cookingcompass.recipe;

import bg.sofia.uni.fmi.mjt.cookingcompass.api.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface Storage<E> {
    void resetStorage(Response response);

    int getPageSize();

    List<E> getPageFromIndex(int index) throws URISyntaxException, IOException, InterruptedException;

    void setPageSize(int pageSize);
}
