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
		// �ֿ���Ʒ�б�
		public ActionForward n1(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
		{
			RoleService roleService = new RoleService();
			RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
			int pPk = roleInfo.getBasicInfo().getPPk();
			int uPk = roleInfo.getBasicInfo().getUPk();
			
			WareHouseDao wareHouse = new WareHouseDao();
	
			/** ��ѯ���ݿ��иý�ɫ��û�н�Ǯ�ֿ� */
			int warehouseID = wareHouse.getWareHouseIdBypPk(uPk,pPk,Wrap.COPPER);
			logger.info("storageAction ����ҵĸ����� �ֿ� :"+warehouseID);
			
			request.setAttribute("warehouseID", warehouseID);
			return mapping.findForward("money_list");
		}
		
		//ȡ����Ǯ
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
				 String silver_num = request.getParameter("silver_num");		//��
				 String copper_num = request.getParameter("copper_num");		//ͭ
				 int silever_numbers = 0;
				 int copper_numbers = 0;
				 logger.info("Ϊ��ʽ��ǰ�û������ǮΪsilver_num:"+silver_num+",copper_num="+copper_num);
				 
				 logger.info("�������е�Ǯ"+partCopper);
				 logger.info("�ֿ����Ǯ"+warehouseMoney);
				
				 // int input_money = (int)MoneyUtil.changeSilverAndCopperToCopper(Integer.valueOf(silver_num),Integer.valueOf(copper_num));
				
				 //������Ǯ��������,Ϊһ����������һ����
				 int wrapMoneyLimit = 100000000;
				 StringBuffer resultWml = new StringBuffer("");
				
				 try{
					 if(silver_num == null || silver_num == "" || silver_num.equals("") || silver_num.equals(" ")){
						 silever_numbers = 0;
						 logger.info("��������Ϊ��");
					 }else {
						 logger.info("�������Ӳ�Ϊ��");
						 silever_numbers = Integer.valueOf(silver_num);
					 }

					 if(copper_num == null || copper_num == "" || copper_num.equals("") || copper_num.equals(" ")){
						 copper_numbers = 0;
						 logger.info("������Ϊ��");
					 }else {
						 logger.info("�����Ĳ�Ϊ��");
						 copper_numbers = Integer.valueOf(copper_num);
					 }
					 logger.info("�û������ǮΪsilver_num:"+silever_numbers+",copper_num="+copper_numbers);
					 long input_money = MoneyUtil.changeSilverAndCopperToCopper(silever_numbers,copper_numbers);
					 logger.info("�û������Ǯ"+input_money);
					if(input_money < 0){
						resultWml.append("�½𾯸���,ˢǮ��һ��Υ����Ϊ!");
						request.setAttribute("resultWml", resultWml.toString());
						logger.info("�½𾯸���,ˢǮ��һ��Υ����Ϊ!");
						return mapping.findForward("input_num");
					}
					 
					if((input_money + partCopper)  > wrapMoneyLimit){
						resultWml.append("�Բ��������ֻ��Я����1��������");
						request.setAttribute("resultWml", resultWml.toString());
						logger.info("�Բ��������ֻ��Я����1��������input_money  > warehouseMoneyLimit");
						return mapping.findForward("input_num");
					}else if(input_money > warehouseMoney){
						resultWml.append("�Բ�������ŵ���������");
						request.setAttribute("resultWml", resultWml.toString());
						logger.info("�Բ�������ŵ���������input_money > Long.valueOf(partCopper)");
						
						return mapping.findForward("input_num");
					}else {
						StorageService storageService = new StorageService(); 
						resultWml.append(storageService.removeMoneyToWrap(pPk,input_money));
						resultWml.append("<br/>��Ǯׯ�й������:"+MoneyUtil.changeCopperToStr((warehouseMoney-input_money)));
						resultWml.append("<br/>��������Я����:"+MoneyUtil.changeCopperToStr((partCopper + input_money)));
						
						logger.info(("��������Я������:resultWml"));
						request.setAttribute("resultWml", resultWml.toString());
						return mapping.findForward("sussend");
					}
					
				}catch (NumberFormatException e)
				{
					// �����ĸ�ʽ����ȷ
					resultWml.append("��ȷ������Ʒ����");
					request.setAttribute("resultWml", resultWml.toString());
					logger.info("��ȷ������Ʒ����");
					return mapping.findForward("input_num");
				}
				
				 }
}
