package ch.silviowangler.timer.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.OffsetDateTime;

/**
 * @author Silvio Wangler
 */
public final class GsonProvider {


  private static Gson INSTANCE = null;

  private GsonProvider() {
    // utility class
  }

  public static Gson gson() {

    if (INSTANCE != null) {
      return INSTANCE;
    }

    GsonBuilder gsonBuilder = new GsonBuilder();

    gsonBuilder.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeJsonSerializer());
    gsonBuilder.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeJsonDeserializer());

    Gson gson = gsonBuilder.create();
    INSTANCE = gson;
    return gson;
  }
}
