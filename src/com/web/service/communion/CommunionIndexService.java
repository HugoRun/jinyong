package com.web.service.communion;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ben.dao.embargo.EmbargoDAO;
import com.ben.vo.communion.CommunionVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.group.GroupService;
import com.pub.ben.info.Expression;

/**
 * ��ҳ����
 * 
 * @author ��ƾ� 11:13:44 AM
 */
public class CommunionIndexService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ��ҳ����
	 * @param roleInfo
	 * @param type
	 * @param title
	 * @param usercommunionpub                ���ĵĵȼ�����
	 * @return
	 */
	public String Communion(RoleEntity roleInfo, int type, String title,
			String usercommunionpub)
	{
		String hint = null;
		if (type == 1)// ��������
		{
			hint = CommPub(roleInfo, type, title, usercommunionpub);
		}
		if (type == 2)// ��Ӫ����
		{
			hint = CommCamp(roleInfo, type, title);
		}
		if (type == 3)// ��������
		{
			hint = CommDuiWu(roleInfo, type, title);
		}
		if (type == 4)// ��������
		{
			hint = CommTong(roleInfo, type, title);
		}
		if (type > 4)
		{// ��������
			hint = CommPub(roleInfo, type, title, usercommunionpub);
		}
		if (type < 1)
		{// ��������
			hint = CommPub(roleInfo, type, title, usercommunionpub);
		}
		return hint;
	}

	/**
	 * ��������
	 */
	public String CommPub(RoleEntity roleInfo, int type, String title,
			String usercommunionpub)
	{
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		String hint = null;
		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(title);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "�����������зǷ��ַ������������룡";
			return hint;
		}*/
		int flag = Expression.hasPublish(title);
		if (flag == -1)
		{
			hint = "�����������зǷ��ַ������������룡";
			return hint;
		}
		if (title != null)
		{
			if (title.length() > 20)
			{
				hint = "��ֻ������20���ַ�";
				return hint;
			}
		}
		if( roleInfo.getBasicInfo().getGrade() < Integer.parseInt(usercommunionpub))
		{
			hint = "�Բ���ֻ��" + usercommunionpub + "�����ϵ���Ҳſ����ڹ���Ƶ�����ԣ�";
			return hint;
		}
		// �ж��Ƿ��ظ�����
		EmbargoDAO embargoDAO = new EmbargoDAO();
		String s = embargoDAO.isEmbargo(roleInfo.getBasicInfo().getPPk(), Time);
		if (s != null)
		{
			hint = "�����ڹ�������Ƶ������" + s + "���ӣ�";
			return hint;
		}
		// ������ʱ��
		if (title != null)
		{
			hint = roleInfo.getStateInfo().isPublicChat();
			if( hint!=null )
			{
				return hint;
			}
		}
		// ����Ƿ��н�ֹ�ؼ���
		if (Expression.hasForbidChar(title,ForBidCache.FORBIDCOMM))
		{
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if(title.trim().equals("")){
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if(title.indexOf("��") != -1){
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if (title != null)
		{
			// ִ�в��빫�������¼
			CommunionVO communionVO = new CommunionVO();
			communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
			communionVO.setPName(roleInfo.getBasicInfo().getName());
			communionVO.setCTitle(title);
			communionVO.setCType(type);
			communionVO.setCreateTime(Time);
			ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
			publicChatInfoCahe.put(communionVO);
		}
		return hint;
	}

	/**
	 * ��Ӫ����
	 */
	public String CommCamp(RoleEntity roleInfo, int type, String title)
	{
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		String hint = null;
		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(title);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "�����������зǷ��ַ������������룡";
			return hint;
		}*/
		int flag = Expression.hasPublish(title);
		if (flag == -1)
		{
			hint = "�����������зǷ��ַ������������룡";
			return hint;
		}
		if (title != null)
		{
			if (title.length() > 20)
			{
				hint = "��ֻ������20���ַ�";
				return hint;
			}
		}
		// �����ɫû�м�����Ӫ�Ͳ�������
		if (title != null)
		{
			if (roleInfo.getBasicInfo().getPRace() == 0)
			{
				hint = "�Բ�����Ŀǰû�м�����Ӫ���޷�����ӪƵ�����ԣ�";
				return hint;
			}
		}
		// ����Ƿ��н�ֹ�ؼ���
		if (Expression.hasForbidChar(title,ForBidCache.FORBIDCOMM))
		{
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if(title.trim().equals("")){
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if(title.indexOf("��") != -1){
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		// ִ�в�����Ӫ�����¼
		if (title != null)
		{
			// ִ�в��빫�������¼ c_pk,p_pk,p_name,c_zhen,c_title,c_type,create_time
			CommunionVO communionVO = new CommunionVO();
			communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
			communionVO.setPName(roleInfo.getBasicInfo().getName());
			communionVO.setCZhen(roleInfo.getBasicInfo().getPRace());
			communionVO.setCTitle(title);
			communionVO.setCType(type);
			communionVO.setCreateTime(Time);
			ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
			publicChatInfoCahe.put(communionVO);
		}
		return hint;
	}

	/**
	 * ��������
	 */
	public String CommDuiWu(RoleEntity roleInfo, int type, String title)
	{
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������

		GroupService groupService = new GroupService();
		String hint = null;
		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(title);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "�����������зǷ��ַ������������룡";
			return hint;
		}*/
		int flag = Expression.hasPublish(title);
		if (flag == -1)
		{
			hint = "�����������зǷ��ַ������������룡";
			return hint;
		}
		if (title != null)
		{
			if (title.length() > 20)
			{
				hint = "��ֻ������20���ַ�";
				return hint;
			}
			if (groupService.getCaptionPk(roleInfo.getBasicInfo().getPPk()) < 0)
			{
				hint = "�Բ�����Ŀǰû�м�����飬�޷������Ƶ�����ԣ�";
				return hint;
			}
		}
		// ����Ƿ��н�ֹ�ؼ���
		if (Expression.hasForbidChar(title,ForBidCache.FORBIDCOMM))
		{
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if(title.trim().equals("")){
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if(title.indexOf("��") != -1){
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if (title != null)
		{
			// ִ�в��빫�������¼ c_pk,p_pk,p_name,c_dui,c_title,c_type,create_time
			CommunionVO communionVO = new CommunionVO();
			communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
			communionVO.setPName(roleInfo.getBasicInfo().getName());
			communionVO.setCDui(roleInfo.getStateInfo().getGroup_id());
			communionVO.setCTitle(title);
			communionVO.setCType(type);
			communionVO.setCreateTime(Time);
			ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
			publicChatInfoCahe.put(communionVO);
		}
		return hint;
	}

	/**
	 * ��������
	 */
	public String CommTong(RoleEntity roleInfo, int type, String title)
	{
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		String hint = null;

		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(title);
		boolean b = m.matches();
		if (b == false)
		{  
			hint = "�����������зǷ��ַ������������룡";
			return hint;
		}*/
		int flag = Expression.hasPublish(title);
		if (flag == -1)
		{
			hint = "�����������зǷ��ַ������������룡";
			return hint;
		}
		if (title != null)
		{
			if (title.length() > 20)
			{
				hint = "��ֻ������20���ַ�";
				return hint;
			}
			if (roleInfo.getBasicInfo().getFaction() == null)
			{
				hint = "�Բ�����Ŀǰû�м�����ɣ��޷��ڰ��Ƶ�����ԣ�";
				return hint;
			}
		}
		// ����Ƿ��н�ֹ�ؼ���
		if (Expression.hasForbidChar(title,ForBidCache.FORBIDCOMM))
		{
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if(title.trim().equals("")){
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if(title.indexOf("��") != -1){
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			return hint;
		}
		if (title != null)
		{
			// ִ�в��빫�������¼ c_pk,p_pk,p_name,c_bang,c_title,c_type,create_time
			CommunionVO communionVO = new CommunionVO();
			communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
			communionVO.setPName(roleInfo.getBasicInfo().getName());
			communionVO.setCBang(roleInfo.getBasicInfo().getFaction().getId());
			communionVO.setCTitle(title);
			communionVO.setCType(type);
			communionVO.setCreateTime(Time);
			ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
			publicChatInfoCahe.put(communionVO);
		}
		return hint;
	}
}
