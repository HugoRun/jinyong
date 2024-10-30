package com.pm.dao.systemInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.pub.db.DBConnection;
import com.pm.vo.sysInfo.SystemControlInfoVO;
import com.pm.vo.sysInfo.SystemInfoVO;

public class SysInfoDao extends DaoBase {
	/**
	 * ����ϵͳ��Ϣ
	 * @param p_pk ���˽�ɫid�������ϵͳ������ر�ֱ֪ͨ��д�㡣
	 * @param info_type ��Ϣ���� 1�Ǹ�����Ϣ��2��ϵͳ���棬3���ر�֪ͨ,4��С���ȡ� 10��������Ϣ,5��pk֪ͨ��Ϣ,7��װ��չʾ��Ϣ
	 * @param info ��Ҫ�������Ϣ
	 * @return if sussend return 1,else return -1
	 */
	public int insertSysInfo(int p_pk,int info_type,String info){
		int i = -1;
		String sql = "insert into s_system_info values (null,'"+p_pk+"','"+info_type+"','"+info+"',now())";
		logger.debug("SysInfoDao insertSysInfo :sql="+sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			i = 1;

		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
			
		return i;
	}
	
	/**
	 * ����ϵͳ��Ϣ
	 * @param p_pk ���˽�ɫid�������ϵͳ������ر�ֱ֪ͨ��д�㡣
	 * @param info_type ��Ϣ���� 1�Ǹ�����Ϣ��2��ϵͳ���棬3���ر�֪ͨ,4��С���ȡ� 10��������Ϣ,5��pk֪ͨ��Ϣ,7��װ��չʾ��Ϣ
	 * @param info ��Ҫ�������Ϣ
	 * @return if sussend return 1,else return -1
	 */
	public int insertSysInfoTongTime(int p_pk,int info_type,String info,String time){
		int i = -1;
		String sql = "insert into s_system_info values (null,'"+p_pk+"','"+info_type+"','"+info+"','"+time+"')";
		logger.debug("SysInfoDao insertSysInfo :sql="+sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			i = 1;

		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
			
		return i;
	}
	
	/**
	 * ����ϵͳ��Ϣ
	 * @param p_pk ���˽�ɫid�������ϵͳ������ر�ֱ֪ͨ��д�㡣
	 * @param info_type ��Ϣ���� 1�Ǹ�����Ϣ��2��ϵͳ���棬3���ر�֪ͨ,4��С���ȡ� 10��������Ϣ,5��pk֪ͨ��Ϣ,7��װ��չʾ��Ϣ
	 * @param info ��Ҫ�������Ϣ
	 * @return if sussend return 1,else return -1
	 */
	public int insertSysInfo(int p_pk,int info_type,String info,int second){
		int i = -1;
		String sql = "insert into s_system_info values (null,'"+p_pk+"','"+info_type+"','"+info+"',NOW()+INTERVAL "+second+" second)";
		logger.debug("SysInfoDao insertSysInfo :sql="+sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			i = 1;

		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
			
		return i;
	}
	
	/**
	 * ����ϵͳ��Ϣ
	 * @param p_pk ���˽�ɫid�������ϵͳ������ر�ֱ֪ͨ��д�㡣
	 * @param info_type ��Ϣ���� 1�Ǹ�����Ϣ��2��ϵͳ���棬3���ر�֪ͨ,4��С���ȡ� 10��������Ϣ,5��pk֪ͨ��Ϣ,7��װ��չʾ��Ϣ
	 * @param info ��Ҫ�������Ϣ
	 * @return if sussend return 1,else return -1
	 */
	public int insertSysInfo(int p_pk,int info_type,String info,String sendTime){
		int i = -1;
		String sql = "insert into s_system_info values (null,'"+p_pk+"','"+info_type+"','"+info+"','"+sendTime+"')";
		logger.debug("SysInfoDao insertSysInfo :sql="+sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			i = 1;

		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
			
		return i;
	}
	
	/**
	 * ѡ��ϵͳ��Ϣ�����Ϣ����
	 * @param p_pk
	 * @param number Ҫѡ����Ϣ��Ŀ
	 * @param info_type ��Ϣ���� 1�Ǹ�����Ϣ��2��ϵͳ���棬3���ر�֪ͨ,4��С���ȡ� 10��������Ϣ,5��pk֪ͨ��Ϣ,7��װ��չʾ��Ϣ
	 * @return if sussend return 1,else return -1
	 */
	public List<SystemInfoVO> selectSysInfo(int p_pk,int info_type,int number){
		List<SystemInfoVO> list = new ArrayList<SystemInfoVO>();
		String sql = "select * from s_system_info where p_pk="+p_pk+" and info_type='"+info_type+"' limit "+number;
		logger.debug("ѡ��ϵͳ��Ϣ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		
		SystemInfoVO sIvo = null;
		try{
			
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				sIvo = new SystemInfoVO();
				sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
				sIvo.setPPk(rs.getInt("p_pk"));
				sIvo.setInfoType(rs.getInt("info_type"));
				sIvo.setSystemInfo(rs.getString("system_info"));
				sIvo.setCreateTime(rs.getString("happen_time"));
				list.add(sIvo);				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
		return list;
	}
	
	
	
	
	/**
	 *  ��ѯϵͳ��Ϣ���и����йص�ϵͳ��Ϣ
	 *  @param pPk ���˽�ɫid. 
	 *  @return SystemInfoVO
	 */
	public SystemInfoVO getSystemSelfInfo(String pPk){
		String sql = "select * from s_system_info where sysInfo_id = (select min(sysInfo_id) from s_system_info " +
				"where NOW() >= happen_time and (happen_time + INTERVAL 10 second) >= NOW() and p_pk="+pPk+" )";
		//logger.debug("ѡ��ȷ��һ��ϵͳ��Ϣ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		SystemInfoVO sIvo = null;
		logger.debug(" ��ѯϵͳ��Ϣ���и����йص�ϵͳ��Ϣ="+sql);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				sIvo = new SystemInfoVO();
				sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
				sIvo.setPPk(rs.getInt("p_pk"));
				sIvo.setInfoType(rs.getInt("info_type"));
				sIvo.setSystemInfo(rs.getString("system_info"));
				sIvo.setCreateTime(rs.getString("happen_time"));
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return sIvo;
	}
	
	
	/**
	 * �������ϵͳ��Ϣ
	 * @return
	 */
	public List<SystemInfoVO> getSystemInfoThree(int p_pk)
	{
		List<SystemInfoVO> list = new ArrayList<SystemInfoVO>();
		/*String sql = "select * from s_system_info where NOW() > happen_time and " +
				"(happen_time + INTERVAL 10 second) > NOW() and ( p_pk=0 or p_pk="
				+p_pk+" ) order by info_type desc,happen_time asc limit 3";*/
		String sql = "select * from s_system_info where NOW() >= happen_time and (happen_time + INTERVAL 10 second) >= NOW() " +
				"and ( (info_type = 1 and p_pk = "+p_pk+") or info_type not in (1,5) ) order by happen_time desc limit 3";

		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(" �������ϵͳ��Ϣ="+sql);
		//System.out.println(" �������ϵͳ��Ϣ="+sql);
		SystemInfoVO sIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				sIvo = new SystemInfoVO();
				sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
				sIvo.setPPk(rs.getInt("p_pk"));
				sIvo.setInfoType(rs.getInt("info_type"));
				sIvo.setSystemInfo(rs.getString("system_info"));
				sIvo.setCreateTime(rs.getString("happen_time"));
				list.add(sIvo); 
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
		return list;		
	}

	/**
	 * ����������ϵͳ��Ϣʱ�䶨��20����
	 * @return
	 */
	public List<SystemInfoVO> getSystemInfoThreeTime(String p_pk)
	{
		List<SystemInfoVO> list = new ArrayList<SystemInfoVO>();
		String sql = "select * from s_system_info where NOW() > happen_time and " +
				"(happen_time + INTERVAL 20 minute) > NOW() and ( (info_type = 1 and p_pk = "+p_pk
				+") or info_type not in (1,5) ) order by happen_time desc limit 5";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(" �������ϵͳ��Ϣ="+sql);
		SystemInfoVO sIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				sIvo = new SystemInfoVO();
				sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
				sIvo.setPPk(rs.getInt("p_pk"));
				sIvo.setInfoType(rs.getInt("info_type"));
				sIvo.setSystemInfo(rs.getString("system_info"));
				sIvo.setCreateTime(rs.getString("happen_time"));
				list.add(sIvo);
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
		return list;		
	}
	/**
	 *  ��ѯϵͳ��Ϣ���е�ϵͳ��Ϣ
	 *  @param pPk ���˽�ɫid. 
	 *  @return SystemInfoVO
	 */
	public SystemInfoVO getSystemInfo(){
		String sql = "select * from s_system_info where sysInfo_id = (select min(sysInfo_id) from s_system_info where NOW() > happen_time and (happen_time + INTERVAL 10 second) > NOW() and p_pk=0 group by info_type desc)";
		logger.debug("��ѯϵͳ��Ϣ���е�ϵͳ��Ϣsql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		SystemInfoVO sIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				sIvo = new SystemInfoVO();
				sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
				sIvo.setPPk(rs.getInt("p_pk"));
				sIvo.setInfoType(rs.getInt("info_type"));
				sIvo.setSystemInfo(rs.getString("system_info"));
				sIvo.setCreateTime(rs.getString("happen_time"));
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return sIvo;
	}
	

	/**
	 * ��ϵͳ��ϢId��ѡ��һ��ϵͳ��Ϣ
	 * @param sysInfoId ϵͳ��Ϣid
	 * @return SystemInfoVO 
	 */
	public SystemInfoVO getSystemInfo(int sysInfoId){
		String sql = "select * from s_system_info where sysInfo_id="+sysInfoId;
		logger.debug("ѡ��ȷ��һ��ϵͳ��Ϣ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		SystemInfoVO sIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				sIvo = new SystemInfoVO();
				sIvo.setSysInfoId(rs.getInt("sysInfo_id"));
				sIvo.setPPk(rs.getInt("p_pk"));
				sIvo.setInfoType(rs.getInt("info_type"));
				sIvo.setSystemInfo(rs.getString("system_info"));
				sIvo.setCreateTime(rs.getString("happen_time"));
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return sIvo;
	}
	
	/**
	 * ɾ������15���ӵ�ϵͳ��Ϣ
	 */
	public void deleteMoreFifteenMinutes(){
		String sql = "delete from s_system_info where now() > (happen_time + INTERVAL 15 minute)";
		logger.debug("ɾ������15���ӵ�sql="+sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}

	/**
	 * ���ϵͳ���Ʊ��еĿ��������Ĺ��ڵȼ����Ƶ�����
	 * @param pk
	 * @param type ���Ʊ��е����͡�1Ϊ��ҵȼ�,2Ϊ����,3Ϊ����,4Ϊ��ν,5Ϊ����ʱ��.
	 */
	public List<SystemControlInfoVO> getSystemInfoControlByPGrade()
	{
		String sql = "select * from u_systeminfo_control where condition_type = 1";
		List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
		logger.debug("����ϵͳ��Ϣ���Ʊ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		SystemControlInfoVO scIvo = null;
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				scIvo = new SystemControlInfoVO();
				scIvo.setControlId(rs.getInt("control_id"));
				scIvo.setCondition(rs.getInt("condition_type"));
				scIvo.setPlayerGrade(rs.getString("player_grade"));
				//scIvo.setTaskId(rs.getInt("task_id"));
				//scIvo.setPopularity(rs.getInt("popularity"));
				//scIvo.setTitle(rs.getString("title"));
				//scIvo.setSendTime(rs.getString("send_time"));
				scIvo.setSendContent(rs.getString("send_content"));
				scIvo.setSendType(rs.getInt("send_type"));
				list.add(scIvo);
				//	map.put(rs.getString("player_grade"), rs.getString("send_content"));
				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return list;
	}
	
	
	/**
	 * ���ϵͳ���Ʊ��еĿ��������Ĺ���������Ƶ�����
	 * @param pk
	 * @param type ���Ʊ��е����͡�1Ϊ��ҵȼ�,2Ϊ����,3Ϊ����,4Ϊ��ν,5Ϊ����ʱ��.
	 */
	public Map<Integer,String> getSystemInfoControlByTaskId()
	{
		String sql = "select * from u_systeminfo_control where condition_type = 2";
		Map<Integer,String> map = new HashMap<Integer,String>();
		logger.debug("����ϵͳ��Ϣ���Ʊ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map.put(rs.getInt("task_id"), rs.getString("send_content"));
				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return map;
	}
	
	/**
	 * ���ϵͳ���Ʊ��еĿ��������Ĺ���������Ƶ�����
	 * @param pk
	 * @param type ���Ʊ��е����͡�1Ϊ��ҵȼ�,2Ϊ����,3Ϊ����,4Ϊ��ν,5Ϊ����ʱ��.
	 */
	public List<SystemControlInfoVO> getSystemInfoControlListByTaskId()
	{
		String sql = "select * from u_systeminfo_control where condition_type = 2";
		List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
		logger.debug("����ϵͳ��Ϣ���Ʊ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				scIvo = new SystemControlInfoVO();
				scIvo.setControlId(rs.getInt("control_id"));
				scIvo.setCondition(rs.getInt("condition_type"));
				scIvo.setPlayerGrade(rs.getString("player_grade"));
				scIvo.setTaskId(rs.getInt("task_id"));
//				scIvo.setPopularity(rs.getInt("popularity"));
//				scIvo.setTitle(rs.getString("title"));
//				scIvo.setSendTime(rs.getString("send_time"));
				scIvo.setSendContent(rs.getString("send_content"));
				scIvo.setSendType(rs.getInt("send_type"));
				list.add(scIvo);
				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return list;
	}
			
	
	/**
	 * ���ϵͳ���Ʊ��еĿ��������Ĺ����������Ƶ�����
	 * @param pk
	 * @param type ���Ʊ��е����͡�1Ϊ��ҵȼ�,2Ϊ����,3Ϊ����,4Ϊ��ν,5Ϊ����ʱ��.
	 */
	public Map<Integer,String> getSystemInfoControlByPopularity()
	{
		String sql = "select * from u_systeminfo_control where condition_type = 3";
		Map<Integer,String> map = new HashMap<Integer,String>();
		logger.debug("����ϵͳ��Ϣ���Ʊ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map.put(rs.getInt("popularity"), rs.getString("send_content"));
				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return map;
	}
	
	/**
	 * ���ϵͳ���Ʊ��еĿ��������Ĺ��ڳ�ν���Ƶ�����
	 * @param pk
	 * @param type ���Ʊ��е����͡�1Ϊ��ҵȼ�,2Ϊ����,3Ϊ����,4Ϊ��ν,5Ϊ����ʱ��.
	 */
	public Map<String,String> getSystemInfoControlByTitle()
	{
		String sql = "select * from u_systeminfo_control where condition_type = 4";
		Map<String,String> map = new HashMap<String,String>();
		logger.debug("����ϵͳ��Ϣ���Ʊ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map.put(rs.getString("title"), rs.getString("send_content"));
				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return map;
	}
	
	/**
	 * ���ϵͳ���Ʊ��еĿ��������Ĺ��ڳ�ν���Ƶ�����
	 * @param pk
	 * @param type ���Ʊ��е����͡�1Ϊ��ҵȼ�,2Ϊ����,3Ϊ����,4Ϊ��ν,5Ϊ����ʱ��.
	 */
	public Map<String,String> getSystemInfoControlByTitle(String title_id)
	{
		String sql = "select * from u_systeminfo_control where condition_type = 4 and title ='"+title_id+"'";
		Map<String,String> map = new HashMap<String,String>();
		logger.debug("����ϵͳ��Ϣ���Ʊ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map.put(rs.getString("title"), rs.getString("send_content"));
				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return map;
	}
	
	/**
	 * ���ϵͳ���Ʊ��еĿ��������Ĺ��ڳ�ν���Ƶ�����
	 * @param pk
	 * @param type ���Ʊ��е����͡�1Ϊ��ҵȼ�,2Ϊ����,3Ϊ����,4Ϊ��ν,5Ϊ����ʱ��.
	 */
	public List<SystemControlInfoVO> getSystemInfoControlListByTitle(String title_id)
	{
		String sql = "select * from u_systeminfo_control where condition_type = 4 and title ='"+title_id+"'";
		List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
		
		logger.debug("����ϵͳ��Ϣ���Ʊ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				scIvo = new SystemControlInfoVO();
				scIvo.setControlId(rs.getInt("control_id"));
				scIvo.setCondition(rs.getInt("condition_type"));
				scIvo.setPlayerGrade(rs.getString("player_grade"));
				scIvo.setTaskId(rs.getInt("task_id"));
				scIvo.setPopularity(rs.getInt("popularity"));
				scIvo.setTitle(rs.getString("title"));
				scIvo.setSendTime(rs.getString("send_time"));
				scIvo.setSendContent(rs.getString("send_content"));
				scIvo.setSendType(rs.getInt("send_type"));
				list.add(scIvo);				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return list;
	}
	

	/**
	 * ���ϵͳ���Ʊ��еĿ��������Ĺ����ۺϿ��Ƶ�����
	 * @param pk
	 */
	public List<SystemControlInfoVO> getSystemInfoControlByPCollage()
	{
		String sql = "select * from u_systeminfo_control where condition_type = 6 ";
		List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
		SystemControlInfoVO infovo = null;
		logger.debug("ѡ��ȷ��һ��ϵͳ��Ϣ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				infovo = new SystemControlInfoVO();
				infovo.setControlId(rs.getInt("control_id"));
				infovo.setCondition(rs.getInt("condition_type"));
				infovo.setPlayerGrade(rs.getString("player_grade"));
				infovo.setTaskId(rs.getInt("task_id"));
				infovo.setPopularity(rs.getInt("popularity"));
				infovo.setTitle(rs.getString("title"));
				infovo.setSendTime(rs.getString("send_time"));
				infovo.setSendContent(rs.getString("send_content"));
				infovo.setSendType(rs.getInt("send_type"));
				list.add(infovo);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return list;
	}
	
	
	public List<SystemControlInfoVO> getSystemInfoControlByNewPlayer()
	{
		String sql = "select * from u_systeminfo_control where condition_type = 7 ";
		List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
		SystemControlInfoVO infovo = null;
		logger.debug("ѡ��ȷ��һ��ϵͳ��Ϣ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				infovo = new SystemControlInfoVO();
				infovo.setControlId(rs.getInt("control_id"));
				infovo.setCondition(rs.getInt("condition_type"));
				infovo.setPlayerGrade(rs.getString("player_grade"));
				infovo.setTaskId(rs.getInt("task_id"));
				infovo.setPopularity(rs.getInt("popularity"));
				infovo.setTitle(rs.getString("title"));
				infovo.setSendTime(rs.getString("send_time"));
				infovo.setSendContent(rs.getString("send_content"));
				infovo.setSendType(rs.getInt("send_type"));
				list.add(infovo);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return list;
	}
	

	/**
	 * ��ö�ʱ����ϵͳ��Ϣ��������Ϣ
	 * @return
	 */
	public List getSystemInfoCOntrolByTime()
	{
		String sql = "select * from u_systeminfo_control where condition_type = 5 ";
		List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
		SystemControlInfoVO infovo = null;
		logger.debug("ѡ��ȷ��һ��ϵͳ��Ϣ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				infovo = new SystemControlInfoVO();
				infovo.setControlId(rs.getInt("control_id"));
				infovo.setSendTime(rs.getString("send_time"));
				infovo.setSendContent(rs.getString("send_content"));
				infovo.setSendType(rs.getInt("send_type"));
				list.add(infovo);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return list;
	}
	
	/**
	 * ��ö�ʱ����ϵͳ��Ϣ��������Ϣ
	 * @return
	 */
	public SystemControlInfoVO getSystemInfoByTypeAndID(String type,String id)
	{
		String sql = "select * from u_systeminfo_control where condition_type = '"+type+"' and task_id = '"+id+"' limit 1";
		SystemControlInfoVO infovo = null;
		logger.debug("ѡ��ȷ��һ��ϵͳ��Ϣ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				infovo = new SystemControlInfoVO();
				infovo.setControlId(rs.getInt("control_id"));
				infovo.setSendTime(rs.getString("send_time"));
				infovo.setSendContent(rs.getString("send_content"));
				infovo.setSendType(rs.getInt("send_type"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return infovo;
	}
	
	/**
	 * ��ö�ʱ����ϵͳ��Ϣ��������Ϣ
	 * @return
	 */
	public List<SystemControlInfoVO> getSystemInfoByNewPlayerGuide(String type,String id)
	{
		List<SystemControlInfoVO> list = new ArrayList<SystemControlInfoVO>();
		String sql = "select * from u_systeminfo_control where condition_type = '"+type+"' and task_id = '"+id+"'";
		SystemControlInfoVO infovo = null;
		logger.debug("ѡ��ȷ��һ��ϵͳ��Ϣ��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		//SystemControlInfoVO scIvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				infovo = new SystemControlInfoVO();
				infovo.setControlId(rs.getInt("control_id"));
				infovo.setSendTime(rs.getString("send_time"));
				infovo.setSendContent(rs.getString("send_content"));
				infovo.setPopularity(rs.getInt("popularity"));
				infovo.setSendType(rs.getInt("send_type"));
				list.add(infovo);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return list;
	}
	

	/**
	 * ��������sceneStr���˷���Ϣ
	 * sceneStr������(111,112,113,115)���scene_name����
	 * systemInfo ��Ϣ
	 * @param systemInfo
	 * @param sceneStr
	 */
	public void insertSysInfo(String systemInfo, String sceneStr)
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		List<Integer> list = partInfoDao.getPPkListBySceneStr(sceneStr);
						
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		
		String sql = "insert into s_system_info values (null,?,1,'"+systemInfo+"',now())";
		logger.debug("��������sceneStr���˷���Ϣ="+sql);		
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			
			if(list != null ){
				logger.debug("������="+list.size());
			} else {
				logger.debug("�յ�");
			}
			for(int i=0; i < list.size();i++) {
				int p_pk = list.get(i);
				 ps.setInt(1, p_pk);  
				 //stmt.addBatch(); 
				 ps.addBatch();
			}
			ps.executeBatch();	
			conn.commit();
			ps.close();

			
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}

	/**
	 * ����ʱ�����ֵ�ȵ�ϵͳ��Ϣ��ʾʱ��
	 * @param p_pk
	 * @param buffEffectValue
	 * @param propName
	 */
	public void updateTimeReducePkValue(int p_pk, int buffEffectTime,
			String propName)
	{
		String sql = "update s_system_info set happen_time = (happen_time +  INTERVAL "+buffEffectTime
						+" minute) where p_pk = "+p_pk + " and info_type = 1 and system_info like '%"+propName+"%'";
		logger.debug("="+sql);				
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);			
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}

	/**
	 * ����pPk��ϵͳ��Ϣ��һЩ�ؼ�����ɾ������Ҫ��ϵͳ��Ϣ
	 * @param pPk
	 * @param propName
	 */
	public void deleteByPPkInfo(int pPk, String propName)
	{
		String sql = "delete from s_system_info where info_type = 1 and p_pk="+pPk+" and system_info like '%"+propName+"%' ";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("����pPk��ϵͳ��Ϣ��һЩ�ؼ�����ɾ������Ҫ��ϵͳ��Ϣ="+sql);		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);			
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
	}

	/**
	 *  ��ѯ�Ƿ��� �ض��� ϵͳ��Ϣ
	 * @param pPk
	 * @param propName
	 * @return
	 */
	public boolean selectByPPkInfo(int pPk, String propName)
	{
		boolean flag = false;
		String sql = "select * from s_system_info where info_type = 1 and p_pk="+pPk+" and system_info like '%"+propName+"%' ";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("��ѯ�Ƿ��� �ض��� ϵͳ��Ϣ="+sql);		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag = true;	
			}		
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return flag;
	}

	/**
	 * ����pPk��ϵͳ��Ϣ��һЩ�ؼ���������ϵͳ��Ϣ��ʱ��
	 * @param pPk
	 * @param propName
	
	public void updateByPPkInfo(int pPk, String propName,int buffTime)
	{
		String sql = "update s_system_info set happen_time = happen_time + "+  +" where info_type = 1 and p_pk="+pPk+" and system_info like '%"+propName+"%' ";
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		logger.debug("����pPk��ϵͳ��Ϣ��һЩ�ؼ�����ɾ������Ҫ��ϵͳ��Ϣ="+sql);		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);			
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
	} */
}
