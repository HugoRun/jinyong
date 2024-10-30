package com.ben.dao.honour;

import com.ben.vo.honour.TitleVO;
import com.ls.ben.dao.BasicDaoSupport;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 系统称号
 */
public class TitleDAO extends BasicDaoSupport<TitleVO> {

    public TitleDAO() {
        super("title_info", DBConnection.GAME_DB);
    }

    /**
     * 根据多称谓id，得到称谓名称字符串
     *
     * @param title_ids
     * @return
     */
    public String getTitleNamesByTitleIDs(String title_ids) {
        StringBuffer title_names = new StringBuffer();
        String sql = "SELECT name FROM `title_info` WHERE id IN (" + title_ids + ")";
        logger.debug(sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
        conn = dbConn.getConn();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (title_names.length() == 0) {
                    title_names.append(StringUtil.isoToGBK(rs.getString("name")));
                } else {
                    title_names.append("," + StringUtil.isoToGBK(rs.getString("name")));
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.closeConn();
        }
        return title_names.toString();
    }

    /**
     * 通过id得到称号信息
     *
     * @return
     */
    public TitleVO getById(String id) {
        return this.getOneBySql("WHERE id = " + id);
    }

    /**
     * 通过名字得到称号信息
     *
     * @param name
     * @return
     */
    public TitleVO getByName(String name) {
        return this.getOneBySql("WHERE name = " + name + " LIMIT 1");
    }

    /**
     * 通过名字和类型得到称号信息
     *
     * @param name
     * @param type
     * @return
     */
    public TitleVO getByNameAndType(String name, int type) {
        return this.getOneBySql("WHERE name = " + name + " AND type = " + type + " LIMIT 1");
    }


    @Override
    protected TitleVO loadData(ResultSet rs) throws SQLException {
        TitleVO title = new TitleVO();
        title.setId(rs.getInt("id"));
        title.setName(rs.getString("name"));
        title.setType(rs.getInt("type"));
        title.setTypeName(rs.getString("type_name"));
        title.setDes(rs.getString("des"));
        title.setAttriStr(rs.getString("attri_str"));
        title.setUseTime(rs.getInt("use_time"));
        return title;
    }
}
