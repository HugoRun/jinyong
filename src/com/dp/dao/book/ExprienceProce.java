package com.dp.dao.book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ls.pub.db.DBConnection;

public class ExprienceProce
{
	private DBConnection dbconn;
	/**
	 * �˳���Ǽ��㾭��ֵ
	 * */
	public void addPlayerExprience(Integer ppk,Integer allex){
		String sql="update u_part_info set p_benji_experience='"+allex.toString()+"' where p_pk="+ppk;
		try{
			dbconn=new DBConnection(DBConnection.GAME_USER_DB);
			Connection conn=dbconn.getConn();
			Statement stmt=conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbconn.closeConn();
		}
	}
	/**
	 * ���ݽ�ɫID��ȡ�ý�ɫ�ȼ�����ֵ
	 * */
	public Integer getPpkExprience(Integer ppk){
		String sql="select p_benji_experience from u_part_info where p_pk="+ppk;
		try{
			dbconn=new DBConnection(DBConnection.GAME_USER_DB);
			Connection conn=dbconn.getConn();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				Integer ss=Integer.parseInt(rs.getString(1).trim());
				rs.close();
				stmt.close();
				return ss;
			}else{
				rs.close();
				stmt.close();
			}
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
			dbconn.closeConn();
		}
		return null;
	}
	/**
	 * ��ȡ��ҵȼ�
	 * */
	public Integer getPpkDengJi(Integer ppk){
		String sql="select p_grade from u_part_info where p_pk="+ppk;
		try{
			dbconn=new DBConnection(DBConnection.GAME_USER_DB);
			Connection conn=dbconn.getConn();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
    			Integer ss=rs.getInt(1);
    			rs.close();
    			stmt.close();
    			return ss;
			}else{
				rs.close();
    			stmt.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbconn.closeConn();
		}
		return null;
	}
}













































