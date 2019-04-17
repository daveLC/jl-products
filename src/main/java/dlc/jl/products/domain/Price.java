package dlc.jl.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dlc.jl.products.resource.FileProductResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    private static final Logger log = LoggerFactory.getLogger(FileProductResource.class);

    private BigDecimal was;
    private BigDecimal now;
    private BigDecimal then1;
    private BigDecimal then2;

    public Price() {}

    public Price(BigDecimal was, BigDecimal now) {
        this.was = was;
        this.now = now;
    }

    public Price(BigDecimal was, BigDecimal now, BigDecimal then1, BigDecimal then2) {
        this.was = was;
        this.now = now;
        this.then1 = then1;
        this.then2 = then2;
    }

    @JsonProperty("now")
    private void nowObjectChecker(Object now) {
        if (now instanceof String) {
            setNow(new BigDecimal((String)now));
        }
    }

    public BigDecimal getWas() {
        return was;
    }

    public void setWas(BigDecimal was) {
        this.was = was;
    }

    public BigDecimal getNow() {
        return now;
    }

    public void setNow(BigDecimal now) {
        this.now = now;
    }

    public BigDecimal getThen1() {
        return then1;
    }

    public void setThen1(BigDecimal then1) {
        this.then1 = then1;
    }

    public BigDecimal getThen2() {
        return then2;
    }

    public void setThen2(BigDecimal then2) {
        this.then2 = then2;
    }
}
