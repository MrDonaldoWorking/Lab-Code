package entity;

// for ReturnActor
public class Request {
    private final String query;

    public Request(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
