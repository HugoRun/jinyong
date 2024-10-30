package com.dp.dao.book;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dp.vo.bzjvo.BookZJVO;
import com.dp.vo.infovo.BookInfoVO;
import com.dp.vo.mybookvo.BookMeVO;
import com.dp.vo.newbook.NewInfo;
import com.dp.vo.phb.PhVO;
import com.dp.vo.typevo.BookTypeVO;
import com.ls.pub.db.DBConnection;

public class BookProce
{
	 DBConnection dbcon;
	/**
	 * ��ȡ�鼮�����Ϣ
	 * */
	public List<BookTypeVO> showTypes(){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
	    	Statement stmt= dbcon.getConn().createStatement();
			String sql = "select * from book_type";
			ResultSet rs = stmt.executeQuery(sql);
			List<BookTypeVO> typelist=new ArrayList<BookTypeVO>();
			while(rs.next()){
				BookTypeVO type=new BookTypeVO();
				type.setTypeid(rs.getInt(1));
				type.setTypename(rs.getString(2));
				typelist.add(type);
			}
			rs.close();
			stmt.close();
			return typelist;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	
	/**
	 * ��ȡ��������鼮��Ϣ
	 * */
	public List<NewInfo> showNewInfo(){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
	    	Statement stmt= dbcon.getConn().createStatement();
			String sql = "select * from (select bi.book_id,bt.type_name,bi.book_name," +
					"bi.author,nr.zj_count,nr.zj_name,nr.gx_date,bt.type_id " +
					"from book_neirong nr,book_info bi,book_type bt  " +
					"where bi.type_id=bt.type_id and bi.book_id=nr.book_id order by nr.gx_date desc)  " +
					"tab group by book_id limit 10";
			ResultSet rs = stmt.executeQuery(sql);
			List<NewInfo> infolist=new ArrayList<NewInfo>();
			while(rs.next()){
				NewInfo info=new NewInfo();
				info.setBookid(rs.getInt(1));
				info.setTypename(rs.getString(2));
				info.setBookname(rs.getString(3));
				info.setAuthor(rs.getString(4));
				info.setZjcount(rs.getInt(5));
				info.setZjname(rs.getString(6));
				info.setGxdate(rs.getDate(7));
				info.setTypeid(rs.getInt(8));
				infolist.add(info);
			}
			rs.close();
			stmt.close();
			return infolist;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ��ѯĳС˵����������½���Ϣ
	 * */
	public NewInfo getNewZjInfoByBookId(Integer bookid){
		String sql="select * from  (select bi.book_id,nr.zj_count,nr.zj_name,nr.gx_date "+
                    "from book_neirong nr,book_info bi,book_type bt "+
                    "where bi.type_id=bt.type_id and bi.book_id=nr.book_id order by nr.gx_date desc) tab "+
                    "where book_id="+bookid+" group by book_id ";
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			NewInfo info=new NewInfo();
			if(rs.next()){
				info.setBookid(rs.getInt(1));
				info.setZjcount(rs.getInt(2));
				info.setZjname(rs.getString(3));
				info.setGxdate(rs.getDate(4));
			}
			rs.close();
			stmt.close();
			return info;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	
	/**
	 * ģ����ѯ�鼮����С˵����
	 * */
	public List<NewInfo> showSearchInfo(String novename){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			String sql ="select bi.book_id,bi.author,bt.type_name,bi.book_name,bt.type_id "+
			"from book_info bi,book_type bt where bi.type_id=bt.type_id and bi.book_name like '%"+novename.trim()+"%'";
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			List<NewInfo> infolist=new ArrayList<NewInfo>();
			while(rs.next()){
				NewInfo info=new NewInfo();
				info.setBookid(rs.getInt(1));
				info.setAuthor(rs.getString(2));
				info.setTypename(rs.getString(3));
				info.setBookname(rs.getString(4));
				info.setTypeid(rs.getInt(5));
				infolist.add(info);
			}
			rs.close();
			stmt.close();
			return infolist;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ����С˵����ѯС˵
	 * */
	public List<NewInfo> getBookByType(Integer typeid,Integer typepage,Integer tpagesize){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			String sql ="select * from (select bi.book_id,bi.book_name,nr.zj_count,nr.zj_name,nr.gx_date,bt.type_id "+
            "from book_neirong nr,book_info bi,book_type bt where bi.type_id=bt.type_id and bi.book_id=nr.book_id "+
            "order by nr.gx_date desc) tab where tab.type_id="+typeid+" group by book_id limit "+tpagesize+" offset "+(typepage-1)*tpagesize;
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			List<NewInfo> infolist=new ArrayList<NewInfo>();
			while(rs.next()){
				NewInfo info=new NewInfo();
				info.setBookid(rs.getInt(1));
				info.setBookname(rs.getString(2));
				info.setZjcount(rs.getInt(3));
				info.setZjname(rs.getString(4));
				info.setGxdate(rs.getDate(5));
				info.setTypeid(rs.getInt(6));
				infolist.add(info);
			}
			rs.close();
			stmt.close();
			return infolist;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ����С˵����ѯ��ҳ��С˵����ҳ��
	 * */
	public Integer getTypePageCount(Integer typeid,Integer tpsize){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			String sql="select count(*) from (select * from (select bi.book_id,bi.book_name,nr.zj_count,nr.zj_name,"+
                "nr.gx_date,bt.type_id from book_neirong nr,book_info bi,book_type bt where bi.type_id=bt.type_id "+
                "and bi.book_id=nr.book_id order by nr.gx_date desc) tab where tab.type_id="+typeid+" group by book_id) tab1";
			Statement stmt= dbcon.getConn().createStatement();ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				Integer rowcount=rs.getInt(1);
				Integer tpc=(rowcount/tpsize+(rowcount%tpsize==0?0:1));
				rs.close();
				stmt.close();
				return  tpc;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ����TYPEID��ȡTYPENAME
	 * */
	public String getTypeById(Integer typeid){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			String sql ="select type_name from book_type where type_id="+typeid;
			Statement stmt= dbcon.getConn().createStatement();
    	    ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
    			String typename=rs.getString("type_name");
    			rs.close();
    			stmt.close();
    			return typename;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ��ѯĳ��С˵�����а�
	 * */
	public List<PhVO> getNoveSqu(Integer typeid,Integer squpage,Integer squpagesize){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
    		String sql="select bi.book_id,bt.type_id,bt.type_name,bi.book_name,bi.book_stow from book_info bi,book_type bt "+
            "where bi.type_id="+typeid+" and bt.type_id=bi.type_id "+
            "order by bi.book_stow desc limit "+squpagesize+" offset "+(squpage-1)*squpagesize;
    	    Statement stmt= dbcon.getConn().createStatement();
    	    ResultSet rs=stmt.executeQuery(sql);
    	    List<PhVO> phlist=new ArrayList<PhVO>();
    	    while(rs.next()){
    	    	PhVO ph=new PhVO();
    	    	ph.setBookid(rs.getInt(1));
				ph.setTypeid(rs.getInt(2));
				ph.setTypename(rs.getString(3));
				ph.setBookname(rs.getString(4));
				ph.setScount(rs.getInt(5));
				phlist.add(ph);
    	    }
    	    rs.close();
			stmt.close();
    	    return phlist;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ��ѯĳ��С˵���а��ҳ�����ҳ��
	 * */
	public Integer getNoveSquCount(Integer typeid,Integer squpagesize){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			String sql="select count(*) from book_info bi where bi.type_id="+typeid;
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				Integer rowcount=rs.getInt(1);
				Integer squpagecount=(rowcount/squpagesize+(rowcount%squpagesize==0?0:1));
				rs.close();
    			stmt.close();
				return squpagecount;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ��С˵������
	 * */
	public List<NewInfo> getBookByTypeAndName(Integer typeid,String name){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
    		String sql ="select bi.book_id,bi.author,bt.type_name,bi.book_name,nr.zj_count,nr.zj_name,nr.gx_date,bt.type_id "+
    		"from book_neirong nr,book_info bi,book_type bt "+
    		"where bi.type_id=bt.type_id and bi.book_id=nr.book_id and bi.book_name like '%"+name.trim()+"%' " +
    	    "and bt.type_id="+typeid+" order by bi.book_id desc limit 10";
    		Statement stmt= dbcon.getConn().createStatement();
    		ResultSet rs=stmt.executeQuery(sql);
     	    List<NewInfo> phlist=new ArrayList<NewInfo>();
     	    while(rs.next()){
     	    	NewInfo info=new NewInfo();
				info.setBookid(rs.getInt(1));
				info.setAuthor(rs.getString(2));
				info.setTypename(rs.getString(3));
				info.setBookname(rs.getString(4));
				info.setZjcount(rs.getInt(5));
				info.setZjname(rs.getString(6));
				info.setGxdate(rs.getDate(7));
				info.setTypeid(rs.getInt(8));
				phlist.add(info);
     	    }
     	    rs.close();
			stmt.close();
     	    return phlist;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ���ĳ��С˵�鿴�½��б�
	 * */
	public List<BookZJVO> getZJbyBookId(Integer bookid,Integer page,Integer pagesize){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
    		String sql ="select nr.nr_id,nr.zj_count,substring(nr.zj_neirong,1,8),zj_name from book_neirong nr where nr.book_id="+bookid+" limit "+pagesize+" offset "+(page-1)*pagesize;
    		Statement stmt= dbcon.getConn().createStatement();
    		ResultSet rs=stmt.executeQuery(sql);
     	    List<BookZJVO> phlist=new ArrayList<BookZJVO>();
     	    while(rs.next()){
     	    	BookZJVO info=new BookZJVO();
     	    	info.setNrid(rs.getInt(1));
     	    	info.setBookzj(rs.getInt(2));
     	    	info.setBookline(rs.getString(3));
     	    	info.setZjname(rs.getString(4));
     	    	phlist.add(info);
     	    }
     	    rs.close();
			stmt.close();
     	    return phlist;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * �½��б���ҳ�����ҳ��
	 * **/
	public Integer getZjListPageCount(Integer bookid,Integer pagesize){
		try{
			String sql="select count(*) from book_neirong where book_id="+bookid;
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				Integer rowcount=rs.getInt(1);
				Integer pagecount=(rowcount/pagesize+(rowcount%pagesize==0?0:1));
				rs.close();
				stmt.close();
				return  pagecount;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	
	/**
	 * ����С˵ID��ѯС˵����
	 * */
	public BookInfoVO getBookInfoById(Integer bookid){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			String sql="select bi.book_id,bi.book_name,bi.type_id,bi.author,bi.book_line,bt.type_name from book_info bi,book_type bt where bi.type_id=bt.type_id and book_id="+bookid;
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			BookInfoVO info=new BookInfoVO();
			info.setBookid(rs.getInt(1));
			info.setBookname(rs.getString(2));
			info.setTypeid(rs.getInt(3));
			info.setAuthor(rs.getString(4));
			info.setBookline(rs.getString(5));
			info.setTypename(rs.getString(6));
			rs.close();
			stmt.close();
			return info;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * �Ķ��鼮ID���½����Ķ��½�����
	 * */
	public String getNeiRongByBidAndZid(Integer ppk,Integer bookid,Integer zjid,Integer pagesize){
		try{
			this.addBookReadCount(bookid);
			dbcon=new DBConnection(DBConnection.GAME_DB);
			String sql="select substr(zj_neirong,1,"+pagesize+") from book_neirong where book_id="+bookid+" and zj_count="+zjid;
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			String neirong=rs.getString(1);
			rs.close();
			stmt.close();
			return neirong;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * �����ҵ��ղؼ�
	 * */
	public void addToMyBox(BookMeVO booktome){
		String sql="insert into book_mine values(null,"+booktome.getRoleid()+","+booktome.getBookid()+","+booktome.getTypeid()+",'"+booktome.getBookmark()+"')";
		try{
			this.addBookStow(booktome.getBookid());
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt=dbcon.getConn().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
	}
	/**
	 * �鿴�ҵ����
	 * */
    public List<BookMeVO> findMyBookBox(Integer roleid){
    	String sql="select * from (select bm.my_id,bm.book_id,bt.type_name,bi.book_name,max(nr.zj_count) mcount,nr.zj_name,max(nr.gx_date),bm.book_mark,bm.role_id,bm.type_id "+
            "from book_info bi,book_type bt,book_neirong nr,book_mine bm "+
            "where bi.type_id=bt.type_id and bm.book_id=bi.book_id and nr.book_id=bi.book_id and bm.role_id="+roleid+
            " group by bm.book_id,nr.zj_name  order by nr.gx_date desc) tab group by tab.book_id order by tab.mcount desc";
    	try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			List<BookMeVO> mybox=new ArrayList<BookMeVO>();
			while(rs.next()){
				BookMeVO bm=new BookMeVO();
				bm.setMyid(rs.getInt(1));
				bm.setBookid(rs.getInt(2));
				bm.setTypename(rs.getString(3));
				bm.setBookname(rs.getString(4));
				bm.setNewzjcount(rs.getInt(5));
				bm.setZjname(rs.getString(6));
				bm.setGxdate(rs.getDate(7));
				bm.setBookmark(rs.getString(8));
				bm.setRoleid(rs.getInt(9));
				bm.setTypeid(rs.getInt(10));
				mybox.add(bm);
			}
			rs.close();
			stmt.close();
			return mybox;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
    	return null;
    }
    /**
     * ��ȡ��ǰ�ҵĴ˽�ɫ�ﹲ���˶��ٱ���
     * */
    public Integer getMyBookCount(Integer roleid){
    	String sql="select count(*) from book_mine where role_id="+roleid;
    	try{
    		dbcon=new DBConnection(DBConnection.GAME_DB);
    		Statement stmt= dbcon.getConn().createStatement();
    		ResultSet rs=stmt.executeQuery(sql);
    		rs.next();
    		Integer sc=rs.getInt(1);
    		rs.close();
			stmt.close();
    		return sc;
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		dbcon.closeConn();
    	}
    	return null;
    }
    /**
     * �����ҵ������Ĵ洢�鼮������ɾ���鼮
     * */
    public void deleteMyBook(Integer myid){
    	String sql="delete from book_mine where my_id="+myid;
    	try{
    		dbcon=new DBConnection(DBConnection.GAME_DB);
    		Statement stmt=dbcon.getConn().createStatement();
    		stmt.executeUpdate(sql);
    		stmt.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		dbcon.closeConn();
    	}
    }
    /**
     * �ж����ղص��鼮�Ƿ����ظ�
     * */
    public boolean checkReplace(Integer roleid,Integer bookid){
    	String sql="select * from book_mine where role_id="+roleid+" and book_id="+bookid;
    	try{
    		dbcon=new DBConnection(DBConnection.GAME_DB);
    		Statement stmt= dbcon.getConn().createStatement();
    		ResultSet rs=stmt.executeQuery(sql);
    		boolean bool=rs.next();
    		rs.close();
			stmt.close();
    		return bool;
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		dbcon.closeConn();
    	}
    	return false;
    }
    /**
     * ��С˵���ͽ�ɫid����С˵
     * */
	public List<NewInfo> getBookByRidAndName(Integer roleid,String name){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			String sql ="select bi.author,bi.book_id,bt.type_name,bi.book_name,bt.type_id "+
			"from book_info bi,book_type bt,book_mine bm "+
			"where bi.type_id=bt.type_id and bm.book_id=bi.book_id and bi.book_name like '%"+name.trim()+"%' and bm.role_id="+roleid;
			Statement stmt= dbcon.getConn().createStatement();
    		ResultSet rs=stmt.executeQuery(sql);
			List<NewInfo> infolist=new ArrayList<NewInfo>();
			while(rs.next()){
				NewInfo info=new NewInfo();
				info.setAuthor(rs.getString(1));
				info.setBookid(rs.getInt(2));
				info.setTypename(rs.getString(3));
				info.setBookname(rs.getString(4));
				info.setTypeid(rs.getInt(5));
				infolist.add(info);
			}
			rs.close();
			stmt.close();
			return infolist;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * �����߲�ѯС˵
	 * */
	public List<NewInfo> showSearchResult(String novename){
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			String sql ="select bi.author,bi.book_id,bt.type_name,bi.book_name,bt.type_id "+
			"from book_info bi,book_type bt "+
			"where bi.type_id=bt.type_id and bi.author like '%"+novename.trim()+"%'";
			Statement stmt= dbcon.getConn().createStatement();
    		ResultSet rs=stmt.executeQuery(sql);
			List<NewInfo> infolist=new ArrayList<NewInfo>();
			while(rs.next()){
				NewInfo info=new NewInfo();
				info.setAuthor(rs.getString(1));
				info.setBookid(rs.getInt(2));
				info.setTypename(rs.getString(3));
				info.setBookname(rs.getString(4));
				info.setTypeid(rs.getInt(5));
				infolist.add(info);
			}
			rs.close();
			stmt.close();
			return infolist;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * �����鼮������ȡ�鼮����
	 * */
	public String getBookNameById(Integer bookid){
		String sql="select book_name from book_info where book_id="+bookid;
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			String bookname=rs.getString(1);
			rs.close();
			stmt.close();
			return bookname;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * �����½��������鼮������ȡ�½�����
	 * */
	public String getZjNameById(Integer zjcount,Integer bookid){
		String sql="select zj_name from book_neirong where zj_count="+zjcount+" and book_id="+bookid;
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			String zjname=rs.getString(1);
			rs.close();
			stmt.close();
			return zjname;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * �����鼮��������ѯ���鼮���ж��ٸ��½�
	 * */
	public Integer getZjCountByBookId(Integer bookid){
		String sql="select count(*) from book_neirong where book_id="+bookid;
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			Integer zjcount=rs.getInt(1);
			rs.close();
			stmt.close();
			return zjcount;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ����С˵��������ȡС˵�����id
	 * */
	public Integer getTypeIdByBookId(Integer bookid){
		String sql="select type_id from book_info where book_id="+bookid;
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			Integer typeid=rs.getInt(1);
			rs.close();
			stmt.close();
			return typeid;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ��ѯǰʮ���鼮�����а��ղ��˴ν�������
	 * */
	public List<PhVO> getPhList(Integer phpage,Integer phpagesize){
		String sql="select bi.book_id,bt.type_id,bt.type_name,bi.book_name,bi.book_stow from book_info bi,book_type bt "+
				"where bt.type_id=bi.type_id and bi.book_stow!=0 order by bi.book_stow desc  limit "+phpagesize+" offset "+(phpage-1)*phpagesize;
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			List<PhVO> phlist=new ArrayList<PhVO>();
			while(rs.next()){
				PhVO ph=new PhVO();
				ph.setBookid(rs.getInt(1));
				ph.setTypeid(rs.getInt(2));
				ph.setTypename(rs.getString(3));
				ph.setBookname(rs.getString(4));
				ph.setScount(rs.getInt(5));
				phlist.add(ph);
			}
			rs.close();
			stmt.close();
			return phlist;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ��ȡ���а��ҳ�����ҳ��
	 * */
	public Integer getPhPageCount(Integer phpagesize){
		String sql="select count(*) from book_info bi,book_type bt where bt.type_id=bi.type_id";
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			Integer rowcount=rs.getInt(1);
			Integer pagecount=(rowcount/phpagesize+(rowcount%phpagesize==0?0:1));
			rs.close();
			stmt.close();
			return pagecount;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
    /**
     * �жϴ˽�ɫ��ͬһ�����д治����
     * */
	public Integer isExistInAmark(Integer bookid,Integer roleid){
		String sql="select * from book_mine where book_id="+bookid+" and role_id="+roleid;
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				Integer ss=rs.getInt(1);
				rs.close();
				stmt.close();
				return ss;
			}else{
				rs.close();
				stmt.close();
				return 0;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ���ݵ�ǰ��ҵĽ�ɫID���鼮��ID����Ӹý�ɫ����ǩ���޸ĸý�ɫ����ǩ
	 * */
	public void addOrupdateMyBookMark(Integer bookid,Integer roleid,String mark,Integer typeid){
		try{
			Integer markid=this.isExistInAmark(bookid, roleid);
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
    		if(markid.equals(0)||markid==0){
    			String sql="update book_info set book_stow=book_stow+1 where book_id="+bookid;
    			String sql1="insert into book_mine values(null,"+roleid+","+bookid+","+typeid+",'"+mark+"')";
    			stmt.executeUpdate(sql1);
    			stmt.executeUpdate(sql);
    			stmt.close();
    		}else{
    			String sql2="update book_mine set book_mark='"+mark+"' where my_id="+markid;
    			stmt.executeUpdate(sql2);
    			stmt.close();
    		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
	}
	/**
	 * �����½ڱ�Ų�ѯ���½ڵ�ǰ�½���
	 * */
	public Integer getZjCountByZjId(Integer zjid){
		try{
			String sql="select zj_count from book_neirong where nr_id="+zjid;
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			Integer zjc=rs.getInt(1);
			rs.close();
			stmt.close();
			return zjc;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ��ѯĳ���½ڹ�����ҳ
	 * */
	public Integer getZjPageCount(Integer bookid,Integer zjcount,Integer pagesize){
		try{
			String sql="select char_length(zj_neirong) from book_neirong where book_id="+bookid+" and zj_count="+zjcount;
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				Integer allzs=rs.getInt(1);
				Integer allcount=(allzs/pagesize+(allzs%pagesize==0?0:1));
				rs.close();
				stmt.close();
				return allcount;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ��ѯÿһҳ��������ʾ�����
	 * **/
	public String getZjPageLine(Integer bookid,Integer zjcount,Integer page,Integer pagesize){
		try{
			String sql="select substr(zj_neirong,"+((page-1)*pagesize+1)+","+pagesize+") from book_neirong where book_id="+bookid+" and zj_count="+zjcount;
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				String pageline=rs.getString(1);
				rs.close();
				stmt.close();
				return pageline;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
	/**
	 * ���С˵���ղش���
	 * **/
	public void addBookStow(Integer bookid){
		try{
			String sql="update book_info set book_stow=book_stow+1 where book_id="+bookid;
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
	}
	/**
	 *���С˵���Ķ����� 
	 * **/
	public void addBookReadCount(Integer bookid){
		String sql="update book_info set read_count=read_count+1 where book_id="+bookid;
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
	}
	/**
	 * ����Ķ���¼
	 * **/
	public void addReadRecord(Integer ppk,Integer bookid,Integer zjcount){
		String sql="insert into book_read_record values(null,"+ppk+","+bookid+","+zjcount+",sysdate())";
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
	}
	/**
	 * ��ѯ����Ƿ�����˱���Ĵ��½�
	 * **/
	public boolean checkIsReadTheBookZj(Integer ppk,Integer bookid,Integer zjcount){
		String sql="select * from book_read_record where read_ppk="+ppk+" and read_bid="+bookid+" and read_zj="+zjcount;
		try{
			dbcon=new DBConnection(DBConnection.GAME_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			boolean bool=rs.next();
			rs.close();
			stmt.close();
			return bool;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return false;
	}
	/**
	 * ���ü�:����
	 * ÿ�����½�ʱ�۷�
	 * ��������=2*��ҵȼ�
	 * **/
	public void substrutTheRate(Integer ppk){
		String sql="select p_grade from u_part_info where p_pk="+ppk;
		try{
			dbcon=new DBConnection(DBConnection.GAME_USER_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				Integer grade=rs.getInt(1);
				String sql1="update u_part_info set p_copper=p_copper-"+grade*2;
				stmt.executeUpdate(sql1);
			}
			rs.close();
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
	}
	/**
	 * �ж�����Ƿ����㹻��������С˵
	 * */
	public boolean checkHaveEnoughCopper(Integer ppk){
		String sql="select p_grade,p_copper from u_part_info where p_pk="+ppk;
		try{
			dbcon=new DBConnection(DBConnection.GAME_USER_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next()){
				Integer rate=rs.getInt(1)*2;
				Integer copper=rs.getInt(2);
				if(copper>=rate){
					rs.close();
					stmt.close();
					return true;
				}else{
					rs.close();
					stmt.close();
					return false;
				}
			}else{
				rs.close();
				stmt.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return false;
	}
	/**
	 * ��ѯ��ұ���ɫ�ȼ�
	 * **/
	public String getThePgradeByPpk(Integer ppk){
		String sql="select p_grade,p_copper from u_part_info where p_pk="+ppk;
		try{
			dbcon=new DBConnection(DBConnection.GAME_USER_DB);
			Statement stmt= dbcon.getConn().createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			StringBuffer gcstr=new StringBuffer();
			if(rs.next()){
				Integer grade=rs.getInt(1);
				Integer copper=rs.getInt(2);
				if(grade==null){
					gcstr.append(0);
				}else{
					gcstr.append(grade+",");
				}
				if(copper==null){
					gcstr.append(0);
				}else{
					gcstr.append(copper);
				}
				rs.close();
				stmt.close();
				return gcstr.toString();
			}else{
				rs.close();
				stmt.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbcon.closeConn();
		}
		return null;
	}
}




































