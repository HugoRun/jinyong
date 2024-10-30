package com.ls.model.user;

import com.ls.ben.dao.DaoBase;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author handan
 * 持久化实体
 */
public class PersistenceEntity {
    /**
     * 持久化需要的dao
     */
    private DaoBase dao = null;
    /**
     * 表名
     */
    private String tab_name;
    /**
     * primary主键字段名
     */
    private String primary_name;
    /**
     * primary主键值
     */
    private String primary_value;
    /**
     * 需要持久化的字段
     */
    private HashMap<String, String> persistenceColumns;

    public PersistenceEntity() {

    }


    public PersistenceEntity(String tab_name, String primary_name, String primary_value) {
        this.init(tab_name, primary_name, primary_value);
    }

    /**
     * 初始化
     */
    public void init(String tab_name, String primary_name, String primary_value) {
        this.dao = new DaoBase();
        this.tab_name = tab_name;
        this.primary_name = primary_name;
        this.primary_value = primary_value;
        persistenceColumns = new HashMap<String, String>(20);
    }

    /**
     * 增加要持久化的字段
     */
    public void addPersistenceColumn(String column, String value) {
        if (column == null || value == null) {
            return;
        }
        persistenceColumns.put(column, value);

    }


    /**
     * 执行持久化操作
     *
     * @param primary主键
     */
    public void persistence() {
        if (persistenceColumns.size() == 0) {
            return;
        }

        StringBuffer update_sql = new StringBuffer();

        update_sql.append("UPDATE ").append(this.tab_name).append(" SET ");

        StringBuffer set_sql = new StringBuffer();
        String column = null;
        String value = null;
        Iterator<String> iterator = persistenceColumns.keySet().iterator();
        while (iterator.hasNext()) {
            column = iterator.next();
            value = persistenceColumns.get(column);
            if (set_sql.toString().isEmpty()) {
                set_sql.append(column + " = '" + value + "'");
            } else {
                set_sql.append(", " + column + "='" + value + "'");
            }
        }
        update_sql.append(set_sql).append(" WHERE ").append(primary_name).append(" = '").append(primary_value).append("'");

        this.persistenceColumns.clear();
        dao.executeUpdateSql(update_sql.toString());
    }
}
