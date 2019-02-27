package info.grigoriadi.yasson.jdbc;

import org.eclipse.yasson.internal.JsonbContext;
import org.eclipse.yasson.internal.JsonbParser;
import org.eclipse.yasson.internal.JsonbRiParser;
import org.eclipse.yasson.internal.Marshaller;
import org.eclipse.yasson.internal.Unmarshaller;
import org.junit.Before;
import org.junit.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.spi.JsonProvider;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;

public class CustomJsonProviderTest {

    private static final String EXPECTED_JSON = "{\"age\":4,\"name\":\"Falco, (written by TestJsonGenerator)\"}";

    private static final String EXPECTED_NAME = "Falco, (parsed with TestJsonParser)";

    @Before
    public void setUp() {
        DB.getInstance().store(null);
    }

    @Test
    public void testProvider() {

        System.out.println("Start of demo");

        Dog dog = new Dog();
        dog.name = "Falco";
        dog.age = 4;

        // Create Jsonb and serialize
        JsonbBuilder builder = JsonbBuilder.newBuilder().withProvider(new TestJsonpProvider());
        Jsonb jsonb = builder.build();

        // Don't need to create the JsonGenerator here, it is pulled inside Jsonb when needed.

        // Marshall the dog
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        jsonb.toJson(dog, baos);

        // Insert the marshalled dog
        PreparedStatement pstmt = new PreparedStatement();
        pstmt.setObject(1, baos.toByteArray());
        pstmt.executeUpdate();

        //Verify that custom generator was used:
        if (!Arrays.equals(EXPECTED_JSON.getBytes(), DB.getInstance().get())) {
            throw new RuntimeException("Custom generator not used!");
        }

        // Select the marshalled dog
        ResultSet rs = new ResultSet();
        rs.next();

        // Instead of returning parser return an InputStream, so the parser may be created by provider later.
        InputStream is = rs.getObject(1, InputStream.class);

        // Unmarshall the dog
        Dog dogUnmarshalled = jsonb.fromJson(is, Dog.class);

        // Verify that custom parser was used:
        if (!EXPECTED_NAME.equals(dogUnmarshalled.name)) {
            throw new RuntimeException("Custom parser not used");
        }

        System.out.println("End of demo");
    }

    @Test
    public void testWithoutProvider() {
        Dog dog = new Dog();
        dog.name = "Falco";
        dog.age = 4;
        JsonbConfig config = new JsonbConfig();
        //put your config here:
        config.withLocale(Locale.getDefault()); //does nothing

        // !!! ATTENTION !!! - following are imports for Yasson's ".internal" package
        JsonbContext context = new JsonbContext(config, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Marshaller marshaller = new Marshaller(context);
        marshaller.marshall(dog, new TestJsonGenerator(JsonProvider.provider().createGenerator(baos)));

        // Insert the marshalled dog
        PreparedStatement pstmt = new PreparedStatement();
        pstmt.setObject(1, baos.toByteArray());
        pstmt.executeUpdate();

        //Verify that custom generator was used:
        if (!Arrays.equals(EXPECTED_JSON.getBytes(), DB.getInstance().get())) {
            throw new RuntimeException("Custom generator not used!");
        }

        // Select the marshalled dog
        ResultSet rs = new ResultSet();
        rs.next();

        // Instead of returning parser return an InputStream, so the parser may be created by provider later.
        InputStream is = rs.getObject(1, InputStream.class);

        Unmarshaller unmarshaller = new Unmarshaller(context);
        TestJsonParser testJsonParser = new TestJsonParser(JsonProvider.provider().createParser(is));
        JsonbParser parser = new JsonbRiParser(testJsonParser);
        Dog result = unmarshaller.deserialize(Dog.class, parser);

        // Verify that custom parser was used:
        if (!EXPECTED_NAME.equals(result.name)) {
            throw new RuntimeException("Custom parser not used");
        }
    }

    public static class Dog {
        public String name;
        public Integer age;
    }
}
