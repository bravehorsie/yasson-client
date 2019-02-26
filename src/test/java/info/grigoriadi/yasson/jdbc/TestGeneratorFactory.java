package info.grigoriadi.yasson.jdbc;

import org.junit.Test;

import javax.json.spi.JsonProvider;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

public class TestGeneratorFactory implements JsonGeneratorFactory {

    private JsonProvider provider;

    public TestGeneratorFactory(JsonProvider provider) {
        this.provider = provider;
    }

    @Override
    public JsonGenerator createGenerator(Writer writer) {
        //default jsonp
        JsonGenerator generator = provider.createGenerator(writer);
        //wrap with custom
        return new TestJsonGenerator(generator);
    }

    @Override
    public JsonGenerator createGenerator(OutputStream out) {
        //default jsonp
        JsonGenerator generator = provider.createGenerator(out);
        //wrap with custom
        return new TestJsonGenerator(generator);
    }

    @Override
    public JsonGenerator createGenerator(OutputStream out, Charset charset) {
        //default jsonp
        JsonGenerator generator = provider.createGenerator(out);
        //wrap with custom
        return new TestJsonGenerator(generator);
    }

    @Override
    public Map<String, ?> getConfigInUse() {
        return null;
    }
}
