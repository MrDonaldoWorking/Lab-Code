package entity;

import java.util.List;

public class Response {
    private final List<Post> items;
    private final long total_count;

    public Response(final List<Post> items, final long total_count) {
        this.items = items;
        this.total_count = total_count;
    }

    public List<Post> getItems() {
        return items;
    }

    public long getTotalCount() {
        return total_count;
    }
}
