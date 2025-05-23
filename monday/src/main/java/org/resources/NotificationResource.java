package org.resources;

import org.query.QueryJoins;

import java.io.IOException;

/**
 * Provides functionality for managing and creating notifications within the application.
 * Extends the capabilities of the BaseResource class to interact with an external API
 * for notification-related operations.
 */
public class NotificationResource extends BaseResource {

    /**
     * Constructs a new instance of NotificationResource.
     *
     * @param apiKey API authentication key for accessing the notification-related API operations
     */
    public NotificationResource(String apiKey) {
        super(apiKey);
    }

    /**
     * Creates a notification based on the provided details and sends a request to the external API.
     *
     * @param userId the unique identifier of the user creating the notification
     * @param targetId the unique id of the element related to notification
     * @param text the content of the notification message
     * @return the response body received from the external API as a string
     * @throws IOException if an I/O error occurs during the API request
     * @throws InterruptedException if the API request is interrupted
     */
    public String createNotification(String userId, String targetId, String text)
            throws IOException, InterruptedException {
        return get(QueryJoins.createNotificationQuery(userId, targetId, text));
    }
}
