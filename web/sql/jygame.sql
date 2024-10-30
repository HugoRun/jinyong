drop database jygame;
create database jygame;  
use jygame;
/**********�û�(t_user_info)***************/
create table t_user_info ( 
   u_pk             SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT ,	 /*������Ա��ϢID*/ 
   u_name           varchar(200)                              ,  /**����Ա��¼��*/
   u_nname          varchar(200)                              ,  /**����Ա����*/
   u_paw            varchar(200)                              ,  /**����Ա��¼����*/
   a_module         varchar(200)                              ,  /**������ģ�� ���ģ�� �ֶ���,�ֿ�*/
   a_admin          int                              not null ,  /**�Ƿ�߼�����Ա 0 ���� 1 ��*/
   c_content        varchar(800)                              ,  /**��ע*/
   create_time      datetime                                  ,  /**����ʱ��*/
   PRIMARY KEY (u_pk));

/**********С��ʿ(u_intimate_hint)***************/
 create table u_intimate_hint(
    h_pk	               smallint unsigned not null auto_increment , /**��Һ���id*/ 
    h_hint	               varchar(500)                              , /**С��ʿ�ı���*/
    h_content              varchar(500)                              , /**С��ʿ������*/ 
    primary key(h_pk)) ENGINE=MyISAM;
 
/*************ϵͳ��Ϣ���Ʊ�(u_systeminof_control)*********/
create table u_systeminfo_control(
  control_id		 int unsigned not null auto_increment		,	    /**  ϵͳ������Ϣ��id */
  condition_type 	 int										,		/** ������������,1Ϊ��ҵȼ�,2Ϊ����,3Ϊ����,4Ϊ��ν,5Ϊ����ʱ��.  */
  player_grade		 varchar(20)				   default 0	,		/**  ��ҵȼ� */
  task_id			 int 						   default 0	,		/**  ����id */
  popularity		 int 						   default 0	,		/**  ���� */
  title 			 varchar(50) 					   			,		/**   ��ν */
  send_time		     varchar(50) 								,		/**   ����ʱ�� */
  send_content		varchar(1000)								,		/**  ��������	*/
  send_type		int								,					/**  1Ϊ��ϵͳ��Ϣ,2Ϊ���ʼ�,3Ϊϵͳ��Ϣ���ʼ�����, 4Ϊ������, ע�ⷢ����ҿ�����Ϣ���ܷ��ʼ�	*/
  primary key(control_id)) ENGINE=MyISAM;	
  
/**********װ��(accouter)***************/ 
create table accouter (
   acc_ID               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,  /*װ��ID*/ 
   acc_Name             varchar(50)                              ,  /*װ������*/ 
   acc_sex              int                                      ,  /*�Ա�*/ 
   acc_Intro_ID         int                                      ,  /*Ʒ��*/ 
   acc_durability       int                                      ,  /*�;�*/ 
   acc_Def_da           int                                      ,  /*���������*/
   acc_Def_xiao         int                                      ,  /*��������С*/ 
   acc_display          varchar(150)                             ,  /*˵��*/ 
   acc_ReLevel          int                                      ,  /*ʹ�õȼ�*/ 
   acc_bonding          int                                      ,  /*�� 1���� 2ʰȡ�� 3װ����*/ 
   acc_sell             int                                      ,  /*������*/ 
   acc_job              varchar(100)                             ,  /*ְҵ*/ 
   acc_drop             varchar(100)                             ,  /*������*/ 
   acc_protect		    int 					                 ,  /**���� ,0�ǲ�������1�Ǳ������������Դ�����*/
   acc_class		    int                                      ,  /*����:1ñ�ӣ�2�·���3����,4Ь*/
   acc_zj_hp		    varchar(100)                             ,  /* ׷����Ѫ */
   acc_zj_mp			varchar(100)                             ,  /* ׷������ */	
   acc_zj_wxgj		    varchar(100)                             ,  /* ׷�����й��� ���ǹ�����ֵ */
   acc_zj_wxfy			varchar(100)                             ,  /* ׷�����з��� �ɶ��,��ʽ�磺�����ͣ�ֵ-��������12��1-33,2*/ 
   acc_pic   		    varchar(100)                             ,  /*ͼƬ*/
   acc_bind_attribute   int 								     ,  /*�Ƿ�̶�����,0Ϊ�ǣ�1Ϊ���̶����ԣ�����ʱ������������ԣ�**/
   acc_marriage			int									     ,  /**���Ҫ��,1Ϊ�ǣ�2Ϊ��Ҫ��*/
   acc_reconfirm        int                                      ,  /**�Ƿ���Ҫ����ȷ��,0��ʾ�ǣ�1��ʾ��*/
   suit_id              int                            default 0 ,  /**��Ӧ��װid,������װĬ��Ϊ0*/
   acc_bonding_num      int                            default 0 , /**����󶨴�����*/
   specialcontent 	    varchar(50)                    			,   /**����������*/
   primary key (acc_ID)) ENGINE=MyISAM;

/**********����(arm)***************/
create table arm (
   arm_ID               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, /**����id*/ 
   arm_Name             varchar(100)                             , /**��������*/  
   arm_job              varchar(100)                             , /**ְҵ*/ 
   arm_Intro_ID         int                                      , /**Ʒ�� */ 
   arm_durability       int                                      , /**�;�*/
   arm_banding          int                                      , /**��*/ 
   arm_ReLevel          int                                      , /**ʹ�õȼ�*/ 
   arm_sex				int										 , /*�Ա�,��Ϊ1��ŮΪ2����Ҫ��Ϊ0*/
   arm_skill 			int								    	 , /*����,��skill_id,��������ã��������磺1��2��3��4*/
   arm_sell             int                                      , /**��������*/ 
   arm_drop             varchar(200)                             , /**������*/ 
   arm_display		    varchar(200)                             , /**˵��*/  
   arm_class		    int                                      , /**����*/ 
   arm_attack_da        int                                      , /**����˺�*/ 
   arm_attack_xiao      int                                      , /**��С�˺�*/
   arm_protect          int                                      , /**����*/
   arm_zj_hp		    varchar(100)                             ,  /* ׷����Ѫ */
   arm_zj_mp			varchar(100)                             ,  /* ׷������ */	
   arm_zj_wxgj		    varchar(100)                             ,  /* ׷�����й��� */
   arm_zj_wxfy			varchar(100)                             ,  /* ׷�����з��� */ 
   arm_pic              varchar(200)                             , /**ͼƬ*/
   arm_bind_attribute   int 									 , /*�Ƿ�̶�����,0Ϊ�ǣ�1Ϊ���̶����ԣ�����ʱ������������ԣ�**/
   arm_marriage			int								         , /**���Ҫ��,1Ϊ�ǣ�2Ϊ��Ҫ��*/
   arm_reconfirm        int                                      ,  /**�Ƿ���Ҫ����ȷ��,0��ʾ�ǣ�1��ʾ��*/
   suit_id              int                            default 0 ,  /**��Ӧ��װid,������װĬ��Ϊ0*/
   arm_bonding_num      int                            default 0 , /**����󶨴�����*/
   specialcontent 	    varchar(50)                    			,   /**����������*/
   primary key (arm_ID))  ENGINE=MyISAM;

/**********��Ʒ(jewelry)***************/
create table jewelry (
   jew_ID               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, /**��Ʒid*/
   jew_Name             varchar(50)                              , /**����*/
   jew_Sex              int                                      , /**�Ա�*/
   jew_Intro_ID         int                                      , /**Ʒ��*/
   jew_durability       int                                      , /**�;�*/
   jew_Display          varchar(200)                             , /**˵��*/
   jew_ReLevel          int                                      , /**ʹ�õȼ� */ 
   jew_Bonding          int                                      , /**��*/ 
   jew_sell             int                                      , /**������ */
   jew_job              varchar(100)                             , /**ְҵ*/
   jew_Drop             varchar(200)                             , /**������*/
   jew_protect		    int                                      , /**����*/
   jew_class		    int                                      , /**���ͽ�ָ=1������=2������=3*/
   jew_Def_da 		    int                                      , /**��������*/
   jew_Def_xiao 	    int                                      , /**��С������*/
   jew_attack_da        int                                      , /**����˺�*/
   jew_attack_xiao      int                                      , /**��С�˺�*/
   jew_zj_hp		    varchar(100)                             ,  /* ׷����Ѫ */
   jew_zj_mp			varchar(100)                             ,  /* ׷������ */	
   jew_zj_wxgj		    varchar(100)                             ,  /* ׷�����й��� */
   jew_zj_wxfy			varchar(100)                             ,  /* ׷�����з��� */ 
   jew_pic              varchar(200)                             , /**ͼƬ*/
   jew_bind_attribute   int 									 , /*�Ƿ�̶�����,0Ϊ�ǣ�1Ϊ���̶����ԣ�����ʱ������������ԣ�**/
   jew_marriage			int								         , /**���Ҫ��,1Ϊ�ǣ�2Ϊ��Ҫ��*/
   jew_reconfirm        int                                      ,  /**�Ƿ���Ҫ����ȷ��,0��ʾ�ǣ�1��ʾ��*/
   suit_id              int                            default 0 ,  /**��Ӧ��װid,������װĬ��Ϊ0*/
   jew_bonding_num      int                            default 0 , /**����󶨴�����*/
   specialcontent 	    varchar(50)                    			,   /**����������*/
   primary key (jew_ID)) ENGINE=MyISAM;
 
 
