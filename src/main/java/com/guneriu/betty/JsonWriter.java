package com.guneriu.betty;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.core.streams.WriteStream;
import org.slf4j.LoggerFactory;

/**
 * Created by ugur on 14.05.2016.
 */
public class JsonWriter<T> implements WriteStream<T> {

    private ObjectMapper mapper = new ObjectMapper();
    private org.slf4j.Logger logger = LoggerFactory.getLogger(JsonWriter.class);

    @Override
    public WriteStream<T> exceptionHandler(Handler<Throwable> handler) {
       logger.info("exception occured when writing to json");
        return this;
    }

    @Override
    public WriteStream<T> write(T data) {
        return null;
    }

    @Override
    public void end() {

    }

    @Override
    public void end(T t) {

    }

    @Override
    public WriteStream<T> setWriteQueueMaxSize(int maxSize) {
        return null;
    }

    @Override
    public boolean writeQueueFull() {
        return false;
    }

    @Override
    public WriteStream<T> drainHandler(Handler<Void> handler) {
        return null;
    }
}
