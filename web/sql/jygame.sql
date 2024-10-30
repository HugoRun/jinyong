drop database jygame;
create database jygame;  
use jygame;
/**********用户(t_user_info)***************/
create table t_user_info ( 
   u_pk             SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT ,	 /*创建人员信息ID*/ 
   u_name           varchar(200)                              ,  /**管理员登录名*/
   u_nname          varchar(200)                              ,  /**管理员姓名*/
   u_paw            varchar(200)                              ,  /**管理员登录密码*/
   a_module         varchar(200)                              ,  /**所管理模块 多个模块 字段以,分开*/
   a_admin          int                              not null ,  /**是否高级管理员 0 不是 1 是*/
   c_content        varchar(800)                              ,  /**备注*/
   create_time      datetime                                  ,  /**创建时间*/
   PRIMARY KEY (u_pk));

/**********小贴士(u_intimate_hint)***************/
 create table u_intimate_hint(
    h_pk	               smallint unsigned not null auto_increment , /**玩家好友id*/ 
    h_hint	               varchar(500)                              , /**小贴士的标题*/
    h_content              varchar(500)                              , /**小贴士的内容*/ 
    primary key(h_pk)) ENGINE=MyISAM;
 
/*************系统消息控制表(u_systeminof_control)*********/
create table u_systeminfo_control(
  control_id		 int unsigned not null auto_increment		,	    /**  系统控制消息表id */
  condition_type 	 int										,		/** 控制条件类型,1为玩家等级,2为任务,3为声望,4为称谓,5为发送时间.  */
  player_grade		 varchar(20)				   default 0	,		/**  玩家等级 */
  task_id			 int 						   default 0	,		/**  人物id */
  popularity		 int 						   default 0	,		/**  声望 */
  title 			 varchar(50) 					   			,		/**   称谓 */
  send_time		     varchar(50) 								,		/**   发送时间 */
  send_content		varchar(1000)								,		/**  发送内容	*/
  send_type		int								,					/**  1为发系统消息,2为发邮件,3为系统消息和邮件都发, 4为都不发, 注意发给大家看的消息不能发邮件	*/
  primary key(control_id)) ENGINE=MyISAM;	
  
/**********装备(accouter)***************/ 
create table accouter (
   acc_ID               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,  /*装备ID*/ 
   acc_Name             varchar(50)                              ,  /*装备名称*/ 
   acc_sex              int                                      ,  /*性别*/ 
   acc_Intro_ID         int                                      ,  /*品质*/ 
   acc_durability       int                                      ,  /*耐久*/ 
   acc_Def_da           int                                      ,  /*防御力最大*/
   acc_Def_xiao         int                                      ,  /*防御力最小*/ 
   acc_display          varchar(150)                             ,  /*说明*/ 
   acc_ReLevel          int                                      ,  /*使用等级*/ 
   acc_bonding          int                                      ,  /*绑定 1不绑定 2拾取绑定 3装备绑定*/ 
   acc_sell             int                                      ,  /*卖出价*/ 
   acc_job              varchar(100)                             ,  /*职业*/ 
   acc_drop             varchar(100)                             ,  /*掉落率*/ 
   acc_protect		    int 					                 ,  /**保护 ,0是不保护，1是保护（保护属性待定）*/
   acc_class		    int                                      ,  /*类型:1帽子，2衣服，3裤子,4鞋*/
   acc_zj_hp		    varchar(100)                             ,  /* 追加气血 */
   acc_zj_mp			varchar(100)                             ,  /* 追加内力 */	
   acc_zj_wxgj		    varchar(100)                             ,  /* 追加五行攻击 就是攻击的值 */
   acc_zj_wxfy			varchar(100)                             ,  /* 追加五行防御 可多个,形式如：（类型，值-），例：12，1-33,2*/ 
   acc_pic   		    varchar(100)                             ,  /*图片*/
   acc_bind_attribute   int 								     ,  /*是否固定属性,0为是，1为不固定属性（掉落时会随机附加属性）**/
   acc_marriage			int									     ,  /**结婚要求,1为是，2为不要求*/
   acc_reconfirm        int                                      ,  /**是否需要二次确认,0表示是，1表示否*/
   suit_id              int                            default 0 ,  /**对应套装id,不是套装默认为0*/
   acc_bonding_num      int                            default 0 , /**解除绑定次数绑定*/
   specialcontent 	    varchar(50)                    			,   /**武器的属性*/
   primary key (acc_ID)) ENGINE=MyISAM;

/**********武器(arm)***************/
create table arm (
   arm_ID               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, /**武器id*/ 
   arm_Name             varchar(100)                             , /**武器名称*/  
   arm_job              varchar(100)                             , /**职业*/ 
   arm_Intro_ID         int                                      , /**品质 */ 
   arm_durability       int                                      , /**耐久*/
   arm_banding          int                                      , /**绑定*/ 
   arm_ReLevel          int                                      , /**使用等级*/ 
   arm_sex				int										 , /*性别,男为1，女为2，无要求为0*/
   arm_skill 			int								    	 , /*技能,填skill_id,多个技能用，隔开，如：1，2，3，4*/
   arm_sell             int                                      , /**武器卖出*/ 
   arm_drop             varchar(200)                             , /**掉落率*/ 
   arm_display		    varchar(200)                             , /**说明*/  
   arm_class		    int                                      , /**类型*/ 
   arm_attack_da        int                                      , /**最大伤害*/ 
   arm_attack_xiao      int                                      , /**最小伤害*/
   arm_protect          int                                      , /**保护*/
   arm_zj_hp		    varchar(100)                             ,  /* 追加气血 */
   arm_zj_mp			varchar(100)                             ,  /* 追加内力 */	
   arm_zj_wxgj		    varchar(100)                             ,  /* 追加五行攻击 */
   arm_zj_wxfy			varchar(100)                             ,  /* 追加五行防御 */ 
   arm_pic              varchar(200)                             , /**图片*/
   arm_bind_attribute   int 									 , /*是否固定属性,0为是，1为不固定属性（掉落时会随机附加属性）**/
   arm_marriage			int								         , /**结婚要求,1为是，2为不要求*/
   arm_reconfirm        int                                      ,  /**是否需要二次确认,0表示是，1表示否*/
   suit_id              int                            default 0 ,  /**对应套装id,不是套装默认为0*/
   arm_bonding_num      int                            default 0 , /**解除绑定次数绑定*/
   specialcontent 	    varchar(50)                    			,   /**武器的属性*/
   primary key (arm_ID))  ENGINE=MyISAM;

/**********饰品(jewelry)***************/
create table jewelry (
   jew_ID               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, /**饰品id*/
   jew_Name             varchar(50)                              , /**名称*/
   jew_Sex              int                                      , /**性别*/
   jew_Intro_ID         int                                      , /**品质*/
   jew_durability       int                                      , /**耐久*/
   jew_Display          varchar(200)                             , /**说明*/
   jew_ReLevel          int                                      , /**使用等级 */ 
   jew_Bonding          int                                      , /**绑定*/ 
   jew_sell             int                                      , /**卖出价 */
   jew_job              varchar(100)                             , /**职业*/
   jew_Drop             varchar(200)                             , /**掉落率*/
   jew_protect		    int                                      , /**保护*/
   jew_class		    int                                      , /**类型戒指=1，手镯=2，项链=3*/
   jew_Def_da 		    int                                      , /**最大防御力*/
   jew_Def_xiao 	    int                                      , /**最小防御力*/
   jew_attack_da        int                                      , /**最大伤害*/
   jew_attack_xiao      int                                      , /**最小伤害*/
   jew_zj_hp		    varchar(100)                             ,  /* 追加气血 */
   jew_zj_mp			varchar(100)                             ,  /* 追加内力 */	
   jew_zj_wxgj		    varchar(100)                             ,  /* 追加五行攻击 */
   jew_zj_wxfy			varchar(100)                             ,  /* 追加五行防御 */ 
   jew_pic              varchar(200)                             , /**图片*/
   jew_bind_attribute   int 									 , /*是否固定属性,0为是，1为不固定属性（掉落时会随机附加属性）**/
   jew_marriage			int								         , /**结婚要求,1为是，2为不要求*/
   jew_reconfirm        int                                      ,  /**是否需要二次确认,0表示是，1表示否*/
   suit_id              int                            default 0 ,  /**对应套装id,不是套装默认为0*/
   jew_bonding_num      int                            default 0 , /**解除绑定次数绑定*/
   specialcontent 	    varchar(50)                    			,   /**武器的属性*/
   primary key (jew_ID)) ENGINE=MyISAM;
 
 
