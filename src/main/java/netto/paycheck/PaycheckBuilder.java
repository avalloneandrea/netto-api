package netto.paycheck;

import netto.domain.Item;
import netto.domain.Paycheck;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.math.MathContext;

@ApplicationScoped
public class PaycheckBuilder {

    private static final BigDecimal NO_TAX_AREA = BigDecimal.valueOf(8174);

    private BigDecimal grossIncome;
    private BigDecimal additionalSalaries;
    private BigDecimal netAllowance;

    private BigDecimal socialTax;
    private BigDecimal taxableIncome;
    private BigDecimal nationalTax;
    private BigDecimal regionalTax;
    private BigDecimal localTax;
    private BigDecimal taxCredit;
    private BigDecimal taxCredit2;

    public PaycheckBuilder() {
        this.grossIncome = BigDecimal.ZERO;
        this.additionalSalaries = BigDecimal.ZERO;
        this.netAllowance = BigDecimal.ZERO;
    }

    public PaycheckBuilder setGrossIncome(double grossIncome) {
        this.grossIncome = BigDecimal.valueOf(grossIncome).max(BigDecimal.ZERO);
        return this;
    }

    public PaycheckBuilder setAdditionalSalaries(int additionalSalaries) {
        this.additionalSalaries = BigDecimal.valueOf(additionalSalaries).max(BigDecimal.ZERO);
        return this;
    }

    public PaycheckBuilder setNetAllowance(double netAllowance) {
        this.netAllowance = BigDecimal.valueOf(netAllowance).max(BigDecimal.ZERO);
        return this;
    }

    public Paycheck build() {
        this.socialTax = socialTax();
        this.taxableIncome = taxableIncome();
        this.nationalTax = nationalTax();
        this.regionalTax = regionalTax();
        this.localTax = localTax();
        this.taxCredit = taxCredit();
        this.taxCredit2 = taxCredit2();
        return paycheck();
    }

    private BigDecimal socialTax() {

        if (grossIncome.compareTo(BigDecimal.ZERO) <= 0)
            return BigDecimal.ZERO;

        else if (grossIncome.compareTo(BigDecimal.valueOf(35000)) <= 0)
            return grossIncome.multiply(BigDecimal.valueOf(0.0719));

        else // if (grossIncome.compareTo(BigDecimal.valueOf(35000)) > 0)
            return grossIncome.multiply(BigDecimal.valueOf(0.0919));

    }

    private BigDecimal taxableIncome() {
        return grossIncome.subtract(socialTax);
    }

    private BigDecimal nationalTax() {

        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;

        else if (taxableIncome.compareTo(BigDecimal.valueOf(15000)) <= 0)
            return taxableIncome.multiply(BigDecimal.valueOf(0.23));

        else if (taxableIncome.compareTo(BigDecimal.valueOf(28000)) <= 0)
            return taxableIncome
                    .subtract(BigDecimal.valueOf(15000))
                    .multiply(BigDecimal.valueOf(0.25))
                    .add(BigDecimal.valueOf(3450));

        else if (taxableIncome.compareTo(BigDecimal.valueOf(50000)) <= 0)
            return taxableIncome
                    .subtract(BigDecimal.valueOf(28000))
                    .multiply(BigDecimal.valueOf(0.35))
                    .add(BigDecimal.valueOf(6700));

        else // if (taxableIncome.compareTo(BigDecimal.valueOf(50000)) > 0)
            return taxableIncome
                    .subtract(BigDecimal.valueOf(50000))
                    .multiply(BigDecimal.valueOf(0.43))
                    .add(BigDecimal.valueOf(14400));

    }

    private BigDecimal regionalTax() {

        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;

        else // if (taxableIncome.compareTo(NO_TAX_AREA) > 0)
            return taxableIncome.multiply(BigDecimal.valueOf(0.0203));

    }

    private BigDecimal localTax() {

        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;

        else // if (taxableIncome.compareTo(NO_TAX_AREA) > 0)
            return taxableIncome.multiply(BigDecimal.valueOf(0.008));

    }

