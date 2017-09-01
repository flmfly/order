package simple.order.support;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import simple.base.service.DictItemService;
import simple.config.annotation.support.OperationHandler;
import simple.order.OrderState;
import simple.order.model.OrderApprove;

@Component
public class OrderApproveOperation implements OperationHandler {

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
			OrderApprove oa = (OrderApprove) obj;
			if (!OrderState.ORDER_STATE_NEW_CODE.equals(oa.getState().getCode())) {
				or.setSuccess(false);
				or.addErrorMessage("只能对新建的采购单进行审核！");
				return or;
			}
		}

		for (Object obj : domains) {
			OrderApprove oa = (OrderApprove) obj;
			oa.setState(this.dictItemService.getBaseDictItemByDictCodeAndDictItemCode(OrderState.ORDER_STATE_DICT_CODE,
					OrderState.ORDER_STATE_APPROVED_CODE));
			this.dictItemService.getHibernateBaseDAO().save(oa);
		}

		or.setSuccess(true);
		return or;
	}

}
