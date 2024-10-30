create database jygame_user;  
/**********玩家注册表(u_login_info)***************/
create table u_login_info ( 
   u_pk             smallint unsigned not null auto_increment ,	 /**创建人员信息id*/ 
   u_name           varchar(200)                              ,  /**管理员登录名*/ 
   u_paw            varchar(200)                              ,  /**管理员登录密码*/
   login_state      int                                       ,  /**登陆状态 1为登陆 0 为未登陆*/ 
   create_time      datetime                                  ,  /**创建时间*/
   primary key (u_pk));
   
/**********角色信息表(u_part_info)***************/
create table u_part_info ( 
   p_pk             smallint unsigned not null auto_increment ,	 /**角色id*/ 
   u_pk             int                                       ,	 /**创建人员信息id*/ 
   p_name           varchar(200)                              ,  /**角色名*/
   p_sex            int                                       ,  /**性别*/   
   p_grade          int                                       ,  /**等级*/ 
   p_up_hp          int                                       ,  /**生命值*/ 
   p_hp             int                                       ,  /**当前HP值*/ 
   p_up_mp          int                                       ,  /**生命值*/
   p_mp             int                                       ,  /**法力值*/
   p_force          int                                       ,  /**力*/
   p_agile          int                                       ,  /**敏*/
   p_physique       int                                       ,  /**体魄	*/
   p_savvy          int                                       ,  /**悟性	*/
   p_gj             int                                       ,  /**攻击*/
   p_fy             int                                       ,  /**防御*/
   p_zbgj_xiao      int                                       ,  /**最小攻击*/
   p_zbgj_da        int                                       ,  /**最大攻击*/
   p_zbfy_xiao      int                                       ,  /**最小防御*/
   p_zbfy_da        int                                       ,  /**最大防御*/
   p_teacher_type   int                                       ,  /**师徒1师傅2徒弟*/
   p_teacher        int                                       ,  /**师傅的名称id	*/
   p_harness        int                                       ,  /**是否已婚 1没结婚 2 结婚*/
   p_fere           int                                       ,  /**伴侣ID*/
   p_title          int                                       ,  /**称号*/
   p_title_name     varchar(500)                              ,  /**称号名称*/
   p_born           int                                       ,  /**出生*/
   p_camp           int                                       ,  /**阵营*/
   p_camp_name      varchar(500)                              ,  /**阵营名称*/
   p_school         int                                       ,  /**门派*/
   p_school_name    varchar(500)                              ,  /**门派名称*/ 
   p_experience     varchar(500)                              ,  /**经验*/
   p_benji_experience     varchar(500)                        ,  /**本级经验*/
   p_xia_experience varchar(500)                              ,  /**下经验*/
   p_silver         varchar(500)                              ,  /**银子单位 两*/
   p_copper         varchar(500)                              ,  /**铜钱单位 文*/
   p_depot          int                                       ,  /**仓库	*/
   p_pk_value       int                                       ,  /**pk值*/
   p_pks            int                                       ,  /**开关1关2开*/
   p_pk_changetime  datetime                                  ,  /**pk开关改变的时间*/
   p_isInitiative	int								 default 0,  /**标识是否处在主动攻击状态，0否；1是*/
   p_isPassivity	int								 default 0,  /**标识是否处在被动攻击状态，0否；1是*/	 
   p_map            varchar(200)                              ,  /**所在场景ID*/
   p_procession     int                                       ,  /**是否组队0无1组*/
   p_procession_numner  varchar(200)                          ,  /**队伍编号*/
   p_tong           int                                       ,  /**帮会*/
   p_tong_name      varchar(500)                              ,  /**帮会名称*/
   
   p_wrap_content		int									,  /**包裹容量*/
   p_wrap_spare			int									,  /**包裹剩余数量*/
   
   create_time      datetime                                  ,  /**创建时间*/
   primary key (p_pk));
    
