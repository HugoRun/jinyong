package com.ls.model.event;

import com.ben.vo.honour.RoleTitleVO;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.model.user.RoleEntity;

/**
 * @author handan
 * ³ÆºÅ³¬Ê±ÊÂ¼þ
 */
public class TitleEvent extends TimerEvent
{
	private int pPk;
	private int tId;
	
	public TitleEvent( RoleTitleVO roleTitle )
	{
		super(roleTitle.getEndTime());
		this.pPk = roleTitle.getPPk();
		this.tId = roleTitle.getTId();
	}

	@Override
	public void handle()
	{
		RoleEntity role_info = RoleCache.getByPpk(pPk);
		if( role_info!=null )
		{
			role_info.getTitleSet().delTitle(tId);
		}
	}

	@Override
	public boolean equals(Object object)
	{
		if(object instanceof TitleEvent)
		{
			TitleEvent other = (TitleEvent) object;
			if( this.pPk==other.pPk && this.tId==other.tId ) 
			{
				return true;
			}
		}
		return false;
	}
}
