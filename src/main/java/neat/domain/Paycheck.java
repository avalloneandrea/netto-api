package neat.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Paycheck {

    private BigDecimal grossIncome;
    private BigDecimal netIncome;

    public Paycheck() {
        this(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public Paycheck(BigDecimal grossIncome, BigDecimal netIncome) {
        this.grossIncome = grossIncome;
        this.netIncome = netIncome;
    }

    public BigDecimal getGrossIncome() {
        return grossIncome.setScale(0, RoundingMode.HALF_EVEN);
    }

    public void setGrossIncome(BigDecimal grossIncome) {
        this.grossIncome = grossIncome;
    }

    public BigDecimal getNetIncome() {
        return netIncome.setScale(0, RoundingMode.HALF_EVEN);
    }

    public void setNetIncome(BigDecimal netIncome) {
        this.netIncome = netIncome;
    }

}
