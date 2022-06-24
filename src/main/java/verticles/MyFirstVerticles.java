package verticles;

import connector.Connection;
import connector.MongoConnector;
import entity.Actor;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import util.DBUtil;

import java.util.ArrayList;
import java.util.List;

public class MyFirstVerticles extends AbstractVerticle {
    Connection connection;
    MongoConnector mongoConnector;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        connection = new Connection();
        mongoConnector = new MongoConnector();
        MongoClient mongoClient = mongoConnector.getConnection(vertx);
        JsonObject query = new JsonObject().put("_id", "conbo");
        mongoClient.findOne("symbols", query, null).onComplete(rs -> {
           if(rs.succeeded()) {
               System.out.println("result: " + rs.result());
           } else {
               rs.cause().printStackTrace();
           }
        });

        Router router = Router.router(vertx);
        router.get("/api/list-name").handler(this::getListActorName);
        vertx.createHttpServer().requestHandler(router)
                .listen(config().getInteger("http.port", 8080), result -> {
                    if(result.succeeded())
                    {
                        startPromise.complete();
                    }
                    else
                    {
                        startPromise.fail(result.cause());
                    }
                });
    }

    private void getListActorName(RoutingContext ctx) {
        System.out.println("hello");
        List<String> nameList = new ArrayList<>();
        HttpServerResponse response = ctx.response();
        MySQLPool conn = connection.getConnection(vertx);
        conn
                .query("SELECT * FROM actor")
                .execute(result -> {
                    if(result.succeeded()) {
                        RowSet<Row> rowResult = result.result();
//                        rowResult.forEach(row -> {
//                            nameList.add(row.getString("first_name")+" "+row.getString("last_name"));
//                        });
//                        response.end(Json.encodePrettily(nameList));

                        try {
                            List<Actor> actors = DBUtil.getList(Actor.class, rowResult);
                            actors.forEach(actor -> System.out.println(actor.getFirstName()+" "+actor.getLastName()));
                            response.end(Json.encodePrettily(actors));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    } else {
                        response.setStatusCode(500).end("SQL error: "+result.cause().toString());
                    }
                    conn.close();
                });
    }
}
