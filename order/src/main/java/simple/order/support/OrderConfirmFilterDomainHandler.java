package simple.order.support;

import java.util.HashMap;
import java.util.Map;

import simple.config.annotation.support.DomainHandlerAdapter;
import simple.order.OrderState;

public class OrderConfirmFilterDomainHandler extends DomainHandlerAdapter {

	public Map<String, Object> getFilter() {
		Map<String, Object> rtn = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("notinval", new String[] { OrderState.ORDER_STATE_NEW_CODE });
		rtn.put("state.code", param);
		return rtn;
	}

}
