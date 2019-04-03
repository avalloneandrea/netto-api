package neat.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Paycheck {

    private BigDecimal grossIncome;
    private BigDecimal netIncome;

    public Paycheck() {
        this.grossIncome = BigDecimal.ZERO;
        this.netIncome = BigDecimal.ZERO;
    }

    public BigDecimal getGrossIncome() {
        return grossIncome.setScale(0, RoundingMode.HALF_EVEN);
    }

    public Paycheck setGrossIncome(BigDecimal grossIncome) {
        this.grossIncome = grossIncome != null ? grossIncome : BigDecimal.ZERO;
        return this;
    }

    public BigDecimal getNetIncome() {
        return netIncome.setScale(0, RoundingMode.HALF_EVEN);
    }

    public Paycheck setNetIncome(BigDecimal netIncome) {
        this.netIncome = netIncome != null ? netIncome : BigDecimal.ZERO;
        return this;
    }

}
