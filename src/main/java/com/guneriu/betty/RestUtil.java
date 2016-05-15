package com.guneriu.betty;

/**
 * Created by ugur on 14.05.2016.
 */
public class RestUtil {

//    private final ObjectMapper objectMapper;
//    private final RestClient restClient;
//
//    public RestUtil(Vertx vertx) {
//        objectMapper = new ObjectMapper();
//        final List<HttpMessageConverter> httpMessageConverters = ImmutableList.of(
//                new FormHttpMessageConverter(),
//                new StringHttpMessageConverter(),
//                new JacksonJsonHttpMessageConverter(objectMapper)
//        );
//
//        final RestClientOptions restClientOptions = new RestClientOptions()
//                .setConnectTimeout(500)
//                .setGlobalRequestTimeout(300)
//                .setDefaultHost("example.com")
//                .setDefaultPort(80)
//                .setKeepAlive(true)
//                .setMaxPoolSize(500);
//
//        restClient = new DefaultRestClient(vertx, restClientOptions, httpMessageConverters);
//    }
//
//    public <T> RestClientRequest request(HttpMethod method, String uri, Class<T> responseClass, Handler<RestClientResponse<T>> responseHandler) {
//        return restClient.request(method, uri, responseClass, responseHandler);
//    }
}