/**********角色登陆记录表(u_part_annal)***************/
create table u_part_annal ( 
   a_pk             smallint unsigned not null auto_increment ,	 /**角色登陆记录表id*/  
   u_pk             int                                       ,	 /**创建人员信息id*/
   p_pk             int                                       ,	 /**角色id*/
   p_name           varchar(200)                              ,  /**角色名*/  
   p_map            varchar(200)                              ,  /**所在场景ID*/
   a_ken            varchar(200)                              ,  /**所在场景视野*/  
   primary key (a_pk));


 /**********角色装备表(u_trait_info)***************/
create table u_trait_info ( 
   t_pk             smallint unsigned not null auto_increment ,	 /**角色装备id*/ 
   u_pk             int                                       ,	 /**创建人员信息id*/ 
   p_pk             int                                       ,	 /**角色id*/ 
   t_caput          int                                       ,  /**头部装备*/
   t_toufy_xiao     int                                 default 0,  /**头部装备最小防御*/
   t_toufy_da       int                                 default 0,  /**头部装备最大防御*/
   t_tou_li         int                                 default 0,  /**头部力量*/
   t_tou_min        int                                 default 0,  /**头部敏捷*/
   t_tou_ti         int                                 default 0,  /**头部气血*/
   t_tou_wu         int                                 default 0,  /**头部悟性*/  
   t_body           int                                       ,   /**身体装备*/
   t_tify_xiao      int                                 default 0,/**身体装备最小防御*/
   t_tify_da        int                                 default 0,/**身体装备最大防御*/
   t_ti_li          int                                 default 0,/**身体力量*/
   t_ti_min         int                                 default 0,/**身体敏捷*/
   t_ti_ti          int                                 default 0,/**身体气血*/
   t_ti_wu          int                                 default 0,/**身体悟性*/  
   t_crura          int                                 default 0,/**腿部装备*/
   t_tuify_xiao     int                                 default 0,/**腿部装备最小防御*/
   t_tuify_da       int                                 default 0,/**腿部装备最大防御*/
   t_tui_li         int                                 default 0,/**腿部力量*/
   t_tui_min        int                                 default 0,/**腿部敏捷*/
   t_tui_ti         int                                 default 0,/**腿部气血*/
   t_tui_wu         int                                 default 0,/**腿部悟性*/  
   t_feet           int                                       ,   /**脚部装备*/
   t_jiaofy_xiao    int                                default 0, /**脚部装备最小防御*/
   t_jiaofy_da      int                                default 0, /**脚部装备最大防御*/
   t_jiao_li        int                                default 0, /**脚部力量*/
   t_jiao_min       int                                default 0, /**脚部敏捷*/
   t_jiao_ti        int                                default 0, /**脚部气血*/
   t_jiao_wu        int                                default 0, /**脚部悟性*/  
   t_necklace       int                                       ,   /**项链*/
   t_xlfy_xiao      int                                default 0, /**项链最小防御*/
   t_xlfy_da        int                                default 0, /**项链最大防御*/ 
   t_xlgj_xiao      int                                default 0, /**项链最小攻击*/
   t_xlgj_da        int                                default 0, /**项链最大攻击*/
   t_xl_li          int                                default 0, /**项链力量*/
   t_xl_min         int                                default 0, /**项链敏捷*/
   t_xl_ti          int                                default 0, /**项链气血*/
   t_xl_wu          int                                default 0, /**项链悟性*/ 
   t_shouzhuo       int                                       ,   /**手镯*/
   t_szfy_xiao      int                               default 0,  /**手镯最小防御*/
   t_szfy_da        int                               default 0,  /**手镯最大防御*/ 
   t_szgj_xiao      int                               default 0,  /**手镯最小攻击*/
   t_szgj_da        int                               default 0,  /**手镯最大攻击*/
   t_sz_li          int                               default 0,  /**手镯力量*/
   t_sz_min         int                               default 0,  /**手镯敏捷*/
   t_sz_ti          int                               default 0,  /**手镯气血*/
   t_sz_wu          int                               default 0,  /**手镯悟性*/  
   t_finger         int                                       ,   /**戒指*/
   t_jzfy_xiao      int                               default 0,  /**戒指最小防御*/
   t_jzfy_da        int                               default 0,  /**戒指最大防御*/ 
   t_jzgj_xiao      int                               default 0,  /**戒指最小攻击*/
   t_jzgj_da        int                               default 0,  /**戒指最大攻击*/
   t_jz_li          int                               default 0,  /**戒指力量*/
   t_jz_min         int                               default 0,  /**戒指敏捷*/
   t_jz_ti          int                               default 0,  /**戒指气血*/
   t_jz_wu          int                               default 0,  /**戒指悟性*/ 
   t_arm            int                                       ,   /**武器*/ 
   t_wqgj_xiao      int                               default 0,  /**武器最小攻击*/
   t_wqgj_da        int                               default 0,  /**武器最大攻击*/
   t_wq_li          int                               default 0,  /**武器力量*/
   t_wq_min         int                               default 0,  /**武器敏捷*/
   t_wq_ti          int                               default 0,  /**武器气血*/
   t_wq_wu          int                               default 0,  /**武器悟性*/  
   t_zbgj_xiao      int                                       ,   /**最小攻击*/
   t_zbgj_da        int                                       ,   /**最大攻击*/
   t_zbfy_xiao      int                                       ,   /**最小防御*/
   t_zbfy_da        int                                       ,   /**最大防御*/ 
   t_li             int                                       ,   /**力量*/
   t_min            int                                       ,   /**敏捷*/
   t_ti             int                                       ,   /**气血*/
   t_wu             int                                       ,   /**悟性*/ 
   create_time      datetime                                  ,   /**创建时间*/
   primary key (t_pk));
   