/***********װ���������ԣ�equip_append_attribute��************/
create table equip_append_attribute(
   id               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, /**id*/
   equip_type       int                                      , /**װ�����ࣺ1----װ��,2----����,3----��Ʒ*/
   equip_class      int                                      , /**װ��С�ࣺ1=����2=����3=����1=��ָ��2=����3=������1=ͷ����2=�ؼף�3=���ӣ�4=ѥ��*/
   level_lower      int                                      , /**�ȼ�����*/
   level_upper      int                                      , /**�ȼ�����*/
   attribute_type   int                                      , /**�����������ͣ�Ѫ=1����=2�����=3��ľ��=4��ˮ��=5�����=6������=7,���й���=8*/
   value_area       varchar(200)                             , /**����ֵ��Χ����ʽ�磺10,20-21,30-31,40*/
   value_probability  varchar(200)                           , /**����ֵ�ĸ��ʿ��ƣ�����ʽ�磺50-35-15*/ 
 primary key (id)) ENGINE=MyISAM;
 
/**********����(skill)************** */

create table skill  (
   sk_id                 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**���ܱ�ʶID */ 
   sk_name               varchar(50)                               , /**�������� */ 
   sk_display            varchar(200)                              , /**��������	�Լ��ܵ����� */ 
   sk_type               int                                       , /**��������	����=1������=0	*/ 
   sk_expend             varchar(100)                              , /** ���Ķ���	����hp��mp*/ 
   sk_usecondition       int                                       , /**������ֵ	ʹ�ü������Ķ������ֵ	*/  
   sk_damage_di          int                                       , /** �˺�ֵ	���˺�������˺�������˺�	*/ 
   sk_damage_gao         int                                       , /**�˺�ֵ	���˺�������˺�������˺�*/ 	
   sk_area	             int                                       , /**ʹ�÷�Χ	1��ʾȺ�壬0��ʾ����*/
   sk_environment        varchar(200)                              , /**ʹ��״̬	ʹ�øü���ʱ��ɫ������״̬Ҫ��*/	
   sk_weapontype         int                                       , /**������Ҫ����������	ʹ�ô˼���ʱ�����������Ҫ��*/
   sk_goods              int                                       , /** ��Ҫ��Ʒ	ʹ�øü�������Ҫ����Ʒ*/
   sk_goodsnumber        int                                       , /** ��Ҫ��Ʒ������	ʹ�øü�������Ҫ����Ʒ����*/
   sk_time               int                                       , /** ����ʱ��	�ü��ܳ�����ʱ��*/
   sk_baolv              varchar(200)                              , /**  ������	���ܵı�������*/
   sk_yun                int                                       , /** ���μ���	���ܵĻ��μ���*/
   sk_yun_bout           int 									   , /*���λغ� */
   sk_buff               int                                       , /** buffЧ��	buffЧ��id*/
   sk_buff_probability   int 							           , /*buff����*/
   sk_lqtime             int                                       , /**��ȴʱ��	�ٴ�ʹ�øü��ܵļ��ʱ��,�Է���Ϊ��λ*/
   sk_marriaged			 int									   , /**���Ҫ��,1Ϊ�ǣ�2Ϊ��Ҫ��*/
   
   
   sk_gj_multiple        double                                    , /**���������ӳ�*/
   sk_fy_multiple        double                                     ,/**�������ӳ�*/  
   sk_hp_multiple        double                                    , /**HP�ӳ�*/    
   sk_mp_multiple        double                                    , /**MP�ӳ�*/        
   sk_bj_multiple        double                                    , /**�����ʼӳ�*/ 
   sk_gj_add             int                                       , /**������������*/
   sk_fy_add             int                                       , /**���ӷ�����*/
   sk_hp_add             int                                       , /**����HP*/
   sk_mp_add             int                                       , /**����MP*/ 
   
   sk_group              int                                       ,  /**��������� �жϿ��������ܵ�����*/
   sk_sleight            int                                       ,  /**�������ļ�������Ҫ��������*/
   sk_next_sleight       int                                       ,  /**����һ������Ҫ��������  ��-1������������*/
 primary key (sk_id)) ENGINE=MyISAM;
 

 
/**********����(task)***************/
create table task (
   t_id                    smallint unsigned not null auto_increment , /**�����ʶ*/ 
   t_zu                    varchar(200)                              , /**������ͬһϵ������ʹ��һ��������code*/ 
   t_zuxl                  varchar(200)                              , /**�����������ʾ�ڴ�����group�е�λ��*/ 
   t_name                  varchar(200)                              , /**������	*/  
   t_level_xiao            int                                       , /**������ȡ��С�ȼ�*/
   
   t_level_da              int                                       , /**������ȡ���ȼ�*/ 	
   t_repute_type           int                                       , /**������ȡ��������*/ 	
   t_repute_value          int                                       , /**������ȡ������ֵ*/ 	
   t_sex                   int                                       , /**������ȡ�����Ա� 0 ûҪ��1��2Ů*/ 	 	
   t_school                varchar(500)                              , /**������ȡ����*/
   t_type                  int                                       , /**��������*/
   t_time                  int                                       , /**����ʱ�䣨���ӣ�*/	
   t_display               varchar(500)                              , /**��ʼ����԰�	*/
   t_tishi                 varchar(500)                              , /**��ʼ������ʾ*/
   t_key                   varchar(1000)                             , /**��ʼ������ʾ�Ĺؼ��֣������,�ŷָ�*/
   t_key_value             varchar(1000)                             , /**�ؼ������ڵĵ�ͼ�������,�ŷָ�*/
   
   t_geidj                 varchar(500)                              , /**����ʼ�������*/
   t_geidj_number          varchar(200)                              , /**����ʼ�����������	*/
   t_geizb                 varchar(500)                              , /**����ʼ����װ��*/ 
   t_geizb_number          varchar(200)                              , /**����ʼ����װ������	*/
   
   t_point                 varchar(200)                              , /**�м��*/	
   t_zjms                  varchar(500)                              , /**ͨ���м�������*/	
   t_bnzjms                varchar(500)                              , /**����ͨ���м�������	*/
   t_zjdwp                 varchar(500)                              , /**ͨ���м����Ҫ����Ʒ	*/
   t_zjdwp_number          int                                       , /**ͨ���м����Ҫ��Ʒ������*/
   t_zjdzb                 varchar(500)                              , /**ͨ���м����Ҫ��װ��	*/
   t_zjdzb_number          int                                       , /**ͨ���м����Ҫװ��������*/
   t_djsc                  int                                       , /**ͨ���м���Ƿ�ɾ���������0��ɾ��1ɾ��*/
   t_djsczb                int                                       , /**ͨ���м���Ƿ�ɾ��װ��0��ɾ��1ɾ��*/
   t_midst_gs              varchar(500)                              , /**ͨ���м�������Ʒ*/
   t_midst_zb              varchar(500)                              , /**ͨ���м�����װ��*/
 
   t_goods                 varchar(500)                              , /**���������Ҫ����*/
   t_goods_number          varchar(200)                              , /**���������Ҫ��������	*/
   t_goodszb               varchar(500)                              , /**���������Ҫװ��*/ 
   t_goodszb_number        varchar(200)                              , /**���������Ҫװ������	*/
   t_killing	           varchar(500)                              , /**���������Ҫ��ɱ¾*/
   t_killing_no            int                                       , /**���������Ҫ��ɱ¾����*/
   t_pet                   int                                       , /**���������Ҫ����*/
   t_pet_number            int                                       , /**���������Ҫ��������*/
     
   t_money	               varchar(500)                              , /**��������Ǯ����*/
   t_exp	               varchar(500)                              , /**��������齱��*/
   t_sw_type               int                                       , /**�������������������*/
   t_sw                    varchar(500)                              , /**���������������*/
   t_encouragement	       varchar(500)                              , /**���ʽ���*/
   t_encouragement_no	   varchar(500)                              , /**���ʽ���������*/
   t_xrwnpc_id             int                                       , /**��һ����¼��ʼnpc��id*/
   t_next	               int                                       , /**��һ����¼��id*/ 
   t_abandon	           int                                       , /**�Ƿ�ɷ���0�ɷ���1���ɷ���*/ 
   t_encouragement_zb	   varchar(500)                              , /**װ������*/
   t_encouragement_no_zb   varchar(500)                              , /**װ����������*/
   t_cycle                 int                                       , /**�Ƿ��ѭ���������� 0 ������ѭ�� 1����ѭ��*/
   t_waive                 int                              default 0, /**������� ���ֶα�ʾ�������ֶκ󷵻ص������ʼID Ĭ��Ϊ0��ʾû�з�������*/
   primary key (t_id)) ENGINE=MyISAM;


