import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class ConsentHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Hämta POST-parametrarna från request body

            //För att läsa in data från request body används InputStreamReader och BufferedReader för att konvertera byte-strömmen till text
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                body.append(line);
            }

            // Parsar parametrarna från body (ex: userId=1&consentCookies=true&consentDataUsage=true)
            //När hela body har lästs, delas den upp i parametrar:
            String[] params = body.toString().split("&");
            int userId = Integer.parseInt(params[0].split("=")[1]);
            boolean consentCookies = Boolean.parseBoolean(params[1].split("=")[1]);
            boolean consentDataUsage = Boolean.parseBoolean(params[2].split("=")[1]);

            User user = UserHandler.users.get(userId);
            if (user != null) {
                user.setConsentCookies(consentCookies);
                user.setConsentDataUsage(consentDataUsage);

                String response = "Samtycke uppdaterat för användare " + userId;
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                String response = "Användare hittades inte";
                exchange.sendResponseHeaders(404, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}