/**********角色包袱表(u_wrap_info)***************/
create table u_wrap_info ( 
   w_pk             smallint unsigned not null auto_increment ,	 /**角色包袱表*/ 
   u_pk             int                                       ,	 /**创建人员信息id*/ 
   p_pk             int                                       ,	 /**角色id*/
   w_type           int                                       ,	 /**包袱分类*/ 
   w_number         int                                       ,	 /**包裹格数*/ 
   w_article        varchar(500)                              ,  /**包裹物品*/
   create_time      datetime                                  ,  /**创建时间*/
   primary key (w_pk));
 
/**********角色五行表(u_limerick_info)***************/
create table u_limerick_info ( 
   l_pk             smallint unsigned not null auto_increment ,	 /**角色id*/ 
   u_pk             int                                       ,	 /**创建人员信息id*/ 
   p_pk             int                                       ,	 /**角色id*/
   l_jin_fy         int                                       ,	 /**金防御力*/
   l_mu_fy          int                                       ,	 /**木防御力*/
   l_shui_fy        int                                       ,	 /**水防御力*/
   l_huo_fy         int                                       ,	 /**火防御力*/
   l_tu_fy          int                                       ,	 /**土防御力*/
   l_jin_gj         int                                       ,	 /**金攻击力*/
   l_mu_gj          int                                       ,	 /**木攻击力*/
   l_shui_gj        int                                       ,	 /**水攻击力*/
   l_huo_gj         int                                       ,	 /**火攻击力*/
   l_tu_gj          int                                       ,	 /**土攻击力*/
   create_time      datetime                                  ,  /**创建时间*/
   primary key (l_pk)); 
   
/**********角色宠物表(p_pet_info)***************/ 
create table p_pet_info(
	pet_pk			 smallint unsigned not null auto_increment , /**ID */
	p_pk			 int 					                   , /**角色id*/
	pet_id			 int					                   , /**对应pet表里的id*/
	pet_name		 varchar(200)				               , /**宠物名称*/
	pet_nickname	 varchar(200)				               , /**宠物昵称*/
	pet_grade		 int					                   , /**等级*/
	pet_exp			 varchar(200)			                   , /**经验*/
	pet_xia_exp		 varchar(200)                              , /**下级经验达到下一级需要的经验*/
	pet_gj_xiao      int                     		           , /**最小攻击*/
	pet_gj_da        int					                   , /**最大攻击*/
	pet_sale		 int			                           , /**卖出价格*/
	pet_img			 varchar(200)                              , /**宠物图片*/
	pet_grow	     int                                       , /**宠物成长率”*/
	pet_wx           int                  			           , /**五行属性金=1，木=2，水=3，火=4，土=5 */
    pet_wx_value     int           				               , /**五行属性值*/ 
    pet_skill_one	 int                                       , /**技能1	可学习的技能id*/
    pet_skill_two	 int                          	           , /**技能2	可学习的技能id*/
    pet_skill_three	 int                        	           , /**技能3	可学习的技能id*/
    pet_skill_four	 int                          		       , /**技能4	可学习的技能id*/
    pet_skill_five	 int                                  	   , /**技能5	可学习的技能id*/
    pet_life		 int			                           , /**寿命**/
    pet_type         int                                       , /**升级	是否可自然升级*/	
    pet_isBring		 int					                   , /**是否在身上:1表示在战斗状态，0表示否*/
    pet_fatigue		 int			 	                       , /**疲劳度0-100,出战状态下增加疲劳度，一个小时加10点*/
 primary key (pet_pk));
  
