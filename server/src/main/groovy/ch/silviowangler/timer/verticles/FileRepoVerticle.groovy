package ch.silviowangler.timer.verticles

import io.vertx.core.AbstractVerticle

/**
 * @author Silvio Wangler
 */
class FileRepoVerticle extends AbstractVerticle {

    @Override
    void start() throws Exception {
        vertx.eventBus().consumer(EventAdresses.OBSERVER.name(), { message ->
            println "file ${message.body()} -- ${message.headers()}"
        })
    }
}
