package bg.sofia.uni.fmi.mjt.cookingcompass.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

public class Request {
    private static final String HOST = "api.edamam.com";
    private static final String PATH = "/api/recipes/v2";
    private static final String SCHEME = "https";
    private static final String APP_KEY = "";
    private static final String APP_ID = "";

    public static RequestBuilder builder() throws URISyntaxException {
        return new RequestBuilder(HttpRequest.newBuilder()
                .header("Accept", "application/json")
                .header("Accept-Language", "en"), SCHEME + "://" + HOST + PATH + "?type=public")
                .addSettings("app_id", APP_ID)
                .addSettings("app_key", APP_KEY);
    }

    public static class RequestBuilder {
        private StringBuilder uri;
        private final HttpRequest.Builder builder;

        private RequestBuilder(HttpRequest.Builder builder, String uri) {
            this.uri = new StringBuilder(uri);
            this.builder = builder;
        }

        public RequestBuilder addSettings(String name, String value) {
            if (name == null || value == null) {
                throw new IllegalArgumentException("Setting can't be with null parameter!");
            }
            if (name.isBlank() || value.isBlank()) {
                throw new IllegalArgumentException("Setting can't be with blank parameter!");
            }
            uri.append("&").append(name).append("=").append(URLEncoder.encode(value, StandardCharsets.UTF_8));
            return this;
        }

        public HttpRequest build() throws URISyntaxException {
            return builder.uri(new URI(uri.toString())).build();
        }
    }
}
