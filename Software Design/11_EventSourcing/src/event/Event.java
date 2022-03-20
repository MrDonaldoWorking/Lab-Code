package event;

import java.io.Serializable;

public abstract class Event {
    protected final Object detail;
    protected final Class<? extends Serializable> type;

    protected Event(final Object detail, final Class<? extends Serializable> type) {
        this.detail = detail;
        this.type = type;
    }

    public Object getDetail() {
        return detail;
    }

    public Class<? extends Serializable> getType() {
        return type;
    }
}
