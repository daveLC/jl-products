package dlc.jl.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private String productId;
    private String title;
    private Price price;

    public Product() {
        
    }

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

}
