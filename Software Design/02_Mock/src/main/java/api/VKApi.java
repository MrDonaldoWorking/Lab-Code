package api;

import com.google.gson.Gson;
import entity.JsonResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class VKApi {
    private static final String urlStr = "https://api.vk.com/method/newsfeed.search?q=%s&access_token=%s&v=%s";
    private static final String startTime = "&start_time=%d";
    private static final String endTime = "&end_time=%d";

    private static final int MILLIS = 1000;
    private static final int SEC_IN_MIN = 60;
    private static final int MIN_IN_HRS = 60;
    private static final int SEC_IN_HRS = SEC_IN_MIN * MIN_IN_HRS;

    private final String version;
    private final String token;

    public VKApi(final Properties properties) {
        this.version = properties.getProperty("vk.api.version");
        this.token = properties.getProperty("vk.api.access.token");
    }

    private String encode(final String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public JsonResponse doRequest(final URL url) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        final int code = connection.getResponseCode();
        InputStream in;
        if (200 <= code && code <= 299) {
            in = connection.getInputStream();
        } else {
            in = connection.getErrorStream();
        }

        final StringBuilder response = new StringBuilder();
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                response.append(currentLine);
            }
        }

        final String json = response.toString();
        return new Gson().fromJson(json, JsonResponse.class);
    }

    public JsonResponse doRequest(final String query, final long start, final long end) throws IOException {
        final String request = String.format(urlStr + startTime + endTime, encode(query), token, version, start, end);
        System.out.printf("Fetching %s at %d..%d%n", query, start, end);
        return doRequest(new URL(request));
    }

    public List<JsonResponse> getPosts(final String query, final int hours) throws IOException {
        if (hours > 24 || hours < 1) {
            throw new IllegalArgumentException("hours must be natural number 1..24");
        }

        final List<JsonResponse> results = new ArrayList<>();
        final long current = System.currentTimeMillis() / MILLIS;
        for (int h = 0; h < hours; ++h) {
            results.add(doRequest(query, current - (h + 1) * SEC_IN_HRS, current - h * SEC_IN_HRS));
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException ignored) {
            }
        }

        return results;
    }
}
