package dlc.jl.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private String productId;
    private String title;
    private Price price;
    private List<ColorSwatch> colorSwatches;

    public BigDecimal getPriceReduction() {

        if (this.getPrice() == null || this.getPrice().getWas() == null || this.getPrice().getNow() == null) {
            return BigDecimal.ZERO;
        }

        return this.getPrice().getWas().subtract(this.getPrice().getNow());
    }

}
