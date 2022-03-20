package service;

import event.*;
import event.storage.EventStorage;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TurnstileService {
    private final EventStorage storage;
    private final Clock clock;

    public TurnstileService(final EventStorage storage, final Clock clock) {
        this.storage = storage;
        this.clock = clock;
    }

    public void enter(final int userId) {
        checkSubscription(userId, storage);
        if (isInside(userId)) {
            throw new IllegalStateException("User " + userId + " can't enter while inside");
        }
        final LocalDate expires = getExpirationDate(userId, storage);
        final LocalDate current = LocalDate.ofInstant(clock.instant(), clock.getZone());
        if (expires.isBefore(current)) {
            throw new IllegalStateException("User " + userId + " subscription is expired");
        }

        storage.addEvent(userId, new Enter(LocalDateTime.ofInstant(clock.instant(), clock.getZone())));
    }

    public void exit(final int userId) {
        checkSubscription(userId, storage);
        if (!isInside(userId)) {
            throw new IllegalStateException("User " + userId + " can't exit while outside");
        }

        storage.addEvent(userId, new Exit(LocalDateTime.ofInstant(clock.instant(), clock.getZone())));
    }

    public static void checkSubscription(final int userId, final EventStorage storage) {
        if (!storage.hasUserId(userId)) {
            throw new IllegalStateException("User " + userId + " has no subscription");
        }
        final List<Event> log = storage.getUserEvents(userId);
        final Event first;
        try {
            first = log.get(0);
        } catch (final IndexOutOfBoundsException e) {
            throw new IllegalStateException("User " + userId + " has no events");
        }
        if (!(first instanceof Subscribe)) {
            throw new IllegalStateException("Event List is not starting with Subscribe at User " + userId);
        }
    }

    private boolean isInside(final int userId) {
        final List<Event> log = storage.getUserEvents(userId);

        for (int i = log.size() - 1; i >= 0; --i) {
            final Event event = log.get(i);
            if (event instanceof Enter) {
                return true;
            }
            if (event instanceof Exit) {
                return false;
            }
        }
        return false;
    }

    public static LocalDate getExpirationDate(final int userId, final EventStorage storage) {
        final List<Event> log = storage.getUserEvents(userId);
        for (int i = log.size() - 1; i >= 0; --i) {
            final Event event = log.get(i);
            if (event instanceof ExtendSubscription) {
                return (LocalDate) event.getDetail();
            }
        }
        return (LocalDate) log.get(0).getDetail();
    }
}
