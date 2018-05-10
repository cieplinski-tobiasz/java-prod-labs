package app.config;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import utils.ranges.DateTimeRange;
import utils.ranges.PositiveIntegerRange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class RangesDTOTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private PositiveIntegerRange integerRange;

    @Mock
    private DateTimeRange dateTimeRange;

    @Test
    public void shouldBuilderThrowNullPointerExceptionWhenCustomerIdRangeIsNull() {
        Throwable thrown = catchThrowable(() ->
                RangesDTO.builder().customerIdRange(null).timestampRange(dateTimeRange)
                        .quantityRange(integerRange).purchasesCountRange(integerRange)
                        .build()
        );

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldBuilderThrowNullPointerExceptionWhenTimestampRangeIsNull() {
        Throwable thrown = catchThrowable(() ->
                RangesDTO.builder().customerIdRange(integerRange).timestampRange(null)
                        .quantityRange(integerRange).purchasesCountRange(integerRange)
                        .build()
        );

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldBuilderThrowNullPointerExceptionWhenQuantityRangeIsNull() {
        Throwable thrown = catchThrowable(() ->
                RangesDTO.builder().customerIdRange(integerRange).timestampRange(dateTimeRange)
                        .quantityRange(null).purchasesCountRange(integerRange)
                        .build()
        );

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldBuilderThrowNullPointerExceptionWhenPurchasesCountRangeIsNull() {
        Throwable thrown = catchThrowable(() ->
                RangesDTO.builder().customerIdRange(integerRange).timestampRange(dateTimeRange)
                        .quantityRange(integerRange).purchasesCountRange(null)
                        .build()
        );

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}