package info.grigoriadi.yasson.jdbc;

import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TestJsonGenerator implements JsonGenerator {

    private JsonGenerator jsonGenerator;

    public TestJsonGenerator(JsonGenerator wrapped) {
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
        return jsonGenerator.write(value + ", (written by TestJsonGenerator)");
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