    private BigDecimal taxCredit() {

        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;

        else if (taxableIncome.compareTo(BigDecimal.valueOf(15000)) <= 0)
            return BigDecimal.valueOf(1880);

        else if (taxableIncome.compareTo(BigDecimal.valueOf(28000)) <= 0)
            return BigDecimal.valueOf(28000)
                    .subtract(taxableIncome)
                    .divide(BigDecimal.valueOf(13000), MathContext.DECIMAL128)
                    .multiply(BigDecimal.valueOf(1190))
                    .add(BigDecimal.valueOf(1910));

        else if (taxableIncome.compareTo(BigDecimal.valueOf(50000)) <= 0)
            return BigDecimal.valueOf(50000)
                    .subtract(taxableIncome)
                    .divide(BigDecimal.valueOf(22000), MathContext.DECIMAL128)
                    .multiply(BigDecimal.valueOf(1910));

        else // if (taxableIncome.compareTo(BigDecimal.valueOf(50000)) > 0)
            return BigDecimal.ZERO;

    }

    private BigDecimal taxCredit2() {

        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;

        else if (taxableIncome.compareTo(BigDecimal.valueOf(15000)) <= 0)
            return BigDecimal.valueOf(1200);

        else if (taxableIncome.compareTo(BigDecimal.valueOf(25000)) <= 0)
            return BigDecimal.ZERO;

        else if (taxableIncome.compareTo(BigDecimal.valueOf(35000)) <= 0)
            return BigDecimal.valueOf(65);

        else // if (taxableIncome.compareTo(BigDecimal.valueOf(35000)) > 0)
            return BigDecimal.ZERO;

    }

    private Paycheck paycheck() {

        BigDecimal numOfMonths = BigDecimal.valueOf(12);
        BigDecimal numOfSalaries = numOfMonths.add(additionalSalaries);
        BigDecimal netIncomeAccumulator = BigDecimal.ZERO;
        Paycheck paycheck = new Paycheck();

        if (grossIncome.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = grossIncome.divide(numOfSalaries, MathContext.DECIMAL128);
            paycheck.setGrossIncome(value);
            netIncomeAccumulator = netIncomeAccumulator.add(value);
        }

        if (socialTax.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = socialTax.divide(numOfSalaries, MathContext.DECIMAL128);
            Item item = new Item().setCode("SOCIAL_TAX").setValue(value);
            paycheck.addTaxes(item);
            netIncomeAccumulator = netIncomeAccumulator.subtract(value);
        }

        if (nationalTax.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = nationalTax.divide(numOfSalaries, MathContext.DECIMAL128);
            Item item = new Item().setCode("NATIONAL_TAX").setValue(value);
            paycheck.addTaxes(item);
            netIncomeAccumulator = netIncomeAccumulator.subtract(value);
        }

        if (regionalTax.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = regionalTax.divide(numOfMonths, MathContext.DECIMAL128);
            Item item = new Item().setCode("REGIONAL_TAX").setValue(value);
            paycheck.addTaxes(item);
            netIncomeAccumulator = netIncomeAccumulator.subtract(value);
        }

        if (localTax.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = localTax.divide(numOfMonths, MathContext.DECIMAL128);
            Item item = new Item().setCode("LOCAL_TAX").setValue(value);
            paycheck.addTaxes(item);
            netIncomeAccumulator = netIncomeAccumulator.subtract(value);
        }

        if (taxCredit.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = taxCredit.divide(numOfMonths, MathContext.DECIMAL128);
            Item item = new Item().setCode("TAX_CREDIT").setValue(value);
            paycheck.addCredits(item);
            netIncomeAccumulator = netIncomeAccumulator.add(value);
        }

        if (taxCredit2.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = taxCredit2.divide(numOfMonths, MathContext.DECIMAL128);
            Item item = new Item().setCode("TAX_CREDIT_2").setValue(value);
            paycheck.addCredits(item);
            netIncomeAccumulator = netIncomeAccumulator.add(value);
        }

        if (netAllowance.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = netAllowance;
            Item item = new Item().setCode("NET_ALLOWANCE").setValue(value);
            paycheck.addCredits(item);
            netIncomeAccumulator = netIncomeAccumulator.add(value);
        }

        paycheck.setNetIncome(netIncomeAccumulator);
        return paycheck;

    }

}
