package neat.it;

import neat.domain.Paycheck;

import java.math.BigDecimal;
import java.math.MathContext;

public class PaycheckBuilder {

    private BigDecimal additionalSalaries;
    private BigDecimal grossIncome;
    private BigDecimal netBonus;

    private BigDecimal socialTax;
    private BigDecimal taxableIncome;
    private BigDecimal noTaxArea;
    private BigDecimal stateTax;
    private BigDecimal federalTax;
    private BigDecimal localTax;
    private BigDecimal salaryCredit1;
    private BigDecimal salaryCredit2;
    private BigDecimal netIncome;

    public PaycheckBuilder() {
        this.additionalSalaries = BigDecimal.ZERO;
        this.grossIncome = BigDecimal.ZERO;
        this.netBonus = BigDecimal.ZERO;
    }

    public PaycheckBuilder setAdditionalSalaries(int additionalSalaries) {
        this.additionalSalaries = new BigDecimal(additionalSalaries).max(BigDecimal.ZERO);
        return this;
    }

    public PaycheckBuilder setGrossIncome(double grossIncome) {
        this.grossIncome = new BigDecimal(grossIncome).max(BigDecimal.ZERO);
        return this;
    }

    public PaycheckBuilder setNetBonus(double netBonus) {
        this.netBonus = new BigDecimal(netBonus).max(BigDecimal.ZERO);
        return this;
    }

    public Paycheck build() {
        this.socialTax = socialTax();
        this.taxableIncome = taxableIncome();
        this.noTaxArea = noTaxArea();
        this.stateTax = stateTax();
        this.federalTax = federalTax();
        this.localTax = localTax();
        this.salaryCredit1 = salaryCredit1();
        this.salaryCredit2 = salaryCredit2();
        this.netIncome = netIncome();
        return paycheck();
    }


    private BigDecimal socialTax() {
        if (grossIncome.compareTo(BigDecimal.ZERO) <= 0)
            return BigDecimal.ZERO;
        else // if (grossIncome.compareTo(BigDecimal.ZERO) > 0)
            return grossIncome.multiply(new BigDecimal(0.0919));
    }

    private BigDecimal taxableIncome() {
        return grossIncome.subtract(socialTax);
    }

    private BigDecimal noTaxArea() {
        return new BigDecimal(8174);
    }

    private BigDecimal stateTax() {

        if (taxableIncome.compareTo(noTaxArea) <= 0)
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

    private BigDecimal federalTax() {
        if (taxableIncome.compareTo(noTaxArea) <= 0)
            return BigDecimal.ZERO;
        else // if (taxableIncome.compareTo(noTaxArea) > 0)
            return taxableIncome.multiply(new BigDecimal(0.0203));
    }

    private BigDecimal localTax() {
        if (taxableIncome.compareTo(noTaxArea) <= 0)
            return BigDecimal.ZERO;
        else // if (taxableIncome.compareTo(noTaxArea) > 0)
            return taxableIncome.multiply(new BigDecimal(0.008));
    }

    private BigDecimal salaryCredit1() {

        if (taxableIncome.compareTo(noTaxArea) <= 0)
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

    private BigDecimal salaryCredit2() {

        if (taxableIncome.compareTo(noTaxArea) <= 0)
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

    private BigDecimal netIncome() {

        BigDecimal numOfMonths = new BigDecimal(12);
        BigDecimal numOfSalaries = numOfMonths.add(additionalSalaries);

        BigDecimal paycheckGrossIncome = grossIncome.divide(numOfSalaries, MathContext.DECIMAL128);
        BigDecimal paycheckSocialTax = socialTax.divide(numOfSalaries, MathContext.DECIMAL128);
        BigDecimal paycheckStateTax = stateTax.divide(numOfSalaries, MathContext.DECIMAL128);
        BigDecimal paycheckFederalTax = federalTax.divide(numOfMonths, MathContext.DECIMAL128);
        BigDecimal paycheckLocalTax = localTax.divide(numOfMonths, MathContext.DECIMAL128);
        BigDecimal paycheckSalaryCredit1 = salaryCredit1.divide(numOfMonths, MathContext.DECIMAL128);
        BigDecimal paycheckSalaryCredit2 = salaryCredit2.divide(numOfMonths, MathContext.DECIMAL128);
        BigDecimal paycheckNetBonus = netBonus.divide(numOfMonths, MathContext.DECIMAL128);

        return paycheckGrossIncome
                .subtract(paycheckSocialTax)
                .subtract(paycheckStateTax)
                .subtract(paycheckFederalTax)
                .subtract(paycheckLocalTax)
                .add(paycheckSalaryCredit1)
                .add(paycheckSalaryCredit2)
                .add(paycheckNetBonus);

    }

    private Paycheck paycheck() {
        return new Paycheck()
                .setGrossIncome(grossIncome)
                .setNetIncome(netIncome);
    }

}
