package yulliya;

import com.fastcgi.FCGIInterface;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {
    private static final String HTTP_RESPONSE = """
            HTTP/1.1 200 OK
            Content-Type: application/json
            Content-Length: %d
            
            %s
            """;
    private static final String HTTP_ERROR = """
            HTTP/1.1 400 Bad Request
            Content-Type: application/json
            Content-Length: %d
            
            %s
            """;

    private static final String RESULT_JSON = """
            {
                "time": "%s",
                "now": "%s",
                "result": %b
            }
            """;

    private static final String ERROR_JSON = """
            {
                "now": "%s",
                "reason": "%s"
            }
            """;

    public static void main(String[] args) {
        System.err.println("Application starting...");
        var fcgi = new FCGIInterface();
        System.err.println("FCGIInterface created");


        while (fcgi.FCGIaccept() >= 0) {
            System.err.println("Connection accepted");
            try {
                var queryParams = System.getProperties().getProperty("QUERY_STRING");
                var params = new Params(queryParams);

                var startTime = Instant.now();
                var result = calculate(params.getX(), params.getY(), params.getR());
                var endTime = Instant.now();

                var json = String.format(RESULT_JSON, ChronoUnit.NANOS.between(startTime, endTime), LocalDateTime.now(), result);
                var response = String.format(HTTP_RESPONSE, json.getBytes(StandardCharsets.UTF_8).length + 2, json);
                System.out.println(response);
            } catch (ValidationException e) {
                var json = String.format(ERROR_JSON, LocalDateTime.now(), e.getMessage());
                var response = String.format(HTTP_ERROR, json.getBytes(StandardCharsets.UTF_8).length + 2, json);
                System.out.println(response);
            }
        }
    }

    /**
     * Проверка попадания точки (x, y) в область радиуса r
     */
    private static boolean calculate(float x, float y, float r) {
        // II четверть: круг радиуса R
        if (x <= 0 && y >= 0) {
            return x * x + y * y <= r * r;
        }

        // I четверть: прямоугольный треугольник
        if (x >= 0 && y >= 0) {
            return x <= r / 2 && y <= r / 2 && y <= -x + r / 2;
        }

        // IV четверть: прямоугольник
        if (x >= 0 && y <= 0) {
            return x >= 0 && x <= r && y >= -r / 2 && y <= 0;
        }

        // III четверть не входит
        return false;
    }
}
