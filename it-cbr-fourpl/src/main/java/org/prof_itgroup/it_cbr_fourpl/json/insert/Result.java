
package org.prof_itgroup.it_cbr_fourpl.json.insert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_id",
    "createdAt",
    "currency",
    "currencyrateDate",
    "currencyrateValue",
    "readACL",
    "removeACL",
    "updateACL",
    "updatedAt"
})
public class Result {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("currencyrateDate")
    private String currencyrateDate;
    @JsonProperty("currencyrateValue")
    private Integer currencyrateValue;
    @JsonProperty("readACL")
    private List<Object> readACL = null;
    @JsonProperty("removeACL")
    private List<Object> removeACL = null;
    @JsonProperty("updateACL")
    private List<Object> updateACL = null;
    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("currencyrateDate")
    public String getCurrencyrateDate() {
        return currencyrateDate;
    }

    @JsonProperty("currencyrateDate")
    public void setCurrencyrateDate(String currencyrateDate) {
        this.currencyrateDate = currencyrateDate;
    }

    @JsonProperty("currencyrateValue")
    public Integer getCurrencyrateValue() {
        return currencyrateValue;
    }

    @JsonProperty("currencyrateValue")
    public void setCurrencyrateValue(Integer currencyrateValue) {
        this.currencyrateValue = currencyrateValue;
    }

    @JsonProperty("readACL")
    public List<Object> getReadACL() {
        return readACL;
    }

    @JsonProperty("readACL")
    public void setReadACL(List<Object> readACL) {
        this.readACL = readACL;
    }

    @JsonProperty("removeACL")
    public List<Object> getRemoveACL() {
        return removeACL;
    }

    @JsonProperty("removeACL")
    public void setRemoveACL(List<Object> removeACL) {
        this.removeACL = removeACL;
    }

    @JsonProperty("updateACL")
    public List<Object> getUpdateACL() {
        return updateACL;
    }

    @JsonProperty("updateACL")
    public void setUpdateACL(List<Object> updateACL) {
        this.updateACL = updateACL;
    }

    @JsonProperty("updatedAt")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updatedAt")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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
