package netto.domain;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.constraint.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

@QuarkusTest
public class PaycheckTest {

    @ParameterizedTest
    @CsvSource({"-20000.00, -20000.00", "0.00, 0.00", "20000.00, 20000.00"})
    public void getAndSetGrossIncomeWithValidValue(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck().setGrossIncome(input);
        assertThat(scaled(paycheck.getGrossIncome()), is(expected));
    }

    @Test
    public void getAndSetGrossIncomeWithInvalidValue() {
        Paycheck paycheck = new Paycheck().setGrossIncome(null);
        assertThat(paycheck.getGrossIncome(), is(BigDecimal.ZERO));
    }

    @ParameterizedTest
    @CsvSource({"-500.00, -500.00", "0.00, 0.00", "500.00, 500.00"})
    public void getAndSetTaxesWithValidValue(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck().setTaxes(Collections.singletonList(new Item().setValue(input)));
        assertThat(first(paycheck.getTaxes()), is(expected));
    }

    @Test
    public void getAndSetTaxesWithInvalidValue() {
        Paycheck paycheck = new Paycheck().setTaxes(null);
        assertThat(paycheck.getTaxes(), is(empty()));
    }

    @ParameterizedTest
    @CsvSource({"-500.00, -500.00", "0.00, 0.00", "500.00, 500.00"})
    public void getAndSetCreditsWithValidValue(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck().setCredits(Collections.singletonList(new Item().setValue(input)));
        assertThat(first(paycheck.getCredits()), is(expected));
    }

    @Test
    public void getAndSetCreditsWithInvalidValue() {
        Paycheck paycheck = new Paycheck().setCredits(null);
        assertThat(paycheck.getCredits(), is(empty()));
    }

    @ParameterizedTest
    @CsvSource({"-1500.00, -1500.00", "0.00, 0.00", "1500.00, 1500.00"})
    public void getAndSetNetIncomeWithValidValue(BigDecimal input, BigDecimal expected) {
        Paycheck paycheck = new Paycheck().setNetIncome(input);
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    @Test
    public void getAndSetNetIncomeWithInvalidValue() {
        Paycheck paycheck = new Paycheck().setNetIncome(null);
        assertThat(paycheck.getNetIncome(), is(BigDecimal.ZERO));
    }

    private BigDecimal scaled(@NotNull BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal first(@NotNull List<Item> collection) {
        return collection
                .stream()
                .findFirst()
                .map(Item::getValue)
                .orElse(null);
    }

}
