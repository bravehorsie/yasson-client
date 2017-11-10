# Tests Yasson deployed on JDK 9 JPMS module path

Client for Jsonb RI Yasson, which puts it natively on module path with all its dependencies (Jsonb API, Jsonp API and Jsonp RI).

Set your java runtime to 9+

```
mvn clean install
mve exec:exec
```


Output: 

```
[INFO] --- exec-maven-plugin:1.6.0:exec (default-cli) @ yasson-client ---
JSONB API module:    module java.json.bind
JSONB impl module:   module org.eclipse.yasson
JSONP API module:    module java.json
JSONP impl module:   module org.glassfish.java.json
Serialized JSON:    {"strValue":"abc"}

```