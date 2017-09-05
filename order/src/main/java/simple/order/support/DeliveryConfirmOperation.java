package simple.order.support;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import simple.base.service.DictItemService;
import simple.config.annotation.support.OperationHandler;
import simple.order.model.DeliveryOrderDetail;

@Component
public class DeliveryConfirmOperation implements OperationHandler {

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
		Map<String, Object> valMap = (Map<String, Object>) parameters.get("receive_num");
		for (Object obj : domains) {
			DeliveryOrderDetail oa = (DeliveryOrderDetail) obj;
			oa.setIqc((Boolean) parameters.get("ipc"));
			oa.setIqcNumber((String) parameters.get("ipc"));
			try {
				oa.setIqcQuantity(Long.parseLong((String) valMap.get(String.valueOf(oa.getId()))));
			} catch (Exception e) {
				oa.setIqcQuantity(oa.getShipQuantity());
			}
			this.dictItemService.getHibernateBaseDAO().save(oa);
		}

		or.setSuccess(true);
		return or;
	}

}
