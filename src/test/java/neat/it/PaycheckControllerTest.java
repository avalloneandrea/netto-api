package neat.it;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {PaycheckController.class, PaycheckBuilder.class})
public class PaycheckControllerTest {

    @Autowired
    private MockMvc webEnvironment;

    @ParameterizedTest
    @CsvSource({"-1", "0", "1"})
    public void getPaycheckWithValidAdditionalSalaries(int input) throws Exception {
        webEnvironment
                .perform(get("/it/paycheck?additionalSalaries=" + input))
                .andExpect(status().is2xxSuccessful());
    }

    @ParameterizedTest
    @CsvSource({"1.5", "two"})
    public void getPaycheckWithInvalidAdditionalSalaries(String input) throws Exception {
        webEnvironment
                .perform(get("/it/paycheck?additionalSalaries=" + input))
                .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"-20000.00", "0", "20000.00"})
    public void getPaycheckWithValidGrossIncome(BigDecimal input) throws Exception {
        webEnvironment
                .perform(get("/it/paycheck?grossIncome=" + input))
                .andExpect(status().is2xxSuccessful());
    }

    @ParameterizedTest
    @CsvSource({"twentythousand"})
    public void getPaycheckWithInvalidGrossIncome(String input) throws Exception {
        webEnvironment
                .perform(get("/it/paycheck?grossIncome=" + input))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getPaycheckWithNullGrossIncome() throws Exception {
        webEnvironment
                .perform(get("/it/paycheck?grossIncome="))
                .andExpect(status().is2xxSuccessful());
    }

    @ParameterizedTest
    @CsvSource({"-600.00", "0", "600.00"})
    public void getPaycheckWithValidNetBonus(BigDecimal input) throws Exception {
        webEnvironment
                .perform(get("/it/paycheck?netBonus=" + input))
                .andExpect(status().is2xxSuccessful());
    }

    @ParameterizedTest
    @CsvSource({"sixhundred"})
    public void getPaycheckWithInvalidNetBonus(String input) throws Exception {
        webEnvironment
                .perform(get("/it/paycheck?netBonus=" + input))
                .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest
    @CsvSource({"-600.00", "0", "600.00"})
    public void getPaycheckWithNullNetBonus() throws Exception {
        webEnvironment
                .perform(get("/it/paycheck?netBonus="))
                .andExpect(status().is2xxSuccessful());
    }

}
