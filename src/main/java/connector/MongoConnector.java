package connector;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoConnector {
    private final String HOST = "localhost";
    private final int PORT = 27017;
    private final String DBNAME = "cen-order";
    private final String USERNAME = "";
    private final String PASS = "";
    private static final String AUTH_SOURCE = "admin";
    private static final String AUTH_MECHANISM = "SCRAM-SHA-1";

    public MongoClient getConnection(Vertx vertx) {
        JsonObject config = new JsonObject();
        config.put("connection_string", "mongodb://" + HOST + ":" + PORT);
        config.put("db_name", DBNAME);
        config.put("authSource", AUTH_SOURCE);
        config.put("authMechanism", AUTH_MECHANISM);

        MongoClient mongoClient = MongoClient.createShared(vertx, config);
        return mongoClient;
    }
}
