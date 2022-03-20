package service;

import event.Enter;
import event.Event;
import event.Exit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class ReportService {
    private final Map<LocalDate, Integer> dailyVisits = new HashMap<>();
    private final Map<LocalDate, Long> dailyDuration = new HashMap<>();
    private final Map<Integer, LocalDateTime> visit = new HashMap<>();
    private int visits = 0;
    private long duration = 0L;

    public void handleEvent(final int userId, final Event event) {
        if (event instanceof Enter) {
            visit.put(userId, (LocalDateTime) event.getDetail());
        } else if (event instanceof Exit) {
            final LocalDateTime time = (LocalDateTime) event.getDetail();
            final LocalDate date = time.toLocalDate();
            final long curDuration = dailyDuration.getOrDefault(date, 0L)
                    + ChronoUnit.HOURS.between(visit.get(userId), time);
            dailyVisits.put(date, dailyVisits.getOrDefault(date, 0) + 1);
            dailyDuration.put(date, curDuration);

            ++visits;
            duration += curDuration;
        }
    }

    public double meanVisitFrequency() {
        return (double) visits / dailyVisits.keySet().size();
    }

    public double meanVisitDuration() {
        return (double) duration / visits;
    }
}
