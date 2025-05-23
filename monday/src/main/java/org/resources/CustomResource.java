package org.resources;

import java.io.IOException;

public class CustomResource extends BaseResource {

    public CustomResource(String apiKey) {
        super(apiKey);
    }

    public String executeCustomQuery(String customQuery)
            throws IOException, InterruptedException {
        return get(customQuery);
    }
}
