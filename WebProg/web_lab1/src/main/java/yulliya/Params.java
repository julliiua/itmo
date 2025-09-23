package yulliya;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class Params {
    private final int x;
    private final float y;
    private final float r;

    public Params(String query) throws ValidationException {
        if (query == null || query.isEmpty()) {
            throw new ValidationException("Missing query string");
        }
        var params = splitQuery(query);
        validateParams(params);
        this.x = Integer.parseInt(params.get("x"));
        this.y = Float.parseFloat(params.get("y"));
        this.r = Float.parseFloat(params.get("r"));
    }

    private static Map<String, String> splitQuery(String query) {
        return Arrays.stream(query.split("&"))
                .map(pair -> pair.split("="))
                .collect(
                        Collectors.toMap(
                                pairParts -> URLDecoder.decode(pairParts[0], StandardCharsets.UTF_8),
                                pairParts -> URLDecoder.decode(pairParts[1], StandardCharsets.UTF_8),
                                (a, b) -> b,
                                HashMap::new
                        )
                );
    }

    private static void validateParams(Map<String, String> params) throws ValidationException {
        // X
        var x = params.get("x");
        if (x == null || x.isEmpty()) {
            throw new ValidationException("x is invalid");
        }
        try {
            int xx = Integer.parseInt(x);
            if (xx < -4 || xx > 4) {
                throw new ValidationException("x has forbidden value");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("x is not a number");
        }

        // Y
        var y = params.get("y");
        if (y == null || y.isEmpty()) {
            throw new ValidationException("y is invalid");
        }
        try {
            float yy = Float.parseFloat(y);
            if (yy < -5 || yy > 5) {
                throw new ValidationException("y has forbidden value");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("y is not a number");
        }

        // R
        var r = params.get("r");
        if (r == null || r.isEmpty()) {
            throw new ValidationException("r is invalid");
        }
        try {
            float rr = Float.parseFloat(r);
            float[] allowedR = {1f, 1.5f, 2f, 2.5f, 3f};
            boolean validR = false;
            for (float val : allowedR) {
                if (rr == val) {
                    validR = true;
                    break;
                }
            }
            if (!validR) {
                throw new ValidationException("r has forbidden value");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("r is not a number");
        }
    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }
}
