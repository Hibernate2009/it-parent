
package org.prof_itgroup.json.order;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_id",
    "CLMstatus",
    "compensatedByInsurance",
    "createdAt",
    "updateACL",
    "orderDate",
    "removeACL",
    "cargoInvoice",
    "readACL",
    "orderNumber",
    "updatedAt",
    "notes",
    "senderId",
    "typeId",
    "id",
    "client",
    "orderPrice",
    "orderWeight",
    "orderVolume",
    "consignee",
    "clientNameEn",
    "clientNameRu",
    "consigneeNameEn",
    "consigneeNameRu",
    "employe",
    "employeName",
    "flowtype",
    "import",
    "model",
    "modelNameEn",
    "modelNameRu",
    "orderNo",
    "sender",
    "supplier",
    "supplierNameEn",
    "senderNameRu",
    "flowtypeName",
    "dtLoss",
    "orderDescription",
    "orderDescription1",
    "orderDescription2",
    "orderLdm",
    "orderParcels",
    "orderPlanDateFrom",
    "orderPlanDateTo",
    "orderPriority",
    "orderSono",
    "orderType",
    "readyTime",
    "senderNameEn",
    "supplierNameRu",
    "truck",
    "truckName"
})
public class Doc {

    @JsonProperty("_id")
    private String _id;
    @JsonProperty("CLMstatus")
    private Integer cLMstatus;
    @JsonProperty("compensatedByInsurance")
    private Integer compensatedByInsurance;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("updateACL")
    private List<Object> updateACL = null;
    @JsonProperty("orderDate")
    private String orderDate;
    @JsonProperty("removeACL")
    private List<Object> removeACL = null;
    @JsonProperty("cargoInvoice")
    private String cargoInvoice;
    @JsonProperty("readACL")
    private List<Object> readACL = null;
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("senderId")
    private Integer senderId;
    @JsonProperty("typeId")
    private Integer typeId;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("client")
    private Integer client;
    @JsonProperty("orderPrice")
    private Integer orderPrice;
    @JsonProperty("orderWeight")
    private Integer orderWeight;
    @JsonProperty("orderVolume")
    private Integer orderVolume;
    @JsonProperty("consignee")
    private Integer consignee;
    @JsonProperty("clientNameEn")
    private String clientNameEn;
    @JsonProperty("clientNameRu")
    private String clientNameRu;
    @JsonProperty("consigneeNameEn")
    private String consigneeNameEn;
    @JsonProperty("consigneeNameRu")
    private String consigneeNameRu;
    @JsonProperty("employe")
    private String employe;
    @JsonProperty("employeName")
    private String employeName;
    @JsonProperty("flowtype")
    private Integer flowtype;
    @JsonProperty("import")
    private String _import;
    @JsonProperty("model")
    private String model;
    @JsonProperty("modelNameEn")
    private String modelNameEn;
    @JsonProperty("modelNameRu")
    private String modelNameRu;
    @JsonProperty("orderNo")
    private String orderNo;
    @JsonProperty("sender")
    private String sender;
    @JsonProperty("supplier")
    private String supplier;
    @JsonProperty("supplierNameEn")
    private String supplierNameEn;
    @JsonProperty("senderNameRu")
    private String senderNameRu;
    @JsonProperty("flowtypeName")
    private String flowtypeName;
    @JsonProperty("dtLoss")
    private String dtLoss;
    @JsonProperty("orderDescription")
    private String orderDescription;
    @JsonProperty("orderDescription1")
    private String orderDescription1;
    @JsonProperty("orderDescription2")
    private String orderDescription2;
    @JsonProperty("orderLdm")
    private Integer orderLdm;
    @JsonProperty("orderParcels")
    private Integer orderParcels;
    @JsonProperty("orderPlanDateFrom")
    private String orderPlanDateFrom;
    @JsonProperty("orderPlanDateTo")
    private String orderPlanDateTo;
    @JsonProperty("orderPriority")
    private String orderPriority;
    @JsonProperty("orderSono")
    private String orderSono;
    @JsonProperty("orderType")
    private Integer orderType;
    @JsonProperty("readyTime")
    private String readyTime;
    @JsonProperty("senderNameEn")
    private String senderNameEn;
    @JsonProperty("supplierNameRu")
    private String supplierNameRu;
    @JsonProperty("truck")
    private Integer truck;
    @JsonProperty("truckName")
    private String truckName;

    @JsonProperty("_id")
    public String get_id() {
        return _id;
    }

    @JsonProperty("_id")
    public void setId1(String _id) {
        this._id = _id;
    }

    @JsonProperty("CLMstatus")
    public Integer getCLMstatus() {
        return cLMstatus;
    }

    @JsonProperty("CLMstatus")
    public void setCLMstatus(Integer cLMstatus) {
        this.cLMstatus = cLMstatus;
    }

    @JsonProperty("compensatedByInsurance")
    public Integer getCompensatedByInsurance() {
        return compensatedByInsurance;
    }

