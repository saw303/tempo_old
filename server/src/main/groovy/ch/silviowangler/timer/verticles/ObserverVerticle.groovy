package ch.silviowangler.timer.verticles

import io.vertx.core.AbstractVerticle

/**
 * @author Silvio Wangler
 */
class ObserverVerticle extends AbstractVerticle {

    @Override
    void start() throws Exception {
        vertx.eventBus().consumer(EventAdresses.OBSERVER.name(), { message ->
            println "Observer ${message.body()}"
        })
    }
}
