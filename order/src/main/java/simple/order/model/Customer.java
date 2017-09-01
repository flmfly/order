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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import simple.config.annotation.AssociateTableColumn;
import simple.config.annotation.AutoFill;
import simple.config.annotation.AutoFillTrigger;
import simple.config.annotation.DataFilter;
import simple.config.annotation.Domain;
import simple.config.annotation.Reference;
import simple.config.annotation.ReferenceType;
import simple.config.annotation.RepresentationField;
import simple.config.annotation.RepresentationFieldType;
import simple.config.annotation.TableColumn;
import simple.config.annotation.Title;
import simple.core.validation.annotation.UniqueKey;
import simple.order.support.OwnerAutoFillHandler;

@Domain
@Entity
@DataFilter(by = "owner", valueProperty = "trader")
@Table(name = "ORDER_CUSTOMER")
@UniqueKey(columnNames = { "owner", "trader" }, message = "该名字的客户已存在！")
@SequenceGenerator(name = "SEQ_ORDER_CUSTOMER", sequenceName = "SEQ_ORDER_CUSTOMER")
@GenericGenerator(name = "idStrategy", strategy = "native", parameters = {
		@Parameter(name = "sequence", value = "SEQ_ORDER_CUSTOMER") })
public class Customer implements Serializable {

	private static final long serialVersionUID = -2222299541763024789L;

	@Id
	@GeneratedValue(generator = "idStrategy")
	@Column(name = "ID")
	@RepresentationField(view = RepresentationFieldType.HIDDEN)
	@TableColumn(title = "ID", type = Number.class, show = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "TRADER_ID")
	@Title("客户")
	@RepresentationField(sort = 30, view = RepresentationFieldType.REFERENCE, isSearchField = true)
	@Reference(id = "id", label = "name", type = ReferenceType.SINGLE_QUERY)
	@AssociateTableColumn(sorts = "30", titles = "客户", columns = "name")
	private Trader trader;

	@ManyToOne
	@JoinColumn(name = "OWNER_ID")
	@AutoFill(trigger = AutoFillTrigger.ALWAYS, handler = OwnerAutoFillHandler.class)
	private Trader owner;

}
