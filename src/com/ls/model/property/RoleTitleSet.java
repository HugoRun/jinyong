package com.ls.model.property;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ben.dao.honour.RoleTitleDAO;
import com.ben.vo.honour.RoleTitleVO;
import com.ben.vo.honour.TitleVO;
import com.ls.ben.cache.staticcache.honour.TitleCache;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.user.UserBase;
import com.ls.model.vip.Vip;
import com.ls.model.vip.VipManager;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.pub.util.DateUtil;
import com.ls.web.service.rank.RankService;
import com.pm.service.mail.MailInfoService;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.service.popupmsg.PopUpMsgService;

/**
 * ���ܣ���ɫ�ƺż���
 */
public class RoleTitleSet extends UserBase
{
	private RoleTitleDAO dao = null;
	private Map<Integer, RoleTitleVO> titleCacheByType = null;//key��<�ƺŵ�����>
	private Map<Integer, RoleTitleVO> titleCacheByTId = null;//key��<�ƺŵ�id>

	private int showTitleId=-1;//��ǰ��ʾ�ĳƺ�id
	private PropertyModel titlePropertys = new PropertyModel();// �ƺ�Ч����

	
	public RoleTitleSet( int p_pk )
	{
		super(p_pk);
		dao = new RoleTitleDAO();
		titleCacheByType = new LinkedHashMap<Integer, RoleTitleVO>();
		titleCacheByTId = new LinkedHashMap<Integer, RoleTitleVO>();
		List<RoleTitleVO> role_title_list = dao.getListByPPk(p_pk);
		
		for (RoleTitleVO roleTitleVO : role_title_list)
		{
			if( roleTitleVO.getIsShow()==1 )
			{
				showTitleId = roleTitleVO.getTId();
			}
			put(roleTitleVO);
		}
	}
	
	/**
	 * ��ʼ���ƺ�
	 */
	public void init()
	{
		showTitleId = -1;
		titleCacheByType.clear();
		titleCacheByTId.clear();
		titlePropertys.init();
		dao.clear(p_pk);
		
		//��������ӳ�ʼ�ƺ�
		int race = this.getRoleEntity().getBasicInfo().getPRace();
		TitleVO race_title = null;//����ƺ�
		switch(race)
		{
			case 1:race_title = TitleCache.getById(3);break;//��
			case 2:race_title = TitleCache.getById(11);break;//��
		}
		gainTitle(race_title);
	}
	
	/**
	 * ����Ҹ��ӳƺ�����
	 * �ȼ��ػ�Ա�ĸ�������Ч�����ټ��سƺŵĸ���Ч��
	 */
	public void loadProperty(PartInfoVO player)
	{
		Vip vip = this.getVIP();
		if( vip!=null )
		{
			vip.loadPropertys(player);
		}
		titlePropertys.loadPropertys(player);
	}
	
	
	private RoleTitleVO put(RoleTitleVO roleTitle)
	{
		RoleTitleVO old_title = null;
		if( roleTitle!=null )
		{
			roleTitle.createTimerEvent(super.getRoleEntity().getEventManager());
			
			old_title = this.titleCacheByType.get(roleTitle.getType());
			if( old_title!=null )
			{
				this.remove(old_title);
			}
			titleCacheByTId.put(roleTitle.getTId(), roleTitle);
			titlePropertys.appendPropertyByAttriStr(roleTitle.getAttriStr(),PropertyModel.ADD_PROPERTY);
			titleCacheByType.put(roleTitle.getType(), roleTitle);
		}
		return old_title;
	}
	
	private RoleTitleVO remove(RoleTitleVO roleTitle)
	{
		RoleTitleVO old_title = null;
		if( roleTitle!=null )
		{
			roleTitle.removeTimerEvent(super.getRoleEntity().getEventManager());
			if( roleTitle.getId()==this.showTitleId )
			{
				this.showTitleId = -1;
			}
			titlePropertys.appendPropertyByAttriStr(roleTitle.getAttriStr(),PropertyModel.SUB_PROPERTY);
			titleCacheByType.remove(roleTitle.getType());
			old_title=  titleCacheByTId.remove(roleTitle.getTId());
			this.dao.delById(this.p_pk,roleTitle.getTId());
		}
		return old_title;
	}

	/**
	 * ɾ��һ���ƺ�
	 */
	public void delTitle(int tId)
	{
		RoleTitleVO role_title = this.getByTId(tId);
		
		switch( role_title.getType())
		{
			case TitleVO.VIP://��Ա�ƺ�ɾ������
				this.delVipTitle(role_title);
				break;
		}
		
		this.remove(role_title);
	}
	
