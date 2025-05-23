package org.query;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryJoinsTest {

    /**
     * Tests for the `mutateItemQuery` method in the {@link QueryJoins} class.
     * <p>
     * The `mutateItemQuery` method generates a GraphQL mutation query to create
     * an item in a specific board and group with the provided name, column values,
     * and a flag indicating whether to create labels if they are missing.
     */

    @Test
    void testMutateItemQuery_WithAllParametersValid() {
        // Arrange
        String boardId = "12345";
        String groupId = "group_01";
        String itemName = "Test Item";
        String columnValues = "{\"key\":\"value\"}";
        Boolean createLabelIfMissing = true;

        // Act
        String result = QueryJoins.mutateItemQuery(boardId, groupId, itemName, columnValues, createLabelIfMissing);

        // Assert
        String expected = """
                mutation {
                    create_item (
                        board_id: 12345,
                        group_id: "group_01",
                        item_name: "Test Item",
                        column_values: {\"key\":\"value\"},
                        create_labels_if_missing: true
                    ) {
                        id
                    }
                }
                """;
        assertEquals(expected, result);
    }

    @Test
    void testMutateItemQuery_WithNullColumnValues() {
        // Arrange
        String boardId = "67890";
        String groupId = "group_02";
        String itemName = "Another Item";
        String columnValues = "null";
        Boolean createLabelIfMissing = false;

        // Act
        String result = QueryJoins.mutateItemQuery(boardId, groupId, itemName, columnValues, createLabelIfMissing);

        // Assert
        String expected = """
                mutation {
                    create_item (
                        board_id: 67890,
                        group_id: "group_02",
                        item_name: "Another Item",
                        column_values: null,
                        create_labels_if_missing: false
                    ) {
                        id
                    }
                }
                """;
        assertEquals(expected, result);
    }

    @Test
    void testMutateItemQuery_WithEmptyStrings() {
        // Arrange
        String boardId = "0";
        String groupId = "";
        String itemName = "";
        String columnValues = "{}";
        Boolean createLabelIfMissing = true;

        // Act
        String result = QueryJoins.mutateItemQuery(boardId, groupId, itemName, columnValues, createLabelIfMissing);

        // Assert
        String expected = """
                mutation {
                    create_item (
                        board_id: 0,
                        group_id: "",
                        item_name: "",
                        column_values: {},
                        create_labels_if_missing: true
                    ) {
                        id
                    }
                }
                """;
        assertEquals(expected, result);
    }

    @Test
    void testMutateItemQuery_WithLargeBoardId() {
        // Arrange
        String boardId = "9876543210123456789";
        String groupId = "group_03";
        String itemName = "Large Board Test";
        String columnValues = "{\"key\":\"value\"}";
        Boolean createLabelIfMissing = false;

        // Act
        String result = QueryJoins.mutateItemQuery(boardId, groupId, itemName, columnValues, createLabelIfMissing);

        // Assert
        String expected = """
                mutation {
                    create_item (
                        board_id: 9876543210123456789,
                        group_id: "group_03",
                        item_name: "Large Board Test",
                        column_values: {"key":"value"},
                        create_labels_if_missing: false
                    ) {
                        id
                    }
                }
                """;
        assertEquals(expected, result);
    }

    /**
     * Tests for the `getItemQuery` method in the {@link QueryJoins} class.
     * <p>
     * The `getItemQuery` method generates a GraphQL query to fetch items
     * by column values with a specific structure that includes cursor, items,
     * updates, group, and column_values.
     */
    @Test
    void testGetItemQuery_WithValidColumnValuesFilter() {
        // Arrange
        String columnValuesFilter = "board_id: 12345, column_id: \"status\", column_value: \"Done\"";

        // Act
        String result = QueryJoins.getItemQuery(columnValuesFilter);

        // Assert
        String expected = """
                query
                {
                    items_page_by_column_values (board_id: 12345, column_id: "status", column_value: "Done") {
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
               """;
        assertEquals(expected, result);
    }

    @Test
    void testDeleteUpdate_WithValidId() {
        // Arrange
        String itemId = "123";

        // Act
        String result = QueryJoins.deleteUpdate(itemId);

        // Assert
        String expected = """
                    mutation {
                        delete_update (
                            id: 123
                        ) {
                            id
                        }
                    }
                """;
        assertEquals(expected, result);
    }

    @Test
    void testDeleteUpdate_WithInvalidId() {
        // Arrange
        String itemId = "";

        // Act
        String result = QueryJoins.deleteUpdate(itemId);

        // Assert
        String expected = """
                    mutation {
                        delete_update (
                            id: 
                        ) {
                            id
                        }
                    }
                """;
        assertEquals(expected, result);
    }
}
