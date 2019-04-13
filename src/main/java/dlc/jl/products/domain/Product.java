package dlc.jl.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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

    public String getNowPrice() {

        if (this.getPrice() == null || this.getPrice().getNow() == null) {
            return "";
        }

        if (price.getNow().compareTo(BigDecimal.TEN) > 0) {
            DecimalFormat df = new DecimalFormat("£###.##");
            return df.format(price.getNow());
        }
        else {
            DecimalFormat df = new DecimalFormat("£0.00");
            return df.format(price.getNow());
        }
    }
}
