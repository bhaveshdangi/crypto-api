package bhavesh.dangi.cryptoapi.service;

import bhavesh.dangi.cryptoapi.dto.Coin;
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
public class CoinServiceImplTest {

    @InjectMocks
    private CoinServiceImpl coinService;

    @Mock
    private AlternativeAPIFeignClient feignClient;

    @Test
    public void onLoadCoins() throws Exception {

        Coin coin = Coin.builder()
                .id("1")
                .code("BTC")
                .name("Bitcoin")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String data = "{\"data\":[{\"id\":\"1\",\"symbol\":\"BTC\",\"name\":\"Bitcoin\"}]}";
        JsonNode coinsJson = objectMapper.readValue(data, JsonNode.class);

        when(feignClient.getCoins()).thenReturn(coinsJson);

        coinService.loadCoins();

        Map<String, Coin> coins = (Map) ReflectionTestUtils.getField(coinService, "CACHE");

        assertNotNull(coins);
        assertEquals(coins.size(), 1);
        assertEquals(coins.get(coin.getCode()).getName(), coin.getName());
    }
}
