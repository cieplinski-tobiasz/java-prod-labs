package config.transaction;

import org.junit.Test;
import utils.ranges.DateTimeRange;
import utils.ranges.PositiveIntegerRange;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionConfigBuilderTest {
    @Test
    public void shouldBuildCustomTransaction() {
        TransactionConfigBuilder uut = new TransactionConfigBuilder();
        int from = 1;
        int to = 2;
        ZonedDateTime fromTimestamp = ZonedDateTime.now();
        ZonedDateTime toTimestamp = ZonedDateTime.now().plusDays(1);

        TransactionConfig result = uut
                .withCountRange(PositiveIntegerRange.between(from, to))
                .withCustomerIdRange(PositiveIntegerRange.between(from, to))
                .withDateTimeRange(DateTimeRange.between(fromTimestamp, toTimestamp))
                .build();

        assertThat(result.getCustomerIdRange().getMinimum()).isEqualTo(from);
        assertThat(result.getCustomerIdRange().getMaximum()).isEqualTo(to);
        assertThat(result.getPurchasesCountRange().getMinimum()).isEqualTo(from);
        assertThat(result.getPurchasesCountRange().getMaximum()).isEqualTo(to);
        assertThat(result.getDateTimeRange().getMinimum()).isEqualTo(fromTimestamp);
        assertThat(result.getDateTimeRange().getMaximum()).isEqualTo(toTimestamp);
    }

    @Test
    public void shouldBuildDefaultTransaction() {
        TransactionConfigBuilder uut = new TransactionConfigBuilder();

        TransactionConfig result = uut.build();

        assertThat(result.getCustomerIdRange().getMinimum()).isEqualTo(1);
        assertThat(result.getCustomerIdRange().getMaximum()).isEqualTo(20);
        assertThat(result.getPurchasesCountRange().getMinimum()).isEqualTo(1);
        assertThat(result.getPurchasesCountRange().getMaximum()).isEqualTo(5);
        assertThat(result.getDateTimeRange().getMinimum()).isEqualTo(ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0));
        assertThat(result.getDateTimeRange().getMaximum()).isEqualTo(ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(0));
    }

}