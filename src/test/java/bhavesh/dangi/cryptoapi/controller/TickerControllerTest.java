package bhavesh.dangi.cryptoapi.controller;

import bhavesh.dangi.cryptoapi.dto.Ticker;
import bhavesh.dangi.cryptoapi.service.TickerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(MockitoJUnitRunner.class)
public class TickerControllerTest {

    private MockMvc mockMvc;

    private final TickerController tickerController;

    private final TickerService tickerService;

    public TickerControllerTest() {

        tickerService = Mockito.mock(TickerService.class);
        tickerController = new TickerController(tickerService);
    }

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(tickerController).build();
    }

    @Test
    public void shouldReturnCoinsSuccessful() throws Exception {

        Ticker ticker = Ticker.builder()
                .code("BTC")
                .price(45803d)
                .volume(35671509256L)
                .dailyChange(-0.150252277564007d)
                .lastUpdated(System.currentTimeMillis())
                .build();
        when(tickerService.getTicker(ticker.getCode())).thenReturn(ticker);

        this.mockMvc.perform(get("/v1/ticker/" + ticker.getCode())
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value(ticker.getCode()))
                .andExpect(jsonPath("$.price").value(ticker.getPrice()))
                .andExpect(jsonPath("$.volume").value(ticker.getVolume()))
                .andExpect(jsonPath("$.daily_change").value(ticker.getDailyChange()))
                .andExpect(jsonPath("$.last_updated").value(ticker.getLastUpdated()));
    }
}
