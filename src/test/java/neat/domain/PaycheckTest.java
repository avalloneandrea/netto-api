package neat.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

public class PaycheckTest {

    @ParameterizedTest
    @CsvSource({"-20000.00, -20000.00", "-1.00, -1.00", "0.00, 0.00", "1.00, 1.00", "20000.00, 20000.00"})
    public void getAndSetGrossIncomeWithValidValue(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck();
        paycheck.setGrossIncome(input);
        assertThat(paycheck.getGrossIncome().setScale(2, RoundingMode.HALF_EVEN), is(expected));
    }

    @Test
    public void getAndSetGrossIncomeWithNullValue() {
        Paycheck paycheck = new Paycheck();
        paycheck.setGrossIncome(null);
        assertThat(paycheck.getGrossIncome(), is(BigDecimal.ZERO));
    }

    @ParameterizedTest
    @CsvSource({"-300.00, -300.00", "-1.00, -1.00", "0.00, 0.00", "1.00, 1.00", "300.00, 300.00"})
    public void getAndSetTaxesWithValidValue(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck();
        paycheck.setTaxes(Collections.singletonMap("", input));
        assertThat(paycheck.getTaxes().values().stream().findFirst().orElse(null), is(expected));
    }

    @Test
    public void getAndSetTaxesWithNullValue() {
        Paycheck paycheck = new Paycheck();
        paycheck.setTaxes(null);
        assertThat(paycheck.getTaxes().values(), is(empty()));
    }

    @ParameterizedTest
    @CsvSource({"-300.00, -300.00", "-1.00, -1.00", "0.00, 0.00", "1.00, 1.00", "300.00, 300.00"})
    public void getAndSetCreditsWithValidValue(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck();
        paycheck.setCredits(Collections.singletonMap("", input));
        assertThat(paycheck.getCredits().values().stream().findFirst().orElse(null), is(expected));
    }

    @Test
    public void getAndSetCreditsWithNullValue() {
        Paycheck paycheck = new Paycheck();
        paycheck.setCredits(null);
        assertThat(paycheck.getCredits().values(), is(empty()));
    }

    @ParameterizedTest
    @CsvSource({"-1200.00, -1200.00", "-1.00, -1.00", "0.00, 0.00", "1.00, 1.00", "1200.00, 1200.00"})
    public void getAndSetNetIncomeWithValidValue(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck();
        paycheck.setNetIncome(input);
        assertThat(paycheck.getNetIncome().setScale(2, RoundingMode.HALF_EVEN), is(expected));
    }

    @Test
    public void getAndSetNetIncomeWithNullValue() {
        Paycheck paycheck = new Paycheck();
        paycheck.setNetIncome(null);
        assertThat(paycheck.getNetIncome(), is(BigDecimal.ZERO));
    }

}
