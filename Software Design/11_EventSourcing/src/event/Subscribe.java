package event;

import java.time.LocalDate;

public class Subscribe extends Event {
    public Subscribe(final LocalDate when) {
        super(when, LocalDate.class);
    }
}
