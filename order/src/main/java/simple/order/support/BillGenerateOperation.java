package simple.order.support;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import simple.base.service.DictItemService;
import simple.config.annotation.support.OperationHandler;
import simple.order.model.Bill;
import simple.order.model.Delivery2Bill;

@Component
public class BillGenerateOperation implements OperationHandler {

	@Autowired
	private DictItemService dictItemService;

	@Override
	public boolean disabled(Object domain) {
		return false;
	}

	@Override
	public OperationResult handle(Map<String, Object> parameters, List<Object> domains) {
		OperationResult or = new OperationResult();
		try {
			for (Object obj : domains) {
				Delivery2Bill oa = (Delivery2Bill) obj;
				Bill bill = new Bill();
				bill.setBillAmount(Double.parseDouble(String.valueOf(parameters.get("amount"))));
				bill.setBuyer(oa.getOrder().getBuyer());
				bill.setOwner(oa.getOrder().getOwner());
				bill.setDeliveryOrder(oa);
				this.dictItemService.getHibernateBaseDAO().save(bill);
			}
		} catch (Exception e) {
			or.setSuccess(false);
			or.addErrorMessage("您的输入有误！");
		}

		or.setSuccess(true);
		return or;
	}

}
