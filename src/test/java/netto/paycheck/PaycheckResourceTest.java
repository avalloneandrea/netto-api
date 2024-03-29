package netto.paycheck;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import netto.domain.Paycheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;

@QuarkusTest
public class PaycheckResourceTest {

    @InjectMock
    PaycheckBuilder paycheckBuilder;

    @BeforeEach
    public void setup() {
        Mockito.when(paycheckBuilder.setGrossIncome(anyDouble())).thenReturn(paycheckBuilder);
        Mockito.when(paycheckBuilder.setAdditionalSalaries(anyInt())).thenReturn(paycheckBuilder);
        Mockito.when(paycheckBuilder.setNetAllowance(anyDouble())).thenReturn(paycheckBuilder);
        Mockito.when(paycheckBuilder.build()).thenReturn(new Paycheck());
    }

    @ParameterizedTest
    @CsvSource({"-20000.00", "0", "20000.00"})
    public void getPaycheckWithValidGrossIncome(String input) {
        given()
                .when().get("/paycheck?grossIncome=" + input)
                .then().statusCode(200);
    }

    @ParameterizedTest
    @CsvSource({"twentythousand"})
    @NullSource
    public void getPaycheckWithInvalidGrossIncome(String input) {
        given()
                .when().get("/paycheck?grossIncome=" + input)
                .then().statusCode(404);
    }

    @ParameterizedTest
    @CsvSource({"-1", "0", "1"})
    public void getPaycheckWithValidAdditionalSalaries(String input) {
        given()
                .when().get("/paycheck?additionalSalaries=" + input)
                .then().statusCode(200);
    }

    @ParameterizedTest
    @CsvSource({"1.5", "two"})
    @NullSource
    public void getPaycheckWithInvalidAdditionalSalaries(String input) {
        given()
                .when().get("/paycheck?additionalSalaries=" + input)
                .then().statusCode(404);
    }

    @ParameterizedTest
    @CsvSource({"-100.00", "0", "100.00"})
    public void getPaycheckWithValidNetAllowance(String input) {
        given()
                .when().get("/paycheck?netAllowance=" + input)
                .then().statusCode(200);
    }

    @ParameterizedTest
    @CsvSource({"onehundred"})
    @NullSource
    public void getPaycheckWithInvalidNetAllowance(String input) {
        given()
                .when().get("/paycheck?netAllowance=" + input)
                .then().statusCode(404);
    }

}
