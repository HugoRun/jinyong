package com.pm.dao.mail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;
import com.pm.vo.mail.MailInfoVO;

public class MailInfoDao extends DaoBase {
	
	/**
	 * 获得个人所有邮件
	 * @param p_pk 个人角色id
	 * @param 
	 * @return list 邮件列表
	 */
	public QueryPage getPersonMailList(String pPk,int page_no){
		QueryPage queryPage = null;
		
		List<MailInfoVO> list = new ArrayList<MailInfoVO>();
		
		String count_sql;
		int count = 0;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		MailInfoVO mvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			count_sql = "SELECT count(1) as mail_count from u_mail_info where receive_pk="+pPk+" and now() < (create_time + INTERVAL 7 DAY)";
							
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if( rs.next() )
			{
				count = rs.getInt("mail_count");
			}
			rs.close();

			queryPage = new  QueryPage(page_no,count);
			
			String sql = "SELECT * FROM u_mail_info where receive_pk="+pPk+" and now() < (create_time + INTERVAL 7 DAY) order by unread asc, improtant desc, create_time desc"
							+" limit " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mvo = new MailInfoVO();
				mvo.setMailId(rs.getInt("mail_id"));
				mvo.setReceivePk(rs.getInt("receive_pk"));
				mvo.setSendPk(rs.getInt("send_pk"));
				mvo.setMailType(rs.getInt("mail_type"));
				mvo.setTitle(rs.getString("title"));
				mvo.setContent(rs.getString("content"));
				mvo.setUnread(rs.getInt("unread"));
				mvo.setImprotant(rs.getInt("improtant"));
				mvo.setCreateTime(rs.getString("create_time"));
				mvo.setAttachmentStr(rs.getString("attachment_str"));
				list.add(mvo);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
		queryPage.setResult(list);
		return queryPage;
	}
	
	
	/**
	 * 根据mailId获得个人邮件
	 * @param p_pk 个人角色id
	 * @param 
	 * @return list 邮件列表
	 */
	public MailInfoVO getPersonMailView(String mailId){
		
		String sql = "SELECT * FROM u_mail_info where mail_id="+mailId;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		MailInfoVO mvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mvo = new MailInfoVO();
				mvo.setMailId(rs.getInt("mail_id"));
				mvo.setReceivePk(rs.getInt("receive_pk"));
				mvo.setSendPk(rs.getInt("send_pk"));
				mvo.setMailType(rs.getInt("mail_type"));
				mvo.setTitle(rs.getString("title"));
				mvo.setContent(rs.getString("content"));
				mvo.setUnread(rs.getInt("unread"));
				mvo.setImprotant(rs.getInt("improtant"));
				mvo.setCreateTime(rs.getString("create_time"));
				mvo.setAttachmentStr(rs.getString("attachment_str"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return mvo;
	}
	
	/**
	 * 根据mailId获得个人邮件的类型
	 * @param p_pk 个人角色id
	 * @param 
	 * @return list 邮件列表
	 */
	public int getMailTypeById(String mailId){
		
		String sql = "SELECT mail_type from u_mail_info where mail_id="+mailId;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		int mail_type = 2;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mail_type = rs.getInt("mail_type");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return mail_type;
	}
	
	
	
	/**
	 * 根据帮派id群发帮派邮件信息(未完成)
	 * @prarm tong_id		帮派id
	 * @prarm title 		标题
	 * @param context 		内容
	 * @return if sussend return 1,else return -1;
	 
	public int insertTongMail(int receive_pk,int send_pk,String title,String content,int improtant){
		int result = -1;
		String sql = "INSERT INTO u_mail_info values(null,"+receive_pk+" ,"+send_pk+" ,'"+title+"','"+content+"',1,"+improtant+" ,now())";
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			result = 1;

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		return result;
	}*/
	
	/**
	 * 查看是否有新邮件
	 * @prarm pPk 个人角色id
	 * @return if have new mail,it will return the number of new mail,else return -1;
	 */
	public int havingNewMail(String pPk){
		int i = -1;
		String sql = "SELECT count(*) from u_mail_info where receive_pk="+pPk+" and now() < (create_time + INTERVAL 7 DAY) and unread = 1 limit 1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				i = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
			return i;
		}
	
	/**
	 * 	根据mailId来删除指定的邮件
	 * @param mailId 邮件id
	 */
	public int deleteMailByid(String mailId,int pPk){
		int i = -1;
		String sql = "delete from u_mail_info where mail_id="+mailId+" and receive_pk="+pPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			i = 1;

			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		return i;
	}
	
	/**
	 * 根据pPk删除个人全部邮件
	 * @param pk 个人角色id
	 * @return if delete sussens return 1,else return -1;
	 */ 
	public int deletePersonMailBypPk(String pk){
		int i = -1;
		String sql = "delete from u_mail_info where receive_pk="+pk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			i = 1;
		} catch (SQLException e){
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return i;
	}
	
	/**
	 * 根据mailId将邮件阅读状态置为已读
	 */
	public void updateMailRead(String mailId){
		String sql = "update u_mail_info set unread = 2 where mail_id="+mailId;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e){
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}
		
		
	/**
	 * 删除所有超过七日的邮件
	 * @param name 个人角色名
	 * @return p_pk 个人角色id，如果没有此角色，则返回-1.
	 */
	public void deleteMailIfOutSeven(){
		String sql = "delete from u_mail_info where now() > (create_time + INTERVAL 7 DAY)";
		logger.debug("删除所有超过七天的邮件的sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
	}


	/**
	 * 删除二级密码设置邮件
	 * @param pk
	 */
	public void deleteSecondPassMail(int u_pk)
	{
		String sql = "delete from u_mail_info where mail_type = 4 and receive_pk = " +
						"(select p_pk from u_part_info where u_pk = "+u_pk+" group by create_time " +
						"asc limit 1)";
		logger.debug("删除二级密码设置邮件sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
	}


	/**
	 * 返回个人是否有邮件类型为4的邮件
	 * @param pk
	 * @return
	 */
	public MailInfoVO getPersonMailTypeList(String pPk,int mail_type)
	{
		
		String sql = "SELECT * FROM u_mail_info where receive_pk = "+pPk+" and mail_type="+mail_type;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		MailInfoVO mvo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				mvo = new MailInfoVO();
				mvo.setMailId(rs.getInt("mail_id"));
				mvo.setReceivePk(rs.getInt("receive_pk"));
				mvo.setSendPk(rs.getInt("send_pk"));
				mvo.setMailType(rs.getInt("mail_type"));
				mvo.setTitle(rs.getString("title"));
				mvo.setContent(rs.getString("content"));
				mvo.setUnread(rs.getInt("unread"));
				mvo.setImprotant(rs.getInt("improtant"));
				mvo.setCreateTime(rs.getString("create_time"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return mvo;
	}


	/**
	 * 更新邮件的内容
	 * @param mailId
	 * @param string
	 */
	public void updateMail(int mailId, String content)
	{
		String sql = "update u_mail_info set content = '"+content+"' where mail_id = "+mailId+"";
		logger.debug("更新邮件的内容sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
	}
	
	/*public int insertMailReturnId(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		int result = -1;
		String sql = "INSERT INTO u_mail_info values(null,"+receive_pk+","+send_pk+","+mail_type+",'"+StringUtil.gbToISO(title)+"','"+StringUtil.gbToISO(content)+"',1,"+improtant+",now())";
		logger.debug("插入mail="+sql);
		String sql1 = "SELECT LAST_INSERT_ID() ";
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ResultSet rs = ps.executeQuery(sql1);
			while(rs.next()){
				result = rs.getInt(1);
			}
			ps.close();
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		return result;
	}
	//返回邮件主键
	public int insertBonusMail(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		int mail_id = -1;
		String sql = "INSERT INTO u_mail_info values(null,"+receive_pk+","+send_pk+","+mail_type+",'"+StringUtil.gbToISO(title)+"','"+StringUtil.gbToISO(content)+"',1,"+improtant+",now())";
		logger.debug("插入mail="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				mail_id = rs.getInt(1);
			}
			ps.close();
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		return mail_id;
	}
	*//**
	 * 插入邮件信息
	 * @prarm receive_pk 	收信人id 
	 * @prarm send_pk		发信人id
	 * @param mail_type		邮件类型，1为普通邮件，2为系统。
	 * @prarm title 		标题
	 * @param context 		内容
	 * @param improtant  	重要性，默认为1，数字越大越重要
	 * @return if sussend return 1,else return -1;
	 *//*
	public int insertMail(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		int result = -1;
		String sql = "INSERT INTO u_mail_info values(null,"+receive_pk+","+send_pk+","+mail_type+",'"+StringUtil.gbToISO(title)+"','"+StringUtil.gbToISO(content)+"',1,"+improtant+",now())";
		logger.debug("插入mail="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			result = 1;

			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		return result;
	}*/
	
	/**
	 * 插入邮件信息
	 * @prarm receive_pk 	收信人id 
	 * @prarm send_pk		发信人id
	 * @param mail_type		邮件类型，1为普通邮件，2为系统。
	 * @prarm title 		标题
	 * @param context 		内容
	 * @param improtant  	重要性，默认为1，数字越大越重要
	 * @return if sussend return 1,else return -1;
	 */
	public int addAndReturnKey(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		int result = -1;
		String sql = "INSERT INTO u_mail_info values(null,"+receive_pk+","+send_pk+","+mail_type+",'"+StringUtil.gbToISO(title)+"','"+StringUtil.gbToISO(content)+"',1,"+improtant+",now(),'')";
		logger.debug("插入mail="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		return result;
	}
	
	/**
	 * 插入邮件信息
	 * @prarm receive_pk 	收信人id 
	 * @prarm send_pk		发信人id
	 * @param mail_type		邮件类型，1为普通邮件，2为系统。
	 * @prarm title 		标题
	 * @param context 		内容
	 * @param improtant  	重要性，默认为1，数字越大越重要
	 * @return if sussend return 1,else return -1;
	 */
	public int addAndReturnKey(MailInfoVO mail){
		if( mail==null )
		{
			return -1;
		}
		int result = -1;
		StringBuffer sql = new StringBuffer();
			
		sql.append("INSERT INTO u_mail_info values(null");
		sql.append(",").append(mail.getReceivePk());
		sql.append(",").append(mail.getSendPk());
		sql.append(",").append(mail.getMailType());
		sql.append(",'").append(mail.getTitle()).append("'");
		sql.append(",'").append(mail.getContent()).append("'");
		sql.append(",'1'");
		sql.append(",").append(mail.getImprotant());
		sql.append(",now()");
		sql.append(",'").append(mail.getAttachmentStr()).append("'");
		sql.append(")");
		
		logger.debug("插入mail="+sql.toString());
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.execute(sql.toString());
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
				mail.setMailId(result);
			}
			rs.close();
			stmt.close();
		}catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	
}
	
