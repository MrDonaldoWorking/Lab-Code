package child;

import akka.actor.UntypedAbstractActor;
import entity.Response;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public abstract class AbstractActor extends UntypedAbstractActor {
    protected abstract String getQuery();

    protected abstract Response parse(final String response);

    @Override
    public void onReceive(final Object message) throws Throwable {
        if (message instanceof String) {
            final URL url = new URL(getQuery() + "&q="
                    + URLEncoder.encode((String) message, StandardCharsets.UTF_8));
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            final String response = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            context().parent().tell(parse(response), self());
        }
    }
}
