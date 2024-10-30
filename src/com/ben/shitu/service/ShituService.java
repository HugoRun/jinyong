package com.ben.shitu.service;

import java.util.ArrayList;
import java.util.List;

import com.ben.shitu.dao.ShituDao;
import com.ben.shitu.model.Shitu;
import com.ben.shitu.model.ShituConstant;
import com.web.jieyi.util.Constant;

public class ShituService
{
	private ShituDao shituDao;

	public ShituService()
	{
		shituDao = new ShituDao();
	}

	public void addShitu(Shitu shitu)
	{
		Shitu shi = shituDao.addShitu(shitu);
		if(shi!=null&&shi.getTe_id()!=0&&shi.getStu_id()==0){
			synchronized (ShituConstant.TEACHER)
			{
				ShituConstant.TEACHER.add(shi);
			}
			
		}
		if(shitu.getStu_id()!=0&&shitu.getTe_id()==0){
			synchronized (ShituConstant.STUDENT)
			{
				ShituConstant.STUDENT.add(shi);
			}
		}
	}
	
	public void delShitu(int stu_id){
		shituDao.delShitu(stu_id);
		List<Shitu> stulist = cloneList(ShituConstant.STUDENT);
		for(Shitu shitu : stulist){
			if(shitu.getStu_id()==stu_id&&shitu.getTe_id()==0){
				ShituConstant.STUDENT.remove(shitu);
				break;
			}
		}
		
	}

	// public List<Shitu> findByType(int type, int p_pk, int start, int count)
	// {
	// return shituDao.findByType(type, p_pk, start, count);
	// }

//	public int findByType(int type, int p_pk)
//	{
//		return shituDao.findByType(type, p_pk, 0, 0).size();
//	}
//
//	public int findHave(int type, int p_pk)
//	{
//		return shituDao.findHave(type, p_pk);
//	}

	public List<Shitu> find(String sql, Object[] args)
	{
		return shituDao.find(sql, args);
	}

	public int findCount(String sql, Object[] args)
	{
		return shituDao.findCount(sql, args);
	}

	public void loadAllTeacher()
	{
		String sql = "select * from shitu s where s.stu_id = 0 group by s.te_id order by s.tim ";
		ShituConstant.TEACHER = shituDao.find(sql, null);
	}

	public void loadAllStudent()
	{
		String sql = "select * from shitu s where s.te_id = 0 group by s.stu_id order by s.tim ";
		ShituConstant.STUDENT = shituDao.find(sql, null);
	}

	public List<Shitu> findStudent(int page)
	{
		List<Shitu> list = new ArrayList<Shitu>();
		if (ShituConstant.TEACHER != null)
		{
			synchronized (ShituConstant.STUDENT)
			{
				list = ShituConstant.STUDENT.subList((page - 1)
						* Constant.EVERY_PAGE_COUNT,
						page * Constant.EVERY_PAGE_COUNT > ShituConstant.STUDENT
								.size() ? ShituConstant.STUDENT.size() : page
								* Constant.EVERY_PAGE_COUNT);
			}
		}
		return list;
	}

	public List<Shitu> findTeacher(int page)
	{
		List<Shitu> list = new ArrayList<Shitu>();
		if (ShituConstant.TEACHER != null)
		{
			synchronized (ShituConstant.TEACHER)
			{
				list = ShituConstant.TEACHER.subList((page - 1)
						* Constant.EVERY_PAGE_COUNT,
						page * Constant.EVERY_PAGE_COUNT > ShituConstant.TEACHER
								.size() ? ShituConstant.TEACHER.size() : page
								* Constant.EVERY_PAGE_COUNT);
			}
		}
		return list;
	}

	public int studentCount()
	{
		int count = 0;
		if(ShituConstant.STUDENT!=null){
			synchronized (ShituConstant.STUDENT)
			{
				count = ShituConstant.STUDENT.size();
			}
		}
		return count;
	}
	
	public int teacherCount()
	{
		int count = 0;
		if(ShituConstant.TEACHER!=null){
			synchronized (ShituConstant.TEACHER)
			{
				count = ShituConstant.TEACHER.size();
			}
		}
		return count;
	}
	
	public Shitu findOne(String sql, Object[] args){
		return shituDao.findOne(sql, args);
	}
	
	public void doit(String sql,Object[] args){
		shituDao.doit(sql, args);
	}
	
	public List<Shitu> findByTeacher(Object te_id){
		return shituDao.findByTeacher(te_id);
	}
	
	public List<Shitu> findByStudent(Object te_id){
		return shituDao.findByStudent(te_id);
	}
	
	public void remove(String stu_name,String tea_name){
		List<Shitu> tealist = cloneList(ShituConstant.TEACHER);
		List<Shitu> stulist = cloneList(ShituConstant.STUDENT);
		for(Shitu shitu: tealist){
			if(shitu.getTe_name()!=null&&shitu.getTe_name().trim().equals(tea_name.trim())){
				ShituConstant.TEACHER.remove(shitu);
				break;
			}
		}
		for(Shitu shitu : stulist){
			if(shitu.getStu_name()!=null&&shitu.getStu_name().trim().equals(stu_name.trim())){
				ShituConstant.STUDENT.remove(shitu);
				break;
			}
		}
	}
	
	public void removeFromStudent(int id){
		List<Shitu> stulist = cloneList(ShituConstant.STUDENT);
		for(Shitu shitu : stulist){
			if(shitu.getStu_id()==id){
				ShituConstant.STUDENT.remove(shitu);
				break;
			}
		}
	}
	
	private List<Shitu> cloneList(List<Shitu> list){
		if(list==null){
			return new ArrayList<Shitu>();
		}else{
			return new ArrayList<Shitu>(list);
		}
	}
	
	public void delAsTeacher(Object id){
		if(id!=null){
		shituDao.delAsTeacher(id);
		}
	}
	
	public void delAsStudent(Object id){
		if(id!=null){
		shituDao.delAsStudent(id);
		}
	}
	
	public void delbyId(Object id){
		if(id!=null){
			shituDao.delbyId(id);
		}
	}
	
	public Shitu findById(Object id){
		if(id!=null){
			return shituDao.findById(id);
		}
		return null;
	}
	                                    
}
