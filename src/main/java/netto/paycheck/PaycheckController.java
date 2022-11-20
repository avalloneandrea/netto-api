package netto.paycheck;

import netto.domain.Paycheck;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/paycheck")
@Tag(name = "Paychecks", description = "Operations about paychecks")
public class PaycheckController {

    @Inject
    PaycheckBuilder paycheckBuilder;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get the paycheck given the income")
    public Paycheck getPaycheck(
            @Parameter(description = "Gross income per year") @QueryParam("grossIncome") @DefaultValue("0") double grossIncome,
            @Parameter(description = "Number of additional salaries") @QueryParam("additionalSalaries") @DefaultValue("0") int additionalSalaries,
            @Parameter(description = "Net bonus per year") @QueryParam("netBonus") @DefaultValue("0") double netBonus) {
        return paycheckBuilder
                .setGrossIncome(grossIncome)
                .setAdditionalSalaries(additionalSalaries)
                .setNetBonus(netBonus)
                .build();
    }

}
