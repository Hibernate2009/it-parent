
package org.prof_itgroup.it_cbr_fourpl.json.find;

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
    "app",
    "cli",
    "sess",
    "coll",
    "query",
    "fields",
    "limit"
})
public class FindRequest {

    @JsonProperty("app")
    private String app;
    @JsonProperty("cli")
    private String cli;
    @JsonProperty("sess")
    private String sess;
    @JsonProperty("coll")
    private String coll;
    @JsonProperty("query")
    private Query query;
    @JsonProperty("fields")
    private List<String> fields = null;
    @JsonProperty("limit")
    private Integer limit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("app")
    public String getApp() {
        return app;
    }

    @JsonProperty("app")
    public void setApp(String app) {
        this.app = app;
    }

    @JsonProperty("cli")
    public String getCli() {
        return cli;
    }

    @JsonProperty("cli")
    public void setCli(String cli) {
        this.cli = cli;
    }

    @JsonProperty("sess")
    public String getSess() {
        return sess;
    }

    @JsonProperty("sess")
    public void setSess(String sess) {
        this.sess = sess;
    }

    @JsonProperty("coll")
    public String getColl() {
        return coll;
    }

    @JsonProperty("coll")
    public void setColl(String coll) {
        this.coll = coll;
    }

    @JsonProperty("query")
    public Query getQuery() {
        return query;
    }

    @JsonProperty("query")
    public void setQuery(Query query) {
        this.query = query;
    }

    @JsonProperty("fields")
    public List<String> getFields() {
        return fields;
    }

    @JsonProperty("fields")
    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @JsonProperty("limit")
    public Integer getLimit() {
        return limit;
    }

    @JsonProperty("limit")
    public void setLimit(Integer limit) {
        this.limit = limit;
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
