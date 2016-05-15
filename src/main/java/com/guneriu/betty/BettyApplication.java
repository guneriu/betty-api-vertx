package com.guneriu.betty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guneriu.betty.model.Season;
import io.netty.util.internal.OneTimeTask;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by ugur on 13.05.2016.
 */
public class BettyApplication extends AbstractVerticle {

    public static String BASE_URL = "api.football-data.org";
    public static String AUTH_TOKEN = "c79e01ed53434139b097811dfb7e154b";

    private MongoClient mongoClient;
    private static final Logger logger = LoggerFactory.getLogger(BettyApplication.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(BettyApplication.class.getCanonicalName());
    }

    @Override
    public void start() throws Exception {

        mongoClient = MongoClient.createNonShared(vertx, new JsonObject("{\"db_name\":\"betty\"}"));

        Router router = Router.router(vertx);

        router.get("/api/soccer/seasons").produces("application/json").handler(this::getSeasons);

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

    @Data
    class Customer{
        private final String name;
    }



    private void getSeasons(RoutingContext routingContext) {

        HttpClientOptions httpClientOptions = new HttpClientOptions()
                .setConnectTimeout(500)
                .setDefaultPort(80)
                .setKeepAlive(true)
                .setDefaultHost(BASE_URL)
                .setMaxPoolSize(500);

        HttpClient client = vertx.createHttpClient(httpClientOptions);

        mongoClient.find("seasons", new JsonObject(), result -> {
            if (result.failed() || result.result().isEmpty()) {
                HttpClientRequest request = client.request(HttpMethod.GET, "/v1/soccerseasons", response -> {
                    logger.info("received request with status code "  + response.statusCode());
                    logger.info("received request with status message " + response.statusMessage());

                    response.bodyHandler(buffer -> {
                        logger.info("received body " + buffer);

                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseString = buffer.toString();
                        try {
                            List<Season> seasons = objectMapper.readValue(responseString, new TypeReference<List<Season>>() {
                            });

                            routingContext.response().end(objectMapper.writeValueAsString(seasons));

                            mongoClient.getCollections(c -> {
                                if (!c.result().contains("seasons")) {
                                    mongoClient.createCollection("seasons", handler -> {
                                        if (handler.succeeded()) {
                                            logger.info("created collection");
                                        } else {
                                            logger.info("could not created collection: " + handler.cause().getMessage());
                                        }
                                    });
                                }
                            });

                            seasons.forEach(s -> {
                                try {
                                    mongoClient.insert("seasons", new JsonObject(objectMapper.writeValueAsString(s)), resultHandler -> {
                                        if (resultHandler.succeeded()) {
                                            logger.info("inserted data");
                                        } else {
                                            logger.info("could not inserted data: " + resultHandler.cause().getMessage());
                                        }
                                    });
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                });

                request.putHeader("X-AUTH_TOKEN", AUTH_TOKEN);

                request.exceptionHandler(handler -> {
                    logger.error("received error", handler.getMessage());
                    handler.printStackTrace();
                });
                request.end();
            } else {
                logger.info("return mongo value");
                routingContext.response().end(result.result().toString());
            }
        });


        logger.info("exit");

    }

}