    @JsonProperty("compensatedByInsurance")
    public void setCompensatedByInsurance(Integer compensatedByInsurance) {
        this.compensatedByInsurance = compensatedByInsurance;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updateACL")
    public List<Object> getUpdateACL() {
        return updateACL;
    }

    @JsonProperty("updateACL")
    public void setUpdateACL(List<Object> updateACL) {
        this.updateACL = updateACL;
    }

    @JsonProperty("orderDate")
    public String getOrderDate() {
        return orderDate;
    }

    @JsonProperty("orderDate")
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @JsonProperty("removeACL")
    public List<Object> getRemoveACL() {
        return removeACL;
    }

    @JsonProperty("removeACL")
    public void setRemoveACL(List<Object> removeACL) {
        this.removeACL = removeACL;
    }

    @JsonProperty("cargoInvoice")
    public String getCargoInvoice() {
        return cargoInvoice;
    }

    @JsonProperty("cargoInvoice")
    public void setCargoInvoice(String cargoInvoice) {
        this.cargoInvoice = cargoInvoice;
    }

    @JsonProperty("readACL")
    public List<Object> getReadACL() {
        return readACL;
    }

    @JsonProperty("readACL")
    public void setReadACL(List<Object> readACL) {
        this.readACL = readACL;
    }

    @JsonProperty("orderNumber")
    public String getOrderNumber() {
        return orderNumber;
    }

    @JsonProperty("orderNumber")
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @JsonProperty("updatedAt")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updatedAt")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonProperty("senderId")
    public Integer getSenderId() {
        return senderId;
    }

    @JsonProperty("senderId")
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    @JsonProperty("typeId")
    public Integer getTypeId() {
        return typeId;
    }

    @JsonProperty("typeId")
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("client")
    public Integer getClient() {
        return client;
    }

    @JsonProperty("client")
    public void setClient(Integer client) {
        this.client = client;
    }

    @JsonProperty("orderPrice")
    public Integer getOrderPrice() {
        return orderPrice;
    }

    @JsonProperty("orderPrice")
    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    @JsonProperty("orderWeight")
    public Integer getOrderWeight() {
        return orderWeight;
    }

    @JsonProperty("orderWeight")
    public void setOrderWeight(Integer orderWeight) {
        this.orderWeight = orderWeight;
    }

    @JsonProperty("orderVolume")
    public Integer getOrderVolume() {
        return orderVolume;
    }

    @JsonProperty("orderVolume")
    public void setOrderVolume(Integer orderVolume) {
        this.orderVolume = orderVolume;
    }

    @JsonProperty("consignee")
    public Integer getConsignee() {
        return consignee;
    }

    @JsonProperty("consignee")
    public void setConsignee(Integer consignee) {
        this.consignee = consignee;
    }

    @JsonProperty("clientNameEn")
    public String getClientNameEn() {
        return clientNameEn;
    }

    @JsonProperty("clientNameEn")
    public void setClientNameEn(String clientNameEn) {
        this.clientNameEn = clientNameEn;
    }

    @JsonProperty("clientNameRu")
    public String getClientNameRu() {
        return clientNameRu;
    }

    @JsonProperty("clientNameRu")
    public void setClientNameRu(String clientNameRu) {
        this.clientNameRu = clientNameRu;
    }

    @JsonProperty("consigneeNameEn")
    public String getConsigneeNameEn() {
        return consigneeNameEn;
    }

    @JsonProperty("consigneeNameEn")
    public void setConsigneeNameEn(String consigneeNameEn) {
        this.consigneeNameEn = consigneeNameEn;
    }

    @JsonProperty("consigneeNameRu")
    public String getConsigneeNameRu() {
        return consigneeNameRu;
    }

    @JsonProperty("consigneeNameRu")
    public void setConsigneeNameRu(String consigneeNameRu) {
        this.consigneeNameRu = consigneeNameRu;
    }

    @JsonProperty("employe")
    public String getEmploye() {
        return employe;
    }

    @JsonProperty("employe")
    public void setEmploye(String employe) {
        this.employe = employe;
    }

    @JsonProperty("employeName")
    public String getEmployeName() {
        return employeName;
    }

    @JsonProperty("employeName")
    public void setEmployeName(String employeName) {
        this.employeName = employeName;
    }

    @JsonProperty("flowtype")
    public Integer getFlowtype() {
        return flowtype;
    }

    @JsonProperty("flowtype")
    public void setFlowtype(Integer flowtype) {
        this.flowtype = flowtype;
    }

    @JsonProperty("import")
    public String getImport() {
        return _import;
    }

    @JsonProperty("import")
    public void setImport(String _import) {
        this._import = _import;
    }

    @JsonProperty("model")
    public String getModel() {
        return model;
    }

    @JsonProperty("model")
    public void setModel(String model) {
        this.model = model;
    }

    @JsonProperty("modelNameEn")
    public String getModelNameEn() {
        return modelNameEn;
    }

    @JsonProperty("modelNameEn")
    public void setModelNameEn(String modelNameEn) {
        this.modelNameEn = modelNameEn;
    }

    @JsonProperty("modelNameRu")
    public String getModelNameRu() {
        return modelNameRu;
    }

    @JsonProperty("modelNameRu")
    public void setModelNameRu(String modelNameRu) {
        this.modelNameRu = modelNameRu;
    }

    @JsonProperty("orderNo")
    public String getOrderNo() {
        return orderNo;
    }

    @JsonProperty("orderNo")
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @JsonProperty("sender")
    public String getSender() {
        return sender;
    }

    @JsonProperty("sender")
    public void setSender(String sender) {
        this.sender = sender;
    }

    @JsonProperty("supplier")
    public String getSupplier() {
        return supplier;
    }

    @JsonProperty("supplier")
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @JsonProperty("supplierNameEn")
    public String getSupplierNameEn() {
        return supplierNameEn;
    }

    @JsonProperty("supplierNameEn")
    public void setSupplierNameEn(String supplierNameEn) {
        this.supplierNameEn = supplierNameEn;
    }

    @JsonProperty("senderNameRu")
    public String getSenderNameRu() {
        return senderNameRu;
    }

    @JsonProperty("senderNameRu")
    public void setSenderNameRu(String senderNameRu) {
        this.senderNameRu = senderNameRu;
    }

    @JsonProperty("flowtypeName")
    public String getFlowtypeName() {
        return flowtypeName;
    }

    @JsonProperty("flowtypeName")
    public void setFlowtypeName(String flowtypeName) {
        this.flowtypeName = flowtypeName;
    }

    @JsonProperty("dtLoss")
    public String getDtLoss() {
        return dtLoss;
    }

    @JsonProperty("dtLoss")
    public void setDtLoss(String dtLoss) {
        this.dtLoss = dtLoss;
    }

    @JsonProperty("orderDescription")
    public String getOrderDescription() {
        return orderDescription;
    }

    @JsonProperty("orderDescription")
    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    @JsonProperty("orderDescription1")
    public String getOrderDescription1() {
        return orderDescription1;
    }

    @JsonProperty("orderDescription1")
    public void setOrderDescription1(String orderDescription1) {
        this.orderDescription1 = orderDescription1;
    }

    @JsonProperty("orderDescription2")
    public String getOrderDescription2() {
        return orderDescription2;
    }

    @JsonProperty("orderDescription2")
    public void setOrderDescription2(String orderDescription2) {
        this.orderDescription2 = orderDescription2;
    }

    @JsonProperty("orderLdm")
    public Integer getOrderLdm() {
        return orderLdm;
    }

    @JsonProperty("orderLdm")
    public void setOrderLdm(Integer orderLdm) {
        this.orderLdm = orderLdm;
    }

    @JsonProperty("orderParcels")
    public Integer getOrderParcels() {
        return orderParcels;
    }

    @JsonProperty("orderParcels")
    public void setOrderParcels(Integer orderParcels) {
        this.orderParcels = orderParcels;
    }

    @JsonProperty("orderPlanDateFrom")
    public String getOrderPlanDateFrom() {
        return orderPlanDateFrom;
    }

    @JsonProperty("orderPlanDateFrom")
    public void setOrderPlanDateFrom(String orderPlanDateFrom) {
        this.orderPlanDateFrom = orderPlanDateFrom;
    }

    @JsonProperty("orderPlanDateTo")
    public String getOrderPlanDateTo() {
        return orderPlanDateTo;
    }

    @JsonProperty("orderPlanDateTo")
    public void setOrderPlanDateTo(String orderPlanDateTo) {
        this.orderPlanDateTo = orderPlanDateTo;
    }

    @JsonProperty("orderPriority")
    public String getOrderPriority() {
        return orderPriority;
    }

    @JsonProperty("orderPriority")
    public void setOrderPriority(String orderPriority) {
        this.orderPriority = orderPriority;
    }

    @JsonProperty("orderSono")
    public String getOrderSono() {
        return orderSono;
    }

    @JsonProperty("orderSono")
    public void setOrderSono(String orderSono) {
        this.orderSono = orderSono;
    }

    @JsonProperty("orderType")
    public Integer getOrderType() {
        return orderType;
    }

    @JsonProperty("orderType")
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @JsonProperty("readyTime")
    public String getReadyTime() {
        return readyTime;
    }

    @JsonProperty("readyTime")
    public void setReadyTime(String readyTime) {
        this.readyTime = readyTime;
    }

    @JsonProperty("senderNameEn")
    public String getSenderNameEn() {
        return senderNameEn;
    }

    @JsonProperty("senderNameEn")
    public void setSenderNameEn(String senderNameEn) {
        this.senderNameEn = senderNameEn;
    }

    @JsonProperty("supplierNameRu")
    public String getSupplierNameRu() {
        return supplierNameRu;
    }

    @JsonProperty("supplierNameRu")
    public void setSupplierNameRu(String supplierNameRu) {
        this.supplierNameRu = supplierNameRu;
    }

    @JsonProperty("truck")
    public Integer getTruck() {
        return truck;
    }

    @JsonProperty("truck")
    public void setTruck(Integer truck) {
        this.truck = truck;
    }

    @JsonProperty("truckName")
    public String getTruckName() {
        return truckName;
    }

    @JsonProperty("truckName")
    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

}
