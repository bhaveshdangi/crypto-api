package bhavesh.dangi.cryptoapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Ticker {

    private String code;

    private Double price;

    private Long volume;

    @JsonProperty("daily_change")
    private Double dailyChange;

    @JsonProperty("last_updated")
    private Long lastUpdated;
}