/**********��ҿɽ��ܵ������б�(accept_task_list)***************/
create table accept_task_list ( 
   id                SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT ,  /**����ID*/ 
   touch_id          int                                       ,  /**������������ID �������ID �Ͳ˵�ID*/
   task_area         varchar(400)                              ,  /**����Χ,�磺1,5��ʾ������1��֮�����1��5������*/ 
   probability       int                                       ,  /**����*/
   task_type         int                                       ,  /**�������� 1 ���ߴ������� 2 �˵���������*/
   PRIMARY KEY (id)) ENGINE=MyISAM;



   
/**********С����(map)***************/
create table map (
  map_ID                  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT      , /**С����*/
  map_Name                varchar(200)                              null , /**��ͼ����*/
  map_display             varchar(200)                              null , /**��ͼ����*/
  map_skill                varchar(100)                                       null , /**��ͼ����*/
  map_from	              int                                       null , /**��������*/
  map_type 				  TINYINT									null,  /**��ͼ���ͣ�1Ϊ��ȫ����2ΪΣ������3Ϊ��̨��4Ϊ������5Ϊ���� ��6Ϊս��*/
  primary key (map_ID)) ENGINE=MyISAM;

create table barea (
   barea_ID               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT      , /**������ ����id*/ 
   barea_Name             varchar(200)                              null , /**��������*/ 
   barea_point            int                                       null , /**���ĵ�*/
   barea_display          varchar(200)                              null , /**������� */
   barea_type           int             null,                       /*���Ʋ�ͬ��������Ǻ���������1��2��3����*/
   primary key (barea_ID)) ENGINE=MyISAM;

/**********�ص�(scene)***************/
create table scene (
  scene_ID                  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**�ص�*/
  scene_Name                varchar(200)                              null , /**��������*/
  scene_coordinate          varchar(200)                              null , /**��������*/
  scene_limit               varchar(200)                              null , /**�������� 1���Դ���  2�����Դ��� 3���Դ��� 4�����Դ���*/
  scene_jumpterm            varchar(200)                              null , /**��ͼ��ת*/
  scene_attribute           varchar(200)                              null , /**��ͼ����(�����Ǽ���)*/
  scene_switch              int                                       null , /**PK����,1��ʾ��ȫ*/
  scene_ken                 varchar(200)                              null , /**��Ұ���*/
  scene_skill	            int                                       null , /**��������*/
  scene_photo               varchar(200)                              null , /**����ͼƬ*/
  scene_display             varchar(200)                              null , /**����˵��*/
  scene_mapqy               int                                       null , /**MPA����*/
  scene_shang               int                                  default 0 , /**�Ƿ������� 0�� 1����*/
  scene_xia                 int                                  default 0 , /**�Ƿ������� 0�� 1����*/
  scene_zuo                 int                                  default 0 , /**�Ƿ������� 0�� 1����*/
  scene_you                 int                                  default 0 , /**�Ƿ������� 0�� 1����*/
  primary key (scene_ID)) ENGINE=MyISAM;




/**********npc(npc)***************/ 
create table npc  (
  npc_ID                  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**npc*/
  npc_Name                varchar(200)                              , /**����	npc����*/
  npc_HP                  varchar(200)                              , /**��Ѫ	npc��Ѫֵ*/
  npc_defence_da          int                                       , /**������	npc���� */
  npc_defence_xiao        int                                       , /**��С����	npc����*/
  npc_jin_fy			  int										, /**�����*/	
  npc_mu_fy				  int										, /**ľ����*/	
  npc_shui_fy			  int										, /**ˮ����*/	
  npc_huo_fy			  int										, /**�����*/
  npc_tu_fy				  int										, /**������*/	
  npc_drop             	  int                                       , /**������	��ʾΪ����������20��ʾ20%�ı�����*/
  npc_Level               int                                       , /**�ȼ�	npc�ȼ� */
  npc_EXP              	  double                                    , /**����	ɱ��npc��þ���*/
  npc_money				  varchar(200)					            , /**����Ǯ������ɱ���ֺ��20��30֮�����ȡ����ʾΪ20,30*/
  npc_take            	  int                                       , /**�ɷ�׽	0��ʾ���ɲ�׽��1��ʾ���Բ�׽*/
  npc_refurbish_time      int										, /**ˢ��ʱ����	����Ϊ��λ*/
  npc_type                int										, /**NPC����1��ͨ�� 2������ 3������ */
  npc_pic				  varchar(200)								, /** NPCͼƬ */
  primary key (npc_ID)) ENGINE=MyISAM;
 
  
 /*******************************npc����(npcskill)***********************/
 create table npcskill (
	npcski_id				SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT				 , /**npc����id*/
	npcski_name             varchar(200)                                             , /**�������ƶԼ��ܵ����� */ 
	npcski_type				varchar(20)												 , /**���� 1Ϊbasic������2Ϊpower������3Ϊsupper����*/
	npc_id					int														 , /**npc��id*/
	npcski_wx				int													     , /**��������:��=1��ľ=2��ˮ=3����=4����=5��*/
	npcski_wx_injure		int														 , /**�����˺�*/
	npcski_injure_xiao		int														 , /**����˺�*/
	npcski_injure_da		int														 , /**����˺�*/
	npcski_probability		int														 , /**���м���*/
  primary key (npcski_id)) ENGINE=MyISAM;
create index Index1 on npcskill(npc_id); 
  
  
  
  
/**********npc�����(npcdrop)***************/ 
create table npcdrop (
  npcdrop_ID                        SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**npc�����*/
  npc_ID	                        int                                       , /**����id*/
  goods_type	                    int                                       , /**��Ʒ����*/
  goods_id	                        int                                       , /**��Ʒid*/
  goods_name			            varchar(200)						      , /**��Ʒ����*/
  npcdrop_probability               int                                       , /**�伸��	ɱ����npc�������Ʒ�ļ��ʣ�100��ʾ���֮һ����*/
  npcdrop_luck                      int                                       , /** �󱩼���	�󱩱�ʾ������Ʒ���伸��Ϊԭ��10����10��ʾɱ����npc��10%�ļ��ʴ�*/
  npcdrop_attribute_probability     int                                       , /*���Ӹ������Լ���*/
  npcdrop_num			            varchar(200) 			                  , /*���������洢��ʽΪ:1,1*/
  npcdrop_taskid    	            int 			                 default 0, /*�������� 0 ��ʾ����״̬���� ��0��ʾ�������*/
  npcdrop_importance				int							 default 0, /** ����Ʒ�Ƿ���Ҫ��1Ϊ��Ҫ��Ʒ��0Ϊ����Ҫ��Ʒ  */
  quality							int							 default -1, /** ����Ʒ�Ƿ���Ҫ��1Ϊ��Ҫ��Ʒ��0Ϊ����Ҫ��Ʒ  */
  primary key (npcdrop_ID)) ENGINE=MyISAM;
create index Index1 on npcdrop(npc_ID); 
   
