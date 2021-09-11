package bhavesh.dangi.cryptoapi.controller;

import bhavesh.dangi.cryptoapi.dto.Coin;
import bhavesh.dangi.cryptoapi.service.CoinService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(MockitoJUnitRunner.class)
public class CoinControllerTest {

    private MockMvc mockMvc;

    private final CoinController coinController;

    private final CoinService coinService;

    public CoinControllerTest() {

        coinService = Mockito.mock(CoinService.class);
        coinController = new CoinController(coinService);
    }

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(coinController).build();
    }

    @Test
    public void shouldReturnCoinsSuccessful() throws Exception {

        Coin coin = Coin.builder()
                .code("BTC")
                .name("Bitcoin")
                .build();
        Set<Coin> coins = new HashSet<>();
        coins.add(coin);
        Mockito.when(coinService.getCoins("ASC")).thenReturn(coins);

        this.mockMvc.perform(get("/v1/coins")
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].code").value(coin.getCode()))
                .andExpect(jsonPath("$[0].name").value(coin.getName()));

    }
}
