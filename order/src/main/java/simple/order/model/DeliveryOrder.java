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

@Domain(defaultSort = "-deliveryDate", value = "发货单")
@DataFilter(by = "owner", valueProperty = "trader")
@Entity
@Table(name = "ORDER_DELIVERY")
@SequenceGenerator(name = "SEQ_ORDER_DELIVERY", sequenceName = "SEQ_ORDER_DELIVERY")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_DELIVERY") })
public class DeliveryOrder implements Serializable {

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

	@Column(name = "DELIVERY_DATE")
	@Title("发货日期")
	@RepresentationField(sort = 10, view = RepresentationFieldType.DATE)
	@SearchField(isRange = true)
	@TableColumn
	private Date deliveryDate;

	@Column(name = "LOGISTICS_VENDOR", length = DataLength.LONG_TEXT_LENGTH)
	@Title("物流商")
	@RepresentationField(sort = 20, isSearchField = true, disable = true)
	@TableColumn()
	@Length(max = DataLength.LONG_TEXT_LENGTH)
	private String logisticsVendor;

	@Column(name = "LOGISTICS_NUMBER", length = DataLength.CODE_LENGTH)
	@Title("物流单号")
	@RepresentationField(sort = 20, isSearchField = true, disable = true)
	@TableColumn()
	@Length(max = DataLength.CODE_LENGTH)
	private String logisticsNumber;

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
	private Set<DeliveryOrderDetail> details = new HashSet<DeliveryOrderDetail>(0);

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

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getLogisticsVendor() {
		return logisticsVendor;
	}

	public void setLogisticsVendor(String logisticsVendor) {
		this.logisticsVendor = logisticsVendor;
	}

	public String getLogisticsNumber() {
		return logisticsNumber;
	}

	public void setLogisticsNumber(String logisticsNumber) {
		this.logisticsNumber = logisticsNumber;
	}

	public BaseDictItem getState() {
		return state;
	}

	public void setState(BaseDictItem state) {
		this.state = state;
	}

	public Set<DeliveryOrderDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<DeliveryOrderDetail> details) {
		this.details = details;
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
