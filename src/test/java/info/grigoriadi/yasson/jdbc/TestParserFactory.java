package info.grigoriadi.yasson.jdbc;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;

public class TestParserFactory implements JsonParserFactory {

    private JsonProvider provider;

    public TestParserFactory(JsonProvider provider) {
        this.provider = provider;
    }

    @Override
    public JsonParser createParser(Reader reader) {
        return null;
    }

    @Override
    public JsonParser createParser(InputStream in) {
        return null;
    }

    @Override
    public JsonParser createParser(InputStream in, Charset charset) {
        //create default
        JsonParser parser = provider.createParser(in);
        //wrap with custom
        return new TestJsonParser(parser);
    }

    @Override
    public JsonParser createParser(JsonObject obj) {
        return null;
    }

    @Override
    public JsonParser createParser(JsonArray array) {
        return null;
    }

    @Override
    public Map<String, ?> getConfigInUse() {
        return null;
    }
}
