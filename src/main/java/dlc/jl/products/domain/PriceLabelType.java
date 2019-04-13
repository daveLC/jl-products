package dlc.jl.products.domain;

import org.springframework.util.StringUtils;
import static java.util.EnumSet.allOf;

public enum PriceLabelType {

    ShowWasNow,
    ShowWasNowThen,
    ShowPercDscount;

    public static PriceLabelType getByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return ShowWasNow;
        }
        else {
            return allOf(PriceLabelType.class).stream()
                    .filter(e -> e.toString().equals(name.toLowerCase())).findFirst()
                    .orElse(ShowWasNow);
        }
    }

}
