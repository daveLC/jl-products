package dlc.jl.products.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dlc.jl.products.domain.Product;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {

    private List<Product> products;

    public ProductResponse() {}

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
