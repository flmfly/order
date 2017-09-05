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
import simple.order.model.DeliveryOrder;
import simple.order.model.DeliveryOrderDetail;
import simple.order.model.SalesOrder;
import simple.order.model.SalesOrderDetail;
import simple.order.model.Trader;

@Component
public class DeliveryOperation implements OperationHandler {

	@Autowired
	private DictItemService dictItemService;

	@Override
	public boolean disabled(Object domain) {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OperationResult handle(Map<String, Object> parameters, List<Object> domains) {
		OperationResult or = new OperationResult();

		Set<Long> idSet = new HashSet<Long>();
		Trader owner = null;
		Trader buyer = null;
		for (Object obj : domains) {
			SalesOrderDetail oa = (SalesOrderDetail) obj;

			owner = oa.getOrder().getOwner();
			buyer = oa.getOrder().getBuyer();
			idSet.add(buyer.getId());
		}

		if (idSet.size() > 1) {
			or.setSuccess(false);
			or.addErrorMessage("同时只能向同一个买家发货！");
			return or;
		}

		DeliveryOrderNumberDefaultValueHandler dodv = new DeliveryOrderNumberDefaultValueHandler();

		DeliveryOrder deliveryOrder = new DeliveryOrder();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		deliveryOrder.setDeliveryDate(c.getTime());
		deliveryOrder.setOrderNumber(dodv.getNumber());
		deliveryOrder.setOwner(owner);
		deliveryOrder.setBuyer(buyer);
		deliveryOrder.setLogisticsVendor((String) parameters.get("log_sup"));
		deliveryOrder.setLogisticsNumber((String) parameters.get("log_num"));
		deliveryOrder.setState(this.dictItemService.getBaseDictItemByDictCodeAndDictItemCode(
				OrderState.ORDER_STATE_DICT_CODE, OrderState.ORDER_STATE_SHIPPED_CODE));
		this.dictItemService.getHibernateBaseDAO().save(deliveryOrder);
		Map<String, Object> valMap = (Map<String, Object>) parameters.get("ship_num");

		Set<DeliveryOrderDetail> details = deliveryOrder.getDetails();

		for (Object obj : domains) {
			SalesOrderDetail oa = (SalesOrderDetail) obj;

			DeliveryOrderDetail dod = new DeliveryOrderDetail();
			dod.setConfirmQuantity(oa.getConfirmQuantity());
			dod.setSalesOrderDetail(oa);
			dod.setOrder(deliveryOrder);
			dod.setProductName(oa.getProductName());
			try {
				dod.setShipQuantity(Long.parseLong((String) valMap.get(String.valueOf(oa.getId()))));
			} catch (Exception e) {
				dod.setShipQuantity(oa.getConfirmQuantity());
			}
			this.dictItemService.getHibernateBaseDAO().save(dod);
			details.add(dod);

			oa.getDeliveryOrders().add(dod);
			SalesOrder so = oa.getOrder();
			so.setState(this.dictItemService.getBaseDictItemByDictCodeAndDictItemCode(OrderState.ORDER_STATE_DICT_CODE,
					OrderState.ORDER_STATE_SHIPPED_CODE));
			this.dictItemService.getHibernateBaseDAO().save(so);
			this.dictItemService.getHibernateBaseDAO().save(oa);

		}

		this.dictItemService.getHibernateBaseDAO().save(deliveryOrder);

		or.setSuccess(true);
		return or;
	}

}
