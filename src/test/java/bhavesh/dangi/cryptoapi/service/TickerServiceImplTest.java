package bhavesh.dangi.cryptoapi.service;

import bhavesh.dangi.cryptoapi.dto.Coin;
import bhavesh.dangi.cryptoapi.dto.Ticker;
import bhavesh.dangi.cryptoapi.feign.client.AlternativeAPIFeignClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TickerServiceImplTest {

    @InjectMocks
    private TickerServiceImpl tickerService;

    @Mock
    private CoinService coinService;

    @Mock
    private AlternativeAPIFeignClient feignClient;

    @Test
    public void getTickerFromAPI() throws Exception {

        Coin coin = Coin.builder()
                .id("1")
                .code("BTC")
                .name("Bitcoin")
                .build();
        when(coinService.getCoin(coin.getCode())).thenReturn(coin);
        ObjectMapper objectMapper = new ObjectMapper();
        String data = "{\"data\":{\"1\":{\"id\":1,\"name\":\"Bitcoin\",\"symbol\":\"BTC\",\"website_slug\":\"bitcoin\",\"quotes\":{\"USD\":{\"price\": 45480.0000000000000000,\"volume_24h\": 34921072223,\"percentage_change_24h\": 0.0868900623798013}}}}}";
        JsonNode coinsJson = objectMapper.readValue(data, JsonNode.class);

        when(feignClient.getTicker(coin.getId())).thenReturn(coinsJson);

        Ticker ticker = tickerService.getTicker(coin.getCode());

        assertNotNull(ticker);
        assertEquals(ticker.getCode(), coin.getCode());
    }

    @Test
    public void getTickerFromCache() throws Exception {

        Coin coin = Coin.builder()
                .id("1")
                .code("BTC")
                .name("Bitcoin")
                .build();
        when(coinService.getCoin(coin.getCode())).thenReturn(coin);

        Ticker ticker = Ticker.builder()
                .code("BTC")
                .price(45803d)
                .volume(35671509256L)
                .dailyChange(-0.150252277564007d)
                .lastUpdated(System.currentTimeMillis())
                .build();

        Map<String, Ticker> cache = (Map) ReflectionTestUtils.getField(tickerService, "CACHE");
        cache.put(ticker.getCode(), ticker);

        Ticker result = tickerService.getTicker(ticker.getCode());

        assertNotNull(result);
        assertEquals(ticker.getCode(), result.getCode());
    }
}
