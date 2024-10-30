package com.ls.web.action.menu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.actions.DispatchAction;

import com.ben.dao.honour.RoleTitleDAO;
import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.guaji.service.GuajiService;
import com.ben.shitu.service.ShituService;
import com.dp.dao.credit.CreditProce;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.PropertyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.system.UMsgService;
import com.lw.service.specialprop.SpecialPropService;
import com.pm.service.mail.MailInfoService;
import com.pm.service.pic.PicService;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.jieyi.util.Constant;
import com.web.service.friend.FriendService;

public class BaseAction extends DispatchAction
{
	protected Logger log = Logger.getLogger(this.getClass());
	
	protected final String INDEX = "index";
	
	protected final String ERROR = "error";
	
	protected final String JIECHUJIEYI = "jiechujieyi";
	
	protected final String GOODLIST = "goodlist";
	
	protected final String FRIENDLIST = "friendlist";
	
	protected final String JIEYILIST = "jieyilist";
	
	protected final String LIHUN_CONMIT = "lihunconmit";
	
	protected final String CONMIT = "conmit";
	
	protected final String MERRY = "merry";
	
	protected final String ERRORGENDER = "errorgender";
	
	protected final String JIEHUNSHENQING = "jiehunshenqing";
	
	protected final String TONGYIJIEHUN = "tongyijiehun";
	
	protected final String GOOD_NOT_ENOUGH = "goodnotenough";
	
	protected final String MERRY_SUCCESS = "merrysuccess";
	
	protected final String SCENE = "no_refurbish_scene";
	
	protected final String BROTHER = "index";
	
	protected final String GET_EXP = "getexp";
	
	protected final String FUQI = "fuqi";
	
	protected final String LOVE_DEAR = "lovedear";
	
	protected final String HINT1 = "����ʱ��������ϵ�ͷ�";
	
	protected final String HINT2 = "������Ʒ��������";
	
	protected final String HINT24 = "�Բ��𣬶Է����ܺ������";
	
	protected final String HINT3 = "����������Ʒ���������ռ���ȫ�������ɣ�";
	
	protected final String HINT4 = "������Ʒ��λ�ö��ڷŴ��󣬷������ⲻ�ϣ�";
	
	protected final String HINT5 = "�Բ�����ѡ��ĺ��Ѳ����ߣ�";
	
	protected final String HINT6 = "�Ǽ�Ҫ�����㣬˫������40�����ϣ�";
	
	protected final String HINT7 = "��ӪҪ�����㣬˫����Ϊͬһ��Ӫ��";
	
	protected final String HINT8 = "Ҫ�����㣬��˫����Ϊ���ѣ�";
	
	protected final String HINT9 = "���ܶ�Ҫ�����㣬��˫�����ܶȴﵽ"+Constant.JIEYI_DEAR_NEED+"�㣡";
	
	protected final String HINT10 = "���Ѿ������!";
	
	protected final String HINT11 = "���ʱ��������ϵ�ͷ�";
	
	protected final String HINT12 = "�����Ʒ��������";
	
	protected final String HINT13 = "���������Ʒ���������ռ���ȫ�������ɣ�";
	
	protected final String HINT14 = "�����Ʒ��λ�ö��ڷŴ��󣬷������ⲻ�ϣ�";
	
	protected final String HINT15 = "�Բ�����ѡ��ĺ��Ѳ����ߣ�";
	
	protected final String HINT16 = "�Ǽ�Ҫ�����㣬˫������40�����ϣ�";
	
	protected final String HINT17 = "��ӪҪ�����㣬˫����Ϊͬһ��Ӫ��";
	
	protected final String HINT18 = "Ҫ�����㣬��˫����Ϊ���ѣ�";
	
	protected final String HINT19 = "���ܶ�Ҫ�����㣬��˫�����ܶȴﵽ"+Constant.JIEYI_DEAR_NEED+"�㣡";
	
	protected final String HINT20 = "�Է��Ѿ������!";
	
	protected final String HINT23 = "��������!";
	
	protected final String HINT21 = "�Է�������";
	
