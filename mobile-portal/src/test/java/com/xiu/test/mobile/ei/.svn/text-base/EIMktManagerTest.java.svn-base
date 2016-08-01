package com.xiu.test.mobile.ei;


import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.sales.common.card.dataobject.CardInputParamDO;
import com.xiu.sales.common.card.dataobject.CardListOutParamDO;
import com.xiu.sales.common.card.dataobject.CardOutParamDO;
import com.xiu.sales.common.card.dointerface.CardService;
import com.xiu.uuc.facade.AcctChangeFacade;
import com.xiu.uuc.facade.AcctItemFacade;
import com.xiu.uuc.facade.dto.AcctChangeDTO;
import com.xiu.uuc.facade.dto.AcctItemDTO;
import com.xiu.uuc.facade.dto.IntegeralRuleDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.VirtualAcctPayDTO;
import com.xiu.uuc.facade.util.Page;

public class EIMktManagerTest {
//	public static String serviceURL = "http://osc.xiu.com/remote/bizOrderQueryServiveClientForHessian";
	public static String serviceURL = "http://mkt.xiu.com/remote/cardService";
	public static String serviceURL2="http://uuc.xiu.com/remote/acctItemFacade";
	public static String serviceURL3="http://uuc.xiu.com/remote/acctChangeFacade";
	
	//虚拟账户积分查询
		@Test
	public void testgetVirtualItemList(){
			System.out.println("测试查询积分接口----");
		AcctItemDTO acctItemDTO = new AcctItemDTO();
		HessianProxyFactory factory = new HessianProxyFactory();
		try{
			AcctItemFacade acctItemFacade= (AcctItemFacade)factory.create(AcctItemFacade.class,serviceURL2);
			acctItemDTO.setUserId(1000079668L);
			Result result = acctItemFacade.getVirtualIntegralInfo(acctItemDTO);
			List<AcctItemDTO> da=(List<AcctItemDTO>)result.getData();
			Long maon=da.get(0).getTotalAmount();
			System.out.println("testBindingUser:" + result.getData());
			System.out.println("积分："+maon);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		//虚拟账户积分列表查询
	@Test
	public void testgetVirtualChangeList(){
		System.out.println("测试查询积分明细接口----");
		AcctChangeDTO acctItemDTO = new AcctChangeDTO();
		HessianProxyFactory factory = new HessianProxyFactory();
		try{
			AcctChangeFacade acctChangeFacade= (AcctChangeFacade)factory.create(AcctChangeFacade.class,serviceURL3);
		//	acctItemDTO.setUserId(2012464294L);1013243419
			acctItemDTO.setUserId(1013243419L);
			acctItemDTO.setAcctTypeCode("03");
			acctItemDTO.setCurrentPage(1);
//			acctItemDTO.setPageSize(10);
			Result rs =acctChangeFacade.getVirtualChangeList(acctItemDTO);
			if(rs.getSuccess().equals("1")){
//				List<AcctChangeDTO> list=(List<AcctChangeDTO>)rs.getData();
//				for(AcctChangeDTO vo:list){
//					System.out.println(vo.getLastIoAmount());
//				}
				Page page= rs.getPage();
				System.out.println("成功："+rs.getData());
				System.out.println(page.getPageSize());
			}else{
				System.out.println("失败："+rs.getErrorMessage());
				System.out.println("失败："+rs.getErrorCode());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//虚拟账户积分充值（积分增加）
	@Test
	public void testaddVirtualAccountIntegral(){
		System.out.println("测试积分充值---");
		HessianProxyFactory factory = new HessianProxyFactory();
		try{
			 AcctItemFacade acctItemFacade= (AcctItemFacade)factory.create(AcctItemFacade.class,serviceURL2);
			AcctItemDTO acctItemDTO = new AcctItemDTO();
			acctItemDTO.setUserId(1000079668L);
			acctItemDTO.setTotalAmount(1L);
			AcctChangeDTO acctChangeDTO = new AcctChangeDTO();
			acctChangeDTO.setRltId(604000161505L);
			acctChangeDTO.setBusiType("013");
			acctChangeDTO.setIoAmount(acctItemDTO.getTotalAmount());
			acctChangeDTO.setIoAmount(1L);
			acctChangeDTO.setRltChannelId("1");
	        acctChangeDTO.setOperId("99999");
	        Map map = new HashMap();
	        map.put("p1", 0);
	        map.put("p2", 0);
	        IntegeralRuleDTO integeralRuleDTO = new IntegeralRuleDTO("004",map);
	        Result result = acctItemFacade.addVirtualAccountIntegral(acctItemDTO, acctChangeDTO, integeralRuleDTO);
	        if(result.getSuccess().equals("1")){
	        	VirtualAcctPayDTO tod=(VirtualAcctPayDTO)result.getData();
	        	System.out.println(tod.getPayTotalMoney());
	        }else{
	        	System.out.println("失败:"+result.getErrorMessage());
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//虚拟账户积分支付（积分减少）
	@Test
	public void testdecVirtualAccountIntegral(){
		System.out.println("测试积分减少---");
		HessianProxyFactory factory = new HessianProxyFactory();
		try{
			AcctItemFacade acctItemFacade= (AcctItemFacade)factory.create(AcctItemFacade.class,serviceURL2);
			AcctItemDTO acctItemDTO = new AcctItemDTO();
			acctItemDTO.setUserId(1000079668L);
			acctItemDTO.setTotalAmount(50L);
			AcctChangeDTO acctChangeDTO = new AcctChangeDTO();
			 acctChangeDTO.setRltId(11111L);
			 acctChangeDTO.setBusiType("05");
			 acctChangeDTO.setIoAmount(acctItemDTO.getTotalAmount());
			 acctChangeDTO.setRltChannelId("1");
			 acctChangeDTO.setOperId("99999");
			 Result rs =acctItemFacade.decVirtualAccountIntegral(acctItemDTO, acctChangeDTO, null);
			 if(rs.getSuccess().equals("1")){
				 VirtualAcctPayDTO dto=(VirtualAcctPayDTO)rs.getData();
				 System.out.println("成功:"+dto.getUnDrawPayMoney());
			 }else{
				 System.out.println("失败:"+rs.getErrorMessage());
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Test
	public void queryCoupos() {
		
		System.out.println("测试查询优惠券...........");
		
		HessianProxyFactory factory = new HessianProxyFactory();  
		try {  
			CardService orderQueryService = (CardService) factory.create(CardService.class, serviceURL);
			// 封装参数
			long userId=2012465541l;
			
			CardInputParamDO paramDo = new CardInputParamDO();
			// 类型
				paramDo.setListValid(4);
			
			// 分页查询
				paramDo.setPageSize(10);
				paramDo.setCurrentPage(1);
			
			paramDo.setUserId(Long.valueOf(userId));
			paramDo.setChannelID(GlobalConstants.COUPON_CHANNEL_ID);
			String terminalUser=null;
			CardOutParamDO outParam= orderQueryService.userCardList(paramDo);
			CardListOutParamDO[] cardListOuts = outParam.getCardLists();
			for (int i = 0; i < cardListOuts.length; i++) {
				System.out.println(cardListOuts[i].getCardId());
			}
			
		} catch (MalformedURLException e) {  
			e.printStackTrace();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		System.out.println("测试 查询优惠券 完成........");
	}
}
