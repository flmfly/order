package simple.order.support;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import simple.base.service.DictItemService;
import simple.config.annotation.support.OperationHandler;
import simple.order.model.Bill;

@Component
public class BillConfirmOperation implements OperationHandler {

	@Autowired
	private DictItemService dictItemService;

	@Override
	public boolean disabled(Object domain) {
		return false;
	}

	@Override
	public OperationResult handle(Map<String, Object> parameters, List<Object> domains) {
		OperationResult or = new OperationResult();
		for (Object obj : domains) {
			Bill oa = (Bill) obj;
			oa.setBuyerConfirm(true);
			this.dictItemService.getHibernateBaseDAO().save(oa);
		}

		or.setSuccess(true);
		return or;
	}

}
