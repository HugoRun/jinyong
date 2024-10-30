package com.dp.dao.book;

import com.dp.vo.bzjvo.BookZJVO;
import com.dp.vo.infovo.BookInfoVO;
import com.dp.vo.mybookvo.BookMeVO;
import com.dp.vo.newbook.NewInfo;
import com.dp.vo.phb.PhVO;
import com.dp.vo.typevo.BookTypeVO;
import com.ls.pub.db.DBConnection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookProce {
    DBConnection dbcon;

    /**
     * 获取书籍类别信息
     */
    public List<BookTypeVO> showTypes() {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            String sql = "SELECT * FROM `book_type`";
            ResultSet rs = stmt.executeQuery(sql);
            List<BookTypeVO> typelist = new ArrayList<BookTypeVO>();
            while (rs.next()) {
                BookTypeVO type = new BookTypeVO();
                type.setTypeid(rs.getInt(1));
                type.setTypename(rs.getString(2));
                typelist.add(type);
            }
            rs.close();
            stmt.close();
            return typelist;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 获取最近更新书籍信息
     */
    public List<NewInfo> showNewInfo() {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            String sql = "SELECT * FROM (SELECT bi.book_id, bt.type_name, bi.book_name, bi.author, nr.zj_count, nr.zj_name, nr.gx_date, bt.type_id FROM `book_neirong` nr, book_info bi, book_type bt WHERE bi.type_id = bt.type_id AND bi.book_id = nr.book_id ORDER BY nr.gx_date DESC) tab GROUP BY book_id LIMIT 10";
            ResultSet rs = stmt.executeQuery(sql);
            List<NewInfo> infolist = new ArrayList<NewInfo>();
            while (rs.next()) {
                NewInfo info = new NewInfo();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 查询某小说的最近更新章节信息
     */
    public NewInfo getNewZjInfoByBookId(Integer bookid) {
        String sql = "SELECT * FROM  (SELECT bi.book_id,nr.zj_count,nr.zj_name,nr.gx_date " + "FROM book_neirong nr,book_info bi,book_type bt " + "WHERE bi.type_id=bt.type_id AND bi.book_id=nr.book_id ORDER BY nr.gx_date desc) tab " + "WHERE book_id=" + bookid + " GROUP BY book_id ";
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            NewInfo info = new NewInfo();
            if (rs.next()) {
                info.setBookid(rs.getInt(1));
                info.setZjcount(rs.getInt(2));
                info.setZjname(rs.getString(3));
                info.setGxdate(rs.getDate(4));
            }
            rs.close();
            stmt.close();
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 模糊查询书籍根据小说名称
     */
    public List<NewInfo> showSearchInfo(String novename) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT bi.book_id,bi.author,bt.type_name,bi.book_name,bt.type_id " + "FROM book_info bi,book_type bt WHERE bi.type_id=bt.type_id AND bi.book_name LIKE '%" + novename.trim() + "%'";
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<NewInfo> infolist = new ArrayList<NewInfo>();
            while (rs.next()) {
                NewInfo info = new NewInfo();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 根据小说类别查询小说
     */
    public List<NewInfo> getBookByType(Integer typeid, Integer typepage, Integer tpagesize) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT * FROM (SELECT bi.book_id,bi.book_name,nr.zj_count,nr.zj_name,nr.gx_date,bt.type_id " + "FROM book_neirong nr,book_info bi,book_type bt WHERE bi.type_id=bt.type_id AND bi.book_id=nr.book_id " + "ORDER BY nr.gx_date desc) tab WHERE tab.type_id=" + typeid + " GROUP BY book_id limit " + tpagesize + " offset " + (typepage - 1) * tpagesize;
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<NewInfo> infolist = new ArrayList<NewInfo>();
            while (rs.next()) {
                NewInfo info = new NewInfo();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 根据小说类别查询分页有小说的总页数
     */
    public Integer getTypePageCount(Integer typeid, Integer tpsize) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT count(*) FROM (SELECT * FROM (SELECT bi.book_id,bi.book_name,nr.zj_count,nr.zj_name," + "nr.gx_date,bt.type_id FROM book_neirong nr,book_info bi,book_type bt WHERE bi.type_id=bt.type_id " + "and bi.book_id=nr.book_id ORDER BY nr.gx_date desc) tab WHERE tab.type_id=" + typeid + " GROUP BY book_id) tab1";
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Integer rowcount = rs.getInt(1);
                Integer tpc = (rowcount / tpsize + (rowcount % tpsize == 0 ? 0 : 1));
                rs.close();
                stmt.close();
                return tpc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 根据TYPEID获取TYPENAME
     */
    public String getTypeById(Integer typeid) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT type_name FROM book_type WHERE type_id=" + typeid;
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String typename = rs.getString("type_name");
                rs.close();
                stmt.close();
                return typename;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 查询某类小说的排行榜
     */
    public List<PhVO> getNoveSqu(Integer typeid, Integer squpage, Integer squpagesize) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT bi.book_id,bt.type_id,bt.type_name,bi.book_name,bi.book_stow FROM book_info bi,book_type bt " + "WHERE bi.type_id=" + typeid + " AND bt.type_id=bi.type_id " + "ORDER BY bi.book_stow DESC LIMIT " + squpagesize + " offset " + (squpage - 1) * squpagesize;
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<PhVO> phlist = new ArrayList<PhVO>();
            while (rs.next()) {
                PhVO ph = new PhVO();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 查询某类小说排行榜分页后的总页数
     */
    public Integer getNoveSquCount(Integer typeid, Integer squpagesize) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT count(*) FROM book_info bi WHERE bi.type_id=" + typeid;
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Integer rowcount = rs.getInt(1);
                Integer squpagecount = (rowcount / squpagesize + (rowcount % squpagesize == 0 ? 0 : 1));
                rs.close();
                stmt.close();
                return squpagecount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 按小说名搜索
     */
    public List<NewInfo> getBookByTypeAndName(Integer typeid, String name) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT bi.book_id,bi.author,bt.type_name,bi.book_name,nr.zj_count,nr.zj_name,nr.gx_date,bt.type_id " + "FROM book_neirong nr,book_info bi,book_type bt " + "WHERE bi.type_id=bt.type_id AND bi.book_id=nr.book_id AND bi.book_name LIKE '%" + name.trim() + "%' " + "and bt.type_id=" + typeid + " ORDER BY bi.book_id DESC LIMIT 10";
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<NewInfo> phlist = new ArrayList<NewInfo>();
            while (rs.next()) {
                NewInfo info = new NewInfo();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 点击某部小说查看章节列表
     */
    public List<BookZJVO> getZJbyBookId(Integer bookid, Integer page, Integer pagesize) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT nr.nr_id,nr.zj_count,substring(nr.zj_neirong,1,8),zj_name FROM book_neirong nr WHERE nr.book_id=" + bookid + " limit " + pagesize + " offset " + (page - 1) * pagesize;
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<BookZJVO> phlist = new ArrayList<BookZJVO>();
            while (rs.next()) {
                BookZJVO info = new BookZJVO();
                info.setNrid(rs.getInt(1));
                info.setBookzj(rs.getInt(2));
                info.setBookline(rs.getString(3));
                info.setZjname(rs.getString(4));
                phlist.add(info);
            }
            rs.close();
            stmt.close();
            return phlist;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 章节列表经分页后的总页数
     **/
    public Integer getZjListPageCount(Integer bookid, Integer pagesize) {
        try {
            String sql = "SELECT count(*) FROM book_neirong WHERE book_id=" + bookid;
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Integer rowcount = rs.getInt(1);
                Integer pagecount = (rowcount / pagesize + (rowcount % pagesize == 0 ? 0 : 1));
                rs.close();
                stmt.close();
                return pagecount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 根据小说ID查询小说内容
     */
    public BookInfoVO getBookInfoById(Integer bookid) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT bi.book_id,bi.book_name,bi.type_id,bi.author,bi.book_line,bt.type_name FROM book_info bi,book_type bt WHERE bi.type_id=bt.type_id AND book_id=" + bookid;
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            BookInfoVO info = new BookInfoVO();
            info.setBookid(rs.getInt(1));
            info.setBookname(rs.getString(2));
            info.setTypeid(rs.getInt(3));
            info.setAuthor(rs.getString(4));
            info.setBookline(rs.getString(5));
            info.setTypename(rs.getString(6));
            rs.close();
            stmt.close();
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 阅读书籍ID和章节数阅读章节内容
     */
    public String getNeiRongByBidAndZid(Integer ppk, Integer bookid, Integer zjid, Integer pagesize) {
        try {
            this.addBookReadCount(bookid);
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT substr(zj_neirong,1," + pagesize + ") FROM book_neirong WHERE book_id=" + bookid + " AND zj_count=" + zjid;
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            String neirong = rs.getString(1);
            rs.close();
            stmt.close();
            return neirong;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 加入我的收藏夹
     */
    public void addToMyBox(BookMeVO booktome) {
        String sql = "INSERT INTO book_mine values(null," + booktome.getRoleid() + "," + booktome.getBookid() + "," + booktome.getTypeid() + ",'" + booktome.getBookmark() + "')";
        try {
            this.addBookStow(booktome.getBookid());
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
    }

    /**
     * 查看我的书架
     */
    public List<BookMeVO> findMyBookBox(Integer roleid) {
        String sql = "SELECT * FROM (SELECT bm.my_id,bm.book_id,bt.type_name,bi.book_name,max(nr.zj_count) mcount,nr.zj_name,max(nr.gx_date),bm.book_mark,bm.role_id,bm.type_id " + "FROM book_info bi,book_type bt,book_neirong nr,book_mine bm " + "WHERE bi.type_id=bt.type_id AND bm.book_id=bi.book_id AND nr.book_id=bi.book_id AND bm.role_id=" + roleid + " GROUP BY bm.book_id,nr.zj_name  ORDER BY nr.gx_date desc) tab GROUP BY tab.book_id ORDER BY tab.mcount desc";
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<BookMeVO> mybox = new ArrayList<BookMeVO>();
            while (rs.next()) {
                BookMeVO bm = new BookMeVO();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 获取当前我的此角色里共存了多少本书
     */
    public Integer getMyBookCount(Integer roleid) {
        String sql = "SELECT count(*) FROM book_mine WHERE role_id=" + roleid;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            Integer sc = rs.getInt(1);
            rs.close();
            stmt.close();
            return sc;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 根据我的书架里的存储书籍的主键删除书籍
     */
    public void deleteMyBook(Integer myid) {
        String sql = "delete FROM book_mine WHERE my_id=" + myid;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
    }

    /**
     * 判断我收藏的书籍是否有重复
     */
    public boolean checkReplace(Integer roleid, Integer bookid) {
        String sql = "SELECT * FROM book_mine WHERE role_id=" + roleid + " AND book_id=" + bookid;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            boolean bool = rs.next();
            rs.close();
            stmt.close();
            return bool;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return false;
    }

    /**
     * 按小说名和角色id查找小说
     */
    public List<NewInfo> getBookByRidAndName(Integer roleid, String name) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT bi.author,bi.book_id,bt.type_name,bi.book_name,bt.type_id " + "FROM book_info bi,book_type bt,book_mine bm " + "WHERE bi.type_id=bt.type_id AND bm.book_id=bi.book_id AND bi.book_name LIKE '%" + name.trim() + "%' AND bm.role_id=" + roleid;
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<NewInfo> infolist = new ArrayList<NewInfo>();
            while (rs.next()) {
                NewInfo info = new NewInfo();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 按作者查询小说
     */
    public List<NewInfo> showSearchResult(String novename) {
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            String sql = "SELECT bi.author,bi.book_id,bt.type_name,bi.book_name,bt.type_id " + "FROM book_info bi,book_type bt " + "WHERE bi.type_id=bt.type_id AND bi.author LIKE '%" + novename.trim() + "%'";
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<NewInfo> infolist = new ArrayList<NewInfo>();
            while (rs.next()) {
                NewInfo info = new NewInfo();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 根据书籍主键获取书籍名称
     */
    public String getBookNameById(Integer bookid) {
        String sql = "SELECT book_name FROM book_info WHERE book_id=" + bookid;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            String bookname = rs.getString(1);
            rs.close();
            stmt.close();
            return bookname;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 根据章节主键和书籍主键获取章节名称
     */
    public String getZjNameById(Integer zjcount, Integer bookid) {
        String sql = "SELECT zj_name FROM book_neirong WHERE zj_count=" + zjcount + " AND book_id=" + bookid;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            String zjname = rs.getString(1);
            rs.close();
            stmt.close();
            return zjname;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 根据书籍的主键查询该书籍共有多少个章节
     */
    public Integer getZjCountByBookId(Integer bookid) {
        String sql = "SELECT count(*) FROM book_neirong WHERE book_id=" + bookid;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            Integer zjcount = rs.getInt(1);
            rs.close();
            stmt.close();
            return zjcount;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 根据小说的主键获取小说的类别id
     */
    public Integer getTypeIdByBookId(Integer bookid) {
        String sql = "SELECT type_id FROM book_info WHERE book_id=" + bookid;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            Integer typeid = rs.getInt(1);
            rs.close();
            stmt.close();
            return typeid;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 查询前十本书籍的排行榜按收藏人次降序排列
     */
    public List<PhVO> getPhList(Integer phpage, Integer phpagesize) {
        String sql = "SELECT bi.book_id,bt.type_id,bt.type_name,bi.book_name,bi.book_stow FROM book_info bi,book_type bt " + "WHERE bt.type_id=bi.type_id AND bi.book_stow!=0 ORDER BY bi.book_stow desc  limit " + phpagesize + " offset " + (phpage - 1) * phpagesize;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<PhVO> phlist = new ArrayList<PhVO>();
            while (rs.next()) {
                PhVO ph = new PhVO();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 获取排行榜分页后的总页数
     */
    public Integer getPhPageCount(Integer phpagesize) {
        String sql = "SELECT count(*) FROM book_info bi,book_type bt WHERE bt.type_id=bi.type_id";
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            Integer rowcount = rs.getInt(1);
            Integer pagecount = (rowcount / phpagesize + (rowcount % phpagesize == 0 ? 0 : 1));
            rs.close();
            stmt.close();
            return pagecount;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 判断此角色在同一本书中存不存在
     */
    public Integer isExistInAmark(Integer bookid, Integer roleid) {
        String sql = "SELECT * FROM book_mine WHERE book_id=" + bookid + " AND role_id=" + roleid;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Integer ss = rs.getInt(1);
                rs.close();
                stmt.close();
                return ss;
            } else {
                rs.close();
                stmt.close();
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 根据当前玩家的角色ID和书籍的ID或添加该角色的书签或修改该角色的书签
     */
    public void addOrupdateMyBookMark(Integer bookid, Integer roleid, String mark, Integer typeid) {
        try {
            Integer markid = this.isExistInAmark(bookid, roleid);
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            if (markid.equals(0) || markid == 0) {
                String sql = "update book_info set book_stow=book_stow+1 WHERE book_id=" + bookid;
                String sql1 = "INSERT INTO book_mine values(null," + roleid + "," + bookid + "," + typeid + ",'" + mark + "')";
                stmt.executeUpdate(sql1);
                stmt.executeUpdate(sql);
                stmt.close();
            } else {
                String sql2 = "update book_mine set book_mark='" + mark + "' WHERE my_id=" + markid;
                stmt.executeUpdate(sql2);
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
    }

    /**
     * 根据章节编号查询出章节当前章节数
     */
    public Integer getZjCountByZjId(Integer zjid) {
        try {
            String sql = "SELECT zj_count FROM book_neirong WHERE nr_id=" + zjid;
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            Integer zjc = rs.getInt(1);
            rs.close();
            stmt.close();
            return zjc;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 查询某个章节共多少页
     */
    public Integer getZjPageCount(Integer bookid, Integer zjcount, Integer pagesize) {
        try {
            String sql = "SELECT char_length(zj_neirong) FROM book_neirong WHERE book_id=" + bookid + " AND zj_count=" + zjcount;
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Integer allzs = rs.getInt(1);
                Integer allcount = (allzs / pagesize + (allzs % pagesize == 0 ? 0 : 1));
                rs.close();
                stmt.close();
                return allcount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 查询每一页的内容显示给玩家
     **/
    public String getZjPageLine(Integer bookid, Integer zjcount, Integer page, Integer pagesize) {
        try {
            String sql = "SELECT substr(zj_neirong," + ((page - 1) * pagesize + 1) + "," + pagesize + ") FROM book_neirong WHERE book_id=" + bookid + " AND zj_count=" + zjcount;
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String pageline = rs.getString(1);
                rs.close();
                stmt.close();
                return pageline;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }

    /**
     * 添加小说的收藏次数
     **/
    public void addBookStow(Integer bookid) {
        try {
            String sql = "update book_info set book_stow=book_stow+1 WHERE book_id=" + bookid;
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
    }

    /**
     * 添加小说的阅读次数
     **/
    public void addBookReadCount(Integer bookid) {
        String sql = "update book_info set read_count=read_count+1 WHERE book_id=" + bookid;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
    }

    /**
     * 添加阅读记录
     **/
    public void addReadRecord(Integer ppk, Integer bookid, Integer zjcount) {
        String sql = "INSERT INTO book_read_record values(null," + ppk + "," + bookid + "," + zjcount + ",sysdate())";
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
    }

    /**
     * 查询玩家是否读过此本书的此章节
     **/
    public boolean checkIsReadTheBookZj(Integer ppk, Integer bookid, Integer zjcount) {
        String sql = "SELECT * FROM book_read_record WHERE read_ppk=" + ppk + " AND read_bid=" + bookid + " AND read_zj=" + zjcount;
        try {
            dbcon = new DBConnection(DBConnection.GAME_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            boolean bool = rs.next();
            rs.close();
            stmt.close();
            return bool;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return false;
    }

    /**
     * 费用即:银两
     * 每当看章节时扣费
     * 缴纳银两=2*玩家等级
     **/
    public void substrutTheRate(Integer ppk) {
        String sql = "SELECT p_grade FROM u_part_info WHERE p_pk=" + ppk;
        try {
            dbcon = new DBConnection(DBConnection.GAME_USER_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Integer grade = rs.getInt(1);
                String sql1 = "update u_part_info set p_copper=p_copper-" + grade * 2;
                stmt.executeUpdate(sql1);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
    }

    /**
     * 判断玩家是否有足够的银两看小说
     */
    public boolean checkHaveEnoughCopper(Integer ppk) {
        String sql = "SELECT p_grade,p_copper FROM u_part_info WHERE p_pk=" + ppk;
        try {
            dbcon = new DBConnection(DBConnection.GAME_USER_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Integer rate = rs.getInt(1) * 2;
                Integer copper = rs.getInt(2);
                if (copper >= rate) {
                    rs.close();
                    stmt.close();
                    return true;
                } else {
                    rs.close();
                    stmt.close();
                    return false;
                }
            } else {
                rs.close();
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return false;
    }

    /**
     * 查询玩家本角色等级
     **/
    public String getThePgradeByPpk(Integer ppk) {
        String sql = "SELECT p_grade,p_copper FROM u_part_info WHERE p_pk=" + ppk;
        try {
            dbcon = new DBConnection(DBConnection.GAME_USER_DB);
            Statement stmt = dbcon.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            StringBuffer gcstr = new StringBuffer();
            if (rs.next()) {
                Integer grade = rs.getInt(1);
                Integer copper = rs.getInt(2);
                if (grade == null) {
                    gcstr.append(0);
                } else {
                    gcstr.append(grade + ",");
                }
                if (copper == null) {
                    gcstr.append(0);
                } else {
                    gcstr.append(copper);
                }
                rs.close();
                stmt.close();
                return gcstr.toString();
            } else {
                rs.close();
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbcon.closeConn();
        }
        return null;
    }
}




































