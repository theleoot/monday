package org.resources;

import org.query.QueryJoins;

import java.io.IOException;


/**
 * BoardResource provides functionality for interacting with boards in the Monday.com API.
 * It extends the BaseResource class and uses the provided API key to perform operations on boards.
 */
public class BoardResource extends BaseResource {

    public BoardResource(String apiKey) {
        super(apiKey);
    }

    /**
     * Fetches the details of a board from the Monday.com API using its ID.
     *
     * @param boardId The ID of the board to be fetched.
     * @return A JSON string containing the board details, including its columns, groups, and tags.
     * @throws IOException If an input or output exception occurs during the HTTP request.
     * @throws InterruptedException If the HTTP request is interrupted.
     */
    public String fetchBoardById(String boardId)
            throws IOException, InterruptedException {
        return get(QueryJoins.fetchBoardByIdQuery(boardId));
    }

    /**
     * Fetches the items of a board from the Monday.com API using its ID.
     *
     * @param boardId The ID of the board whose items are to be fetched.
     * @return A JSON string containing the board items, including their details.
     * @throws IOException If an input or output exception occurs during the HTTP request.
     * @throws InterruptedException If the HTTP request is interrupted.
     */
    public String fetchItemsByBoardId(String boardId)
            throws IOException, InterruptedException {
        return get(QueryJoins.fetchBoardItemsQuery(boardId));
    }
}
