package bhavesh.dangi.cryptoapi.service;

import bhavesh.dangi.cryptoapi.dto.Coin;
import bhavesh.dangi.cryptoapi.feign.client.AlternativeAPIFeignClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@AllArgsConstructor
public class CoinServiceImpl implements CoinService {

    private final AlternativeAPIFeignClient feignClient;

    private static final SortedSet<Coin> CACHE_ASC = new TreeSet<>();

    private static final SortedSet<Coin> CACHE_DESC = new TreeSet<>(Comparator.comparing(Coin::getCode, Comparator.reverseOrder()));

    private static final Map<String, Coin> CACHE = new HashMap<>();

    public Set<Coin> getCoins(String sort) {

        if ("DESC".equalsIgnoreCase(sort)) {
            return CACHE_DESC;
        } else {
            return CACHE_ASC;
        }
    }

    public Coin getCoin(String coinCode) {

        return CACHE.get(coinCode);
    }

    @PostConstruct
    public void loadCoins() {

        log.info("Loading coins from API on start up.");

        JsonNode data = feignClient.getCoins();

        log.info("Coins retrieved successfully from API.");

        ArrayNode coins = (ArrayNode) data.get("data");

        StreamSupport.stream(coins.spliterator(), false)
                .map(coin -> Coin.builder()
                        .id(coin.findValue("id").asText())
                        .code(coin.findValue("symbol").asText())
                        .name(coin.findValue("name").asText())
                        .build())
                .forEach(coin -> {
                    CACHE_ASC.add(coin);
                    CACHE_DESC.add(coin);
                    CACHE.put(coin.getCode(), coin);
                });
        log.info("Coins loaded. Size : {}", CACHE.size());


    }
}
