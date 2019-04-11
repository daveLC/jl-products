package dlc.jl.products;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {


    @GetMapping(name = "/", produces = "application/json")
    public List<Product> list() {
        return getProducts();
    }

    private List<Product> getProducts() {
        Product product = new Product("TEST-ID");
        return new ArrayList<>(Collections.singletonList(product));
    }
}
