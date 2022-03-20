package event;

import java.time.LocalDateTime;

public class Enter extends Event {
    public Enter(final LocalDateTime when) {
        super(when, LocalDateTime.class);
    }
}
