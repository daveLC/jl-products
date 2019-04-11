package dlc.jl.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dlc.jl.products.resource.FileProductResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    private final static Logger log = LoggerFactory.getLogger(FileProductResource.class);

    private BigDecimal was;
    private BigDecimal now;

    public Price() {}

    public Price(BigDecimal was, BigDecimal now) {
        this.was = was;
        this.now = now;
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
}
