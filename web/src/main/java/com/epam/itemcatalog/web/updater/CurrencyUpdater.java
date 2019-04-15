package com.epam.itemcatalog.web.updater;

import com.epam.itemcatalog.model.dto.CurrencyDto;
import com.epam.itemcatalog.service.CurrencyService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * CurrencyUpdate update currencies {@link com.epam.itemcatalog.model.entity.Currency}
 * in database with scheduled
 */
@Component
public class CurrencyUpdater {

    private static final String API_URL = "http://data.fixer.io/api/latest?access_key=2abd85b5127409fc2855641d69dfed7e";
    private static final String RATES = "rates";

    private CurrencyService currencyService;

    @Autowired
    public CurrencyUpdater(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Update currencies every minute
     * @throws UnirestException
     */
    @Scheduled(cron = "0 * * * * *")
    public void updateCurrencies() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get(API_URL).asJson();
        JSONObject jsonObject = convertResponseToJsonObject(response);

        for (String key : jsonObject.keySet()) {
            CurrencyDto currencyDto = new CurrencyDto(key, jsonObject.getBigDecimal(key));
            currencyService.save(currencyDto);
        }
    }

    private JSONObject convertResponseToJsonObject(HttpResponse<JsonNode> response) {
        JsonNode jsonNode = response.getBody();
        return jsonNode.getObject().getJSONObject(RATES);
    }
}
