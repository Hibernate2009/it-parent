
package org.prof_itgroup.it_cbr_fourpl.json.login;

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
    "agency",
    "createdAt",
    "deletedAt",
    "email",
    "emailVerified",
    "flowtype",
    "fullName",
    "isBlocked",
    "password",
    "phone",
    "readACL",
    "removeACL",
    "roles",
    "updateACL",
    "updatedAt",
    "username"
})
public class User {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("agency")
    private String agency;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("deletedAt")
    private String deletedAt;
    @JsonProperty("email")
    private String email;
    @JsonProperty("emailVerified")
    private Boolean emailVerified;
    @JsonProperty("flowtype")
    private List<Object> flowtype = null;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("isBlocked")
    private Boolean isBlocked;
    @JsonProperty("password")
    private String password;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("readACL")
    private List<Object> readACL = null;
    @JsonProperty("removeACL")
    private List<Object> removeACL = null;
    @JsonProperty("roles")
    private List<String> roles = null;
    @JsonProperty("updateACL")
    private List<Object> updateACL = null;
    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonProperty("username")
    private String username;
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

    @JsonProperty("agency")
    public String getAgency() {
        return agency;
    }

    @JsonProperty("agency")
    public void setAgency(String agency) {
        this.agency = agency;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("deletedAt")
    public String getDeletedAt() {
        return deletedAt;
    }

    @JsonProperty("deletedAt")
    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("emailVerified")
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    @JsonProperty("emailVerified")
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    @JsonProperty("flowtype")
    public List<Object> getFlowtype() {
        return flowtype;
    }

    @JsonProperty("flowtype")
    public void setFlowtype(List<Object> flowtype) {
        this.flowtype = flowtype;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("isBlocked")
    public Boolean getIsBlocked() {
        return isBlocked;
    }

    @JsonProperty("isBlocked")
    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
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

    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(List<String> roles) {
        this.roles = roles;
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

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
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