	/**
	 * ɾ����Ա�ƺ�
	 */
	private void delVipTitle(RoleTitleVO role_title)
	{
		if( role_title==null )
		{
			return;
		}
		Vip vip =VipManager.getByTId(role_title.getTId());
		if( vip!=null )
		{
			// �����ʼ�������ҵ�����
			StringBuffer sb = new StringBuffer(800);
			MailInfoService mailInfoService = new MailInfoService();
			String title = "ϵͳ�ʼ�";
			sb.append("���").append(vip.getName()).append("�ʸ��Ѿ�����,���������ٴι����Ա�ʸ������8���Ż�!<br/>");
			// ���ȵõ����л�Ա���͵ĵ���
			PropDao propDao = new PropDao();
			List<PropVO> list = propDao.getListByType(PropType.VIP);
			for (PropVO prop:list)
			{
				sb.append(prop.getPropName());
				sb.append("<anchor>");
				sb.append("<go method=\"post\" href=\"").append(GameConfig.getContextPath()).append("/vip.do"+"\">");
				sb.append("<postfield name=\"cmd\" value=\"n2\" />");
				sb.append("<postfield name=\"prop_id\" value=\"").append(prop.getPropID()).append("\" />");
				sb.append("</go>");
				sb.append("����");
				sb.append("</anchor><br/>");
			}
			mailInfoService.sendMailBySystem(this.p_pk, title, sb.toString());
			
			//ͳ����Ҫ
			new RankService().updateVIP(p_pk, 0, 0);
			//VIP���ڸ�����ʽ��Ϣ
			new PopUpMsgService().addSysSpecialMsg(p_pk,0,0, PopUpMsgType.VIP_ENDTIME);
		}
	}
	
	
	/**
	 * �ƺ��б�
	 * @return
	 */
	public List<RoleTitleVO> getRoleTitleList()
	{
		return new ArrayList<RoleTitleVO>(titleCacheByType.values());
	}

	/**
	 * ���ĳƺ���ʾ״̬
	 * @param id	Ҫ���ĵĳƺ�
	 */
	public void updateShowStatus( int tId )
	{
		RoleTitleVO cur_title = this.getByTId(tId);
		
		if( cur_title.getIsShow()==1 )//�����ǰ��ʾ�ľ��Ǹóƺ�
		{
			this.showTitleId = -1;
		}
		else
		{
			RoleTitleVO show_title = this.getShowTitle();
			if( show_title!=null )
			{
				show_title.updateIsShow();
			}
			this.showTitleId = tId;
		}
		
		cur_title.updateIsShow();
	}
	
	/**
	 * �õ�����ʹ�óƺŵ�����
	 * @return
	 */
	public String getShowTitleName()
	{
		RoleTitleVO showTitle = this.titleCacheByTId.get(this.showTitleId);
		if( showTitle!=null )
		{
			return showTitle.getName();
		}
		else
		{
			return "��";
		}
	}
	
	/**
	 * ���һ���ƺ�
	 * @return
	 */
	public String gainTitle( TitleVO newTitle)
	{
		if( newTitle==null )
		{
			return "�ƺ���Ϣ����";
		}
		
		RoleTitleVO roleTitle = new RoleTitleVO(p_pk,newTitle);
		this.dao.add(roleTitle);
		this.put(roleTitle);
		
		switch(roleTitle.getType())
		{
			case TitleVO.VIP:
				this.gainVipTitle(roleTitle);
				break;
		}
		
		return null;
	}
	
	/**
	 * ���vip�ƺ�
	 * @param roleTitle
	 */
	private void gainVipTitle(RoleTitleVO roleTitle)
	{
		Vip vip = this.getVIP();
		if( vip!=null )
		{
			SystemInfoService systemInfoService = new SystemInfoService();
			String info = "���"+ this.getRoleEntity().getName()+ "��Ϊ��������" + vip.getName() + "";
			systemInfoService.insertSystemInfoBySystem(info);
		}
	}
	
