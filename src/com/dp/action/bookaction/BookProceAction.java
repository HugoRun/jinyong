package com.dp.action.bookaction;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dp.dao.book.BookProce;
import com.dp.dao.book.ExprienceProce;
import com.dp.vo.bzjvo.BookZJVO;
import com.dp.vo.infovo.BookInfoVO;
import com.dp.vo.mybookvo.BookMeVO;
import com.dp.vo.newbook.NewInfo;
import com.dp.vo.phb.PhVO;
import com.dp.vo.pubvo.BookPageVO;
import com.dp.vo.typevo.BookTypeVO;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.model.user.RoleEntity;
import com.pub.Listener.OnlineCounterListener;
public class BookProceAction extends DispatchAction{
	 //�������ʱ��ʱ��
	 //����������� ��ѯ�鼮��� ��ѯ�鼮����Ϣ
	  public ActionForward n1(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  HttpSession session=request.getSession();
		  if(session.getAttribute("bookpagebean")==null){
			  BookPageVO bookpagebean=new BookPageVO();
			  session.setAttribute("bookpagebean",bookpagebean);
		  }
		  BookPageVO bookpagebean=(BookPageVO)session.getAttribute("bookpagebean");
		  bookpagebean.setTipsign(0);
		  List<BookTypeVO> typelist=proce.showTypes();
		  List<NewInfo>  infolist=proce.showNewInfo();
		  request.setAttribute("infolist",infolist);
		  request.setAttribute("typelist",typelist);
		  Date startdate=new Date();
		  if(bookpagebean.getHour()==0&&bookpagebean.getMinutes()==0){
			  bookpagebean.setHour(startdate.getHours());
			  bookpagebean.setMinutes(startdate.getMinutes());
			  //OnlineCounterListener.hour=bookpagebean.getHour();
			  //OnlineCounterListener.minute=bookpagebean.getMinutes();
		  }
		  proce=null;
		  return mapping.findForward("typelist");
	  }
	  //����С˵���ƹؼ����������鼮
	  public ActionForward n2(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  BookPageVO bookpagebean=(BookPageVO)request.getSession().getAttribute("bookpagebean");
		  if(request.getParameter("tipsign")!=null){
			  bookpagebean.setTipsign(Integer.parseInt(request.getParameter("tipsign")));
		  }
		  bookpagebean.setResultsign(1);
		  String novename=request.getParameter("novename");
		  List<NewInfo>  searchlist=proce.showSearchInfo(novename);
		  request.setAttribute("searchlist",searchlist);
		  proce=null;
		  return mapping.findForward("bookresult");
	  }
	  //����С˵����ѯС˵
	  public ActionForward n3(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  BookPageVO bookpagebean=(BookPageVO)request.getSession().getAttribute("bookpagebean");
		  if(request.getParameter("typeid")!=null){
			  bookpagebean.setTypeid(Integer.parseInt(request.getParameter("typeid")));
		  }
		  if(request.getParameter("tipsign")!=null){
			  bookpagebean.setTipsign(Integer.parseInt(request.getParameter("tipsign")));
		  }
		  if(request.getParameter("typepage")!=null){
			  bookpagebean.setTypepage(Integer.parseInt(request.getParameter("typepage")));
		  }
		  Integer tpcount=proce.getTypePageCount(bookpagebean.getTypeid(),bookpagebean.getTypepagesize());
		  if(bookpagebean.getTypepage()<=1){
			  bookpagebean.setTypepage(1);
		  }else if(bookpagebean.getTypepage()>tpcount){
			  bookpagebean.setTypepage(tpcount);
		  }
		  String typename=proce.getTypeById(bookpagebean.getTypeid());
		  List<NewInfo>  searchlist=proce.getBookByType(bookpagebean.getTypeid(),bookpagebean.getTypepage(),bookpagebean.getTypepagesize());
		  request.setAttribute("searchlist",searchlist);
		  request.setAttribute("typename",typename);
		  request.setAttribute("typeid",bookpagebean.getTypeid());
		  request.setAttribute("typepage",bookpagebean.getTypepage());
		  request.setAttribute("tpcount",tpcount);
		  proce=null;
		  return mapping.findForward("booklist");
	  }
	  //��ѯĳ��С˵�����а�
	  public ActionForward n4(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  BookPageVO bookpagebean=(BookPageVO)request.getSession().getAttribute("bookpagebean");
		  if(request.getParameter("typeid")!=null){
			  bookpagebean.setTypeid(Integer.parseInt(request.getParameter("typeid")));
		  }
		  if(request.getParameter("tipsign")!=null){
			  bookpagebean.setTipsign(Integer.parseInt(request.getParameter("tipsign")));
		  }
		  if(request.getParameter("squpage")!=null){
			  bookpagebean.setSqupage(Integer.parseInt(request.getParameter("squpage")));
		  }
		  Integer squpagecount=proce.getNoveSquCount(bookpagebean.getTypeid(), bookpagebean.getSqupagesize());
		  if(bookpagebean.getSqupage()<=1){
			  bookpagebean.setSqupage(1);
		  }else if(bookpagebean.getSqupage()>squpagecount){
			  bookpagebean.setSqupage(squpagecount);
		  }
		  String typename=proce.getTypeById(bookpagebean.getTypeid());
		  List<PhVO> phlist=proce.getNoveSqu(bookpagebean.getTypeid(),bookpagebean.getSqupage(),bookpagebean.getSqupagesize());
		  request.setAttribute("phlist",phlist);
		  request.setAttribute("typeid",bookpagebean.getTypeid());
		  request.setAttribute("typename",typename);
		  request.setAttribute("squpage",bookpagebean.getSqupage());
		  request.setAttribute("squpagecount",squpagecount);	
		  request.setAttribute("typeid",bookpagebean.getTypeid());
		  proce=null;
		  return mapping.findForward("showphb");
	  }
	  //��ѯС˵�Ļ�����Ϣ���½�����
	  public ActionForward n5(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  BookPageVO bookpagebean=(BookPageVO)request.getSession().getAttribute("bookpagebean");
		  if(request.getParameter("tipsign")!=null){
			  bookpagebean.setTipsign(Integer.parseInt(request.getParameter("tipsign")));
		  }
		  if(request.getParameter("page")!=null&&bookpagebean.getExists()==0){
    		  try{
    			  bookpagebean.setZjlistpage(Integer.parseInt(request.getParameter("page")));
    		  }catch(NumberFormatException e){
    			  request.setAttribute("mess","�����ҳ�����Ϊ����!");
    			  bookpagebean.setExists(1);
    			  return this.n5(mapping, form, request, response);
    		  }
		  }
		  if(request.getParameter("bookid")!=null){
			  bookpagebean.setBookid(Integer.parseInt(request.getParameter("bookid")));
		  }
		  Integer zjlistpagecount=proce.getZjListPageCount(bookpagebean.getBookid(),bookpagebean.getZjpagesize());
		  if(bookpagebean.getZjlistpage()<=1){
			  bookpagebean.setZjlistpage(1);
		  }else if(bookpagebean.getZjlistpage()>zjlistpagecount){
			  bookpagebean.setZjlistpage(zjlistpagecount);
		  }
		  NewInfo info=proce.getNewZjInfoByBookId(bookpagebean.getBookid());
		  BookInfoVO book=proce.getBookInfoById(bookpagebean.getBookid());
		  List<BookZJVO> zjinfo=proce.getZJbyBookId(bookpagebean.getBookid(),bookpagebean.getZjlistpage(),bookpagebean.getZjpagesize());
		  request.setAttribute("bookinfo",book);
		  request.setAttribute("bookid",bookpagebean.getBookid());
		  request.setAttribute("zjinfo",zjinfo);
		  request.setAttribute("zjlistpage",bookpagebean.getZjlistpage());
		  request.setAttribute("zjlistcount",zjlistpagecount);
		  request.setAttribute("newzjinfo",info);
		  bookpagebean.setExists(0);
		  proce=null;
		  return mapping.findForward("zjinfo");
	  }
	  //���½ڷ�ҳ����ʾ������Ķ�
	  public ActionForward n17(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		  BookProce proce=new BookProce();
		  BookPageVO bookpagebean=(BookPageVO)request.getSession().getAttribute("bookpagebean");
		  if(request.getParameter("tipsign")!=null){
			  bookpagebean.setTipsign(Integer.parseInt(request.getParameter("tipsign")));
		  }
		  Integer bookid=Integer.parseInt(request.getParameter("bookid"));
		  Integer zjid=Integer.parseInt(request.getParameter("zjid"));
		  bookpagebean.setPage(Integer.parseInt(request.getParameter("page")));
		  Integer pagecount=proce.getZjPageCount(bookid,zjid,bookpagebean.getPagesize());
		  if(bookpagebean.getPage()<=1){
			  bookpagebean.setPage(1);
		  }else if(bookpagebean.getPage()>pagecount){
			  bookpagebean.setPage(pagecount);
		  }
		  Integer zjcount=proce.getZjCountByBookId(bookid);
		  String bookname=proce.getBookNameById(bookid);
		  String zjname=proce.getZjNameById(zjid,bookid);
		  String neirong=proce.getZjPageLine(bookid,zjid, bookpagebean.getPage(), bookpagebean.getPagesize());
		  request.setAttribute("neirong",neirong);
		  request.setAttribute("bookname",bookname);
		  request.setAttribute("zjname",zjname);
		  request.setAttribute("zjcount",zjid);
		  request.setAttribute("bookid",bookid);
		  request.setAttribute("pagecount",pagecount);
		  request.setAttribute("page",bookpagebean.getPage());
		  request.setAttribute("zjgross",zjcount);
		  proce=null;
		  return mapping.findForward("readline");
    	}
	  //�Ķ��鼮ID���½����Ķ��½�����
	  public ActionForward n7(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  HttpSession session=request.getSession();
		  String pPk = (String) request.getSession().getAttribute("pPk");
		  BookPageVO bookpagebean=(BookPageVO)session.getAttribute("bookpagebean");
		  if(request.getParameter("tipsign")!=null){
			  bookpagebean.setTipsign(Integer.parseInt(request.getParameter("tipsign")));
		  }
		  BookProce proce=new BookProce();
		  if(request.getParameter("bookid")!=null){
			  bookpagebean.setN7bookid(Integer.parseInt(request.getParameter("bookid")));
		  }
		  Integer zjcount=proce.getZjCountByBookId(bookpagebean.getN7bookid());
		  if(request.getParameter("zjid")!=null){
			  bookpagebean.setN7zjid(Integer.parseInt(request.getParameter("zjid")));
		  }
		  bookpagebean.setPage(1);
		  if(bookpagebean.getN7zjid()>=zjcount){
			  bookpagebean.setN7zjid(zjcount);
		  }if(bookpagebean.getN7zjid()<=1){
			  bookpagebean.setN7zjid(1);
		  }
		  boolean readbool=proce.checkIsReadTheBookZj(Integer.valueOf(pPk),bookpagebean.getN7bookid(),bookpagebean.getN7zjid());
		  if(readbool==true){
			  String bookname=proce.getBookNameById(bookpagebean.getN7bookid());
			  String zjname=proce.getZjNameById(bookpagebean.getN7zjid(),bookpagebean.getN7bookid());
			  String neirong=proce.getNeiRongByBidAndZid(Integer.valueOf(pPk),bookpagebean.getN7bookid(),bookpagebean.getN7zjid(),bookpagebean.getPagesize());
			  Integer pagecount=proce.getZjPageCount(bookpagebean.getN7bookid(),bookpagebean.getN7zjid(),bookpagebean.getPagesize());
			  request.setAttribute("neirong",neirong);
			  request.setAttribute("bookname",bookname);
			  request.setAttribute("zjname",zjname);
			  request.setAttribute("zjcount",bookpagebean.getN7zjid());
			  request.setAttribute("bookid",bookpagebean.getN7bookid());
			  request.setAttribute("pagecount",pagecount);
			  request.setAttribute("page",bookpagebean.getPage());
			  request.setAttribute("zjgross",zjcount);
			  proce=null;
			  return mapping.findForward("readline");
		  }else{
			  String[] gcstr=proce.getThePgradeByPpk(Integer.valueOf(pPk)).split(",");
			  Integer grade=Integer.parseInt(gcstr[0]);
			  Integer copper=Integer.parseInt(gcstr[1]);
			  boolean bool=proce.checkHaveEnoughCopper(Integer.valueOf(pPk));
			  if(bool==false){
				  request.setAttribute("sign",1);
				  request.setAttribute("copmess","�����е�����Ϊ"+copper+"��!");
				  request.setAttribute("message","��Ǹ,�����е�����������������һ��С˵!");
				  proce=null;
				  return mapping.findForward("clewmess");
			  }else{
				  request.setAttribute("sign",2);
				  request.setAttribute("copmess","�����е�����Ϊ"+copper+"��!");
				  request.setAttribute("message","��һ��С˵����������"+grade*2+"��!");
				  proce=null;
				  return mapping.findForward("clewmess");
			  }
		  }
	  }
	  //���ݶ��ߵ�ѡ�����������ҵ����������ʵ��Ĵ���
    	public ActionForward n18(ActionMapping mapping, ActionForm form,
    			HttpServletRequest request, HttpServletResponse response) throws Exception{
    	   HttpSession session=request.getSession();
    	   String pPk = (String) request.getSession().getAttribute("pPk");
    	   
    	   BookPageVO bookpagebean=(BookPageVO)session.getAttribute("bookpagebean");
    	   Integer sign=Integer.parseInt(request.getParameter("sign"));
    	   Integer ppk= Integer.valueOf(pPk);
    	   BookProce proce=new BookProce();
    	   if(sign==2){
    			proce.addReadRecord(ppk,bookpagebean.getN7bookid(),bookpagebean.getN7zjid());//����Ķ���¼
        		proce.substrutTheRate(ppk);//�۵�������Ӧ����
    	   }else{
    		   if(bookpagebean.getTipsign()==1){
    			   bookpagebean.setN7zjid(bookpagebean.getN7zjid()-1);
    		   }else if(bookpagebean.getTipsign()==-1){
    			   bookpagebean.setN7zjid(bookpagebean.getN7zjid()+1);
    		   }else if(bookpagebean.getTipsign()==0){
    			   return this.n1(mapping, form, request, response);
    		   }else if(bookpagebean.getTipsign()==2){
    			   return this.n5(mapping, form, request, response);
    		   }else if(bookpagebean.getTipsign()==3){
    			   return this.n9(mapping, form, request, response);
    		   }else if(bookpagebean.getTipsign()==4){
    			   return this.n3(mapping, form, request, response);
    		   }
    	   }
  		   Integer pagecount=proce.getZjPageCount(bookpagebean.getN7bookid(),bookpagebean.getN7zjid(),bookpagebean.getPagesize());
  		   Integer zjcount=proce.getZjCountByBookId(bookpagebean.getN7bookid());
  		   String bookname=proce.getBookNameById(bookpagebean.getN7bookid());
  		   String zjname=proce.getZjNameById(bookpagebean.getN7zjid(),bookpagebean.getN7bookid());
  		   String neirong=proce.getZjPageLine(bookpagebean.getN7bookid(),bookpagebean.getN7zjid(), bookpagebean.getPage(), bookpagebean.getPagesize());
  		   request.setAttribute("neirong",neirong);
  		   request.setAttribute("bookname",bookname);
  		   request.setAttribute("zjname",zjname);
  		   request.setAttribute("zjcount",bookpagebean.getN7zjid());
  		   request.setAttribute("bookid",bookpagebean.getN7bookid());
  		   request.setAttribute("pagecount",pagecount);
  		   request.setAttribute("page",bookpagebean.getPage());
  		   request.setAttribute("zjgross",zjcount);
  		   proce=null;
  		   return mapping.findForward("readline");
    	}
	  //����С˵���ƹؼ��ֺ�С˵����������鼮
	  public ActionForward n8(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  BookPageVO bookpagebean=(BookPageVO)request.getSession().getAttribute("bookpagebean");
		  if(request.getParameter("tipsign")!=null){
			 bookpagebean.setTipsign(Integer.parseInt(request.getParameter("tipsign"))); 
		  }
		  bookpagebean.setResultsign(2);
		  String novename=request.getParameter("novename");
		  Integer typeid=Integer.parseInt(request.getParameter("typeid"));
		  List<NewInfo>  searchlist=proce.getBookByTypeAndName(typeid,novename);
		  request.setAttribute("searchlist",searchlist);
		  proce=null;
		  return mapping.findForward("bookresult");
	  }
	  //����ҵ������ʾ�ҵ���� 
	  public ActionForward n9(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  HttpSession session=request.getSession();
		  String pPk = (String) request.getSession().getAttribute("pPk");
		  
		  BookPageVO bookpagebean=(BookPageVO)session.getAttribute("bookpagebean");
		  if(request.getParameter("tipsign")!=null){
			  bookpagebean.setTipsign(Integer.parseInt(request.getParameter("tipsign")));
		  }
		  Integer ppkid= Integer.valueOf(pPk);
		  List<BookMeVO> mylist=proce.findMyBookBox(ppkid);
		  Integer bookcount=proce.getMyBookCount(ppkid);
		  request.setAttribute("mylist",mylist);
		  request.setAttribute("bookcount",bookcount);
		  request.setAttribute("roleid",ppkid);
		  proce=null;
		  return mapping.findForward("mybookbox");
	  }
	 //��С˵�ղص��ҵ����
	  public ActionForward n10(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  Integer bookid=Integer.parseInt(request.getParameter("bookid"));
		  Integer typeid=Integer.parseInt(request.getParameter("typeid"));
		  HttpSession session=request.getSession();
		  String pPk = (String) request.getSession().getAttribute("pPk");
			
		  Integer ppkid= Integer.valueOf(pPk);
		  boolean bool=proce.checkReplace(ppkid,bookid);
		  if(bool==true){
			  request.setAttribute("mess","�����ղ��ظ��鼮!");
			  return mapping.findForward("message");
		  }
		  Integer count=proce.getMyBookCount(ppkid);
		  if(count>=5){
			  request.setAttribute("mess","��Ǹ,�����������!");
			  return mapping.findForward("message");
		  }
		  BookMeVO bm=new BookMeVO();
		  bm.setBookid(bookid);
		  bm.setTypeid(typeid);
		  bm.setRoleid(ppkid);
		  bm.setBookmark("0,0");
		  proce.addToMyBox(bm);
		  proce=null;
		  return this.n9(mapping, form, request, response);
	  }
	//�������ͷ���鼮ɾ����
	  public ActionForward n11(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  Integer myid=Integer.parseInt(request.getParameter("myid"));
		  proce.deleteMyBook(myid);
		  proce=null;
		  return this.n9(mapping, form, request, response);
	  }
	  //����С˵���ؼ��ֺͽ�ɫ������ѯ�ҵ�������С˵
	  public ActionForward n12(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  BookPageVO bookpagebean=(BookPageVO)request.getSession().getAttribute("bookpagebean");
		  if(request.getParameter("tipsign")!=null){
			  bookpagebean.setTipsign(Integer.parseInt(request.getParameter("tipsign")));
		  }
		  if(request.getParameter("novename")!=null){
			  bookpagebean.setNovename(request.getParameter("novename"));
		  }
		  bookpagebean.setResultsign(3);
		  Integer roleid=Integer.parseInt(request.getParameter("roleid"));
		  List<NewInfo> bmlist=proce.getBookByRidAndName(roleid, bookpagebean.getNovename());
		  request.setAttribute("searchlist",bmlist);
		  proce=null;
		  return mapping.findForward("bookresult");
	  }
	  //������������ѯͼ��
	  public ActionForward n13(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  BookPageVO bookpagebean=(BookPageVO)request.getSession().getAttribute("bookpagebean");
		  if(request.getParameter("tipsign")!=null){
			  bookpagebean.setTipsign(Integer.parseInt(request.getParameter("tipsign")));
		  }
		  if(request.getParameter("aut")!=null){
			  bookpagebean.setNovename(request.getParameter("aut"));
		  }
		  bookpagebean.setResultsign(4);
		  List<NewInfo>  searchlist=proce.showSearchResult(bookpagebean.getNovename());
		  request.setAttribute("searchlist",searchlist);
		  proce=null;
		  return mapping.findForward("bookresult");
	  }
	  //��ѯ�����鼮�����а��
	  public ActionForward n14(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  BookPageVO bookpagebean=(BookPageVO)request.getSession().getAttribute("bookpagebean");
		  if(request.getParameter("phpage")!=null){
			  bookpagebean.setPhpage(Integer.valueOf(request.getParameter("phpage")));
		  }
		  Integer pagecount=proce.getPhPageCount(bookpagebean.getPhpagesize());
		  if(bookpagebean.getPhpage()<=1){
			  bookpagebean.setPhpage(1);
		  }else if(bookpagebean.getPhpage()>pagecount){
			  bookpagebean.setPhpage(pagecount);
		  }
		  List<PhVO> phlist=proce.getPhList(bookpagebean.getPhpage(),bookpagebean.getPhpagesize());
		  request.setAttribute("phlist",phlist);
		  request.setAttribute("phpagecount",pagecount);
		  request.setAttribute("phpage",bookpagebean.getPhpage());
		  proce=null;
		  return mapping.findForward("allphb");
	  }
	  //����ǰ�½���Ϊ��ǩ���ݵ�ǰ��ҽ�ɫID���鼮id
	  public ActionForward n15(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  BookProce proce=new BookProce();
		  HttpSession session=request.getSession();
		  String pPk = (String) request.getSession().getAttribute("pPk");
			RoleCache roleCache = new RoleCache();
			RoleEntity roleEntity = roleCache.getByPpk(pPk);
			
		  
		  BookPageVO bookpagebean=(BookPageVO)session.getAttribute("bookpagebean");
		  if(request.getParameter("zjid")!=null){
			  bookpagebean.setZjid(Integer.parseInt(request.getParameter("zjid")));
		  }
		  if(request.getParameter("bookid")!=null){
			 bookpagebean.setBookid(Integer.parseInt(request.getParameter("bookid")));
		  }
		  Integer ppkid= Integer.valueOf(pPk);
		  Integer typeid=proce.getTypeIdByBookId(bookpagebean.getBookid());
		  Integer count=proce.getMyBookCount(ppkid);
		  if(count>=5){
			  request.setAttribute("mess","��Ǹ,�����������!");
			  return mapping.findForward("message");
		  }
		  proce.addOrupdateMyBookMark(bookpagebean.getBookid(), ppkid,(bookpagebean.getZjid()+","+bookpagebean.getPage()), typeid);
		  proce=null;
		  request.setAttribute("mess","��ǩ���óɹ�!");
		  return mapping.findForward("message");
	  }
	  //�˳����
	  public ActionForward n16(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		  ExprienceProce proce=new ExprienceProce();
		  Date enddate=new Date();
		  HttpSession session=request.getSession();
		  String pPk = (String) request.getSession().getAttribute("pPk");
			RoleCache roleCache = new RoleCache();
			RoleEntity roleEntity = roleCache.getByPpk(pPk);
			
		  BookPageVO bookpagebean=(BookPageVO)session.getAttribute("bookpagebean");
		  Integer endhour=enddate.getHours()-bookpagebean.getHour();
		  Integer endminu=enddate.getMinutes()-bookpagebean.getMinutes();
		  Integer submins=endhour*60+endminu;
		  Integer ppk= Integer.valueOf(pPk);
		  if(submins<10){
			  proce=null;
			  request.setAttribute("mess","�����������ʱ�䲻����Ϊ��Ӯȡ����!");
			  return mapping.findForward("exmess");
		  }else{
			  Integer dengji=proce.getPpkDengJi(ppk);
			  Integer yuanex=proce.getPpkExprience(ppk);
			  Integer addex=0;
			  int i=submins/10;
			  for(int k=0;k<i;k++){
				  addex+=(dengji*dengji*dengji/10+5);
			  }
			  yuanex+=addex;
			  proce.addPlayerExprience(ppk,yuanex);
			  proce=null;
			  request.setAttribute("mess","��ϲ��,�ѼӾ���Ϊ:"+addex+"!");
			  bookpagebean.setHour(0);bookpagebean.setMinutes(0);
			  //OnlineCounterListener.hour=0;
			  //OnlineCounterListener.minute=0;
			  return mapping.findForward("exmess");
		  }
	  }
	  //������һ��
	public ActionForward n19(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		 BookPageVO bookpagebean=(BookPageVO)request.getSession().getAttribute("bookpagebean");
		if(bookpagebean.getTipsign()==0){
			return this.n1(mapping, form, request, response);
		}else if(bookpagebean.getTipsign()==5||bookpagebean.getTipsign()==4){
			return this.n3(mapping, form, request, response);
		}else if(bookpagebean.getTipsign()==6){
			return this.n14(mapping, form, request, response);
		}else if(bookpagebean.getTipsign()==7){
			return this.n5(mapping, form, request, response);
		}else if(bookpagebean.getTipsign()==8||bookpagebean.getTipsign()==3){
			return this.n9(mapping, form, request, response);
		}else if(bookpagebean.getTipsign()==9&&bookpagebean.getResultsign()==1){
			return this.n2(mapping, form, request, response);
		}else if(bookpagebean.getTipsign()==9&&bookpagebean.getResultsign()==2){
			return this.n8(mapping, form, request, response);
		}else if(bookpagebean.getTipsign()==9&&bookpagebean.getResultsign()==3){
			return this.n12(mapping, form, request, response);
		}else if(bookpagebean.getTipsign()==9&&bookpagebean.getResultsign()==4){
			return this.n13(mapping, form, request, response);
		}else if(bookpagebean.getTipsign()==10){
			return this.n4(mapping, form, request, response);
		}
		return n1(mapping, form, request, response);
	}
}


