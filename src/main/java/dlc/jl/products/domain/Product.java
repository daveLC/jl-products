package dlc.jl.products.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import dlc.jl.products.json.Views;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @JsonView(Views.Basic.class)
    private String productId;

    @JsonView(Views.Basic.class)
    private String title;

    @JsonView(Views.Basic.class)
    private String priceLabel;

    private Price price;

    @JsonView(Views.Basic.class)
    private List<ColorSwatch> colorSwatches;

    public BigDecimal getPriceReduction() {

        if (this.getPrice() == null || this.getPrice().getWas() == null || this.getPrice().getNow() == null) {
            return BigDecimal.ZERO;
        }

        return this.getPrice().getWas().subtract(this.getPrice().getNow());
    }

    @JsonView(Views.Basic.class)
    public String getNowPrice() {

        if (this.getPrice() == null || this.getPrice().getNow() == null) {
            return "";
        }

        return toStringPrice(this.getPrice().getNow());
    }

    public String getShowWasNow() {

        if (this.getPrice() == null || this.getPrice().getNow() == null || this.getPrice().getWas() == null) {
            return "";
        }

        return getWasString() + getNowString(", ");
    }

    public String getShowWasThenNow() {

        if (this.getPrice() == null || this.getPrice().getNow() == null || this.getPrice().getWas() == null) {
            return "";
        }

        return getWasString() + getThenString() + getNowString(", ");
    }

    public String getShowPercDscount() {

        if (this.getPrice() == null || this.getPrice().getNow() == null || this.getPrice().getWas() == null) {
            return "";
        }

        return getPercString() + getNowString(" - ");
    }

    private String getWasString() {
        return "Was " + toStringPrice(this.getPrice().getWas());
    }

    private String getNowString(String prefix) {
        return prefix + "now " + toStringPrice(this.getPrice().getNow());
    }

    private String getThenString() {
        if (this.getPrice().getThen2() != null) {
            return ", then " + toStringPrice(this.getPrice().getThen2());
        }
        else if (this.getPrice().getThen1() != null) {
            return ", then " + toStringPrice(this.getPrice().getThen1());
        }
        return "";
    }

    private String getPercString() {
        BigDecimal percent = (this.getPrice().getWas().subtract(this.getPrice().getNow()).multiply(new BigDecimal(100)))
                .divide(this.getPrice().getWas(), 0, RoundingMode.HALF_UP);
        return percent + "% off";
    }

    private String toStringPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.TEN) > 0) {
            DecimalFormat df = new DecimalFormat("£###.##");
            return df.format(price);
        }
        else {
            DecimalFormat df = new DecimalFormat("£0.00");
            return df.format(price);
        }
    }

    public void determinePriceLabel(PriceLabelType priceLabelType) {
        if (priceLabelType == PriceLabelType.ShowWasNowThen) {
            this.setPriceLabel(this.getShowWasThenNow());
        }
        else if (priceLabelType == PriceLabelType.ShowPercDscount) {
            this.setPriceLabel(this.getShowPercDscount());
        }
        else {
            this.setPriceLabel(this.getShowWasNow());
        }
    }
}
