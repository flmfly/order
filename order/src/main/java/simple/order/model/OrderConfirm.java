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
import simple.config.annotation.DataFilter;
import simple.config.annotation.DataLength;
import simple.config.annotation.DefaultValue;
import simple.config.annotation.DictField;
import simple.config.annotation.Domain;
import simple.config.annotation.Operation;
import simple.config.annotation.OperationTarget;
import simple.config.annotation.Reference;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.SearchField;
import simple.config.annotation.TabView;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.config.annotation.support.DateDefaultValueHandler;
import simple.order.support.OrderConfirmOperation;

@Domain(defaultSort = "-orderDate", value = "订单", defaultFilterHandler = "simple.order.support.OrderConfirmFilterDomainHandler")
@DataFilter(by = "supplier.trader", valueProperty = "trader")
@Entity
@Operation(code = "refresh", iconStyle = "fa fa-refresh", handler = OrderConfirmOperation.class, multi = true, name = "确认订单", target = OperationTarget.ALL)
@Table(name = "ORDER_ORDER")
@SequenceGenerator(name = "SEQ_ORDER_ORDER", sequenceName = "SEQ_ORDER_ORDER")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_ORDER") })
public class OrderConfirm implements Serializable {

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
	@DefaultValue(handler = DateDefaultValueHandler.class)
	@RepresentationField(sort = 10, view = RepresentationFieldType.DATE)
	@SearchField(isRange = true)
	@TableColumn
	private Date orderDate;

	@ManyToOne
	@JoinColumn(name = "SUPPLIER_ID")
	@RepresentationField(sort = 30, title = "供应商", view = RepresentationFieldType.REFERENCE, isSearchField = true, visable = false)
	private Supplier supplier;

	// @Column(name = "RECEIVE_DATE")
	// @Title("收货日期")
	// @RepresentationField(sort = 10, view = RepresentationFieldType.DATE)
	// @SearchField(isRange = true)
	// @TableColumn
	// private Date receiveDate;

	// @Column(name = "DEAL_AMOUNT", columnDefinition = "NUMERIC(12,2)")
	// @Title("订单金额")
	// @RepresentationField(sort = 60)
	// @DecimalMax("9999999999.99")
	// private Double dealAmount;

	// @Column(name = "RECEIVE_QUANTITY", columnDefinition = "NUMERIC(12,0)")
	// @Title("实收数量")
	// @RepresentationField(sort = 60)
	// @DecimalMax("999999999999")
	// private Long receiveQuantity;

	// @Column(name = "IQC")
	// @Title("IQC检测")
	// @RepresentationField(sort = 9999, view = RepresentationFieldType.BOOLEAN)
	// @SearchField
	// @BooleanValue({ "合格", "不合格" })
	// @TableColumn(sort = 9999)
	// private Boolean iqc;
	//
	// @Column(name = "IOC_NUMBER", length = DataLength.CODE_LENGTH)
	// @Title("质检单号")
	// @RepresentationField(sort = 20, isSearchField = true, disable = true)
	// @TableColumn()
	// @Length(max = DataLength.CODE_LENGTH)
	// private String iqcNumber;

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
	private Set<OrderDetail> details = new HashSet<OrderDetail>(0);

	@ManyToOne
	@JoinColumn(name = "SALES_ORDER_ID")
	@RepresentationField(sort = 30, title = "销售订单", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "orderNumber")
	@AssociateTableColumn(sorts = "30", titles = "销售订单", columns = "orderNumber")
	private SalesOrder salesOrder;

	@ManyToOne
	@JoinColumn(name = "OWNER_ID")
	@RepresentationField(sort = 30, title = "买家", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name")
	@AssociateTableColumn(sorts = "30", titles = "买家", columns = "name")
	private Trader owner;

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

	public BaseDictItem getState() {
		return state;
	}

	public void setState(BaseDictItem state) {
		this.state = state;
	}

	public Set<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<OrderDetail> details) {
		this.details = details;
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public Trader getOwner() {
		return owner;
	}

	public void setOwner(Trader owner) {
		this.owner = owner;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
