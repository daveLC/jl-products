package dlc.jl.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Product {

    private String productId;
    private String title;
    private Price price;

    public Product(String productId, String title) {
        this.productId = productId;
        this.title = title;
    }

    public Product(String productId, String title, Price price) {
        this.productId = productId;
        this.title = title;
        this.price = price;
    }

    public BigDecimal getPriceReduction() {

        if (this.getPrice() == null || this.getPrice().getWas() == null || this.getPrice().getNow() == null) {
            return BigDecimal.ZERO;
        }

        return this.getPrice().getWas().subtract(this.getPrice().getNow());
    }

}
