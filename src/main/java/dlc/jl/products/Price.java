package dlc.jl.products;

import java.math.BigDecimal;

public class Price {

    BigDecimal was;
    BigDecimal now;

    public Price(BigDecimal was, BigDecimal now) {
        this.was = was;
        this.now = now;
    }
}
