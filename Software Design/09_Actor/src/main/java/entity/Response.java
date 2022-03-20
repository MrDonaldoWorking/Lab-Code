package entity;

import java.util.List;

public class Response {
    private final List<String> urls;
    private final String engine;

    public Response(final List<String> urls, final String engine) {
        this.urls = urls;
        this.engine = engine;
    }

    public List<String> getUrls() {
        return urls;
    }

    public String getEngine() {
        return engine;
    }
}
