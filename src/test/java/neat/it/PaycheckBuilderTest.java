package neat.it;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import neat.domain.Paycheck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

@RunWith(JUnitParamsRunner.class)
public class PaycheckBuilderTest {

    @Test
    @Parameters({
            "-1, 0", "0, 0", "1, 0",
            "2043.50, 150", "4087, 300", "6130.50, 450",
            "8173, 599", "8174, 599", "8175, 600",
            "9880.50, 786", "11587, 875", "13293.50, 965",
            "14999, 1054", "15000, 1055", "15001, 1055",
            "17400, 1178", "19800, 1297", "22200, 1415",
            "24599, 1534", "24600, 1534", "24601, 1534",
            "25100, 1559", "25600, 1584", "26100, 1609",
            "26599, 1633", "26600, 1633", "26601, 1633",
            "26950, 1651", "27300, 1660", "27650, 1665",
            "27999, 1669", "28000, 1669", "28001, 1669",
            "34750, 1927", "41500, 2209", "48250, 2492",
            "54999, 2774", "55000, 2774", "55001, 2774",
            "60000, 2983", "65000, 3194", "70000, 3406",
            "74999, 3618", "75000, 3618", "75001, 3618",
            "81250, 3882", "87500, 4139", "93750, 4394"
    })
    public void buildPaycheckOnNumericalGrossIncome(BigDecimal input, BigDecimal expected) {
        PaycheckBuilder paycheckBuilder = new PaycheckBuilder(input, BigDecimal.ZERO, BigDecimal.ZERO);
        Paycheck paycheck = paycheckBuilder.build();
        Assert.assertEquals(expected, paycheck.getNetIncome());
    }

}
