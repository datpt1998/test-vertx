package connector;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLAuthenticationPlugin;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;

import java.util.Base64;

public class Connection {
    private final String HOST = "localhost";
    private final int PORT = 3306;
    private final String DBNAME = "sakila";
    private final String USERNAME = "root";
    private final String PASS = "timebird@2020";
    private final int POOL_SIZE = 5;

    public MySQLPool getConnection(Vertx vertx) {
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                .setPort(PORT)
                .setHost(HOST)
                .setDatabase(DBNAME)
                .setUser(USERNAME)
                .setPassword(PASS);
//            .setAuthenticationPlugin(MySQLAuthenticationPlugin.MYSQL_CLEAR_PASSWORD);

//        System.out.println(connectOptions.getPassword());

        // Pool options
        PoolOptions poolOptions = new PoolOptions()
                .setMaxSize(POOL_SIZE);

        // Create the client pool
        return MySQLPool.pool(vertx, connectOptions, poolOptions);
    }

}
