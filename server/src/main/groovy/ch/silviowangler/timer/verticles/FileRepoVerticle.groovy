package ch.silviowangler.timer.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

import static ch.silviowangler.timer.verticles.EventAdresses.CREATE_TIME_ENTRY
import static ch.silviowangler.timer.verticles.EventAdresses.GET_TIME_ENTRIES
/**
 * @author Silvio Wangler
 */
class FileRepoVerticle extends AbstractVerticle {

    private String source
    private static final Logger logger = LoggerFactory.getLogger(FileRepoVerticle)

    FileRepoVerticle() {
        this.source = System.getProperty('tempo.source')
    }

    @Override
    void start() throws Exception {
        vertx.eventBus().consumer(GET_TIME_ENTRIES.name(), { message ->

            String userId = message.headers().get('userId')
            String absPath = "${this.source}/${userId}.json" as String

            vertx.fileSystem().readFile(absPath, { result ->

                if (result.succeeded()) {
                    logger.info("About to read from user file {}", absPath)
                    def jsonObject = new JsonObject(result.result().toString())
                    message.reply(jsonObject.getJsonArray('records').toString())
                } else {
                    logger.warn("User file {} does not exist", absPath)
                    message.fail(404, "user ${userId} does not exist")
                }
            })
        })

        vertx.eventBus().consumer(CREATE_TIME_ENTRY.name(), { message ->

            String userId = message.headers().get('userId')
            String absPath = "${this.source}/${userId}.json" as String

            vertx.fileSystem().readFile(absPath, { result ->

                if (result.succeeded()) {
                    logger.info("About to read from user file {}", absPath)
                    def jsonObject = new JsonObject(result.result().toString())
                    message.reply(jsonObject.getJsonArray('records').toString())
                } else {
                    logger.warn("User file {} does not exist", absPath)
                    message.fail(404, "user ${userId} does not exist")
                }
            })
        })
    }


}
