package org.query;


/**
 * Utility class for generating GraphQL query strings for Monday.com API operations.
 * Contains static methods to build mutation and query strings for various item and board operations.
 */
public final class QueryJoins {

    private QueryJoins() {
        // Private constructor to prevent instantiation
    }

    /**
     * Generates a GraphQL mutation query string for creating an item in Monday.com.
     *
     * @param boardId              The ID of the board where the item will be created
     * @param groupId              The ID of the group within the board where the item will be placed
     * @param itemName             The name/title of the new item
     * @param columnValues         JSON string containing column values for the new item
     * @param createLabelIfMissing Whether to create missing labels automatically
     * @return GraphQL mutation query string for item creation
     */
    public static String mutateItemQuery(final String boardId, final String groupId, final String itemName, final String columnValues, final Boolean createLabelIfMissing) {

        final String ITEM_MUTATION_TEMPLATE = """
                 mutation {
                     create_item (
                         board_id: %s,
                         group_id: "%s",
                         item_name: "%s",
                         column_values: %s,
                         create_labels_if_missing: %b
                     ) {
                         id
                     }
                 }
                """;

        return ITEM_MUTATION_TEMPLATE.formatted(boardId, groupId, itemName, columnValues, createLabelIfMissing);
    }

    /**
     * Generates a GraphQL mutation query to create a subitem in Monday.com.
     *
     * @param parentItemId         ID of the parent item to which the subitem will be linked
     * @param subitemName          Name of the subitem to be created
     * @param columnValues         JSON string containing the subitem's column values
     * @param createLabelIfMissing Flag to automatically create missing labels
     * @return String containing the formatted GraphQL mutation query
     */
    public static String mutateSubitemQuery(final String parentItemId, final String subitemName, final String columnValues, final Boolean createLabelIfMissing) {

        final String SUBITEM_MUTATION_TEMPLATE = """
                mutation {
                    create_subitem (
                        parent_item_id: %s,
                        item_name: "%s",
                        column_values: %s,
                        create_labels_if_missing: %b
                    ) {
                        id,
                        name,
                        column_values {
                            id,
                            text
                        },
                        board {
                            id,
                            name
                        }
                    }
                }
                """;

        return SUBITEM_MUTATION_TEMPLATE.formatted(parentItemId, subitemName, columnValues, createLabelIfMissing);
    }

    /**
     * Generates a GraphQL query to fetch item details by ID.
     *
     * @param itemId The ID of the item to fetch
     * @return String containing the formatted GraphQL query
     */
    public static String fetchItemByIdQuery(final String itemId) {
        final String FETCH_ITEM_BY_ID_TEMPLATE = """
                    query {
                         items(ids: %s) {
                             id,
                             name,
                             group {
                                 id,
                                 title
                             }
                             column_values {
                                 id,
                                 text,
                                 value
                             }
                         }
                     }
                """;

        return FETCH_ITEM_BY_ID_TEMPLATE.formatted(itemId);
    }

    /**
     * Generates a GraphQL query to fetch board details by ID.
     *
     * @param boardId The ID of the board to fetch
     * @return String containing the formatted GraphQL query
     */
    public static String fetchBoardByIdQuery(final String boardId) {
        return """
                 query {
                     boards(ids: %s) {
                         id,
                         name,
                         permissions,
                         tags {
                             id,
                             name
                         },
                         groups {
                             id,
                             title
                         },
                         columns {
                             id,
                             title,
                             type,
                             settings_str
                         }
                     }
                 }
                """.formatted(boardId);
    }

    /**
     * Generates a GraphQL query to fetch items by column values from Monday.com.
     *
     * @param columnValuesFilter String containing the filter criteria for column values
     * @return String containing the formatted GraphQL query
     */
    public static String getItemQuery(final String columnValuesFilter) {
        return """
                 query {
                     items_page_by_column_values (%s) {
                         cursor
                         items {
                             id
                             name
                             updates {
                                 id
                                 body
                             }
                             group {
                                 id
                                 title
                             }
                             column_values {
                                 id
                                 text
                                 value
                             }
                         }
                     }
                 }
                """.formatted(columnValuesFilter);
    }

    /**
     * Generates a GraphQL mutation to update an item's column value.
     *
     * @param boardId  The ID of the board containing the item
     * @param itemId   The ID of the item to update
     * @param columnId The ID of the column to update
     * @param value    The new value to set
     * @return String containing the formatted GraphQL mutation
     */
    public static String updateItemQuery(
            final String boardId,
            final String itemId,
            final String columnId,
            final String value) {

        final String UPDATE_ITEM_TEMPLATE = """
                    mutation {
                        change_simple_column_value (
                            board_id: %s,
                            item_id: %s,
                            column_id: "%s",
                            value: "%s"
                        ) {
                            id,
                            name,
                            column_values {
                                id,
                                text,
                                value
                            }
                        }
                    }
                """;

        return UPDATE_ITEM_TEMPLATE.formatted(boardId, itemId, columnId, value);
    }

