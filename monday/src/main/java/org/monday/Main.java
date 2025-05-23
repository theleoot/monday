package org.monday;


import org.resources.*;
import java.util.Objects;


/**
 * Represents a client for interacting with the Monday.com API.
 * Provides access to board and item resources.
 */
class Monday {

    protected final String apiKey;

    /**
     * Creates a new Monday client instance.
     *
     * @param token The API authentication token for Monday.com required for authorization
     * @throws IllegalArgumentException if the token is null or empty
     */
    public Monday(String token) {
        if (Objects.isNull(token) || token.trim().isEmpty()) {
            throw new IllegalArgumentException("API token cannot be null or empty");
        }
        this.apiKey = token;
    }

    /**
     * Creates an ItemResource instance to interact with Monday.com items.
     *
     * @return An ItemResource instance for making item-related API calls
     */
    public ItemResource items() {
        return new ItemResource(apiKey);
    }

    /**
     * Creates a ColumnsResource instance to interact with Monday.com columns.
     *
     * @return A ColumnsResource instance for making column-related API calls
     */
    public ColumnsResource columns() { return new ColumnsResource(apiKey); }

    /**
     * Creates a BoardResource instance to interact with Monday.com boards.
     *
     * @return A BoardResource instance for making board-related API calls
     */
    public BoardResource boards() {
        return new BoardResource(apiKey);
    }

    /**
     * Creates a CustomResource instance to execute custom GraphQL queries.
     *
     * @return A CustomResource instance for making custom API calls
     */
    public CustomResource custom() {
        return new CustomResource(apiKey);
    }

    /**
     * Creates a NotificationResource instance to interact with Monday.com notifications.
     *
     * @return A NotificationResource instance for making notification-related API calls
     */
    public NotificationResource notifications() { return new NotificationResource(apiKey); }

    /**
     * Creates a MeResource instance to interact with personal user details
     * in the Monday.com API, such as current user*/
    public MeResource me() { return new MeResource(apiKey); }

    /**
     * Creates an UpdateResource instance to interact with Monday.com updates.
     *
     * @return An UpdateResource instance for making update-related API calls
     */
    public UpdateResource updates() { return new UpdateResource(apiKey); }
}