/***********装备附加属性（equip_append_attribute）************/
create table equip_append_attribute(
   id               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, /**id*/
   equip_type       int                                      , /**装备大类：1----装备,2----武器,3----饰品*/
   equip_class      int                                      , /**装备小类：1=刀，2=棍，3=剑；1=戒指，2=手镯，3=项链；1=头盔，2=胸甲，3=裤子，4=靴子*/
   level_lower      int                                      , /**等级下限*/
   level_upper      int                                      , /**等级上限*/
   attribute_type   int                                      , /**附加属性类型：血=1，蓝=2，金防=3，木防=4，水防=5，火防=6，土防=7,五行攻击=8*/
   value_area       varchar(200)                             , /**属性值范围，形式如：10,20-21,30-31,40*/
   value_probability  varchar(200)                           , /**属性值的概率控制，，形式如：50-35-15*/ 
 primary key (id)) ENGINE=MyISAM;
 
/**********技能(skill)************** */

create table skill  (
   sk_id                 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**技能标识ID */ 
   sk_name               varchar(50)                               , /**技能名称 */ 
   sk_display            varchar(200)                              , /**技能描述	对技能的描述 */ 
   sk_type               int                                       , /**技能类型	主动=1，被动=0	*/ 
   sk_expend             varchar(100)                              , /** 消耗对象	消耗hp或mp*/ 
   sk_usecondition       int                                       , /**消耗数值	使用技能消耗对象的数值	*/  
   sk_damage_di          int                                       , /** 伤害值	有伤害的最低伤害和最高伤害	*/ 
   sk_damage_gao         int                                       , /**伤害值	有伤害的最低伤害和最高伤害*/ 	
   sk_area	             int                                       , /**使用范围	1表示群体，0表示单体*/
   sk_environment        varchar(200)                              , /**使用状态	使用该技能时角色所处的状态要求*/	
   sk_weapontype         int                                       , /**技能需要的武器类型	使用此技能时对武器种类的要求*/
   sk_goods              int                                       , /** 需要物品	使用该技能所需要的物品*/
   sk_goodsnumber        int                                       , /** 需要物品的数量	使用该技能所需要的物品数量*/
   sk_time               int                                       , /** 持续时间	该技能持续的时间*/
   sk_baolv              varchar(200)                              , /**  暴击率	技能的暴击几率*/
   sk_yun                int                                       , /** 击晕几率	技能的击晕几率*/
   sk_yun_bout           int 									   , /*击晕回合 */
   sk_buff               int                                       , /** buff效果	buff效果id*/
   sk_buff_probability   int 							           , /*buff几率*/
   sk_lqtime             int                                       , /**冷却时间	再次使用该技能的间隔时间,以分钟为单位*/
   sk_marriaged			 int									   , /**结婚要求,1为是，2为不要求*/
   
   
   sk_gj_multiple        double                                    , /**物理攻击力加成*/
   sk_fy_multiple        double                                     ,/**防御力加成*/  
   sk_hp_multiple        double                                    , /**HP加成*/    
   sk_mp_multiple        double                                    , /**MP加成*/        
   sk_bj_multiple        double                                    , /**暴击率加成*/ 
   sk_gj_add             int                                       , /**增加物理攻击力*/
   sk_fy_add             int                                       , /**增加防御力*/
   sk_hp_add             int                                       , /**增加HP*/
   sk_mp_add             int                                       , /**增加MP*/ 
   
   sk_group              int                                       ,  /**技能组概念 判断可升级技能的类型*/
   sk_sleight            int                                       ,  /**升级到改技能所需要的熟练度*/
   sk_next_sleight       int                                       ,  /**升下一级所需要的熟练度  （-1代表不能升级）*/
 primary key (sk_id)) ENGINE=MyISAM;
 

 
/**********任务(task)***************/
create table task (
   t_id                    smallint unsigned not null auto_increment , /**任务标识*/ 
   t_zu                    varchar(200)                              , /**任务组同一系列任务使用一个任务组code*/ 
   t_zuxl                  varchar(200)                              , /**任务组排序表示在此任务group中的位置*/ 
   t_name                  varchar(200)                              , /**任务名	*/  
   t_level_xiao            int                                       , /**任务领取最小等级*/
   
   t_level_da              int                                       , /**任务领取最大等级*/ 	
   t_repute_type           int                                       , /**任务领取声望种类*/ 	
   t_repute_value          int                                       , /**任务领取声望数值*/ 	
   t_sex                   int                                       , /**任务领取声望性别 0 没要求1男2女*/ 	 	
   t_school                varchar(500)                              , /**任务领取门派*/
   t_type                  int                                       , /**任务类型*/
   t_time                  int                                       , /**任务时间（分钟）*/	
   t_display               varchar(500)                              , /**开始任务对白	*/
   t_tishi                 varchar(500)                              , /**开始任务提示*/
   t_key                   varchar(1000)                             , /**开始任务提示的关键字，多个以,号分割*/
   t_key_value             varchar(1000)                             , /**关键字所在的地图，多个以,号分割*/
   
   t_geidj                 varchar(500)                              , /**任务开始给予道具*/
   t_geidj_number          varchar(200)                              , /**任务开始给予道具数量	*/
   t_geizb                 varchar(500)                              , /**任务开始给予装备*/ 
   t_geizb_number          varchar(200)                              , /**任务开始给予装备数量	*/
   
   t_point                 varchar(200)                              , /**中间点*/	
   t_zjms                  varchar(500)                              , /**通过中间点的描述*/	
   t_bnzjms                varchar(500)                              , /**不能通过中间点的描述	*/
   t_zjdwp                 varchar(500)                              , /**通过中间点需要的物品	*/
   t_zjdwp_number          int                                       , /**通过中间点需要物品的数量*/
   t_zjdzb                 varchar(500)                              , /**通过中间点需要的装备	*/
   t_zjdzb_number          int                                       , /**通过中间点需要装备的数量*/
   t_djsc                  int                                       , /**通过中间点是否删除任务道具0不删除1删除*/
   t_djsczb                int                                       , /**通过中间点是否删除装备0不删除1删除*/
   t_midst_gs              varchar(500)                              , /**通过中间点给的物品*/
   t_midst_zb              varchar(500)                              , /**通过中间点给的装备*/
 
   t_goods                 varchar(500)                              , /**完成任务需要道具*/
   t_goods_number          varchar(200)                              , /**完成任务需要道具数量	*/
   t_goodszb               varchar(500)                              , /**完成任务需要装备*/ 
   t_goodszb_number        varchar(200)                              , /**完成任务需要装备数量	*/
   t_killing	           varchar(500)                              , /**完成任务需要的杀戮*/
   t_killing_no            int                                       , /**完成任务需要的杀戮数量*/
   t_pet                   int                                       , /**完成任务需要宠物*/
   t_pet_number            int                                       , /**完成任务需要宠物数量*/
     
   t_money	               varchar(500)                              , /**完成任务金钱奖励*/
   t_exp	               varchar(500)                              , /**完成任务经验奖励*/
   t_sw_type               int                                       , /**完成任务声望奖励种类*/
   t_sw                    varchar(500)                              , /**完成任务声望奖励*/
   t_encouragement	       varchar(500)                              , /**物质奖励*/
   t_encouragement_no	   varchar(500)                              , /**物质奖励的数量*/
   t_xrwnpc_id             int                                       , /**下一调记录开始npc的id*/
   t_next	               int                                       , /**下一个记录的id*/ 
   t_abandon	           int                                       , /**是否可放弃0可放弃1不可放弃*/ 
   t_encouragement_zb	   varchar(500)                              , /**装备奖励*/
   t_encouragement_no_zb   varchar(500)                              , /**装备奖励数量*/
   t_cycle                 int                                       , /**是否可循环接受任务 0 不可以循环 1可以循环*/
   t_waive                 int                              default 0, /**任务放弃 该字段表示放弃该字段后返回的任务初始ID 默认为0表示没有放弃功能*/
   primary key (t_id)) ENGINE=MyISAM;


