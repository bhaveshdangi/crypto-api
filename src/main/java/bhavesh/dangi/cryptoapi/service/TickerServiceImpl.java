package bhavesh.dangi.cryptoapi.service;

import bhavesh.dangi.cryptoapi.dto.Coin;
import bhavesh.dangi.cryptoapi.dto.Ticker;
import bhavesh.dangi.cryptoapi.exception.InvalidRequestException;
import bhavesh.dangi.cryptoapi.feign.client.AlternativeAPIFeignClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@AllArgsConstructor
public class TickerServiceImpl implements TickerService {

    private final AlternativeAPIFeignClient feignClient;

    private final CoinService coinService;

    private static final Map<String, Ticker> CACHE = new ConcurrentHashMap<>();

    public Ticker getTicker(String coinCode) {

        Coin coin = coinService.getCoin(coinCode);

        if (Objects.isNull(coin)) {
            log.error("Coin not found with coin code : {}", coinCode);
            throw new InvalidRequestException("Coin not found with code : " + coinCode);
        }
        log.debug("Retrieving ticker with coin id : {}", coin.getId());
        Ticker ticker = CACHE.get(coin.getId());

        if (Objects.nonNull(ticker)) {
            long fiveMinutesPastTimeMillis = LocalDateTime.now().minus(Duration.ofMinutes(5)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            // Checking for ticker expiration - if it's 5 minutes elapsed then load new from API
            if (fiveMinutesPastTimeMillis < ticker.getLastUpdated()) {
                log.info("Returning ticker from cache with coin id : {}", coin.getId());
                return ticker;
            }
        }
        log.info("Retrieving ticker from API with coin id : {}", coin.getId());
        JsonNode data = feignClient.getTicker(coin.getId());

        log.debug("Ticker loaded from API with coin id : {}", coin.getId());
        ObjectNode objectNode = (ObjectNode) data.get("data").get(coin.getId());

        ticker = Ticker.builder()
                .code(objectNode.get("symbol").textValue())
                .price(objectNode.path("quotes").path("USD").path("price").asDouble())
                .volume(objectNode.path("quotes").path("USD").path("volume_24h").asLong())
                .dailyChange(objectNode.path("quotes").path("USD").path("percentage_change_24h").asDouble())
                .lastUpdated(System.currentTimeMillis())
                .build();

        CACHE.put(coin.getId(), ticker);
        log.debug("Ticker cached with coin id : {} and returning.", coin.getId());
        return ticker;
    }
}
