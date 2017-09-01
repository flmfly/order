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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.AutoFill;
import simple.config.annotation.AutoFillTrigger;
import simple.config.annotation.DataFilter;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.Reference;
import simple.config.annotation.ReferenceType;
import simple.config.annotation.ReferenceValue;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.core.validation.annotation.UniqueKey;
import simple.order.support.OwnerAutoFillHandler;

@Domain(defaultSort = "-id", value = "产品")
@Entity
@DataFilter(by = "owner", valueProperty = "trader")
@Table(name = "ORDER_PRODUCT_TO_BUY")
@UniqueKey(columnNames = { "owner", "product" }, message = "产品已存在！")
@SequenceGenerator(name = "SEQ_ORDER_PRODUCT_TO_BUY", sequenceName = "SEQ_ORDER_PRODUCT_TO_BUY")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_PRODUCT_TO_BUY") })
public class ProductToBuy implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "ID", type = Number.class, show = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	@RepresentationField(sort = 30, title = "商品", view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name", type = ReferenceType.SINGLE_QUERY)
	@AssociateTableColumn(sorts = "30,40", titles = "商品,商家", columns = "name,owner.name")
	private ProductView product;

	@Column(name = "NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "名称", isSearchField = true)
	@TableColumn(title = "名称")
	@ReferenceValue(field = "product", path = "name")
	@NotNull(message = "名称不能为空！")
	@Length(max = DataLength.NAME_LENGTH)
	private String name;

	@ManyToOne
	@JoinColumn(name = "OWNER_ID")
	@AutoFill(trigger = AutoFillTrigger.ALWAYS, handler = OwnerAutoFillHandler.class)
	private Trader owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductView getProduct() {
		return product;
	}

	public void setProduct(ProductView product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Trader getOwner() {
		return owner;
	}

	public void setOwner(Trader owner) {
		this.owner = owner;
	}

}
