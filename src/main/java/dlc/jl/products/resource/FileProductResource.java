package dlc.jl.products.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dlc.jl.products.domain.Product;
import dlc.jl.products.json.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FileProductResource implements ProductResource {

    private final static Logger log = LoggerFactory.getLogger(FileProductResource.class);

    private String filename;
    private ProductResponse productResponse = new ProductResponse();

    @Autowired
    public FileProductResource(@Value("${products.resource.filename}") String filename) {
        this.filename = filename;
    }

    @Override
    public List<Product> getProducts() {
        if (productResponse.getProducts() == null) {
            loadProducts();
        }
        return Optional.ofNullable(productResponse.getProducts()).orElse(Collections.emptyList());
    }

    private void loadProducts() {

        try {
            File jsonFile = new ClassPathResource(filename).getFile();
            ObjectMapper jsonMapper = new ObjectMapper();
            this.productResponse = jsonMapper.readValue(jsonFile, new TypeReference<ProductResponse>() {});
        }
        catch (IOException ioException) {
            log.warn("Unable to load products from {} - {}", filename, ioException.getMessage());
        }
        catch (ClassCastException classCastException) {
            log.warn("Unable to load products - {}", filename, classCastException.getMessage());
        }
    }
}
