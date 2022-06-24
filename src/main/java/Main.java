import verticles.MyFirstVerticles;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Main {
    public static void main(String[] args) {
        VertxOptions vertxOptions = new VertxOptions();
        Vertx vertx = Vertx.vertx(vertxOptions);
        vertx.deployVerticle(new MyFirstVerticles());
    }
}
