package neat.it;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import neat.domain.Paycheck;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Api(tags = "Italy", description = "Operations about italian paychecks")
public class PaycheckController {

    @ApiOperation(value = "Get the paycheck given the income")
    @RequestMapping(method = RequestMethod.GET, value = "/it/paycheck", produces = MediaType.APPLICATION_JSON_VALUE)
    public Paycheck getPaycheck(
            @ApiParam(value = "Number of additional salaries") @RequestParam(value = "additionalSalaries", defaultValue = "0") int additionalSalaries,
            @ApiParam(value = "Gross income per year") @RequestParam(value = "grossIncome", defaultValue = "0") double grossIncome,
            @ApiParam(value = "Net bonus per year") @RequestParam(value = "netBonus", defaultValue = "0") double netBonus) {
        return new PaycheckBuilder()
                .setAdditionalSalaries(additionalSalaries)
                .setGrossIncome(grossIncome)
                .setNetBonus(netBonus)
                .build();
    }

}
