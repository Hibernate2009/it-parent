
package org.prof_itgroup.directory.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "app",
    "system",
    "enum",
    "doc"
})
public class Directory {

    @JsonProperty("app")
    private String app;
    @JsonProperty("system")
    private String system;
    @JsonProperty("enum")
    private String _enum;
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
    
    @JsonProperty("system")
    public String getSystem() {
        return system;
    }

    @JsonProperty("system")
    public void setSystem(String system) {
        this.system = system;
    }

    @JsonProperty("enum")
    public String getEnum() {
        return _enum;
    }

    @JsonProperty("enum")
    public void setEnum(String _enum) {
        this._enum = _enum;
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