/**********角色技能表(u_skill_info)***************/
create table u_skill_info ( 
   s_pk             smallint unsigned not null auto_increment ,	 /**id*/ 
   p_pk             int                                       ,	 /**角色id*/
   sk_id         	int                                       ,	 /**技能id*/ 
   sk_name			varchar(200)										  ,	 /**技能名字*/ 
   sk_usetime		datetime									,/**本技能上一次的使用的时间*/
   create_time      datetime                                  ,  /**创建时间*/
   primary key (s_pk)); 
  
/**********角色宠物交易表(u_pet_sell)***************/ 
create table u_pet_sell(
	ps_pk			     smallint unsigned not null auto_increment , /**ID */
	p_pk			     int 					                   , /**请求角色id*/
	p_by_pk			     int 					                   , /**被请求角色id*/ 
	pet_id			     int					                   , /**对应pet表里的id*/
	ps_silver_money      int                                       , /**发出请求要物品的价格的银子*/
    ps_copper_money      int                                       , /**发出请求要物品的价格的铜钱*/
 primary key (ps_pk));
 
/**********角色交易(u_sell_info)***************/
create table u_sell_info ( 
   s_pk                   smallint unsigned not null auto_increment ,	 /**id*/
   p_pk                   int                                       ,	 /**发出请求角色id*/
   p_by_pk                int                                       ,	 /**被请求角色id*/
   s_wuping               int                                       ,    /**发出请求要交易的物品*/
   s_wp_type              int                                       ,    /**发出请求要物品类型*/
   s_wp_number            int                                       ,    /**发出请求要物品的数量*/ 
   s_wp_silver_money      int                                       ,    /**发出请求要物品的价格的银子*/
   s_wp_copper_money      int                                       ,    /**发出请求要物品的价格的铜钱*/ 
   s_silver_money         int                                       ,    /**发出请求要交易的银子*/
   s_copper_money         int                                       ,    /**发出请求要交易的铜钱*/ 
   s_sf_ok                int                                       ,    /**发出请求的玩家是否确认0没确认1确认*/ 
   s_by_wuping            int                                       ,    /**被请求要交易的物品*/
   s_by_wp_type           int                                       ,    /**被请求要物品类型*/
   s_by_wp_number         int                                       ,    /**被请求要物品的数量*/ 
   s_by_silver_money      int                                       ,    /**被请求要物品的银子*/
   s_by_copper_money      int                                       ,    /**被请求要物品的铜钱*/ 
   s_by_silver            int                                       ,    /**被请求要物品要交易的银子*/
   s_by_copper            int                                       ,    /**被请求要物品要交易的铜钱*/ 
   s_by_sf_ok             int                                       ,    /**被请求的玩家是否确认0没确认1确认*/ 
   create_time            datetime                                  ,    /**创建时间*/
   primary key (s_pk));
 
 /**********交流频道(u_communion)***************/
 create table u_communion ( 
   c_pk                   smallint unsigned not null auto_increment ,	 /**公共频道id*/
   p_pk                   int                                       ,	 /**发送角色id*/
   p_name                 varchar(200)                              ,	 /**发送角色名称*/
   p_pk_by                int                                       ,	 /**接收角色id*/
   p_name_by              varchar(200)                              ,	 /**接收角色名称*/ 
   c_bang                 int                                       ,	 /**所属帮会*/
   c_dui                  int                                       ,	 /**所属组队*/
   c_zhen                 int                                       ,	 /**所属阵营*/
   c_title                varchar(500)                              ,    /**聊天类容*/ 
   c_type                 int                                       ,    /**类型*/ 
   create_time            datetime                                  ,    /**创建时间*/
   primary key (c_pk)); 
   
   
   
   