    /**
     * Generates a GraphQL mutation query string to update multiple column values for an item on a specified board.
     *
     * @param boardId      The ID of the board containing the item to be updated.
     * @param itemId       The ID of the item whose column values are to be updated.
     * @param columnValues JSON string containing the updated column values.
     * @return A string containing the formatted GraphQL mutation query for updating multiple column values.
     */
    public static String updateMultiplesColumnValuesQuery(
            final String boardId,
            final String itemId,
            final String columnValues) {

        final String UPDATE_MULTIPLES_COLUMNS_VALUES_TEMPLATE = """
            mutation {
                change_multiple_column_values (
                    board_id: %s,
                    item_id: %s,
                    column_values: %s,
                    create_labels_if_missing: true,
                ) {
                    id,
                    name,
                    column_values {
                        id,
                        text
                    }
                }
            }
        """;

        return UPDATE_MULTIPLES_COLUMNS_VALUES_TEMPLATE.formatted(boardId, itemId, columnValues);
    }

    /**
     * Generates a GraphQL mutation to move an item to a different group.
     *
     * @param itemId  The ID of the item to move
     * @param groupId The ID of the destination group
     * @return String containing the formatted GraphQL mutation
     */
    public static String moveItemToGroupQuery(final String itemId, final String groupId) {
        final String MOVE_ITEM_TEMPLATE = """
                    mutation {
                        move_item_to_group (
                            item_id: "%s",
                            group_id: "%s"
                        ) {
                            id
                        }
                    }
                """;

        return MOVE_ITEM_TEMPLATE.formatted(itemId, groupId);
    }

    /**
     * Generates a GraphQL mutation to archive an item.
     *
     * @param itemId The ID of the item to archive
     * @return String containing the formatted GraphQL mutation
     */
    public static String archiveItemQuery(final String itemId) {
        final String ARCHIVE_ITEM_TEMPLATE = """
                    mutation {
                        archive_item (
                            item_id: %s
                        ) {
                            id
                        }
                    }
                """;

        return ARCHIVE_ITEM_TEMPLATE.formatted(itemId);
    }

    /**
     * Generates a GraphQL mutation to delete an item.
     *
     * @param itemId The ID of the item to delete
     * @return String containing the formatted GraphQL mutation
     */
    public static String deleteItemQuery(final String itemId) {
        final String DELETE_ITEM_TEMPLATE = """
                    mutation {
                        delete_item (
                            item_id: %s
                        ) {
                            id
                        }
                    }
                """;

        return DELETE_ITEM_TEMPLATE.formatted(itemId);
    }

    public static String fetchBoardItemsQuery(String boardId) {
        final String FETCH_BOARD_ITEMS_TEMPLATE = """
                    query {
                        boards (ids: %s) {
                            name,
                            items_page {
                                cursor,
                                items {
                                    group {
                                        id,
                                        title
                                    }
                                    id,
                                    name,
                                    column_values {
                                        id,
                                        text,
                                        type,
                                        value
                                    }
                                }
                            }
                        }
                    }
                """;

        return FETCH_BOARD_ITEMS_TEMPLATE.formatted(boardId);
    }

    public static String createNotificationQuery(
            String userId,
            String targetId,
            String text) {
        final String CREATE_NOTIFICATION_TEMPLATE = """
                    mutation {
                        create_notification (
                            user_id: %s,
                            target_id: %s,
                            text: "%s",
                            target_type: Project
                        ) {
                            text
                        }
                    }
                """;

        return CREATE_NOTIFICATION_TEMPLATE.formatted(userId, targetId, text);
    }

    public static String getCurrentUserDetails() {
        final String GET_CURRENT_USER_DETAILS_TEMPLATE = """
                    query {
                       me {
                         is_guest
                         created_at
                         name
                         id
                       }
                     }
                """;

        return GET_CURRENT_USER_DETAILS_TEMPLATE;
    }

    public static String createUpdateQuery(
            String itemId,
            String updateValue) {
        final String CREATE_UPDATE_TEMPLATE = """
                    mutation {
                        create_update (
                            item_id: %s,
                            body: "%s"
                        ) {
                            id
                        }
                    }
                """;

        return CREATE_UPDATE_TEMPLATE.formatted(itemId, updateValue);
    }

    public static String deleteUpdate(String itemId) {
        final String DELETE_UPDATE_TEMPLATE = """
                    mutation {
                        delete_update (
                            id: %s
                        ) {
                            id
                        }
                    }
                """;

        return DELETE_UPDATE_TEMPLATE.formatted(itemId);
    }

    public static String fetchItemUpdatesQuery(
            String itemId,
            int limit) {
        final String FETCH_ITEM_UPDATES_TEMPLATE = """
                    query {
                        items (ids: [%s]) {
                            id,
                            updates (limit: %s) {
                                id,
                                body
                            }
                        }
                    }
                """;

        return FETCH_ITEM_UPDATES_TEMPLATE.formatted(itemId, limit);
    }

    public static String deleteItemUpdateQuery(String itemId) {
        final String DELETE_ITEM_UPDATE_TEMPLATE = """
                    mutation {
                        delete_update (id: "%s") {
                            id
                        }
                    }
                """;

        return DELETE_ITEM_UPDATE_TEMPLATE.formatted(itemId);
    }

    public static String createColumnQuery(
            String boardId,
            String columnTitle,
            String columType) {
        final String CREATE_COLUMN_TEMPLATE = """
                    mutation {
                        create_column (
                            board_id: %s,
                            title: "%s",
                            column_type: %s
                        ) {
                            id,
                            title,
                            description
                        }
                    }
                """;

        return CREATE_COLUMN_TEMPLATE.formatted(
                boardId,
                columnTitle,
                columType);
    }
}
