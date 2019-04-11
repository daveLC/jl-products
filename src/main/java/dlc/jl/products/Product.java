package dlc.jl.products;

import java.math.BigDecimal;

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

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public Price getPrice() {
        return price;
    }

    public BigDecimal getPriceReduction() {

        if (this.getPrice() == null || this.getPrice().was == null || this.getPrice().now == null) {
            return BigDecimal.ZERO;
        }

        return this.getPrice().was.subtract(this.getPrice().now);
    }
}
