import com.ukma.mail.MainVerticle;
import io.vertx.core.Vertx;

public class MailApplication {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(MainVerticle.class.getName())
      .onFailure(Throwable::printStackTrace)
      .onSuccess(success -> System.out.println("mail-service is started"));
  }
}
