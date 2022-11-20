package netto.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@QuarkusTest
public class ItemTest {

    @ParameterizedTest
    @CsvSource({"code, code"})
    public void getAndSetCodeWithValidValue(String input, String expected) {
        Item item = new Item().setCode(input);
        assertThat(item.getCode(), is(expected));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void getAndSetCodeWithInvalidValue(String input) {
        Item item = new Item().setCode(input);
        assertThat(item.getCode(), is(""));
    }

    @ParameterizedTest
    @CsvSource({"-500.00, -500.00", "0.00, 0.00", "500.00, 500.00"})
    public void getAndSetValueWithValidValue(BigDecimal input, BigDecimal expected) {
        Item item = new Item().setValue(input);
        assertThat(item.getValue(), is(expected));
    }

    @Test
    public void getAndSetValueWithInvalidValue() {
        Item item = new Item().setValue(null);
        assertThat(item.getValue(), is(BigDecimal.ZERO));
    }

}
