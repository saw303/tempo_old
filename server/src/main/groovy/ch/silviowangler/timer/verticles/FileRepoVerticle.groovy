package ch.silviowangler.timer.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject

/**
 * @author Silvio Wangler
 */
class FileRepoVerticle extends AbstractVerticle {

    @Override
    void start() throws Exception {
        vertx.eventBus().consumer(EventAdresses.GET_C_TIME_ENTIRIES.name(), { message ->

            println "FILE ${message.body()} -- ${message.headers()}"

            message.reply(new JsonObject('''
            {
                "records": [
                    {
                        "from": "2017-03-13T08:00Z",
                        "to": "2017-03-13T12:15Z"
                    },
                    {
                        "from": "2017-03-13T20:00Z",
                        "to": "2017-03-13T21:00Z"
                    }
                ]
            }
''').toString())

        })
    }
}
