package com.ls.web.service.wml.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ben.vo.intimatehint.IntimateHintVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.system.IntimateHintService;
import com.pm.service.mail.MailInfoService;
/**
 * @author ls
 * ������Ϸ��ҳ�涯̬���ֵ�wml�ű�
 */
public class MainPageWmlService
{
	/**
	 * �õ���ɫ�ʼ���ʾ
	 */
	public String getMailHint( RoleEntity roleInfo )
	{
		String mail_hint = "";
		MailInfoService mailInfoService = new MailInfoService();
		int newMail = mailInfoService.havingNewMail(roleInfo.getBasicInfo().getPPk()+"");
		
		if(newMail>0)
		{
			mail_hint = "(��)";
		}
		
		return mail_hint;
	}
	
	/**
	 * �õ���ҳ�������С��ʿ
	 */
	public String getIntimateHintWml( RoleEntity roleInfo )
	{
		if( roleInfo==null )
		{
			return null;
		}
		
		if( roleInfo.getBasicInfo().getGrade()>=10 )//��ҵȼ����ڵ���10ʱ������ʾ����С��ʿ
		{
			return null;
		}
		IntimateHintService intimateHintService = new IntimateHintService();
		IntimateHintVO intimateHintVO = intimateHintService.getRandomIntimateHint();
		return intimateHintVO.getHHint()+":"+intimateHintVO.getHContent()+"<br/>";
	}
	
	
	/**
	 * �õ���ҳ��ģ��ߵ�ͼ��wml�ű�
	 */
	public String getWalkWml( RoleEntity roleInfo ,HttpServletRequest request,HttpServletResponse response)
	{
		if( roleInfo==null )
		{
			return null;
		}
		StringBuffer result = new StringBuffer();
		
		RoomService roomService = new RoomService();
		SceneVO current_scene_info =  roleInfo.getBasicInfo().getSceneInfo();
		
		if( current_scene_info.getSceneJumpterm()>0 )
		{
			result.append("����:");
			result.append("<anchor>");
			result.append("<go method=\"get\" href=\"").append(response.encodeURL(GameConfig.getContextPath()+"/walk.do?way=5")).append("\"></go>");
			result.append(current_scene_info.getJumpSceneInfo().getSceneName());
			result.append("</anchor>");
			result.append("<br/>");
		}
		
		if( current_scene_info.getSceneShang()>0 )
		{
			SceneVO shang_scene_info = roomService.getById(current_scene_info.getSceneShang()+"");
			result.append("��:");
			result.append("<anchor>");
			result.append("<go method=\"get\" href=\"").append(response.encodeURL(GameConfig.getContextPath()+"/walk.do?way=1")).append("\"></go>");
			result.append(shang_scene_info.getSceneName());
			result.append("</anchor>");
			result.append("<br/>");
		}
		if( current_scene_info.getSceneXia()>0 )
		{
			SceneVO xia_scene_info = roomService.getById(current_scene_info.getSceneXia()+"");
			result.append("��:");
			result.append("<anchor>");
			result.append("<go method=\"get\" href=\"").append(response.encodeURL(GameConfig.getContextPath()+"/walk.do?way=2")).append("\"></go>");
			result.append(xia_scene_info.getSceneName());
			result.append("</anchor>");
			result.append("<br/>");
		}
		if( current_scene_info.getSceneZuo()>0 )
		{
			SceneVO zuo_scene_info = roomService.getById(current_scene_info.getSceneZuo()+"");
			result.append("��:");
			result.append("<anchor>");
			result.append("<go method=\"get\" href=\"").append(response.encodeURL(GameConfig.getContextPath()+"/walk.do?way=3")).append("\"></go>");
			result.append(zuo_scene_info.getSceneName());
			result.append("</anchor>");
			result.append("<br/>");
		}
		if( current_scene_info.getSceneYou()>0 )
		{
			SceneVO you_scene_info = roomService.getById(current_scene_info.getSceneYou()+"");
			result.append("��:");
			result.append("<anchor>");
			result.append("<go method=\"get\" href=\"").append(response.encodeURL(GameConfig.getContextPath()+"/walk.do?way=4")).append("\"></go>");
			result.append(you_scene_info.getSceneName());
			result.append("</anchor>");
			result.append("<br/>");
		}
		
		return result.toString();
	}
}
