package com.pub;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ben.dao.TimeShow;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.web.service.petservice.HhjPetService;

public class SessionFilter implements Filter {

	public void destroy() { 
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)  { 
		HttpServletRequest req = (HttpServletRequest) request;

		HttpSession session = req.getSession(); 
		try {
			request.setCharacterEncoding("UTF-8");		   
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		    String pettimeaa = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������ 
		    
			SimpleDateFormat petformatter = new SimpleDateFormat("yyyyMMddHHmmss");// ��ʱ����и�ʽ��
			String pettime = petformatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������ 
						
			RoleService roleService = new RoleService();
			RoleEntity roleEntity = roleService.getRoleInfoBySession(session);
						
		    if (session.getAttribute("uPk")!=null && roleEntity!=null) { // ��ʱֻ���˵�½�� 
		    	//�����������
		    	/*SellInfoDAO dao = new SellInfoDAO();
		    	SellInfoDAO sellInfoDAO = new SellInfoDAO();
		    	SellInfoVO sellMode = (SellInfoVO) dao.getSellMode(roleEntity.getBasicInfo().getPPk());
		    	if(sellMode != null){
		    		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
		    		int timss = DateUtil.getDifferTimes(vo.getCreateTime(),pettimeaa);
		    		if(timss > 5){
		    			dao.getSelleInfoDeMon(vo.getSPk()+"");
		    		}
		    	}*/
				//����Я���� ��ǰʱ���Я�����ʱ����ͬ��ô��ִ�еݼ���������
				HhjPetService petService = new HhjPetService();
				TimeShow timeShow = new TimeShow();
				int pet = petService.pet(roleEntity.getBasicInfo().getPPk());
				
				if(roleEntity.getRolePetInfo().getPetNextTime()!=null){  
					if(Long.parseLong(pettime) > Long.parseLong(roleEntity.getRolePetInfo().getPetNextTime())) {
    					
    					int times = 5;// 5���� 
    					petService.petFatigue(roleEntity);
    					String ss = timeShow.time(times);
    					String dd = ss.replaceAll("-", "");
    					String ff = dd.replaceAll(" ", "");
    					String qq = ff.replaceAll(":", "");
    					roleEntity.getRolePetInfo().setPetNextTime(qq);
    				}
				} else if(pet > 0){
					int times = 5;// 5����
					String ss = timeShow.time(times);
					String dd = ss.replaceAll("-", "");
					String ff = dd.replaceAll(" ", "");
					String qq = ff.replaceAll(":", "");
					roleEntity.getRolePetInfo().setPetNextTime(qq); 					
				}
				chain.doFilter(request, response);
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	} 
	public void init(FilterConfig config) throws ServletException { 
	}

}
