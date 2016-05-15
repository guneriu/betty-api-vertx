package com.guneriu.betty.service;

import com.guneriu.betty.model.Season;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import lombok.Data;

import java.util.List;

/**
 * Created by ugur on 14.05.2016.
 */
public class FootballService {

    private Vertx vertx;
    private MongoClient mongoClient;
    private HttpClient httpClient;
    private static String BASE_URL = "api.football-data.org";
    private static String AUTH_TOKEN = "c79e01ed53434139b097811dfb7e154b";

    public FootballService(Vertx vertx) {
        this.vertx = vertx;
        this.mongoClient = MongoClient.createNonShared(vertx, new JsonObject("{\"db_name\":\"betty\"}"));
        HttpClientOptions httpClientOptions = new HttpClientOptions()
                .setConnectTimeout(500)
                .setDefaultPort(80)
                .setKeepAlive(true)
                .setDefaultHost(BASE_URL)
                .setMaxPoolSize(500);

        httpClient = vertx.createHttpClient(httpClientOptions);
    }

    public List<Season> getSeasons() {

    }

    private <T> List<T> getOrCreateCollection(String collectionName, T clazz) {
        mongoClient.getCollections(resultHandler -> {
            if (resultHandler.succeeded() && !resultHandler.result().isEmpty()) {
                return resultHandler.result();
            }
        });

        return exist;
    }


}
