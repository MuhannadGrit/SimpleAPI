public class User {
    private int id;
    private String name;
    private String email;
    private boolean consentCookies;
    private boolean consentDataUsage;

    public User(int id, String name, String email, boolean consentCookies, boolean consentDataUsage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.consentCookies = consentCookies;
        this.consentDataUsage = consentDataUsage;
    }

    public int getId() { return id; }
    public void setConsentCookies(boolean consentCookies) { this.consentCookies = consentCookies; }
    public void setConsentDataUsage(boolean consentDataUsage) { this.consentDataUsage = consentDataUsage; }

    @Override
    public String toString() {
        return "ID: " + id + ", Namn: " + name + ", E-post: " + email + ", Samtycke Cookies: " + consentCookies + ", Samtycke Data: " + consentDataUsage;
    }
}
