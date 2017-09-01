package simple.order.support;

import javax.servlet.http.HttpServletRequest;

import simple.config.annotation.support.DefaultValueHandler;

public abstract class NumberDefaultValueHandler implements DefaultValueHandler {

	@Override
	public Object handle(Object target, HttpServletRequest request)
			throws IllegalArgumentException, IllegalAccessException {

		return this.getPrefix() + MakeOrderNum.makeOrderNum();
	}

	public abstract String getPrefix();

}
