package dlc.jl.products

import dlc.jl.products.domain.Price
import dlc.jl.products.domain.Product
import dlc.jl.products.json.ProductResponse
import dlc.jl.products.resource.FileProductResource
import dlc.jl.products.resource.ProductResource
import spock.lang.Specification

class ProductServiceSpec extends Specification {

    def "retrieving a list of products"() {

        given: "a list of products"
        def products = [
                Product.builder().productId("TEST-01").title("Test One Product").price(new Price(new BigDecimal(40), new BigDecimal(20))).build(),
                Product.builder().productId("TEST-02").title("Test Two Product").price(new Price(null, new BigDecimal(20))).build(),
                Product.builder().productId("TEST-03").title("Test Three Product").price(new Price(new BigDecimal(40), new BigDecimal(10))).build(),
                Product.builder().productId("TEST-04").title("Test Four Product").price(new Price(new BigDecimal(40), new BigDecimal(30))).build(),
        ]

        and: "a test product resource"
        ProductResource testProductResource = { -> products }

        and: "the product service"
        ProductService productService = new ProductService(testProductResource)

        when: "retrieving them"
        def result = productService.getProducts()

        then: "it should return a list of products"
        result.size() == 3

        and: "it should only contain those with price reductions"
        !result.any { it.getPriceReduction() <= 0 }

        and: "it should be sorted to show the highest price reduction first"
        result[0].getPriceReduction() == new BigDecimal(30)
        result[1].getPriceReduction() == new BigDecimal(20)
        result[2].getPriceReduction() == new BigDecimal(10)
    }

    def "retrieving products, each should contain an array of colorSwatches"() {

        given: "the products are read using the FileProductResource"
        ProductResource fileProductResource = new FileProductResource("data/actual-products.json")

        and: "the product service"
        ProductService productService = new ProductService(fileProductResource)

        when: "retrieving the products"
        def result = productService.getProducts()

        then: "each should contain a colorSwatch element"
        result.each { Product p ->
            p.getColorSwatches() != null
        }
    }
}
