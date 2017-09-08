package simple.order.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import simple.config.annotation.support.TableColumnHandler;
import simple.core.service.BaseService;
import simple.order.model.DeliveryOrderDetail;
import simple.order.model.SalesOrderDetail;

@Component
public class SalesDeliveryNumberTableColumnHandler implements TableColumnHandler {

	@Autowired
	private BaseService baseService;

	@SuppressWarnings("rawtypes")
	@Override
	public Object handle(Object row) {
		long id = Long.parseLong(String.valueOf(((HashMap) row).get("id")));
		SalesOrderDetail sod = this.baseService.getHibernateBaseDAO().get(SalesOrderDetail.class, id);

		Set<DeliveryOrderDetail> dodSet = sod.getDeliveryOrders();

		List<String> numberList = new ArrayList<String>();

		for (DeliveryOrderDetail dod : dodSet) {
			numberList.add(dod.getOrder().getOrderNumber());
		}

		Collections.sort(numberList);

		return StringUtils.join(numberList, ",");
	}

}
