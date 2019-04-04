package neat.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PaycheckTest {

    @ParameterizedTest
    @CsvSource({
            "-9999.00, -9999", "-1.00, -1", "0.00, 0", "1.00, 1",
            "20000.00, 20000", "20000.25, 20000", "20000.50, 20000", "20000.75, 20001"})
    public void getAndSetGrossIncomeWithParameterizedValue(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck();
        paycheck.setGrossIncome(input);
        assertThat(paycheck.getGrossIncome(), is(expected));
    }

    @Test
    public void getAndSetGrossIncomeWithNullValue() {
        Paycheck paycheck = new Paycheck();
        paycheck.setGrossIncome(null);
        assertThat(paycheck.getGrossIncome(), is(BigDecimal.ZERO));
    }

    @ParameterizedTest
    @CsvSource({
            "-9999.00, -9999", "-1.00, -1", "0.00, 0", "1.00, 1",
            "1200.00, 1200", "1200.25, 1200", "1200.50, 1200", "1200.75, 1201"})
    public void getAndSetNetIncomeWithParameterizedValue(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck();
        paycheck.setNetIncome(input);
        assertThat(paycheck.getNetIncome(), is(expected));
    }

    @Test
    public void getAndSetNetIncomeWithNullValue() {
        Paycheck paycheck = new Paycheck();
        paycheck.setNetIncome(null);
        assertThat(paycheck.getNetIncome(), is(BigDecimal.ZERO));
    }

}
