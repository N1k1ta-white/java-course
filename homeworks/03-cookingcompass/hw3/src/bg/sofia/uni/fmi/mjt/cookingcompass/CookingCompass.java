package bg.sofia.uni.fmi.mjt.cookingcompass;

import bg.sofia.uni.fmi.mjt.cookingcompass.api.Request;
import bg.sofia.uni.fmi.mjt.cookingcompass.api.Response;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.HealthLabel;
import bg.sofia.uni.fmi.mjt.cookingcompass.enums.MealType;
import bg.sofia.uni.fmi.mjt.cookingcompass.recipe.Recipe;
import bg.sofia.uni.fmi.mjt.cookingcompass.recipe.Storage;
import bg.sofia.uni.fmi.mjt.cookingcompass.recipe.StorageOfRecipes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CookingCompass implements Compass<Recipe> {
    private final Storage<Recipe> storage;

    public static CookingCompassBuilder builder() {
        return new CookingCompassBuilder();
    }

    public CookingCompass(HttpRequest httpRequest, int pageSize) throws IOException, InterruptedException {
        if (httpRequest == null) {
            throw new IllegalArgumentException("HttpRequest cannot be null!");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page cannot be negative or zero!");
        }
        storage = new StorageOfRecipes(Response.of(httpRequest), pageSize);
    }

    public void setNewSearch(HttpRequest request) throws IOException, InterruptedException {
        if (request == null) {
            throw new IllegalArgumentException("HttpRequest cannot be null!");
        }
        storage.resetStorage(Response.of(request));
    }

    public Collection<Recipe> getPage(int number) throws URISyntaxException, IOException, InterruptedException {
        if (number < 0) {
            throw new IllegalArgumentException("Page cannot be negative!");
        }
        return storage.getPageFromIndex(storage.getPageSize() * number);
    }

    public void setPage(int pageSize) {
        storage.setPageSize(pageSize);
    }

    public static class CookingCompassBuilder {
        private static final int DEFAULT_SIZE = 6;
        private int pageSize;
        private String keywords;
        private List<MealType> mealTypes;
        private List<HealthLabel> healthLabels;

        private CookingCompassBuilder() {
            pageSize = DEFAULT_SIZE;
            keywords = null;
            mealTypes = new ArrayList<>();
            healthLabels = new ArrayList<>();
        }

        private Request.RequestBuilder initializeRequest() throws URISyntaxException {
            Request.RequestBuilder requestBuilder = Request.builder();
            if (keywords != null) {
                requestBuilder.addSettings("q", keywords);
            }
            if (mealTypes != null) {
                mealTypes.forEach(el ->
                        requestBuilder.addSettings("mealType", el.getValue())
                );
            }
            if (healthLabels != null) {
                healthLabels.forEach(el ->
                        requestBuilder.addSettings("health", el.getApiParameter())
                );
            }
            return requestBuilder;
        }

        public CookingCompassBuilder setPageSize(int pageSize) {
            if (pageSize <= 0) {
                throw new IllegalArgumentException("Page size cannot be " + pageSize);
            }
            this.pageSize = pageSize;
            return this;
        }

        public CookingCompassBuilder setKeywords(String keywords) {
            if (keywords == null) {
                throw new IllegalArgumentException("Keywords cannot be null!");
            }
            this.keywords = keywords;
            return this;
        }

        public CookingCompassBuilder setMealTypes(List<MealType> mealTypes) {
            if (mealTypes == null) {
                throw new IllegalArgumentException("Array of MealTypes cannot be null!");
            }
            if (mealTypes.contains(MealType.LUNCH_DINNER)) {
                throw new IllegalArgumentException("I'm hurry and I haven't time for better solution(");
            }
            if (mealTypes.size() != Set.of(mealTypes).size()) {
                throw new IllegalArgumentException("Array of MealTypes has duplicates!");
            }
            this.mealTypes = mealTypes;
            return this;
        }

        public CookingCompassBuilder setHealthLabels(List<HealthLabel> healthLabels) {
            if (healthLabels == null) {
                throw new IllegalArgumentException("Array of HealthLabels cannot be null!");
            }
            if (healthLabels.size() != Set.of(healthLabels).size()) {
                throw new IllegalArgumentException("Array of HealthLabels has duplicates!");
            }
            this.healthLabels = healthLabels;
            return this;
        }

        public CookingCompass build() throws URISyntaxException, IOException, InterruptedException {
            Request.RequestBuilder requestBuilder = initializeRequest();
            return new CookingCompass(requestBuilder.build(), pageSize);
        }

        public String getCurrentUri() throws URISyntaxException {
            return initializeRequest().build().uri().toString();
        }
    }
}
