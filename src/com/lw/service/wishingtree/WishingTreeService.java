package com.lw.service.wishingtree;

import java.util.List;

import com.ls.pub.bean.QueryPage;
import com.lw.dao.wishingtree.WishingTreeDAO;
import com.lw.vo.wishingtree.WishingTreeVO;

public class WishingTreeService
{
	// �õ�ף��
	public QueryPage getAllWishing(int page_no)
	{
		WishingTreeDAO dao = new WishingTreeDAO();
		return dao.getAllWishing(page_no);
	}

	// �õ�ף��
	public List<WishingTreeVO> getTopWishing()
	{
		WishingTreeDAO dao = new WishingTreeDAO();
		return dao.getTopWishing();
	}

	// ����ף��
	public void insertWishing(int p_pk, String p_name, String wishing)
	{
		WishingTreeDAO dao = new WishingTreeDAO();
		dao.insertWishing(p_pk, p_name, wishing);
	}

	// ����ף��Ϊ�ö�
	public void setTopWishing(int id)
	{
		WishingTreeDAO dao = new WishingTreeDAO();
		dao.setTopWishing(id);
		setNomarlWishing();
	}

	// ����ף��Ϊ��ͨ״̬
	private void setNomarlWishing()
	{
		if (getTopWishingNum() > 3)
		{
			WishingTreeDAO dao = new WishingTreeDAO();
			dao.setNomarWishing();
		}
	}

	private int getTopWishingNum()
	{
		WishingTreeDAO dao = new WishingTreeDAO();
		return dao.getTopWishingNum();
	}

	public void deleteWishing(int id)
	{
		WishingTreeDAO dao = new WishingTreeDAO();
		dao.deleteWishing(id);
	}
}
