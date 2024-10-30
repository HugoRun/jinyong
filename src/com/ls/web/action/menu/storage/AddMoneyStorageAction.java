package com.ls.web.action.menu.storage;

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
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Wrap;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.storage.StorageService;

public class AddMoneyStorageAction extends DispatchAction{

		Logger logger =  Logger.getLogger("log.action");
	
		// 金钱仓库列表
	public ActionForward n1(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
			{
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
						
			return mapping.findForward("money_list");
		}
	
	//储存金钱
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
			 String copper_num = request.getParameter("copper_num");		//铜
			 
			 if(copper_num == null || copper_num == "" || copper_num.equals("")){
				 copper_num = "0";
			 }
			 
			 logger.info("包裹里有的钱"+partCopper);
			 logger.info("仓库里的钱"+warehouseMoney);
			
			 //金钱存入上限
			 long warehouseMoneyLimit = Long.valueOf(wareHouseVO.getUwMoney());
			 StringBuffer resultWml = new StringBuffer("");
			 try{ 
				 
				long input_money = Integer.valueOf(copper_num);
				if(input_money < 0) {
					input_money = 0;
					resultWml.append("新金警告您,刷钱是一件违法行为！");
					request.setAttribute("resultWml", resultWml.toString());
					return mapping.findForward("input_num");
				}
				
				if(input_money + warehouseMoney > warehouseMoneyLimit){
					resultWml.append("对不起，钱庄中最多只可存放一亿"+GameConfig.getMoneyUnitName()+"！");
					request.setAttribute("resultWml", resultWml.toString());
					return mapping.findForward("input_num");
				}else if(input_money > partCopper){
					resultWml.append("对不起，您携带的金钱不够！");
					request.setAttribute("resultWml", resultWml.toString());
					
					return mapping.findForward("input_num");
				}else {		
					StorageService storageService = new StorageService();
					resultWml.append(storageService.removeMoneyToWare(pPk,input_money)); 
					resultWml.append("<br/>您钱庄中存放金钱:"+MoneyUtil.changeCopperToStr((input_money + warehouseMoney)));
					resultWml.append("<br/>您包裹中携带金钱:"+MoneyUtil.changeCopperToStr((Long.valueOf(partCopper) - input_money)));
					
					request.setAttribute("resultWml", resultWml.toString());
					return mapping.findForward("sussend");
				}
				
			}catch (NumberFormatException e)
			{
				// 数量的格式不正确
				resultWml.append("正确输入金钱数量");
				request.setAttribute("resultWml", resultWml.toString());
				logger.info("正确输入金钱数量");
				return mapping.findForward("input_num");
			}
			
			}
}