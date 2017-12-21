
package org.prof_itgroup.json.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "app",
    "doc"
})
public class Order {

    @JsonProperty("app")
    private String app;
    @JsonProperty("doc")
    private Doc doc;

    @JsonProperty("app")
    public String getApp() {
        return app;
    }

    @JsonProperty("app")
    public void setApp(String app) {
        this.app = app;
    }

    @JsonProperty("doc")
    public Doc getDoc() {
        return doc;
    }

    @JsonProperty("doc")
    public void setDoc(Doc doc) {
        this.doc = doc;
    }

}
