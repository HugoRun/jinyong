package com.ls.web.service.attack;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.dao.info.npc.NpcDao;
import com.ls.ben.vo.info.attack.FightList;
import com.ls.ben.vo.info.attribute.attack.SingleWXAttack;
import com.ls.ben.vo.info.attribute.attack.WXAttack;
import com.ls.ben.vo.info.attribute.attack.WXDefence;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.npc.NpcskillVO;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.pkhite.PKHiteVO;
import com.ls.model.property.PassSkillInterface;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.WuXing;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.pet.PetService;
import com.ls.web.service.pk.PKHiteService;
import com.ls.web.service.player.FightService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.rank.RankService;
import com.ls.web.service.skill.SkillService;
import com.pub.GameArgs;
import com.web.service.task.TaskPageService;


public class AttackService {
	

	public static Logger logger =  Logger.getLogger("log.service");

	PlayerService playerService = null;
	PetService petService = null;
	NpcService npcService = null;

	/**
	 * playerA攻击playerB
	 * @param tong 0 是普通PK 1是帮战 2是阵营 5是攻城战
	 * 
	 */
	public void attackPlayer (Fighter playerA,Fighter playerB,String unAttack)
	{
		if( playerA==null || playerB==null )
		{
			return ;
		}
		FightService fightService = new FightService();
		playerService = new PlayerService();
		
		/**********************初始化玩家战斗属性***************/
		
		
		/*****************************回合开始*********************************/
		if( unAttack==null )
		{
			fightService.attackPlayer(playerA, playerB);
		}
		
		//playerB自动反击playerA（物理攻击）
		if( !playerB.isDead() )
		{
			fightService.autoCounterattack( playerB,playerA);
		} 
		/*****************************回合结束*********************************/
		
	}
	
	
	/**
	 * 一个回合的攻击
	 */
	public void attackNPC(Fighter player,List npcs)
	{
		//判断两个vo是否有效
		if( player==null || player.getPPk()==0 )
		{
			return ;
		}
		FightService fightService = new FightService();
		playerService = new PlayerService();
		npcService = new NpcService();
		petService = new PetService();
		
		
		/*****************************回合开始*********************************/
		
		//攻击npc
		fightService.attackNPC(player, npcs);
		//宠物攻击
		petService.petAttackNpcs(player, npcs);
		//npc攻击角色
		npcService.attackPlayer(npcs, player);
		
		/*****************************回合结束*********************************/

	}
	
	/**
	 * 通过玩家之间的门派关系，增强玩家的攻击力,玩家A攻击玩家B
	 * 在不同门派pk时，加入相克元素
	 * 明教克少林：明教与少林pk时，明教的攻击力提高10%
	 * 少林克丐帮：少林与丐帮pk时，少林的攻击力提高10%
	 * 丐帮克明教：丐帮与明教pk时，丐帮的攻击力提高10%
	 */
	private void enhanceGJByCampRelation(Fighter playerA,Fighter playerB)
	{
		if( playerA==null || playerB==null )
		{
			return;
		}
		
/*		String a_school_id = playerA.getPSchool();
		String b_school_id = playerB.getPSchool();
		
		
		
		if( a_school_id==null || a_school_id.equals("") || b_school_id==null || b_school_id.equals("") )
		{
			return;
		}
		
		if( a_school_id.equals("mingjiao") && b_school_id.equals("shaolin"))
		{
			playerA.setGjSubjoin(playerA.getGjSubjoin()+10);
		}
		else if( a_school_id.equals("shaolin") && b_school_id.equals("gaibang"))
		{
			playerA.setGjSubjoin(playerA.getGjSubjoin()+10);
		}
		else if( a_school_id.equals("gaibang") && b_school_id.equals("mingjiao"))
		{
			playerA.setGjSubjoin(playerA.getGjSubjoin()+10);
		}
*/	}

	/**
	 * 物理攻击伤害
	 * @param player
	 * @param npc
	 * @return
	 */
	public static int getPhysicsInjureValue(PartInfoVO player,NpcAttackVO npc)
	{
		int injure = 0;
		int character_gi = player.getGj();
		logger.info("************物理攻击力:"+character_gi);
		int npc_defence = npc.getNpcDefance();
		injure = AttackService.normalInjureExpressions(character_gi,npc_defence,player.getPGrade(),npc.getLevel() );
		//logger.info("NPC受到玩家攻击的物理攻击伤害:"+injure);
		if( injure<=0 )
			injure = 0;
		return injure;
	}
	
