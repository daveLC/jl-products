package dlc.jl.products;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    public List<Product> getProducts() {
        return new ArrayList<>();
    }
}
