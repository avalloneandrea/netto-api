package netto.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class Paycheck {

    private BigDecimal grossIncome;
    private List<Item> taxes;
    private List<Item> credits;
    private BigDecimal netIncome;

    public Paycheck() {
        this.grossIncome = BigDecimal.ZERO;
        this.taxes = new ArrayList<>();
        this.credits = new ArrayList<>();
        this.netIncome = BigDecimal.ZERO;
    }

    public BigDecimal getGrossIncome() {
        return grossIncome;
    }

    public Paycheck setGrossIncome(BigDecimal grossIncome) {
        this.grossIncome = defaultIfNull(grossIncome, BigDecimal.ZERO);
        return this;
    }

    public List<Item> getTaxes() {
        return taxes;
    }

    public Paycheck setTaxes(List<Item> taxes) {
        this.taxes = defaultIfNull(taxes, new ArrayList<>());
        return this;
    }

    public Paycheck addTaxes(Item... taxes) {
        this.taxes.addAll(Arrays.asList(taxes));
        return this;
    }

    public List<Item> getCredits() {
        return credits;
    }

    public Paycheck setCredits(List<Item> credits) {
        this.credits = defaultIfNull(credits, new ArrayList<>());
        return this;
    }

    public Paycheck addCredits(Item... credits) {
        this.credits.addAll(Arrays.asList(credits));
        return this;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public Paycheck setNetIncome(BigDecimal netIncome) {
        this.netIncome = defaultIfNull(netIncome, BigDecimal.ZERO);
        return this;
    }

}
