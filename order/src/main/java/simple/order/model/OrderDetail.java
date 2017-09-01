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

import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.Domain;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;

@Domain(value = "订单明细")
@Entity
@Table(name = "ORDER_ORDER_DETAIL")
@SequenceGenerator(name = "SEQ_ORDER_ORDER_DETAIL", sequenceName = "SEQ_ORDER_ORDER_DETAIL")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_ORDER_DETAIL") })
public class OrderDetail implements Serializable {

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
	private Order order;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	@RepresentationField(sort = 30, title = "产品", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(sorts = "30", titles = "产品", columns = "name")
	private ProductToBuy product;

	@Column(name = "ORDER_QUANTITY", columnDefinition = "NUMERIC(12,0)")
	@Title("订单数量")
	@RepresentationField(sort = 60)
	@DecimalMax("999999999999")
	private Long orderQuantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ProductToBuy getProduct() {
		return product;
	}

	public void setProduct(ProductToBuy product) {
		this.product = product;
	}

	public Long getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

}
