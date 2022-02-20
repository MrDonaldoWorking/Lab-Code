package entity;

public class Post {
    private final long id;
    private final long owner_id;
    private final long date;
    private final String content;

    public Post(final long id, final long ownerId, final long date, final String content) {
        this.id = id;
        this.owner_id = ownerId;
        this.date = date;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return owner_id;
    }

    public long getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}
