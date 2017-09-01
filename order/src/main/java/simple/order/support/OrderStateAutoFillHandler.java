package simple.order.support;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import simple.base.service.DictItemService;
import simple.config.annotation.support.AutoFillHandler;
import simple.order.OrderState;

@Component
public class OrderStateAutoFillHandler implements AutoFillHandler {

	@Autowired
	private DictItemService dictItemService;

	public void handle(Field field, Object target, HttpServletRequest request)
			throws IllegalArgumentException, IllegalAccessException {

		field.setAccessible(true);
		field.set(target, this.dictItemService.getBaseDictItemByDictCodeAndDictItemCode(
				OrderState.ORDER_STATE_DICT_CODE, OrderState.ORDER_STATE_NEW_CODE));
	}

}