/****************************************LS****************************/
      
/****************************************NPC临时表****************************/
   
create table n_attack_info (
  n_pk              SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**当前战斗NPCID */
  u_pk              int                                       , /**创建人员信息id*/ 
  p_pk		        int					                      , /**角色id*/
  n_current_HP	    varchar(200)      			              , /**npc当前血值*/
  n_attackswitch	int                                       , /**主动攻击开关	0表示此点刷新出的npc被动攻击玩家，1表示此点刷新出的npc主动攻击玩家，*/
  npc_ID            int					                      , /**npc */
  npc_Name          varchar(200)                              , /**名称	npc名称*/
  npc_HP            varchar(200)                              , /**气血	npc气血值*/
  npc_defence_da    int					                      , /**最大防御	npc防御 */
  npc_defence_xiao  int					                      , /**最小防御	npc防御*/
  npc_jin_fy	    int					                      , /**金防御*/	
  npc_mu_fy	        int					                      , /**木防御*/	
  npc_shui_fy	    int					                      , /**水防御*/	
  npc_huo_fy	    int					                      , /**火防御*/
  npc_tu_fy	        int					                      , /**土防御*/	
  npc_drop          int					                      , /**暴击率	表示为正整数，如20表示20%的暴击率*/
  npc_Level         int					                      , /**等级	npc等级 */
  npc_EXP           double				                      , /**经验	杀死npc获得经验*/
  npc_money	        varchar(200)			                  , /**掉落钱数：如杀死怪后掉20到30之间随机取，表示为20,30*/
  npc_take          int					                      , /**可否捕捉	0表示不可捕捉，1表示可以捕捉*/
  npc_refurbish_time   int				                      , /**刷新时间间隔	分钟为单位*/
  scene_id          int                                       , /**刷新地点id*/
  npc_key		    varchar(200	)			                  , /** 生成唯一标示 */
  npc_isAttack		int				default 0                 , /**标示npc是否处于战斗状态；1表示是，0表示否，默认为0*/
  npc_type           int                default 1               , /* npc类型，可以被打败的npc为2；和可以被打死的npc为1 **/
  dizzy_bout_num      int                              default 0, /** 击晕状态的剩余回合数 */
  poison_bout_num    int          default 0                     ,/**中毒状态的剩余回合数
  create_time     	datetime				                  , /**创建时间 */
  primary key (n_pk));
  
  /**********************************打怪掉落物品表***********************************************/
  create table n_dropgoods_info(
  d_pk						 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT ,/**ID */
  p_pk						 int					   				   ,/**角色id*/
  drop_num					 int									   ,/*掉落数量*/
  goods_id					 int 									   ,/*物品id*/
  goods_name				 varchar(200)							   ,/*物品名字*/
  goods_type				 int									   ,/*物品类型*/
  primary key (d_pk));
  
/**********************************打怪时npc掉经验和钱的临时表***********************************************/
    
 create table n_dropExpMoney_info(
   d_pk							 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT ,/**ID */
   p_pk							 int					   				   ,/**角色id*/
   drop_exp					     int									   ,/*npc掉落经验*/	
   drop_money				     int									   ,/*npc掉落的钱数*/
   primary key (d_pk));
    



    
    
