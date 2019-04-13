package dlc.jl.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dlc.jl.products.resource.FileProductResource;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
public class Price {

    private final static Logger log = LoggerFactory.getLogger(FileProductResource.class);

    private BigDecimal was;
    private BigDecimal now;

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
}
