package simple.order.support;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import simple.base.Globe;
import simple.base.model.BaseUser;
import simple.config.annotation.support.AutoFillHandler;

public class OwnerAutoFillHandler implements AutoFillHandler {

	public void handle(Field field, Object target, HttpServletRequest request)
			throws IllegalArgumentException, IllegalAccessException {
		BaseUser user = (BaseUser) request.getSession().getAttribute(Globe.SESSION_USER_KEY);
		field.setAccessible(true);
		field.set(target, user.getTrader());
	}

}
