package neat.it;

import neat.domain.Item;
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
    private BigDecimal salaryCredit;
    private BigDecimal salaryCredit2;

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
        this.stateTax = stateTax();
        this.federalTax = federalTax();
        this.localTax = localTax();
        this.salaryCredit = salaryCredit();
        this.salaryCredit2 = salaryCredit2();
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

    private BigDecimal stateTax() {

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

    private BigDecimal federalTax() {
        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;
        else // if (taxableIncome.compareTo(NO_TAX_AREA) > 0)
            return taxableIncome.multiply(new BigDecimal(0.0203));
    }

    private BigDecimal localTax() {
        if (taxableIncome.compareTo(NO_TAX_AREA) <= 0)
            return BigDecimal.ZERO;
        else // if (taxableIncome.compareTo(NO_TAX_AREA) > 0)
            return taxableIncome.multiply(new BigDecimal(0.008));
    }

    private BigDecimal salaryCredit() {

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

    private BigDecimal salaryCredit2() {

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

    private Paycheck paycheck() {

        BigDecimal numOfMonths = new BigDecimal(12);
        BigDecimal numOfSalaries = numOfMonths.add(additionalSalaries);

        BigDecimal grossIncome = this.grossIncome.divide(numOfSalaries, MathContext.DECIMAL128);
        BigDecimal socialTax = this.socialTax.divide(numOfSalaries, MathContext.DECIMAL128);
        BigDecimal stateTax = this.stateTax.divide(numOfSalaries, MathContext.DECIMAL128);
        BigDecimal federalTax = this.federalTax.divide(numOfMonths, MathContext.DECIMAL128);
        BigDecimal localTax = this.localTax.divide(numOfMonths, MathContext.DECIMAL128);
        BigDecimal salaryCredit = this.salaryCredit.divide(numOfMonths, MathContext.DECIMAL128);
        BigDecimal salaryCredit2 = this.salaryCredit2.divide(numOfMonths, MathContext.DECIMAL128);
        BigDecimal netBonus = this.netBonus.divide(numOfMonths, MathContext.DECIMAL128);
        BigDecimal netIncome = grossIncome
                .subtract(socialTax)
                .subtract(stateTax)
                .subtract(federalTax)
                .subtract(localTax)
                .add(salaryCredit)
                .add(salaryCredit2)
                .add(netBonus);

        return new Paycheck()
                .setGrossIncome(grossIncome)
                .addTaxes(new Item().setCode("Social tax").setValue(socialTax))
                .addTaxes(new Item().setCode("State tax").setValue(stateTax))
                .addTaxes(new Item().setCode("Federal tax").setValue(federalTax))
                .addTaxes(new Item().setCode("Local tax").setValue(localTax))
                .addCredits(new Item().setCode("Salary credit").setValue(salaryCredit))
                .addCredits(new Item().setCode("Salary credit 2").setValue(salaryCredit2))
                .addCredits(new Item().setCode("Net bonus").setValue(netBonus))
                .setNetIncome(netIncome);

    }

}