	/*
	/**
	 * 装备攻击伤害
	 * @param player
	 * @param npc
	 * @return
	 *//*
	public static int getZbInjureValue(PartInfoVO player,NpcAttackVO npc)
	{
		int injure = 0;
		int character_gi = player.getZbGj();
		int npc_defence = npc.getNpcDefance();
		injure = AttackService.normalInjureExpressions(character_gi,npc_defence,player.getPGrade(),npc.getLevel() );
		logger.info("NPC受到玩家攻击的物理攻击伤害:"+injure);
		if( injure<=0 )
			injure = 0;
		return injure;
	}
	*/
	/**
	 * 单五行攻击伤害
	 * @param singleWXAttack
	 * @param wxDefence
	 * @param attack_level
	 * @param defence_level
	 * @return	玩家每100点物理防御力算一点五行防御力
	 */
	public static  int getWxInjureValue(NpcskillVO npcSkill,WXDefence wxDefence,int attack_level,int defence_level,int wx_fy_join)
	{
		int wxInjureValue = 0;
		//如果npc无五行伤害返回0
		if ((npcSkill == null) || (wxDefence == null))
	    {
			logger.debug("参数错误");
			return wxInjureValue;
		}
		
		int wx = npcSkill.getNpcskiWx();
	    if (wx > 0)
	    {
	      if (wx == 1)
	      {
				wxInjureValue =  wxInjureExpressionsNPC(npcSkill.getNpcskiWxInjure(),wxDefence.getJinFy(),attack_level,defence_level,wx_fy_join);
			}
			else  if (wx == 2)
			{
				wxInjureValue =  wxInjureExpressionsNPC(npcSkill.getNpcskiWxInjure(),wxDefence.getMuFy(),attack_level,defence_level,wx_fy_join);
			}
			else  if (wx == 3)
			{
				wxInjureValue =  wxInjureExpressionsNPC(npcSkill.getNpcskiWxInjure(),wxDefence.getShuiFy(),attack_level,defence_level,wx_fy_join);
			}
			else  if (wx == 4)
			{
				wxInjureValue =  wxInjureExpressionsNPC(npcSkill.getNpcskiWxInjure(),wxDefence.getHuoFy(),attack_level,defence_level,wx_fy_join);
			}
			else  if (wx == 5)
			{
				wxInjureValue =  wxInjureExpressionsNPC(npcSkill.getNpcskiWxInjure(),wxDefence.getTuFy(),attack_level,defence_level,wx_fy_join);
			}
		}	
		if( wxInjureValue<0 )
		{
			wxInjureValue = 0;
		}
		return wxInjureValue;
	}
	/**
	 * 宠物单五行攻击伤害
	 * @param singleWXAttack
	 * @param wxDefence
	 * @param attack_level
	 * @param defence_level
	 * @param pet_npc_id 这个宠物的npcid
	 * @return
	 */
	public static  int getPetWxInjureValue(SingleWXAttack singleWXAttack,WXDefence wxDefence,int attack_level,int defence_level,int pet_npc_Id)
	{
		int wxInjureValue = 0;
		//如果npc无五行伤害返回0
		if( singleWXAttack==null || wxDefence==null )
		{
			logger.debug("参数错误");
			return wxInjureValue;
		}
		
		NpcDao infodao = new NpcDao();
//		int pet_npc_level = infodao.getNpcGradeById(pet_npc_Id);
		NpcCache npcCache = new NpcCache();
		int pet_npc_level = npcCache.getNpcGradeById(pet_npc_Id);
		
		if( singleWXAttack.getWxValue()>0 )
		{
			int wx = singleWXAttack.getWx();
			if( wx==WuXing.JIN )
			{
				wxInjureValue =  wxPetInjureExpressions(singleWXAttack.getWxValue(),wxDefence.getJinFy(),attack_level,defence_level,pet_npc_level);
			}
			else if( wx==WuXing.MU )
			{
				wxInjureValue =  wxPetInjureExpressions(singleWXAttack.getWxValue(),wxDefence.getMuFy(),attack_level,defence_level,pet_npc_level);
			}
			else if( wx==WuXing.SHUI )
			{
				wxInjureValue =  wxPetInjureExpressions(singleWXAttack.getWxValue(),wxDefence.getShuiFy(),attack_level,defence_level,pet_npc_level);
			}
			else if( wx==WuXing.HUO )
			{
				wxInjureValue =  wxPetInjureExpressions(singleWXAttack.getWxValue(),wxDefence.getHuoFy(),attack_level,defence_level,pet_npc_level);
			}
			else if( wx==WuXing.TU )
			{
				wxInjureValue =  wxPetInjureExpressions(singleWXAttack.getWxValue(),wxDefence.getTuFy(),attack_level,defence_level,pet_npc_level);
			}
		}	
		if( wxInjureValue<0 )
		{
			wxInjureValue = 0;
		}
		return wxInjureValue;
	}
	
