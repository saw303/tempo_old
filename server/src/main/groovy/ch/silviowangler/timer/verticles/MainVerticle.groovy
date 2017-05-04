package ch.silviowangler.timer.verticles

import ch.silviowangler.timer.TempoProperties
import ch.silviowangler.timer.vertx.codecs.UserStoreCodec
import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Future
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

/**
 * @author Silvio Wangler
 */
class MainVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(MainVerticle)

    @Override
    void start(Future<Void> startFuture) throws Exception {

        vertx.eventBus().registerCodec(new UserStoreCodec())

        vertx.deployVerticle(FileRepoVerticle.canonicalName, [worker: true])

        def httpServerOptions = [
                'config': [(TempoProperties.HTTP_PORT): System.getProperty(TempoProperties.HTTP_PORT, "8300")]
        ]

        def options = new DeploymentOptions()

        vertx.deployVerticle(HttpServerVerticle.canonicalName, httpServerOptions)
    }
}