/*************************玩家快捷键设置（u_shortcut_info）********************************/
    create table u_shortcut_info(
    sc_pk							 smallint unsigned not null auto_increment   ,/**id*/ 
    p_pk							 int										 ,/**玩家id*/
    sc_name							 varchar(200)                                ,/**快捷键名字，标号*/
    sc_display						 varchar(200)                                ,/**设置后显示的名字，例如；技能名称,药品名称*/
    sc_type							 int                                         ,/**类型,值为-1时表示没有设置快捷键*/
    operate_id						 int                                         ,/**操作id*/
    object							 int                                         ,/**作用对象*/          
    primary key(sc_pk));

  
  
   /*************************玩家包裹里的道具（u_propgroup_info）,每条记录：记录的是一组道具********************************/
  create table u_propgroup_info(
  	pg_pk	                         smallint unsigned not null auto_increment       ,   /**id*/ 
    p_pk                             int                                             ,	 /**角色id*/
    pg_type                          int                                             ,	 /**道具组分类*/ 
    prop_id			                 int                                             ,	 /**道具id*/
    prop_type						 int                                             ,   /**道具类型*/
    prop_name	                     varchar(200)                                    ,   /**道具名字*/
    prop_price                       int                                             ,    /**卖出价钱*/
    prop_num                         int                                             ,	 /**道具数量，不能超过限制格数*/ 
    create_time                      datetime                                        ,  /**创建时间*/
    primary key(pg_pk));
    
     /*************************控制表（需要时间或使用次数控制的对象）（u_time_control）********************************************/
    create table u_time_control(
    id	                         smallint unsigned not null auto_increment       ,   /**id*/ 
    p_pk                         int                                             ,   /**角色id*/
    object_id                    int                                             ,	 /**需要控制的对象*/
    object_type                  int                                             ,   /**需要控制的对象的类型，如：道具，菜单等*/
    use_datetime                 datetime                                        ,   /**记录最后一次的使用时间*/
    use_times                    int                                             ,   /**记录当天的使用次数*/
    primary key(id));
    
  /*************************玩家坐标标记记录_标记道具用（u_coordinate_info）********************************/
  create table u_coordinate_info(
  c_pk	                         smallint unsigned not null auto_increment       , /**id*/
  p_pk                           int                                             , /**角色id*/
  coordinate_prop_id             int                                             , /**坐标道具id*/
  coordinate                     int                                             , /**标记坐标*/
  prop_isUse                    int                                     default 0, /**标记道具是否使用*/
  primary key(c_pk));
 
  
  /*******************************组队表（u_group_info）************************************************************/
  create table u_group_info(
  g_pk                          smallint unsigned not null auto_increment       , /**id*/ 
  p_pk                          int                                             , /**角色id*/
  g_captain_pk                  int                                             , /**表示是队长id，（队长id和p_pk相同）*/
  primary key(g_pk));
   
   
  /*******************************组队通知表（u_groupnotify_info）************************************************************/
  create table u_groupnotify_info(
  n_pk                                 smallint unsigned not null auto_increment        , /**id*/ 
  notifyed_pk                          int                                              , /**被通知的玩家id*/
  create_notify_pk                     int                                              , /**产生通知的玩家id*/ 
  notify_content                       varchar(200)                                     , /**通知的内容**/
  notify_type                          int                                              , /**通知类型：1：通知有人申请组队；2：通知队伍解散；3.通知对方同意组队；4：。。。***/
  create_time                          datetime                                         , /**创建时间*/
  notify_flag                          int                                     default 0, /**通知标识，0表示没有通知，1表示已通知*/
  primary key(n_pk));
   
  
  
  /***********************************************buff使用效果(u_buffeffect_info)***********************************/
  create table u_buffeffect_info  (
  bf_pk                       smallint unsigned not null auto_increment                  , /**id*/ 
  buff_id                     int                                                        , /**buff_id*/
  buff_name                   varchar(200)                                               , /**名称*/	
  buff_display                varchar(200)                                               , /**buff描述*/
  buff_type                   int                                                        , /**buff类型*/  
  buff_effect_value           int                                                        , /**buff效果值，*/ 
  
  spare_bout                  int                                                        , /**剩下的使用回合数*/
  buff_bout                   int                                              default 0 , /**持续回合*/
  buff_time                   int                                              default 0 , /**buff持续时间，单位为分钟*/
  use_time                    datetime                                                   , /**使用buff的时间*/
  
  buff_use_mode               int                                                        , /**使用方式，1表示增益，2表示减益*/
  buff_bout_overlap           int                                              default 0 , /**是否回合叠加,0表示不能，1表示能*/
  buff_time_overlap           int                                              default 0 , /**是否时间叠加,0表示不能，1表示能*/
  
  
  effect_object               int                                                        , /**buff效果作用对象*/
  effect_object_type          int                                                        , /**buff效果作用对象,11表示玩家，12表示npc*/
  primary key(bf_pk));
   
       
  /**********pk控制表表(u_pk_control)****************/
  create table u_pk_control(
  	 id             	        smallint unsigned not null auto_increment 	,/**id*/  
	 zd_pk						int											,/**主动攻击的玩家id*/
	 zd_dead_flag				int								   default 0,/**主动玩家的死亡标志*/	
	 zd_attack_no				int											,/**主动攻击者顺序,该字段影响受到攻击玩家反击的频率*/ 
	 /**被动动玩家攻击玩家*/
	 bd_pk						int								           ,/**被动攻击的玩家id*/
	 bd_dead_flag				int								  default 0,/**被动玩家的死亡标志*/	 
	 dead_notify			    int								  default 0,/**是否通知对方死亡*/ 
	 drop_exp				    int							      default 0,/**掉落经验*/ 
	 attack_map					int									       ,/**战斗场景id*/
  	 create_time      datetime                                 	           ,/**战斗开始时间*/
  
  primary key(id));
    
    

   
   /*****************************pk通知(u_pk_notify)*****************************************/
   create table u_pk_notify(
    n_pk                                 smallint unsigned not null auto_increment        , /**id*/
    notifyed_pk                          int                                              , /**被通知的玩家id*/
    create_notify_pk                     int                                              , /**产生通知的玩家id*/ 
    notify_content                       varchar(200)                                     , /**通知的内容**/
    notify_type                          int                                              , /**通知类型：1.受到攻击***/
    create_time                          datetime                                         , /**创建时间*/
   primary key(n_pk));
   
   
     /**********pk日志表(u_pk_log)****************/
  create table u_pk_log(
  	 id             	        smallint unsigned not null auto_increment 	, /**id*/
	 zd_pk						int											, /**主动攻击的玩家id*/
	 bd_pk						int								            , /**被动攻击的玩家id*/
	 create_time                datetime                                    , /**创建时间*/
  primary key(id));
   
   
   
   create table u_wrap_info ( 
   w_pk             smallint unsigned not null auto_increment ,	  /**角色包袱表*/ 
   u_pk             int                                       ,	  /**创建人员信息id*/ 
   p_pk             int                                       ,	  /**角色id*/ 
   table_type       int                                       ,   /**物品相关表类型*/
   goods_type       int                                       ,   /**物品类型*/
   w_id             int                                       ,   /**物品ID*/
   w_name           varchar(200)                              ,   /**物品名称*/
   w_durability     int                               default 0,  /*耐久*/ 
   w_dura_consume   int                               default 0,  /*耐久消耗*/
   w_Bonding        int                                        ,  /**绑定*/ 
   t_fy_xiao        int                               default 0,  /**附加属性最小防御*/
   t_fy_da          int                               default 0,  /**附加属性最大防御*/ 
   t_gj_xiao        int                               default 0,  /**附加属性最小攻击*/
   t_gj_da          int                               default 0,  /**附加属性最大攻击*/
   t_mp             int                               default 0,  /**附加属性发力*/
   t_hp             int                               default 0,  /**附加属性气血*/
 
   w_jin_fy         int                               default 0,  /**附加属性金防御力*/
   w_mu_fy          int                               default 0,  /**附加属性木防御力*/
   w_shui_fy        int                               default 0,  /**附加属性水防御力*/
   w_huo_fy         int                               default 0,  /**附加属性火防御力*/
   w_tu_fy          int                               default 0,  /**附加属性土防御力*/
   w_jin_gj         int                               default 0,  /**附加属性金攻击力*/
   w_mu_gj          int                               default 0,  /**附加属性木攻击力*/
   w_shui_gj        int                               default 0,  /**附加属性水攻击力*/
   w_huo_gj         int                               default 0,  /**附加属性火攻击力*/
   w_tu_gj          int                               default 0,  /**附加属性土攻击力*/ 
   w_type           int                               default 0,  /**是否被装备 0 没有 1被装备了*/ 
   create_time      datetime                                   ,  /**创建时间*/
   primary key (w_pk));
   
   
   
   