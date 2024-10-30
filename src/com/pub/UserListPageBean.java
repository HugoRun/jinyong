package com.pub;

public class UserListPageBean {
		 /**
	     * һ����ʾ������¼��
	     */
	    public int MAX_ROW = 7;
	    /**
	     * ��ǰ��ʼλ��
	     */
	    private int index=1;
	    /**
	     * ��ҳ��
	     */
	    private int page_num;
	    /**
	     * ��ѯ�Ľ������С
	     */
	    private int total;
	    /**
	     * ��������
	     */
	    private String url;

	    public UserListPageBean() {
	        index = 0;
	        page_num = 1;
	        total = 0;
	    }
	    
	    public int getTotal() {
	        return total;
	    }
	    public int getIndex() {
	        return index;
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

		public int getMAX_ROW(){
			return MAX_ROW;
		}
		public void setMAX_ROW(int max_row) {
			MAX_ROW = max_row;
		}
	    public void setIndex(int index) {
	        this.index = index;
	    }
	    public void setIndex(String index) {
	    	int no=1;
	    	if(index!=null&&!index.trim().equals(""))
	    	{
	    		if(isNumber(index))
	    		{
	    			no=Integer.parseInt(index);
	    			if(no<1)
	    			{
	    				no=1;
	    			}
	    		}
	    	}
	        this.index =no;
	    }
	    
	    /**
	     * ��ʼ��
	     * @param index
	     * @param num
	     * @param url
	     */
	    public void init(String index,int num,String url)
	    {
	    	this.init(index, this.MAX_ROW, num, url);
	    }
	    
	    /**
	     * ��ʼ��
	     * @param index
	     * @param max_row
	     * @param num
	     * @param url
	     */
	    public void init(String index,int max_row,int num,String url)
	    {
	    	if(max_row<1)
	    	{
	    		this.MAX_ROW=1;
	    	}
	    	else
	    	{
	    		this.MAX_ROW=max_row;
	    	}
	    	if(url==null||url.trim().equals(""))
	    	{
	    		this.url="index.jsp";
	    	}
	    	else
	    	{
	    		this.url=url;
	    	}
	    	setTotal(num);
	    	int no=1;
	    	if(index!=null&&!index.trim().equals("") &&!index.trim().equals("null"))
	    	{
	    		if(isNumber(index))
	    		{
	    			no=Integer.parseInt(index);
	    			if(no>page_num)
	    			{
	    				no=page_num;
	    			}
	    			if(no<1)
	    			{
	    				no=1;
	    			}
	    		}
	    	}
	        this.index =no;
	    }
	    /**
	     * ������ҳ��
	     * @param num
	     */
	    public void setTotal(int num) {
	       if(num>0)
	       {
	    	   total = num;
	           if (total % MAX_ROW == 0) {
	               page_num = total / MAX_ROW;
	           }
	           else {
	               page_num = total / MAX_ROW + 1;
	           } 
	       }else
	       {
	    	   total=0 ;
	    	   page_num=1;
	       }
	    	
	    }
	    
	    /**
	     * ���ı��в���ҳ����Ϊnum�ı��
	     * @param text
	     * @param num ��ʼλ��
	     */
	    private void insertTag(StringBuffer text, int num) {
	        int temp = index / MAX_ROW;
	        if (num == temp) {
	            text.append(num + 1).append("  ");
	        }
	        else {
	            text.append("<a href=" + url + "&start_index=");
	            text.append(num * MAX_ROW).append(">").append(num + 1).append("</a>  ");
	        }
	    }

	    /**
	     * ���ı��в���λ��Ϊ��ʼλ��Ϊnum,ҳ����Ϊstr�ı��
	     * @param text
	     * @param num ��ʼλ��
	     * @param str ҳ����
	     */
	    private void insertTag(StringBuffer text, int num, String str) {
	        text.append("<a href=" + url + "&start_index=");
	        text.append(num).append(">").append(str).append("</a>  ");
	    }
	    /**
	     * ��wap�ı��в�����ת�༭��
	     */
	    public String insertTag() {
	    	StringBuffer text=new StringBuffer("��");
	        text.append(total);
	        text.append("�� ");
	        text.append(index).append("/").append(page_num);
	        text.append(" <input name='pageNo'  format='*N' type='text' maxlength='4' size='2' />ҳ<anchor><go href='");
	        text.append(this.url);
	        text.append("' method='post'><postfield name='pageNo' value='$pageNo'/></go>GO</anchor>");
	       	return text.toString();
	    }
	    /**
	     *��WEB���в�����ת�༭��
	     */
	    public String insertWEBTag() {
	    	StringBuffer text=new StringBuffer("��");
	        text.append(total);
	        text.append("�� ");
	        text.append(index).append("/").append(page_num);
	        text.append(" <form action='");
	        text.append(this.url);
	        text.append("'><input name='pageNo'  format='*N' type='text' maxlength='4' size='2' />ҳ");
	        
	        text.append("<input value='����' type='submit'></form>");
	       	return text.toString();
	    } 
	   /**
	    * ��ӡ����ҳ����
	    * @return
	    */
	    public String getFooter() {
	        if (page_num<= 1) {
	            return "";
	        }
	        else
	        {
	        	StringBuffer str = new StringBuffer();
	            if(index==1)
	            {
	            	str.append("<a href='").append(url).append("&amp;pageNo=");
	            	str.append(index+1);
	            	str.append("'>��һҳ</a>");
	            }
	            else if (index < page_num) {
	            	str.append("<a href='").append(url).append("&amp;pageNo=");
	            	str.append(index+1);
	            	str.append("'>��һҳ</a> ");
	            	str.append("<a href='").append(url).append("&amp;pageNo=");
	            	str.append(index-1);
	            	str.append("'>��һҳ</a>");
	            }
	            else {
	            	str.append("<a href='").append(url).append("&amp;pageNo=");
	            	str.append(index-1);
	            	str.append("'>��һҳ</a>");
	                 }
	          return str.toString();
	        }
	    }
	    /**
	     * �Ƿ�Ϊ����
	     * @param str String
	     * @return boolean
	     */
	   private boolean isNumber(String str) {
	  	  
	  	 if(str==null||str.equals(""))
	  	 {
	  		 return false;
	  	 }
	      String num = "0123456789";
	      boolean Flag = true;
	      char c[] = str.toCharArray();

	      for (int i = 0; i <c.length; i++) {
	        if (str.indexOf(c[i]) < 0) {
	          Flag = false;
	          break;
	        }
	      }
	      return Flag;
	    }

}
