package bg.sofia.uni.fmi.mjt.cookingcompass.recipe;

import bg.sofia.uni.fmi.mjt.cookingcompass.api.Response;
import bg.sofia.uni.fmi.mjt.cookingcompass.exceptions.NoRecipesException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class StorageOfRecipes implements Storage<Recipe> {
    private int pageSize;
    private final List<Recipe> storage;
    private String nextPage;

    private boolean getNextPageFromAPI() throws URISyntaxException, IOException, InterruptedException {
        if (nextPage.equals("None")) {
            return false;
        }
        Response response = Response.of(nextPage);
        storage.addAll(response.receipts());
        nextPage = response.nextPage();
        return true;
    }

    public StorageOfRecipes(Response response, int pageSize) {
        storage = new ArrayList<>();
        this.pageSize = pageSize;
        nextPage = response.nextPage();
        storage.addAll(response.receipts());
    }

    public void resetStorage(Response response) {
        if (response == null) {
            throw new IllegalArgumentException("Nothing from to reset storage!");
        }
        storage.clear();
        storage.addAll(response.receipts());
        nextPage = response.nextPage();
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<Recipe> getPageFromIndex(int index) throws URISyntaxException, IOException, InterruptedException {
        if (index < 0) {
            throw new IllegalArgumentException("Recipe's index cannot be negative!");
        }
        List<Recipe> result = new ArrayList<>();
        while (index + pageSize >= storage.size()) {
            if (!getNextPageFromAPI()) {
                if (index > storage.size()) {
                    throw new NoRecipesException();
                }
                for (int i = index; i < storage.size(); i++) {
                    result.add(storage.get(i));
                }
                return result;
            }
        }
        for (int i = index; i < index + pageSize; i++) {
            result.add(storage.get(i));
        }
        return result;
    }
}
