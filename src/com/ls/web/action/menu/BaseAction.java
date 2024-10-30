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
	
	protected final String HINT1 = "结义时出错，请联系客服";
	
	protected final String HINT2 = "结义物品需求不满足";
	
	protected final String HINT24 = "对不起，对方不能和你结义";
	
	protected final String HINT3 = "结义所需物品不够，等收集完全了再来吧！";
	
	protected final String HINT4 = "结义物品的位置都摆放错误，分明心意不诚！";
	
	protected final String HINT5 = "对不起，您选择的好友不在线！";
	
	protected final String HINT6 = "登记要求不满足，双方均在40级以上！";
	
	protected final String HINT7 = "阵营要求不满足，双方需为同一阵营！";
	
	protected final String HINT8 = "要求不满足，需双方互为好友！";
	
	protected final String HINT9 = "亲密度要求不满足，需双方亲密度达到"+Constant.JIEYI_DEAR_NEED+"点！";
	
	protected final String HINT10 = "您已经结婚了!";
	
	protected final String HINT11 = "结婚时出错，请联系客服";
	
	protected final String HINT12 = "结婚物品需求不满足";
	
	protected final String HINT13 = "结婚所需物品不够，等收集完全了再来吧！";
	
	protected final String HINT14 = "结婚物品的位置都摆放错误，分明心意不诚！";
	
	protected final String HINT15 = "对不起，您选择的好友不在线！";
	
	protected final String HINT16 = "登记要求不满足，双方均在40级以上！";
	
	protected final String HINT17 = "阵营要求不满足，双方需为同一阵营！";
	
	protected final String HINT18 = "要求不满足，需双方互为好友！";
	
	protected final String HINT19 = "亲密度要求不满足，需双方亲密度达到"+Constant.JIEYI_DEAR_NEED+"点！";
	
	protected final String HINT20 = "对方已经结婚了!";
	
	protected final String HINT23 = "银两不够!";
	
	protected final String HINT21 = "对方不在线";
	
	protected final String HINT22 = "对不起，你们不满足结义的条件！请仔细阅读结义条件！";
	
	
	protected String getMerryHint(String name){
		return "红娘：原来公子是看上了"+name.trim()+"姑娘啊，但我觉得"+name.trim()+"姑娘应该对公子你没兴趣，你还是重新再挑一个吧！";
	}
	
	protected String jieYISuccessMes(String name,String detail){
		return "您已申请与"+name+"结"+detail.trim();
	}
	
	protected String getSysInfo(String name1,String name2){
		return name1.trim()+"与"+name2.trim()+"结义金兰，大家祝贺他们！";
	}
	
	protected String jieYIFailMes(String name,String detail){
		return name+"拒绝与您结"+detail.trim();
	}
	
	protected String jieYISuccMes(String name,String detail){
		
		return name+"已同意与您结"+detail.trim();
	}
	
	protected String jieYISuccMes(String name){
		return "恭喜你！你与"+name+"结义金兰，从此你们将肝胆相照，同生共死。";
	}
	
	protected String jiechuJieyi(String name){
		return "您已经和"+name+"割袍断交了！";
	}
	
	protected String jiechuJieyiMes(String name){
		return name+"已经和您割袍断交了！";
	}
	
	protected String lihun(String name){
		return "您已经和"+name+"离婚了！";
	}
	
	protected String lihunMes(String name){
		return name+"已经和您离婚了！";
	}
	
	protected String notAgree(String name){
		return name+"不同意和您离婚！";
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		return time;
	}
	
}
