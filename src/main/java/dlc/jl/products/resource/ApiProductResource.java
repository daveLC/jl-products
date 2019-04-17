package dlc.jl.products.resource;

import dlc.jl.products.domain.Product;
import dlc.jl.products.json.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@ConditionalOnProperty(
        name = "feature.toggles.productsFromApi",
        havingValue = "true",
        matchIfMissing = true
)
public class ApiProductResource implements ProductResource {

    private final static Logger log = LoggerFactory.getLogger(ApiProductResource.class);

    private String url;
    private RestTemplate restTemplate;

    @Autowired
    public ApiProductResource(@Value("${products.resource.url}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> getProducts() {

        log.debug("Loading products from {}...", url);

        ProductResponse productResponse = restTemplate.getForObject(url, ProductResponse.class);

        return Optional.ofNullable(Objects.requireNonNull(productResponse).getProducts()).orElse(Collections.emptyList());
    }
}
