package netto.paycheck;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import netto.domain.Paycheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Paychecks", description = "Operations about paychecks")
@CrossOrigin
@RestController
public class PaycheckController {

    @Autowired
    private PaycheckBuilder paycheckBuilder;

    @Operation(summary = "Get the paycheck given the income")
    @GetMapping(value = "/paycheck", produces = MediaType.APPLICATION_JSON_VALUE)
    public Paycheck getPaycheck(@Parameter(name = "additionalSalaries", description = "Number of additional salaries") @RequestParam(value = "additionalSalaries", defaultValue = "0") int additionalSalaries,
                                @Parameter(name = "grossIncome", description = "Gross income per year") @RequestParam(value = "grossIncome", defaultValue = "0") double grossIncome,
                                @Parameter(name = "netBonus", description = "Net bonus per year") @RequestParam(value = "netBonus", defaultValue = "0") double netBonus) {
        return paycheckBuilder
                .setAdditionalSalaries(additionalSalaries)
                .setGrossIncome(grossIncome)
                .setNetBonus(netBonus)
                .build();
    }

}
