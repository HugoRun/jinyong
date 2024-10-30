package com.pm.service.job;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.lw.vo.sinasys.SinaDAO;
import com.lw.vo.sinasys.SinaSysVO;
import com.lw.vo.sinasys.SinaUserVO;

public class SinaJob implements Job
{
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		FileWriter fw;
		FileWriter fw_2;
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String date_str = simpleDateFormat.format(date.getTime());
		try
		{
			fw = new FileWriter("/usr/home/hz_liuwei/sinalog/" + date_str
					+ "isdn.log");
			HashMap<String, SinaSysVO> sinapvmap_bak = SinaSysVO.sinapvmap;
			Iterator<?> it = sinapvmap_bak.keySet().iterator();
			PrintWriter out = new PrintWriter(fw);
			StringBuffer sb = new StringBuffer();
			while (it.hasNext())
			{
				String key = (String) it.next();
				SinaSysVO vo = sinapvmap_bak.get(key);
				sb.append(vo.getData() + "|" + vo.getMv() + "|"
						+ vo.getIplogin() + "|" + vo.getPv1() + "|"
						+ vo.getPv2() + "|" + vo.getLoginpv() + "\r\n");

			}
			out.write(sb.toString());
			out.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		SinaSysVO.getNew();
		
		/** *******另外的统计*************** */
		SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date_2 = new Date();
		String date_str_2 = simpleDateFormat_2.format(date_2.getTime());
		SinaDAO dao = new SinaDAO();
		try
		{
			fw_2 = new FileWriter("/usr/home/hz_liuwei/sinalog/" + date_str
					+ "user.log");
			PrintWriter out_2 = new PrintWriter(fw_2);
			List<SinaUserVO> list = dao.getSinaList(date_str_2);
			StringBuffer sb_2 = new StringBuffer();
			if (list != null && list.size() != 0)
			{
				SinaUserVO vo = null;
				for (int i = 0; i < list.size(); i++)
				{
					vo = list.get(i);
					sb_2.append(vo.getDate() + "|" + vo.getP_pk() + "|"
							+ vo.getGrade() + "|" + vo.getWm() + "\r\n");
				}
				out_2.write(sb_2.toString());
				out_2.flush();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
