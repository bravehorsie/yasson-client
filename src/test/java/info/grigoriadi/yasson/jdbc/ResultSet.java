package info.grigoriadi.yasson.jdbc;

import java.io.ByteArrayInputStream;

public class ResultSet {


    public void next() {
        //no impl
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Integer columnIndex, Class<T> tClass) {
        return (T) new ByteArrayInputStream(DB.getInstance().get());
    }
}
