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

        if (stateTax.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = stateTax.divide(numOfSalaries, MathContext.DECIMAL128);
            Item item = new Item().setCode("STATE_TAX").setValue(value);
            paycheck.addTaxes(item);
            netIncomeAccumulator = netIncomeAccumulator.subtract(value);
        }

        if (federalTax.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = federalTax.divide(numOfMonths, MathContext.DECIMAL128);
            Item item = new Item().setCode("FEDERAL_TAX").setValue(value);
            paycheck.addTaxes(item);
            netIncomeAccumulator = netIncomeAccumulator.subtract(value);
        }

        if (localTax.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = localTax.divide(numOfMonths, MathContext.DECIMAL128);
            Item item = new Item().setCode("LOCAL_TAX").setValue(value);
            paycheck.addTaxes(item);
            netIncomeAccumulator = netIncomeAccumulator.subtract(value);
        }

        if (salaryCredit.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = salaryCredit.divide(numOfMonths, MathContext.DECIMAL128);
            Item item = new Item().setCode("SALARY_CREDIT").setValue(value);
            paycheck.addCredits(item);
            netIncomeAccumulator = netIncomeAccumulator.add(value);
        }

        if (salaryCredit2.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = salaryCredit2.divide(numOfMonths, MathContext.DECIMAL128);
            Item item = new Item().setCode("SALARY_CREDIT_2").setValue(value);
            paycheck.addCredits(item);
            netIncomeAccumulator = netIncomeAccumulator.add(value);
        }

        if (netBonus.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal value = netBonus.divide(numOfMonths, MathContext.DECIMAL128);
            Item item = new Item().setCode("Net bonus").setValue(value);
            paycheck.addCredits(item);
            netIncomeAccumulator = netIncomeAccumulator.add(value);
        }

        paycheck.setNetIncome(netIncomeAccumulator);
        return paycheck;

    }

}
