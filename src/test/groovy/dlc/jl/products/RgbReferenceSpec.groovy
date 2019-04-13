package dlc.jl.products

import dlc.jl.products.resource.RgbReference
import spock.lang.Specification
import spock.lang.Unroll

class RgbReferenceSpec extends Specification {

    @Unroll
    def "RbgReference should return RGB code '#expected' for color '#color'"() {

        when: "lookupRgbCode is called"
        def result = RgbReference.lookupRgbCode(color)

        then: "the correct RGB code should be returned"
        result == expected

        where:
        color               || expected
        null                || ""
        ""                  || ""
        "nothing at all"    || ""
        "RED"               || "FF0000"
        " purple "          || "800080"
        "coral"             || "FF7F50"
    }
}
