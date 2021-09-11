package bhavesh.dangi.cryptoapi.feign.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "alternative-api-service", url = "${alternative.api.url}")
public interface AlternativeAPIFeignClient {

    @RequestMapping(path = "/v2/listings", method = RequestMethod.GET, produces = "application/json")
    JsonNode getCoins();

    @RequestMapping(path = "/v2/ticker/{id}/", method = RequestMethod.GET, produces = "application/json")
    JsonNode getTicker(@PathVariable("id") String id);
}
