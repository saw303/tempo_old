package ch.silviowangler.timer.verticles

import ch.silviowangler.timer.TempoProperties
import io.vertx.core.AbstractVerticle
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.CookieHandler
import io.vertx.ext.web.handler.JWTAuthHandler
import io.vertx.ext.web.handler.SessionHandler
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.sstore.LocalSessionStore

import static ch.silviowangler.timer.verticles.EventAdresses.GET_TIME_ENTRIES

/**
 * @author Silvio Wangler
 */
class HttpServerVerticle extends AbstractVerticle {

    def logger = LoggerFactory.getLogger(HttpServerVerticle)


    @Override
    void start() throws Exception {

        def httpPort = vertx.getOrCreateContext().config()[TempoProperties.HTTP_PORT] as Integer

        def options = [
                port       : httpPort,
                logActivity: true
        ]

        def config = [
                keyStore: [
                        path    : "/Users/saw/development/private/timer/server/keystore.jceks",
                        type    : "jceks",
                        password: "secret"
                ]
        ]

        def server = vertx.createHttpServer(options)
        def jwt = JWTAuth.create(vertx, config)

        def router = Router.router(vertx)

        router.route().handler(CookieHandler.create())
        router.route().handler(
                SessionHandler.create(LocalSessionStore.create(vertx))
                        .setCookieHttpOnlyFlag(true)
                        .setCookieSecureFlag(true)
        )

        router.route('/app/*').handler(StaticHandler.create())

        router.route().path('/api/*').handler(JWTAuthHandler.create(jwt, '/api/newToken'))

        def apiRoute = router.route().path('/*')

        apiRoute.handler({ context ->
            context.response()
            // do not allow proxies to cache the data
                    .putHeader("Cache-Control", "no-store, no-cache")
            // prevents Internet Explorer from MIME - sniffing a
            // response away from the declared content-type
                    .putHeader("X-Content-Type-Options", "nosniff")
            // Strict HTTPS (for about ~6Months)
                    .putHeader("Strict-Transport-Security", "max-age=" + 15768000)
            // IE8+ do not allow opening of attachments in the context of this resource
                    .putHeader("X-Download-Options", "noopen")
            // enable XSS for IE
                    .putHeader("X-XSS-Protection", "1; mode=block")
            // deny frames
                    .putHeader("X-FRAME-OPTIONS", "DENY")

            context.next()
        })

        router.route().path('/api/newToken').handler({ context ->
            context.response().putHeader("Content-Type", "text/plain")
            def token = jwt.generateToken([
                    sub: "paulo"
            ], [:])

            logger.info("JWT Token is '${token}'")
            context.response().end(token)
        })

        router.get('/api/user/:userid/records').handler(this.&handleGetRecords)
        router.post('/api/user/:userid/records').handler(this.&handleNewRecord)

        server.requestHandler(router.&accept).listen()
    }

    private void handleNewRecord(RoutingContext routingContext) {
        routingContext.response().statusCode = 201
        routingContext.response().end('ok')
    }

    private void handleGetRecords(RoutingContext routingContext) {
        def request = routingContext.request()

        request.bodyHandler({ b ->

            def userid = request.getParam('userid')

            logger.info "Reading record for user {}", userid
            vertx.eventBus().send(GET_TIME_ENTRIES.name(), "read time records", [headers: [userId: userid]], { reply ->

                // This handler will be called for every request
                def response = routingContext.response()

                if (reply.succeeded()) {
                    response.putHeader("content-type", "text/json")
                    // Write to the response and end it
                    response.end(reply.result().body())
                } else {

                    logger.warn("Reply failed {}", reply.failed())
                    response.statusCode = 500
                    response.putHeader("content-type", "text/plain")

                    response.end('That did not work out well')
                }
            })
        })
    }
}