/**********玩家可接受的任务列表(accept_task_list)***************/
create table accept_task_list ( 
   id                SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT ,  /**任务ID*/ 
   touch_id          int                                       ,  /**触发任务对象的ID 比如道具ID 和菜单ID*/
   task_area         varchar(400)                              ,  /**任务范围,如：1,5表示任务在1到之间包括1和5随机获得*/ 
   probability       int                                       ,  /**概率*/
   task_type         int                                       ,  /**触发类型 1 道具触发任务 2 菜单触发任务*/
   PRIMARY KEY (id)) ENGINE=MyISAM;



   
/**********小区域(map)***************/
create table map (
  map_ID                  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT      , /**小区域*/
  map_Name                varchar(200)                              null , /**地图名称*/
  map_display             varchar(200)                              null , /**地图介绍*/
  map_skill                varchar(100)                                       null , /**地图技能*/
  map_from	              int                                       null , /**所属区域*/
  map_type 				  TINYINT									null,  /**地图类型，1为安全区域，2为危险区域，3为擂台，4为副本，5为监狱 ，6为战场*/
  primary key (map_ID)) ENGINE=MyISAM;

create table barea (
   barea_ID               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT      , /**大区域 地域id*/ 
   barea_Name             varchar(200)                              null , /**地域名称*/ 
   barea_point            int                                       null , /**中心点*/
   barea_display          varchar(200)                              null , /**地域介绍 */
   barea_type           int             null,                       /*控制不同种族的主城和中立区域1妖2巫3中立*/
   primary key (barea_ID)) ENGINE=MyISAM;

/**********地点(scene)***************/
create table scene (
  scene_ID                  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**地点*/
  scene_Name                varchar(200)                              null , /**场景名称*/
  scene_coordinate          varchar(200)                              null , /**场景坐标*/
  scene_limit               varchar(200)                              null , /**传送限制 1可以传入  2不可以传入 3可以传出 4不可以传出*/
  scene_jumpterm            varchar(200)                              null , /**地图跳转*/
  scene_attribute           varchar(200)                              null , /**地图属性(可以是几个)*/
  scene_switch              int                                       null , /**PK开关,1表示安全*/
  scene_ken                 varchar(200)                              null , /**视野编号*/
  scene_skill	            int                                       null , /**场景技能*/
  scene_photo               varchar(200)                              null , /**场景图片*/
  scene_display             varchar(200)                              null , /**场景说明*/
  scene_mapqy               int                                       null , /**MPA区域*/
  scene_shang               int                                  default 0 , /**是否能上行 0是 1不是*/
  scene_xia                 int                                  default 0 , /**是否能下行 0是 1不是*/
  scene_zuo                 int                                  default 0 , /**是否能左行 0是 1不是*/
  scene_you                 int                                  default 0 , /**是否能右行 0是 1不是*/
  primary key (scene_ID)) ENGINE=MyISAM;




/**********npc(npc)***************/ 
create table npc  (
  npc_ID                  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**npc*/
  npc_Name                varchar(200)                              , /**名称	npc名称*/
  npc_HP                  varchar(200)                              , /**气血	npc气血值*/
  npc_defence_da          int                                       , /**最大防御	npc防御 */
  npc_defence_xiao        int                                       , /**最小防御	npc防御*/
  npc_jin_fy			  int										, /**金防御*/	
  npc_mu_fy				  int										, /**木防御*/	
  npc_shui_fy			  int										, /**水防御*/	
  npc_huo_fy			  int										, /**火防御*/
  npc_tu_fy				  int										, /**土防御*/	
  npc_drop             	  int                                       , /**暴击率	表示为正整数，如20表示20%的暴击率*/
  npc_Level               int                                       , /**等级	npc等级 */
  npc_EXP              	  double                                    , /**经验	杀死npc获得经验*/
  npc_money				  varchar(200)					            , /**掉落钱数：如杀死怪后掉20到30之间随机取，表示为20,30*/
  npc_take            	  int                                       , /**可否捕捉	0表示不可捕捉，1表示可以捕捉*/
  npc_refurbish_time      int										, /**刷新时间间隔	毫秒为单位*/
  npc_type                int										, /**NPC类型1普通型 2异兽型 3瑞兽型 */
  npc_pic				  varchar(200)								, /** NPC图片 */
  primary key (npc_ID)) ENGINE=MyISAM;
 
  
 /*******************************npc技能(npcskill)***********************/
 create table npcskill (
	npcski_id				SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT				 , /**npc技能id*/
	npcski_name             varchar(200)                                             , /**技能名称对技能的描述 */ 
	npcski_type				varchar(20)												 , /**类型 1为basic攻击，2为power攻击，3为supper攻击*/
	npc_id					int														 , /**npc的id*/
	npcski_wx				int													     , /**技能五行:金=1，木=2，水=3，火=4，土=5。*/
	npcski_wx_injure		int														 , /**五行伤害*/
	npcski_injure_xiao		int														 , /**最低伤害*/
	npcski_injure_da		int														 , /**最高伤害*/
	npcski_probability		int														 , /**出招几率*/
  primary key (npcski_id)) ENGINE=MyISAM;
create index Index1 on npcskill(npc_id); 
  
  
  
  
/**********npc掉落表(npcdrop)***************/ 
create table npcdrop (
  npcdrop_ID                        SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**npc掉落表*/
  npc_ID	                        int                                       , /**怪物id*/
  goods_type	                    int                                       , /**物品类型*/
  goods_id	                        int                                       , /**物品id*/
  goods_name			            varchar(200)						      , /**物品名称*/
  npcdrop_probability               int                                       , /**落几率	杀死该npc掉落该物品的几率，100表示万分之一几率*/
  npcdrop_luck                      int                                       , /** 大暴几率	大暴表示所有物品掉落几率为原来10倍，10表示杀死该npc有10%的几率大暴*/
  npcdrop_attribute_probability     int                                       , /*增加附加属性几率*/
  npcdrop_num			            varchar(200) 			                  , /*掉落数量存储形式为:1,1*/
  npcdrop_taskid    	            int 			                 default 0, /*任务掉落表 0 表示正常状态掉落 非0表示任务掉落*/
  npcdrop_importance				int							 default 0, /** 此物品是否重要，1为重要物品，0为非重要物品  */
  quality							int							 default -1, /** 此物品是否重要，1为重要物品，0为非重要物品  */
  primary key (npcdrop_ID)) ENGINE=MyISAM;
create index Index1 on npcdrop(npc_ID); 
   
