package dlc.jl.products;

import com.fasterxml.jackson.annotation.JsonView;
import dlc.jl.products.domain.PriceLabelType;
import dlc.jl.products.domain.Product;
import dlc.jl.products.json.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(name = "/", produces = "application/json")
    @JsonView(Views.Basic.class)
    public List<Product> list(@RequestParam(value = "labelType", defaultValue = "ShowWasNow") String labelType) {
        return productService.getProducts(PriceLabelType.getByName(labelType));
    }

}
