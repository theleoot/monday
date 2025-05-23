package org.resources;

import org.query.QueryJoins;

import java.io.IOException;

public class MeResource extends BaseResource{

    public MeResource(String token) {
        super(token);
    }

    public String getDetails()
            throws IOException, InterruptedException {
        return get(QueryJoins.getCurrentUserDetails());
    }
}
