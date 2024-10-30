package com.ls.web.action.menu.getStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.dao.storage.WareHouseDao;
import com.ls.ben.vo.storage.WareHouseVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Wrap;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.storage.StorageService;

public class GetMoneyStorageAction extends DispatchAction{


	Logger logger =  Logger.getLogger("log.action");
		// 仓库物品列表
		public ActionForward n1(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			int pPk = roleInfo.getBasicInfo().getPPk();
			int uPk = roleInfo.getBasicInfo().getUPk();
			
			WareHouseDao wareHouse = new WareHouseDao();
	
			/** 查询数据库中该角色有没有金钱仓库 */
			int warehouseID = wareHouse.getWareHouseIdBypPk(uPk,pPk,Wrap.COPPER);
			logger.info("storageAction 中玩家的该类型 仓库 :"+warehouseID);
			
			request.setAttribute("warehouseID", warehouseID);
			return mapping.findForward("money_list");
		}
		
		//取出金钱
		public ActionForward n2(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
					{
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			int pPk = roleInfo.getBasicInfo().getPPk();
			
				int uPk = roleInfo.getBasicInfo().getUPk();
				
				request.setAttribute("id", uPk + "");
				
				//包裹里有的钱	
				long partCopper = roleInfo.getBasicInfo().getCopper();
				
				
				//仓库里的钱
				 WareHouseDao storageDao = new WareHouseDao();
				 WareHouseVO wareHouseVO = storageDao.getWareHouseIdBypPk(pPk+"",Wrap.COPPER);
				 long warehouseMoney = Long.valueOf(wareHouseVO.getUwMoneyNumber());
				
				 //用户输入的钱
				 String silver_num = request.getParameter("silver_num");		//银
				 String copper_num = request.getParameter("copper_num");		//铜
				 int silever_numbers = 0;
				 int copper_numbers = 0;
				 logger.info("为格式化前用户输入的钱为silver_num:"+silver_num+",copper_num="+copper_num);
				 
				 logger.info("包裹里有的钱"+partCopper);
				 logger.info("仓库里的钱"+warehouseMoney);
				
				 // int input_money = (int)MoneyUtil.changeSilverAndCopperToCopper(Integer.valueOf(silver_num),Integer.valueOf(copper_num));
				
				 //包裹金钱存入上限,为一百万银，即一亿文
				 int wrapMoneyLimit = 100000000;
				 StringBuffer resultWml = new StringBuffer("");
				
				 try{
					 if(silver_num == null || silver_num == "" || silver_num.equals("") || silver_num.equals(" ")){
						 silever_numbers = 0;
						 logger.info("输入银子为空");
					 }else {
						 logger.info("输入银子不为空");
						 silever_numbers = Integer.valueOf(silver_num);
					 }

					 if(copper_num == null || copper_num == "" || copper_num.equals("") || copper_num.equals(" ")){
						 copper_numbers = 0;
						 logger.info("输入文为空");
					 }else {
						 logger.info("输入文不为空");
						 copper_numbers = Integer.valueOf(copper_num);
					 }
					 logger.info("用户输入的钱为silver_num:"+silever_numbers+",copper_num="+copper_numbers);
					 long input_money = MoneyUtil.changeSilverAndCopperToCopper(silever_numbers,copper_numbers);
					 logger.info("用户输入的钱"+input_money);
					if(input_money < 0){
						resultWml.append("新金警告您,刷钱是一件违法行为!");
						request.setAttribute("resultWml", resultWml.toString());
						logger.info("新金警告您,刷钱是一件违法行为!");
						return mapping.findForward("input_num");
					}
					 
					if((input_money + partCopper)  > wrapMoneyLimit){
						resultWml.append("对不起，您最多只可携带银1百万银！");
						request.setAttribute("resultWml", resultWml.toString());
						logger.info("对不起，您最多只可携带银1百万银！input_money  > warehouseMoneyLimit");
						return mapping.findForward("input_num");
					}else if(input_money > warehouseMoney){
						resultWml.append("对不起，您存放的银不够！");
						request.setAttribute("resultWml", resultWml.toString());
						logger.info("对不起，您存放的银不够！input_money > Long.valueOf(partCopper)");
						
						return mapping.findForward("input_num");
					}else {
						StorageService storageService = new StorageService(); 
						resultWml.append(storageService.removeMoneyToWrap(pPk,input_money));
						resultWml.append("<br/>您钱庄中共存放银:"+MoneyUtil.changeCopperToStr((warehouseMoney-input_money)));
						resultWml.append("<br/>您包裹还携带银:"+MoneyUtil.changeCopperToStr((partCopper + input_money)));
						
						logger.info(("您包裹还携带银两:resultWml"));
						request.setAttribute("resultWml", resultWml.toString());
						return mapping.findForward("sussend");
					}
					
				}catch (NumberFormatException e)
				{
					// 数量的格式不正确
					resultWml.append("正确输入物品数量");
					request.setAttribute("resultWml", resultWml.toString());
					logger.info("正确输入物品数量");
					return mapping.findForward("input_num");
				}
				
				 }
}
