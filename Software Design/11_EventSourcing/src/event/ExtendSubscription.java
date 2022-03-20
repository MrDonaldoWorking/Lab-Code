package event;

import java.time.LocalDate;

public class ExtendSubscription extends Event {
    public ExtendSubscription(final LocalDate when) {
        super(when, LocalDate.class);
    }
}
