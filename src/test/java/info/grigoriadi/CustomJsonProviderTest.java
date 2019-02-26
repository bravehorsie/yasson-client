package info.grigoriadi;

import org.junit.Test;

import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.spi.JsonProvider;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class CustomJsonProviderTest {

    private static final class TestJsonpProvider extends JsonProvider {

        @Override
        public JsonParser createParser(Reader reader) {
            return null;
        }

        @Override
        public JsonParser createParser(InputStream in) {
            return null;
        }

        @Override
        public JsonParserFactory createParserFactory(Map<String, ?> config) {
            return null;
        }

        @Override
        public JsonGenerator createGenerator(Writer writer) {
            JsonGenerator generator = JsonProvider.provider().createGenerator(writer);
            return new TestJsonGenerator(generator);
        }

        @Override
        public JsonGenerator createGenerator(OutputStream out) {
            return null;
        }

        @Override
        public JsonGeneratorFactory createGeneratorFactory(Map<String, ?> config) {
            return null;
        }

        @Override
        public JsonReader createReader(Reader reader) {
            return null;
        }

        @Override
        public JsonReader createReader(InputStream in) {
            return null;
        }

        @Override
        public JsonWriter createWriter(Writer writer) {
            return null;
        }

        @Override
        public JsonWriter createWriter(OutputStream out) {
            return null;
        }

        @Override
        public JsonWriterFactory createWriterFactory(Map<String, ?> config) {
            return null;
        }

        @Override
        public JsonReaderFactory createReaderFactory(Map<String, ?> config) {
            return null;
        }

        @Override
        public JsonObjectBuilder createObjectBuilder() {
            return null;
        }

        @Override
        public JsonArrayBuilder createArrayBuilder() {
            return null;
        }

        @Override
        public JsonBuilderFactory createBuilderFactory(Map<String, ?> config) {
            return null;
        }
    }

    private static final class TestJsonGenerator implements JsonGenerator {

        private JsonGenerator jsonGenerator;

        private TestJsonGenerator(JsonGenerator wrapped) {
            this.jsonGenerator = wrapped;
        }

        @Override
        public JsonGenerator writeStartObject() {
            return jsonGenerator.writeStartObject();
        }

        @Override
        public JsonGenerator writeStartObject(String name) {
            return jsonGenerator.writeStartObject(name);
        }

        @Override
        public JsonGenerator writeKey(String name) {
            System.out.println("TestJsonGenerator: writing key: " + name);
            return jsonGenerator.writeKey(name);
        }

        @Override
        public JsonGenerator writeStartArray() {
            return jsonGenerator.writeStartArray();
        }

        @Override
        public JsonGenerator writeStartArray(String name) {
            return jsonGenerator.writeStartArray(name);
        }

        @Override
        public JsonGenerator write(String name, JsonValue value) {
            return jsonGenerator.write(name, value);
        }

        @Override
        public JsonGenerator write(String name, String value) {
            return jsonGenerator.write(name, value);
        }

        @Override
        public JsonGenerator write(String name, BigInteger value) {
            return jsonGenerator.write(name, value);
        }

        @Override
        public JsonGenerator write(String name, BigDecimal value) {
            return jsonGenerator.write(name, value);
        }

        @Override
        public JsonGenerator write(String name, int value) {
            return jsonGenerator.write(name, value);
        }

        @Override
        public JsonGenerator write(String name, long value) {
            return jsonGenerator.write(name, value);
        }

        @Override
        public JsonGenerator write(String name, double value) {
            return jsonGenerator.write(name, value);
        }

        @Override
        public JsonGenerator write(String name, boolean value) {
            return jsonGenerator.write(name, value);
        }

        @Override
        public JsonGenerator writeNull(String name) {
            return jsonGenerator.writeNull(name);
        }

        @Override
        public JsonGenerator writeEnd() {
            return jsonGenerator.writeEnd();
        }

        @Override
        public JsonGenerator write(JsonValue value) {
            return jsonGenerator.write(value);
        }

        @Override
        public JsonGenerator write(String value) {
            System.out.println("TestJsonGenerator: writing string: " + value);
            return jsonGenerator.write(value + ", (Written by TestJsonGenerator)");
        }

        @Override
        public JsonGenerator write(BigDecimal value) {
            return jsonGenerator.write(value);
        }

        @Override
        public JsonGenerator write(BigInteger value) {
            return jsonGenerator.write(value);
        }

        @Override
        public JsonGenerator write(int value) {
            return jsonGenerator.write(value);
        }

        @Override
        public JsonGenerator write(long value) {
            return jsonGenerator.write(value);
        }

        @Override
        public JsonGenerator write(double value) {
            return jsonGenerator.write(value);
        }

        @Override
        public JsonGenerator write(boolean value) {
            return jsonGenerator.write(value);
        }

        @Override
        public JsonGenerator writeNull() {
            return jsonGenerator.writeNull();
        }

        @Override
        public void close() {
            jsonGenerator.close();
        }

        @Override
        public void flush() {
            jsonGenerator.flush();
        }

    }

    @Test
    public void testProvider() {
        Jsonb jsonb = JsonbBuilder.newBuilder().withProvider(new TestJsonpProvider()).build();
        Pojo pojo = new Pojo();
        pojo.strValue = "String value";
        String json = jsonb.toJson(pojo);
        System.out.println("json = " + json);
    }

    public static class Pojo {
        public String strValue;
    }
}