/**********NPCˢ��(npcrefurbish)***************/ 
create table npcrefurbish (
  npcrefurbish_id            SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**id*/
  npc_id	                 int                                       , /**����id*/
  scene_id	                 int                                       , /**ˢ�µ�	ˢ��npc�ĵص�code*/
  refurbish_number	         varchar(200)                              , /**ˢ������	�˵�ˢ�µ�npc������  �趨��Χ�磺2��5����ʾ2��5��*/
  refurbish_attackswitch     int                                       , /**������������	0��ʾ�˵�ˢ�³���npc����������ң�1��ʾ�˵�ˢ�³���npc����������ң�*/
  refurbish_probability      int                                       , /**ˢ�¸���	�˵�ÿ������ˢ�³��ֵļ��ʣ���100Ϊ��ĸ������ֵ��ʾ����*/
  refurbish_time_ks          varchar(200)                              , /**ˢ��ʱ��1	��ĳ��ʱ�䵽��һ��ʱ��֮�����  ��ʼʱ��*/
  refurbish_time_js          varchar(200)                              , /**ˢ��ʱ��1	��ĳ��ʱ�䵽��һ��ʱ��֮�����  ����ʱ��*/
  day_time_ks                varchar(200)                              , /**ˢ��ʱ��2	ÿ���ĳ��ʱ�䵽��һ��ʱ�����*/
  day_time_js                varchar(200)                              , /**ˢ��ʱ��2	ÿ���ĳ��ʱ�䵽��һ��ʱ�����*/
  isBoss                     int                              default 0, /**��ʾnpc�Ƿ���boss*/
  isDead                     int                              default 1, /**�Ƿ�������1��ʾ�ǣ�0��ʾû��*/
  dead_time                  datetime                                  , /**���һ������ʱ��*/
  primary key (npcrefurbish_id)) ENGINE=innodb;
create index Index1 on npcrefurbish(scene_id,isBoss);
create index Index2 on npcrefurbish(scene_id,npc_id);
create index Index3 on npcrefurbish(scene_id,isDead);
create index Index4 on npcrefurbish(npc_id,scene_id,isBoss);
    
/*********�˵���(salelist)***************/ 
create table npcshop (
  npcshop_id                 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**NPC����id*/
  npc_id	                 int                                       , /**����id*/
  goods_type	             int                                       , /**��Ʒ����*/
  goods_id	                 int                                       , /**��Ʒid*/
  goods_name                 varchar(200)                              , /**��Ʒ����*/
  npc_shop_goodsbuy          varchar(200)                              , /**�����*/
  primary key (npcshop_id)) ENGINE=MyISAM;
create index Index1 on npcshop(npc_id); 
  
/**********�����(pet)************** */ 
create table pet (
  	pet_id                   SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**����id*/
  	npc_id                   int                                       , /**���NPCID*/
	pet_name                 varchar(200)                              , /**��������*/
    pet_img                  varchar(200)                              , /**����ͼƬ*/
    pet_drop_da	             double                                    , /**����ɳ��� �硰min=1&max=1.2��*/
    pet_drop_xiao	         double                                    , /**����ɳ��� */
    pet_wx                   varchar(200)                              , /**��������	��=1��ľ=2��ˮ=3����=4����=5 */
    pet_wx_value             varchar(200)                              , /**��������ֵ*/   
    pet_isAutoGrow           int                                       , /**����	�Ƿ����Ȼ����*/
    pet_type                 int                                       , /**�������� */	 
    pet_fatigue		         int			 	                       , /**ƣ�Ͷ�0-100,��ս״̬������ƣ�Ͷȣ�һ��Сʱ��10��*/
    pet_longe		         int			 	                       , /**��������*/
    longe_number             int			 	                       , /**��������ʹ�ô���*/ 
    skill_control            int			 	                       , /**�������������ѧϰ���ٸ�����*/
    pet_skill_one	         int                                       , /**����1	��ѧϰ�ļ���id*/
    pet_skill_two	         int                          	           , /**����2	��ѧϰ�ļ���id*/
    pet_skill_three     	 int                        	           , /**����3	��ѧϰ�ļ���id*/
    pet_skill_four	         int                          		       , /**����4	��ѧϰ�ļ���id*/
    pet_skill_five	         int                                  	   , /**����5	��ѧϰ�ļ���id*/
    
    pet_violence_drop        double                                    , /**���ﱩ����*/
  primary key (pet_id)) ENGINE=MyISAM;
  
  /**********���＼�ܱ�(pet_skill)***************/ 
create table pet_skill (
  	pet_skill_id             SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**����id*/
	pet_skill_name           varchar(200)                              , /**��������*/
	pet_skill_bewrite        varchar(200)                              , /**��������*/
	pet_skill_type	         int                                       , /**��������,��������=0����������=1*/
	pet_skill_gj_da	         int                                       , /**��С�˺�ֵ*/ 
	pet_skill_gj_xiao		 int									   , /**��С�˺�ֵ*/
	pet_skill_area	         int                                       , /**ʹ�÷�Χ  1��ʾȺ�壬0��ʾ����*/ 
	
	pet_skill_several        int                                       , /*c*���ܹ�������*/
    pet_skill_multiple       double                                    , /**���ܹ�������*/
    pet_skill_injure_multiple double                                   , /**���������ӳ�*/
    pet_skill_violence_drop_multiple double                            , /**�����ʼӳ�*/
    
    
    pet_skill_group          int                                       , /**������*/
    pet_skill_level          int                                       , /**���ܵȼ�*/
    pet_grade                int                                       , /**����ѧϰ�ü�������Ҫ�ĵȼ�*/
  primary key (pet_skill_id)) ENGINE=MyISAM;   
  
/**********���＼�ܿ��Ʊ�(pet_skill_control)***************/ 
create table pet_skill_control (
  	control_id               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**���＼�ܿ���id*/
	pet_id                   int                                       , /**����id*/
    pet_skill_id             int                                       , /**����id*/
    control_drop             int                                       , /**���м���*/
    
    
    pet_skill_group          int                                       , /**������*/
  primary key (control_id)) ENGINE=MyISAM; 
create index Index1 on pet_skill_control(pet_id,pet_skill_group);
  
  
/**********����ɳ���(pet_shape)***************/ 
create table pet_shape (
  	shape_id                 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**����ɳ�id*/
  	pet_id                   int                                       , /**����id*/
  	pet_type                 int                                       , /**��������1��ͨ�� 2������ 3������*/
	shape_rating             int                                       , /**�ȼ�	*/
	shape_sale				 int									   , /**�����۸�*/
	shape_ben_experience     varchar(200)                              , /**��������	�ﵽ�ĵȼ���Ҫ����	*/
	shape_xia_experience     varchar(200)                              , /**�¼�����	�ﵽ��һ����Ҫ�ľ���*/
	shape_attack_xiao        varchar(200)                              , /**��С����*/
	shape_attack_da          varchar(200)                              , /**��󹥻�*/
	shape_type               int                                       , /**����	�Ƿ����Ȼ����*/	
  primary key (shape_id)) ENGINE=MyISAM; 
  

 /****************************����ɳ���************************************/
 create table u_grow_info(
   g_pk		SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT 	  		,   /**id */
   g_grade  	    int							            	,   /**�ȼ�*/ 
   g_school         varchar(500)                                ,   /**����*/
   g_title          varchar(500)                                ,   /**��λ*/
   g_exp	        varchar(500)						        ,   /**��������*/
   g_next_exp	    varchar(500)						        ,   /**�¼�����*/
   g_HP		        int								            ,   /**Ѫ��ֵ*/
   g_MP		        int								            ,   /**����ֵ*/
   g_force          int                                    	  	,   /**��*/
   g_agile          int                                   		,   /**��*/
   g_physique       int                                    	   	,   /**����*/
   g_savvy          int                                   		,   /**����*/
   g_gj       	    int                                   		,   /**����*/
   g_fy       	    int                                    		,   /**����*/
   g_isAutogrow	    int								            ,   /**�Ƿ����Ȼ���� 1��ʾ������Ȼ������0��ʾ��������Ȼ���� */
   
   g_drop_multiple       double                                    , /**���ɱ�����*/ 
   primary key (g_pk)) ENGINE=MyISAM;
   
  
  /************************************�˵���**********************************/
  create table operate_menu_info(
  	   id				  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT 	  	    ,  /**id */
  	   menu_name          varchar(500)											,  /**�˵�����*/
  	   menu_type		  int													,  /**�˵�����*/
  	   menu_use_number	int                                              default 0  ,/** ÿ��ʹ�ô��� */
  	   menu_img			  varchar(500)											,  /**�˵�ͼƬ*/
  	   menu_map			  int													,  /**�˵��ص�id*/
  	   menu_operate1      varchar(500)											,  /**�����ֽ�1*/
  	   menu_operate2      varchar(500)											,  /**�����ֽ�2*/
  	   menu_operate3      varchar(500)											,  /**�����ֽ�3*/
  	   menu_camp          int													,  /**0Ϊ������1Ϊ����2Ϊа*/
  	   menu_dialog        varchar(600)											,  /**�Ի�	�������ֵ�һ�仰*/
  	   menu_time_begin    varchar(200)                                          ,  /**ˢ��ʱ��1	��ĳ��ʱ�䵽��һ��ʱ��֮�����  ��ʼʱ��   ������ʽ��  2008-8-9   16:22:00*/
       menu_time_end      varchar(200)                                          ,  /**ˢ��ʱ��1	��ĳ��ʱ�䵽��һ��ʱ��֮�����  ����ʱ��*/
       menu_day_begin     varchar(200)                                          ,  /**ˢ��ʱ��2	ÿ���ĳ��ʱ�䵽��һ��ʱ�����    ������ʽ�� 16:00:9*/
       menu_day_end       varchar(200)                                          ,  /**ˢ��ʱ��2	ÿ���ĳ��ʱ�䵽��һ��ʱ�����*/ 
  	   menu_refurbish_time		int												,  /**�ٴ�ʹ�ô˹��ܲ˵��ļ��ʱ��,����Ϊ��λ*/
  	   menu_father_id			int									    		,  /**���˵�id��������˵�idΪ0�����ʾ�Ƕ����˵�*/
  	   menu_order				int									         	,  /**ͬһ���˵���С����������ʾ*/
  	   menu_tasks_id			varchar(500)                                    ,  /**�˵�npc��ӵ���û�������������ַ���,��ʽ�磺2,4,5*/
  	   menu_task_flag           int                                    default 0,  /**��ʶ�Ƿ�������˵�,0��ʾ��ͨ�˵���1��ʾ����˵�*/
  	   menu_operate4      int(5)			default 0								,  /**�����ֽ�4*/
  	   primary key (id)) ENGINE=MyISAM;
