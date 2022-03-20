package service;

import entity.Subscription;
import event.Event;
import event.ExtendSubscription;
import event.Subscribe;
import event.storage.EventStorage;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static service.TurnstileService.checkSubscription;
import static service.TurnstileService.getExpirationDate;

public class ManagerService {
    private final EventStorage storage;
    private final Clock clock;

    public ManagerService(final EventStorage storage, final Clock clock) {
        this.storage = storage;
        this.clock = clock;
    }

    public void subscribe(final int userId) {
        if (storage.hasUserId(userId)) {
            throw new IllegalStateException("User " + userId + " has already subscribed");
        }
        final List<Event> events = storage.getUserEvents(userId);
        if (events != null && !events.isEmpty()) {
            throw new IllegalStateException("User " + userId + " has already events, but didn't subscribe");
        }
        storage.addEvent(userId, new Subscribe(LocalDate.ofInstant(clock.instant(), clock.getZone())));
    }

    public void extendSubscription(final int userId, final int days) {
        if (!storage.hasUserId(userId)) {
            throw new IllegalStateException("Nothing to extend to user " + userId);
        }
        final LocalDate expires = getExpirationDate(userId, storage);
        storage.addEvent(userId, new ExtendSubscription(expires.plusDays(days)));
    }

    public Subscription getInfo(final int userId) {
        checkSubscription(userId, storage);
        return new Subscription(userId, getExpirationDate(userId, storage));
    }
}
