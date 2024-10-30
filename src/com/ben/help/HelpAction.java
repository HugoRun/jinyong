package com.ben.help;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.guaji.vo.GuaJiConstant;
import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.dao.goods.equip.GameEquipDao;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.MapType;
import com.ls.web.action.menu.BaseAction;
import com.web.jieyi.util.Constant;

public class HelpAction extends BaseAction
{
	private HelpService helpService = new HelpService();

	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int nowPage = Integer
				.parseInt(request.getParameter("nowPage") == null ? "1"
						: request.getParameter("nowPage").trim());
		List<Help> list = helpService.findBySuperId(0, (nowPage - 1)
				* Constant.EVERY_PAGE_COUNT, Constant.EVERY_PAGE_COUNT);
		int count = helpService.findBySuperId(0);
		departList(request, list, count, nowPage);
		return mapping.findForward(INDEX);
	}

	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("id");
		if (id == null || "".equals(id.trim()))
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
		Help help = helpService.findById(id);
		if (help == null)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
		switch (help.getType())
		{
			// 掉落查询
			case HelpConstant.DIAOLUO:
				setAttribute(request, "help", help);
				return mapping.findForward("diaoluo");
				// 寻找GM
			case HelpConstant.GM:
				return mapping.findForward("gm");
			default:
				int nowPage = Integer
						.parseInt(request.getParameter("nowPage") == null||"".equals(request.getParameter("nowPage").trim()) ? "1"
								: request.getParameter("nowPage").trim());
				List<Help> list = helpService.findBySuperId(help.getId(),
						(nowPage - 1) * Constant.EVERY_PAGE_COUNT,
						Constant.EVERY_PAGE_COUNT);
				int count = helpService.findBySuperId(help.getId());
				departList(request, list, count, nowPage);
				setAttribute(request, "id", help.getId());
				return mapping.findForward("each");

		}
	}

	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("id");
		if (id == null || "".equals(id.trim()))
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
		Help help = helpService.findById(id);
		if (help == null)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
		setAttribute(request, "help", help);
		setAttribute(request, "nowp",
				request.getParameter("nowp") == null ? "1" : request
						.getParameter("nowp").trim());
		setAttribute(request, "nowPage", request.getParameter("nowPage"));
		return mapping.findForward("one");
	}
	
	private String getBack(HttpServletRequest request,Object id,Object nowPage,HttpServletResponse response){
		String back = "<br/><anchor><go href=\""+response.encodeURL(GameConfig.getContextPath()+"/help.do") +"\" method=\"post\">"+
                 "<postfield name=\"cmd\" value=\"n2\" />"+
                 "<postfield name=\"id\" value=\""+id+"\" />"+
             "<postfield name=\"nowPage\" value=\""+nowPage+"\" />"+
            "</go>返回</anchor>";
		return back;
	}

	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("id");
		if (id == null || "".equals(id.trim()))
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
		Help help = helpService.findById(id);

		if (help == null)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
		BasicInfo bi = getBasicInfo(request);
		int maptype = bi.getSceneInfo().getMap().getMapType();
		if(maptype==MapType.COMPASS){
			setMessage(request, "迷宫中无法使用该功能");
			return mapping.findForward(ERROR);
		}
		if(maptype==MapType.LEITAI_CHALLENGE||maptype==MapType.LEITAI_ACTIVE){
			setMessage(request, "擂台中无法使用该功能");
			return mapping.findForward(ERROR);
		}
		SceneVO curScene = roomService.getById(bi.getSceneId());
		if (curScene != null
				&& curScene.getSceneLimit() != null
				&& curScene.getSceneLimit().indexOf(
						roomService.NOT_CARRY_OUT) != -1)
		{
			setMessage(request, "对不起，目前该地点不允许传出！");
			return mapping.findForward(ERROR);
		}
		String id1 = request.getParameter("id1");
		String nowPa = request.getParameter("nowPa");
		RoleEntity roleEntity = getRoleEntity(request);
		if (help.getLevel_limit() > 0
				&& roleEntity.getBasicInfo().getGrade() < help.getLevel_limit())
		{

			if (help.getType() == HelpConstant.TASK)
			{
				setMessage(request, HelpConstant.LEVEL_TSAK_NOT_ALLOW
						+ getBack(request,id1, nowPa, response));
			}
			else
			{
				setMessage(request, HelpConstant.LEVEL_NOT_ALLOW
						+ getBack(request,id1, nowPa, response));
			}
			return mapping.findForward(ERROR);
		}
		if (help.getTask_men() != 0 && help.getTask_men() != roleEntity.getBasicInfo().getPRace())
		{
			setMessage(request, HelpConstant.TASK_MEN_NOT_ALLOW+ getBack(request,id1, nowPa, response));
			return mapping.findForward(ERROR);
		}
		if (help.getTask_zu() != null && !"".equals(help.getTask_zu().trim()))
		{
			if (roleEntity.getTaskInfo().getTaskCompleteInfo().taskCompleteBoo(
					help.getTask_zu().trim()))
			{
				setMessage(request, HelpConstant.TASK_NOT_ALLOW
						+ getBack(request,id1, nowPa, response));
				return mapping.findForward(ERROR);
			}
		}
		if (help.getScene_id() == 0)
		{
			setMessage(request, "出错了,请联系GM报告错误");
			return mapping.findForward(ERROR);
		}
		roleEntity.getBasicInfo().updateSceneId(help.getScene_id() + "");
		return mapping.findForward("no_refurbish_scene");
	}

	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("id");
		String id1 = request.getParameter("id1");
		if (id == null || "".equals(id.trim())||id1 == null || "".equals(id1.trim()))
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
		Help help = helpService.findById(id);
		if (help == null)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
		int nowPage = Integer
				.parseInt(request.getParameter("nowPage") == null ? "1"
						: request.getParameter("nowPage").trim());
		List<Help> list = helpService.findBySuperId(help.getId(), (nowPage - 1)
				* Constant.EVERY_PAGE_COUNT, Constant.EVERY_PAGE_COUNT);
		int count = helpService.findBySuperId(help.getId());
		departList(request, list, count, nowPage);
		setAttribute(request, "id", help.getId());
		setAttribute(request, "nowPa", request.getParameter("nowPa"));
		setAttribute(request, "id1", id1);
		return mapping.findForward("task");
	}

	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String type = request.getParameter("type");
		if (type == null || "".equals(type.trim()))
		{
			type = "1";
		}
		setAttribute(request, "type", type);
		setAttribute(request, "id", request.getParameter("id"));
		return mapping.findForward("diaoluosercher");
	}

	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GameEquipDao ged=new GameEquipDao();
		PropDao pd=new PropDao();
		String type = request.getParameter("type");
		if (type == null || "".equals(type.trim()))
		{
			type = "1";
		}
		setAttribute(request, "id", request.getParameter("id"));
		setAttribute(request, "type", type);
		String name = request.getParameter("name");
		if (name == null || "".equals(name.trim()))
		{
			setMessage(request, "请输入物品名称");
			return mapping.findForward("diaoluosercher");
		}
		if (name.trim().equals("书信"))
		{
			setMessage(request, "您查询的物品在该类型中暂无相关掉落信息。");
			return mapping.findForward("jieguo");
		}
		Object obj = null;
		int id = 0;
		switch (Integer.parseInt(type.trim()))
		{
			case GoodsType.EQUIP:
				// 装备
				String allName= ged.getEquipName(name.trim());
				EquipCache equipCache = new EquipCache();
				GameEquip equip = equipCache.getByName(allName);
				id = (equip == null ? 0 : equip.getId());
				obj = equip;
				break;
			case GoodsType.PROP:
				// 道具
				String allPropName=pd.getPropName(name.trim());
				PropVO propVo = GuaJiConstant.PROP_NAME.get(allPropName);
				id = (propVo == null ? 0 : propVo.getPropID());
				obj = propVo;
				break;
		}
		if (obj == null)
		{
			setMessage(request, "您查询的物品在该类型中暂无相关信息。");
			return mapping.findForward("jieguo");
		}
		StringBuffer sb = new StringBuffer();
		if (obj instanceof GameEquip)
		{
			GameEquip av = (GameEquip) obj;
			sb.append(av.getName()).append("<br/>");
			sb.append(av.getDes()).append("<br/>");
			sb.append("----------------------<br/>");
			sb.append("耐久:" ).append( av.getEndure()).append("<br/>");
			sb.append("攻击:" ).append( av.getMinAtt() ).append( "-" ).append( av.getMaxAtt()).append( "<br/>");
			sb.append("卖出价格:").append( av.getPrice() ).append("<br/>");
			sb.append("使用等级:" ).append( av.getGrade() ).append( "<br/>");
		}
		else if (obj instanceof PropVO)
		{
			PropVO av = (PropVO) obj;
			sb.append(av.getPropName() ).append( "<br/>");
			sb.append(av.getPropDisplay() ).append( "<br/>");
//			sb.append("----------------------<br/>");
//			sb.append("卖出价格:" + av.getPropSell() + "<br/>");
//			sb.append("使用等级:" + av.getPropReLevel() + "<br/>");
//			sb.append("----------------------<br/>");
			setAttribute(request, "prop_class", av.getPropClass());
			setAttribute(request, "prop_id", av.getPropID());
		}
		setAttribute(request, "display", sb.toString());
		setAttribute(request, "obj", obj);
		List<Guai> list = helpService.findByDiaoluo1(id, type);
		setAttribute(request, "list", list);
		return mapping.findForward("jieguo");
	}

}
