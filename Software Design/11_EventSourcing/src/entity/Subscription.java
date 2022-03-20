package entity;

import java.time.LocalDate;

public class Subscription {
    private final int id;
    private LocalDate expires;

    public Subscription(final int id, final LocalDate expires) {
        this.id = id;
        this.expires = expires;
    }

    public int getId() {
        return id;
    }

    public void setExpires(final LocalDate expires) {
        this.expires = expires;
    }

    public LocalDate getExpires() {
        return expires;
    }
}
