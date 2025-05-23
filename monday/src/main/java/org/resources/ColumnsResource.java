package org.resources;

import org.query.QueryJoins;

import java.io.IOException;


/**
 * Resource class for managing Monday.com board columns.
 * Provides functionality to create and manipulate columns within boards.
 */
public class ColumnsResource extends BaseResource {

    /**
     * Creates a new ColumnsResource instance.
     *
     * @param token The API authentication token for Monday.com
     * @throws IllegalArgumentException if the token is null or empty
     */
    public ColumnsResource(final String token) {
        super(token);
    }

    /**
     * Creates a new column in a specified board.
     *
     * @param boardId     The ID of the board to add the column to
     * @param columnTitle The title/name of the new column
     * @param columnType  The type of column to create (e.g., "text", "numeric", etc.)
     * @return Response from the API as a JSON string
     * @throws IOException              if an I/O error occurs during the request
     * @throws InterruptedException     if the request is interrupted
     */
    public String createColumn(
            final String boardId,
            final String columnTitle,
            final String columnType)
            throws IOException, InterruptedException {
        return get(QueryJoins.createColumnQuery(boardId, columnTitle, columnType));
    }

    /**
     * Updates multiple column values for a specific item in a specified board.
     *
     * @param boardId      ID of the board containing the item
     * @param itemId       ID of the item to update
     * @param columnValues JSON-formatted string containing the column IDs and their corresponding new values.
     *                     Example:
     * <pre>
     * {@code
     * "{\"status\": \"Done\", \"text\": \"Updated text\"}"
     * }
     * </pre>
     * @return API response containing the updated item details
     * @throws IOException          if there is an error making the API request
     * @throws InterruptedException if the API request is interrupted
     */
    public String updateMultipleColumns(
            String boardId,
            String itemId,
            String columnValues)
            throws IOException, InterruptedException {
        return get(QueryJoins.updateMultiplesColumnValuesQuery(
                boardId,
                itemId,
                stringTOJson(columnValues)));
    }
}