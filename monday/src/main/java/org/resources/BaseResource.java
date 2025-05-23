package org.resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Objects;

/**
 * Base resource class for handling common functionality across Monday.com API resources.
 * Provides HTTP request handling and JSON serialization capabilities.
 */
public class BaseResource {

    private static final String API_BASE_URL = "https://api.monday.com/v2/";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/json";
    private static final String AUTH_HEADER = "Authorization";

    private final Gson gson = new Gson();
    private final String token;
    private final HttpClient client;

    /**
     * Creates a new BaseResource instance.
     *
     * @param apiKey The API authentication token for Monday.com
     * @throws IllegalArgumentException if apiKey is null or empty
     */
    public BaseResource(final String apiKey) {
        if (Objects.isNull(apiKey) || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("API key cannot be null or empty");
        }
        this.token = apiKey;
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Executes a GraphQL query against the Monday.com API.
     *
     * @param query The GraphQL query to execute
     * @return Returns body response as string
     * @throws IOException              if an I/O error occurs when sending or receiving
     * @throws InterruptedException     if the operation is interrupted
     * @throws IllegalArgumentException if the query is null or empty
     */
    public String get(final String query) throws IOException, InterruptedException {
        if (Objects.isNull(query) || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }

        JSONObject jsonPayload = new JSONObject().put("query", query);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL))
                .header(CONTENT_TYPE_HEADER, CONTENT_TYPE_VALUE)
                .header(AUTH_HEADER, "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload.toString()))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    /**
     * Converts a HashMap to a JSON string with escaped forward slashes.
     *
     * @param map HashMap containing key-value pairs to convert
     * @return JSON string representation of the map
     * @throws IllegalArgumentException if the map is null
     */
    protected String hashMapToJson(final HashMap<String, String> map) {
        Objects.requireNonNull(map, "Map cannot be null");

        Type typeObject = new TypeToken<HashMap<String, String>>() {
        }.getType();
        String jsonObject = gson.toJson(map, typeObject);
        return gson.toJson(jsonObject).replace("/", "//");
    }

    /**
     * Converts a string to its JSON representation.
     *
     * @param stringInput String to convert to JSON format
     * @return JSON string representation
     * @throws IllegalArgumentException if input is null
     */
    protected String stringTOJson(final String stringInput) {
        Objects.requireNonNull(stringInput, "Input string cannot be null");
        return gson.toJson(stringInput);
    }
}