	protected final String HINT22 = "�Բ������ǲ�������������������ϸ�Ķ�����������";
	
	
	protected String getMerryHint(String name){
		return "���ԭ�������ǿ�����"+name.trim()+"���ﰡ�����Ҿ���"+name.trim()+"����Ӧ�öԹ�����û��Ȥ���㻹����������һ���ɣ�";
	}
	
	protected String jieYISuccessMes(String name,String detail){
		return "����������"+name+"��"+detail.trim();
	}
	
	protected String getSysInfo(String name1,String name2){
		return name1.trim()+"��"+name2.trim()+"������������ף�����ǣ�";
	}
	
	protected String jieYIFailMes(String name,String detail){
		return name+"�ܾ�������"+detail.trim();
	}
	
	protected String jieYISuccMes(String name,String detail){
		
		return name+"��ͬ��������"+detail.trim();
	}
	
	protected String jieYISuccMes(String name){
		return "��ϲ�㣡����"+name+"����������Ӵ����ǽ��ε����գ�ͬ��������";
	}
	
	protected String jiechuJieyi(String name){
		return "���Ѿ���"+name+"���۶Ͻ��ˣ�";
	}
	
	protected String jiechuJieyiMes(String name){
		return name+"�Ѿ��������۶Ͻ��ˣ�";
	}
	
	protected String lihun(String name){
		return "���Ѿ���"+name+"����ˣ�";
	}
	
	protected String lihunMes(String name){
		return name+"�Ѿ���������ˣ�";
	}
	
	protected String notAgree(String name){
		return name+"��ͬ�������飡";
	}
	
	protected RoleService roleService = new RoleService();
	protected PlayerPropGroupDao ppgd = new PlayerPropGroupDao();
	protected FriendService friendService = new FriendService();
	protected UMsgService uMsgService = new UMsgService();
	protected SystemInfoService systemInfoService = new SystemInfoService();
	protected RoleTitleDAO roleHonourDAO = new RoleTitleDAO();
	protected GoodsService goodsService = new GoodsService();
	protected SpecialPropService specoalPropService = new SpecialPropService();
	protected ShituService shituService = new ShituService();
	protected PartInfoDAO partInfoDAO = new PartInfoDAO();
	protected PicService picService = new PicService(); 
	protected MailInfoService mailInfoService = new MailInfoService();
	protected PropertyService propertyService = new PropertyService();
	protected CreditProce creditProce = new CreditProce();
	protected GuajiService guajiService = new GuajiService();
	protected RoomService roomService = new RoomService();
	protected EconomyService economyService = new EconomyService();
	protected NpcCache npcCache = new NpcCache();
	protected RoleEntity getRoleEntity(HttpServletRequest request){
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		return roleInfo;
	}
	protected int getP_Pk(HttpServletRequest request){
		int p_pk;
		if (request.getSession().getAttribute("pPk") != null)
		{
			p_pk = Integer.parseInt(request.getSession().getAttribute("pPk")
					.toString().trim());
		}
		else
		{
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request
					.getSession());
			p_pk = roleInfo.getBasicInfo().getPPk();
		}
		return p_pk;
	}
	
	protected  BasicInfo getBasicInfo(HttpServletRequest request){
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		return roleInfo.getBasicInfo();
	}
	
	protected void departList(HttpServletRequest request,List list,int count,int nowPage){
		int allPage = getAllCount(count, Constant.EVERY_PAGE_COUNT);
		request.setAttribute("list", list);
		request.setAttribute("allPage", allPage);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("listsize",list.size());
		request.setAttribute("cc",count);
		request.setAttribute("evep",Constant.EVERY_PAGE_COUNT);
	}
	
	private static int getAllCount(int allSize, int everyPage) {
		return allSize % everyPage == 0 ? allSize / everyPage : allSize
				/ everyPage + 1;
	}
	
	protected void setMessage(HttpServletRequest request,String message){
		request.setAttribute("message", message);
	}
	
	protected void setAttribute(HttpServletRequest request,String name,Object o){
		request.setAttribute(name, o);
	}
	
	protected String getTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		return time;
	}
	
}
