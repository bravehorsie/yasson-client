package info.grigoriadi.yasson.jdbc;

public class DB {

    private byte[] json;

    private static final DB instance = new DB();

    private DB() {

    }

    public static DB getInstance() {
        return instance;
    }

    public byte[] get() {
        return json;
    }

    public void store(byte[] json) {
        this.json = json;
    }
}
