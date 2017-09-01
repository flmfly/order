package simple.order.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.base.model.BaseDictItem;
import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.AutoFill;
import simple.config.annotation.AutoFillTrigger;
import simple.config.annotation.DataFilter;
import simple.config.annotation.DataLength;
import simple.config.annotation.DictField;
import simple.config.annotation.Domain;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.SearchField;
import simple.config.annotation.TabView;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.order.support.OwnerAutoFillHandler;

@Domain(defaultSort = "-orderDate", value = "订单")
@DataFilter(by = "owner", valueProperty = "trader")
@Entity
@Table(name = "ORDER_SALES")
@SequenceGenerator(name = "SEQ_ORDER_SALES", sequenceName = "SEQ_ORDER_SALES")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_SALES") })
public class SalesOrder implements Serializable {

	private static final long serialVersionUID = 4364629901095352378L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "ID", type = Number.class, show = false)
	private Long id;

	@Column(name = "ORDER_NUMBER", length = DataLength.LONG_TEXT_LENGTH)
	@Title("订单编号")
	@RepresentationField(sort = 20, isSearchField = true, disable = true)
	@TableColumn()
	@Length(max = DataLength.LONG_TEXT_LENGTH)
	private String orderNumber;

	@Column(name = "ORDER_DATE")
	@Title("订单日期")
	@RepresentationField(sort = 10, view = RepresentationFieldType.DATE)
	@SearchField(isRange = true)
	@TableColumn
	private Date orderDate;

	@Column(name = "DELIVERY_DATE")
	@Title("计划发货日期")
	@RepresentationField(sort = 10, view = RepresentationFieldType.DATE)
	@SearchField(isRange = true)
	@TableColumn
	private Date deliveryDate;

	@ManyToOne
	@JoinColumn(name = "STATE_ID")
	@RepresentationField(sort = 70, title = "状态", view = RepresentationFieldType.SELECT, isSearchField = true)
	@DictField("orderState")
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(titles = "状态", columns = "name", sorts = "50")
	private BaseDictItem state;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	@Cascade({ CascadeType.ALL })
	@RepresentationField(view = RepresentationFieldType.TAB, title = "产品明细")
	@TabView
	private Set<SalesOrderDetail> details = new HashSet<SalesOrderDetail>(0);

	@OneToMany(mappedBy = "salesOrder", fetch = FetchType.LAZY)
	@Cascade({ CascadeType.ALL })
	private Set<OrderConfirm> orders = new HashSet<OrderConfirm>(0);

	@ManyToOne
	@JoinColumn(name = "OWNER_ID")
	@AutoFill(trigger = AutoFillTrigger.ALWAYS, handler = OwnerAutoFillHandler.class)
	private Trader owner;

	@ManyToOne
	@JoinColumn(name = "BUYER_ID")
	private Trader buyer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public BaseDictItem getState() {
		return state;
	}

	public void setState(BaseDictItem state) {
		this.state = state;
	}

	public Set<SalesOrderDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<SalesOrderDetail> details) {
		this.details = details;
	}

	public Set<OrderConfirm> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderConfirm> orders) {
		this.orders = orders;
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

}
