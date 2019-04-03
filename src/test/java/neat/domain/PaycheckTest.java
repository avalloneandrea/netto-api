package neat.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;

@RunWith(JUnitParamsRunner.class)
public class PaycheckTest {

    @Test
    @Parameters({
            "-9999.00, -9999", "-1.00, -1", "0.00, 0", "1.00, 1",
            "20000.00, 20000", "20000.25, 20000", "20000.50, 20000", "20000.75, 20001"})
    public void getAndSetGrossIncomeWithNumericalValues(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck();
        paycheck.setGrossIncome(input);
        Assert.assertThat(paycheck.getGrossIncome(), is(expected));
    }

    @Test
    public void getAndSetGrossIncomeWithNullValue() {
        Paycheck paycheck = new Paycheck();
        paycheck.setGrossIncome(null);
        Assert.assertThat(paycheck.getGrossIncome(), is(BigDecimal.ZERO));
    }

    @Test
    @Parameters({
            "-9999.00, -9999", "-1.00, -1", "0.00, 0", "1.00, 1",
            "1200.00, 1200", "1200.25, 1200", "1200.50, 1200", "1200.75, 1201"})
    public void getAndSetNetIncomeWithNumericalValues(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck();
        paycheck.setNetIncome(input);
        Assert.assertThat(paycheck.getNetIncome(), is(expected));
    }

    @Test
    public void getAndSetNetIncomeWithNullValue() {
        Paycheck paycheck = new Paycheck();
        paycheck.setNetIncome(null);
        Assert.assertThat(paycheck.getNetIncome(), is(BigDecimal.ZERO));
    }

}
