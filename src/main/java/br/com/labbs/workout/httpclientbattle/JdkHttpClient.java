package br.com.labbs.workout.httpclientbattle;

import br.com.labbs.workout.httpclientbattle.shared.Env;
import br.com.labbs.workout.httpclientbattle.shared.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.net.URI;
import java.net.URISyntaxException;

@SuppressWarnings("unused")
public class JdkHttpClient implements HttpClient<HttpRequest.Builder, HttpResponse> {

    public static final String JDK_HTTP_CLIENT = "JDK_HTTP_CLIENT";
    private static final jdk.incubator.http.HttpClient httpClient = jdk.incubator.http.HttpClient.newBuilder()
            .build();

    private URI serverUri;

    {
        try {
            serverUri = new URI(Env.URL_SERVER.get());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getClientName() {
        return JDK_HTTP_CLIENT;
    }

    @Override
    public HttpRequest.Builder newRequest(String s) {
        return HttpRequest.newBuilder()
                .uri(serverUri)
                .version(jdk.incubator.http.HttpClient.Version.HTTP_1_1)
                .GET();
    }

    @Override
    public void addHeaderToRequest(HttpRequest.Builder builder, String key, String value) {
        builder.header(key, value);
    }

    @Override
    public HttpResponse execRequest(HttpRequest.Builder builder, int request_number) throws Exception {
        return httpClient.send(builder.build(), HttpResponse.BodyHandler.asString());
    }

    @Override
    public int getResponseStatusCode(HttpResponse httpResponse) {
        return httpResponse.statusCode();
    }
}
