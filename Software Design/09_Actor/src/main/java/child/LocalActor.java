package child;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Response;

import java.util.ArrayList;
import java.util.List;

public class LocalActor extends AbstractActor {
    @Override
    protected String getQuery() {
        return "http://localhost:33303/get";
    }

    @Override
    protected Response parse(final String response) {
        final JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        final List<String> links = List.of(json.get("link").getAsString());
        return new Response(links, "local");
    }
}
