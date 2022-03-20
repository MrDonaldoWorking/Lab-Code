package master;

import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedAbstractActor;
import child.AbstractActor;
import entity.Response;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AggregatorActor extends UntypedAbstractActor {
    private final List<Class<? extends AbstractActor>> engines;
    private final List<Response> responses = new ArrayList<>();
    private final int LIMIT = 5;

    public AggregatorActor(final List<Class<? extends AbstractActor>> engines) {
        this.engines = engines;
    }

    private void print() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Found ").append("links").append(System.lineSeparator());
        for (final Response response : responses) {
            for (int i = 0; i < Math.min(LIMIT, response.getUrls().size()); ++i) {
                builder.append(response.getEngine()).append(": ").append(response.getUrls().get(i));
                builder.append(System.lineSeparator());
            }
        }
        context().parent().tell(builder.toString(), self());
//        System.out.println(builder);
        context().stop(self());
        context().system().terminate();
    }

    @Override
    public void onReceive(final Object message) throws Throwable {
        if (message instanceof ReceiveTimeout) {
            print();
        } else if (message instanceof Response) {
            responses.add((Response) message);
            context().stop(context().sender());

            if (responses.size() >= engines.size()) {
                print();
            }
        } else {
            assert message instanceof String;

            for (final Class<? extends AbstractActor> engine : engines) {
                context().actorOf(Props.create(engine)).tell(message, self());
            }
            context().setReceiveTimeout(Duration.create(5, TimeUnit.MINUTES));
        }
    }
}
