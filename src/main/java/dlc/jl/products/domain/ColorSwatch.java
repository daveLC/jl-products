package dlc.jl.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dlc.jl.products.resource.RgbReference;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColorSwatch {

    private String color;

    private String basicColor;
    
    @JsonProperty("skuId")
    private String skuid;

    public String getRgbColor() {
        return RgbReference.lookupRgbCode(this.getBasicColor());
    }

}
