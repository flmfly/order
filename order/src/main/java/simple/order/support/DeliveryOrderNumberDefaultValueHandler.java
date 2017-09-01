package simple.order.support;

public class DeliveryOrderNumberDefaultValueHandler extends NumberDefaultValueHandler {

	public String getPrefix() {
		return "D";
	}

	public String getNumber() {
		try {
			return (String) super.handle(null, null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
