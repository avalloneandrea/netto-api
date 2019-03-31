package neat.it;

import neat.domain.Paycheck;

import java.math.BigDecimal;
import java.math.MathContext;

public class PaycheckBuilder {

    private final BigDecimal grossIncome;
    private final BigDecimal numOfSalaries;
    private final BigDecimal netBonus;

    private final BigDecimal socialTax;
    private final BigDecimal taxableIncome;
    private final BigDecimal stateTax;
    private final BigDecimal federalTax;
    private final BigDecimal localTax;
    private final BigDecimal salaryCredit1;
    private final BigDecimal salaryCredit2;
    private final BigDecimal netIncome;

    public PaycheckBuilder() {
        this(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public PaycheckBuilder(BigDecimal grossIncome, BigDecimal additionalSalaries, BigDecimal netBonus) {

        this.grossIncome = grossIncome;
        this.numOfSalaries = new BigDecimal(12).add(additionalSalaries);
        this.netBonus = netBonus;

        this.socialTax = getSocialTax();
        this.taxableIncome = getTaxableIncome();
        this.stateTax = getStateTax();
        this.federalTax = getFederalTax();
        this.localTax = getLocalTax();
        this.salaryCredit1 = getSalaryCredit1();
        this.salaryCredit2 = getSalaryCredit2();
        this.netIncome = getNetIncome();

    }

    private BigDecimal getSocialTax() {
        return grossIncome.multiply(new BigDecimal(0.0919));
    }

    private BigDecimal getTaxableIncome() {
        return grossIncome.subtract(socialTax);
    }

    private BigDecimal getStateTax() {

        if (taxableIncome.compareTo(new BigDecimal(8174)) <= 0)
            return new BigDecimal(0);

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
        return taxableIncome.multiply(new BigDecimal(0.0203));
    }

    private BigDecimal getLocalTax() {
        return taxableIncome.multiply(new BigDecimal(0.008));
    }

    private BigDecimal getSalaryCredit1() {

        if (taxableIncome.compareTo(new BigDecimal(8174)) <= 0)
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

        if (taxableIncome.compareTo(new BigDecimal(8174)) <= 0)
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

    public Paycheck build() {
        Paycheck paycheck = new Paycheck();
        paycheck.setGrossIncome(grossIncome);
        paycheck.setNetIncome(netIncome);
        return paycheck;
    }

}
