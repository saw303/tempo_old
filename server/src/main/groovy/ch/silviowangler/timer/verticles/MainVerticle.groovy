package ch.silviowangler.timer.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future

/**
 * @author Silvio Wangler
 */
class MainVerticle extends AbstractVerticle {

    @Override
    void start(Future<Void> startFuture) throws Exception {

        vertx.deployVerticle(new ObserverVerticle())
        vertx.deployVerticle(FileRepoVerticle.canonicalName, [worker: true])

        vertx.deployVerticle(new HttpServerVerticle(), { res ->

            if (res.succeeded()) {
                startFuture.complete()
            } else {
                startFuture.fail(res.cause())
            }
        })

    }
}