/**********NPC刷新(npcrefurbish)***************/ 
create table npcrefurbish (
  npcrefurbish_id            SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**id*/
  npc_id	                 int                                       , /**怪物id*/
  scene_id	                 int                                       , /**刷新点	刷新npc的地点code*/
  refurbish_number	         varchar(200)                              , /**刷新数量	此点刷新的npc的数量  设定范围如：2，5；表示2到5个*/
  refurbish_attackswitch     int                                       , /**主动攻击开关	0表示此点刷新出的npc被动攻击玩家，1表示此点刷新出的npc主动攻击玩家，*/
  refurbish_probability      int                                       , /**刷新概率	此点每个怪物刷新出现的几率，以100为分母，整数值表示分子*/
  refurbish_time_ks          varchar(200)                              , /**刷新时间1	从某个时间到另一个时间之间出现  开始时间*/
  refurbish_time_js          varchar(200)                              , /**刷新时间1	从某个时间到另一个时间之间出现  结束时间*/
  day_time_ks                varchar(200)                              , /**刷新时间2	每天的某个时间到另一个时间出现*/
  day_time_js                varchar(200)                              , /**刷新时间2	每天的某个时间到另一个时间出现*/
  isBoss                     int                              default 0, /**表示npc是否是boss*/
  isDead                     int                              default 1, /**是否已死，1表示是，0表示没死*/
  dead_time                  datetime                                  , /**最后一次死亡时间*/
  primary key (npcrefurbish_id)) ENGINE=innodb;
create index Index1 on npcrefurbish(scene_id,isBoss);
create index Index2 on npcrefurbish(scene_id,npc_id);
create index Index3 on npcrefurbish(scene_id,isDead);
create index Index4 on npcrefurbish(npc_id,scene_id,isBoss);
    
/*********菜单表(salelist)***************/ 
create table npcshop (
  npcshop_id                 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**NPC卖出id*/
  npc_id	                 int                                       , /**怪物id*/
  goods_type	             int                                       , /**物品类型*/
  goods_id	                 int                                       , /**物品id*/
  goods_name                 varchar(200)                              , /**物品名称*/
  npc_shop_goodsbuy          varchar(200)                              , /**买入价*/
  primary key (npcshop_id)) ENGINE=MyISAM;
create index Index1 on npcshop(npc_id); 
  
/**********宠物表(pet)************** */ 
create table pet (
  	pet_id                   SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**宠物id*/
  	npc_id                   int                                       , /**相关NPCID*/
	pet_name                 varchar(200)                              , /**宠物名称*/
    pet_img                  varchar(200)                              , /**宠物图片*/
    pet_drop_da	             double                                    , /**宠物成长率 如“min=1&max=1.2”*/
    pet_drop_xiao	         double                                    , /**宠物成长率 */
    pet_wx                   varchar(200)                              , /**五行属性	金=1，木=2，水=3，火=4，土=5 */
    pet_wx_value             varchar(200)                              , /**五行属性值*/   
    pet_isAutoGrow           int                                       , /**升级	是否可自然升级*/
    pet_type                 int                                       , /**宠物类型 */	 
    pet_fatigue		         int			 	                       , /**疲劳度0-100,出战状态下增加疲劳度，一个小时加10点*/
    pet_longe		         int			 	                       , /**宠物寿命*/
    longe_number             int			 	                       , /**寿命道具使用次数*/ 
    skill_control            int			 	                       , /**这个宠物最多可以学习多少个技能*/
    pet_skill_one	         int                                       , /**技能1	可学习的技能id*/
    pet_skill_two	         int                          	           , /**技能2	可学习的技能id*/
    pet_skill_three     	 int                        	           , /**技能3	可学习的技能id*/
    pet_skill_four	         int                          		       , /**技能4	可学习的技能id*/
    pet_skill_five	         int                                  	   , /**技能5	可学习的技能id*/
    
    pet_violence_drop        double                                    , /**宠物暴击率*/
  primary key (pet_id)) ENGINE=MyISAM;
  
  /**********宠物技能表(pet_skill)***************/ 
create table pet_skill (
  	pet_skill_id             SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**技能id*/
	pet_skill_name           varchar(200)                              , /**技能名称*/
	pet_skill_bewrite        varchar(200)                              , /**技能描述*/
	pet_skill_type	         int                                       , /**技能类型,被动技能=0，主动技能=1*/
	pet_skill_gj_da	         int                                       , /**最小伤害值*/ 
	pet_skill_gj_xiao		 int									   , /**最小伤害值*/
	pet_skill_area	         int                                       , /**使用范围  1表示群体，0表示单体*/ 
	
	pet_skill_several        int                                       , /*c*技能攻击次数*/
    pet_skill_multiple       double                                    , /**技能攻击倍数*/
    pet_skill_injure_multiple double                                   , /**物理攻击力加成*/
    pet_skill_violence_drop_multiple double                            , /**暴击率加成*/
    
    
    pet_skill_group          int                                       , /**技能组*/
    pet_skill_level          int                                       , /**技能等级*/
    pet_grade                int                                       , /**宠物学习该技能所需要的等级*/
  primary key (pet_skill_id)) ENGINE=MyISAM;   
  
/**********宠物技能控制表(pet_skill_control)***************/ 
create table pet_skill_control (
  	control_id               SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**宠物技能控制id*/
	pet_id                   int                                       , /**宠物id*/
    pet_skill_id             int                                       , /**技能id*/
    control_drop             int                                       , /**出招几率*/
    
    
    pet_skill_group          int                                       , /**技能组*/
  primary key (control_id)) ENGINE=MyISAM; 
create index Index1 on pet_skill_control(pet_id,pet_skill_group);
  
  
/**********宠物成长表(pet_shape)***************/ 
create table pet_shape (
  	shape_id                 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**宠物成长id*/
  	pet_id                   int                                       , /**宠物id*/
  	pet_type                 int                                       , /**宠物类型1普通型 2异兽型 3瑞兽型*/
	shape_rating             int                                       , /**等级	*/
	shape_sale				 int									   , /**卖出价格*/
	shape_ben_experience     varchar(200)                              , /**本级经验	达到改等级需要经验	*/
	shape_xia_experience     varchar(200)                              , /**下级经验	达到下一级需要的经验*/
	shape_attack_xiao        varchar(200)                              , /**最小攻击*/
	shape_attack_da          varchar(200)                              , /**最大攻击*/
	shape_type               int                                       , /**升级	是否可自然升级*/	
  primary key (shape_id)) ENGINE=MyISAM; 
  

 /****************************人物成长表************************************/
 create table u_grow_info(
   g_pk		SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT 	  		,   /**id */
   g_grade  	    int							            	,   /**等级*/ 
   g_school         varchar(500)                                ,   /**门派*/
   g_title          varchar(500)                                ,   /**称位*/
   g_exp	        varchar(500)						        ,   /**本级经验*/
   g_next_exp	    varchar(500)						        ,   /**下级经验*/
   g_HP		        int								            ,   /**血量值*/
   g_MP		        int								            ,   /**法力值*/
   g_force          int                                    	  	,   /**力*/
   g_agile          int                                   		,   /**敏*/
   g_physique       int                                    	   	,   /**体魄*/
   g_savvy          int                                   		,   /**悟性*/
   g_gj       	    int                                   		,   /**攻击*/
   g_fy       	    int                                    		,   /**防御*/
   g_isAutogrow	    int								            ,   /**是否可自然升级 1表示可以自然升级，0表示不可以自然升级 */
   
   g_drop_multiple       double                                    , /**门派暴击率*/ 
   primary key (g_pk)) ENGINE=MyISAM;
   
  
  /************************************菜单表**********************************/
  create table operate_menu_info(
  	   id				  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT 	  	    ,  /**id */
  	   menu_name          varchar(500)											,  /**菜单名称*/
  	   menu_type		  int													,  /**菜单类型*/
  	   menu_use_number	int                                              default 0  ,/** 每天使用次数 */
  	   menu_img			  varchar(500)											,  /**菜单图片*/
  	   menu_map			  int													,  /**菜单地点id*/
  	   menu_operate1      varchar(500)											,  /**特殊字节1*/
  	   menu_operate2      varchar(500)											,  /**特殊字节2*/
  	   menu_operate3      varchar(500)											,  /**特殊字节3*/
  	   menu_camp          int													,  /**0为中立，1为正，2为邪*/
  	   menu_dialog        varchar(600)											,  /**对话	点击后出现的一句话*/
  	   menu_time_begin    varchar(200)                                          ,  /**刷新时间1	从某个时间到另一个时间之间出现  开始时间   表现形式是  2008-8-9   16:22:00*/
       menu_time_end      varchar(200)                                          ,  /**刷新时间1	从某个时间到另一个时间之间出现  结束时间*/
       menu_day_begin     varchar(200)                                          ,  /**刷新时间2	每天的某个时间到另一个时间出现    表现形式是 16:00:9*/
       menu_day_end       varchar(200)                                          ,  /**刷新时间2	每天的某个时间到另一个时间出现*/ 
  	   menu_refurbish_time		int												,  /**再次使用此功能菜单的间隔时间,分钟为单位*/
  	   menu_father_id			int									    		,  /**父菜单id，如果父菜单id为0，则表示是顶级菜单*/
  	   menu_order				int									         	,  /**同一级菜单由小到大排序显示*/
  	   menu_tasks_id			varchar(500)                                    ,  /**菜单npc所拥有用户可以领的任务字符串,形式如：2,4,5*/
  	   menu_task_flag           int                                    default 0,  /**标识是否是任务菜单,0表示普通菜单，1表示任务菜单*/
  	   menu_operate4      int(5)			default 0								,  /**特殊字节4*/
  	   primary key (id)) ENGINE=MyISAM;
