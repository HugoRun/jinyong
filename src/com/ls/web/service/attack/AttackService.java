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
	 * playerA����playerB
	 * @param tong 0 ����ͨPK 1�ǰ�ս 2����Ӫ 5�ǹ���ս
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
		
		/**********************��ʼ�����ս������***************/
		
		
		/*****************************�غϿ�ʼ*********************************/
		if( unAttack==null )
		{
			fightService.attackPlayer(playerA, playerB);
		}
		
		//playerB�Զ�����playerA����������
		if( !playerB.isDead() )
		{
			fightService.autoCounterattack( playerB,playerA);
		} 
		/*****************************�غϽ���*********************************/
		
	}
	
	
	/**
	 * һ���غϵĹ���
	 */
	public void attackNPC(Fighter player,List npcs)
	{
		//�ж�����vo�Ƿ���Ч
		if( player==null || player.getPPk()==0 )
		{
			return ;
		}
		FightService fightService = new FightService();
		playerService = new PlayerService();
		npcService = new NpcService();
		petService = new PetService();
		
		
		/*****************************�غϿ�ʼ*********************************/
		
		//����npc
		fightService.attackNPC(player, npcs);
		//���﹥��
		petService.petAttackNpcs(player, npcs);
		//npc������ɫ
		npcService.attackPlayer(npcs, player);
		
		/*****************************�غϽ���*********************************/

	}
	
	/**
	 * ͨ�����֮������ɹ�ϵ����ǿ��ҵĹ�����,���A�������B
	 * �ڲ�ͬ����pkʱ���������Ԫ��
	 * ���̿����֣�����������pkʱ�����̵Ĺ��������10%
	 * ���ֿ�ؤ�������ؤ��pkʱ�����ֵĹ��������10%
	 * ؤ������̣�ؤ��������pkʱ��ؤ��Ĺ��������10%
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
	 * �������˺�
	 * @param player
	 * @param npc
	 * @return
	 */
	public static int getPhysicsInjureValue(PartInfoVO player,NpcAttackVO npc)
	{
		int injure = 0;
		int character_gi = player.getGj();
		logger.info("************��������:"+character_gi);
		int npc_defence = npc.getNpcDefance();
		injure = AttackService.normalInjureExpressions(character_gi,npc_defence,player.getPGrade(),npc.getLevel() );
		//logger.info("NPC�ܵ���ҹ������������˺�:"+injure);
		if( injure<=0 )
			injure = 0;
		return injure;
	}
	
	/*
	/**
	 * װ�������˺�
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
		logger.info("NPC�ܵ���ҹ������������˺�:"+injure);
		if( injure<=0 )
			injure = 0;
		return injure;
	}
	*/
	/**
	 * �����й����˺�
	 * @param singleWXAttack
	 * @param wxDefence
	 * @param attack_level
	 * @param defence_level
	 * @return	���ÿ100�������������һ�����з�����
	 */
	public static  int getWxInjureValue(NpcskillVO npcSkill,WXDefence wxDefence,int attack_level,int defence_level,int wx_fy_join)
	{
		int wxInjureValue = 0;
		//���npc�������˺�����0
		if ((npcSkill == null) || (wxDefence == null))
	    {
			logger.debug("��������");
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
	 * ���ﵥ���й����˺�
	 * @param singleWXAttack
	 * @param wxDefence
	 * @param attack_level
	 * @param defence_level
	 * @param pet_npc_id ��������npcid
	 * @return
	 */
	public static  int getPetWxInjureValue(SingleWXAttack singleWXAttack,WXDefence wxDefence,int attack_level,int defence_level,int pet_npc_Id)
	{
		int wxInjureValue = 0;
		//���npc�������˺�����0
		if( singleWXAttack==null || wxDefence==null )
		{
			logger.debug("��������");
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
	 * wxAttack����wxDefence�������˺�
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
		//�������Լ��ܵļ���
		SkillService skillService = new SkillService();
		PassSkillInterface passSkillInterface = skillService.getPassSkillPropertyInfo(p_pk);
		if( wxAttack==null || wxDefence==null || attack_level<0 || defence_level<0 )
		{
			logger.info("getWxInjureValue:�������Ϊ�գ�");
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
		
		//����������ԭ������ˮ��ˮ��ľ��ľ���𣬻�������������
		//����������˵�ԭ�����ľ��ľ����������ˮ��ˮ�˻𣬻�˽�
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
		logger.debug("���й����˺�:"+wxInjureValue);
		
		return wxInjureValue;
	}

	

	
	/**
	 * �����˺�����
	 * �����й�������Ӧ�����з��������й�������˵����з����������������й�������˵����з�����
	 * ������ֹ�ʽΪ��	(���й�������Ӧ�����з���)��10��{1+(���й�������˵����з���)%+[(�������й�����������з���)��2]%}=�����˺�ֵ
	 * �Խ𹥻�Ϊ����(�𹥣����)��10��{1+(�𹥣�ľ��)%+[(������ľ��)��2]%}=�����˺�ֵ
	 * @param wx_att			���й���
	 * @param xiangsheng_att	��������
	 * @param wx_def			���з���
	 * @param xiangke_def		�������
	 * @return
	 */
	public static int wxInjureExpressions(int wx_att,int xiangsheng_att,int wx_def,int xiangke_def)
	{
		if( wx_att<xiangsheng_att)
		{
			return 0;
		}
		
		int xiangke_rate = 0;//��˱���
		int xiangsheng_rate = 0;//��������
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
	 * �������˺�����
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
	 * �������˺�����
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
	 * NPC�����˺�����
	 * (11.26�޸� ��ʽ��Ϊֻ���㹥���ͷ�����)
	 * ��12.02�޸�,��ʽ��Ϊÿ���ֵ���30��Ѫ.��
	 * @param gj
	 * @param fy
	 * @param a_level   �������ĵȼ�
	 * @param b_level	���������ĵȼ�
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
	 * NPC�����˺�����
	 * (11.26�޸� ��ʽ��Ϊֻ���㹥���ͷ�����)
	 * ��12.02�޸�,��ʽ��Ϊÿ���ֵ���30��Ѫ.��
	 * @param gj
	 * @param fy
	 * @param a_level   �������ĵȼ�
	 * @param b_level	���������ĵȼ�
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
	 * �����˺�����
	 * (11.26�޸� ��ʽ��Ϊֻ���㹥���ͷ�����)
	 * ��12.02�޸�,��ʽ��Ϊÿ���ֵ���50��Ѫ.��
	 * ��12.04�޸�
	 	�����˺� =  (�������й���-�Է����з�����*�ȼ�ϵ��-���Է��ȼ�-����ȼ���*����ȼ���
		ע:�ȼ�ϵ�� = ����ȼ�/���ﲶ׽�ȼ������ݿ�ȡ���ĵȼ������ȼ�ϵ����=1��
		(12.09�޸� ȥ�� *����ȼ� ��һ����.��
		��12.13 �޸�
		����Ԫ���˺������ڵ���0��:((����Ԫ��ϵ��-npc��ӦԪ�ط���)*����ȼ�/3+(����ȼ�-npc�ȼ�)*����ȼ�/15)
	 * @param gj
	 * @param fy
	 * @param a_level   �������ĵȼ�
	 * @param b_level	���������ĵȼ�
	 * @param pet_npc_level �˳��ﱻ��׽֮ǰ�ĵȼ�
	 * @return
	 */
	public static int wxPetInjureExpressions(int gj,int fy,int pet_level,int b_level, int pet_npc_level)
	{		
		if(fy < 0)
			fy = 0;
		float injureValue = 0f;
		injureValue = (gj - fy)*pet_level/3 + (pet_level- b_level)*pet_level/15;
		logger.info("����������˺�Ϊ="+injureValue);
		
		
		return (int)injureValue;
		/*//injureValue = (int)(( gj-fy ) - ( b_level-a_level )*a_level );
		//injureValue = (int)( gj-fy )*50;
		//�ȼ�ϵ��
		float gradeFinal = pet_level/pet_npc_level;
		if(gradeFinal > 1){
			gradeFinal = 1;
		}
		
		//injureValue = (gj - fy)*gradeFinal - ( b_level - pet_level )*pet_level;
		injureValue = (gj - fy)*gradeFinal - ( b_level - pet_level );
		injureValue = injureValue * 50;
		if(injureValue < 0) 
			injureValue = 0;
		
		logger.info("�ȼ�ϵ��="+gradeFinal+" ,���������˺�ֵΪ="+injureValue);*/
	}
	
	
	/**
	 * ���＼���˺����㹫ʽ
	 * �����˺� = ��(��������ֵ*����ȼ���/8+������ֵ+�ɳ���*����ȼ�)-�Է���������-���Է��ȼ�-����ȼ���*����ȼ���
	 */
	/*public static int petSkillInjureExpressions(double pet_skill_gj,int pet_level,double pet_grow,int npc_defence,int npc_level)
	{
		int injureValue = 0;
		//skillGj, pet.getPetGrade(), pet.getPetGrow(), npc.getNpcDefance(), npc.getLevel()
		injureValue = (int)(( ((pet_skill_gj*pet_level)/8 + pet_skill_gj+  pet_grow*pet_level) - npc_defence) - (npc_level-pet_level)*pet_level);
		
		logger.info("���＼�ܹ����˺�:"+injureValue);
		if( injureValue<=0 )
			injureValue=0;
		return injureValue;
	}
*/
	/**
	 * ���＼���˺����㹫ʽ
	 * �����˺� = ��(��������ֵ*����ȼ���/8+������ֵ+�ɳ���*����ȼ�)-�Է���������-���Է��ȼ�-����ȼ���*����ȼ���
	 * 4��8�� �޸� �����˺� = ��(��������ֵ*����ȼ���/8+������ֵ+�ɳ���*����ȼ�)-�Է���������
	 * �޸� �����˺� = �����＼�ܹ����� + ������ͨ������ �� - �Է����� 
	 */
	public static int petSkillInjureExpressions(double pet_skill_gj,int pet_level,double pet_grow,int npc_defence,int npc_level,int pet_physics_gj)
	{
		int injureValue = 0;
		//skillGj, pet.getPetGrade(), pet.getPetGrow(), npc.getNpcDefance(), npc.getLevel()
		//injureValue = (int)(( ((pet_skill_gj*pet_level)/8 + pet_skill_gj+  pet_grow*pet_level) - npc_defence) - (npc_level-pet_level)*pet_level);
		//injureValue = (int)(( ((pet_skill_gj*pet_level)/8 + pet_skill_gj+  pet_grow*pet_level) - npc_defence));
		injureValue = (int)(pet_skill_gj+pet_physics_gj-npc_defence);
		logger.info("���＼�ܹ����˺�:"+injureValue);
		if( injureValue<=0 )
			injureValue=0;
		return injureValue;
	}
	
	
	/**
	 * ��ҹ���npc�������˺�
	 * ��ͨ���˺�ֵ���㹫ʽ
	 * @param user_attack
	 * @param npc_defence
	 * @param user_level
	 * @param npc_level
	 * @return ������-������-��NPC�ȼ�-����ȼ���*����ȼ�*0.5
	 */
	public  static int normalInjureExpressions(int user_attack,int npc_defence,int user_level,int npc_level)
	{
		if(user_attack < 0)
			user_attack = 0;
		if(npc_defence < 0)
			npc_defence = 0;
		
		int injureValue = 0;
		//3��27���޸� ȥ���ȼ�����
		injureValue = (int)(( user_attack-npc_defence )*(1-(npc_level-user_level)/10));
		if(injureValue<=0)
		{
			injureValue = 1;
		}
		return injureValue;
	}
	
	
	/**
	 * npc������ҵ��˺�
	 * npc�����˺�ֵ���㹫ʽ
	 * NPC����-����ҷ���*0.6+װ������*0.4��-����ҵȼ�-NPC�ȼ���*NPC�ȼ�*0.5��
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
	 * ����PK������Ľ��
	 * @author Thomas.lei
	 * @param PlayerA �����������
	 * @param PlayerB �����������
	 */
	public static void processPKOver(Fighter playerA,Fighter playerB,HttpServletRequest request)
	{
		if(playerA.isDead())
		{
			playerA.appendKillDisplay("����"+playerB.getPName()+ "ɱ����!");
			
			request.setAttribute("player", playerA);
			request.setAttribute("dropExp", "" + playerA.getDropExp());
		}
		else
		{
			//�õ�������ҵ���ľ���ֵ
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
			
			//��װrequest
			request.setAttribute("dropExp", "" + dropExp);
			request.setAttribute("playerA", playerA);
			request.setAttribute("playerB", playerB);
		}
	}
	/**
	 * ������NPC�Ľ��
	 * @author Thomas.lei
	 * @param Player ���
	 * @param npcs NPC�б�
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
			//�����������Զ���ֵ�
			roleEntity.getSettingInfo().getAutoAttackSetting().init();
			//ͳ����Ҫ
			new RankService().updateAdd(roleEntity.getBasicInfo().getPPk(), "dead", 1);
			request.setAttribute("player", player);	
		}
		else
		{
			FightList fightList = new FightList();
			fightService.playerWIN(player, fightList); 
			//��������м��
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
	 * �õ��˴�PK������ 0Ϊ��ͨPK 1Ϊǿ��PK 3+Ϊ�PK���ս ��̨��
	 * @author Thomas.lei
	 * @param RoleEntityA
	 * @param RoleEntityB
	 * @return String �˴ε�pK����
	 */
	public static String getPKType(RoleEntity roleA,RoleEntity roleB)
	{
		//�õ�������ҵ�������Ϣ
		int a_race=roleA.getBasicInfo().getPRace();
		int b_race=roleB.getBasicInfo().getPRace();
		//�õ�������ҵ�PK������Ϣ
		int pkSwichA=roleA.getBasicInfo().getPkSwitch();
		int pkSwichB=roleB.getBasicInfo().getPkSwitch();
		//��Ҫ���صģУ�����
		String pkType=GameArgs.PK_TYPE_PUTONG;//Ĭ��Ϊ��ͨPK
		//��ͬ�������PK���ض�Ϊ����Ϊ��ͨPK
		if(a_race!=b_race&&pkSwichA==Integer.parseInt(GameArgs.PK_SWICH_OPEN)&&pkSwichB==Integer.parseInt(GameArgs.PK_SWICH_OPEN))
		{
			pkType=GameArgs.PK_TYPE_PUTONG;
		}
		//ͬ����PKΪǿ��PK  ��ͬ����pk������һ���ǹصľ�Ϊǿ��PK
		if((a_race!=b_race&&pkSwichA==Integer.parseInt(GameArgs.PK_SWICH_OPEN)&&pkSwichB==Integer.parseInt(GameArgs.PK_SWICH_OPEN))||(a_race!=b_race&&(pkSwichA==Integer.parseInt(GameArgs.PK_SWICH_CLOSE)||pkSwichB==Integer.parseInt(GameArgs.PK_SWICH_CLOSE))))
		{
			pkType=GameArgs.PK_TYPE_QIANGZHI;
		}
		//�PK
		if(false)
		{
			/***�л��ʱ������ж�����������pk����**/
		}
		return pkType;
	}
	/**
	 * pk������������ҵĳ����Ϣ
	 * playerA ʧ����
	 * playerB ʤ����
	 */
	public static void processPkHite(Fighter loser,Fighter winner)
	{
		PKHiteService pkhiteservice=new PKHiteService();
		RoleEntity winner_info=RoleService.getRoleInfoById(winner.getPPk());
		RoleEntity die_info=RoleService.getRoleInfoById(loser.getPPk());
		/*****���������׶�pk���Ƴ�޶�****/
		if(winner_info.getIsRookie()==true||die_info.getIsRookie()==true)
		{
			return;
		}
		PKHiteVO pv=pkhiteservice.checkHiteInfo(loser.getPPk(),winner.getPPk());
		int hitePoint=0;
		int generalPkCount=0;
		int activePkCount=0;
		
		String tong = GameArgs.PK_TYPE_PUTONG;//��ʱʹ��
		
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
		if(pv!=null)//�м�¼����³�޵�
		{
			pv.setEnemyGrade(winner_info.getBasicInfo().getGrade());
			pv.setHitePoint(pv.getHitePoint()+hitePoint);
			pv.setGeneralPkCount(pv.getGeneralPkCount()+generalPkCount);
			pv.setActivePkCount(pv.getActivePkCount()+activePkCount);
			pkhiteservice.updateHitePoint(pv);
		}
		else//û�м�¼�������µĳ�޶���
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
