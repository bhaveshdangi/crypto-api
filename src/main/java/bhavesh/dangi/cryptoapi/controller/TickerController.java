package bhavesh.dangi.cryptoapi.controller;

import bhavesh.dangi.cryptoapi.dto.Ticker;
import bhavesh.dangi.cryptoapi.service.TickerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "Ticker API",
                version = "v1",
                description = "Ticker Information API")
)
@SecurityScheme(name = "crypto-api", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class TickerController {

    private final TickerService tickerService;

    @GetMapping("/ticker/{coinCode}")
    public Ticker getTicker(@PathVariable(value = "coinCode") String coinCode) {

        log.debug("Get ticker controller called with coinCode : {}", coinCode);
        return tickerService.getTicker(coinCode);
    }
}
