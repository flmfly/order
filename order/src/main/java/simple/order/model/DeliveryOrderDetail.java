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
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;

@Domain(value = "订单明细")
@Entity
@Table(name = "ORDER_DELIVERY_DETAIL")
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
	@RepresentationField(sort = 30, title = "订单", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "orderNumber")
	@AssociateTableColumn(sorts = "30", titles = "订单", columns = "orderNumber")
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
	private Long orderQuantity;

	@Column(name = "SHIP_QUANTITY", columnDefinition = "NUMERIC(12,0)")
	@Title("实发数量")
	@RepresentationField(sort = 60)
	@DecimalMax("999999999999")
	private Long shipQuantity;

	@ManyToOne
	@JoinColumn(name = "SALES_ORDER_ID")
	@RepresentationField(sort = 30, title = "订单", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "orderNumber")
	@AssociateTableColumn(sorts = "30", titles = "订单", columns = "orderNumber")
	private SalesOrder salesOrder;

}
