import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import child.LocalActor;
import com.xebialabs.restito.semantics.Action;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;
import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static org.junit.Assert.assertTrue;

import entity.Request;
import master.AggregatorActor;
import org.glassfish.grizzly.http.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class StubServerTest {
    private final int PORT = 33303;
    private StubServer server;

    @Before
    public void start() {
        server = new StubServer(PORT).run();
    }

    @After
    public void stop() {
        server.stop();
    }

    @Test
    public void simpleTest() throws InterruptedException {
        final String json = "{\n" +
                "    \"position\": 1,\n" +
                "    \"title\": \"ITMO University\",\n" +
                "    \"link\": \"https://en.itmo.ru\"" +
                "}";

        whenHttp(server)
                .match(Condition.method(Method.GET), Condition.matchesUri(Pattern.compile(".*q=.*")))
                .then(Action.stringContent(json));

        final ActorSystem system = ActorSystem.create("search");
        final AtomicReference<String> result = new AtomicReference<>();
        final Consumer<String> set = result::set;
        final ActorRef actor = system.actorOf(Props.create(ReturnActor.class, set, List.of(LocalActor.class)));
        actor.tell(new Request("query"), ActorRef.noSender());

        while (result.get() == null) {
            Thread.sleep(1000);
            System.out.println("Still waiting");
        }
        System.out.println(result);

        assertTrue(result.get().contains("en.itmo.ru"));
        assertTrue(result.get().contains("local"));
        assertTrue(result.get().startsWith("Found links"));
    }
}