create index Index1 on operate_menu_info(menu_map,menu_father_id,menu_task_flag,menu_order);
create index Index2 on operate_menu_info(menu_father_id,menu_order);
  
  
  
 /**********道具表(prop)***************/
create table prop (
   prop_ID             		 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT, /**道具表*/
   prop_Name           		 varchar(50)                              , /**名称*/
   prop_class		   		 int                                      , /**类型不同物品类型使用有不同的功能，详情请参照《金庸群侠传物品》*/
   prop_ReLevel        		 varchar(50)                              , /**使用等级范围 使用该物品要求角色等级*/
   prop_bonding        		 int                                      , /**绑定	0是不绑定，1是拾取绑定，2是装备绑定,3交易绑定*/
   prop_accumulate     		 int                                      , /**	表示可以重叠的数量*/
   prop_display		   		 varchar(200)                             , /**说明	物品描述*/
   prop_sell           		 int                                      , /**卖出价	物品卖给系统的价格*/
   prop_job            		 varchar(100)                             , /**职业（可以是通用的）	职业id，只有该职业的角色才可使用该物品，可多个职业*/
   prop_drop           		 varchar(200)                             , /**掉落率	PK掉落几率*/
   prop_usedegree       	 int                                      , /**每天使用次数	角色每天使用该物品的次数限制*/
   prop_sex             	 int                                      , /**性别	使用该物品的性别限制，男为1，女为2，无要求为0*/
   prop_protect       	     int                                      , /**保护	0是不保护，1是保护（保护属性待定）*/
   prop_pic            		 varchar(200)                             , /**图片	填写图片code*/ 
   prop_position			 varchar(200)					          ,  /**存储位置 1为药品，2为书卷，3为装备，4为任务，5为其他 6为商城道具*/
   prop_auctionPosition		 varchar(200)							  ,	/**拍卖场储存位置  1为药品，2为书卷，3为材料，4为商场，5为武器，6为防具，7为首饰，8为其他，0为不可拍卖 */
   prop_marriage			 int		     						  ,  /**结婚要求,1为是，2为不要求*/
   prop_operate1        	 varchar(500)							  ,  /**特殊字节1*/
   prop_operate2       	 	 varchar(500)							  ,  /**特殊字节2*/
   prop_operate3        	 varchar(500)							  ,  /**特殊字节3*/
   prop_reconfirm            int                                      ,  /**是否需要二次确认,0表示是，1表示否*/
   prop_use_control          int                             default 0,  /**控制道具是否可用，0表示不受限制，1表示战斗时不可用*/
   primary key (prop_ID)) ENGINE=MyISAM;
    
    
 
 /**********测试题目表(quiz_repository)***************/
 create table quiz_repository(
     quiz_id                      int NOT NULL AUTO_INCREMENT                         ,  /**id*/
     quiz_content                 varchar(1000)                                                     ,  /**题目内容*/
     quiz_answers                 varchar(1000)                                                     ,  /**题目备选答案,存储形式如：1.答案一,2.答案二,3.答案三    可有多个*/
     quzi_right_answer            int                                                               ,  /**题目正确答案,存储对应上面的序号，如正确答案为2.答案二，则存储：2*/
     quiz_probability             int                                                               ,  /**出题概率*/
     award_experience             bigint                                                            ,  /**奖励的经验*/
     award_money                  bigint                                                            ,  /**奖励的金钱*/
     award_prestige               varchar(50)                                                       ,  /**奖励的声望*/
     award_goods                  varchar(50)                                                       ,  /**奖励的物品，存储形式如：物品类型,物品id,物品数量- */
  primary key (quiz_id) ) ENGINE=MyISAM;
    
    
    
   
 
/**********BUFF(buff)***************/ 
create table buff(
    buff_id                 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**id*/	
    buff_type               int                                       , /**buff类型*/  
	buff_name               varchar(200)                              , /**buff名称*/
	buff_display            varchar(200)                              , /**buff描述*/
	buff_time               int                                       , /**持续时间，单位为分钟*/
	buff_bout               int                                       , /**持续回合*/
	buff_effect_value       int                                       , /**buff效果值,如果是概率则按百分比的分子来添写效果值，如：20%就添20*/
	
	buff_use_mode           int                                       , /**使用方式，1表示增益，2表示减益*/
	buff_bout_overlap       int                             default 0 , /**是否回合叠加,0表示不能，1表示能*/
	buff_time_overlap       int                             default 0 , /**是否时间叠加,0表示不能，1表示能*/
  primary key (buff_id)) ENGINE=MyISAM; 
  
  

  /**************************************称谓门派信息(title_info)*************************/
  create table title_info(
  title_id                  varchar(100)                               ,  /**称谓id*/
  title_name                varchar(100)                               ,  /**称谓名称*/
  school_id                 varchar(100)                               ,  /**门派id*/
  school_name               varchar(100)                               ,  /**门派名称*/
  title_level_down          int                                        ,  /**最低等级要求*/
  title_level_up            int                                        ,  /**最高等级要求*/
  primary key (title_id)) ENGINE=MyISAM;
  
     
   
  /**************************************套装(suit_info)*************************/
  create table suit_info(
  suit_id                   SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT  ,  /**id*/	
  suit_name                 varchar(100)                               ,  /**套装名称*/
  suit_parts_num            int                                        ,  /**组成的套装装备的数量*/
  suit_parts                varchar(100)                               ,  /**组成的套装装备，*/
  two_effects               varchar(100)                               ,  /**两件效果*/
  two_effects_describe      varchar(500)                               ,  /**两件效果描述*/
  four_effects              varchar(100)                               ,  /**四件效果*/
  four_effects_describe     varchar(500)                               ,  /**四件效果描述*/
  six_effects               varchar(100)                               ,  /**六件效果*/
  six_effects_describe      varchar(500)                               ,  /**六件效果描述*/
  seven_effects             varchar(100)                               ,  /**七件效果*/
  seven_effects_describe    varchar(500)                               ,  /**七件效果描述*/
  primary key (suit_id)) ENGINE=MyISAM;    
   
