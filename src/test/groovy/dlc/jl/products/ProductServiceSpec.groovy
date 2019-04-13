package dlc.jl.products

import dlc.jl.products.domain.ColorSwatch
import dlc.jl.products.domain.Price
import dlc.jl.products.domain.PriceLabelType
import dlc.jl.products.domain.Product
import dlc.jl.products.resource.FileProductResource
import dlc.jl.products.resource.ProductResource
import spock.lang.Specification
import spock.lang.Unroll

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

    def "retrieving a list of products should contain the 'nowPrice' string"() {

        given: "a list of products"
        def products = [
                Product.builder().productId("TEST-01").title("Test One Product")
                        .price(new Price(new BigDecimal(40), new BigDecimal(20.00))).build(),
                Product.builder().productId("TEST-02").title("Test Two Product")
                        .price(new Price(new BigDecimal(40), new BigDecimal(5.00))).build(),
                Product.builder().productId("TEST-03").title("Test Free Product")
                        .price(new Price(new BigDecimal(40), BigDecimal.ZERO)).build(),
        ]

        and: "a test product resource"
        ProductResource testProductResource = { -> products }

        and: "the product service"
        ProductService productService = new ProductService(testProductResource)

        when: "retrieving them"
        def result = productService.getProducts()

        then: "the results should contain the nowPrice"
        result[0].nowPrice == "£0.00"
        result[1].nowPrice == "£5.00"
        result[2].nowPrice == "£20"
    }

    def "retrieving products, each should contain an array of colorSwatches"() {

        given: "the products are read using the FileProductResource"
        ProductResource fileProductResource = new FileProductResource("data/test-swatches.json")

        and: "the product service"
        ProductService productService = new ProductService(fileProductResource)

        when: "retrieving the products"
        def result = productService.getProducts()

        then: "each should contain a colorSwatch element"
        result.each { Product p ->
            p.getColorSwatches() != null
        }

        and: "where appropriate, the swatch should have a color and skuid"
        result[0].getColorSwatches().each { ColorSwatch cs ->
            cs.color != null
            cs.skuid != null
        }

        and: "where appropriate, the swatch should have the correct rgbColor"
        result[0].getColorSwatches()[0].rgbColor == "0000FF"
        result[0].getColorSwatches()[1].rgbColor == "FFFF00"
    }

    def "retrieving products, check price label values"() {

        given: "the products are read using the FileProductResource"
        ProductResource fileProductResource = new FileProductResource("data/test-price-label.json")

        and: "the product service"
        ProductService productService = new ProductService(fileProductResource)

        when: "retrieving the products"
        def result = productService.getProducts()

        then: "the ShowWasNow price is as expected"
        result[0].showWasNow == "Was £90, now £3.00"
        result[1].showWasNow == "Was £90, now £40"
        result[2].showWasNow == "Was £90, now £50"

        and: "the ShowWasThenNow is as expected"
        result[0].showWasThenNow == "Was £90, then £40, now £3.00"
        result[1].showWasThenNow == "Was £90, then £50, now £40"
        result[2].showWasThenNow == "Was £90, now £50"

        and: "the ShowPercDscount is as expected"
        result[0].showPercDscount == "97% off - now £3.00"
        result[1].showPercDscount == "56% off - now £40"
        result[2].showPercDscount == "44% off - now £50"
    }

    @Unroll
    def "retrieving products, check price label property contains the expected value for #priceLabel"() {

        given: "the products are read using the FileProductResource"
        ProductResource fileProductResource = new FileProductResource("data/test-price-label.json")

        and: "the product service"
        ProductService productService = new ProductService(fileProductResource)

        when: "retrieving the products"
        def result = productService.getProducts(priceLabel)

        then: "the price label is as expected"
        result[0].priceLabel == expected

        where:
        priceLabel                          || expected
        PriceLabelType.ShowWasNow           || "Was £90, now £3.00"
        PriceLabelType.ShowWasNowThen       || "Was £90, then £40, now £3.00"
        PriceLabelType.ShowPercDscount      || "97% off - now £3.00"
    }
}
