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
    @CsvSource({"-2, 1310.50", "-1, 1310.50", "0, 1310.50", "1, 1221.66", "2, 1145.52"})
    public void buildPaycheckWithValidAdditionalSalaries(int input, BigDecimal expected) {
        Paycheck paycheck = paycheckBuilder
                .setAdditionalSalaries(input)
                .setGrossIncome(20000)
                .setNetBonus(0)
                .build();
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    @ParameterizedTest
    @CsvSource({
            "-20000.00, 0.00", "-1.00, 0.00", "0.00, 0.00", "1.00, 0.08", "4087.00, 309.28",
            "8173.00, 618.49", "8174.00, 618.57", "8175.00, 618.64", "11587.00, 877.54",
            "14999.00, 1057.41", "15000.00, 1057.46", "15001.00, 1057.51", "19800.00, 1300.56",
            "24599.00, 1539.01", "24600.00, 1539.06", "24601.00, 1539.11", "25600.00, 1588.75",
            "26599.00, 1638.39", "26600.00, 1638.44", "26601.00, 1638.49", "27300.00, 1665.58",
            "27999.00, 1674.92", "28000.00, 1674.93", "28001.00, 1674.94", "41500.00, 2217.17",
            "54999.00, 2784.61", "55000.00, 2784.65", "55001.00, 2784.70", "65000.00, 3207.10",
            "74999.00, 3632.12", "75000.00, 3632.17", "75001.00, 3632.21", "100000.00, 4668.48"})
    public void buildPaycheckWithValidGrossIncome(double input, BigDecimal expected) {
        Paycheck paycheck = paycheckBuilder
                .setAdditionalSalaries(0)
                .setGrossIncome(input)
                .setNetBonus(0)
                .build();
        assertThat(scaled(paycheck.getNetIncome()), is(expected));
    }

    @ParameterizedTest
    @CsvSource({"-600.00, 1310.50", "-1.00, 1310.50", "0.00, 1310.50", "1.00, 1310.58", "600.00, 1360.50"})
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
