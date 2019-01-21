package info.grigoriadi;

import org.junit.Assert;
import org.junit.Test;

import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReadJsonStructureToStringTest {

    @Test
    public void testCustomSerializer() {
        Dog bruno = new Dog();
        bruno.setName("Bruno");

        Dog pluto = new Dog();
        pluto.setName("Pluto");
        bruno.setFriends(new ArrayList<>());
        bruno.getFriends().add(pluto);

        AnimalContainer container = new AnimalContainer();
        MetaData metaData = new MetaData();
        metaData.setType(AnimalType.DOG);
        container.setMetaData(metaData);
        container.setAnimal(bruno);

        String json = "{\"animal\":{\"name\":\"Bruno\",\"friends\":[{\"name\":\"Pluto\"}]},\"metaData\":{\"type\":\"DOG\"}}";
        Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withDeserializers(new AnimalContainerDeserializer()));

        AnimalContainer result = jsonb.fromJson(json, AnimalContainer.class);
        Assert.assertTrue(result.getAnimal() instanceof Dog);
        Assert.assertEquals("Bruno", result.getAnimal().getName());
    }

    public static class AnimalContainerDeserializer implements JsonbDeserializer<AnimalContainer> {

        //Cache jsonb instance to avoid parsing Dog.class with reflection every time.
        Jsonb jsonb = JsonbBuilder.create();

        @Override
        public AnimalContainer deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
            String animalJson = null;
            AnimalContainer container = new AnimalContainer();

            while (parser.hasNext()) {
                JsonParser.Event next = parser.next();
                switch (next) {
                    case KEY_NAME:
                        String field = parser.getString();
                        switch (field) {
                            case "metaData":
                                container.setMetaData(ctx.deserialize(MetaData.class, parser));
                                break;
                            case "animal":
                                animalJson = readJsonStructure(parser);
                                break;
                            default:
                                // ignore
                                break;
                        }
                        break;
                    case END_OBJECT:
                        if (container.getMetaData().getType() == AnimalType.DOG) {
                            container.setAnimal(jsonb.fromJson(animalJson, Dog.class));
                            return container;
                        }
                }

            }
            throw new RuntimeException("Parse error");
        }

        private String readJsonStructure(JsonParser parser) {
            StringWriter writer = new StringWriter();
            JsonGenerator generator = Json.createGenerator(writer);
            int level = 0;
            while (parser.hasNext()) {
                JsonParser.Event next = parser.next();
                switch (next) {
                    case START_ARRAY:
                        level++;
                        generator.writeStartArray();
                        break;
                    case START_OBJECT:
                        level++;
                        generator.writeStartObject();
                        break;
                    case END_ARRAY:
                    case END_OBJECT:
                        generator.writeEnd();
                        level--;
                        if (level == 0) {
                            generator.close();
                            return writer.toString();
                        }
                        break;
                    case KEY_NAME:
                        generator.writeKey(parser.getString());
                        break;
                    case VALUE_NULL:
                        generator.writeNull();
                        break;
                    case VALUE_TRUE:
                        generator.write(true);
                        break;
                    case VALUE_FALSE:
                        generator.write(false);
                        break;
                    case VALUE_NUMBER:
                        generator.write(parser.getBigDecimal());
                        break;
                    case VALUE_STRING:
                        generator.write(parser.getString());
                        break;
                }
            }
            throw new RuntimeException("JsonStructure not terminated");
        }
    }

    public static class AnimalContainer {
        private Animal animal;
        private MetaData metaData;

        public Animal getAnimal() {
            return animal;
        }

        public void setAnimal(Animal animal) {
            this.animal = animal;
        }

        public MetaData getMetaData() {
            return metaData;
        }

        public void setMetaData(MetaData metaData) {
            this.metaData = metaData;
        }
    }

    public static class MetaData {
        private AnimalType type;

        public AnimalType getType() {
            return type;
        }

        public void setType(AnimalType type) {
            this.type = type;
        }
    }

    public static abstract class Animal {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Dog extends Animal {
        private List<Dog> friends;

        public List<Dog> getFriends() {
            return friends;
        }

        public void setFriends(List<Dog> friends) {
            this.friends = friends;
        }
    }

    public enum AnimalType {
        DOG;
    }


}
