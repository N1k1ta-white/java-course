package bg.sofia.uni.fmi.mjt.cookingcompass.api;

import bg.sofia.uni.fmi.mjt.cookingcompass.exceptions.NetworkExceptionFactory;
import bg.sofia.uni.fmi.mjt.cookingcompass.recipe.Recipe;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public record Response(List<Recipe> receipts, String nextPage) {

    private static final int LOWER_BOUND_OF_ERRORS = 400;
    private static final int UPPER_BOUND_OF_ERRORS = 599;

    public static Response of(HttpRequest request) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= LOWER_BOUND_OF_ERRORS && response.statusCode() <= UPPER_BOUND_OF_ERRORS) {
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonArray().asList()
                    .get(0).getAsJsonObject();
            throw NetworkExceptionFactory.exceptionByCodeStatus(response.statusCode(), jsonObject
                    .get("message").getAsString());
        }
        return JsonConverter.convertJson(response.body());
    }

    public static Response of(String uri) throws URISyntaxException, IOException, InterruptedException {
        return of(HttpRequest.newBuilder(new URI(uri)).build());
    }
}