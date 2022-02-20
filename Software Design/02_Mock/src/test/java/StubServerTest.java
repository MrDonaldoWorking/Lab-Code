import api.VKApi;
import com.xebialabs.restito.semantics.Condition;
import com.xebialabs.restito.server.StubServer;
import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xebialabs.restito.semantics.Action;

import entity.JsonResponse;
import org.glassfish.grizzly.http.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class StubServerTest {
    private final VKApi api = new VKApi(new Properties());
    private final int port = 33303;
    private StubServer server;

    @Before
    public void start() {
        server = new StubServer(port).run();
    }

    @After
    public void stop() {
        server.stop();
    }

    @Test
    public void shouldParse() throws IOException {
        final String json = "{\n" +
                "    \"response\": {\n" +
                "        \"items\": [\n" +
                "            {\n" +
                "                \"id\": 217290,\n" +
                "                \"date\": 1642384860,\n" +
                "                \"owner_id\": -93248495,\n" +
                "                \"from_id\": -93248495,\n" +
                "                \"post_type\": \"post\",\n" +
                "                \"text\": \"Требования к проектированию перспективной «легкой» БМП в интересах сухопутных войск ВСУ.\\n\\nИз интересного стоит отметить, что несмотря на общее движение в сторону стандартов НАТО, в проектировании четко прослеживается «советская школа БМП». \\nТак же обратите внимание, на возможность создания БМП-К (лол). Интересна так же заявляемая возможность подключения к тактическим АСУ, а разработка и интеграция последних, как думаю многие знают сильно влетает в «копеечку» \\n\\nПриглашаем обсудить требования к перспективной БМП в комментариях)\\n\\n#БМП@ovbron #Украина@ovbron\",\n" +
                "                \"marked_as_ads\": 0,\n" +
                "                \"attachments\": [\n" +
                "                    {\n" +
                "                        \"type\": \"photo\",\n" +
                "                        \"photo\": {\n" +
                "                            \"album_id\": -7,\n" +
                "                            \"date\": 1642370472,\n" +
                "                            \"id\": 457291558,\n" +
                "                            \"owner_id\": -93248495,\n" +
                "                            \"access_key\": \"af7816ca5a46b14d4d\",\n" +
                "                            \"sizes\": [\n" +
                "                                {\n" +
                "                                    \"height\": 96,\n" +
                "                                    \"url\": \"https://sun9-8.userapi.com/impf/KioZoA247jZCbWIkDdvGQDZja2phLoi6H6gIKQ/qNwaw14KB3Q.jpg?size=130x96&quality=96&sign=d927ed9e2bd3e089a90ed899b076cd0b&c_uniq_tag=d2Zebedg1d5fKxgx1DVTDDzORnAbOjkh_JEznd-ydaw&type=album\",\n" +
                "                                    \"type\": \"m\",\n" +
                "                                    \"width\": 130\n" +
                "                                },\n" +
                "                                {\n" +
                "                                    \"height\": 96,\n" +
                "                                    \"url\": \"https://sun9-8.userapi.com/impf/KioZoA247jZCbWIkDdvGQDZja2phLoi6H6gIKQ/qNwaw14KB3Q.jpg?size=130x96&quality=96&sign=d927ed9e2bd3e089a90ed899b076cd0b&c_uniq_tag=d2Zebedg1d5fKxgx1DVTDDzORnAbOjkh_JEznd-ydaw&type=album\",\n" +
                "                                    \"type\": \"o\",\n" +
                "                                    \"width\": 130\n" +
                "                                },\n" +
                "                                {\n" +
                "                                    \"height\": 148,\n" +
                "                                    \"url\": \"https://sun9-8.userapi.com/impf/KioZoA247jZCbWIkDdvGQDZja2phLoi6H6gIKQ/qNwaw14KB3Q.jpg?size=200x147&quality=96&sign=56bb362b77acbd229cf1994b998bd382&c_uniq_tag=pGNXcerZLZIxanLDn7Rb0ahoS9IAX2QCrNmmB75ePBI&type=album\",\n" +
                "                                    \"type\": \"p\",\n" +
                "                                    \"width\": 200\n" +
                "                                },\n" +
                "                                {\n" +
                "                                    \"height\": 236,\n" +
                "                                    \"url\": \"https://sun9-8.userapi.com/impf/KioZoA247jZCbWIkDdvGQDZja2phLoi6H6gIKQ/qNwaw14KB3Q.jpg?size=320x236&quality=96&sign=9978d82ec684be2a5f5a32013c9ddf2e&c_uniq_tag=j6de0clJuOwEZgXoMlmJwkd6pHbOlciPiD-CihmykFA&type=album\",\n" +
                "                                    \"type\": \"q\",\n" +
                "                                    \"width\": 320\n" +
                "                                },\n" +
                "                                {\n" +
                "                                    \"height\": 376,\n" +
                "                                    \"url\": \"https://sun9-8.userapi.com/impf/KioZoA247jZCbWIkDdvGQDZja2phLoi6H6gIKQ/qNwaw14KB3Q.jpg?size=510x376&quality=96&sign=1dafdbd48e4da55b7bac58683b1e0e1b&c_uniq_tag=UD9OmYsQrJ0m3IAXvKn07gVQPabzlXL6sr5X_iSHG6I&type=album\",\n" +
                "                                    \"type\": \"r\",\n" +
                "                                    \"width\": 510\n" +
                "                                },\n" +
                "                                {\n" +
                "                                    \"height\": 55,\n" +
                "                                    \"url\": \"https://sun9-8.userapi.com/impf/KioZoA247jZCbWIkDdvGQDZja2phLoi6H6gIKQ/qNwaw14KB3Q.jpg?size=75x55&quality=96&sign=6fd87627c58b2cad717edf7e444625c0&c_uniq_tag=JmIKBp0NYCs5Q3XbV2z5T7SBsHkFOCzgz1PH3TVAbJc&type=album\",\n" +
                "                                    \"type\": \"s\",\n" +
                "                                    \"width\": 75\n" +
                "                                },\n" +
                "                                {\n" +
                "                                    \"height\": 445,\n" +
                "                                    \"url\": \"https://sun9-8.userapi.com/impf/KioZoA247jZCbWIkDdvGQDZja2phLoi6H6gIKQ/qNwaw14KB3Q.jpg?size=604x445&quality=96&sign=6adf7fe77f05cc90ea58ae9fd6d56b1e&c_uniq_tag=6RpKJhqzEZSTv-lZjnHZdOLrWDNEqqWxTSgOH5teQaQ&type=album\",\n" +
                "                                    \"type\": \"x\",\n" +
                "                                    \"width\": 604\n" +
                "                                },\n" +
                "                                {\n" +
                "                                    \"height\": 595,\n" +
                "                                    \"url\": \"https://sun9-8.userapi.com/impf/KioZoA247jZCbWIkDdvGQDZja2phLoi6H6gIKQ/qNwaw14KB3Q.jpg?size=807x595&quality=96&sign=e2f20f4e3a00b82dfaa024d708aeeef5&c_uniq_tag=rLDNVPRET9d02U4Ne-J5he3tf_-iLRBL7xva9UjFlXk&type=album\",\n" +
                "                                    \"type\": \"y\",\n" +
                "                                    \"width\": 807\n" +
                "                                },\n" +
                "                                {\n" +
                "                                    \"height\": 944,\n" +
                "                                    \"url\": \"https://sun9-8.userapi.com/impf/KioZoA247jZCbWIkDdvGQDZja2phLoi6H6gIKQ/qNwaw14KB3Q.jpg?size=1280x944&quality=96&sign=18761c116497eb0c0de1a8046b6e2d2f&c_uniq_tag=j33-tAStg-eWqSWagV0wPTmkKVL0LurZhwAeCa53zFs&type=album\",\n" +
                "                                    \"type\": \"z\",\n" +
                "                                    \"width\": 1280\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"text\": \"\",\n" +
                "                            \"user_id\": 100,\n" +
                "                            \"has_tags\": false\n" +
                "                        }\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"post_source\": {\n" +
                "                    \"platform\": \"iphone\",\n" +
                "                    \"type\": \"api\"\n" +
                "                },\n" +
                "                \"comments\": {\n" +
                "                    \"can_post\": 1,\n" +
                "                    \"count\": 0\n" +
                "                },\n" +
                "                \"likes\": {\n" +
                "                    \"can_like\": 1,\n" +
                "                    \"count\": 2,\n" +
                "                    \"user_likes\": 0,\n" +
                "                    \"can_publish\": 1\n" +
                "                },\n" +
                "                \"reposts\": {\n" +
                "                    \"count\": 1,\n" +
                "                    \"user_reposted\": 0\n" +
                "                },\n" +
                "                \"views\": {\n" +
                "                    \"count\": 42\n" +
                "                },\n" +
                "                \"copyright\": {\n" +
                "                    \"link\": \"https://defence-ua.com\",\n" +
                "                    \"type\": \"external_link\",\n" +
                "                    \"name\": \"defence-ua.com\"\n" +
                "                },\n" +
                "                \"is_favorite\": false,\n" +
                "                \"donut\": {\n" +
                "                    \"is_donut\": false\n" +
                "                },\n" +
                "                \"short_text_rate\": 0.8\n" +
                "            }\n" +
                "        ],\n" +
                "        \"next_from\": \"1/-93248495_217290\",\n" +
                "        \"count\": 1000,\n" +
                "        \"total_count\": 1728203\n" +
                "    }\n" +
                "}";

        whenHttp(server)
                .match(Condition.method(Method.GET), Condition.get("/posts"))
                .then(Action.stringContent(json));
        final JsonResponse response = api.doRequest(new URL("http://localhost:" + port + "/posts"));
        assertEquals(1728203, response.getResponse().getTotalCount());
        assertEquals(-93248495, response.getResponse().getItems().get(0).getOwnerId());
        assertEquals(217290, response.getResponse().getItems().get(0).getId());
    }
}
