package simple.order.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.AutoFill;
import simple.config.annotation.AutoFillTrigger;
import simple.config.annotation.BooleanValue;
import simple.config.annotation.DataFilter;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.Operation;
import simple.config.annotation.OperationTarget;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.core.jpa.convert.BooleanToStringConverter;
import simple.order.support.BillPaidConfirmOperation;
import simple.order.support.OwnerAutoFillHandler;

@Domain(value = "应付账款")
@DataFilter(by = "owner", valueProperty = "trader")
@Entity
@Table(name = "ORDER_BILL")
@Operation.List({
		@Operation(code = "refresh", iconStyle = "fa fa-refresh", handler = BillPaidConfirmOperation.class, multi = true, name = "账单到账", target = OperationTarget.ALL) })
@SequenceGenerator(name = "SEQ_ORDER_DELIVERY_DETAIL", sequenceName = "SEQ_ORDER_DELIVERY_DETAIL")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_DELIVERY_DETAIL") })
public class BillPaidConfirm implements Serializable {

	private static final long serialVersionUID = 4364629901095352378L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "ID", type = Number.class, show = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "OWNER_ID")
	@AutoFill(trigger = AutoFillTrigger.ALWAYS, handler = OwnerAutoFillHandler.class)
	private Trader owner;

	@ManyToOne
	@JoinColumn(name = "BUYER_ID")
	private Trader buyer;

	@Column(name = "PRODUCT_NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "产品名称", isSearchField = true, disable = true)
	@TableColumn(title = "产品名称")
	@Length(max = DataLength.NAME_LENGTH)
	private String productName;

	@Column(name = "BILL_AMOUNT", columnDefinition = "NUMERIC(12,2)")
	@Title("账单金额")
	@RepresentationField(sort = 60)
	@DecimalMax("999999999999")
	private Double billAmount;

	@Column(name = "SHIP_QUANTITY", columnDefinition = "NUMERIC(12,0)")
	@Title("实发数量")
	@RepresentationField(sort = 60)
	@DecimalMax("999999999999")
	private Long shipQuantity;

	@Column(name = "IQC_QUANTITY", columnDefinition = "NUMERIC(12,0)")
	@Title("收货数量")
	@RepresentationField(sort = 60)
	@DecimalMax("999999999999")
	private Long iqcQuantity;

	@ManyToOne
	@JoinColumn(name = "SALES_ORDER_DETAIL_ID")
	@RepresentationField(sort = 30, title = "订单", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "orderNumber")
	@AssociateTableColumn(sorts = "30", titles = "订单", columns = "orderNumber")
	private SalesOrderDetail salesOrderDetail;

	@ManyToOne
	@JoinColumn(name = "DELIVERY_ORDER_ID")
	@RepresentationField(sort = 30, title = "发货单", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "orderNumber")
	@AssociateTableColumn(sorts = "30", titles = "发货单", columns = "orderNumber")
	private DeliveryOrder deliveryOrder;

	@Column(name = "BUYER_CONFIRM", columnDefinition = "CHAR(1)")
	@RepresentationField(sort = 80, title = "买家已确认", view = RepresentationFieldType.BOOLEAN, defaultVal = "false")
	@BooleanValue({ "是", "否" })
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean buyerConfirm;

	@Column(name = "PAID", columnDefinition = "CHAR(1)")
	@RepresentationField(sort = 80, title = "买家已付款", view = RepresentationFieldType.BOOLEAN, defaultVal = "false")
	@BooleanValue({ "是", "否" })
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean paid;

	@Column(name = "PAID_CONFIRM", columnDefinition = "CHAR(1)")
	@RepresentationField(sort = 80, title = "确认付款", view = RepresentationFieldType.BOOLEAN, defaultVal = "false")
	@BooleanValue({ "是", "否" })
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean paidConfirm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getShipQuantity() {
		return shipQuantity;
	}

	public void setShipQuantity(Long shipQuantity) {
		this.shipQuantity = shipQuantity;
	}

	public SalesOrderDetail getSalesOrderDetail() {
		return salesOrderDetail;
	}

	public void setSalesOrderDetail(SalesOrderDetail salesOrderDetail) {
		this.salesOrderDetail = salesOrderDetail;
	}

	public Long getIqcQuantity() {
		return iqcQuantity;
	}

	public void setIqcQuantity(Long iqcQuantity) {
		this.iqcQuantity = iqcQuantity;
	}

	public Trader getOwner() {
		return owner;
	}

	public void setOwner(Trader owner) {
		this.owner = owner;
	}

	public Trader getBuyer() {
		return buyer;
	}

	public void setBuyer(Trader buyer) {
		this.buyer = buyer;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public DeliveryOrder getDeliveryOrder() {
		return deliveryOrder;
	}

	public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
	}

	public Boolean getBuyerConfirm() {
		return buyerConfirm;
	}

	public void setBuyerConfirm(Boolean buyerConfirm) {
		this.buyerConfirm = buyerConfirm;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public Boolean getPaidConfirm() {
		return paidConfirm;
	}

	public void setPaidConfirm(Boolean paidConfirm) {
		this.paidConfirm = paidConfirm;
	}

}
