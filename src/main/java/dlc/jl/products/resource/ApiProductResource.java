package dlc.jl.products.resource;

import dlc.jl.products.domain.Product;
import dlc.jl.products.json.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
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

    private String urlTemplate;
    private RestTemplate restTemplate;

    @Autowired
    public ApiProductResource(@Value("${products.resource.url}") String urlTemplate, RestTemplate restTemplate) {
        this.urlTemplate = urlTemplate;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> getProducts(String categoryId) {
        log.debug("Loading products for category {}...", categoryId);
        String url = urlTemplate.replace("CATEGORY_ID", categoryId);

        log.debug("Loading products from {}...", url);

        ProductResponse productResponse = new ProductResponse();
        try {
            productResponse = restTemplate.getForObject(url, ProductResponse.class);
        }
        catch (RestClientException rce) {
            log.error("Failed to retrieve products from {}", url);
        }

        return Optional.ofNullable(Objects.requireNonNull(productResponse).getProducts()).orElse(Collections.emptyList());
    }
}