	/**
	 * wxAttack攻击wxDefence的五行伤害
	 * @param playerA
	 * @param npc
	 * @return
	 */
	public static int getWxInjureValue(WXAttack wxAttack,WXDefence wxDefence,int attack_level,int defence_level,int p_pk)
	{ 
		int wxInjureValue = 0;
		
		int jinInjureValue = 0;
		int muInjureValue = 0;
		int shuiInjureValue = 0;
		int huoInjureValue = 0;
		int tuInjureValue = 0;
		//五行属性技能的加载
		SkillService skillService = new SkillService();
		PassSkillInterface passSkillInterface = skillService.getPassSkillPropertyInfo(p_pk);
		if( wxAttack==null || wxDefence==null || attack_level<0 || defence_level<0 )
		{
			logger.info("getWxInjureValue:传入参数为空！");
			return wxInjureValue;
		}
		
		int jin_gj = wxAttack.getJinGj();
		int mu_gj = wxAttack.getMuGj();
		int shui_gj = wxAttack.getShuiGj();
		int huo_gj = wxAttack.getHuoGj();
		int tu_gj = wxAttack.getTuGj();
		
		int jin_fy = wxDefence.getJinFy();
		int mu_fy = wxDefence.getMuFy();
		int shui_fy = wxDefence.getShuiFy();
		int huo_fy = wxDefence.getHuoFy();
		int tu_fy = wxDefence.getTuFy();
		
		//五行相生的原理，金生水，水生木，木生火，火生土，土生金
		//按照五行相克的原理，金克木，木克土，土克水，水克火，火克金
		if( jin_gj>jin_fy )
			jinInjureValue = wxInjureExpressions(jin_gj,shui_fy,jin_fy,mu_fy);
			jinInjureValue = jinInjureValue + (int)(jinInjureValue*passSkillInterface.getSkJMultiple());
		if( mu_gj>mu_fy )
			muInjureValue = wxInjureExpressions(mu_gj,huo_gj,mu_fy,tu_fy);
			muInjureValue = muInjureValue + (int)(muInjureValue*passSkillInterface.getSkMMultiple());
		if( shui_gj>shui_fy )
			shuiInjureValue = wxInjureExpressions(shui_gj,mu_gj,shui_fy,huo_fy);
			shuiInjureValue = shuiInjureValue + (int)(shuiInjureValue*passSkillInterface.getSkSMultiple());
		if( huo_gj>huo_fy )
			huoInjureValue = wxInjureExpressions(huo_gj,tu_gj,huo_fy,jin_fy);
			huoInjureValue = huoInjureValue + (int)(huoInjureValue*passSkillInterface.getSkHMultiple());
		if( tu_gj>tu_fy )
			tuInjureValue = wxInjureExpressions(tu_gj,jin_gj,tu_fy,shui_fy);
			tuInjureValue = tuInjureValue + (int)(tuInjureValue*passSkillInterface.getSkTMultiple());
		
		
		
		wxInjureValue = jinInjureValue + muInjureValue + shuiInjureValue + huoInjureValue + tuInjureValue;
		logger.debug("五行攻击伤害:"+wxInjureValue);
		
		return wxInjureValue;
	}

	

	
	/**
	 * 五行伤害计算
	 * 若五行攻击＞相应的五行防御，五行攻击＞相克的五行防御，且相生的五行攻击＞相克的五行防御，
	 * 具体表现公式为：	(五行攻击－相应的五行防御)×10×{1+(五行攻击－相克的五行防御)%+[(相生五行攻击－相克五行防御)÷2]%}=属性伤害值
	 * 以金攻击为例：(金攻－金防)×10×{1+(金攻－木防)%+[(土攻－木防)÷2]%}=属性伤害值
	 * @param wx_att			五行攻击
	 * @param xiangsheng_att	相生五行
	 * @param wx_def			五行防御
	 * @param xiangke_def		相克五行
	 * @return
	 */
	public static int wxInjureExpressions(int wx_att,int xiangsheng_att,int wx_def,int xiangke_def)
	{
		if( wx_att<xiangsheng_att)
		{
			return 0;
		}
		
		int xiangke_rate = 0;//相克比率
		int xiangsheng_rate = 0;//相生比率
		if( wx_att>xiangke_def)
		{
			xiangke_rate = wx_att - xiangke_def;
		}
		if( xiangsheng_att>xiangke_def)
		{
			xiangsheng_rate = (xiangsheng_att-xiangke_def);
		}
		
		double wx_injure_total = 1.0*(wx_att-wx_def)*10*(100+xiangke_rate+xiangsheng_rate/2)/100;
		return Math.round((float)wx_injure_total);
	}
	/**
	 * 新五行伤害计算
	 */
	public static int wxInjureExpressions(int gj,int fy,int kfy)
	{
		if(gj<0)
			gj=0;
		if(fy<0)
			fy=0;
		if(kfy<0)
			kfy=0;
		int injureValue=(gj-fy)*10*(1+(gj-kfy)/100);
		if(injureValue<0)
		{
			injureValue=0;
		}
		return injureValue;
	}
	/**
	 * 新五行伤害计算
	 */
	public static int wxInjureExpression(int gj,int kefy,int shenggj)
	{
		if(gj<0)
			gj=0;
		if(kefy<0)
			kefy=0;
		if(kefy<0)
			kefy=0;
		int injureValue=(gj-kefy)*10*(1+(gj-kefy)/100+((shenggj-kefy)/2)/100);
		if(injureValue<0)
		{
			injureValue=0;
		}
		return injureValue;
	}
	/**
	 * NPC五行伤害计算
	 * (11.26修改 公式改为只计算攻击和防御差)
	 * （12.02修改,公式改为每点差值会掉30点血.）
	 * @param gj
	 * @param fy
	 * @param a_level   攻击方的等级
	 * @param b_level	被攻击方的等级
	 * @return
	 */
	public static int wxInjureExpressionsNPC(int gj,int fy,int a_level,int b_level)
	{
		if(gj < 0) 
			gj = 0;
		if(fy < 0)
			fy = 0;
		int injureValue = 0;
		//injureValue = (int)(( gj-fy ) - ( b_level-a_level )*a_level );
		injureValue = ( gj-fy )*30;
		if(injureValue < 0)
			injureValue = 0;
		return injureValue;
	}
	
	
	/**
	 * NPC五行伤害计算
	 * (11.26修改 公式改为只计算攻击和防御差)
	 * （12.02修改,公式改为每点差值会掉30点血.）
	 * @param gj
	 * @param fy
	 * @param a_level   攻击方的等级
	 * @param b_level	被攻击方的等级
	 * @return
	 */
	public static int wxInjureExpressionsNPC(int gj,int fy,int a_level,int b_level,int join_fy)
	{
		if(gj < 0) 
			gj = 0;
		if(fy < 0)
			fy = 0;
		int injureValue = 0;
		//injureValue = (int)(( gj-fy ) - ( b_level-a_level )*a_level );
		injureValue = (int)( gj-fy-join_fy );
		if(injureValue < 0)
			injureValue = 0;
		return injureValue;
	}
	
