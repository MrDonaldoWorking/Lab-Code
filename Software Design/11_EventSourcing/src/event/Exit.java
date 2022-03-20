package event;

import java.time.LocalDateTime;

public class Exit extends Event {
    public Exit(final LocalDateTime when) {
        super(when, LocalDateTime.class);
    }
}
