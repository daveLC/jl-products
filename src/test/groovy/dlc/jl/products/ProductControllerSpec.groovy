package dlc.jl.products

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@AutoConfigureMockMvc
@WebMvcTest
class ProductControllerSpec extends Specification {

    @Autowired
    private MockMvc mvc

    def "when performing a GET on the '/products' endpoint the response has status 200 and the response contains products"() {

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
}