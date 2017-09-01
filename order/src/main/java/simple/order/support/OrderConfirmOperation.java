package simple.order.support;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import simple.base.service.DictItemService;
import simple.config.annotation.support.OperationHandler;
import simple.order.OrderState;
import simple.order.model.OrderConfirm;
import simple.order.model.OrderDetail;
import simple.order.model.SalesOrder;
import simple.order.model.SalesOrderDetail;
import simple.order.model.Trader;

@Component
public class OrderConfirmOperation implements OperationHandler {

	@Autowired
	private DictItemService dictItemService;

	@Override
	public boolean disabled(Object domain) {
		return false;
	}

	@Override
	public OperationResult handle(Map<String, Object> parameters, List<Object> domains) {
		OperationResult or = new OperationResult();
		Set<Long> idSet = new HashSet<Long>();
		Trader owner = null;
		for (Object obj : domains) {
			OrderConfirm oa = (OrderConfirm) obj;
			if (!OrderState.ORDER_STATE_APPROVED_CODE.equals(oa.getState().getCode())) {
				or.setSuccess(false);
				or.addErrorMessage("只能对未确认的采购单进行确认！");
				return or;
			}

			owner = oa.getOwner();
			idSet.add(oa.getOwner().getId());
		}

		if (idSet.size() > 1) {
			or.setSuccess(false);
			or.addErrorMessage("只有同一个买家的采购单才能合并！");
			return or;
		}

		Set<OrderConfirm> orders = new HashSet<OrderConfirm>();

		Set<SalesOrderDetail> details = new HashSet<SalesOrderDetail>();
		SalesOrder so = new SalesOrder();
		for (Object obj : domains) {
			OrderConfirm oa = (OrderConfirm) obj;
			oa.setState(this.dictItemService.getBaseDictItemByDictCodeAndDictItemCode(OrderState.ORDER_STATE_DICT_CODE,
					OrderState.ORDER_STATE_ACCEPTED_CODE));
			orders.add(oa);

			Set<OrderDetail> tmp = oa.getDetails();
			for (OrderDetail od : tmp) {
				SalesOrderDetail sod = new SalesOrderDetail();
				sod.setOrder(so);
				sod.setConfirmQuantity(od.getOrderQuantity());
				sod.setOrderQuantity(od.getOrderQuantity());
				sod.setProductName(od.getProduct().getName());
				details.add(sod);
			}

		}

		SalesOrderNumberDefaultValueHandler sodv = new SalesOrderNumberDefaultValueHandler();

		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		so.setOrderDate(c.getTime());
		so.setOrderNumber(sodv.getNumber());
		so.setOrders(orders);
		so.setOwner(owner);
		so.setBuyer(orders.iterator().next().getOwner());
		so.setState(this.dictItemService.getBaseDictItemByDictCodeAndDictItemCode(OrderState.ORDER_STATE_DICT_CODE,
				OrderState.ORDER_STATE_NEW_CODE));

		so.setDetails(details);

		this.dictItemService.getHibernateBaseDAO().save(so);

		or.setSuccess(true);

		for (Object obj : domains) {
			OrderConfirm oa = (OrderConfirm) obj;
			oa.setSalesOrder(so);
			this.dictItemService.getHibernateBaseDAO().save(oa);
		}
		return or;
	}

}
