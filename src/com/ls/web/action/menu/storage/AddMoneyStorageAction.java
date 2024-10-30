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
	
		// ��Ǯ�ֿ��б�
	public ActionForward n1(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
			{
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
						
			return mapping.findForward("money_list");
		}
	
	//�����Ǯ
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
				{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
			int uPk = roleInfo.getBasicInfo().getUPk();
			
			request.setAttribute("id", uPk + "");
			
			//�������е�Ǯ
			long partCopper = roleInfo.getBasicInfo().getCopper();
			
			
			//�ֿ����Ǯ
			 WareHouseDao storageDao = new WareHouseDao();
			 WareHouseVO wareHouseVO = storageDao.getWareHouseIdBypPk(pPk+"",Wrap.COPPER);
			 long warehouseMoney = Long.valueOf(wareHouseVO.getUwMoneyNumber()); 
			
			 //�û������Ǯ
			 String copper_num = request.getParameter("copper_num");		//ͭ
			 
			 if(copper_num == null || copper_num == "" || copper_num.equals("")){
				 copper_num = "0";
			 }
			 
			 logger.info("�������е�Ǯ"+partCopper);
			 logger.info("�ֿ����Ǯ"+warehouseMoney);
			
			 //��Ǯ��������
			 long warehouseMoneyLimit = Long.valueOf(wareHouseVO.getUwMoney());
			 StringBuffer resultWml = new StringBuffer("");
			 try{ 
				 
				long input_money = Integer.valueOf(copper_num);
				if(input_money < 0) {
					input_money = 0;
					resultWml.append("�½𾯸���,ˢǮ��һ��Υ����Ϊ��");
					request.setAttribute("resultWml", resultWml.toString());
					return mapping.findForward("input_num");
				}
				
				if(input_money + warehouseMoney > warehouseMoneyLimit){
					resultWml.append("�Բ���Ǯׯ�����ֻ�ɴ��һ��"+GameConfig.getMoneyUnitName()+"��");
					request.setAttribute("resultWml", resultWml.toString());
					return mapping.findForward("input_num");
				}else if(input_money > partCopper){
					resultWml.append("�Բ�����Я���Ľ�Ǯ������");
					request.setAttribute("resultWml", resultWml.toString());
					
					return mapping.findForward("input_num");
				}else {		
					StorageService storageService = new StorageService();
					resultWml.append(storageService.removeMoneyToWare(pPk,input_money)); 
					resultWml.append("<br/>��Ǯׯ�д�Ž�Ǯ:"+MoneyUtil.changeCopperToStr((input_money + warehouseMoney)));
					resultWml.append("<br/>��������Я����Ǯ:"+MoneyUtil.changeCopperToStr((Long.valueOf(partCopper) - input_money)));
					
					request.setAttribute("resultWml", resultWml.toString());
					return mapping.findForward("sussend");
				}
				
			}catch (NumberFormatException e)
			{
				// �����ĸ�ʽ����ȷ
				resultWml.append("��ȷ�����Ǯ����");
				request.setAttribute("resultWml", resultWml.toString());
				logger.info("��ȷ�����Ǯ����");
				return mapping.findForward("input_num");
			}
			
			}
}