package netto.paycheck;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.constraint.NotNull;
import netto.domain.Paycheck;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@QuarkusTest
public class PaycheckBuilderTest {

    @Inject
    PaycheckBuilder paycheckBuilder;

    @ParameterizedTest
    @CsvSource({"-20000.00, 0.00", "0.00, 0.00", "8174.00, 624.02", "15000.00, 1106.01", "28000.00, 1750.19",
            "35000.00, 2054.67", "40000.00, 2239.16", "55000.00, 2846.32", "75000.00, 3666.15", "100000.00, 4690.98"})
    public void buildPaycheckWithValidGrossIncome(double input, BigDecimal expected) {
        Paycheck paycheck = paycheckBuilder
                .setAdditionalSalaries(0)
                .setGrossIncome(input)
                .setNetBonus(0)
                .build();
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    @ParameterizedTest
    @CsvSource({"-2, 1359.91", "-1, 1359.91", "0, 1359.91", "1, 1269.90", "2, 1192.75"})
    public void buildPaycheckWithValidAdditionalSalaries(int input, BigDecimal expected) {
        Paycheck paycheck = paycheckBuilder
                .setAdditionalSalaries(input)
                .setGrossIncome(20000)
                .setNetBonus(0)
                .build();
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    @ParameterizedTest
    @CsvSource({"-500.00, 1359.91", "0.00, 1359.91", "500.00, 1401.57"})
    public void buildPaycheckWithValidNetBonus(double input, BigDecimal expected) {
        Paycheck paycheck = paycheckBuilder
                .setAdditionalSalaries(0)
                .setGrossIncome(20000)
                .setNetBonus(input)
                .build();
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    private BigDecimal scaled(@NotNull BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }

}
