package netto.domain;

import java.math.BigDecimal;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class Item {

    private String code;
    private BigDecimal value;

    public Item() {
        this.code = "";
        this.value = BigDecimal.ZERO;
    }

    public String getCode() {
        return code;
    }

    public Item setCode(String code) {
        this.code = defaultIfNull(code, "");
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Item setValue(BigDecimal value) {
        this.value = defaultIfNull(value, BigDecimal.ZERO);
        return this;
    }

}