	/**
	 * 五行伤害计算
	 * (11.26修改 公式改为只计算攻击和防御差)
	 * （12.02修改,公式改为每点差值会掉50点血.）
	 * （12.04修改
	 	五行伤害 =  (宠物五行攻击-对方五行防御）*等级系数-（对方等级-宠物等级）*宠物等级；
		注:等级系数 = 宠物等级/宠物捕捉等级（数据库取出的等级），等级系数《=1）
		(12.09修改 去掉 *宠物等级 这一因素.）
		（12.13 修改
		宠物元素伤害（大于等于0）:((宠物元素系数-npc对应元素防御)*宠物等级/3+(宠物等级-npc等级)*宠物等级/15)
	 * @param gj
	 * @param fy
	 * @param a_level   攻击方的等级
	 * @param b_level	被攻击方的等级
	 * @param pet_npc_level 此宠物被捕捉之前的等级
	 * @return
	 */
	public static int wxPetInjureExpressions(int gj,int fy,int pet_level,int b_level, int pet_npc_level)
	{		
		if(fy < 0)
			fy = 0;
		float injureValue = 0f;
		injureValue = (gj - fy)*pet_level/3 + (pet_level- b_level)*pet_level/15;
		logger.info("宠物的五行伤害为="+injureValue);
		
		
		return (int)injureValue;
		/*//injureValue = (int)(( gj-fy ) - ( b_level-a_level )*a_level );
		//injureValue = (int)( gj-fy )*50;
		//等级系数
		float gradeFinal = pet_level/pet_npc_level;
		if(gradeFinal > 1){
			gradeFinal = 1;
		}
		
		//injureValue = (gj - fy)*gradeFinal - ( b_level - pet_level )*pet_level;
		injureValue = (gj - fy)*gradeFinal - ( b_level - pet_level );
		injureValue = injureValue * 50;
		if(injureValue < 0) 
			injureValue = 0;
		
		logger.info("等级系数="+gradeFinal+" ,宠物五行伤害值为="+injureValue);*/
	}
	
	
	/**
	 * 宠物技能伤害计算公式
	 * 技能伤害 = （(（技能数值*宠物等级）/8+技能数值+成长率*宠物等级)-对方防御力）-（对方等级-宠物等级）*宠物等级；
	 */
	/*public static int petSkillInjureExpressions(double pet_skill_gj,int pet_level,double pet_grow,int npc_defence,int npc_level)
	{
		int injureValue = 0;
		//skillGj, pet.getPetGrade(), pet.getPetGrow(), npc.getNpcDefance(), npc.getLevel()
		injureValue = (int)(( ((pet_skill_gj*pet_level)/8 + pet_skill_gj+  pet_grow*pet_level) - npc_defence) - (npc_level-pet_level)*pet_level);
		
		logger.info("宠物技能攻击伤害:"+injureValue);
		if( injureValue<=0 )
			injureValue=0;
		return injureValue;
	}
*/
	/**
	 * 宠物技能伤害计算公式
	 * 技能伤害 = （(（技能数值*宠物等级）/8+技能数值+成长率*宠物等级)-对方防御力）-（对方等级-宠物等级）*宠物等级；
	 * 4月8日 修改 技能伤害 = （(（技能数值*宠物等级）/8+技能数值+成长率*宠物等级)-对方防御力）
	 * 修改 技能伤害 = （宠物技能攻击力 + 宠物普通攻击力 ） - 对方防御 
	 */
	public static int petSkillInjureExpressions(double pet_skill_gj,int pet_level,double pet_grow,int npc_defence,int npc_level,int pet_physics_gj)
	{
		int injureValue = 0;
		//skillGj, pet.getPetGrade(), pet.getPetGrow(), npc.getNpcDefance(), npc.getLevel()
		//injureValue = (int)(( ((pet_skill_gj*pet_level)/8 + pet_skill_gj+  pet_grow*pet_level) - npc_defence) - (npc_level-pet_level)*pet_level);
		//injureValue = (int)(( ((pet_skill_gj*pet_level)/8 + pet_skill_gj+  pet_grow*pet_level) - npc_defence));
		injureValue = (int)(pet_skill_gj+pet_physics_gj-npc_defence);
		logger.info("宠物技能攻击伤害:"+injureValue);
		if( injureValue<=0 )
			injureValue=0;
		return injureValue;
	}
	
	
	/**
	 * 玩家攻击npc的物理伤害
	 * 普通的伤害值计算公式
	 * @param user_attack
	 * @param npc_defence
	 * @param user_level
	 * @param npc_level
	 * @return （攻击-防御）-（NPC等级-宠物等级）*宠物等级*0.5
	 */
	public  static int normalInjureExpressions(int user_attack,int npc_defence,int user_level,int npc_level)
	{
		if(user_attack < 0)
			user_attack = 0;
		if(npc_defence < 0)
			npc_defence = 0;
		
		int injureValue = 0;
		//3月27日修改 去掉等级限制
		injureValue = (int)(( user_attack-npc_defence )*(1-(npc_level-user_level)/10));
		if(injureValue<=0)
		{
			injureValue = 1;
		}
		return injureValue;
	}
	
	
	/**
	 * npc攻击玩家的伤害
	 * npc技能伤害值计算公式
	 * NPC攻击-（玩家防御*0.6+装备防御*0.4）-（玩家等级-NPC等级）*NPC等级*0.5；
	 */
	public static int npcSkillInjureExpressions(int npc_gj,int user_fy,int user_zbfy,int user_level,int npc_level)
	{
		int injureValue = 0;
		injureValue=(npc_gj-(user_fy+user_zbfy))*(1-(user_level-npc_level)/10);
		if( injureValue<=0 )
			injureValue=1;
		return injureValue;
	}
	
