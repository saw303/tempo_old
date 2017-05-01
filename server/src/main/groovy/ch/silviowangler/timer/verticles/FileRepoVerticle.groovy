package ch.silviowangler.timer.verticles

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
/**
 * @author Silvio Wangler
 */
class FileRepoVerticle extends AbstractVerticle {

    private File source
    private static final Logger logger = LoggerFactory.getLogger(FileRepoVerticle)

    FileRepoVerticle() {
        this.source = new File(System.getProperty('tempo.source'))

        assert this.source.exists()
        assert this.source.isDirectory()
        assert this.source.canWrite()
    }

    @Override
    void start() throws Exception {
        vertx.eventBus().consumer(EventAdresses.GET_TIME_ENTRIES.name(), { message ->

            String userId = message.headers().get('userId')
            File userFile = new File(this.source, "${userId}.json")

            if (userFile.exists()) {
                logger.info("About to read from user file {}", userFile.name)

                message.reply(new JsonObject(userFile.text).toString())
            } else {
                logger.warn("User file {} does not exist", userFile.absolutePath)
                message.fail(404, "user ${userId} does not exist")
            }
        })
    }
}
