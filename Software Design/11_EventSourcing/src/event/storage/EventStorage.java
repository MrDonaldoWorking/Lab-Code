package event.storage;

import event.Event;
import service.ReportService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventStorage {
    private final Map<Integer, List<Event>> log = new HashMap<>();
    private final Map<Integer, ReportService> usersDetails = new HashMap<>();

    public boolean hasUserId(final int userId) {
        return log.containsKey(userId);
    }

    public void addEvent(final int userId, final Event event) {
        log.putIfAbsent(userId, new ArrayList<>());
        log.get(userId).add(event);

        usersDetails.putIfAbsent(userId, new ReportService());
        usersDetails.get(userId).handleEvent(userId, event);
    }

    public List<Event> getUserEvents(final int userId) {
        return log.get(userId);
    }

    public ReportService getUserReport(final int userId) {
        return usersDetails.get(userId);
    }
}
