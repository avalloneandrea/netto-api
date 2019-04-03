package neat.it;

import neat.domain.Paycheck;

import java.math.BigDecimal;
import java.math.MathContext;

public class PaycheckBuilder {

    private static final BigDecimal NO_TAX_AREA = new BigDecimal(8174);

    private BigDecimal additionalSalaries;
    private BigDecimal grossIncome;
    private BigDecimal netBonus;

    private BigDecimal socialTax;
    private BigDecimal taxableIncome;
    private BigDecimal stateTax;
    private BigDecimal federalTax;
    private BigDecimal localTax;
    private BigDecimal salaryCredit1;
    private BigDecimal salaryCredit2;
    private BigDecimal numOfSalaries;
    private BigDecimal netIncome;

    public PaycheckBuilder() {
        this.additionalSalaries = BigDecimal.ZERO;
        this.grossIncome = BigDecimal.ZERO;
        this.netBonus = BigDecimal.ZERO;
    }

    public PaycheckBuilder setAdditionalSalaries(int additionalSalaries) {
        this.additionalSalaries = new BigDecimal(Math.max(additionalSalaries, 0));
        return this;
    }

    public PaycheckBuilder setGrossIncome(BigDecimal grossIncome) {
        this.grossIncome = valueOrZero(grossIncome);
        return this;
    }

    public PaycheckBuilder setNetBonus(BigDecimal netBonus) {
        this.netBonus = valueOrZero(netBonus);
        return this;
    }

    public Paycheck build() {

        this.socialTax = getSocialTax();
        this.taxableIncome = getTaxableIncome();
        this.stateTax = getStateTax();
        this.federalTax = getFederalTax();
        this.localTax = getLocalTax();
        this.salaryCredit1 = getSalaryCredit1();
        this.salaryCredit2 = getSalaryCredit2();
        this.numOfSalaries = getNumOfSalaries();
        this.netIncome = getNetIncome();

        Paycheck paycheck = new Paycheck();
        paycheck.setGrossIncome(grossIncome);
        paycheck.setNetIncome(netIncome);
        return paycheck;

    }

    private BigDecimal getSocialTax() {
        if (grossIncome.compareTo(BigDecimal.ZERO) <= 0)
            return BigDecimal.ZERO;
        else // if (grossIncome.compareTo(BigDecimal.ZERO) > 0)
            return grossIncome.multiply(new BigDecimal(0.0919));
    }

    private BigDecimal getTaxableIncome() {
        return grossIncome.subtract(socialTax);
    }

    private BigDecimal getStateTax() {

        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;

        else if (taxableIncome.compareTo(new BigDecimal(15000)) <= 0)
            return taxableIncome.multiply(new BigDecimal(0.23));

        else if (taxableIncome.compareTo(new BigDecimal(28000)) <= 0)
            return taxableIncome
                    .subtract(new BigDecimal(15000))
                    .multiply(new BigDecimal(0.27))
                    .add(new BigDecimal(3450));

        else if (taxableIncome.compareTo(new BigDecimal(55000)) <= 0)
            return taxableIncome
                    .subtract(new BigDecimal(28000))
                    .multiply(new BigDecimal(0.38))
                    .add(new BigDecimal(6960));

        else if (taxableIncome.compareTo(new BigDecimal(75000)) <= 0)
            return taxableIncome
                    .subtract(new BigDecimal(55000))
                    .multiply(new BigDecimal(0.41))
                    .add(new BigDecimal(17220));

        else // if (taxableIncome.compareTo(new BigDecimal(75000)) > 0)
            return taxableIncome
                    .subtract(new BigDecimal(75000))
                    .multiply(new BigDecimal(0.43))
                    .add(new BigDecimal(25420));

    }

    private BigDecimal getFederalTax() {
        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;
        else // if (taxableIncome.compareTo(NO_TAX_AREA) > 0)
            return taxableIncome.multiply(new BigDecimal(0.0203));
    }

    private BigDecimal getLocalTax() {
        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;
        else // if (taxableIncome.compareTo(NO_TAX_AREA) > 0)
            return taxableIncome.multiply(new BigDecimal(0.008));
    }

    private BigDecimal getSalaryCredit1() {

        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;

        else if (taxableIncome.compareTo(new BigDecimal(28000)) <= 0)
            return new BigDecimal(28000)
                    .subtract(taxableIncome)
                    .divide(new BigDecimal(20000), MathContext.DECIMAL128)
                    .multiply(new BigDecimal(902))
                    .add(new BigDecimal(978));

        else if (taxableIncome.compareTo(new BigDecimal(55000)) <= 0)
            return new BigDecimal(55000)
                    .subtract(taxableIncome)
                    .divide(new BigDecimal(27000), MathContext.DECIMAL128)
                    .multiply(new BigDecimal(978));

        else // if (taxableIncome.compareTo(new BigDecimal(55000)) > 0)
            return BigDecimal.ZERO;

    }

    private BigDecimal getSalaryCredit2() {

        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;

        else if (taxableIncome.compareTo(new BigDecimal(24600)) <= 0)
            return new BigDecimal(960);

        else if (taxableIncome.compareTo(new BigDecimal(26600)) <= 0)
            return new BigDecimal(26600)
                    .subtract(taxableIncome)
                    .divide(new BigDecimal(2000), MathContext.DECIMAL128)
                    .multiply(new BigDecimal(960));

        else // if (taxableIncome.compareTo(new BigDecimal(26600)) > 0)
            return BigDecimal.ZERO;

    }

    private BigDecimal getNumOfSalaries() {
        return additionalSalaries.add(new BigDecimal(12));
    }

    private BigDecimal getNetIncome() {

        BigDecimal paycheckGrossIncome = grossIncome.divide(numOfSalaries, MathContext.DECIMAL128);
        BigDecimal paycheckSocialTax = socialTax.divide(numOfSalaries, MathContext.DECIMAL128);
        BigDecimal paycheckStateTax = stateTax.divide(numOfSalaries, MathContext.DECIMAL128);
        BigDecimal paycheckFederalTax = federalTax.divide(new BigDecimal(11), MathContext.DECIMAL128);
        BigDecimal paycheckLocalTax = localTax.divide(new BigDecimal(11), MathContext.DECIMAL128);
        BigDecimal paycheckSalaryCredit1 = salaryCredit1.divide(new BigDecimal(12), MathContext.DECIMAL128);
        BigDecimal paycheckSalaryCredit2 = salaryCredit2.divide(new BigDecimal(12), MathContext.DECIMAL128);
        BigDecimal paycheckNetBonus = netBonus.divide(new BigDecimal(12), MathContext.DECIMAL128);

        return paycheckGrossIncome
                .subtract(paycheckSocialTax)
                .subtract(paycheckStateTax)
                .subtract(paycheckFederalTax)
                .subtract(paycheckLocalTax)
                .add(paycheckSalaryCredit1)
                .add(paycheckSalaryCredit2)
                .add(paycheckNetBonus);

    }

    private BigDecimal valueOrZero(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0)
            return BigDecimal.ZERO;
        else // if (value.compareTo(BigDecimal.ZERO) > 0)
            return value;
    }

}
