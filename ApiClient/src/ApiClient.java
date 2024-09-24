import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiClient {

    private static final String API_URL = "http://localhost:8000/api/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Välj ett alternativ:");
            System.out.println("1. Skapa användare");
            System.out.println("2. Visa alla användare");
            System.out.println("3. Radera användare");
            System.out.println("4. Uppdatera samtycke");
            System.out.println("5. Avsluta");

            int val = scanner.nextInt();
            scanner.nextLine(); // Konsumera newline

            switch (val) {
                case 1:
                    createUser();
                    break;
                case 2:
                    getUsers();
                    break;
                case 3:
                    System.out.print("Ange användar-ID att radera: ");
                    int userId = scanner.nextInt();
                    deleteUser(userId);
                    break;
                case 4:
                    System.out.print("Ange användar-ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Konsumera newline
                    System.out.print("Samtycke för cookies (true/false): ");
                    boolean consentCookies = scanner.nextBoolean();
                    System.out.print("Samtycke för dataanvändning (true/false): ");
                    boolean consentDataUsage = scanner.nextBoolean();
                    updateConsent(id, consentCookies, consentDataUsage);
                    break;
                case 5:
                    System.out.println("Avslutar programmet.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Ogiltigt val. Försök igen.");
            }
        }
    }

    // Skapa användare (POST /api/users)
    private static void createUser() {
        try {
            URL url = new URL(API_URL + "users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Skapa användare
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write("".getBytes()); // Ingen specifik data krävs för användarskapande i detta fall
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Användare skapad.");
            } else {
                System.out.println("Fel vid skapandet av användare.");
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hämta alla användare (GET /api/users)
    private static void getUsers() {
        try {
            URL url = new URL(API_URL + "users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Användare:\n" + response.toString());

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Radera användare (DELETE /api/users?id={id})
    private static void deleteUser(int userId) {
        try {
            URL url = new URL(API_URL + "users?id=" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Användare raderad.");
            } else {
                System.out.println("Fel vid radering av användare.");
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Uppdatera samtycke (POST /api/consent?userId={id}&consentCookies={true/false}&consentDataUsage={true/false})
    // Uppdatera samtycke (POST /api/consent)
    private static void updateConsent(int userId, boolean consentCookies, boolean consentDataUsage) {
        try {
            URL url = new URL(API_URL + "consent");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Skicka POST-parametrar i body istället för i URL:en
            String urlParameters = "userId=" + userId + "&consentCookies=" + consentCookies + "&consentDataUsage=" + consentDataUsage;
            OutputStream os = connection.getOutputStream();
            os.write(urlParameters.getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Samtycke uppdaterat.");
            } else {
                System.out.println("Fel vid uppdatering av samtycke.");
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
