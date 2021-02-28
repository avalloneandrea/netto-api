package netto.paycheck;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {PaycheckController.class, PaycheckBuilder.class})
public class PaycheckControllerTest {

    @Autowired
    private MockMvc webEnvironment;

    @ParameterizedTest
    @CsvSource({"-1", "0", "1"})
    @EmptySource
    public void getPaycheckWithValidAdditionalSalaries(String input) throws Exception {
        webEnvironment
                .perform(get("/paycheck?additionalSalaries=" + input))
                .andExpect(status().is2xxSuccessful());
    }

    @ParameterizedTest
    @CsvSource({"1.5", "two"})
    @NullSource
    public void getPaycheckWithInvalidAdditionalSalaries(String input) throws Exception {
        webEnvironment
                .perform(get("/paycheck?additionalSalaries=" + input))
                .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"-20000.00", "0", "20000.00"})
    @EmptySource
    public void getPaycheckWithValidGrossIncome(String input) throws Exception {
        webEnvironment
                .perform(get("/paycheck?grossIncome=" + input))
                .andExpect(status().is2xxSuccessful());
    }

    @ParameterizedTest
    @CsvSource({"twentythousand"})
    @NullSource
    public void getPaycheckWithInvalidGrossIncome(String input) throws Exception {
        webEnvironment
                .perform(get("/paycheck?grossIncome=" + input))
                .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"-500.00", "0", "500.00"})
    @EmptySource
    public void getPaycheckWithValidNetBonus(String input) throws Exception {
        webEnvironment
                .perform(get("/paycheck?netBonus=" + input))
                .andExpect(status().is2xxSuccessful());
    }

    @ParameterizedTest
    @CsvSource({"fivehundred"})
    @NullSource
    public void getPaycheckWithInvalidNetBonus(String input) throws Exception {
        webEnvironment
                .perform(get("/paycheck?netBonus=" + input))
                .andExpect(status().is4xxClientError());
    }

}
