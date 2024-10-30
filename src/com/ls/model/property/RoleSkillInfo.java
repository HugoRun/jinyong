package com.ls.model.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.skill.SkillCache;
import com.ls.ben.dao.info.skill.PlayerSkillDao;
import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.info.skill.SkillVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.user.UserBase;
import com.ls.web.service.log.DataErrorLog;
import com.pm.vo.passiveskill.PassSkillVO;

/**
 *  �洢��ҵ� ������Ϣ
 * @author Administrator
 *
 */
public class RoleSkillInfo extends UserBase
{
	public PlayerSkillDao skillDao;
	private HashMap<Integer,PlayerSkillVO> skill;
	private RoleSkillPropertyInfo roleSkillPropertyInfo;
	/**
	 * ��ʼ��
	 * @param roleInfo
	 */
	public RoleSkillInfo(int p_pk)
	{
		super(p_pk);
		skillDao = new PlayerSkillDao();
		skill = skillDao.getPlayerAllSkill(p_pk);	
		roleSkillPropertyInfo = new RoleSkillPropertyInfo(p_pk);
	}
	
	
	/**
	 * ��ռ���
	 */
	public void clear()
	{
		if( skill.size()>0 )
		{
			skillDao.clear(p_pk);
			skill.clear();//��չ�������
			roleSkillPropertyInfo = new RoleSkillPropertyInfo(p_pk);//���ر�������
		}
	}
	
	

	/**
	 * �õ���ҵ� ���� �б�
	 * @return
	 */
	public List<PlayerSkillVO> getSkillList()
	{
		
		List<PlayerSkillVO> list = new ArrayList<PlayerSkillVO>(skill.values());
		return list;	
	}
	
	/**
	 * �õ���ҵ��������������б�
	 * @return
	 */
	public List<PlayerSkillVO> getAttackSkillList()
	{
		
		List<PlayerSkillVO> list = new ArrayList<PlayerSkillVO>(skill.values());
		PlayerSkillVO skill = null;
		
		if( list!=null && list.size()!=0 )
		{
			for( int i=list.size()-1;i>=0;i-- )
			{
				skill = list.get(i);
				if( skill.getSkType()!=1 )//���˵���������������
				{
					list.remove(i);
				}
				else if( skill.getSkType()==1 && skill.getSkId()==1 )//���˵��ǲ�׽���＼��
				{
					list.remove(i);
				}
			}
		}
		
		return list;	
	}
	
	/**
	 * ʹ�� ����id�õ���ҵ� ���弼��, 
	 * @return
	 */
	public PlayerSkillVO getSkillBySkId( String skId)
	{
		List<PlayerSkillVO> list = getSkillList();
		PlayerSkillVO playerSkillVO = null;
		for (PlayerSkillVO skillVO: list ) {
			if(skillVO.getSkId() == Integer.parseInt(skId)) {
				playerSkillVO = skillVO;
			}
		}
		
		return playerSkillVO;		
	}

	/**
	 * ʹ�� ���˼������� 
	 * @param s_pk
	 * @return
	 */
	public PlayerSkillVO getSkillBySPk(int s_pk)
	{
		return skill.get(s_pk);
	}
	/**
	 * �޸ļ������ƺ� ��ݼ� ����
	 */
	public void updateSkillName(int p_pk,int s_pk,String s_name){
		PlayerSkillDao playerSkillDao = new PlayerSkillDao();
		RoleEntity roleEntity = RoleCache.getByPpk(p_pk);			
		RoleShortCutInfo roleShortCutInfo = roleEntity.getRoleShortCutInfo();
		
		PlayerSkillVO vo = null;
		vo = playerSkillDao.getById(s_pk);
		
		skill.put(s_pk, vo);
		
		//���¼��ؿ�ݼ�
		roleShortCutInfo.updateShortcutName(s_pk, s_name); 
	}
	
	/**
	 * ѧϰһ������
	 */
	public PlayerSkillVO study( int sId ) {
		SkillVO skill = SkillCache.getById(sId);
		if( skill!=null )
		{
			PlayerSkillVO playerSkill = new PlayerSkillVO();
			playerSkill.setPPk(p_pk);
			playerSkill.setSkId(sId);
			new SkillDao().loadPlayerSkillDetail(playerSkill);//���ؼ�����Ϣ
			this.skillDao.add(playerSkill);//���ݿ����
			this.addSkillToPlayer(playerSkill);//�ڴ����
			return playerSkill;
		}
		else
		{
			DataErrorLog.debugData("RoleSkillInfo.study:�޸ü���,sId="+sId);
			return null;
		}
	}
	
	/**
	 * ����һ������
	 */
	public void addSkillToPlayer( PlayerSkillVO skillvo ) {
		if(skillvo==null)
		{
			return;
		}
		skill.put(skillvo.getSPk(), skillvo);
		if( skillvo.getSkType()==0||skillvo.getSkType()==4 )//����Ǳ������ܣ������ر�����������
		{
			roleSkillPropertyInfo.loadPropertys(skillvo.getPPk());
		}
	}
	
	/**
	 * �������Ƴ�һ������
	 * 
	 */
	public void removeSkillFromPlayer ( int sPk ) {
		skill.remove(sPk);
	}
	
	/**
	 * �õ���ҵı������ܵ�����
	 * **/
	public PassSkillVO getPassSkillProperty(){
		PassSkillVO vo = new PassSkillVO();
		vo.setSkGjMultiple(roleSkillPropertyInfo.getSkGjMultiple());
		vo.setSkFyMultiple(roleSkillPropertyInfo.getSkFyMultiple());
		vo.setSkHpMultiple(roleSkillPropertyInfo.getSkHpMultiple());
		vo.setSkMpMultiple(roleSkillPropertyInfo.getSkMpMultiple());
		vo.setSkBjMultiple(roleSkillPropertyInfo.getSkBjMultiple());
		vo.setSkGjAdd(roleSkillPropertyInfo.getSkGjAdd());
		vo.setSkFyAdd(roleSkillPropertyInfo.getSkFyAdd());
		vo.setSkHpAdd(roleSkillPropertyInfo.getSkHpAdd());
		vo.setSkMpAdd(roleSkillPropertyInfo.getSkMpAdd());
		vo.setSkJMultiple(roleSkillPropertyInfo.getSkJMultiple());
		vo.setSkMMultiple(roleSkillPropertyInfo.getSkMMultiple());
		vo.setSkSMultiple(roleSkillPropertyInfo.getSkSMultiple());
		vo.setSkHMultiple(roleSkillPropertyInfo.getSkHMultiple());
		vo.setSkTMultiple(roleSkillPropertyInfo.getSkTMultiple());
		return vo;
	}

}
