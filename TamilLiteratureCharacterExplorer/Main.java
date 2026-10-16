import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.OutputStream;
import java.io.IOException;

public class Main {
    static byte[] bytes(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    static void respond(com.sun.net.httpserver.HttpExchange ex, int status,
                        String type, byte[] body) throws IOException {
        ex.getResponseHeaders().set("Content-Type", type);
        ex.getResponseHeaders().set("X-Content-Type-Options", "nosniff");
        ex.sendResponseHeaders(status, body.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(body); }
    }

    public static void main(String[] args) throws Exception {
        Path dataPath = Path.of("characters.json");
        if (!Files.exists(dataPath)) {
            System.out.println("ERROR: characters.json not found. Run from the project folder.");
            return;
        }
        String json = Files.readString(dataPath, StandardCharsets.UTF_8);
        String html = Files.readString(Path.of("index.html"), StandardCharsets.UTF_8);

        
int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);

        server.createContext("/api/characters", ex -> {
            if (!"GET".equals(ex.getRequestMethod())) {
                ex.getResponseHeaders().set("Allow", "GET");
                respond(ex, 405, "text/plain; charset=UTF-8", bytes("Method Not Allowed"));
                return;
            }
            respond(ex, 200, "application/json; charset=UTF-8", bytes(json));
        });
        server.createContext("/", ex -> {
            if (!"GET".equals(ex.getRequestMethod())) {
                ex.getResponseHeaders().set("Allow", "GET");
                respond(ex, 405, "text/plain; charset=UTF-8", bytes("Method Not Allowed"));
                return;
            }
            String path = ex.getRequestURI().getPath();
            if (!"/".equals(path)) {
                respond(ex, 404, "text/plain; charset=UTF-8", bytes("Not Found"));
                return;
            }
            respond(ex, 200, "text/html; charset=UTF-8", bytes(html));
        });
        server.start();
        System.out.println("Tamil Literature Character Explorer is running.");
        System.out.println("Open http://localhost:8080 in your browser.");
        System.out.println("Total records: " + json.split("\"name\"").length);
        System.out.println("Press Ctrl+C to stop.");
    }
}
