import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Lägg till endpoints
        //Skapar en endpoint /api/users som hanteras av klassen UserHandler.
        server.createContext("/api/users", new UserHandler());

        //Skapar en endpoint /api/consent som hanteras av ConsentHandler.
        server.createContext("/api/consent", new ConsentHandler());

        //Använder standardtrådpool för att hantera inkommande HTTP-förfrågningar parallellt.
        server.setExecutor(null); // Default executor


        //Startar servern och gör den redo att ta emot inkommande förfrågningar på port 8000.
        server.start();
        System.out.println("Servern startade på port 8000...");
    }
}
