package ch.silviowangler.timer.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Silvio Wangler
 */
public class OffsetDateTimeJsonSerializer implements JsonSerializer<OffsetDateTime> {
  @Override
  public JsonElement serialize(OffsetDateTime src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.format(DateTimeFormatter.ISO_DATE_TIME));
  }
}
