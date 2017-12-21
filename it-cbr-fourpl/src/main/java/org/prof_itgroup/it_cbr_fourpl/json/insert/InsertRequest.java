
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
    "app",
    "cli",
    "sess",
    "coll",
    "doc"
})
public class InsertRequest {

    @JsonProperty("app")
    private String app;
    @JsonProperty("cli")
    private String cli;
    @JsonProperty("sess")
    private String sess;
    @JsonProperty("coll")
    private String coll;
    @JsonProperty("doc")
    private Doc doc;
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

    @JsonProperty("doc")
    public Doc getDoc() {
        return doc;
    }

    @JsonProperty("doc")
    public void setDoc(Doc doc) {
        this.doc = doc;
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
