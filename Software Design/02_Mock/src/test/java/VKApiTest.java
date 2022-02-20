import api.VKApi;
import entity.JsonResponse;
import entity.Post;
import entity.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VKApiTest {
    private VKApi api;

    @Before
    public void prepare() throws IOException {
        api = Mockito.mock(VKApi.class);
        final List<JsonResponse> responses = new ArrayList<>();
        responses.add(new JsonResponse(new Response(
                List.of(new Post(0, 9, System.currentTimeMillis() / 1000 - 3600, "content")),
                1
        )));

        final ArgumentMatcher<Integer> matcher = val -> 1 <= val && val <= 24;
        Mockito.when(api.getPosts(Mockito.anyString(), Mockito.intThat(matcher))).thenReturn(responses);
    }

    @Test
    public void empty() throws IOException {
        assertTrue(api.getPosts("query", -1).isEmpty());
    }

    @Test
    public void shouldParse() throws IOException {
        final List<JsonResponse> responses = api.getPosts("query", 5);
        final JsonResponse response = responses.get(0);
        assertEquals(1, response.getResponse().getTotalCount());
        assertEquals(9, response.getResponse().getItems().get(0).getOwnerId());
        assertFalse(response.getResponse().getItems().get(0).getContent().isEmpty());
    }
}
