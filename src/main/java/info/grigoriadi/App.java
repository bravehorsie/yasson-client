package info.grigoriadi;

import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ClassNotFoundException {
        System.out.println("JSONB API module:    " + Jsonb.class.getModule());

        Class yassonCls = Class.forName("org.eclipse.yasson.JsonBindingProvider");
        System.out.println("JSONB impl module:   " + yassonCls.getModule());

        System.out.println("JSONP API module:    " + Json.class.getModule());

        Class jsonpCls = Class.forName("org.glassfish.json.JsonGeneratorImpl");
        System.out.println("JSONP impl module:   " + jsonpCls.getModule());

        Jsonb jsonb = JsonbBuilder.create();

        System.out.println( "Serialized JSON:    " + jsonb.toJson(new Pojo("abc")) );
    }

    public static final class Pojo {
        private String strValue;

        public Pojo(String strValue) {
            this.strValue = strValue;
        }

        public String getStrValue() {
            return strValue;
        }

        public void setStrValue(String strValue) {
            this.strValue = strValue;
        }
    }
}
