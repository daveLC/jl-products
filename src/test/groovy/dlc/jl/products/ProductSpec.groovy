package dlc.jl.products

import dlc.jl.products.domain.Price
import dlc.jl.products.domain.Product
import spock.lang.Specification
import spock.lang.Unroll

class ProductSpec extends Specification {

    @Unroll
    def "Product should return valid price reduction for '#title'"() {

        when: "a product is created"
        Price price = nullPrice ? null : new Price(was, now)
        Product product = Product.builder().title(title).price(price).build()

        then: "it should have the expected pricing"
        product.getPriceReduction() == expected

        where:
        title           | nullPrice | was               | now               || expected
        "null price"    | true      | null              | null              || BigDecimal.ZERO
        "null was+now"  | false     | null              | null              || BigDecimal.ZERO
        "null was"      | false     | null              | BigDecimal.TEN    || BigDecimal.ZERO
        "null now"      | false     | BigDecimal.TEN    | null              || BigDecimal.ZERO
    }


    @Unroll
    def "Product should return valid now price for '#title'"() {

        when: "a product is created"
        Price price = nullPrice ? null : new Price(was, now)
        Product product = Product.builder().title(title).price(price).build()

        then: "it should have the expected pricing"
        product.getNowPrice() == expected

        where:
        title           | nullPrice | was               | now                           || expected
        "null price"    | true      | null              | null                          || ""
        "null was+now"  | false     | null              | null                          || ""
        "null now"      | false     | BigDecimal.TEN    | null                          || ""
        "£10"           | false     | null              | BigDecimal.TEN                || "£10"
        "£1.78"         | false     | null              | new BigDecimal(1.78)          || "£1.78"
    }


    @Unroll
    def "Product should return valid show-was-now price for '#title'"() {

        when: "a product is created"
        Price price = nullPrice ? null : new Price(was, now)
        Product product = Product.builder().title(title).price(price).build()

        then: "it should have the expected pricing"
        product.getShowWasNow() == expected

        where:
        title           | nullPrice | was                | now                           || expected
        "null price"    | true      | null               | null                          || ""
        "null was+now"  | false     | null               | null                          || ""
        "null now"      | false     | BigDecimal.TEN     | null                          || ""
        "null was"      | false     | null               | BigDecimal.TEN                || ""
        "10 > 1.5"      | false     | new BigDecimal(10) | new BigDecimal(1.5)           || "Was £10, now £1.50"
    }

    @Unroll
    def "Product should return valid show-was-then-now price for '#title'"() {

        when: "a product is created"
        Price price = nullPrice ? null : new Price(was, now, then1, then2)
        Product product = Product.builder().title(title).price(price).build()

        then: "it should have the expected pricing"
        product.getShowWasThenNow() == expected

        where:
        title           | nullPrice | was                | now                   | then1              | then2             || expected
        "null price"    | true      | null               | null                  | null               | null              || ""
        "null was+now"  | false     | null               | null                  | null               | null              || ""
        "null now"      | false     | BigDecimal.TEN     | null                  | null               | null              || ""
        "null was"      | false     | null               | BigDecimal.TEN        | null               | null              || ""
        "no then"       | false     | new BigDecimal(10) | new BigDecimal(1.5)   | null               | null              || "Was £10, now £1.50"
        "no then1"      | false     | new BigDecimal(10) | new BigDecimal(1.5)   | null               | new BigDecimal(5) || "Was £10, then £5.00, now £1.50"
        "no then2"      | false     | new BigDecimal(10) | new BigDecimal(1.5)   | new BigDecimal(5)  | null              || "Was £10, then £5.00, now £1.50"
        "10 > 5 > 1.5"  | false     | new BigDecimal(10) | new BigDecimal(1.5)   | new BigDecimal(7)  | new BigDecimal(5) || "Was £10, then £5.00, now £1.50"
    }


    @Unroll
    def "Product should return valid percent price for '#title'"() {

        when: "a product is created"
        Price price = nullPrice ? null : new Price(was, now)
        Product product = Product.builder().title(title).price(price).build()

        then: "it should have the expected pricing"
        product.getShowPercDscount() == expected

        where:
        title           | nullPrice | was                | now                           || expected
        "null price"    | true      | null               | null                          || ""
        "null was+now"  | false     | null               | null                          || ""
        "null now"      | false     | BigDecimal.TEN     | null                          || ""
        "null was"      | false     | null               | BigDecimal.TEN                || ""
        "10 > 5"        | false     | new BigDecimal(10) | new BigDecimal(5)             || "50% off - now £5.00"
    }
}
