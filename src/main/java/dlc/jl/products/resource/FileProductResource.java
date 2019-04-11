package dlc.jl.products.resource;

import dlc.jl.products.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileProductResource implements ProductResource {

    @Override
    public List<Product> getProducts() {
        return null;
    }
}