create index Index1 on operate_menu_info(menu_map,menu_father_id,menu_task_flag,menu_order);
create index Index2 on operate_menu_info(menu_father_id,menu_order);
  
  
  
 /**********���߱�(prop)***************/
create table prop (
   prop_ID             		 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, /**���߱�*/
   prop_Name           		 varchar(50)                              , /**����*/
   prop_class		   		 int                                      , /**���Ͳ�ͬ��Ʒ����ʹ���в�ͬ�Ĺ��ܣ���������ա���ӹȺ������Ʒ��*/
   prop_ReLevel        		 varchar(50)                              , /**ʹ�õȼ���Χ ʹ�ø���ƷҪ���ɫ�ȼ�*/
   prop_bonding        		 int                                      , /**��	0�ǲ��󶨣�1��ʰȡ�󶨣�2��װ����,3���װ�*/
   prop_accumulate     		 int                                      , /**	��ʾ�����ص�������*/
   prop_display		   		 varchar(200)                             , /**˵��	��Ʒ����*/
   prop_sell           		 int                                      , /**������	��Ʒ����ϵͳ�ļ۸�*/
   prop_job            		 varchar(100)                             , /**ְҵ��������ͨ�õģ�	ְҵid��ֻ�и�ְҵ�Ľ�ɫ�ſ�ʹ�ø���Ʒ���ɶ��ְҵ*/
   prop_drop           		 varchar(200)                             , /**������	PK���伸��*/
   prop_usedegree       	 int                                      , /**ÿ��ʹ�ô���	��ɫÿ��ʹ�ø���Ʒ�Ĵ�������*/
   prop_sex             	 int                                      , /**�Ա�	ʹ�ø���Ʒ���Ա����ƣ���Ϊ1��ŮΪ2����Ҫ��Ϊ0*/
   prop_protect       	     int                                      , /**����	0�ǲ�������1�Ǳ������������Դ�����*/
   prop_pic            		 varchar(200)                             , /**ͼƬ	��дͼƬcode*/ 
   prop_position			 varchar(200)					          ,  /**�洢λ�� 1ΪҩƷ��2Ϊ���3Ϊװ����4Ϊ����5Ϊ���� 6Ϊ�̳ǵ���*/
   prop_auctionPosition		 varchar(200)							  ,	/**����������λ��  1ΪҩƷ��2Ϊ���3Ϊ���ϣ�4Ϊ�̳���5Ϊ������6Ϊ���ߣ�7Ϊ���Σ�8Ϊ������0Ϊ�������� */
   prop_marriage			 int		     						  ,  /**���Ҫ��,1Ϊ�ǣ�2Ϊ��Ҫ��*/
   prop_operate1        	 varchar(500)							  ,  /**�����ֽ�1*/
   prop_operate2       	 	 varchar(500)							  ,  /**�����ֽ�2*/
   prop_operate3        	 varchar(500)							  ,  /**�����ֽ�3*/
   prop_reconfirm            int                                      ,  /**�Ƿ���Ҫ����ȷ��,0��ʾ�ǣ�1��ʾ��*/
   prop_use_control          int                             default 0,  /**���Ƶ����Ƿ���ã�0��ʾ�������ƣ�1��ʾս��ʱ������*/
   primary key (prop_ID)) ENGINE=MyISAM;
    
    
 
 /**********������Ŀ��(quiz_repository)***************/
 create table quiz_repository(
     quiz_id                      int NOT NULL AUTO_INCREMENT                         ,  /**id*/
     quiz_content                 varchar(1000)                                                     ,  /**��Ŀ����*/
     quiz_answers                 varchar(1000)                                                     ,  /**��Ŀ��ѡ��,�洢��ʽ�磺1.��һ,2.�𰸶�,3.����    ���ж��*/
     quzi_right_answer            int                                                               ,  /**��Ŀ��ȷ��,�洢��Ӧ�������ţ�����ȷ��Ϊ2.�𰸶�����洢��2*/
     quiz_probability             int                                                               ,  /**�������*/
     award_experience             bigint                                                            ,  /**�����ľ���*/
     award_money                  bigint                                                            ,  /**�����Ľ�Ǯ*/
     award_prestige               varchar(50)                                                       ,  /**����������*/
     award_goods                  varchar(50)                                                       ,  /**��������Ʒ���洢��ʽ�磺��Ʒ����,��Ʒid,��Ʒ����- */
  primary key (quiz_id) ) ENGINE=MyISAM;
    
    
    
   
 
/**********BUFF(buff)***************/ 
create table buff(
    buff_id                 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**id*/	
    buff_type               int                                       , /**buff����*/  
	buff_name               varchar(200)                              , /**buff����*/
	buff_display            varchar(200)                              , /**buff����*/
	buff_time               int                                       , /**����ʱ�䣬��λΪ����*/
	buff_bout               int                                       , /**�����غ�*/
	buff_effect_value       int                                       , /**buffЧ��ֵ,����Ǹ����򰴰ٷֱȵķ�������дЧ��ֵ���磺20%����20*/
	
	buff_use_mode           int                                       , /**ʹ�÷�ʽ��1��ʾ���棬2��ʾ����*/
	buff_bout_overlap       int                             default 0 , /**�Ƿ�غϵ���,0��ʾ���ܣ�1��ʾ��*/
	buff_time_overlap       int                             default 0 , /**�Ƿ�ʱ�����,0��ʾ���ܣ�1��ʾ��*/
  primary key (buff_id)) ENGINE=MyISAM; 
  
  

  /**************************************��ν������Ϣ(title_info)*************************/
  create table title_info(
  title_id                  varchar(100)                               ,  /**��νid*/
  title_name                varchar(100)                               ,  /**��ν����*/
  school_id                 varchar(100)                               ,  /**����id*/
  school_name               varchar(100)                               ,  /**��������*/
  title_level_down          int                                        ,  /**��͵ȼ�Ҫ��*/
  title_level_up            int                                        ,  /**��ߵȼ�Ҫ��*/
  primary key (title_id)) ENGINE=MyISAM;
  
     
   
  /**************************************��װ(suit_info)*************************/
  create table suit_info(
  suit_id                   SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT  ,  /**id*/	
  suit_name                 varchar(100)                               ,  /**��װ����*/
  suit_parts_num            int                                        ,  /**��ɵ���װװ��������*/
  suit_parts                varchar(100)                               ,  /**��ɵ���װװ����*/
  two_effects               varchar(100)                               ,  /**����Ч��*/
  two_effects_describe      varchar(500)                               ,  /**����Ч������*/
  four_effects              varchar(100)                               ,  /**�ļ�Ч��*/
  four_effects_describe     varchar(500)                               ,  /**�ļ�Ч������*/
  six_effects               varchar(100)                               ,  /**����Ч��*/
  six_effects_describe      varchar(500)                               ,  /**����Ч������*/
  seven_effects             varchar(100)                               ,  /**�߼�Ч��*/
  seven_effects_describe    varchar(500)                               ,  /**�߼�Ч������*/
  primary key (suit_id)) ENGINE=MyISAM;    
   
