package info.grigoriadi.yasson.jdbc;

import org.junit.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;

public class CustomJsonProviderTest {

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
        String expectedJson = "{\"age\":4,\"name\":\"Falco, (written by TestJsonGenerator)\"}";
        if (!Arrays.equals(expectedJson.getBytes(), DB.getInstance().get())) {
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
        if (!"Falco, (parsed with TestJsonParser)".equals(dogUnmarshalled.name)) {
            throw new RuntimeException("Custom parser not used");
        }

        System.out.println("End of demo");
    }

    public static class Dog {
        public String name;
        public Integer age;
    }
}
