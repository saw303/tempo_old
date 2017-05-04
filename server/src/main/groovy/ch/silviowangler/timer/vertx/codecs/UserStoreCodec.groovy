package ch.silviowangler.timer.vertx.codecs

import ch.silviowangler.timer.domain.UserStore
import ch.silviowangler.timer.gson.GsonProvider
import groovy.transform.CompileStatic
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec

/**
 * @author Silvio Wangler
 */
@CompileStatic
class UserStoreCodec implements MessageCodec<UserStore, UserStore> {

  static final String NAME = 'userstore'
  
  @Override
  void encodeToWire(Buffer buffer, UserStore userStore) {
    String json = GsonProvider.gson().toJson(userStore)
    buffer.appendString(json)
  }

  @Override
  UserStore decodeFromWire(int pos, Buffer buffer) {
    return GsonProvider.gson().fromJson(buffer.toString(), UserStore)
  }

  @Override
  UserStore transform(UserStore userStore) {
    return userStore
  }

  @Override
  String name() {
    return NAME
  }

  @Override
  byte systemCodecID() {
    return -1
  }
}
