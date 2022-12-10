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
    @CsvSource({"-20000.00, 0.00", "0.00, 0.00", "8174.00, 632.19", "15000.00, 1117.13", "28000.00, 1767.83", "50000.00, 2643.93", "100000.00, 4690.98"})
    public void buildPaycheckWithValidGrossIncome(double input, BigDecimal expected) {
        Paycheck paycheck = paycheckBuilder
                .setAdditionalSalaries(0)
                .setGrossIncome(input)
                .setNetAllowance(0)
                .build();
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    @ParameterizedTest
    @CsvSource({"-2, 1372.51", "-1, 1372.51", "0, 1372.51", "1, 1281.35", "2, 1203.21"})
    public void buildPaycheckWithValidAdditionalSalaries(int input, BigDecimal expected) {
        Paycheck paycheck = paycheckBuilder
                .setAdditionalSalaries(input)
                .setGrossIncome(20000)
                .setNetAllowance(0)
                .build();
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    @ParameterizedTest
    @CsvSource({"-100.00, 1372.51", "0.00, 1372.51", "100.00, 1472.51"})
    public void buildPaycheckWithValidNetAllowance(double input, BigDecimal expected) {
        Paycheck paycheck = paycheckBuilder
                .setAdditionalSalaries(0)
                .setGrossIncome(20000)
                .setNetAllowance(input)
                .build();
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    private BigDecimal scaled(@NotNull BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }

}
