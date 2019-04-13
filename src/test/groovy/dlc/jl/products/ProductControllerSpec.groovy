package dlc.jl.products

import dlc.jl.products.domain.Price
import dlc.jl.products.domain.PriceLabelType
import dlc.jl.products.domain.Product
import groovy.json.JsonSlurper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@WebMvcTest
@AutoConfigureMockMvc
class ProductControllerSpec extends Specification {

    @Autowired
    private MockMvc mvc

    @SpringBean
    ProductService productService = Mock()

    def "when performing a GET on the '/products' endpoint the response has status 200 and the response contains products"() {

        given: "the product service returns a list of Products"
        productService.getProducts(_) >> {
            Product product = Product.builder().productId("TEST-ID").title("Test Product").build();
            [product] as List<Product>
        }

        when: "performing a GET on the '/products' endpoint"
        def response = mvc.perform(get('/products').contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().response
        def json = new JsonSlurper().parseText(response.contentAsString)

        then: "the response has status 200"
        response.status == HttpStatus.OK.value()

        and: "the response contains products"
        json.size() > 0
        json.each { product ->
            product instanceof Product
        }
    }

    def "when calling the '/products' endpoint with the labelType=ShowWasNow, the ShowWasNow property should exist"() {

        given: "the product service returns a list of Products"
        productService.getProducts(PriceLabelType.ShowWasNow) >> {
            Product product = Product.builder()
                    .productId("TEST-ID")
                    .title("Test Product")
                    .price(new Price(new BigDecimal(20), new BigDecimal(10)))
                    .priceLabel("Was £20, now £10")
                    .build();
            [product] as List<Product>
        }

        when: "performing a GET on the '/products' endpoint"
        def response = mvc.perform(get('/products').contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn().response
        def json = new JsonSlurper().parseText(response.contentAsString)

        then: "the response has status 200"
        json[0].priceLabel == "Was £20, now £10"
    }

}