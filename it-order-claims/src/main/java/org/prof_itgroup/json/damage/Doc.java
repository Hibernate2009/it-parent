
package org.prof_itgroup.json.damage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "pos_num",
    "declarantId",
    "orderId",
    "VINid",
    "dtLoss",
    "compensatedByInsurance",
    "notes",
    "typeId"
})
public class Doc {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("pos_num")
    private Integer posNum;
    @JsonProperty("declarantId")
    private Integer declarantId;
    @JsonProperty("orderId")
    private Long orderId;
    @JsonProperty("VINid")
    private String vINid;
    @JsonProperty("dtLoss")
    private String dtLoss;
    @JsonProperty("compensatedByInsurance")
    private Double compensatedByInsurance;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("typeId")
    private Integer typeId;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("pos_num")
    public Integer getPosNum() {
        return posNum;
    }

    @JsonProperty("pos_num")
    public void setPosNum(Integer posNum) {
        this.posNum = posNum;
    }

    @JsonProperty("declarantId")
    public Integer getDeclarantId() {
        return declarantId;
    }

    @JsonProperty("declarantId")
    public void setDeclarantId(Integer declarantId) {
        this.declarantId = declarantId;
    }

    @JsonProperty("orderId")
    public Long getOrderId() {
        return orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("VINid")
    public String getVINid() {
        return vINid;
    }

    @JsonProperty("VINid")
    public void setVINid(String vINid) {
        this.vINid = vINid;
    }

    @JsonProperty("dtLoss")
    public String getDtLoss() {
        return dtLoss;
    }

    @JsonProperty("dtLoss")
    public void setDtLoss(String dtLoss) {
        this.dtLoss = dtLoss;
    }

    @JsonProperty("compensatedByInsurance")
    public Double getCompensatedByInsurance() {
        return compensatedByInsurance;
    }

    @JsonProperty("compensatedByInsurance")
    public void setCompensatedByInsurance(Double compensatedByInsurance) {
        this.compensatedByInsurance = compensatedByInsurance;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("typeId")
    public Integer getTypeId() {
        return typeId;
    }

    @JsonProperty("typeId")
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

}
