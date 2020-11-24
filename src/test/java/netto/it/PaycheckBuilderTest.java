package netto.it;

import netto.domain.Paycheck;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PaycheckBuilder.class)
public class PaycheckBuilderTest {

    @Autowired
    private PaycheckBuilder paycheckBuilder;

    @ParameterizedTest
    @CsvSource({"-2, 1330.50", "-1, 1330.50", "0, 1330.50", "1, 1241.66", "2, 1165.52"})
    public void buildPaycheckWithValidAdditionalSalaries(int input, BigDecimal expected) {
        Paycheck paycheck = paycheckBuilder
                .setAdditionalSalaries(input)
                .setGrossIncome(20000)
                .setNetBonus(0)
                .build();
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    @ParameterizedTest
    @CsvSource({"-20000.00, 0.00", "0.00, 0.00", "8174.00, 618.57", "15000.00, 1077.46", "28000.00, 1728.00",
            "35000.00, 2033.13", "40000.00, 2271.75", "55000.00, 2784.65", "75000.00, 3632.17", "100000.00, 4668.48"})
    public void buildPaycheckWithValidGrossIncome(double input, BigDecimal expected) {
        Paycheck paycheck = paycheckBuilder
                .setAdditionalSalaries(0)
                .setGrossIncome(input)
                .setNetBonus(0)
                .build();
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    @ParameterizedTest
    @CsvSource({"-500.00, 1330.50", "0.00, 1330.50", "500.00, 1372.16"})
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
