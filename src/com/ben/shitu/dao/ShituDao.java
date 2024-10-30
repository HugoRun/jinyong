package com.ben.shitu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ben.shitu.model.Shitu;
import com.ben.shitu.model.ShituConstant;
import com.ben.vo.friend.FriendVO;
import com.pub.db.mysql.SqlData;
import com.web.jieyi.util.Constant;

public class ShituDao
{
	SqlData con;

	public Shitu addShitu(Shitu shitu)
	{
		if (shitu != null)
		{
			String sql = "insert into shitu(`te_id`,`stu_id`,`te_name`,`stu_name`,`te_level`,`stu_level`,`tim`) values ("+shitu.getTe_id()+","+shitu.getStu_id()
			+",'"+shitu.getTe_name()+"','"+shitu.getStu_name()+"',"+shitu.getTe_level()+","+shitu.getStu_level()+",now())";
			String sql1 = "SELECT LAST_INSERT_ID() ";
			try
			{
				con = new SqlData();
				con.update(sql);
				ResultSet rs = con.query(sql1);
				while(rs.next()){
					shitu.setId(rs.getLong(1));
				}
				
			}
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (SQLException e)
			{
				con.close();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return shitu;
	}

	 public List<Shitu> findByTeacher(Object te_id){
	 String sql = "select * from shitu s where s.te_id = "+te_id+" and s.stu_id !=0 ";
	 List<Shitu> list = new ArrayList<Shitu>();
	 try
	 {
	 con = new SqlData();
	 ResultSet rs = con.query(sql);
	 list = get(rs);
	 }
	 catch (ClassNotFoundException e)
	 {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 catch (SQLException e)
	 {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }finally{
	 con.close();
	 return list;
	 }
	 }
	 
	 
	 public List<Shitu> findByStudent(Object stu_id){
		 String sql = "select * from shitu s where s.stu_id = "+stu_id+" and s.te_id !=0 ";
		 List<Shitu> list = new ArrayList<Shitu>();
		 try
		 {
		 con = new SqlData();
		 ResultSet rs = con.query(sql);
		 list = get(rs);
		 }
		 catch (ClassNotFoundException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 catch (SQLException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }finally{
		 con.close();
		 return list;
		 }
		 }
	 
	 public Shitu findById(Object id){
		 String sql = "select * from shitu s where s.id = "+id;
		 List<Shitu> list = new ArrayList<Shitu>();
		 try
		 {
		 con = new SqlData();
		 ResultSet rs = con.query(sql);
		 list = get(rs);
		 }
		 catch (ClassNotFoundException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 catch (SQLException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }finally{
		 con.close();
		 return list==null?null:list.size()>0?list.get(0):null;
		 }
	 }
	 
	 public void delAsTeacher(Object id){
		 String sql = "delete from shitu where te_id = "+id;
		 try
		 {
		 con = new SqlData();
		 con.update(sql);
		 }
		 catch (ClassNotFoundException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 catch (SQLException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }finally{
		 con.close();
		 }
	 }
	 
	 public void delAll(Object id){
		 String sql = "delete from shitu where te_id = "+id+" or stu_id = "+id;
		 try
		 {
		 con = new SqlData();
		 con.update(sql);
		 }
		 catch (ClassNotFoundException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 catch (SQLException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }finally{
		 con.close();
		 }
	 }
	 
	 public void delAsStudent(Object id){
		 String sql = "delete from shitu where stu_id = "+id;
		 try
		 {
		 con = new SqlData();
		 con.update(sql);
		 }
		 catch (ClassNotFoundException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 catch (SQLException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }finally{
		 con.close();
		 }
	 }
	 
	 public void delbyId(Object id){
		 String sql = "delete from shitu where id = "+id;
		 try
		 {
		 con = new SqlData();
		 con.update(sql);
		 }
		 catch (ClassNotFoundException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 catch (SQLException e)
		 {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }finally{
		 con.close();
		 }
	 }

	private List<Shitu> get(ResultSet rs) throws java.sql.SQLException
	{
		List<Shitu> list = new ArrayList<Shitu>();
		if (rs != null)
		{
			while (rs.next())
			{
				Shitu vo = new Shitu(rs.getLong("id"), rs.getInt("te_id"), rs
						.getInt("stu_id"), rs.getString("te_name"), rs
						.getString("stu_name"), rs.getInt("te_level"), rs
						.getInt("stu_level"), rs.getString("tim"),rs.getString("chuangong"));
				list.add(vo);
			}
		}
		return list;
	}

	public Shitu findOne(String sql, Object[] args)
	{
		List<Shitu> list = new ArrayList<Shitu>();
		try
		{
			con = new SqlData();
			ResultSet rs = con.query(sql, args);
			list = get(rs);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			con.close();
			return list == null ? null : list.size() > 0 ? list.get(0) : null;
		}
	}

	public List<Shitu> find(String sql, Object[] args)
	{
		List<Shitu> list = new ArrayList<Shitu>();
		try
		{
			con = new SqlData();
			ResultSet rs = con.query(sql, args);
			list = get(rs);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			con.close();
			return list;
		}
	}
	
	public void doit(String sql,Object[] args){
		try
		{
			con = new SqlData();
			con.update(sql, args);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}
	
	public void delShitu(Object stu_id){
		String sql = "delete from shitu where stu_id = "+stu_id+" and te_id = 0";
		try
		{
			con = new SqlData();
			con.update(sql);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
	}

	public int findCount(String sql, Object[] args)
	{
		int count = 0;
		try
		{
			con = new SqlData();
			ResultSet rs = con.query(sql, args);
			if (rs.next())
			{
				count = rs.getInt("cou");
			}
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			con.close();
			return count;
		}
	}
	
}