/**********��ֹȡ������(jy_forbid_name)***************/
 create table jy_forbid_name (
    id	               smallint unsigned not null auto_increment , /**��ֹȡ������id*/ 
    checkcase           int                                       , /**  �������  */
    checktype            int                                       , /** ������� */
    onechar             varchar(100)                              , /** ��ֹ����ؼ���  */
    str            	varchar(100)                                  , /** ��ֹȡ������ */ 
    primary key(id)) ENGINE=MyISAM;

  /**********************************ϵͳ��Ʊ��lottery***********************************************/
  create table lottery(
   	 lottery_bonus	             int	 		   		     			   , /**�����ܶ� */
 	 sys_charity_bonus		     int					   				   , /**���ƽ����ܶ�*/
 	 lottery_tax                 int                                       , /**˰�����*/
  	 sys_bonus_type			     int									   , /**ϵͳ׷�ӽ�������*/
	 sys_bonus_id				 int									   , /**ϵͳ׷�ӽ���ID*/
	 sys_bonus_intro             int                                     ,/**ϵͳ׷�ӽ���Ʒ��*/
	 sys_bonus_num	             int                                       , /**ϵͳ׷�ӽ�������*/
  	 sys_lottery_number          varchar(200)                              , /**��������*/
  	 sys_subjoin		         int                        DEFAULT 0               , /**ϵͳ�������*/
  	 sys_charity_number         varchar(200)                                , /**�����н�����*/
  	 lottery_win_num    int    DEFAULT 0                                       /**�����н�ע��*/
  ) ENGINE=MyISAM;
  /**��Ʊ�� ��**/
  create table lottery_new(
  	lottery_yb					varchar(100)							,/**�����ܽ���**/
  	lottery_bonus				varchar(50)								,/**����׷�ӽ���**/
  	lottery_tax					int									default 0 /**˰��*   Ϊǧ��֮��**/  
  ) ENGINE=MyISAM;
  
   /**********************************ϵͳ���ʱ�laborage***********************************************/
  create table laborage(
 	 min_time		            int					   				   , /**ʱ����Сֵ*/
 	 max_time                   int                                    , /**ʱ�����ֵ*/
  	 laborage_bonus				varchar(100)							/**��������*/
  ) ENGINE=MyISAM;
  
  /********************************************����������**************************************/
create table p_credit(
      cid int primary key auto_increment,/**����������**/
      cname varchar(20)                      ,/**����������**/
      cdisplay varchar(500)                   /**����������**/
) ENGINE=MyISAM;

/*************************npc���侭�鱶��(exp_npcdrop)********************************/
  create table exp_npcdrop(
  	en_pk	                         smallint unsigned not null auto_increment       ,  /**id*/ 
  	default_exp                      int                                    default 1,  /**Ĭ�Ͼ��鱶��*/
  	begin_time                       varchar(300)                                    ,  /**��ʼʱ��*/
  	end_time                         varchar(300)                                    ,  /**����ʱ��ʱ��*/
  	en_multiple                      int                                    default 1,  /**���鱶��*/
  	en_cimelia                       int                                    default 1,  /**��������*/
  	enforce                          int                                             ,  /**ִ�� 0 ��ִ�� 1ִ��*/
  	exp_cimelia                      int                                             ,  /**1�Ǿ������ 2�ǵ�������*/
  	acquit_format                    int                                             ,  /**���ָ�ʽ 1(16:00:00���ָ�ʽ) 2(2009-01-16 16:37:45���ָ�ʽ)*/
    primary key(en_pk)) ENGINE=MyISAM;
    
/*******************������Ϣ**************/
create table instance_info(
    id                               int not null auto_increment                     ,  /**id*/   
    display                          varchar(200)                                    ,  /**����*/
    map_id                           int                                             ,  /**��������map��id*/
    enter_scene_id                   int                                             ,  /**��ҽ��븱��ʱ�ĵص�*/
    reset_time_distance              int                                             ,  /**����ʱ������ʱ�䵥λΪ��*/
    pre_reset_time                   datetime                                        ,  /**��һ�ε�����ʱ��*/
    level_limit                      int                                    default 0,  /**�ȼ�Ҫ��*/
    group_limit                      int                                    default 0,  /**��ӳ�Ա��������*/
    boss_scene_num                   int                                             ,  /**��boss�ص������*/
    primary key(id)) ENGINE=MyISAM;


/*************** �������н�����(tong_build)*********************************/
 create table tong_build(
   tb_id                smallint unsigned not null auto_increment , /**����*/ 
   tb_name              varchar(100)                              , /**��������*/ 
   tb_grade             int                                       , /**�����ȼ�*/
   tb_definition        varchar(200)                              , /**����˵��*/
   tb_group             int                                       , /**����������*/
   tb_map               int                                       , /**�������ڵĵ�ͼ*/
   primary key (tb_id)) ENGINE=MyISAM;  
   
/***************���ɽ�������������(tong_build_up)*********************************/
 create table tong_build_up(
   tbu_id                smallint unsigned not null auto_increment , /**����*/ 
   tb_id                 int                                       , /**����id*/
   tb_grade              int                                       , /**�����ȼ�*/
   tb_name               varchar(100)                              , /**����name*/
   tb_money              varchar(100)                              , /**������������Ҫ��Ǯ�ļ���*/
   tbu_prop              varchar(200)                              , /**������������Ҫ�ĵ��� ����ID �ö��ŷָ�*/
   tbu_prop_number       varchar(200)                              , /**������������Ҫ�ĵ������� ���������ö��ŷָ�*/
   primary key (tbu_id)) ENGINE=MyISAM;

/***************���ɽ���������(tong_build_condition)*********************************/
 create table tong_build_condition(
   tbc_id                smallint unsigned not null auto_increment , /**����*/ 
   tb_id                 int                                       , /**����id*/
   tb_grade              int                                       , /**�����ȼ�*/
   tb_name               varchar(100)                              , /**����name*/
   tb_money              varchar(100)                              , /**������������Ҫ��Ǯ�ļ���*/
   tbu_prop              varchar(200)                              , /**������������Ҫ�ĵ��� ����ID �ö��ŷָ�*/
   tbu_prop_number       varchar(200)                              , /**������������Ҫ�ĵ������� ���������ö��ŷָ�*/
   primary key (tbc_id)) ENGINE=MyISAM;
   
/***************��Ʒ�ϳɱ�(synthesize)*********************************/
 create table synthesize(
  s_id                    smallint unsigned not null auto_increment ,/**�䷽ID����*/
  s_type                  int                ,/**�䷽���� 1Ϊ��� 2Ϊ��ҩ 3Ϊ���� 4Ϊ֯�� 5Ϊ�鱦 6Ϊľ�� */
  s_level                 int                ,/**�䷽�ȼ� ����3������*/
  prop                    varchar(200)       ,/**�ϳ���Ҫ����Ʒ���� �ο��һ��˵����ݱ༭����*/
  s_prop                  varchar(200)       ,/**������Ʒ*/
  s_sleight               int                ,/**ʹ�ø��䷽���Եõ��ļ���������*/
  s_min_sleight            int                ,/**ʹ�ø��䷽��µļ���������*/
  s_max_sleight           int                ,/**����������(ʹ���䷽�õ��������ȵ����ֵ)*/
  s_book           int                ,/**�Ƿ���Ҫ������ 0Ϊ����Ҫ 1Ϊ��Ҫ*/
   primary key (s_id)) ENGINE=MyISAM;






/*****************D------P-----��Ʒ��ر�*****************/
create table equip_need_stake(
   ens_id            int primary key auto_increment      ,/***��������***/
   ens_equip_id            int                           ,/**��Ҫ��ص���ƷID***/
   ens_equip_type          int                            /**��Ҫ��ص���Ʒ���***/
) ENGINE=MyISAM;
/*****************��Ʒ���;��***************/
create table equip_get_path(
   egp_id            int primary key auto_increment,/**��������***/
   egp_name          varchar(20)                    /***��Ʒ����***/
) ENGINE=MyISAM;


/**********IP������(ip_whitelist)***************/
 create table ip_whitelist(
    ip_pk	               int not null auto_increment               , /**������ID*/ 
    ip_begin               varchar(100)                              , /**IP��ʼ*/
    ip_end                 varchar(100)                              , /**IP����*/ 
    primary key(ip_pk)) ENGINE=MyISAM;
    
