package yuliya;

import java.math.BigDecimal;

public class AreaChecker {
    public static boolean hit(BigDecimal x, BigDecimal y, BigDecimal r) {
        BigDecimal zero = BigDecimal.ZERO;
        boolean isHit = false;

        if (x.compareTo(zero) <= 0 && y.compareTo(zero) >= 0) {
            BigDecimal rHalf = r.divide(new BigDecimal("2"));
            if (x.compareTo(r.negate()) >= 0 && y.compareTo(rHalf) <= 0) {
                isHit = true;
            }
        }

// I четверть: треугольник (x > 0, y > 0)
        if (x.compareTo(zero) >= 0 && y.compareTo(zero) >= 0) {
            BigDecimal border = r.divide(new BigDecimal("2")).subtract(x.divide(new BigDecimal("2")));
            if (y.compareTo(border) <= 0) {
                isHit = true;
            }
        }

// IV четверть: сектор круга (x > 0, y < 0)
        if (x.compareTo(zero) >= 0 && y.compareTo(zero) <= 0) {
            BigDecimal radius = r.divide(new BigDecimal("2"));
            BigDecimal sum = x.multiply(x).add(y.multiply(y));
            if (sum.compareTo(radius.multiply(radius)) <= 0) {
                isHit = true;
            }
        }

        return isHit;
    }

}

