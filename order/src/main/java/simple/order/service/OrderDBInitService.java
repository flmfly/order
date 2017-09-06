package simple.order.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import simple.base.model.BaseDict;
import simple.base.model.BaseDictItem;
import simple.base.model.BaseMenu;
import simple.base.model.BaseResource;
import simple.core.service.BaseService;
import simple.order.OrderState;

@Service
public class OrderDBInitService implements InitializingBean {

	@Autowired
	private BaseService baseService;

	@Autowired
	@Qualifier("transactionManager")
	private PlatformTransactionManager txManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		TransactionTemplate tmpl = new TransactionTemplate(txManager);
		tmpl.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				updateDB();
			}
		});
	}

	private void updateDB() {

		BaseMenu bm = new BaseMenu();
		bm.setCode("10000");
		bm.setName("主数据管理");
		bm.setIconCss("fa fa-database");
		bm.setSort(1000);
		bm = this.insertIfNotExist(bm, "code", bm.getCode());
		this.saveMenu("order_producttobuy", "商品管理", "/list/product", "10000001", "fa fa-cubes", 100, bm);
		this.saveMenu("order_product", "采购商品管理", "/list/producttobuy", "10000002", "fa fa-cube", 200, bm);
		this.saveMenu("order_supplier", "供应商管理", "/list/supplier", "10000003", "fa fa-user", 300, bm);
		this.saveMenu("order_customer", "客户管理", "/list/customer", "10000004", "fa fa-user", 400, bm);
		this.saveMenu("order_trader", "商家管理", "/list/trader", "10000999", "fa fa-user", 999, bm);

		bm = new BaseMenu();
		bm.setCode("20000");
		bm.setName("采购管理");
		bm.setIconCss("fa fa-truck");
		bm.setSort(2000);
		bm = this.insertIfNotExist(bm, "code", bm.getCode());
		this.saveMenu("order_order", "建议采购单", "/list/order", "20000001", "fa fa-clipboard", 100, bm);
		this.saveMenu("order_orderapprove", "采购单管理", "/list/orderapprove", "20000002", "fa fa-clipboard", 200, bm);
		this.saveMenu("order_orderview", "采购单统计", "/list/orderview", "20000003", "fa fa-bar-chart", 300, bm);

		bm = new BaseMenu();
		bm.setCode("30000");
		bm.setName("销售管理");
		bm.setIconCss("fa fa-flag-o");
		bm.setSort(3000);
		bm = this.insertIfNotExist(bm, "code", bm.getCode());
		this.saveMenu("order_orderconfirm", "销售单确认", "/list/orderconfirm", "30000001", "fa fa-clipboard", 100, bm);
		this.saveMenu("order_salesorder", "销售单管理", "/list/salesorder", "30000002", "fa fa-clipboard", 200, bm);
		this.saveMenu("order_salesorderview", "销售单统计", "/list/salesorderview", "30000003", "fa fa-bar-chart", 300, bm);

		bm = new BaseMenu();
		bm.setCode("40000");
		bm.setName("仓库管理");
		bm.setIconCss("fa fa-cubes");
		bm.setSort(4000);
		bm = this.insertIfNotExist(bm, "code", bm.getCode());
		this.saveMenu("order_salesorderdetail", "发货", "/list/salesorderdetail", "40000001", "fa fa-ship", 100,
				bm);
		this.saveMenu("order_deliveryorder", "发货单管理", "/list/deliveryorder", "40000501", "fa fa-ship", 150, bm);
		this.saveMenu("order_deliveryorderdetail", "收货管理", "/list/deliveryorderdetail", "40000002", "fa fa-handshake-o", 200,
				bm);

		bm = new BaseMenu();
		bm.setCode("50000");
		bm.setName("财务管理");
		bm.setIconCss("fa fa-yen");
		bm.setSort(4000);
		bm = this.insertIfNotExist(bm, "code", bm.getCode());
		this.saveMenu("order_delivery2bill", "确认应收", "/list/delivery2bill", "40000001", "fa fa-check", 100, bm);
		// this.saveMenu("order_deliveryorder", "应收确认", "/list/deliveryorder",
		// "40000002", "fa fa-user-secret", 200, bm);
		this.saveMenu("order_bill", "应付管理", "/list/bill", "40000004", "fa fa-credit-card", 400, bm);
		this.saveMenu("order_billpaidconfirm", "应收管理", "/list/billpaidconfirm", "40000005", "fa fa-thumbs-o-up", 500, bm);
		// this.saveMenu("order_deliveryorder", "清账", "/list/deliveryorder",
		// "40000005", "fa fa-user-secret", 500, bm);

		// bm = new BaseMenu();
		// bm.setCode("50000");
		// bm.setName("财务管理");
		// bm.setIconCss("fa fa-puzzle-piece");
		// bm.setSort(10);
		// bm = this.insertIfNotExist(bm, "code", bm.getCode());
		// this.saveMenu("caas_host", "销售对账单查询", "/list/caashost", "50000001",
		// "fa fa-user-secret", 100, bm);
		// this.saveMenu("caas_client", "销售对账单清帐", "/list/caasclient",
		// "50000002", "fa fa-rocket", 200, bm);
		// this.saveMenu("caas_host", "采购对账单查询", "/list/caashost", "50000003",
		// "fa fa-user-secret", 300, bm);
		// this.saveMenu("caas_client", "采购对账单清帐", "/list/caasclient",
		// "50000004", "fa fa-rocket", 400, bm);
		// this.saveMenu("caas_client", "发票录入", "/list/caasclient", "50000005",
		// "fa fa-rocket", 500, bm);

		// this.saveMenu("base_resource", "资源维护", "/list/baseresource",
		// "001001",
		// "fa fa-clipboard", 10, bm);
		// this.saveMenu("base_menu", "菜单维护", "/tree/basemenu", "001002",
		// "fa fa-list-ul", 20, bm);
		// this.saveMenu("base_dict", "字典维护", "/list/basedict", "001003",
		// "fa fa-sort-alpha-asc", 30, bm);
		// this.saveMenu("base_dict_item", "字典项维护",
		// "/tree/basedictitem/basedict/dict", "001004", "fa fa-bars", 40,
		// bm);
		// this.saveMenu("base_employee", "人员维护", "/list/baseemployee",
		// "001005",
		// "fa fa-male", 50, bm);
		// this.saveMenu("base_org", "组织维护", "/tree/baseorg", "001006",
		// "fa fa-globe", 60, bm);
		// this.saveMenu("base_role", "角色维护", "/list/baserole", "001007",
		// "fa fa-users", 70, bm);
		// this.saveMenu("base_user", "用户维护", "/list/baseuser", "001008",
		// "fa fa-user", 80, bm);
		// BaseDict bd = new BaseDict();
		// bd.setCode("userState");
		// bd.setName("用户状态");
		//
		// bd = this.insertIfNotExist(bd, "code", bd.getCode());
		//
		// BaseDictItem bdi2 = this.saveBaseDictItem("userStateNormal", "激活",
		// null, bd);
		// this.saveBaseDictItem("userStateLocked", "锁定", null, bd);
		//
		// BaseDict bd1 = new BaseDict();
		// bd1.setCode("userType");
		// bd1.setName("用户类型");
		//
		// bd1 = this.insertIfNotExist(bd1, "code", bd1.getCode());
		//
		// BaseDictItem bdi1 = this.saveBaseDictItem("userTypeAdmin", "管理员",
		// null,
		// bd1);
		// this.saveBaseDictItem("userTypeRegist", "注册用户", null, bd1);
		//
		BaseDict smsOperateState = new BaseDict();
		smsOperateState.setCode(OrderState.ORDER_STATE_DICT_CODE);
		smsOperateState.setName("订单状态");
		smsOperateState = this.insertIfNotExist(smsOperateState, "code", smsOperateState.getCode());
		this.saveBaseDictItem(OrderState.ORDER_STATE_NEW_CODE, "新建", null, smsOperateState);
		this.saveBaseDictItem(OrderState.ORDER_STATE_APPROVED_CODE, "审核通过", null, smsOperateState);
		this.saveBaseDictItem(OrderState.ORDER_STATE_ACCEPTED_CODE, "卖家已确认", null, smsOperateState);
		this.saveBaseDictItem(OrderState.ORDER_STATE_SCHEDULED_CODE, "已安排发货", null, smsOperateState);
		this.saveBaseDictItem(OrderState.ORDER_STATE_SHIPPED_CODE, "已发货", null, smsOperateState);
		this.saveBaseDictItem(OrderState.ORDER_STATE_SIGNED_CODE, "已签收", null, smsOperateState);
		this.saveBaseDictItem(OrderState.ORDER_STATE_FINISHED_CODE, "已完成", null, smsOperateState);
		//
		// smsOperateState = new BaseDict();
		// smsOperateState.setCode(CAASExtractResult.STATE_DICT_CODE);
		// smsOperateState.setName("抽取任务状态");
		// smsOperateState = this.insertIfNotExist(smsOperateState, "code",
		// smsOperateState.getCode());
		// this.saveBaseDictItem(CAASExtractResult.STATE_UPLOADED_CODE, "上传成功",
		// null, smsOperateState);
		// this.saveBaseDictItem(CAASExtractResult.STATE_LOADING_CODE, "加载中",
		// null, smsOperateState);
		// this.saveBaseDictItem(CAASExtractResult.STATE_FAILDED_CODE, "加载失败",
		// null, smsOperateState);
		// this.saveBaseDictItem(CAASExtractResult.STATE_FINISHED_CODE, "完成",
		// null, smsOperateState);
		//
		// BaseUser admin = new BaseUser();
		// admin.setAccount("admin");
		// admin.setPassword("cnplhd1@3$");
		// admin.setPassword1("cnplhd1@3$");
		// admin.setName("系统管理员");
		// admin.setType(bdi1);
		// admin.setState(bdi2);
		//
		// this.insertIfNotExist(admin, "account", admin.getAccount());
	}

	private BaseDictItem saveBaseDictItem(String code, String name, BaseDictItem parent, BaseDict dict) {
		BaseDictItem bdi = new BaseDictItem();
		bdi.setCode(code);
		bdi.setName(name);
		bdi.setParent(parent);
		bdi.setDict(dict);
		bdi = this.insertIfNotExist(bdi, "code", bdi.getCode());
		return bdi;
	}

	private void saveMenu(String code, String name, String uri, String mcode, String css, int sort, BaseMenu parent) {
		BaseResource br = new BaseResource();
		br.setCode(code);
		br.setName(name);
		br.setUri(uri);
		br = this.insertIfNotExist(br, "code", br.getCode());

		BaseMenu bm = new BaseMenu();
		bm.setCode(mcode);
		bm.setName(name);
		bm.setIconCss(css);
		bm.setParent(parent);
		bm.setResource(br);
		bm.setSort(sort);
		bm = this.insertIfNotExist(bm, "code", bm.getCode());
	}

	@SuppressWarnings("unchecked")
	private <T> T insertIfNotExist(T t, String param, String val) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq(param, val));
		List<T> result = (List<T>) baseService.find(t.getClass(), criterions);
		if (result.isEmpty()) {
			this.baseService.save(t);
		} else {
			return result.get(0);
		}

		return t;
	}

}
