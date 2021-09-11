package bhavesh.dangi.cryptoapi.service;

import bhavesh.dangi.cryptoapi.dto.Coin;

import java.util.Set;

public interface CoinService {

    Set<Coin> getCoins(String sort);

    Coin getCoin(String coinCode);
}
