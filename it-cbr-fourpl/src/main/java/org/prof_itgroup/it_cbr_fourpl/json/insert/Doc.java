
package org.prof_itgroup.it_cbr_fourpl.json.insert;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "currency",
    "currencyrateValue",
    "currencyrateDate"
})
public class Doc {

    @JsonProperty("currency")
    private String currency;
    @JsonProperty("currencyrateValue")
    private Double currencyrateValue;
    @JsonProperty("currencyrateDate")
    private String currencyrateDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("currencyrateValue")
    public Double getCurrencyrateValue() {
        return currencyrateValue;
    }

    @JsonProperty("currencyrateValue")
    public void setCurrencyrateValue(Double currencyrateValue) {
        this.currencyrateValue = currencyrateValue;
    }

    @JsonProperty("currencyrateDate")
    public String getCurrencyrateDate() {
        return currencyrateDate;
    }

    @JsonProperty("currencyrateDate")
    public void setCurrencyrateDate(String currencyrateDate) {
        this.currencyrateDate = currencyrateDate;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
