package com.ls.web.action.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.friend.FriendDAO;
import com.ben.vo.friend.FriendVO;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.user.BasicInfo;
import com.ls.web.service.log.LogService;
import com.lw.service.specialprop.SpecialPropService;
import com.web.jieyi.util.Constant;

public class BrotherAction extends BaseAction
{
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int nowPage = Integer.parseInt(request.getParameter("nowPage")==null?"1":request.getParameter("nowPage").trim());
		
		int p_pk = getP_Pk(request);
		List<FriendVO> list = friendService.findCanGetExp(p_pk, 1,(nowPage-1)*Constant.EVERY_PAGE_COUNT,Constant.EVERY_PAGE_COUNT);
		int count = friendService.findCanGetExpCount(p_pk, 1);
		departList(request, list, count, nowPage);
		request.setAttribute("w_type", request.getParameter("w_type")==null?request.getAttribute("w_type"):request.getParameter("w_type"));
		request.setAttribute("propUseEffect", request.getParameter("propUseEffect")==null?request.getAttribute("propUseEffect"):request.getParameter("propUseEffect"));
		request.setAttribute("pg_pk", request.getParameter("pg_pk")==null?request.getAttribute("pg_pk"):request.getParameter("pg_pk"));
		request.setAttribute("goods_id", request.getParameter("goods_id")==null?request.getAttribute("goods_id"):request.getParameter("goods_id"));
		request.setAttribute("goods_type", request.getParameter("goods_type")==null?request.getAttribute("goods_type"):request.getParameter("goods_type"));
		request.setAttribute("page_no", request.getParameter("page_no")==null?request.getAttribute("page_no"):request.getParameter("page_no"));
		return mapping.findForward(BROTHER);
	}
	
//	��ȡ����
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		int f_pk = (request.getParameter("fid")==null?0:Integer.parseInt(request.getParameter("fid").trim()));
//		String name = request.getParameter("fdName");
//		int fdE = (request.getParameter("fdE")==null?0:Integer.parseInt(request.getParameter("fdE").trim()));
		FriendVO fv = friendService.findById(f_pk);
		if(fv!=null){
		friendService.getExp(fv.getFPk());
		request.setAttribute("message", "����ȡ��"+fv.getFdName()+"��"+fv.getExpShare()+"�㾭�飡");
		//���
		LogService logService = new LogService();
		logService.recordExpLog(bi.getPPk(), bi.getName(), bi.getCurExp()+"", fv.getExpShare()+"", "�ֵ���ȡ"+fv.getFdName());
		
		bi.updateAddCurExp(fv.getExpShare());
		
		PlayerPropGroupVO propGroup = null;
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		String pg_pk = request.getParameter("pg_pk");
		if(pg_pk!=null&&!"".equals(pg_pk.trim())){
		propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk.trim()));
		goodsService.removeProps(propGroup, 1);
		}
		}
		request.setAttribute("w_type", request.getParameter("w_type")==null?request.getAttribute("w_type"):request.getParameter("w_type"));
		request.setAttribute("propUseEffect", request.getParameter("propUseEffect")==null?request.getAttribute("propUseEffect"):request.getParameter("propUseEffect"));
		request.setAttribute("pg_pk", request.getParameter("pg_pk")==null?request.getAttribute("pg_pk"):request.getParameter("pg_pk"));
		request.setAttribute("goods_id", request.getParameter("goods_id")==null?request.getAttribute("goods_id"):request.getParameter("goods_id"));
		request.setAttribute("goods_type", request.getParameter("goods_type")==null?request.getAttribute("goods_type"):request.getParameter("goods_type"));
		request.setAttribute("page_no", request.getParameter("page_no")==null?request.getAttribute("page_no"):request.getParameter("page_no"));
		return mapping.findForward(GET_EXP);
	}
	
	//���������
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("w_type", request.getAttribute("w_type"));
		request.setAttribute("propUseEffect", request.getAttribute("propUseEffect"));
		request.setAttribute("pg_pk", request.getAttribute("pg_pk"));
		request.setAttribute("goods_id", request.getAttribute("goods_id"));
		request.setAttribute("goods_type", request.getAttribute("goods_type"));
		request.setAttribute("page_no", request.getAttribute("page_no"));
		BasicInfo bi = getBasicInfo(request);
		List<FriendVO> list = friendService.findCanGetExp(bi.getPPk(), 2, 0, 0);
		if(list==null||list.size()==0){
			request.setAttribute("message", "��û���κξ��������ȡ��");
		}else{
			FriendVO fv = list.get(0);
			request.setAttribute("message", "����ȡ��"+(bi.getSex()==1?"����":"�Ϲ�")+fv.getFdName()+"��"+fv.getExpShare()+"�㾭�飡");
			
			//���
			LogService logService = new LogService();
			logService.recordExpLog(bi.getPPk(), bi.getName(), bi.getCurExp()+"", fv.getExpShare()+"", "������ȡ"+fv.getFdName());
			
			friendService.getExp(fv.getFPk());
			bi.updateAddCurExp(fv.getExpShare());
		}
		return mapping.findForward(FUQI);
	}
	