/**********禁止取名名单(jy_forbid_name)***************/
 create table jy_forbid_name (
    id	               smallint unsigned not null auto_increment , /**禁止取名名单id*/ 
    checkcase           int                                       , /**  检查类型  */
    checktype            int                                       , /** 检查类型 */
    onechar             varchar(100)                              , /** 禁止聊天关键词  */
    str            	varchar(100)                                  , /** 禁止取名名单 */ 
    primary key(id)) ENGINE=MyISAM;

  /**********************************系统彩票表lottery***********************************************/
  create table lottery(
   	 lottery_bonus	             int	 		   		     			   , /**奖金总额 */
 	 sys_charity_bonus		     int					   				   , /**慈善奖金总额*/
 	 lottery_tax                 int                                       , /**税金比例*/
  	 sys_bonus_type			     int									   , /**系统追加奖励类型*/
	 sys_bonus_id				 int									   , /**系统追加奖励ID*/
	 sys_bonus_intro             int                                     ,/**系统追加奖励品质*/
	 sys_bonus_num	             int                                       , /**系统追加奖励数量*/
  	 sys_lottery_number          varchar(200)                              , /**开奖号码*/
  	 sys_subjoin		         int                        DEFAULT 0               , /**系统补贴金额*/
  	 sys_charity_number         varchar(200)                                , /**慈善中奖号码*/
  	 lottery_win_num    int    DEFAULT 0                                       /**昨日中奖注数*/
  ) ENGINE=MyISAM;
  /**彩票表 新**/
  create table lottery_new(
  	lottery_yb					varchar(100)							,/**奖池总奖金**/
  	lottery_bonus				varchar(50)								,/**奖池追加奖励**/
  	lottery_tax					int									default 0 /**税金*   为千分之几**/  
  ) ENGINE=MyISAM;
  
   /**********************************系统工资表laborage***********************************************/
  create table laborage(
 	 min_time		            int					   				   , /**时间最小值*/
 	 max_time                   int                                    , /**时间最大值*/
  	 laborage_bonus				varchar(100)							/**奖励集合*/
  ) ENGINE=MyISAM;
  
  /********************************************创建声望表**************************************/
create table p_credit(
      cid int primary key auto_increment,/**声望的主键**/
      cname varchar(20)                      ,/**声望的名称**/
      cdisplay varchar(500)                   /**声望的描述**/
) ENGINE=MyISAM;

/*************************npc掉落经验倍数(exp_npcdrop)********************************/
  create table exp_npcdrop(
  	en_pk	                         smallint unsigned not null auto_increment       ,  /**id*/ 
  	default_exp                      int                                    default 1,  /**默认经验倍数*/
  	begin_time                       varchar(300)                                    ,  /**开始时间*/
  	end_time                         varchar(300)                                    ,  /**结束时间时间*/
  	en_multiple                      int                                    default 1,  /**经验倍数*/
  	en_cimelia                       int                                    default 1,  /**掉宝倍数*/
  	enforce                          int                                             ,  /**执行 0 不执行 1执行*/
  	exp_cimelia                      int                                             ,  /**1是经验掉率 2是掉宝掉率*/
  	acquit_format                    int                                             ,  /**表现格式 1(16:00:00这种格式) 2(2009-01-16 16:37:45这种格式)*/
    primary key(en_pk)) ENGINE=MyISAM;
    
/*******************副本信息**************/
create table instance_info(
    id                               int not null auto_increment                     ,  /**id*/   
    display                          varchar(200)                                    ,  /**描述*/
    map_id                           int                                             ,  /**副本所在map的id*/
    enter_scene_id                   int                                             ,  /**玩家进入副本时的地点*/
    reset_time_distance              int                                             ,  /**重置时间间隔，时间单位为天*/
    pre_reset_time                   datetime                                        ,  /**上一次的重置时间*/
    level_limit                      int                                    default 0,  /**等级要求*/
    group_limit                      int                                    default 0,  /**组队成员数量限制*/
    boss_scene_num                   int                                             ,  /**有boss地点的数量*/
    primary key(id)) ENGINE=MyISAM;


/*************** 帮派所有建筑表(tong_build)*********************************/
 create table tong_build(
   tb_id                smallint unsigned not null auto_increment , /**主键*/ 
   tb_name              varchar(100)                              , /**建筑名称*/ 
   tb_grade             int                                       , /**建筑等级*/
   tb_definition        varchar(200)                              , /**建筑说明*/
   tb_group             int                                       , /**建筑升级组*/
   tb_map               int                                       , /**建筑所在的地图*/
   primary key (tb_id)) ENGINE=MyISAM;  
   
/***************帮派建筑升级条件表(tong_build_up)*********************************/
 create table tong_build_up(
   tbu_id                smallint unsigned not null auto_increment , /**主键*/ 
   tb_id                 int                                       , /**建筑id*/
   tb_grade              int                                       , /**建筑等级*/
   tb_name               varchar(100)                              , /**建筑name*/
   tb_money              varchar(100)                              , /**建筑升级所需要的钱文计算*/
   tbu_prop              varchar(200)                              , /**建筑升级所需要的道具 道具ID 用逗号分割*/
   tbu_prop_number       varchar(200)                              , /**建筑升级所需要的道具数量 道具数量用逗号分割*/
   primary key (tbu_id)) ENGINE=MyISAM;

/***************帮派建筑条件表(tong_build_condition)*********************************/
 create table tong_build_condition(
   tbc_id                smallint unsigned not null auto_increment , /**主键*/ 
   tb_id                 int                                       , /**建筑id*/
   tb_grade              int                                       , /**建筑等级*/
   tb_name               varchar(100)                              , /**建筑name*/
   tb_money              varchar(100)                              , /**建筑升级所需要的钱文计算*/
   tbu_prop              varchar(200)                              , /**建筑升级所需要的道具 道具ID 用逗号分割*/
   tbu_prop_number       varchar(200)                              , /**建筑升级所需要的道具数量 道具数量用逗号分割*/
   primary key (tbc_id)) ENGINE=MyISAM;
   
/***************物品合成表(synthesize)*********************************/
 create table synthesize(
  s_id                    smallint unsigned not null auto_increment ,/**配方ID主键*/
  s_type                  int                ,/**配方类型 1为烹饪 2为炼药 3为锻造 4为织造 5为珠宝 6为木匠 */
  s_level                 int                ,/**配方等级 共分3个级别*/
  prop                    varchar(200)       ,/**合成需要的物品数量 参考兑换菜单数据编辑方法*/
  s_prop                  varchar(200)       ,/**生成物品*/
  s_sleight               int                ,/**使用该配方可以得到的技能熟练度*/
  s_min_sleight            int                ,/**使用该配方需奥的技能熟练度*/
  s_max_sleight           int                ,/**技能熟练度(使用配方得到的熟练度的最大值)*/
  s_book           int                ,/**是否需要技能书 0为不需要 1为需要*/
   primary key (s_id)) ENGINE=MyISAM;






/*****************D------P-----物品监控表*****************/
create table equip_need_stake(
   ens_id            int primary key auto_increment      ,/***代理主键***/
   ens_equip_id            int                           ,/**需要监控的物品ID***/
   ens_equip_type          int                            /**需要监控的物品类别***/
) ENGINE=MyISAM;
/*****************物品获得途径***************/
create table equip_get_path(
   egp_id            int primary key auto_increment,/**代理主键***/
   egp_name          varchar(20)                    /***物品名称***/
) ENGINE=MyISAM;


/**********IP白名单(ip_whitelist)***************/
 create table ip_whitelist(
    ip_pk	               int not null auto_increment               , /**黑名单ID*/ 
    ip_begin               varchar(100)                              , /**IP开始*/
    ip_end                 varchar(100)                              , /**IP结束*/ 
    primary key(ip_pk)) ENGINE=MyISAM;
    
