package neat.domain;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Paycheck {

    private BigDecimal grossIncome;
    private Map<String, BigDecimal> taxes;
    private Map<String, BigDecimal> credits;
    private BigDecimal netIncome;

    public Paycheck() {
        this.grossIncome = BigDecimal.ZERO;
        this.taxes = new LinkedHashMap<>();
        this.credits = new LinkedHashMap<>();
        this.netIncome = BigDecimal.ZERO;
    }

    public BigDecimal getGrossIncome() {
        return grossIncome;
    }

    public Paycheck setGrossIncome(BigDecimal grossIncome) {
        this.grossIncome = defaultIfNull(grossIncome, BigDecimal.ZERO);
        return this;
    }

    public Map<String, BigDecimal> getTaxes() {
        return taxes;
    }

    public Paycheck setTaxes(Map<String, BigDecimal> taxes) {
        this.taxes = defaultIfNull(taxes, new LinkedHashMap<>());
        return this;
    }

    public Paycheck setTax(String key, BigDecimal value) {
        this.taxes.put(defaultIfNull(key, ""), defaultIfNull(value, BigDecimal.ZERO));
        return this;
    }

    public Map<String, BigDecimal> getCredits() {
        return credits;
    }

    public Paycheck setCredits(Map<String, BigDecimal> credits) {
        this.credits = defaultIfNull(credits, new LinkedHashMap<>());
        return this;
    }

    public Paycheck setCredit(String key, BigDecimal value) {
        this.credits.put(defaultIfNull(key, ""), defaultIfNull(value, BigDecimal.ZERO));
        return this;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public Paycheck setNetIncome(BigDecimal netIncome) {
        this.netIncome = defaultIfNull(netIncome, BigDecimal.ZERO);
        return this;
    }

    private <T> T defaultIfNull(T object, T defaultValue) {
        return object != null ? object : defaultValue;
    }

}
