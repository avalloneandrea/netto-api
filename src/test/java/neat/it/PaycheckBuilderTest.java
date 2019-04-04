package neat.it;

import neat.domain.Paycheck;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PaycheckBuilderTest {

    @ParameterizedTest
    @CsvSource({"-2, 1307", "-1, 1307", "0, 1307", "1, 1218", "2, 1142"})
    public void buildPaycheckWithParameterizedAdditionalSalaries(int input, BigDecimal expected) {
        Paycheck paycheck = new PaycheckBuilder()
                .setAdditionalSalaries(input)
                .setGrossIncome(20000)
                .setNetBonus(0)
                .build();
        assertThat(paycheck.getNetIncome(), is(expected));
    }

    @ParameterizedTest
    @CsvSource({
            "-9999.00, 0", "-1.00, 0", "0.00, 0", "1.00, 0", "4087.00, 309",
            "5279.10, 399", "5279.20, 400", "5292.30, 400", "5292.40, 401",
            "8173.00, 618", "8174.00, 619", "8175.00, 619", "11587.00, 875",
            "14999.00, 1054", "15000.00, 1055", "15001.00, 1055", "19800.00, 1297",
            "24599.00, 1534", "24600.00, 1534", "24601.00, 1534", "25600.00, 1584",
            "26599.00, 1633", "26600.00, 1633", "26601.00, 1633", "27300.00, 1660",
            "27999.00, 1669", "28000.00, 1669", "28001.00, 1669", "41500.00, 2209",
            "54999.00, 2774", "55000.00, 2774", "55001.00, 2774", "65000.00, 3194",
            "74999.00, 3618", "75000.00, 3618", "75001.00, 3618", "87500.00, 4139"})
    public void buildPaycheckWithParameterizedGrossIncome(double input, BigDecimal expected) {
        Paycheck paycheck = new PaycheckBuilder()
                .setAdditionalSalaries(0)
                .setGrossIncome(input)
                .setNetBonus(0)
                .build();
        assertThat(paycheck.getNetIncome(), is(expected));
    }

    @ParameterizedTest
    @CsvSource({
            "-9999.00, 1307", "-1.00, 1307", "0.00, 1307", "1.00, 1307", "600.00, 1357",
            "814.70, 1374", "814.80, 1375", "826.70, 1375", "826.80, 1376"})
    public void buildPaycheckWithParameterizedNetBonus(double input, BigDecimal expected) {
        Paycheck paycheck = new PaycheckBuilder()
                .setAdditionalSalaries(0)
                .setGrossIncome(20000)
                .setNetBonus(input)
                .build();
        assertThat(paycheck.getNetIncome(), is(expected));
    }

}
