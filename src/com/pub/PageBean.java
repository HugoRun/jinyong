package com.pub;

public class PageBean {
    /**
     * 一行显示的最大记录数
     */
    public int MAX_ROW = 15;
    /**
     * 当前开始位置
     */
    private int index;
    /**
     * 总页数
     */
    private int page_num;
    /**
     * 查询的结果集大小
     */
    private int total;
    /**
     * 返回链接
     */
    private String url;

    public PageBean() {
        index = 0;
        page_num = 1;
        total = 0;
    }

    public int getTotal() {
        return total;
    }

    /**
     * 设置总页数
     *
     * @param num
     */
    public void setTotal(int num) {
        if (num > 0) {
            total = num;
            if (total % MAX_ROW == 0) {
                page_num = total / MAX_ROW;
            } else {
                page_num = total / MAX_ROW + 1;
            }
        } else {
            total = 0;
            page_num = 1;
        }

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setIndex(String index) {
        int no = 1;
        if (index != null && !index.trim().equals("")) {
            if (isNumber(index)) {
                no = Integer.parseInt(index);
                if (no < 1) {
                    no = 1;
                }
            }
        }
        this.index = no;
    }

    public int getPageNum() {
        return page_num;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMAX_ROW() {
        return MAX_ROW;
    }

    public void setMAX_ROW(int max_row) {
        MAX_ROW = max_row;
    }

    /**
     * 初始化
     *
     * @param index
     * @param max_row
     * @param num
     * @param url
     */
    public void init(String index, int max_row, int num, String url) {
        if (max_row < 1) {
            this.MAX_ROW = 1;
        } else {
            this.MAX_ROW = max_row;
        }
        if (url == null || url.trim().equals("")) {
            this.url = "index.jsp";
        } else {
            this.url = url;
        }
        setTotal(num);
        int no = 1;
        if (index != null && !index.trim().equals("") && !index.trim().equals("null")) {
            if (isNumber(index)) {
                no = Integer.parseInt(index);
                if (no > page_num) {
                    no = page_num;
                }
                if (no < 1) {
                    no = 1;
                }
            }
        }
        this.index = no;
    }

    /**
     * 在文本中插入页面标号为num的标记
     *
     * @param text
     * @param num  起始位置
     */
    private void insertTag(StringBuffer text, int num) {
        int temp = index / MAX_ROW;
        if (num == temp) {
            text.append(num + 1).append("  ");
        } else {
            text.append("<a href=" + url + "&start_index=");
            text.append(num * MAX_ROW).append(">").append(num + 1).append("</a>  ");
        }
    }

    /**
     * 在文本中插入位置为起始位置为num,页面标号为str的标记
     *
     * @param text
     * @param num  起始位置
     * @param str  页面编号
     */
    private void insertTag(StringBuffer text, int num, String str) {
        text.append("<a href=" + url + "&start_index=");
        text.append(num).append(">").append(str).append("</a>  ");
    }

    /**
     * 在wap文本中插入跳转编辑筐
     */
    public String insertTag() {
        String text = "共" + total +
                "个 " +
                index + "/" + page_num +
                " <input name='pageNo'  format='*N' type='text' maxlength='4' size='2' />页<anchor><go href='" +
                this.url +
                "' method='post'><postfield name='pageNo' value='$pageNo'/></go>GO</anchor>";
        return text;
    }

    /**
     * 文WEB本中插入跳转编辑筐
     */
    public String insertWEBTag() {

        String text = "共" + total +
                "个 " +
                index + "/" + page_num +
                " <form action='" +
                this.url +
                "'><input name='pageNo'  format='*N' type='text' maxlength='4' size='2' />页" +
                "<input value='跳到' type='submit'></form>";
        return text;
    }

    /**
     * 打印出翻页连接
     *
     * @return
     */
    public String getFooter() {
        if (page_num <= 1) {
            return "";
        } else {
            StringBuffer str = new StringBuffer();
            if (index == 1) {
                str.append("<a href='").append(url).append("&amp;pageNo=");
                str.append(index + 1);
                str.append("'>下一页</a>");
                if (page_num > 2) {
                    str.append("<a href='").append(url).append("&amp;pageNo=");
                    str.append(page_num);
                    str.append("'>到尾页</a>");
                }
            } else if (index < page_num) {
                str.append("<a href='").append(url).append("&amp;pageNo=");
                str.append(index + 1);
                str.append("'>下一页</a> ");
                str.append("<a href='").append(url).append("&amp;pageNo=");
                str.append(index - 1);
                str.append("'>上一页</a>");
            } else {
                str.append("<a href='").append(url).append("&amp;pageNo=");
                str.append(index - 1);
                str.append("'>上一页</a>");
            }


            if (index == page_num && page_num > 2) {
                str.append("<a href='").append(url).append("&amp;pageNo=");
                str.append(1);
                str.append("'>到首页</a>");
            }
            return str.toString();
        }
    }

    /**
     * 是否为数字
     *
     * @param str String
     * @return boolean
     */
    private boolean isNumber(String str) {

        if (str == null || str.equals("")) {
            return false;
        }
        String num = "0123456789";
        boolean Flag = true;
        char[] c = str.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (str.indexOf(c[i]) < 0) {
                Flag = false;
                break;
            }
        }
        return Flag;
    }

}
