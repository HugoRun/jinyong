/**
 * 
 */
package com.pm.action.viewpic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.map.MapService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;


/**
 * @author zhangjj
 *
 */
public class ViewPicAction extends DispatchAction
{

	Logger logger =  Logger.getLogger("log.action");
	
	// 查看地图的图片
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
    		RoleService roleService = new RoleService();
    		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
    		int pPk = roleInfo.getBasicInfo().getPPk();
    		
			String mapid = roleInfo.getBasicInfo().getSceneId();
			
			logger.info("mapid="+mapid+" ,pPk="+pPk);
			
			RoomService roomService = new RoomService();
			SceneVO scenevo = roomService.getById(mapid);
			
			request.setAttribute("scenevo",scenevo);
		
			MapService mapService = new MapService();
			String mapDisplay = mapService.getDiaplayById(scenevo.getSceneMapqy());
			
			request.setAttribute("mapDisplay",mapDisplay);
		
			return mapping.findForward("sceneView");
			
	}
	
	
	
	// 查看地图的图片
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
    		RoleService roleService = new RoleService();
    		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
    		int pPk = roleInfo.getBasicInfo().getPPk();
    		
			
			String mapid = request.getParameter("mapid");
			if( mapid==null )
			{
				logger.debug("mapid为空");
			}
			
			logger.info("mapid="+mapid+" ,pPk="+pPk);
			
			RoomService roomService = new RoomService();
			SceneVO scenevo = roomService.getById(mapid);
			
			request.setAttribute("scenevo",scenevo);
		
			MapService mapService = new MapService();
			String mapDisplay = mapService.getDiaplayById(scenevo.getSceneMapqy());
			
			request.setAttribute("mapDisplay",mapDisplay);
		
			return mapping.findForward("sceneView");
			
	}
}
