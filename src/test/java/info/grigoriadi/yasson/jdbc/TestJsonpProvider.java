package info.grigoriadi.yasson.jdbc;

import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.spi.JsonProvider;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public class TestJsonpProvider extends JsonProvider {

    //cache default provider
    private JsonProvider provider;

    public TestJsonpProvider() {
        //default JSONP provider
        this.provider = JsonProvider.provider();
    }

    @Override
    public JsonParser createParser(Reader reader) {
        return null;
    }

    @Override
    public JsonParser createParser(InputStream in) {
        //defaut
        JsonParser parser = provider.createParser(in);
        //wrap with custom
        return new TestJsonParser(parser);
    }

    @Override
    public JsonParserFactory createParserFactory(Map<String, ?> config) {
        return new TestParserFactory(provider);
    }

    @Override
    public JsonGenerator createGenerator(Writer writer) {
        //default
        JsonGenerator generator = provider.createGenerator(writer);
        //wrap with custom
        return new TestJsonGenerator(generator);
    }

    @Override
    public JsonGenerator createGenerator(OutputStream out) {
        //default
        JsonGenerator generator = provider.createGenerator(out);
        //wrap with custom
        return new TestJsonGenerator(generator);
    }

    @Override
    public JsonGeneratorFactory createGeneratorFactory(Map<String, ?> config) {
        return new TestGeneratorFactory(provider);
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
