package com.ls.ben.dao.task;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.task.UTaskVO;
import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pub.db.mysql.SqlData;

/**
 * 功能:
 * 
 * @author 刘帅 3:11:12 PM
 */
public class UTaskDAO  extends DaoBase{
	SqlData con;

	/**
	 * 添加对话任务
	 */
	public void getUTaskAdd(String pPk, String pName, String tZu, String tPx,
			String tId, String tTitle, String tType, String tXrwnpcId,
			String tNext, String createTime, String tTime) {
		try {
			con = new SqlData();
			String sql = "insert into u_task(t_pk,p_pk,p_name,t_zu,t_px,t_id,t_title,t_type,t_xrwnpc_id,t_next,create_time,t_time) values(null,'"
					+ pPk
					+ "','"
					+ pName
					+ "','"
					+ tZu
					+ "','"
					+ tPx
					+ "','"
					+ tId
					+ "','"
					+ tTitle
					+ "','"
					+ tType
					+ "','"
					+ tXrwnpcId
					+ "','" + tNext + "','" + createTime + "','" + tTime + "')";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	/**
	 * 修改对话任务
	 */
	public void getUTaskUpdate(String pPk, String pName, String tZu,
			String tPx, String tId, String tTitle, String tType,
			String tXrwnpcId, String tNext, String createTime, String tTime) {
		try {
			con = new SqlData();
			String sql = "update u_task set t_px='" + tPx + "',t_id='" + tId
					+ "',t_title='" + tTitle + "',t_type='" + tType
					+ "',t_xrwnpc_id='" + tXrwnpcId + "',t_next='" + tNext
					+ "',create_time='" + createTime + "',t_time='" + tTime
					+ "' where p_pk='" + pPk + "' and t_zu='" + tZu + "'";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	/**
	 * 删除对话任务
	 */
	public void getUTaskDelete(String tPk) {
		try {
			con = new SqlData();
			String sql = "delete from u_task where t_pk='" + tPk + "'";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	/**
	 * 添加杀怪任务
	 */
	public void getUTaskAddXG(String pPk, String pName, String tZu, String tPx,
			String tId, String tTitle, String tType, String tXrwnpcId,
			String tNext, String tKilling, String tKillingNo,
			String createTime, String tTime) {
		try {
			con = new SqlData();
			String sql = "insert into u_task(t_pk,p_pk,p_name,t_zu,t_px,t_id,t_title,t_type,t_xrwnpc_id,t_next,t_killing,t_killing_no,create_time,t_time) values(null,'"
					+ pPk
					+ "','"
					+ pName
					+ "','"
					+ tZu
					+ "','"
					+ tPx
					+ "','"
					+ tId
					+ "','"
					+ tTitle
					+ "','"
					+ tType
					+ "','"
					+ tXrwnpcId
					+ "','"
					+ tNext
					+ "','"
					+ tKilling
					+ "','"
					+ tKillingNo
					+ "','" + createTime + "','" + tTime + "')";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	/**
	 * 修改杀怪任务
	 */
	public void getUTaskUpdateXG(String pPk, String pName, String tZu,
			String tPx, String tId, String tTitle, String tType,
			String tXrwnpcId, String tNext, String tKilling, String tKillingNo,
			String tKillingOk, String createTime, String tTime) {
		try {
			con = new SqlData();
			String sql = "update  u_task set t_px='" + tPx + "',t_id='" + tId
					+ "',t_title='" + tTitle + "',t_type='" + tType
					+ "',t_xrwnpc_id='" + tXrwnpcId + "',t_next='" + tNext
					+ "',t_killing='" + tKilling + "',t_killing_no='"
					+ tKillingNo + "',t_killing_ok='" + tKillingOk
					+ "',create_time='" + createTime + "',t_time='" + tTime
					+ "' where p_pk='" + pPk + "' and t_zu='" + tZu + "'";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	/**
	 * 删除杀怪任务
	 */
	public void getUTaskDeleteXG(String tPk) {
		try {
			con = new SqlData();
			String sql = "delete from u_task where t_pk='" + tPk + "'";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	/**
	 * 添加携带类任务
	 */
	public void getUTaskAddXD(String pPk, String pName, String tZu, String tPx,
			String tId, String tTitle, String tType, String tXrwnpcId,
			String tNext, String tGoods, String tGoodsNo, String tGoodszb,
			String tGoodszbNumber, String createTime, String tTime) {
		try {
			con = new SqlData();
			String sql = "insert into u_task"
					+ "(t_pk,p_pk,p_name,t_zu,t_px,t_id,t_title,t_type,t_xrwnpc_id,t_next,"
					+ "t_goods,t_goods_no,t_goodszb,t_goodszb_number,create_time,t_time) "
					+ "values(null,'"
					+ pPk
					+ "','"
					+ pName
					+ "','"
					+ tZu
					+ "','"
					+ tPx
					+ "','"
					+ tId
					+ "','"
					+ tTitle
					+ "','"
					+ tType
					+ "','"
					+ tXrwnpcId
					+ "','"
					+ tNext
					+ "','"
					+ tGoods
					+ "','"
					+ tGoodsNo
					+ "','"
					+ tGoodszb
					+ "','"
					+ tGoodszbNumber
					+ "','"
					+ createTime
					+ "','" + tTime + "')";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
	
	/**
	 * 修改携带类任务
	 */
	public void getUTaskUpdateXD(String pPk, String pName, String tZu, String tPx,
			String tId, String tTitle, String tType, String tXrwnpcId,
			String tNext, String tGoods, String tGoodsNo, String tGoodszb,
			String tGoodszbNumber, String createTime, String tTime) {
		try {
			con = new SqlData();
			String sql = "update u_task set  t_px='"
					+ tPx
					+ "',t_id='"
					+ tId
					+ "',t_title='"
					+ tTitle
					+ "',t_type='"
					+ tType
					+ "',t_xrwnpc_id='"
					+ tXrwnpcId
					+ "',t_next='"
					+ tNext
					+ "',t_goods='"
					+ tGoods
					+ "',t_goods_no='"
					+ tGoodsNo
					+ "',t_goodszb='"
					+ tGoodszb
					+ "',t_goodszb_number='"
					+ tGoodszbNumber
					+ "',create_time='"
					+ createTime
					+ "',t_time='" + tTime + "' where p_pk='" + pPk + "' and t_zu='" + tZu + "'";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
	/**
	 * 删除杀怪任务
	 */
	public void getUTaskDeleteXD(String tPk) {
		try {
			con = new SqlData();
			String sql = "delete from u_task where t_pk='" + tPk + "'";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
	
	
	/**
	 * 添加复合类任务
	 */
	public void getUTaskAddFH(String pPk,String pName,String tZu,String tPx,String tId,String tTitle,String tType,
			String tXrwnpcId,String tNext,String tPoint,String tZjdwp,
			String tZjdwpNumber,String tZjdzb,String tZjdzbNumber,String tDjscwp,String tDjsczb,String createTime,String tTime) {
		try {
			con = new SqlData(); 
			String sql = "insert into u_task "
				+ "(t_pk,p_pk,p_name,t_zu,t_px,t_id,t_title,t_type,t_xrwnpc_id,t_next,"
				+ "t_point,t_zjdwp,t_zjdwp_number,t_zjdzb,t_zjdzb_number,t_djscwp,t_djsczb,create_time,t_time) "
				+ "values(null,'"
				+ pPk + "','" + pName + "','" + tZu + "','" + tPx + "','" + tId
				+ "','" + tTitle + "','" + tType + "','" + tXrwnpcId + "','" + tNext
				+ "','" + tPoint + "','" + tZjdwp + "','"
				+ tZjdwpNumber + "','"+tZjdzb+"','"+tZjdzbNumber+"','"+tDjscwp+"','"+tDjsczb+"','" + createTime + "','" + tTime + "')";
			
			
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
	/**
	 * 修改复合类任务
	 */
	public void getUTaskUpdateFH(String pPk,String pName,String tZu,String tPx,String tId,String tTitle,String tType,
			String tXrwnpcId,String tNext,String tPoint,String tZjdwp,
			String tZjdwpNumber,String tZjdzb,String tZjdzbNumber,String tDjscwp,String tDjsczb,String createTime,String tTime) {
		try {
			con = new SqlData();
			String sql = "update u_task set t_px='" + tPx + "',t_id='" + tId
			+ "',t_title='" + tTitle + "',t_type='" + tType + "',t_xrwnpc_id='" + tXrwnpcId + "',t_next='" + tNext
			+ "',t_point='" + tPoint + "',t_zjdwp='" + tZjdwp + "',t_zjdwp_number='"
			+ tZjdwpNumber + "',t_zjdzb='"+tZjdzb+"',t_zjdzb_number='"+tZjdzbNumber+"',t_djscwp='"+tDjscwp+"',t_djsczb='"
			+tDjsczb+"',create_time='" + createTime + "',t_time='" + tTime + "' where p_pk='" + pPk + "' and t_zu='" + tZu + "'";
		con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
	/**
	 * 通过角色ID 和任务ID 查询信息
	 */
	public UTaskVO getUTaskPk(String pPk, String tId) {
		try {
			con = new SqlData();
			String sql = "select * from u_task where p_pk='" + pPk
					+ "' and t_id='" + tId + "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = new UTaskVO();
			while (rs.next()) {
				vo.setTPk(rs.getInt("t_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTPx(rs.getString("t_px"));
				vo.setTId(rs.getInt("t_id"));
				vo.setTTitle(rs.getString("t_title"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNo(rs.getString("t_goods_no"));
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTPet(rs.getInt("t_pet"));
				vo.setTPetNumber(rs.getInt("t_pet_number"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setTTime(rs.getInt("t_time"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	/**
	 * 通过角色ID 和任务ID 查询信息
	 */
	public List<UTaskVO> getUTaskPk(String pPk) {
		try {
			con = new SqlData();
			String sql = "select * from u_task where p_pk='" + pPk + "'";
			ResultSet rs = con.query(sql);
			List<UTaskVO> list = new ArrayList<UTaskVO>();
			while (rs.next()) {
				UTaskVO vo = new UTaskVO();
				vo.setTPk(rs.getInt("t_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTPx(rs.getString("t_px"));
				vo.setTId(rs.getInt("t_id"));
				vo.setTTitle(rs.getString("t_title"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNo(rs.getString("t_goods_no"));
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTPet(rs.getInt("t_pet"));
				vo.setTPetNumber(rs.getInt("t_pet_number"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setTTime(rs.getInt("t_time"));
				list.add(vo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	/**
	 * 通过角色ID 和任务ID 查询信息
	 */
	public UTaskVO getUTaskTPkZu(String tPk,String pPk) {
		try {
			con = new SqlData();
			String sql = "select t_pk,p_pk,t_zu,t_px,t_id,t_type,t_xrwnpc_id,t_next from u_task where p_pk='"+pPk+"' and  t_id='" + tPk + "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = null;
			if (rs.next()) { 
				vo = new UTaskVO();
				vo.setTPk(rs.getInt("t_pk")); 
				vo.setPPk(rs.getInt("p_pk"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTPx(rs.getString("t_px"));
				vo.setTId(rs.getInt("t_id")); 
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
	/**
	 * 通过角色ID 和任务ID 查询信息
	 */
	public UTaskVO getUTaskTPkZuaa(String pPk,String tZu) {
		try {
			con = new SqlData();
			String sql = "select t_pk,p_pk,t_zu,t_px,t_id,t_type,t_xrwnpc_id,t_next from u_task where p_pk='"+pPk+"' and t_zu='" + tZu + "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = null;
			if (rs.next()) { 
				vo = new UTaskVO();
				vo.setTPk(rs.getInt("t_pk"));  
				vo.setPPk(rs.getInt("p_pk"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTPx(rs.getString("t_px"));
				vo.setTId(rs.getInt("t_id")); 
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
	 
	/**
	 * 通过角色ID 和任务ID 查询信息
	 */
	public UTaskVO getUTaskTPk(String tPk) {
		try {
			con = new SqlData();
			String sql = "select t_pk,t_killing,t_killing_no,t_killing_ok from u_task where t_pk='"
					+ tPk + "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = new UTaskVO();
			while (rs.next()) {
				vo.setTPk(rs.getInt("t_pk"));
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTKillingOk(rs.getInt("t_killing_ok"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	/**
	 * 通过角色ID 和任务ID 查询信息
	 */
	public UTaskVO getUTaskTPkGossds(String tPk) {
		try {
			con = new SqlData();
			String sql = "select t_pk,t_goods,t_goods_no,t_goods_ok,t_goodszb,t_goodszb_number,t_goodszb_ok from u_task where t_pk='"
					+ tPk + "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = new UTaskVO();
			while (rs.next()) {
				vo.setTPk(rs.getInt("t_pk"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNo(rs.getString("t_goods_no"));
				vo.setTGoodsOk(rs.getString("t_goods_ok"));
				vo.setTGoodszb(rs.getString("t_goodszb"));
				vo.setTGoodszbNumber(rs.getString("t_goodszb_number"));
				vo.setTGoodszbOk(rs.getString("t_goodszb_ok"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
	
	/**
	 * 通过角色ID 和任务ID 查询信息
	 */
	public UTaskVO getUTaskTPkPoint(String tPk) {
		try {
			con = new SqlData();
			 
			String sql = "select t_pk,t_point,t_point_no,t_zjdwp,t_zjdwp_number,t_zjdwp_ok,t_zjdzb,t_zjdzb_number,t_zjdzb_ok,t_djscwp,t_djsczb  from u_task where t_pk='"
					+ tPk + "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = new UTaskVO();
			while (rs.next()) {
				vo.setTPk(rs.getInt("t_pk"));
				vo.setTPoint(rs.getString("t_point"));
				vo.setTPointNo(rs.getString("t_point_no"));
				vo.setTZjdwp(rs.getString("t_zjdwp"));
				vo.setTZjdwpNumber(rs.getInt("t_zjdwp_number"));
				vo.setTZjdwpOk(rs.getInt("t_zjdwp_ok"));
				vo.setTZjdzb(rs.getString("t_zjdzb"));
				vo.setTZjdzbNumber(rs.getInt("t_zjdzb_number"));
				vo.setTZjdzbOk(rs.getInt("t_zjdzb_ok"));
				vo.setTDjscwp(rs.getInt("t_djscwp"));
				vo.setTDjsczb(rs.getInt("t_djsczb"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
	
	
	/**
	 * 通过角色ID 和任务ID 查询信息
	 */
	public UTaskVO getUTask(String pPk, String tId) {
		try {
			con = new SqlData();
			String sql = "select * from u_task where p_pk='" + pPk
					+ "' and t_next='" + tId + "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = new UTaskVO();
			while (rs.next()) {
				vo.setTPk(rs.getInt("t_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTPx(rs.getString("t_px"));
				vo.setTId(rs.getInt("t_id"));
				vo.setTTitle(rs.getString("t_title"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNo(rs.getString("t_goods_no"));
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTPet(rs.getInt("t_pet"));
				vo.setTPetNumber(rs.getInt("t_pet_number"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setTTime(rs.getInt("t_time"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	/**
	 * 通过角色ID 和NPCID 查询信息 任务类型
	 */
	public List<UTaskVO> getUTaskNpcId(String pPk, String npcId, String tType) {
		try {
			con = new SqlData();
			String sql = "select * from u_task where p_pk='" + pPk
					+ "' and t_type='" + tType + "' and t_killing='" + npcId
					+ "'";
			ResultSet rs = con.query(sql);
			List<UTaskVO> list = new ArrayList<UTaskVO>();
			while (rs.next()) {
				UTaskVO vo = new UTaskVO();
				vo.setTPk(rs.getInt("t_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPName(rs.getString("p_name"));
				vo.setTZu(rs.getString("t_zu"));
				vo.setTPx(rs.getString("t_px"));
				vo.setTId(rs.getInt("t_id"));
				vo.setTTitle(rs.getString("t_title"));
				vo.setTType(rs.getInt("t_type"));
				vo.setTXrwnpcId(rs.getInt("t_xrwnpc_id"));
				vo.setTNext(rs.getInt("t_next"));
				vo.setTGoods(rs.getString("t_goods"));
				vo.setTGoodsNo(rs.getString("t_goods_no"));
				vo.setTKilling(rs.getString("t_killing"));
				vo.setTKillingNo(rs.getInt("t_killing_no"));
				vo.setTKillingOk(rs.getInt("t_killing_ok"));
				vo.setTPet(rs.getInt("t_pet"));
				vo.setTPetNumber(rs.getInt("t_pet_number"));
				vo.setCreateTime(rs.getString("create_time"));
				vo.setTTime(rs.getInt("t_time"));
				list.add(vo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	/**
	 * 返回杀怪数量
	 */
	public int tKillingOk(String tPk) {
		try {
			con = new SqlData();
			String sql = "select t_killing_ok from u_task where t_pk='" + tPk
					+ "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = new UTaskVO();
			int tKillingOk = 0;
			while (rs.next()) {
				tKillingOk = rs.getInt("t_killing_ok");
			}
			return tKillingOk;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return 0;
	}

	/**
	 * 返回杀怪数量
	 */
	public int tKilling(String tPk) {
		try {
			con = new SqlData();
			String sql = "select t_killing_no from u_task where t_pk='" + tPk
					+ "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = new UTaskVO();
			int tKillingNo = 0;
			while (rs.next()) {
				tKillingNo = rs.getInt("t_killing_no");
			}
			return tKillingNo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return 0;
	}

	/**
	 * 修改杀怪数量
	 * */
	public void tKillingOKUpdate(int t_killing_ok, String tPk) {
		try {
			con = new SqlData();
			String sql = "update u_task set t_killing_ok='" + t_killing_ok
					+ "' where t_pk='" + tPk + "'";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	public int getUTaskTPk(String pPk, String tId) {
		try {
			con = new SqlData();
			String sql = "select t_pk from u_task where p_pk='" + pPk
					+ "' and t_next='" + tId + "'";
			ResultSet rs = con.query(sql);
			int t_pk = 0;
			while (rs.next()) {
				t_pk = rs.getInt("t_pk");
			}
			return t_pk;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return 0;
	}
	
	/**
	 * 通过菜单ID 查询菜单类型
	 */
	public int menuType(int menuID) {
		try {
			con = new SqlData();
			String sql = "select menu_type from operate_menu_info where id='" + menuID + "'";
			ResultSet rs = con.query(sql);
			UTaskVO vo = new UTaskVO();
			int menu_type = 0;
			while (rs.next()) {
				menu_type = rs.getInt("menu_type");
			}
			return menu_type;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return 0;
	}
	
	/**
	 * 判断任务是否完成
	 */
	public boolean isCompleted(int p_pk,int task_id)
	{
		boolean isCompleted = false;
		
		String sql = "select t_pk from u_task where p_pk=" + p_pk + "  ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		
		return isCompleted ;
	}
}
