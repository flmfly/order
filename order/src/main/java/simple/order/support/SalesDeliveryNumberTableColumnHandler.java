package simple.order.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import simple.config.annotation.support.TableColumnHandler;
import simple.order.model.DeliveryOrderDetail;
import simple.order.model.SalesOrderDetail;

public class SalesDeliveryNumberTableColumnHandler implements TableColumnHandler {

	@Override
	public Object handle(Object row) {
		SalesOrderDetail sod = (SalesOrderDetail) row;

		Set<DeliveryOrderDetail> dodSet = sod.getDeliveryOrders();

		List<String> numberList = new ArrayList<String>();

		for (DeliveryOrderDetail dod : dodSet) {
			numberList.add(dod.getOrder().getOrderNumber());
		}

		Collections.sort(numberList);

		return StringUtils.join(numberList, ",");
	}

}
