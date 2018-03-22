package config.purchase;


import org.junit.Test;
import utils.ranges.PositiveIntegerRange;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseConfigBuilderTest {
    @Test
    public void shouldBuildCustomObject() {
        PurchaseConfigBuilder uut = new PurchaseConfigBuilder();

        PurchaseConfig result = uut.withQuantityRange(PositiveIntegerRange.between(1, 7)).build();

        assertThat(result.getQuantityRange().getMinimum()).isEqualTo(1);
        assertThat(result.getQuantityRange().getMaximum()).isEqualTo(7);
    }

    @Test
    public void shouldBuildDefaultObject() {
        PurchaseConfigBuilder uut = new PurchaseConfigBuilder();

        PurchaseConfig result = uut.build();

        assertThat(result.getQuantityRange().getMinimum()).isEqualTo(1);
        assertThat(result.getQuantityRange().getMaximum()).isEqualTo(5);
    }

}