package config.item;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;

public class ItemConfigBuilderTest {
    @Test
    public void shouldBuildDefaultObjectWhenValidationIsOK() {
        ItemConfigBuilder uut = new ItemConfigBuilder();

        ItemConfig result = uut.build();

        assertThat(result.getItemsFilename()).isEqualTo("items.csv");
    }

    @Test
    public void shouldBuildCustomObjectWhenValidationIsOK() {
        ItemConfigBuilder uut = new ItemConfigBuilder();

        ItemConfig result = uut.withItemsFilename("test.csv").build();

        assertThat(result.getItemsFilename()).isEqualTo("test.csv");
    }
}