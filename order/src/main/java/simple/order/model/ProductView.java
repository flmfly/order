package simple.order.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import simple.config.annotation.AutoFill;
import simple.config.annotation.AutoFillTrigger;
import simple.config.annotation.DataLength;
import simple.config.annotation.Domain;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.config.annotation.TreeInfo;
import simple.core.validation.annotation.UniqueKey;
import simple.order.support.OwnerAutoFillHandler;

@Domain(defaultSort = "-id", value = "产品")
@Entity
@Table(name = "ORDER_PRODUCT")
@UniqueKey(columnNames = { "owner", "name" }, message = "产品已存在！")
@TreeInfo(id = "id", label = "name", pid = "parent.id")
@SequenceGenerator(name = "SEQ_ORDER_PRODUCT", sequenceName = "SEQ_ORDER_PRODUCT")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_PRODUCT") })
public class ProductView implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "ID", type = Number.class, show = false)
	private Long id;

	@Column(name = "CODE", length = DataLength.CODE_LENGTH)
	@RepresentationField(sort = 10, title = "编码", isSearchField = true)
	@TableColumn(title = "编码")
	@Length(max = DataLength.CODE_LENGTH)
	private String code;

	@Column(name = "NAME", length = DataLength.NAME_LENGTH)
	@RepresentationField(sort = 20, title = "名称", isSearchField = true)
	@TableColumn(title = "名称")
	@NotNull(message = "名称不能为空！")
	@Length(max = DataLength.NAME_LENGTH)
	private String name;

	@Column(name = "INTRO")
	@Title("商品介绍")
	@Lob
	@RepresentationField(view = RepresentationFieldType.HTML_EDITOR, sort = 60)
	private String intro;
	//
	// @Column(name = "PRICE", columnDefinition = "NUMERIC(12,2)")
	// @Title("商品价格")
	// @RepresentationField(sort = 60)
	// @NotNull(message = "商品价格不能为空！")
	// @DecimalMax("9999999999.99")
	// private Double price;

	@Column(name = "STOCK", columnDefinition = "NUMERIC(12,0)")
	@Title("商品库存")
	@RepresentationField(sort = 60)
	@DecimalMax("999999999999")
	private Long stock;

	@ManyToOne
	@JoinColumn(name = "OWNER_ID")
	@AutoFill(trigger = AutoFillTrigger.ALWAYS, handler = OwnerAutoFillHandler.class)
	private Trader owner;
}
