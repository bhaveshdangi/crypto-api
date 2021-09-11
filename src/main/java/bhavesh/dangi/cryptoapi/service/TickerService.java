package bhavesh.dangi.cryptoapi.service;

import bhavesh.dangi.cryptoapi.dto.Ticker;

public interface TickerService {

    Ticker getTicker(String coinCode);
}
