package child;

import com.google.gson.*;
import entity.Response;

import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;
import java.util.List;

public class GoogleActor extends AbstractActor {
    private final String APIKey = "d428a70b23c9a63fc89ccc8fee44b8a00f9a4d9e162faf4e267f265e0fc8469e";

    @Override
    protected String getQuery() {
        return "https://serpapi.com/search.json?hl=en&gl=us&google_domain=google.com&api_key=" + APIKey;
    }

    @Override
    protected Response parse(final String response) {
        final JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        final JsonArray pages = json.getAsJsonArray("organic_results");

        final List<String> links = new ArrayList<>();
        for (final JsonElement page : pages) {
            links.add(page.getAsJsonObject().get("link").getAsString());
        }
        return new Response(links, "google");
    }
}
