package dlc.jl.products

import dlc.jl.products.domain.Price
import dlc.jl.products.domain.Product
import dlc.jl.products.resource.ProductResource
import spock.lang.Specification


class ProductServiceSpec extends Specification {

    def "retrieving a list of products"() {

        given: "a list of products"
        def products = [
                new Product("TEST-01", "Test One Product", new Price(new BigDecimal(40), new BigDecimal(20))),
                new Product("TEST-02", "Test Two Product", new Price(null, new BigDecimal(20))),
                new Product("TEST-03", "Test Three Product", new Price(new BigDecimal(40), new BigDecimal(10))),
                new Product("TEST-04", "Test Four Product", new Price(new BigDecimal(40), new BigDecimal(30)))
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
        !result.any { it.priceReduction <= 0 }

        and: "it should be sorted to show the highest price reduction first"
        result[0].priceReduction == new BigDecimal(30)
        result[1].priceReduction == new BigDecimal(20)
        result[2].priceReduction == new BigDecimal(10)
    }
}