/*********IP������(ip_blacklist)***************/
 create table ip_blacklist(
    ip_pk	               int not null auto_increment               , /**������ID*/ 
    ip_list                varchar(100)                              , /**IP��ʼ*/
    primary key(ip_pk)) ENGINE=MyISAM;

    /*********ϵͳ���ܱ�(�ݶ�)(system))***************/
 create table system(
    s_player                int            default  600             ,/**ϵͳ��������*/
    all_key                 varchar(50)                             ,/**��������*/
    pc_islogininfoname      int            default  0               ,/**��PC��½������ID 0 �� 1��*/
    pc_black                int            default  0               ,/**��PC��½������IP 0 �� 1��*/
    pc_ua                   int            default  0               ,/**��PC��½UA 0 �� 1��*/
    pc_link_number          int            default  0                /**��PC��½�������3�� 0 �� 1��*/
  ) ENGINE=MyISAM;
  
   /************�̳���Ʒ��Ϣ(commodity_info)***********************/  
 create table commodity_info(
    id                      int not null auto_increment               , /**ID*/     
    prop_id                 int                                       , /**����id*/
    prop_name               varchar(100)                              , /**��������*/
    type                    int                                       , /**��Ʒ����*/
    buy_mode                int                                       , /**�������ͣ�1��ʾԪ����2��ʾ����*/
    original_price          int                                       , /**ԭʼ�۸�*/
    discount                int                            default -1 , /**��Ʒ��ǰ���ۿۣ�������ʽΪ���磺80����8�ۣ�Ĭ��Ϊ-1��ʾ������*/
    commodity_total         int                            default -1 ,/**��Ʒ����-1��ʾ������*/
    sell_num                int                             default 0 , /**��������*/
    is_hot                  int                             default 0 , /**�Ƿ����Ƽ���Ʒ,-��ʾ�ǣ�0��ʾ��*/
    state                   int                             default 1 , /**��Ʒ״̬,1��ʾ��Ч��0��ʾ��Ч��Ʒ*/
    isUsedAfterBuy          int                             default 0 ,/**������*/
    hot_display             varchar(300)                              , /**�Ƽ���Ʒ������*/
    create_time             datetime                                  , /**����ʱ��*/
    is_nomral				int								default 0 ,/***�Ƿ�����ͨԪ���̳�**/
    is_hotmall				int								default 0 ,/***�Ƿ�����ͨ�����̳�**/
    is_jfmall				int								default 0 ,/***�Ƿ�����ͨ�����̳�**/
    is_vipmall				int								default 0 ,/***�Ƿ�����ͨVIP�̳�**/
    primary key(id)
 ) ENGINE=MyISAM;
 
 /**********�ؼ���Ϣ��(miji_info)***************/
 create table miji_info(
    mj_id	               int not null auto_increment               , /**�ؼ�ID*/ 
    mj_info               varchar(500)                              , /** �ؼ� ���� */
    primary key(mj_id)) ENGINE=MyISAM;
    
/*****************   ���ɹ���ս��  (��̨)   ***************/
   create table tong_siege_battle (
  	  siege_id		 int unsigned not null auto_increment                       ,/** ����սID,������ĳ�����еĹ���  */
  	  siege_name	 varchar(100)						 						,/**  ����ս�������� ***/
  	  map_id		 int 														,/*** ���ɹ���ս����Ӧ��map_type  **/
  	  affect_map_id  int														,/**   Ӱ��ĵ�ͼMAP_ID,�ڴ�ID �ڵ������ܴ˳��й�Ͻ,�����һ��, �Զ������� ***/
  	  tax			 int														,/*****   �˹���ս�������������˰��ˮƽ,��1��10֮��   *****/
  	  tax_money		 int														,/**      �˹���ս�������������˰��      **/
  	  out_scene		 int														,/****   ǿ�ƴ���ʱ�ĵص�    */		
  	  relive_scene	 varchar(20)											    ,/****   ��Ҹ����,1Ϊ���Ƿ�,2Ϊ�سǷ���.    */		
  	  primary key(siege_id)) ENGINE=MyISAM;		
  	  
  	  
 /***********************************���ߵ��ߣ���̨�ı�***********************************/
 create table be_off_prop(
    be_id               int not null auto_increment                        ,/**����id*/
	prop_name           varchar(50)                              default  0,/**��������*/
	prop_display        varchar(500)                                       ,/**��������*/
	prop_money          varchar(50)                              default  0,/**����Ԫ��*/
	prop_time           varchar(50)                              default  0,/**����ʱ�� Сʱ����*/
	primary key(be_id)) ENGINE=MyISAM;
	
 /***********************************��̨�ƺű�(honour)***********************************/
  create table honour(
    ho_id                int not null auto_increment                        ,/**����id*/
	ho_title             varchar(100)                                       ,/**�ƺ�����*/
	ho_type              int                                                ,/**�ƺ�����*/
	ho_type_name         varchar(100)                                       ,/**�ƺ���������*/
	ho_display           varchar(100)                                       ,/**�ƺ�����*/
	ho_attack            int                                       default 0,/**���ӹ���*/
	ho_def               int                                       default 0,/**���ӷ���*/
	ho_hp                int                                       default 0,/**������Ѫ*/
	ho_crit              int                                       default 0,/**���ӱ���*/
	use_time             int                                       default 0 ,/**˵��*/
	primary key(ho_id)) ENGINE=MyISAM;		
	
 /***  ���ͱ�  ****/
  create table carry_table_info (
  		carry_id			int unsigned not null auto_increment  ,		/** ���ͱ�ID  */
  		carry_type_id		int														,		/***  �ص�����   ****/	
  		carry_type_name		varchar(50)												,		/***  �ص��������� **/	
  		scene_id			int														,		/**   �ص�id    **/
  		scene_name  		varchar(50)												,		/***  �ص�����  ****/
  		carry_grade			int														,		/**   �ص㴫�͵ȼ�  **/
 primary key(carry_id)) ENGINE=MyISAM;
 
 /******************************�齱��*********************************/
 create table lottery_draw (
 		id					int unsigned not null auto_increment					,/**ID**/
 		type				int														,/**����**/
 		lottery_name		varchar(50)												,/**�齱�����**/
 		draw_people			int														,/**�齱����**/
 		draw_level			varchar(10)												,/**�齱�ȼ�����**/
 		bonus				varchar(50)												,/**��������**/
 		time_type			int														,/**ʱ������***/
 		time_hour			int														,/***Сʱ**/
 		time_minute			int														,/***����**/
 		time_week			varchar(10)												,/***����**/
 		is_run				int											 default 0	,/***�Ƿ�ִ��**/
 primary key(id)) ENGINE=MyISAM;

