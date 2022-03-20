import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import child.GoogleActor;
import entity.Request;
import master.AggregatorActor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {
    public static void main(final String[] args) throws InterruptedException {
        final String query;
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            query = reader.readLine();
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }

        final ActorSystem system = ActorSystem.create("search");
        final AtomicReference<String> result = new AtomicReference<>();
        final Consumer<String> set = result::set;
        final ActorRef actor = system.actorOf(Props.create(ReturnActor.class, set, List.of(GoogleActor.class)));
        actor.tell(new Request(query), ActorRef.noSender());
        // get here result from AggregatorActor through listening on onReceive method
        while (result.get() == null) {
            Thread.sleep(1000);
            System.out.println("Still waiting");
        }
        System.out.println(result);
    }
}
