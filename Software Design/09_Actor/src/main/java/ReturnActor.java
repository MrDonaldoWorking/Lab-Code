import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import child.AbstractActor;
import entity.Request;
import master.AggregatorActor;

import java.util.List;
import java.util.function.Consumer;

public class ReturnActor extends UntypedAbstractActor {
    private final Consumer<String> function;
    private final List<? extends AbstractActor> engines;

    public ReturnActor(final Consumer<String> function, final List<? extends AbstractActor> engines) {
        this.function = function;
        this.engines = engines;
    }

    @Override
    public void onReceive(final Object message) throws Throwable {
        if (message instanceof Request) {
            final String query = ((Request) message).getQuery();
            context().actorOf(Props.create(AggregatorActor.class, engines)).tell(query, self());
        } else {
            assert message instanceof String;
            function.accept((String) message);
        }
    }
}
