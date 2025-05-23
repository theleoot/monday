package org.resources;

import org.query.QueryJoins;

import java.io.IOException;
import java.util.HashMap;

/**
 * Resource class for interacting with Monday.com items.
 * Extends BaseResource to handle item-specific API operations.
 */
public class ItemResource extends BaseResource {

    /**
     * Creates a new ItemResource instance.
     *
     * @param token API authentication token for Monday.com
     */
    public ItemResource(final String token) {
        super(token);
    }

    /**
     * Creates a new item in a Monday.com board.
     *
     * @param boardId              ID of the board to create the item in
     * @param groupId              ID of the group to add the item to
     * @param itemName             Name of the new item
     * @param columnValues         Map of column IDs to their values
     * @param createLabelIfMissing Whether to create missing labels
     * @return API response containing the created item details
     * @throws IOException          if there is an error making the API request
     * @throws InterruptedException if the API request is interrupted
     */
    public String createItem(final String boardId,
                             final String groupId,
                             final String itemName,
                             final HashMap<String, String> columnValues,
                             final Boolean createLabelIfMissing)
            throws IOException, InterruptedException {
        return get(QueryJoins.mutateItemQuery(
                boardId, groupId, itemName,
                hashMapToJson(columnValues),
                createLabelIfMissing));
    }

    /**
     * Creates a new subitem under a parent item in Monday.com.
     *
     * @param parentId             ID of the parent item
     * @param subitemName          Name of the subitem to create
     * @param columnValues         Map of column IDs to their values
     * @param createLabelIfMissing Whether to create missing labels
     * @return API response containing the created subitem details
     * @throws IOException          if there is an error making the API request
     * @throws InterruptedException if the API request is interrupted
     */
    public String createSubitem(
            final String parentId,
            final String subitemName,
            final HashMap<String, String> columnValues,
            final Boolean createLabelIfMissing)
            throws IOException, InterruptedException {
        return get(QueryJoins.mutateSubitemQuery(
                parentId,
                subitemName,
                hashMapToJson(columnValues),
                createLabelIfMissing));
    }

    /**
     * Fetches items by column value from a Monday.com board.
     *
     * @param boardId  ID of the board to fetch items from
     * @param columnId ID of the column to filter by
     * @param value    Value to filter the column by
     * @return API response containing the filtered items
     * @throws IOException          if there is an error making the API request
     * @throws InterruptedException if the API request is interrupted
     */
    public String fetchItemsByColumnValue(
            final String boardId,
            final String columnId,
            final String value)
            throws IOException, InterruptedException {
        String columnValuesFilter = String.format(
                "board_id: %s, columns: [{column_id: \"%s\", column_values: [\"%s\"]}]",
                boardId,
                columnId,
                value);

        return get(QueryJoins.getItemQuery(columnValuesFilter));
    }

    /**
     * Fetches an item by its ID.
     *
     * @param itemId ID of the item to fetch
     * @return API response containing the item details
     * @throws IOException          if there is an error making the API request
     * @throws InterruptedException if the API request is interrupted
     */
    public String fetchItemById(final String itemId)
            throws IOException, InterruptedException {
        return get(QueryJoins.fetchItemByIdQuery(itemId));
    }

    /**
     * Updates the value of a specific column for an item.
     *
     * @param boardId  ID of the board containing the item
     * @param itemId   ID of the item to update
     * @param columnId ID of the column to update
     * @param value    New value to set for the column
     * @return API response containing the updated item details
     * @throws IOException          if there is an error making the API request
     * @throws InterruptedException if the API request is interrupted
     */
    public String changeItemValue(
            final String boardId,
            final String itemId,
            final String columnId,
            final String value)
            throws IOException, InterruptedException {
        return get(QueryJoins.updateItemQuery(boardId, itemId, columnId, value));
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
    public String changeMultiplesColumnValues(
            final String boardId,
            final String itemId,
            final String columnValues)
            throws IOException, InterruptedException {
        return get(QueryJoins.updateMultiplesColumnValuesQuery(
                boardId,
                itemId,
                stringTOJson(columnValues)
        ));
    }

    /**
     * Moves an item to a different group within its board.
     *
     * @param itemId  ID of the item to move
     * @param groupId ID of the destination group
     * @return API response confirming the move operation
     * @throws IOException          if there is an error making the API request
     * @throws InterruptedException if the API request is interrupted
     */
    public String moveItemToGroup(final String itemId, final String groupId)
            throws IOException, InterruptedException {
        return get(QueryJoins.moveItemToGroupQuery(itemId, groupId));
    }

    /**
     * Archives an item in Monday.com.
     *
     * @param itemId ID of the item to archive
     * @return API response confirming the archive operation
     * @throws IOException          if there is an error making the API request
     * @throws InterruptedException if the API request is interrupted
     */
    public String archiveItem(final String itemId)
            throws IOException, InterruptedException {
        return get(QueryJoins.archiveItemQuery(itemId));
    }

    /**
     * Permanently deletes an item from Monday.com.
     *
     * @param itemId ID of the item to delete
     * @return API response confirming the deletion
     * @throws IOException          if there is an error making the API request
     * @throws InterruptedException if the API request is interrupted
     */
    public String deleteItem(final String itemId)
            throws IOException, InterruptedException {
        return get(QueryJoins.deleteItemQuery(itemId));
    }
}