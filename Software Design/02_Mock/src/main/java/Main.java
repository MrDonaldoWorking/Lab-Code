import api.VKApi;
import entity.JsonResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(final String[] args) throws IOException {
        if (args == null || args.length != 2) {
            System.err.println("Usage: <hashtag without #> <last hours 1..24>");
            return;
        }

        final String hashtag = args[0];
        int hours;
        try {
            hours = Integer.parseInt(args[1]);
        } catch (final NumberFormatException e) {
            System.err.println("hours must be natural number 1..24");
            return;
        }
        if (hours < 1 || hours > 24) {
            System.err.println("hours must be in [1, 24]");
            return;
        }

        final Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            System.err.println("Couldn't find application.properties file :(");
            return;
        }

        List<JsonResponse> responses = new VKApi(properties).getPosts('#' + hashtag, hours);
        System.out.printf("Statistics for '#%s' in last %d hours:%n", hashtag, hours);
        for (int i = responses.size() - 1; i > -1; --i) {
            System.out.printf("%d ", responses.get(i).getResponse().getTotalCount());
        }
    }
}
