package com.lw.service.UnchartedRoom;

import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.web.service.room.RoomService;
import com.lw.dao.UnchartedRoom.UnchartedRoomDAO;
import com.lw.vo.UnchartedRoom.UnchartedRoomVO;

public class UnchartedRoomService
{
	// ��ҵĵ�ͼ���� �����ؾ�״̬
	public void updatePlayerUnchartedRoomState(RoleEntity roleinfo,
			String new_scene_id)
	{
		String scene_id = roleinfo.getBasicInfo().getSceneId();
		RoomService rs = new RoomService();
		if (scene_id == null || scene_id.equals("") || scene_id.equals("null"))
		{
			return;
		}
		else
		{
			SceneVO scene = rs.getById(scene_id + "");
			SceneVO scene_new = rs.getById(new_scene_id + "");
			if (MapType.UNCHARTEDROOM == scene_new.getMap().getMapType())
			{
				inUnchartedRoom(roleinfo.getBasicInfo().getPPk());
			}
			if (MapType.UNCHARTEDROOM == scene.getMap().getMapType())
			{
				outUnchartedRoom(roleinfo.getBasicInfo().getPPk());
			}
		}
	}

	public boolean ifPlayerHaveGetPrize()
	{
		int num = getUnchartedRoomNum();
		if (num < 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	// ����������ߺ���ؾ���ͼ��Ϣ
	public void updateOfflinePlayerUnchartedRoomState(RoleEntity roleinfo)
	{
		String scene_id = roleinfo.getBasicInfo().getSceneId();
		RoomService rs = new RoomService();
		if (scene_id == null || scene_id.equals("") || scene_id.equals("null"))
		{
			return;
		}
		else
		{
			SceneVO scene = rs.getById(scene_id + "");
			if (MapType.UNCHARTEDROOM == scene.getMap().getMapType())
			{
				outUnchartedRoom(roleinfo.getBasicInfo().getPPk());
				int resurrection_point = rs.getResurrectionPoint(roleinfo);
				roleinfo.getBasicInfo().updateSceneId(resurrection_point + "");
			}
		}
	}
	
	// ����������ߺ���ؾ���ͼ��Ϣ
	public void updateOfflinePlayerTianguanState(RoleEntity roleinfo)
	{
		String scene_id = roleinfo.getBasicInfo().getSceneId();
		RoomService rs = new RoomService();
		if (scene_id == null || scene_id.equals("") || scene_id.equals("null"))
		{
			return;
		}
		else
		{
			SceneVO scene = rs.getById(scene_id + "");
			if (MapType.TIANGUAN == scene.getMap().getMapType())
			{
				int resurrection_point = rs.getResurrectionPoint(roleinfo);
				roleinfo.getBasicInfo().updateSceneId(resurrection_point + "");
			}
		}
	}

	// �õ���Ҹ���
	private int getUnchartedRoomNum()
	{
		UnchartedRoomDAO dao = new UnchartedRoomDAO();
		return dao.getUnchartedRoomPlayerNum();
	}

	// �Ƿ��и���ҵļ�¼
	private UnchartedRoomVO getUnchartedRoomRecord(int p_pk)
	{
		UnchartedRoomDAO dao = new UnchartedRoomDAO();
		UnchartedRoomVO vo = dao.getUnchartedRoomPlayerVO(p_pk);
		return vo;
	}

	// ������ҽ����ؾ���ʱ�� ����
	private boolean inUnchartedRoom(int p_pk)
	{
		UnchartedRoomDAO dao = new UnchartedRoomDAO();
		UnchartedRoomVO vo = getUnchartedRoomRecord(p_pk);
		if (vo == null)
		{
			dao.insertInUnchartedRoomState(p_pk);
		}
		else
		{
			dao.updateInUnchartedRoomState(p_pk);
		}
		return true;
	}

	// ��������뿪�ؾ���ʱ���״̬
	private boolean outUnchartedRoom(int p_pk)
	{
		UnchartedRoomDAO dao = new UnchartedRoomDAO();
		dao.updateOutUnchartedRoomState(p_pk);
		return true;
	}
}
