package dlc.jl.products.resource;

import dlc.jl.products.domain.Product;

import java.util.List;

public interface ProductResource {

    List<Product> getProducts(String categoryId);
}
