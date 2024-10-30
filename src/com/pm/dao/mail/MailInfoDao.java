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
	 * ��ø��������ʼ�
	 * @param p_pk ���˽�ɫid
	 * @param 
	 * @return list �ʼ��б�
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
			count_sql = "select count(1) as mail_count from u_mail_info where receive_pk="+pPk+" and now() < (create_time + INTERVAL 7 DAY)";
							
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if( rs.next() )
			{
				count = rs.getInt("mail_count");
			}
			rs.close();

			queryPage = new  QueryPage(page_no,count);
			
			String sql = "select * from u_mail_info where receive_pk="+pPk+" and now() < (create_time + INTERVAL 7 DAY) order by unread asc, improtant desc, create_time desc"
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
	 * ����mailId��ø����ʼ�
	 * @param p_pk ���˽�ɫid
	 * @param 
	 * @return list �ʼ��б�
	 */
	public MailInfoVO getPersonMailView(String mailId){
		
		String sql = "select * from u_mail_info where mail_id="+mailId;
		
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
	 * ����mailId��ø����ʼ�������
	 * @param p_pk ���˽�ɫid
	 * @param 
	 * @return list �ʼ��б�
	 */
	public int getMailTypeById(String mailId){
		
		String sql = "select mail_type from u_mail_info where mail_id="+mailId;
		
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
	 * ���ݰ���idȺ�������ʼ���Ϣ(δ���)
	 * @prarm tong_id		����id
	 * @prarm title 		����
	 * @param context 		����
	 * @return if sussend return 1,else return -1;
	 
	public int insertTongMail(int receive_pk,int send_pk,String title,String content,int improtant){
		int result = -1;
		String sql = "insert into u_mail_info values(null,"+receive_pk+" ,"+send_pk+" ,'"+title+"','"+content+"',1,"+improtant+" ,now())";
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
	 * �鿴�Ƿ������ʼ�
	 * @prarm pPk ���˽�ɫid
	 * @return if have new mail,it will return the number of new mail,else return -1;
	 */
	public int havingNewMail(String pPk){
		int i = -1;
		String sql = "select count(*) from u_mail_info where receive_pk="+pPk+" and now() < (create_time + INTERVAL 7 DAY) and unread = 1 limit 1";
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
	 * 	����mailId��ɾ��ָ�����ʼ�
	 * @param mailId �ʼ�id
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
	 * ����pPkɾ������ȫ���ʼ�
	 * @param pk ���˽�ɫid
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
	 * ����mailId���ʼ��Ķ�״̬��Ϊ�Ѷ�
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
	 * ɾ�����г������յ��ʼ�
	 * @param name ���˽�ɫ��
	 * @return p_pk ���˽�ɫid�����û�д˽�ɫ���򷵻�-1.
	 */
	public void deleteMailIfOutSeven(){
		String sql = "delete from u_mail_info where now() > (create_time + INTERVAL 7 DAY)";
		logger.debug("ɾ�����г���������ʼ���sql="+sql);
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
	 * ɾ���������������ʼ�
	 * @param pk
	 */
	public void deleteSecondPassMail(int u_pk)
	{
		String sql = "delete from u_mail_info where mail_type = 4 and receive_pk = " +
						"(select p_pk from u_part_info where u_pk = "+u_pk+" group by create_time " +
						"asc limit 1)";
		logger.debug("ɾ���������������ʼ�sql="+sql);
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
	 * ���ظ����Ƿ����ʼ�����Ϊ4���ʼ�
	 * @param pk
	 * @return
	 */
	public MailInfoVO getPersonMailTypeList(String pPk,int mail_type)
	{
		
		String sql = "select * from u_mail_info where receive_pk = "+pPk+" and mail_type="+mail_type;
		
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
	 * �����ʼ�������
	 * @param mailId
	 * @param string
	 */
	public void updateMail(int mailId, String content)
	{
		String sql = "update u_mail_info set content = '"+content+"' where mail_id = "+mailId+"";
		logger.debug("�����ʼ�������sql="+sql);
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
		String sql = "insert into u_mail_info values(null,"+receive_pk+","+send_pk+","+mail_type+",'"+StringUtil.gbToISO(title)+"','"+StringUtil.gbToISO(content)+"',1,"+improtant+",now())";
		logger.debug("����mail="+sql);
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
	//�����ʼ�����
	public int insertBonusMail(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		int mail_id = -1;
		String sql = "insert into u_mail_info values(null,"+receive_pk+","+send_pk+","+mail_type+",'"+StringUtil.gbToISO(title)+"','"+StringUtil.gbToISO(content)+"',1,"+improtant+",now())";
		logger.debug("����mail="+sql);
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
	 * �����ʼ���Ϣ
	 * @prarm receive_pk 	������id 
	 * @prarm send_pk		������id
	 * @param mail_type		�ʼ����ͣ�1Ϊ��ͨ�ʼ���2Ϊϵͳ��
	 * @prarm title 		����
	 * @param context 		����
	 * @param improtant  	��Ҫ�ԣ�Ĭ��Ϊ1������Խ��Խ��Ҫ
	 * @return if sussend return 1,else return -1;
	 *//*
	public int insertMail(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		int result = -1;
		String sql = "insert into u_mail_info values(null,"+receive_pk+","+send_pk+","+mail_type+",'"+StringUtil.gbToISO(title)+"','"+StringUtil.gbToISO(content)+"',1,"+improtant+",now())";
		logger.debug("����mail="+sql);
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
	 * �����ʼ���Ϣ
	 * @prarm receive_pk 	������id 
	 * @prarm send_pk		������id
	 * @param mail_type		�ʼ����ͣ�1Ϊ��ͨ�ʼ���2Ϊϵͳ��
	 * @prarm title 		����
	 * @param context 		����
	 * @param improtant  	��Ҫ�ԣ�Ĭ��Ϊ1������Խ��Խ��Ҫ
	 * @return if sussend return 1,else return -1;
	 */
	public int addAndReturnKey(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		int result = -1;
		String sql = "insert into u_mail_info values(null,"+receive_pk+","+send_pk+","+mail_type+",'"+StringUtil.gbToISO(title)+"','"+StringUtil.gbToISO(content)+"',1,"+improtant+",now(),'')";
		logger.debug("����mail="+sql);
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
	 * �����ʼ���Ϣ
	 * @prarm receive_pk 	������id 
	 * @prarm send_pk		������id
	 * @param mail_type		�ʼ����ͣ�1Ϊ��ͨ�ʼ���2Ϊϵͳ��
	 * @prarm title 		����
	 * @param context 		����
	 * @param improtant  	��Ҫ�ԣ�Ĭ��Ϊ1������Խ��Խ��Ҫ
	 * @return if sussend return 1,else return -1;
	 */
	public int addAndReturnKey(MailInfoVO mail){
		if( mail==null )
		{
			return -1;
		}
		int result = -1;
		StringBuffer sql = new StringBuffer();
			
		sql.append("insert into u_mail_info values(null");
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
		
		logger.debug("����mail="+sql.toString());
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
	
