package nathan.luka.myseries.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.io.StringWriter;

public class SerieSerializer extends JsonSerializer<Serie> {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(Serie value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, value);
        gen.writeFieldName(writer.toString());
    }
}