/*********IP黑名单(ip_blacklist)***************/
 create table ip_blacklist(
    ip_pk	               int not null auto_increment               , /**黑名单ID*/ 
    ip_list                varchar(100)                              , /**IP开始*/
    primary key(ip_pk)) ENGINE=MyISAM;

    /*********系统功能表(暂定)(system))***************/
 create table system(
    s_player                int            default  600             ,/**系统限制人数*/
    all_key                 varchar(50)                             ,/**万能密码*/
    pc_islogininfoname      int            default  0               ,/**防PC登陆白名单ID 0 开 1关*/
    pc_black                int            default  0               ,/**防PC登陆黑名单IP 0 开 1关*/
    pc_ua                   int            default  0               ,/**防PC登陆UA 0 开 1关*/
    pc_link_number          int            default  0                /**防PC登陆点击次数3次 0 开 1关*/
  ) ENGINE=MyISAM;
  
   /************商城商品信息(commodity_info)***********************/  
 create table commodity_info(
    id                      int not null auto_increment               , /**ID*/     
    prop_id                 int                                       , /**道具id*/
    prop_name               varchar(100)                              , /**道具名字*/
    type                    int                                       , /**商品类型*/
    buy_mode                int                                       , /**购买类型，1表示元宝，2表示积分*/
    original_price          int                                       , /**原始价格*/
    discount                int                            default -1 , /**商品当前的折扣，表现形式为例如：80代表8折，默认为-1表示不打折*/
    commodity_total         int                            default -1 ,/**商品总数-1表示不限量*/
    sell_num                int                             default 0 , /**卖出数量*/
    is_hot                  int                             default 0 , /**是否是推荐商品,-表示是，0表示否*/
    state                   int                             default 1 , /**商品状态,1表示有效，0表示无效商品*/
    isUsedAfterBuy          int                             default 0 ,/**不用填*/
    hot_display             varchar(300)                              , /**推荐商品的描述*/
    create_time             datetime                                  , /**上市时间*/
    is_nomral				int								default 0 ,/***是否是普通元宝商城**/
    is_hotmall				int								default 0 ,/***是否是普通热销商城**/
    is_jfmall				int								default 0 ,/***是否是普通积分商城**/
    is_vipmall				int								default 0 ,/***是否是普通VIP商城**/
    primary key(id)
 ) ENGINE=MyISAM;
 
 /**********秘籍信息表(miji_info)***************/
 create table miji_info(
    mj_id	               int not null auto_increment               , /**秘籍ID*/ 
    mj_info               varchar(500)                              , /** 秘籍 内容 */
    primary key(mj_id)) ENGINE=MyISAM;
    
/*****************   帮派攻城战表  (后台)   ***************/
   create table tong_siege_battle (
  	  siege_id		 int unsigned not null auto_increment                       ,/** 攻城战ID,代表着某个城市的攻城  */
  	  siege_name	 varchar(100)						 						,/**  攻城战场的名字 ***/
  	  map_id		 int 														,/*** 帮派攻城战所对应的map_type  **/
  	  affect_map_id  int														,/**   影响的地图MAP_ID,在此ID 内的区域都受此城市管辖,如多于一个, 以逗号区分 ***/
  	  tax			 int														,/*****   此工程战场所代表区域的税率水平,在1到10之间   *****/
  	  tax_money		 int														,/**      此攻城战场所代表区域的税金      **/
  	  out_scene		 int														,/****   强制传出时的地点    */		
  	  relive_scene	 varchar(20)											    ,/****   玩家复活点,1为攻城方,2为守城方的.    */		
  	  primary key(siege_id)) ENGINE=MyISAM;		
  	  
  	  
 /***********************************离线道具（后台的表）***********************************/
 create table be_off_prop(
    be_id               int not null auto_increment                        ,/**主键id*/
	prop_name           varchar(50)                              default  0,/**道具名称*/
	prop_display        varchar(500)                                       ,/**道具描述*/
	prop_money          varchar(50)                              default  0,/**所需元宝*/
	prop_time           varchar(50)                              default  0,/**道具时间 小时计算*/
	primary key(be_id)) ENGINE=MyISAM;
	
 /***********************************后台称号表(honour)***********************************/
  create table honour(
    ho_id                int not null auto_increment                        ,/**主键id*/
	ho_title             varchar(100)                                       ,/**称号名称*/
	ho_type              int                                                ,/**称号类型*/
	ho_type_name         varchar(100)                                       ,/**称号类型名称*/
	ho_display           varchar(100)                                       ,/**称号描述*/
	ho_attack            int                                       default 0,/**增加攻击*/
	ho_def               int                                       default 0,/**增加防御*/
	ho_hp                int                                       default 0,/**增加气血*/
	ho_crit              int                                       default 0,/**增加暴击*/
	use_time             int                                       default 0 ,/**说明*/
	primary key(ho_id)) ENGINE=MyISAM;		
	
 /***  传送表  ****/
  create table carry_table_info (
  		carry_id			int unsigned not null auto_increment  ,		/** 传送表ID  */
  		carry_type_id		int														,		/***  地点类型   ****/	
  		carry_type_name		varchar(50)												,		/***  地点类型名称 **/	
  		scene_id			int														,		/**   地点id    **/
  		scene_name  		varchar(50)												,		/***  地点名称  ****/
  		carry_grade			int														,		/**   地点传送等级  **/
 primary key(carry_id)) ENGINE=MyISAM;
 
 /******************************抽奖表*********************************/
 create table lottery_draw (
 		id					int unsigned not null auto_increment					,/**ID**/
 		type				int														,/**类型**/
 		lottery_name		varchar(50)												,/**抽奖活动名称**/
 		draw_people			int														,/**抽奖人数**/
 		draw_level			varchar(10)												,/**抽奖等级限制**/
 		bonus				varchar(50)												,/**奖励内容**/
 		time_type			int														,/**时间类型***/
 		time_hour			int														,/***小时**/
 		time_minute			int														,/***分钟**/
 		time_week			varchar(10)												,/***星期**/
 		is_run				int											 default 0	,/***是否执行**/
 primary key(id)) ENGINE=MyISAM;

/******************************门派大弟子*********************************/
 create table menpaicontest (
 		id					int unsigned not null auto_increment					,/**ID**/
 		time_week			varchar(10)												,/***星期**/
 		ready_hour			int														,/***准备时间*/
 		ready_minute		int														,/***准备时间*/
 		run_hour			int														,/***开始时间*/
 		run_minute			int														,/***开始时间*/
 		over_hour			int														,/***结束时间*/
 		over_minute			int														,/***结束时间*/
 		all_hour			int														,/***结束时间*/
 		all_minute			int														,/***结束时间*/
 primary key(id)) ENGINE=MyISAM;

 /***********************************会员后台表(vip)***********************************/
  create table vip(
    v_id                 int not null auto_increment                        ,/**主键id*/
    v_name               varchar(100)                                       ,/**VIP名称*/
    use_time             int                                                ,/**使用时间 小时计算*/
    mall_agio            int                                                ,/**商场折扣*/
    ho_id                int                                                ,/**称号ID*/
    is_die_drop_exp      int                                       default 0,/**死亡是不是损失经验 0 损失 1不损失*/
    v_hint               varchar(600)                                       ,/**VIP说明*/
    v_money              int                                       default 0,/**VIP说明*/
	primary key(v_id)) ENGINE=MyISAM;
	
	 	        	   	   	  /***  帮助  ****/
  	  create table  help  (
  	  		id			int unsigned not null auto_increment 					 ,		/** 帮助表ID  */  	 
  	  		super_id		int(11)		default 0											,		/*** 父类型id **/	
  	  		name		varchar(100)	not null									,		/**   类型名称    **/
  	  		des		text(1000)  default null									,		/**   类型描述    **/
  	  		shunxu		int(11)   default 0									,		/**   排序    **/
  	  		scene_id		int(12) default 0													,		/**   对应场景的id    **/
  	  		level_limit		int(2)	default 0														,		/**   对传送做等级限制   **/
  	  		type int (2) default 0                                                       ,/**  菜单类型   **/
  	  		link_name varchar(50) default null                                ,/**  连接名称   **/
  	  		task_men int(2) default 0                                ,/**  任务类型:是否是本门派，0需要，1明教，2丐帮，3少林   **/
  	  		task_zu varchar(50) default null                          ,/**任务组的名称*/
  	 primary key(id)) ENGINE=MyISAM;
  	 
  	 
  	   	   	  /***  系统奖励表  ****/
  	  create table  system_hortation_info  (
  	  		horta_id			int unsigned not null auto_increment 					 ,		/** 系统奖励表ID  */  	 
  	  		
  	  		horta_type		int(11)													,		/*** 系统奖励类型 **/	
  	  		horta_name		varchar(100)										,		/**   系统奖励名称    **/
  	  		
  	  		horta_son_id		int(11)										,		/**   具体奖励,决定了奖励在页面显示的顺序    **/
  	  		horta_son_name		varchar(100)									,		/**   具体奖励名称    **/
  	  		
  	  		
  	  		vip_grade		varchar(10)													,		/**   系统奖励条件之, 会员等级 ,以下如果没有要求统统填入零 ,数据格式为  ,2,3,    **/
  	  		online_time		int(30)															,		/**   系统奖励条件之, 在线时间,以秒为单位   **/
  	  		wj_grade			varchar(10)												    	,		/**   系统奖励条件之, 玩家等级    **/
  	  		wj_sex			    Enum('0','1','2')										,		/**   系统奖励条件之, 玩家性别   **/
  	  		
  	  		wj_menpai		 Enum('0','1','2','3')										,		/**   系统奖励条件之, 玩家门派 1是明教,2是丐帮,3是少林  **/
  	  		wj_title			    varchar(100)									    	,		/**   系统奖励条件之, 玩家称号, 如果有多个称号,以","分割   **/
  	  		wj_credit			varchar(10)													,		/**   系统奖励条件之, 玩家声望,  以"-"连接, 如有多个,以";"连接    **/
  	  		wj_next			varchar(10)										 		,		/**   系统奖励条件之, 空余等下一个   **/
  	  		
  	  		
  	  		is_only_one		Enum('0','1','2','3','4','5','6','7','8','9')		,		/** 是否仅领取一次, 为1表示仅领取一次,为0表示可以不止领取一次 */
  	  		onces				int(3) 														,		/**  一天之内最多能领取几次  */
  	  		
  	  		
  	  		give_goods			varchar(200)												,		/**    奖励装备或物品, 以","分割, 和兑换菜单的制作方法相同    ****/
  	  		isuseable			int(3)											default 1		,		/**   是否有效,为零表示无效,不会被显示出来. 为1的话则可以显示  **/
  	  		
  	  		horta_display		varchar(100)												,		/**   奖励描述  **/
  	 primary key(horta_id))  ENGINE=MyISAM;
  	 
