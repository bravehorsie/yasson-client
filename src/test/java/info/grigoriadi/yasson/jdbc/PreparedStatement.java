package info.grigoriadi.yasson.jdbc;

public class PreparedStatement {

    public void setObject(int parameterIndex, Object x) {
        if (!(x instanceof byte[])) {
            throw new UnsupportedOperationException("Not supported");
        }
        DB.getInstance().store((byte[]) x);
    }

    public void executeUpdate() {
        //no impl
    }
}
