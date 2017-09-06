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

import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.AutoFill;
import simple.config.annotation.AutoFillTrigger;
import simple.config.annotation.BooleanValue;
import simple.config.annotation.DataFilter;
import simple.config.annotation.Domain;
import simple.config.annotation.Operation;
import simple.config.annotation.OperationTarget;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.core.jpa.convert.BooleanToStringConverter;
import simple.order.support.BillConfirmOperation;
import simple.order.support.BillPaidOperation;
import simple.order.support.OwnerAutoFillHandler;

@Domain(value = "应付账款")
@DataFilter(by = "buyer", valueProperty = "trader")
@Entity
@Table(name = "ORDER_BILL")
@Operation.List({
		@Operation(code = "refresh", iconStyle = "fa fa-refresh", handler = BillConfirmOperation.class, multi = true, name = "账单确认", target = OperationTarget.ALL),
		@Operation(code = "refresh", iconStyle = "fa fa-refresh", handler = BillPaidOperation.class, multi = true, name = "清账", target = OperationTarget.ALL) })

@SequenceGenerator(name = "SEQ_ORDER_BILL", sequenceName = "SEQ_BILL")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_BILL") })
public class Bill implements Serializable {

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

	@Column(name = "BILL_AMOUNT", columnDefinition = "NUMERIC(12,2)")
	@Title("账单金额")
	@RepresentationField(sort = 60)
	@TableColumn(sort=60)
	@DecimalMax("999999999999")
	private Double billAmount;

	@ManyToOne
	@JoinColumn(name = "DELIVERY_ORDER_ID")
	@RepresentationField(sort = 30, title = "发货单", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "orderNumber")
	@AssociateTableColumn(sorts = "30,31,32", titles = "发货单,实发数量,收货数量", columns = "order.orderNumber,shipQuantity,iqcQuantity")
	private Delivery2Bill deliveryOrder;

	@Column(name = "BUYER_CONFIRM", columnDefinition = "CHAR(1)")
	@Title("买家已确认")
	@RepresentationField(sort = 80, title = "买家已确认", view = RepresentationFieldType.BOOLEAN, defaultVal = "false")
	@BooleanValue({ "是", "否" })
	@TableColumn(sort = 60)
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean buyerConfirm;

	@Column(name = "PAID", columnDefinition = "CHAR(1)")
	@Title("买家已付款")
	@RepresentationField(sort = 80, title = "买家已付款", view = RepresentationFieldType.BOOLEAN, defaultVal = "false")
	@BooleanValue({ "是", "否" })
	@TableColumn(sort = 60)
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean paid;

	@Column(name = "PAID_CONFIRM", columnDefinition = "CHAR(1)")
	@Title("确认付款")
	@RepresentationField(sort = 80, title = "确认付款", view = RepresentationFieldType.BOOLEAN, defaultVal = "false")
	@BooleanValue({ "是", "否" })
	@TableColumn(sort = 60)
	@Convert(converter = BooleanToStringConverter.class)
	private Boolean paidConfirm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Delivery2Bill getDeliveryOrder() {
		return deliveryOrder;
	}

	public void setDeliveryOrder(Delivery2Bill deliveryOrder) {
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
