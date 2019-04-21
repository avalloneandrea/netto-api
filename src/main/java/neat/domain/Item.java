package neat.domain;

import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class Item {

    private String code;
    private BigDecimal amount;

    public Item() {
        this.code = Strings.EMPTY;
        this.amount = BigDecimal.ZERO;
    }

    public String getCode() {
        return code;
    }

    public Item setCode(String code) {
        this.code = defaultIfNull(code, Strings.EMPTY);
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Item setAmount(BigDecimal amount) {
        this.amount = defaultIfNull(amount, BigDecimal.ZERO);
        return this;
    }

}
