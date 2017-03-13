package ch.silviowangler.timer.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router

/**
 * @author Silvio Wangler
 */
class HttpServerVerticle extends AbstractVerticle {

    def logger = LoggerFactory.getLogger(HttpServerVerticle)


    @Override
    void start() throws Exception {

        def options = [
                port       : 8300,
                logActivity: true
        ]

        def server = vertx.createHttpServer(options)

        def router = Router.router(vertx)

        router.route().handler({ routingContext ->

            def request = routingContext.request()

            logger.info("Request: ${request.uri()}")

            request.bodyHandler({ b ->
                println "REQUEST: ${b.toString()}"
                vertx.eventBus().send(EventAdresses.GET_C_TIME_ENTIRIES.name(), "read time records", [headers: [userId: '12']], { reply ->

                    // This handler will be called for every request
                    def response = routingContext.response()
                    response.putHeader("content-type", "text/json")

                    if (reply.succeeded()) {
                        // Write to the response and end it
                        response.end(reply.result().body())
                    } else {
                        response.end("Scheisse!")
                    }
                })
            })


        })

        server.requestHandler(router.&accept).listen()
    }
}