	/**
	 * �Ƿ��и����͵ĳƺ�
	 * @param titleType		�ƺ�����
	 * @return
	 */
	public boolean isHaveByType(int titleType)
	{
		if( this.titleCacheByType.get(titleType)==null )
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * �Ƿ���VIP�ƺ�
	 * @return
	 */
	public String isGainNewVipTitle( TitleVO newVipTitle)
	{
		if( newVipTitle==null )
		{
			return "��Ա�ƺŴ���";
		}
		RoleTitleVO role_title = this.titleCacheByType.get(TitleVO.VIP);
		
		if( role_title!=null )
		{
			Vip vip = getVIP();
			StringBuffer sb = new StringBuffer();
			sb.append("�������Ѿ���").append(vip.getName()).append(",");
			sb.append( role_title.getSimpleLeftTimeDes()).append( "�������ʹ�øõ��ߺ��㽫���");
			sb.append( DateUtil.returnTimeStr(newVipTitle.getUseTime() * 60) ).append( "�ġ�");
			sb.append( newVipTitle.getName() ).append( "��������ƺţ�����");
			sb.append(vip.getName()).append("���ĳƺż����������Խ���ʧ����ȷ��Ҫʹ�øõ�����<br/>");
			return sb.toString();
		}
		
		return null;
	}
	
	/**
	 * �õ�vip ��Ϣ
	 * @return
	 */
	public Vip getVIP()
	{
		RoleTitleVO role_title = this.titleCacheByType.get(TitleVO.VIP);
		if(role_title!=null)
		{
			return VipManager.getByTId(role_title.getTId());
		}
		return null;
	}
	
	/**
	 * ���ݳƺ��ж��Ƿ�����ƺ�Ҫ��
	 * @param tId		�ƺ�id
	 * @return
	 */
	public boolean isHaveByTitleStr( String titleStr )
	{
		if( StringUtils.isEmpty(titleStr) || titleStr.equals("0"))
		{
			return true;
		}
		
		String[] title_str_list = titleStr.split(",");
		for (String title_str : title_str_list)
		{
			if( StringUtils.isNumeric(title_str) && this.titleCacheByTId.get(Integer.parseInt(title_str))!=null )
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * �Ƿ��иóƺ�
	 * @param tId		�ƺ�id
	 * @return
	 */
	public boolean isHaveByTId( TitleVO newTitle )
	{
		if( newTitle!=null && this.titleCacheByTId.get(newTitle.getId())==null )
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * �õ�����ʹ�õĳƺ�
	 * @return
	 */
	public RoleTitleVO getShowTitle()
	{
		return this.titleCacheByTId.get(this.showTitleId);
	}

	/**
	 * ͨ��id�õ���ϸ��Ϣ
	 * @param 			�ƺ�id
	 * @return
	 */
	public RoleTitleVO getByTId(String tId)
	{
		return  getByTId(Integer.parseInt(tId));
	}
	
	/**
	 * ͨ��id�õ���ϸ��Ϣ
	 * @param 			�ƺ�id
	 * @return
	 */
	public RoleTitleVO getByTId(int tId)
	{
		return  this.titleCacheByTId.get(tId);
	}


	
	//��ҵ�½ʱ���ͳƺ�ϵͳ��Ϣ
	public void sendTitleSysInfo(RoleEntity role_info){
		List<RoleTitleVO> list = getRoleTitleList();
		for(int i = 0;i<list.size();i++){
			RoleTitleVO vo = list.get(i);
			if(vo.getTId() == 39){
				SystemInfoService systemInfoService = new SystemInfoService();
				systemInfoService.insertSystemInfoBySystem("���µ�һ��"+role_info.getBasicInfo().getName()+"����,��Ҽе���ӭ!");
			}
			if(vo.getTId() == 42){
				SystemInfoService systemInfoService = new SystemInfoService();
				systemInfoService.insertSystemInfoBySystem("С����"+role_info.getBasicInfo().getName()+"������!");
			}
			if(vo.getTId() == 43){
				SystemInfoService systemInfoService = new SystemInfoService();
				systemInfoService.insertSystemInfoBySystem("�����"+role_info.getBasicInfo().getName()+"������!");
			}
			if(vo.getTId() == 44){
	    		SceneVO sceneVO = SceneCache.getById(role_info.getBasicInfo().getSceneId());
				SystemInfoService systemInfoService = new SystemInfoService();
				systemInfoService.insertSystemInfoBySystem("���̵���ϯ����"+role_info.getBasicInfo().getName()+"��"+sceneVO.getSceneName()+"������!");
			}
			if(vo.getTId() == 45){
	    		SceneVO sceneVO = SceneCache.getById(role_info.getBasicInfo().getSceneId());
				SystemInfoService systemInfoService = new SystemInfoService();
				systemInfoService.insertSystemInfoBySystem("���ֵ���ϯ����"+role_info.getBasicInfo().getName()+"��"+sceneVO.getSceneName()+"������!");
			}
			if(vo.getTId() == 46){
	    		SceneVO sceneVO = SceneCache.getById(role_info.getBasicInfo().getSceneId());
				SystemInfoService systemInfoService = new SystemInfoService();
				systemInfoService.insertSystemInfoBySystem("ؤ�����ϯ����"+role_info.getBasicInfo().getName()+"��"+sceneVO.getSceneName()+"������!");
			}
		}
	}
}