	/**
	 * 处理PK死亡后的结果
	 * @author Thomas.lei
	 * @param PlayerA 主动攻击玩家
	 * @param PlayerB 被动攻击玩家
	 */
	public static void processPKOver(Fighter playerA,Fighter playerB,HttpServletRequest request)
	{
		if(playerA.isDead())
		{
			playerA.appendKillDisplay("您被"+playerB.getPName()+ "杀死了!");
			
			request.setAttribute("player", playerA);
			request.setAttribute("dropExp", "" + playerA.getDropExp());
		}
		else
		{
			//得到死亡玩家掉落的经验值
			long dropExp = 0;
			if (playerB.isNotDropExp())
			{
				RoleEntity b_role_info = RoleService.getRoleInfoById(playerB.getPPk());
				dropExp = b_role_info.getStateInfo().getShouldDropExperience();
			}
			else
			{
				dropExp = playerB.getDropExp();
			}
			
			//封装request
			request.setAttribute("dropExp", "" + dropExp);
			request.setAttribute("playerA", playerA);
			request.setAttribute("playerB", playerB);
		}
	}
	/**
	 * 处理攻击NPC的结果
	 * @author Thomas.lei
	 * @param Player 玩家
	 * @param npcs NPC列表
	 * @param roleEntity
	 * @param request
	 */
	public static void processAttackNPCOver(Fighter player,List npcs,RoleEntity roleEntity,HttpServletRequest request)
	{
		FightService fightService = new FightService();
		if(player.isDead())
		{
			request.getSession().setAttribute("getKillDisplay", "");
			if(roleEntity.getTaskInfo().getTaskId() != -1&& roleEntity.getTaskInfo().getTaskPoint()!= null&& !roleEntity.getTaskInfo().getTaskPoint().equals("")&& roleEntity.getTaskInfo().getTaskMenu()!= -1){
				roleEntity.getTaskInfo().setTaskId(-1);
				roleEntity.getTaskInfo().setTaskMenu(-1);
				roleEntity.getTaskInfo().setTaskPoint(""); 
			}
			//清除玩家设置自动打怪的
			roleEntity.getSettingInfo().getAutoAttackSetting().init();
			//统计需要
			new RankService().updateAdd(roleEntity.getBasicInfo().getPPk(), "dead", 1);
			request.setAttribute("player", player);	
		}
		else
		{
			FightList fightList = new FightList();
			fightService.playerWIN(player, fightList); 
			//清除任务中间点
			TaskPageService taskPageService = new TaskPageService();
			String deadnpcxiayibu = taskPageService.deleteTeskPorint(roleEntity); 
			String displiey = (String)request.getSession().getAttribute("getKillDisplay");
			player.appendKillDisplay(displiey);
			request.setAttribute("player", player);
			request.setAttribute("fightList", fightList);
			request.setAttribute("deadnpcxiayibu", deadnpcxiayibu);
			request.getSession().setAttribute("getKillDisplay", "");
		}
	}
	/**
	 * 得到此次PK的类型 0为普通PK 1为强制PK 3+为活动PK如帮战 擂台等
	 * @author Thomas.lei
	 * @param RoleEntityA
	 * @param RoleEntityB
	 * @return String 此次的pK类型
	 */
	public static String getPKType(RoleEntity roleA,RoleEntity roleB)
	{
		//得到两个玩家的种族信息
		int a_race=roleA.getBasicInfo().getPRace();
		int b_race=roleB.getBasicInfo().getPRace();
		//得到两个玩家的PK开关信息
		int pkSwichA=roleA.getBasicInfo().getPkSwitch();
		int pkSwichB=roleB.getBasicInfo().getPkSwitch();
		//需要返回的ＰＫ类型
		String pkType=GameArgs.PK_TYPE_PUTONG;//默认为普通PK
		//不同种族而且PK开关都为开则为普通PK
		if(a_race!=b_race&&pkSwichA==Integer.parseInt(GameArgs.PK_SWICH_OPEN)&&pkSwichB==Integer.parseInt(GameArgs.PK_SWICH_OPEN))
		{
			pkType=GameArgs.PK_TYPE_PUTONG;
		}
		//同种族PK为强制PK  不同种族pk开关有一个是关的就为强制PK
		if((a_race!=b_race&&pkSwichA==Integer.parseInt(GameArgs.PK_SWICH_OPEN)&&pkSwichB==Integer.parseInt(GameArgs.PK_SWICH_OPEN))||(a_race!=b_race&&(pkSwichA==Integer.parseInt(GameArgs.PK_SWICH_CLOSE)||pkSwichB==Integer.parseInt(GameArgs.PK_SWICH_CLOSE))))
		{
			pkType=GameArgs.PK_TYPE_QIANGZHI;
		}
		//活动PK
		if(false)
		{
			/***有活动的时候添加判断条件并返回pk类型**/
		}
		return pkType;
	}
	/**
	 * pk结束后增加玩家的仇恨信息
	 * playerA 失败者
	 * playerB 胜利者
	 */
	public static void processPkHite(Fighter loser,Fighter winner)
	{
		PKHiteService pkhiteservice=new PKHiteService();
		RoleEntity winner_info=RoleService.getRoleInfoById(winner.getPPk());
		RoleEntity die_info=RoleService.getRoleInfoById(loser.getPPk());
		/*****新手引导阶段pk不计仇恨度****/
		if(winner_info.getIsRookie()==true||die_info.getIsRookie()==true)
		{
			return;
		}
		PKHiteVO pv=pkhiteservice.checkHiteInfo(loser.getPPk(),winner.getPPk());
		int hitePoint=0;
		int generalPkCount=0;
		int activePkCount=0;
		
		String tong = GameArgs.PK_TYPE_PUTONG;//暂时使用
		
		if(tong.equals(GameArgs.PK_TYPE_ACTIVE))
		{
			hitePoint=5;
			activePkCount=1;
		}
		else
		{
			hitePoint=10;
			generalPkCount=1;
		}
		if(pv!=null)//有记录则更新仇恨点
		{
			pv.setEnemyGrade(winner_info.getBasicInfo().getGrade());
			pv.setHitePoint(pv.getHitePoint()+hitePoint);
			pv.setGeneralPkCount(pv.getGeneralPkCount()+generalPkCount);
			pv.setActivePkCount(pv.getActivePkCount()+activePkCount);
			pkhiteservice.updateHitePoint(pv);
		}
		else//没有记录则增加新的仇恨对象
		{
			pv=new PKHiteVO();
			pv.setP_pk(loser.getPPk());
			pv.setEnemyUpk(winner_info.getBasicInfo().getUPk());
			pv.setEnemyPpk(winner.getPPk());
			pv.setEnemyName(winner_info.getBasicInfo().getName());
			pv.setEnemyGrade(winner_info.getBasicInfo().getGrade());
			pv.setHitePoint(hitePoint);
			pv.setGeneralPkCount(generalPkCount);
			pv.setActivePkCount(activePkCount);
			pkhiteservice.addEnemy(pv);
		}
	}
	

	
}
