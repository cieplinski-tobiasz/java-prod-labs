package app.config;

import lombok.Builder;
import lombok.NonNull;
import utils.ranges.Range;

import java.time.ZonedDateTime;

@Builder
public final class RangesDTO {
    @NonNull
    public final Range<Integer> customerIdRange;
    @NonNull
    public final Range<Integer> purchasesCountRange;
    @NonNull
    public final Range<Integer> quantityRange;
    @NonNull
    public final Range<ZonedDateTime> timestampRange;
}
