<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.web.alipay.util.*"%>
<%@ page import="com.web.alipay.service.*"%>
<jsp:directive.page import="org.springframework.web.context.WebApplicationContext"/> 
<%
	WebApplicationContext context =  (WebApplicationContext)this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	RechargeService alipayService =(RechargeService)context.getBean("rechargeService");
	//获取支付宝POST过来反馈信息
	Map<String,String> params = new HashMap<String,String>();
	Map requestParams = request.getParameterMap();
	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		String name = (String) iter.next();
		String[] values = (String[]) requestParams.get(name);
		String valueStr = "";
		for (int i = 0; i < values.length; i++) {
			valueStr = (i == values.length - 1) ? valueStr + values[i]
					: valueStr + values[i] + ",";
		}
		params.put(name, valueStr);
	}
	//交易状态
	String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
	
	if(AlipayNotify.verify(params)){//验证成功
		if(trade_status.equals("TRADE_FINISHED")){
		} else if (trade_status.equals("TRADE_SUCCESS")){
				alipayService.updatealipaynotify(params);
		}
		out.println("success");	//请不要修改或删除
	}else{//验证失败
		out.println("fail");
	}
%>
