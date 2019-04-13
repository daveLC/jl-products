package dlc.jl.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import dlc.jl.products.json.Views;
import dlc.jl.products.resource.RgbReference;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColorSwatch {

    @JsonView(Views.Basic.class)
    private String color;

    private String basicColor;

    @JsonProperty("skuId")
    @JsonView(Views.Basic.class)
    private String skuid;

    @JsonView(Views.Basic.class)
    public String getRgbColor() {
        return RgbReference.lookupRgbCode(this.getBasicColor());
    }

}