/***********************************门派NPC(menpainpc)***********************************/
  create table menpainpc(
    id                 int not null auto_increment                        ,/**主键id*/
    p_type             int			                                      ,/**VIP名称*/
    npc_lv             int                                                ,/**使用时间 小时计算*/
    npc_id             int                                                ,/**商场折扣*/
    scence_id          int                                                ,/**称号ID*/
	primary key(id)) ENGINE=MyISAM;
  	 
  	 
  	  /****************************擂台************************/
  	 CREATE TABLE `leitai` (                               
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,      
          `name` varchar(50) NOT NULL comment '擂台名称',                        
          `des` text NOT NULL comment '擂台描述' ,                                         
          `starttime` datetime DEFAULT NULL comment '擂台开始时间',                  
          `endtime` datetime DEFAULT NULL comment '擂台结束时间', 
          `scene_id` int(11) DEFAULT '0' comment '死亡返回场景' ,
          `candead` int(11) DEFAULT '0' comment '可以死亡次数',                      
          PRIMARY KEY (`id`)                                  
        )  ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;  
        
          	 /****************************活动擂台************************/
  	 CREATE TABLE `active_leitai` (                               
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,      
          `scene_id` int(11) DEFAULT '0' comment '返回场景',
          `round1_starttime` datetime DEFAULT NULL comment '第1轮开始时间',                  
          `round1_endtime` datetime DEFAULT NULL comment '第1轮结束时间', 
          `round2_starttime` datetime DEFAULT NULL comment '第2轮开始时间',                  
          `round2_endtime` datetime DEFAULT NULL comment '第2轮结束时间', 
          `round3_starttime` datetime DEFAULT NULL comment '第3轮开始时间',                  
          `round3_endtime` datetime DEFAULT NULL comment '第3轮结束时间', 
           PRIMARY KEY (`id`)                                  
        )  ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;  
        
        
        /**指南针*/
        CREATE TABLE `compass`(
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,      
          `scene_id` int(11) DEFAULT '0' comment '场景',
          `des` varchar(255) not null comment '指南针',
            PRIMARY KEY (`id`)                                  
        )  ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;
        
         /**活动擂台*/
        CREATE TABLE `leitaiactive`(
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,     
         `into_scene` int(2) default 0 comment '进入场景',
         `ret_scene` int(2) default 0 comment '返回场景ID',
         `max_peo` int(2) default 0 comment '报名人数上限',
         `shengwang30` int(2) default 0 comment '30-39奖励声望数值',
          `shengwang40` int(2) default 0 comment '40-49奖励声望数值',
          `shengwang50` int(2) default 0 comment '50-59奖励声望数值',
          `shengwang60` int(2) default 0 comment '60-69奖励声望数值',
          `shengwang70` int(2) default 0 comment '70-79奖励声望数值',
          `shengwang80` int(2) default 0 comment '80-89奖励声望数值',
          `max_dead` int(2) default 0 comment '死亡次数控制 ',
          `baomingstarttime` varchar(20) DEFAULT NULL comment '报名开始时间',
          `starttime` varchar(20) DEFAULT NULL comment  '开始时间',                  
          `endtime` varchar(20) DEFAULT NULL comment '结束时间', 
          `overtime` int(2) default 0 comment '报名后多长时间内不能进入',
         PRIMARY KEY (`id`)            
        )ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;
        
      /**挑战擂台*/
        CREATE TABLE `leitaichallenge`(
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,    
          `lei_name` varchar(255) not null default '擂台', 
         `into_scene` int(2) default 0 comment '进入场景',
         `ret_scene` int(2) default 8 comment '返回场景',
         `ppk` int(2) default 0 comment '擂主id',
         `name` varchar(255) default null comment '擂主名称',
         `cppk` int(2) default 0 comment '挑战者id',
         `cName` varchar(255) default null comment '挑战者名称',
         `credit` int(2) default 0 comment '挑战的声望数',
         `time` datetime default null comment '挑战时间',
         `onein` int(2) default 0 comment '擂主是否进入',
         `twoin` int(2) default 0 comment '挑战者是否进入',
         `pkstate` int(2) default 0 comment 'PK状态',
         PRIMARY KEY (`id`)            
        )ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;
        
     /**对战擂台*/
        CREATE TABLE `battle`(
          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,    
         `ret_scene` int(2) default 8 comment '返回场景',
         `max_peo` int(2) default 50 comment '最多报名人数',
         `scene1` int(2) default 0 comment '场景1',
         `scene2` int(2) default 0 comment '场景2',
         `scene3` int(2) default 0 comment '场景3',
         `scene4` int(2) default 0 comment '场景4',
         `scene5` int(2) default 0 comment '场景5',
         `scene6` int(2) default 0 comment '场景6',
         `scene7` int(2) default 0 comment '场景7',
         `scene8` int(2) default 0 comment '场景8',
         `min_sheng` int(2) default 100 comment '对战声望最低值',
         `baomingtime` varchar(255) default null comment '报名开始时间',
         ·baomingendtime· varchar(255) default null comment '报名结束时间',
         `starttime1` varchar(255) default null comment '第一场开始时间',
          `endtime1` varchar(255) default null comment '第一场结束时间',
          `starttime2` varchar(255) default null comment '第二场开始时间',
          `endtime2` varchar(255) default null comment '二场结束时间',
          `starttime3` varchar(255) default null comment '第三场开始时间',
          `endtime3` varchar(255) default null comment '第三场结束时间',
          `starttime4` varchar(255) default null comment '第四场开始时间',
          `endtime4` varchar(255) default null comment '第四场结束时间',
          `jiangli1` int(3) default 0 comment '第一名奖励',
          `jiangli2` int(3) default 0 comment '第二名奖励',
         PRIMARY KEY (`id`)            
        )ENGINE=MyISAM  AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;    