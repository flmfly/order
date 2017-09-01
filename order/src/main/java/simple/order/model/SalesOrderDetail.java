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
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.Operation;
import simple.config.annotation.OperationParameter;
import simple.config.annotation.OperationTarget;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.StandardOperation;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.order.support.DeliveryOperation;

@Domain(value = "订单明细")
@StandardOperation(delete = false)
@Operation(code = "refresh", iconStyle = "fa fa-refresh", handler = DeliveryOperation.class, multi = true, name = "确认订单", target = OperationTarget.ALL, parameters = {
		@OperationParameter(title = "物流商", code = "log_sup"), @OperationParameter(title = "物流号码", code = "log_num"),
		@OperationParameter(title = "发货数量", code = "ship_num", multi = true, multiViewProperty = "productName") })
@Entity
@Table(name = "ORDER_SALES_DETAIL")
@SequenceGenerator(name = "SEQ_ORDER_SALES_DETAIL", sequenceName = "SEQ_ORDER_SALES_DETAIL")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_SALES_DETAIL") })
public class SalesOrderDetail implements Serializable {

	private static final long serialVersionUID = 4364629901095352378L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "ID", type = Number.class, show = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	@RepresentationField(sort = 30, title = "订单", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "orderNumber")
	@AssociateTableColumn(sorts = "30", titles = "订单", columns = "orderNumber")
	private SalesOrder order;

	@Column(name = "PRODUCT_NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "产品名称", isSearchField = true, disable = true)
	@TableColumn(title = "产品名称")
	@Length(max = DataLength.NAME_LENGTH)
	private String productName;

	@Column(name = "ORDER_QUANTITY", columnDefinition = "NUMERIC(12,0)")
	@Title("订单数量")
	@RepresentationField(sort = 60, disable = true)
	@TableColumn
	@DecimalMax("999999999999")
	private Long orderQuantity;

	@Column(name = "CONFIRM_QUANTITY", columnDefinition = "NUMERIC(12,0)")
	@Title("送货数量")
	@RepresentationField(sort = 60)
	@TableColumn
	@DecimalMax("999999999999")
	private Long confirmQuantity;

	@ManyToOne
	@JoinColumn(name = "DELIVERY_ORDER_ID")
	@RepresentationField(sort = 30, title = "发货单", view = RepresentationFieldType.REFERENCE, isSearchField = true, visable = false)
	@Reference(id = "id", label = "orderNumber")
	@AssociateTableColumn(sorts = "30", titles = "发货单", columns = "orderNumber")
	private DeliveryOrder deliveryOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SalesOrder getOrder() {
		return order;
	}

	public void setOrder(SalesOrder order) {
		this.order = order;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public Long getConfirmQuantity() {
		return confirmQuantity;
	}

	public void setConfirmQuantity(Long confirmQuantity) {
		this.confirmQuantity = confirmQuantity;
	}

}
