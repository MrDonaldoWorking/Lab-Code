package entity;

public class JsonResponse {
    private final Response response;

    public JsonResponse(final Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
