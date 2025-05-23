package org.resources;

import org.query.QueryJoins;

import java.io.IOException;

public class UpdateResource extends BaseResource {

    public UpdateResource(String api_key) {
        super(api_key);
    }

    public String createUpdate(String itemId, String updateValue)
            throws IOException, InterruptedException {
        return get(QueryJoins.createUpdateQuery(itemId, updateValue));
    }

    public String fetchItemUpdates(String itemId, int limit)
            throws IOException, InterruptedException {
        return get(QueryJoins.fetchItemUpdatesQuery(itemId, limit));
    }

    public String deleteItemUpdate(String itemId)
            throws IOException, InterruptedException {
        return get(QueryJoins.deleteItemUpdateQuery(itemId));
    }
}