/******************************���ɴ����*********************************/
 create table menpaicontest (
 		id					int unsigned not null auto_increment					,/**ID**/
 		time_week			varchar(10)												,/***����**/
 		ready_hour			int														,/***׼��ʱ��*/
 		ready_minute		int														,/***׼��ʱ��*/
 		run_hour			int														,/***��ʼʱ��*/
 		run_minute			int														,/***��ʼʱ��*/
 		over_hour			int														,/***����ʱ��*/
 		over_minute			int														,/***����ʱ��*/
 		all_hour			int														,/***����ʱ��*/
 		all_minute			int														,/***����ʱ��*/
 primary key(id)) ENGINE=MyISAM;

 /***********************************��Ա��̨��(vip)***********************************/
  create table vip(
    v_id                 int not null auto_increment                        ,/**����id*/
    v_name               varchar(100)                                       ,/**VIP����*/
    use_time             int                                                ,/**ʹ��ʱ�� Сʱ����*/
    mall_agio            int                                                ,/**�̳��ۿ�*/
    ho_id                int                                                ,/**�ƺ�ID*/
    is_die_drop_exp      int                                       default 0,/**�����ǲ�����ʧ���� 0 ��ʧ 1����ʧ*/
    v_hint               varchar(600)                                       ,/**VIP˵��*/
    v_money              int                                       default 0,/**VIP˵��*/
	primary key(v_id)) ENGINE=MyISAM;
	
	 	        	   	   	  /***  ����  ****/
  	  create table  help  (
  	  		id			int unsigned not null auto_increment 					 ,		/** ������ID  */  	 
  	  		super_id		int(11)		default 0											,		/*** ������id **/	
  	  		name		varchar(100)	not null									,		/**   ��������    **/
  	  		des		text(1000)  default null									,		/**   ��������    **/
  	  		shunxu		int(11)   default 0									,		/**   ����    **/
  	  		scene_id		int(12) default 0													,		/**   ��Ӧ������id    **/
  	  		level_limit		int(2)	default 0														,		/**   �Դ������ȼ�����   **/
  	  		type int (2) default 0                                                       ,/**  �˵�����   **/
  	  		link_name varchar(50) default null                                ,/**  ��������   **/
  	  		task_men int(2) default 0                                ,/**  ��������:�Ƿ��Ǳ����ɣ�0��Ҫ��1���̣�2ؤ�3����   **/
  	  		task_zu varchar(50) default null                          ,/**�����������*/
  	 primary key(id)) ENGINE=MyISAM;
  	 
  	 
  	   	   	  /***  ϵͳ������  ****/
  	  create table  system_hortation_info  (
  	  		horta_id			int unsigned not null auto_increment 					 ,		/** ϵͳ������ID  */  	 
  	  		
  	  		horta_type		int(11)													,		/*** ϵͳ�������� **/	
  	  		horta_name		varchar(100)										,		/**   ϵͳ��������    **/
  	  		
  	  		horta_son_id		int(11)										,		/**   ���影��,�����˽�����ҳ����ʾ��˳��    **/
  	  		horta_son_name		varchar(100)									,		/**   ���影������    **/
  	  		
  	  		
  	  		vip_grade		varchar(10)													,		/**   ϵͳ��������֮, ��Ա�ȼ� ,�������û��Ҫ��ͳͳ������ ,���ݸ�ʽΪ  ,2,3,    **/
  	  		online_time		int(30)															,		/**   ϵͳ��������֮, ����ʱ��,����Ϊ��λ   **/
  	  		wj_grade			varchar(10)												    	,		/**   ϵͳ��������֮, ��ҵȼ�    **/
  	  		wj_sex			    Enum('0','1','2')										,		/**   ϵͳ��������֮, ����Ա�   **/
  	  		
  	  		wj_menpai		 Enum('0','1','2','3')										,		/**   ϵͳ��������֮, ������� 1������,2��ؤ��,3������  **/
  	  		wj_title			    varchar(100)									    	,		/**   ϵͳ��������֮, ��ҳƺ�, ����ж���ƺ�,��","�ָ�   **/
  	  		wj_credit			varchar(10)													,		/**   ϵͳ��������֮, �������,  ��"-"����, ���ж��,��";"����    **/
  	  		wj_next			varchar(10)										 		,		/**   ϵͳ��������֮, �������һ��   **/
  	  		
  	  		
  	  		is_only_one		Enum('0','1','2','3','4','5','6','7','8','9')		,		/** �Ƿ����ȡһ��, Ϊ1��ʾ����ȡһ��,Ϊ0��ʾ���Բ�ֹ��ȡһ�� */
  	  		onces				int(3) 														,		/**  һ��֮���������ȡ����  */
  	  		
  	  		
  	  		give_goods			varchar(200)												,		/**    ����װ������Ʒ, ��","�ָ�, �Ͷһ��˵�������������ͬ    ****/
  	  		isuseable			int(3)											default 1		,		/**   �Ƿ���Ч,Ϊ���ʾ��Ч,���ᱻ��ʾ����. Ϊ1�Ļ��������ʾ  **/
  	  		
  	  		horta_display		varchar(100)												,		/**   ��������  **/
  	 primary key(horta_id))  ENGINE=MyISAM;
  	 
/***********************************����NPC(menpainpc)***********************************/
  create table menpainpc(
    id                 int not null auto_increment                        ,/**����id*/
    p_type             int			                                      ,/**VIP����*/
    npc_lv             int                                                ,/**ʹ��ʱ�� Сʱ����*/
    npc_id             int                                                ,/**�̳��ۿ�*/
    scence_id          int                                                ,/**�ƺ�ID*/
	primary key(id)) ENGINE=MyISAM;
  	 
  	 
  	  /****************************��̨************************/
  	 CREATE TABLE `leitai` (                               
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,      
          `name` varchar(50) NOT NULL comment '��̨����',                        
          `des` text NOT NULL comment '��̨����' ,                                         
          `starttime` datetime DEFAULT NULL comment '��̨��ʼʱ��',                  
          `endtime` datetime DEFAULT NULL comment '��̨����ʱ��', 
          `scene_id` int(11) DEFAULT '0' comment '�������س���' ,
          `candead` int(11) DEFAULT '0' comment '������������',                      
          PRIMARY KEY (`id`)                                  
        )  ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;  
        
          	 /****************************���̨************************/
  	 CREATE TABLE `active_leitai` (                               
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,      
          `scene_id` int(11) DEFAULT '0' comment '���س���',
          `round1_starttime` datetime DEFAULT NULL comment '��1�ֿ�ʼʱ��',                  
          `round1_endtime` datetime DEFAULT NULL comment '��1�ֽ���ʱ��', 
          `round2_starttime` datetime DEFAULT NULL comment '��2�ֿ�ʼʱ��',                  
          `round2_endtime` datetime DEFAULT NULL comment '��2�ֽ���ʱ��', 
          `round3_starttime` datetime DEFAULT NULL comment '��3�ֿ�ʼʱ��',                  
          `round3_endtime` datetime DEFAULT NULL comment '��3�ֽ���ʱ��', 
           PRIMARY KEY (`id`)                                  
        )  ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;  
        
        
        /**ָ����*/
        CREATE TABLE `compass`(
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,      
          `scene_id` int(11) DEFAULT '0' comment '����',
          `des` varchar(255) not null comment 'ָ����',
            PRIMARY KEY (`id`)                                  
        )  ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;
        
         /**���̨*/
        CREATE TABLE `leitaiactive`(
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,     
         `into_scene` int(2) default 0 comment '���볡��',
         `ret_scene` int(2) default 0 comment '���س���ID',
         `max_peo` int(2) default 0 comment '������������',
         `shengwang30` int(2) default 0 comment '30-39����������ֵ',
          `shengwang40` int(2) default 0 comment '40-49����������ֵ',
          `shengwang50` int(2) default 0 comment '50-59����������ֵ',
          `shengwang60` int(2) default 0 comment '60-69����������ֵ',
          `shengwang70` int(2) default 0 comment '70-79����������ֵ',
          `shengwang80` int(2) default 0 comment '80-89����������ֵ',
          `max_dead` int(2) default 0 comment '������������ ',
          `baomingstarttime` varchar(20) DEFAULT NULL comment '������ʼʱ��',
          `starttime` varchar(20) DEFAULT NULL comment  '��ʼʱ��',                  
          `endtime` varchar(20) DEFAULT NULL comment '����ʱ��', 
          `overtime` int(2) default 0 comment '������೤ʱ���ڲ��ܽ���',
         PRIMARY KEY (`id`)            
        )ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;
        
      /**��ս��̨*/
        CREATE TABLE `leitaichallenge`(
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,    
          `lei_name` varchar(255) not null default '��̨', 
         `into_scene` int(2) default 0 comment '���볡��',
         `ret_scene` int(2) default 8 comment '���س���',
         `ppk` int(2) default 0 comment '����id',
         `name` varchar(255) default null comment '��������',
         `cppk` int(2) default 0 comment '��ս��id',
         `cName` varchar(255) default null comment '��ս������',
         `credit` int(2) default 0 comment '��ս��������',
         `time` datetime default null comment '��սʱ��',
         `onein` int(2) default 0 comment '�����Ƿ����',
         `twoin` int(2) default 0 comment '��ս���Ƿ����',
         `pkstate` int(2) default 0 comment 'PK״̬',
         PRIMARY KEY (`id`)            
        )ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;
        
     /**��ս��̨*/
        CREATE TABLE `battle`(
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,    
         `ret_scene` int(2) default 8 comment '���س���',
         `max_peo` int(2) default 50 comment '��౨������',
         `scene1` int(2) default 0 comment '����1',
         `scene2` int(2) default 0 comment '����2',
         `scene3` int(2) default 0 comment '����3',
         `scene4` int(2) default 0 comment '����4',
         `scene5` int(2) default 0 comment '����5',
         `scene6` int(2) default 0 comment '����6',
         `scene7` int(2) default 0 comment '����7',
         `scene8` int(2) default 0 comment '����8',
         `min_sheng` int(2) default 100 comment '��ս�������ֵ',
         `baomingtime` varchar(255) default null comment '������ʼʱ��',
         ��baomingendtime�� varchar(255) default null comment '��������ʱ��',
         `starttime1` varchar(255) default null comment '��һ����ʼʱ��',
          `endtime1` varchar(255) default null comment '��һ������ʱ��',
          `starttime2` varchar(255) default null comment '�ڶ�����ʼʱ��',
          `endtime2` varchar(255) default null comment '��������ʱ��',
          `starttime3` varchar(255) default null comment '��������ʼʱ��',
          `endtime3` varchar(255) default null comment '����������ʱ��',
          `starttime4` varchar(255) default null comment '���ĳ���ʼʱ��',
          `endtime4` varchar(255) default null comment '���ĳ�����ʱ��',
          `jiangli1` int(3) default 0 comment '��һ������',
          `jiangli2` int(3) default 0 comment '�ڶ�������',
         PRIMARY KEY (`id`)            
        )ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;    