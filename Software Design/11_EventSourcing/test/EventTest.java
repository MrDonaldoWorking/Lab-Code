import entity.Subscription;
import event.storage.EventStorage;
import org.junit.Before;
import org.junit.Test;
import service.ManagerService;
import service.ReportService;
import service.TurnstileService;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    private EventStorage storage;
    private CustomClock clock;

    @Before
    public void prepare() {
        storage = new EventStorage();
        clock = new CustomClock();
    }

    @Test
    public void singleSubscription() {
        final ManagerService service = new ManagerService(storage, clock);
        service.subscribe(1);

        final Subscription subscription = service.getInfo(1);
        assertEquals(1, subscription.getId());
        assertEquals(LocalDate.ofInstant(clock.instant(), clock.getZone()), subscription.getExpires());
    }

    @Test
    public void withExtend() {
        final ManagerService service = new ManagerService(storage, clock);
        service.subscribe(1);

        assertThrows(IllegalStateException.class, () -> service.extendSubscription(2, 10));
        assertDoesNotThrow(() -> service.extendSubscription(1, 10));

        final Subscription subscription = service.getInfo(1);
        assertEquals(1, subscription.getId());
        assertEquals(LocalDate.ofInstant(clock.instant(), clock.getZone()).plusDays(10), subscription.getExpires());
    }

    @Test
    public void reportTest() {
        final TurnstileService turnstileService = new TurnstileService(storage, clock);
        final ManagerService managerService = new ManagerService(storage, clock);
        managerService.subscribe(1);
        managerService.extendSubscription(1, 1);
        turnstileService.enter(1);
        clock.setPlusTo(Duration.ofHours(1));
        turnstileService.exit(1);

        assertEquals(1, storage.getUserReport(1).meanVisitDuration());
        assertEquals(1, storage.getUserReport(1).meanVisitFrequency());
    }
}
