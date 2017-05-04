package ch.silviowangler.timer.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Silvio Wangler
 */
public class OffsetDateTimeJsonDeserializer implements JsonDeserializer<OffsetDateTime> {

  final DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;

  @Override
  public OffsetDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String localdateAsString = json.getAsJsonPrimitive().getAsString();

    return OffsetDateTime.parse(localdateAsString, fmt);
  }
}
