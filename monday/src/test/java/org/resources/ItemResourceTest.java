//package org.resources;
//
//import org.junit.jupiter.api.Test;
//import org.query.QueryJoins;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//class ItemResourceTest {
//
//    @Test
//    void testChangeItemValue_SuccessfulValueChange() throws IOException, InterruptedException {
//        // Arrange
//        String boardId = "board123";
//        String itemId = "item456";
//        String columnId = "column789";
//        String value = "newValue";
//
//        QueryJoins queryJoinsMock = mock(QueryJoins.class);
//        BaseResource baseResourceMock = mock(BaseResource.class);
//        ItemResource itemResource = new ItemResource("mockToken") {
//            @Override
//            public String get(String query) throws IOException, InterruptedException {
//                return "success";
//            }
//        };
//
//        when(queryJoinsMock.updateItemQuery(boardId, itemId, columnId, value)).thenReturn("mockQuery");
//
//        // Act
//        String result = itemResource.changeItemValue(boardId, itemId, columnId, value);
//
//        // Assert
//        assertEquals("success", result);
//    }
//
//    @Test
//    void testChangeItemValue_ThrowsIOException() {
//        // Arrange
//        String boardId = "board123";
//        String itemId = "item456";
//        String columnId = "column789";
//        String value = "newValue";
//        ItemResource itemResource = new ItemResource("mockToken") {
//            @Override
//            public String get(String query) throws IOException {
//                throw new IOException("IO error");
//            }
//        };
//
//        // Act & Assert
//        assertThrows(IOException.class, () -> itemResource.changeItemValue(boardId, itemId, columnId, value));
//    }
//
//    @Test
//    void testChangeItemValue_ThrowsInterruptedException() {
//        // Arrange
//        String boardId = "board123";
//        String itemId = "item456";
//        String columnId = "column789";
//        String value = "newValue";
//        ItemResource itemResource = new ItemResource("mockToken") {
//            @Override
//            public String get(String query) throws InterruptedException {
//                throw new InterruptedException("Interrupted");
//            }
//        };
//
//        // Act & Assert
//        assertThrows(InterruptedException.class, () -> itemResource.changeItemValue(boardId, itemId, columnId, value));
//    }
//}