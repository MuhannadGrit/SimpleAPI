import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class UserHandler implements HttpHandler {

    //används för att lagra användare med ett unikt ID (användare som key och deras ID som value).
    public static Map<Integer, User> users = new HashMap<>();
    private static int currentId = 1;

    //Den här metoden hanterar inkommande HTTP-förfrågningar, beroende på vilken HTTP-metod som används (POST, GET, DELETE).
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Skapa ett nytt användarkonto
            User newUser = new User(currentId++, "Användare", "user@example.com", false, false);
            users.put(newUser.getId(), newUser);

            String response = "Användare skapad: ID " + newUser.getId();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            //Returnerar information om alla användare som lagrats i users.
        } else if ("GET".equals(exchange.getRequestMethod())) {
            // Hämta användarinformation
            String response = users.values().toString();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            // Radera användarinformation
            int userId = Integer.parseInt(exchange.getRequestURI().getQuery().split("=")[1]);
            users.remove(userId);
            String response = "Användare med ID " + userId + " raderad.";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
