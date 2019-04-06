package neat.it;

import neat.domain.Paycheck;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PaycheckBuilderTest {

    @ParameterizedTest
    @CsvSource({"-2, 1310", "-1, 1310", "0, 1310", "1, 1222", "2, 1146"})
    public void buildPaycheckWithValidAdditionalSalaries(int input, BigDecimal expected) {
        Paycheck paycheck = new PaycheckBuilder()
                .setAdditionalSalaries(input)
                .setGrossIncome(20000)
                .setNetBonus(0)
                .build();
        assertThat(paycheck.getNetIncome(), is(expected));
    }

    @ParameterizedTest
    @CsvSource({
            "-20000.00, 0", "-1.00, 0", "0.00, 0", "1.00, 0", "4087.00, 309", "4567.89, 346",
            "8173.00, 618", "8174.00, 619", "8175.00, 619", "11587.00, 878",
            "14999.00, 1057", "15000.00, 1057", "15001.00, 1058", "19800.00, 1301",
            "24599.00, 1539", "24600.00, 1539", "24601.00, 1539", "25600.00, 1589",
            "26599.00, 1638", "26600.00, 1638", "26601.00, 1638", "27300.00, 1666",
            "27999.00, 1675", "28000.00, 1675", "28001.00, 1675", "41500.00, 2217",
            "54999.00, 2785", "55000.00, 2785", "55001.00, 2785", "65000.00, 3207",
            "74999.00, 3632", "75000.00, 3632", "75001.00, 3632", "100000.00, 4668"})
    public void buildPaycheckWithValidGrossIncome(double input, BigDecimal expected) {
        Paycheck paycheck = new PaycheckBuilder()
                .setAdditionalSalaries(0)
                .setGrossIncome(input)
                .setNetBonus(0)
                .build();
        assertThat(paycheck.getNetIncome(), is(expected));
    }

    @ParameterizedTest
    @CsvSource({"-600.00, 1310", "-1.00, 1310", "0.00, 1310", "1.00, 1311", "600.00, 1360", "678.99, 1367"})
    public void buildPaycheckWithValidNetBonus(double input, BigDecimal expected) {
        Paycheck paycheck = new PaycheckBuilder()
                .setAdditionalSalaries(0)
                .setGrossIncome(20000)
                .setNetBonus(input)
                .build();
        assertThat(paycheck.getNetIncome(), is(expected));
    }

}