//	���ӷ�������ֵ����
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		String prop_id = (String)request.getAttribute("goods_id");
		if(prop_id==null||"".equals(prop_id.trim())){
			request.setAttribute("message", "����ʹ�ø���Ʒ");
		}
		PropVO pv = goodsService.getPropInfo(Integer.parseInt(prop_id.trim()));
		if(pv!=null&&pv.getPropOperate1()!=null&&!"".equals(pv.getPropOperate1())){
			//�����۶����ӵ�����˫��
			List<FriendVO> list = friendService.isMerry(bi.getPPk()+"");
			if(list!=null&&list.size()>0){
				FriendVO fv = list.get(0);
				friendService.addLoveDear(fv.getPPk(), fv.getFdPk(),fv.getFdName(),Integer.parseInt(pv.getPropOperate1().trim()),bi.getName());
				request.setAttribute("message", "��ʹ��"+pv.getPropName()+"������˫�����Ӱ�������ֵ"+pv.getPropOperate1().trim()+"��Ŀǰ��İ�������ֵΪ"+(fv.getLove_dear()+Integer.parseInt(pv.getPropOperate1().trim()))+"������������ֵΪ0ʱ������ָ����ʧȥ���á�");
			    if(Integer.parseInt(pv.getPropOperate1().trim())>0){
			    	specoalPropService.getEquipItemSign1(bi.getPPk(), 0,Constant.MERRY_GIFT);
			    	specoalPropService.getEquipItemSign1(fv.getFdPk(), 0,Constant.MERRY_GIFT);
			    	String[] prop = Constant.OTHER_MERRY_GIFT.split(",");
			    	for(int i=0;i<prop.length;i++){
			    		if(prop[i] != null && prop[i].equals("")){
			    			int prop_id_bak = Integer.parseInt(prop[i]);
			    			specoalPropService.getEquipItemSign1(bi.getPPk(), 0,prop_id_bak);
					    	specoalPropService.getEquipItemSign1(fv.getFdPk(), 0,prop_id_bak);
			    		}
			    	}
			    }
			}else{
				request.setAttribute("message", "����û��飬����ʹ�øõ���");
			}
		}else{
			request.setAttribute("message", "�õ��߲���ʹ��");
		}
		request.setAttribute("w_type", request.getAttribute("w_type"));
		request.setAttribute("propUseEffect", request.getAttribute("propUseEffect"));
		request.setAttribute("pg_pk", request.getAttribute("pg_pk"));
		request.setAttribute("goods_id", request.getAttribute("goods_id"));
		request.setAttribute("goods_type", request.getAttribute("goods_type"));
		request.setAttribute("page_no", request.getAttribute("page_no"));
		return mapping.findForward(FUQI);
	}
	
	//�������ܶȵ���
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		request.setAttribute("w_type", request.getParameter("w_type")==null?request.getAttribute("w_type"):request.getParameter("w_type"));
		request.setAttribute("propUseEffect", request.getParameter("propUseEffect")==null?request.getAttribute("propUseEffect"):request.getParameter("propUseEffect"));
		request.setAttribute("pg_pk", request.getParameter("pg_pk")==null?request.getAttribute("pg_pk"):request.getParameter("pg_pk"));
		request.setAttribute("goods_id", request.getParameter("goods_id")==null?request.getAttribute("goods_id"):request.getParameter("goods_id"));
		request.setAttribute("goods_type", request.getParameter("goods_type")==null?request.getAttribute("goods_type"):request.getParameter("goods_type"));
		request.setAttribute("page_no", request.getParameter("page_no")==null?request.getAttribute("page_no"):request.getParameter("page_no"));
		int nowPage = Integer.parseInt(request.getParameter("nowPage")==null?"1":request.getParameter("nowPage").trim());
		List<FriendVO> list = friendService.listfriend(bi.getPPk(),(nowPage-1)*Constant.EVERY_PAGE_COUNT,Constant.EVERY_PAGE_COUNT);
		int count = friendService.getFriendNum(bi.getPPk());
		departList(request, list, count, nowPage);
		return mapping.findForward("addlove");
	}
	
	//�������ܶȵ���
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		request.setAttribute("w_type", request.getParameter("w_type")==null?request.getAttribute("w_type"):request.getParameter("w_type"));
		request.setAttribute("propUseEffect", request.getParameter("propUseEffect")==null?request.getAttribute("propUseEffect"):request.getParameter("propUseEffect"));
		request.setAttribute("pg_pk", request.getParameter("pg_pk")==null?request.getAttribute("pg_pk"):request.getParameter("pg_pk"));
		request.setAttribute("goods_id", request.getParameter("goods_id")==null?request.getAttribute("goods_id"):request.getParameter("goods_id"));
		request.setAttribute("goods_type", request.getParameter("goods_type")==null?request.getAttribute("goods_type"):request.getParameter("goods_type"));
		request.setAttribute("page_no", request.getParameter("page_no")==null?request.getAttribute("page_no"):request.getParameter("page_no"));
		String fd_pk = request.getParameter("fd_pk");
		String fdName  = request.getParameter("fdName");
		String prop_id = request.getParameter("goods_id");
		String pg_pk = request.getParameter("pg_pk");
		if(pg_pk==null||"".equals(pg_pk.trim())||"null".equals(pg_pk.trim())||fd_pk==null||"".equals(fd_pk.trim())||"null".equals(fd_pk.trim())||fdName==null||prop_id==null||"".equals(prop_id.trim())||"null".equals(prop_id.trim())){
			request.setAttribute("message", "�Բ��������²���");
			return mapping.findForward(FUQI);
		}
		boolean b = friendService.whetherfriend(Integer.parseInt(fd_pk.trim()), bi.getPPk()+"");
		if(b){
			request.setAttribute("message", "�Բ���"+fdName+"û�м���Ϊ����");
			return mapping.findForward(FUQI);
		}
		PropVO pv = goodsService.getPropInfo(Integer.parseInt(prop_id.trim()));
		if(pv==null||pv.getPropOperate1()==null||"".equals(pv.getPropOperate1().trim())||Integer.parseInt(pv.getPropOperate1().trim())<=0){
			request.setAttribute("message", "�Բ��𣬸õ��߲�����");
			return mapping.findForward(FUQI);
		}
		PlayerPropGroupVO propGroup = null;
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk.trim()));
		goodsService.removeProps(propGroup, 1);
		friendService.addLove(bi.getPPk(), fd_pk, Integer.parseInt(pv.getPropOperate1().trim()));
		request.setAttribute("message", "����"+fdName+"�����ܶ�������"+pv.getPropOperate1().trim());
		systemInfoService.insertSystemInfoBySystem(Integer.parseInt(fd_pk.trim()), "���ĺ���"+bi.getName()+"ʹ����"+propGroup.getPropName()+",�����������ܶ�������"+pv.getPropOperate1().trim());
		return mapping.findForward(FUQI);
	}
}
