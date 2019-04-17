package dlc.jl.products;

import com.fasterxml.jackson.annotation.JsonView;
import dlc.jl.products.domain.PriceLabelType;
import dlc.jl.products.domain.Product;
import dlc.jl.products.json.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("catelog")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/{categoryId}/products/discount/prices", produces = "application/json")
    @JsonView(Views.Basic.class)
    public List<Product> list(@PathVariable("categoryId") String categoryId) {
        return productService.getProducts(categoryId);
    }

    @GetMapping(path = "/{categoryId}/products/discount/prices/{labelType}", produces = "application/json")
    @JsonView(Views.Basic.class)
    public List<Product> list(@PathVariable("categoryId") String categoryId, @PathVariable("labelType") String labelType) {
        PriceLabelType priceLabel = StringUtils.isEmpty(labelType) ? PriceLabelType.ShowWasNow : PriceLabelType.getByName(labelType);
        return productService.getProducts(categoryId, priceLabel);
    }

}
