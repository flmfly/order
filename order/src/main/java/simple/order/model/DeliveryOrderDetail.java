package simple.order.model;

import java.io.Serializable;

import javax.persistence.Column;
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
import simple.config.annotation.BooleanValue;
import simple.config.annotation.DataFilter;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.Operation;
import simple.config.annotation.OperationParameter;
import simple.config.annotation.OperationParameterType;
import simple.config.annotation.OperationTarget;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.StandardOperation;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.order.support.DeliveryConfirmOperation;

@Domain(value = "发货单明细")
@StandardOperation(modify = false, delete = false, imp = false, add = false)
@DataFilter(by = "order.owner", valueProperty = "trader")
@Entity
@Table(name = "ORDER_DELIVERY_DETAIL")
@Operation(code = "refresh", iconStyle = "green fa fa-check", handler = DeliveryConfirmOperation.class, multi = false, name = "确认收货", target = OperationTarget.ALL, parameters = {
		@OperationParameter(type = OperationParameterType.FIELD, code = "iqcNumber"),
		@OperationParameter(type = OperationParameterType.FIELD, code = "iqc"),
		@OperationParameter(code = "receive_num", multi = true, multiViewProperty = "productName") })
@SequenceGenerator(name = "SEQ_ORDER_DELIVERY_DETAIL", sequenceName = "SEQ_ORDER_DELIVERY_DETAIL")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_DELIVERY_DETAIL") })
public class DeliveryOrderDetail implements Serializable {

	private static final long serialVersionUID = 4364629901095352378L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "ID", type = Number.class, show = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	@RepresentationField(sort = 30, title = "发货单", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "orderNumber")
	@AssociateTableColumn(sorts = "30", titles = "发货单", columns = "orderNumber")
	private DeliveryOrder order;

	@Column(name = "PRODUCT_NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "产品名称", isSearchField = true, disable = true)
	@TableColumn(title = "产品名称")
	@Length(max = DataLength.NAME_LENGTH)
	private String productName;

	@Column(name = "CONFIRM_QUANTITY", columnDefinition = "NUMERIC(12,0)")
	@Title("送货数量")
	@RepresentationField(sort = 60)
	@DecimalMax("999999999999")
	private Long confirmQuantity;

	@Column(name = "SHIP_QUANTITY", columnDefinition = "NUMERIC(12,0)")
	@Title("实发数量")
	@RepresentationField(sort = 60)
	@DecimalMax("999999999999")
	@TableColumn
	private Long shipQuantity;

	@Column(name = "IQC_QUANTITY", columnDefinition = "NUMERIC(12,0)")
	@Title("收货数量")
	@RepresentationField(sort = 60)
	@DecimalMax("999999999999")
	@TableColumn
	private Long iqcQuantity;

	@ManyToOne
	@JoinColumn(name = "SALES_ORDER_DETAIL_ID")
	@RepresentationField(sort = 30, title = "订单", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "order.orderNumber")
//	@AssociateTableColumn(sorts = "30", titles = "订单", columns = "order.orderNumber")
	private SalesOrderDetail salesOrderDetail;

	@Column(name = "IQC")
	@Title("IQC检测")
	@RepresentationField(sort = 9999, view = RepresentationFieldType.BOOLEAN)
	@BooleanValue({ "合格", "不合格" })
	@TableColumn(sort = 9999)
	private Boolean iqc;

	@Column(name = "IOC_NUMBER", length = DataLength.CODE_LENGTH)
	@Title("质检单号")
	@RepresentationField(sort = 20, isSearchField = true)
	@TableColumn()
	@Length(max = DataLength.CODE_LENGTH)
	private String iqcNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeliveryOrder getOrder() {
		return order;
	}

	public void setOrder(DeliveryOrder order) {
		this.order = order;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getConfirmQuantity() {
		return confirmQuantity;
	}

	public void setConfirmQuantity(Long confirmQuantity) {
		this.confirmQuantity = confirmQuantity;
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

	public Boolean getIqc() {
		return iqc;
	}

	public void setIqc(Boolean iqc) {
		this.iqc = iqc;
	}

	public String getIqcNumber() {
		return iqcNumber;
	}

	public void setIqcNumber(String iqcNumber) {
		this.iqcNumber = iqcNumber;
	}

	public Long getIqcQuantity() {
		return iqcQuantity;
	}

	public void setIqcQuantity(Long iqcQuantity) {
		this.iqcQuantity = iqcQuantity;
	}

}
