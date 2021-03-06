package dlc.jl.products;

import dlc.jl.products.domain.PriceLabelType;
import dlc.jl.products.domain.Product;
import dlc.jl.products.resource.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductResource productResource;

    @Autowired
    public ProductService(ProductResource productResource) {
        this.productResource = productResource;
    }

    public List<Product> getProducts(String categoryId) {
        return getProducts(categoryId, PriceLabelType.ShowWasNow);
    }

    public List<Product> getProducts(String categoryId, PriceLabelType priceLabelType) {

        List<Product> products = productResource.getProducts(categoryId);

        products = products.stream()
                .filter(p->p.getPriceReduction().compareTo(BigDecimal.ZERO) > 0)
                .sorted(Comparator.comparing(Product::getPriceReduction).reversed())
                .collect(Collectors.toList());

        products.forEach(p -> p.determinePriceLabel(priceLabelType));

        return products;
    }
}
