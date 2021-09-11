package bhavesh.dangi.cryptoapi.controller;

import bhavesh.dangi.cryptoapi.dto.Coin;
import bhavesh.dangi.cryptoapi.service.CoinService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "Coin API",
                version = "v1",
                description = "Coin Information API")
)
public class CoinController {

    private final CoinService coinService;

    @Operation(summary = "Retrieve coins.", description = "Retrieve coins by sort order.", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping(value = "/coins", produces = APPLICATION_JSON_VALUE)
    public Set<Coin> getCoins(@RequestParam(value = "sort", defaultValue = "ASC") String sort) {

        log.debug("Get coins controller called with sort order : {}", sort);
        return coinService.getCoins(sort);
    }
}
