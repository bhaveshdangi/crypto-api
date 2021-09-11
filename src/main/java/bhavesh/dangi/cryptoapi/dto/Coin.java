package bhavesh.dangi.cryptoapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Coin implements Comparable<Coin> {

    @JsonIgnore
    private String id;

    private String code;

    private String name;

    @Override
    public int compareTo(Coin coin) {
        return this.getCode().compareTo(coin.getCode());
    }
}
