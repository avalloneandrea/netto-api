package neat.it;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import neat.domain.Paycheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Italy", description = "Operations about italian paychecks")
@CrossOrigin
@RestController
public class PaycheckController {

    @Autowired
    private PaycheckBuilder paycheckBuilder;

    @ApiOperation(value = "Get the paycheck given the income")
    @GetMapping(value = "/it/paycheck", produces = MediaType.APPLICATION_JSON_VALUE)
    public Paycheck getPaycheck(@ApiParam(value = "Number of additional salaries") @RequestParam(value = "additionalSalaries", defaultValue = "0") int additionalSalaries,
                                @ApiParam(value = "Gross income per year") @RequestParam(value = "grossIncome", defaultValue = "0") double grossIncome,
                                @ApiParam(value = "Net bonus per year") @RequestParam(value = "netBonus", defaultValue = "0") double netBonus) {
        return paycheckBuilder
                .setAdditionalSalaries(additionalSalaries)
                .setGrossIncome(grossIncome)
                .setNetBonus(netBonus)
                .build();
    }

}
