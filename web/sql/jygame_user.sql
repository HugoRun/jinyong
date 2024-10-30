drop database jygame_user;
create database jygame_user;
use jygame_user;
/**********玩家注册表(u_login_info)***************/
create table u_login_info ( 
   u_pk             int not null auto_increment ,	 /**创建人员信息id*/ 
   u_name           varchar(20)                      UNIQUE   ,  /**管理员登录名*/ 
   u_paw            varchar(50)                              ,  /**管理员登录密码*/
   login_state      int                                       ,  /**登陆状态 1为登陆 0 为未登陆*/ 
   create_time      datetime                                  ,  /**创建时间*/
   last_login_ip    varchar(20)                               ,  /**玩家最后登陆的IP地址*/
   last_login_time  datetime                     default null ,  /**玩家最后一次登陆时间*/
   yuanbao          int                          default    0 ,  /**元宝数量*/
   jifen            int                          default    0 ,  /**商城积分数量*/
   super_qudao      varchar(255)                 default null ,/**父渠道*/
   qudao            varchar(255)                 default null,/**子渠道*/
   primary key (u_pk))  ENGINE=innodb;
create index Index1 on u_login_info(login_state);

/**********玩家白名单不需要判断(u_login_sift)***************/
create table u_login_sift ( 
   s_pk             int not null auto_increment               ,  /**创建人员信息id*/ 
   u_name           varchar(20)                              ,  /**管理员登录名*/
   primary key (s_pk));
create index Index1 on u_login_sift(u_name);

/**********角色信息表(u_part_info)***************/
create table u_part_info ( 
   p_pk             int not null auto_increment ,	 /**角色id*/ 
   u_pk             int                                       ,	 /**创建人员信息id*/ 
   p_name           varchar(20)                      UNIQUE   ,  /**角色名，用户名唯一*/
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
   p_title          varchar(20)                              ,  /**称号*/
   p_title_name     varchar(50)                              ,  /**称号名称*/
   p_race          int                                                  ,/**角色种族1妖2巫族**/
   p_born           int                                       ,  /**出生*/
   p_camp           int                                       ,  /**阵营*/
   p_camp_name      varchar(50)                              ,  /**阵营名称*/
   p_school         varchar(20)                              ,  /**门派*/
   p_school_name    varchar(50)                              ,  /**门派名称*/ 
   p_experience     varchar(20)                              ,  /**经验*/
   p_benji_experience     varchar(20)                        ,  /**本级经验*/
   p_xia_experience varchar(20)                              ,  /**下经验*/
   p_silver         varchar(5)                              ,  /**元宝*/
   p_copper         varchar(20)                              ,  /**铜钱单位 文*/
   p_depot          int                                       ,  /**仓库	*/
   p_pk_value       int                                       ,  /**pk值*/
   p_pks            int                                       ,  /**开关1关2开*/
   p_drop_multiple       double                               , /**人物暴击率*/
   p_pk_changetime  datetime                                  ,  /**pk开关改变的时间*/
   p_isInitiative	int								 default 0,  /**标识是否处在主动攻击状态，0否；1是*/
   p_isPassivity	int								 default 0,  /**标识是否处在被动攻击状态，0否；1是*/	 
   p_map            varchar(5)                              ,  /**所在场景ID*/
   p_procession     int                                       ,  /**是否组队0无1组*/
   p_procession_numner  varchar(5)                          ,  /**队伍编号*/
   p_tong           int                                       ,  /**帮会*/
   p_tong_name      varchar(50)                              ,  /**帮会名称*/
   
   p_wrap_content		int									,  /**包裹容量*/
   p_wrap_spare			int									,  /**包裹剩余数量*/
   
   create_time      datetime                                  ,  /**创建时间*/
   delete_flag		tinyint						default  0	 , /** 删除标志，,0表示正常状态，-1表示角色已经被删除，1表示删除缓冲状态 */
   delete_time		datetime									, /** 删除时间 */
   te_level               int                        default 0,
   chuangong         varchar(50)          default null,
   login_state               int                        default 0,/**登陆状态*/
   p_kill_num		int									default 0,/***杀人数**/
   last_shoutu_time  datetime,
   player_state_by_new		int									default 0,/***新手状态  1为新手 0不是新手 11为游客新手 10为游客不是新手**/
   primary key (p_pk)) ENGINE=innodb;
   create index Index1 on u_part_info (p_pk,u_pk);
   create index Index2 on u_part_info (u_pk);
   

  
/**********角色装备表(u_part_equip)***************/
create table u_part_equip ( 
   pw_pk            int not null auto_increment ,	  /**角色包袱表*/
   p_pk             int                                       ,	  /**角色id*/ 
   table_type       int                                       ,   /**物品相关表类型*/
   goods_type       int                                       ,   /**物品类型*/
   w_id             int                                       ,   /**物品ID*/
   w_name           varchar(20)                              ,   /**物品名称*/
   w_durability     int                                       ,   /**耐久*/ 
   w_dura_consume   int                                       ,   /**耐久消耗*/
   w_Bonding        int                                       ,   /**绑定*/ 
   w_protect        int                                       ,   /**保护*/ 
   w_isReconfirm    int                                       ,   /**二次确认*/ 
   w_price          int                                       ,   /**卖出价钱*/
   w_fy_xiao        int                               default 0,  /**附加属性最小防御*/
   w_fy_da          int                               default 0,  /**附加属性最大防御*/ 
   w_gj_xiao        int                               default 0,  /**附加属性最小攻击*/
   w_gj_da          int                               default 0,  /**附加属性最大攻击*/ 
   w_hp             int                               default 0,  /**附加属性气血*/
   w_mp             int                               default 0,  /**附加属性法力*/
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
   w_quality        int                                        ,  /**品质*/
   suit_id          int                               default 0,  /**对应套装id,不是套装默认为0*/
   w_wx_type        int                                        ,  /**装备五行类型*/
   w_buff_isEffected  int                             default 0,  /**附加buff是否有效，0表示无效，1表示有效*/
   enchant_type		  varchar(10)				      default 0,  /**附加点化属性  */
   enchant_value	  int		                      default 0,  /**附加点化属性值 */
   w_zj_hp		      varchar(10)                             ,  /**追加气血 */
   w_zj_mp			  varchar(10)                             ,  /**追加内力 */	
   w_zj_wxgj		  varchar(10)                             ,  /**追加攻击 */
   w_zj_wxfy		  varchar(10)                             ,  /**追加防御 */ 
   w_zb_grade		  int                                      ,  /**装备的等级 */		
   create_time        datetime                                 ,  /**创建时间*/
   p_poss             int                             default 0,  /**创建时间*/
   w_Bonding_Num      int                             default 0,   /**解除绑定次数绑定*/
   specialcontent     int                             default 0,   /**武器的属性*/
   primary key (pw_pk)) ENGINE=innodb;
   create index Index1 on u_part_equip (p_pk,w_id,table_type);
   create index Index2 on u_part_equip (p_pk,w_type,table_type);
   create index Index3 on u_part_equip (p_pk,w_type,w_dura_consume);
  
 /**********角色仓库表(u_warehouse_info)*****************************/
create table u_warehouse_info (
	uw_id	    int not null auto_increment 	,	/** 角色包袱表*/
	u_pk		int 										,	/** 创建人员信息id*/
	p_pk 		int 										,	/** 角色id*/
	uw_prop_id  int 										,   /** 物品id，这是其在各自分类表中的id */
	uw_type 	int 										,	/** 仓库分类 */
	
	uw_prop_type int										,	/** 物品类型 */
	uw_number 	int 							default 100	,	/** 仓库格数上限，默认为100。 */
	uw_article	varchar(50)								,	/** 仓库物品*/
	uw_prop_number int 										,	/** 物品数量 */
	uw_money 	varchar(50)				default 10000000000	,	/** 金钱仓库上限 ，默认为一亿银.*/
	
	uw_pet		varchar(10)								,	/** 宠物仓库,储存的是宠物数量 */
	uw_money_number	varchar(15)								,	/** 金钱仓库 */
	uw_pet_number	int 						default 5	,  	/** 宠物仓库上限，默认为5 */
	uw_warehouse_spare int									,	/** 物品仓库空余格数 */
	create_time 	datetime 								,	/** 创建时间*/
	
	prop_bonding	int										,	/** 道具绑定 */
	prop_protect	int										,	/** 道具保护*/
	prop_isReconfirm	int									, 	/** 道具二次确定*/
	prop_use_control	int									,	/** 道具是否可用*/ 	
	
	prop_operate1    varchar(50)							,	/** 道具特殊字段1 , 目前仅为捆装药使用 */	
	primary key (uw_id));	
	create index Index1 on u_warehouse_info (p_pk);
	create index Index2 on u_warehouse_info (uw_type,uw_prop_id);
	
   
/**********角色宠物表(p_pet_info)***************/ 
create table p_pet_info(
	pet_pk			 int not null auto_increment , /**ID */
	p_pk			 int 					                   , /**角色id*/
	pet_id			 int					                   , /**对应pet表里的id*/
	pet_name		 varchar(20)				               , /**宠物名称*/
	pet_nickname	 varchar(20)				               , /**宠物昵称*/
	pet_grade		 int					                   , /**等级*/
	pet_exp			 int			                           , /**经验*/
	pet_ben_exp		 int			                  default 0, /**当前经验经验*/
	pet_xia_exp		 int                                       , /**下级经验达到下一级需要的经验*/
	pet_gj_xiao      int                     		           , /**最小攻击*/
	pet_gj_da        int					                   , /**最大攻击*/
	pet_sale		 int			                           , /**卖出价格*/
	pet_img			 varchar(50)                              , /**宠物图片*/
	pet_grow	     double                                    , /**宠物成长率”*/
	pet_wx           int                  			           , /**五行属性金=1，木=2，水=3，火=4，土=5 */
    pet_wx_value     int           				               , /**五行属性值*/
    pet_skill_one	 int                                       , /**技能1	可学习的技能id*/
    pet_skill_two	 int                          	           , /**技能2	可学习的技能id*/
    pet_skill_three	 int                        	           , /**技能3	可学习的技能id*/
    pet_skill_four	 int                          		       , /**技能4	可学习的技能id*/
    pet_skill_five	 int                                  	   , /**技能5	可学习的技能id*/
    pet_life		 int			                           , /**寿命**/
    pet_isAutoGrow   int                                       , /**升级	是否可自然升级*/	
    pet_isBring		 int					                   , /**是否在身上:1表示在战斗状态，0表示否*/
    pet_fatigue		 int			 	                       , /**疲劳度0-100,出战状态下增加疲劳度，一个小时加10点*/
    pet_longe		 int			 	                       , /**宠物寿命*/
    longe_number     int			 	                       , /**寿命道具使用次数*/
    longe_number_ok  int			 	                       , /**寿命道具已经使用次数*/
    skill_control    int			 	                       , /**这个宠物最多可以学习多少个技能*/
    pet_type         int                                       , /**宠物类型*/
    pet_init_num     int                              default 0, /**洗宠物的次数*/
    pet_violence_drop double                                   , /**宠物暴击率*/
    primary key (pet_pk)) ENGINE=innodb;
create index Index1 on p_pet_info (pet_pk,p_pk,pet_id);
create index Index2 on p_pet_info (p_pk,pet_isBring);
    
    
/**********角色宠物交易表(u_pet_sell)***************/ 
create table u_pet_sell(
	ps_pk			     int not null auto_increment , /**ID */
	p_pk			     int 					                   , /**请求角色id*/
	p_by_pk			     int 					                   , /**被请求角色id*/ 
	pet_id			     int					                   , /**对应pet表里的id*/
	ps_silver_money      int                                       , /**发出请求要物品的价格的银子*/
    ps_copper_money      int                                       , /**发出请求要物品的价格的铜钱*/
    create_time          datetime                                  , /**创建时间*/
    primary key (ps_pk)) ENGINE=innodb;
    create index Index1 on u_pet_sell (p_pk,p_by_pk);
    
    
/**********角色交易(u_sell_info)***************/
create table u_sell_info ( 
   s_pk                   int not null auto_increment ,	 /**id*/
   p_pk                   int                                       ,	 /**发出请求角色id*/
   p_by_pk                int                                       ,	 /**被请求角色id*/
   s_wuping               int                                       ,    /**发出请求要交易的物品*/
   s_wp_type              int                                       ,    /**发出请求要物品类型*/
   s_wp_number            int                                       ,    /**发出请求要物品的数量*/ 
   s_wp_silver_money      int                                       ,    /**发出请求要物品的价格的银子*/
   s_wp_copper_money      int                                       ,    /**发出请求要物品的价格的铜钱*/ 
   sell_mode              int                                       ,    /**发出请求要交易的银子*/
   create_time            datetime                                  ,    /**创建时间*/
   primary key (s_pk)) ENGINE=innodb;
   create index Index1 on u_sell_info (p_pk,p_by_pk);
   
   
/****************************************LS****************************/
      

  
  /**********************************打怪掉落物品表***********************************************/
  create table n_dropgoods_info(
  d_pk						 int NOT NULL AUTO_INCREMENT ,/**ID */
  p_pk						 int					   				   ,/**角色id*/
  drop_num					 int									   ,/*掉落数量*/
  goods_id					 int 									   ,/*物品id*/
  goods_name				 varchar(20)							   ,/*物品名字*/
  goods_type				 int									   ,/*物品类型*/
  goods_quality              int                              default 0,/**掉落物品的品质 0表示普通，1表示优秀，2表示良，3表示极品*/
  goods_importance		  int								default  0, /** 物品是否重要，1为重要,0为不重要。如果重要的话,就发系统消息  */
  goods_dropInfo          varchar(100)				  				, /***    掉落信息提示  *****/
  primary key (d_pk)) ENGINE=innodb; 
  create index Index1 on n_dropgoods_info (p_pk);
  
/**********************************打怪时npc掉经验和钱的临时表***********************************************/
    
 create table n_dropExpMoney_info(
   d_pk							 int NOT NULL AUTO_INCREMENT ,/**ID */
   p_pk							 int					   				   ,/**角色id*/
   drop_exp					     int									   ,/*npc掉落经验*/	
   drop_money				     int									   ,/*npc掉落的钱数*/
   drop_pet_exp					 int									   ,/** npc给宠物掉落的经验 */		
   primary key (d_pk)) ENGINE=innodb;
     
   
  /*****************************pk通知(u_pk_notify)*****************************************/
   create table u_pk_notify(
    n_pk                                 int not null auto_increment        , /**id*/
    notifyed_pk                          int                                              , /**被通知的玩家id*/
    create_notify_pk                     int                                              , /**产生通知的玩家id*/ 
    notify_content                       varchar(200)                                     , /**通知的内容**/
    notify_type                          int                                              , /**通知类型：1.受到攻击***/
    create_time                          datetime                                         , /**创建时间*/
   primary key(n_pk));
  create index Index1 on u_pk_notify (notifyed_pk);
    
/*************************玩家快捷键设置（u_shortcut_info）********************************/
    create table u_shortcut_info(
    sc_pk							 int not null auto_increment   ,/**id*/ 
    p_pk							 int										 ,/**玩家id*/
    sc_name							 varchar(20)                                 ,/**快捷键名字，标号*/
    sc_display						 varchar(20)                                 ,/**设置后显示的名字，例如；技能名称,药品名称*/
    sc_type							 int                                         ,/**类型,值为-1时表示没有设置快捷键*/
    operate_id						 int                                         ,/**操作id*/
    object							 int                                         ,/**作用对象*/          
    primary key(sc_pk));
create index Index1 on u_shortcut_info(p_pk);

/*************************玩家设置自动打怪（u_automatism）********************************/
    create table u_automatism(
    a_pk							 int not null auto_increment   ,/**id*/ 
    p_pk							 int										 ,/**玩家id*/ 
    sc_type							 int                                         ,/**类型,值为-1时表示没有设置快捷键*/
    operate_id						 int                                         ,/**操作id*/
    object							 int                                         ,/**作用对象*/          
    primary key(a_pk));
create index Index1 on u_automatism (p_pk);

/*************************玩家包裹里的道具（u_propgroup_info）,每条记录：记录的是一组道具********************************/
  create table u_propgroup_info(
  	pg_pk	                         int not null auto_increment       ,   /**id*/ 
    p_pk                             int                                             ,	 /**角色id*/
    pg_type                          int                                             ,	 /**道具组分类*/ 
    prop_id			                 int                                             ,	 /**道具id*/
    prop_type						 int                                             ,   /**道具类型*/
    prop_bonding                     int                                             ,   /**道具绑定**/
    prop_protect                     int                                             ,
    prop_isReconfirm                 int                                             ,											
    prop_use_control                 int                                    default 0,  /**控制道具是否可用，0表示不受限制，1表示战斗时不可用*/
    prop_name	                     varchar(20)                                     ,  /**道具名字*/
    prop_price                       int                                             ,  /**卖出价钱*/
    prop_num                         int                                             ,	/**道具数量，不能超过限制格数*/ 
    create_time                      datetime                                        ,  /**创建时间*/    
    primary key(pg_pk)) ENGINE=innodb;
 create index Index1 on u_propgroup_info (p_pk,prop_id);
 create index Index2 on u_propgroup_info (p_pk,prop_type);
    
  /*************************玩家坐标标记记录_标记道具用（u_coordinate_info）********************************/
  create table u_coordinate_info(
  c_pk	                         smallint unsigned not null auto_increment       , /**id*/
  p_pk                           int                                             , /**角色id*/
  coordinate_prop_id             int                                             , /**坐标道具id*/
  coordinate                     int                                             , /**标记坐标*/
  prop_isUse                    int                                     default 0, /**标记道具是否使用*/
  primary key(c_pk));
create index Index1 on u_coordinate_info (p_pk,coordinate_prop_id);
  
   
   
  /*******************************组队通知表（u_groupnotify_info）************************************************************/
  create table u_groupnotify_info(
  n_pk                                 int not null auto_increment        , /**id*/ 
  notifyed_pk                          int                                              , /**被通知的玩家id*/
  create_notify_pk                     int                                              , /**产生通知的玩家id*/ 
  notify_content                       varchar(200)                                     , /**通知的内容**/
  notify_type                          int                                              , /**通知类型：1：通知有人申请组队；2：通知队伍解散；3.通知对方同意组队；4：。。。***/
  create_time                          datetime                                         , /**创建时间*/
  notify_flag                          int                                     default 0, /**通知标识，0表示没有通知，1表示已通知*/
  primary key(n_pk));
  create index Index1 on u_groupnotify_info(notifyed_pk,n_pk);
    
    
    /**********角色技能表(u_skill_info)***************/
   create table u_skill_info ( 
   s_pk             int not null auto_increment ,	 /**id*/ 
   p_pk             int                                       ,	 /**角色id*/
   sk_id         	int                                       ,	 /**技能id*/ 
   sk_name			varchar(20)				    			  ,	 /**技能名字*/ 
   sk_sleight       int                              default 0,  /**技能熟练度，即使用次数*/
   sk_usetime		datetime								  ,  /**本技能上一次的使用的时间*/
   create_time      datetime                                  ,  /**创建时间*/
   
   sk_type               int                                       , /**技能类型*/        
   sk_gj_multiple        double                                    , /** 物理攻击力加成*/  
   sk_fy_multiple        double                                    , /**防御力加成*/      
   sk_hp_multiple        double                                    , /**HP加成*/          
   sk_mp_multiple        double                                    , /**MP加成*/          
   sk_bj_multiple         double                                    , /**暴击率加成*/      
   sk_gj_add             int                                       , /**增加物理攻击力*/  
   sk_fy_add             int                                       , /**增加防御力*/      
   sk_hp_add             int                                       , /**增加HP*/          
   sk_mp_add             int                                       , /**增加MP*/    
   sk_group              int                                        ,  /**技能组*/
   primary key (s_pk)) ENGINE=innodb; 
create index Index1 on u_skill_info (p_pk,sk_id,sk_type);
create index Index2 on u_skill_info (p_pk,sk_group);
   
   
     /***********************************************buff使用效果(u_buffeffect_info)***********************************/
  create table u_buffeffect_info  (
  bf_pk                       int not null auto_increment                  , /**id*/ 
  buff_id                     int                                                        , /**buff_id*/
  buff_name                   varchar(20)                                                , /**名称*/	
  buff_display                varchar(80)                                                , /**buff描述*/
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
  create index Index1 on u_buffeffect_info(buff_bout,effect_object);
  create index Index2 on u_buffeffect_info(effect_object,effect_object_type,buff_type);
  
    /*************************控制表（需要时间或使用次数控制的对象）（u_time_control）********************************************/
    create table u_time_control(
    id	                         int not null auto_increment       ,   /**id*/ 
    p_pk                         int                                             ,   /**角色id*/
    object_id                    int                                             ,	 /**需要控制的对象*/
    object_type                  int                                             ,   /**需要控制的对象的类型，如：道具，菜单等*/
    use_datetime                 datetime                                        ,   /**记录最后一次的使用时间*/
    use_times                    int                                             ,   /**记录当天的使用次数*/
    primary key(id));
create index Index1 on u_time_control (p_pk);
     
/**********任务(u_task)***************/
 create table u_task ( 
   t_pk                   int not null auto_increment ,/**任务id*/
   p_pk                   int                                       ,/**角色id*/
   p_name                 varchar(20)                               ,/**角色名称*/
   t_zu                   varchar(50)                               ,/**任务组*/
   t_px                   varchar(20)                               ,/**任务排序*/
   t_id                   int                                       ,/**任务的ID*/
   t_title                varchar(50)                               ,/**任务的标题*/ 
   t_type                 int                                       ,/**任务类型*/
   t_xrwnpc_id            int                                       ,/**下一步任务开始npc的id*/
   t_next	              int                                       ,/**下一个任务id*/
   
   t_point                varchar(200)                              ,/**中间点	如有多个中间点应用，隔开*/	 
   t_point_no             varchar(200)                              ,/**是否完成中间点的ID用，隔开*/ 
   t_zjdwp                varchar(500)                              ,/**通过中间点需要的物品	*/
   t_zjdwp_number         int                                       ,/**通过中间点需要物品的数量*/
   t_zjdwp_ok	          int                              default 0,/**完成物品数量*/ 
   t_zjdzb                varchar(500)                              ,/**通过中间点需要的装备	*/
   t_zjdzb_number         int                                       ,/**通过中间点需要装备的数量*/
   t_zjdzb_ok	          int                              default 0,/**完成物品数量*/ 
   t_djscwp               int                                       ,/**通过中间点是否删除物品*/
   t_djsczb               int                                       ,/**通过中间点是否删除装备*/
   t_midst_gs             varchar(500)                              , /**通过中间点给的物品*/
   t_midst_zb             varchar(500)                              , /**通过中间点给的装备*/
   
   t_goods	              varchar(500)                              ,/**完成任务需要物品*/
   t_goods_no	          varchar(500)                              ,/**完成任务需要物品数量*/
   t_goods_ok	          varchar(500)                              ,/**完成物品数量*/ 
   t_goodszb              varchar(500)                              ,/**完成任务需要装备*/ 
   t_goodszb_number       varchar(500)                              ,/**完成任务需要装备数量*/
   t_goodszb_ok	          varchar(500)                              ,/**完成装备数量*/
   t_killing	          varchar(500)                              ,/**完成任务需要的杀戮*/
   t_killing_no           int                                       ,/**完成任务需要的杀戮数量*/
   t_killing_ok           int                              default 0,/**完成杀戮数量*/
   t_pet                  int                                       ,/**完成任务需要宠物*/
   t_pet_number           int                                       ,/**完成任务需要宠物数量*/
   t_pet_ok               int                              default 0,/**完成宠物数量*/
   create_time            datetime                                  ,/**任务领取时间*/
   t_time                 int                                       ,/**任务时间（分钟）*/
   t_give_up              int                              default 0,/**是否为放弃任务 0没有放弃 1放弃*/
   up_task_id             int                                       ,/**上一条任务的ID*/
   primary key (t_pk)) ENGINE=innodb;
   create index Index1 on u_task (t_pk,p_pk,t_id);
   create index Index2 on u_task (p_pk,t_zu);
   
/**********玩家已经完成过的任务(u_task_complete)***************/
create table u_task_complete(
  	c_pk	               int not null auto_increment , /**玩家已经完成过的任务id*/ 
    p_pk                   int                                       ,/**角色id*/
    task_zu                varchar(50)                               ,/**已经完成的任务组*/
    primary key(c_pk));
    create index Index1 on u_task_complete (p_pk,task_zu);
    
     
/**********帮会(u_tong)***************/
create table u_tong(
  	t_pk	               int not null auto_increment , /**帮会id*/ 
    t_name                 varchar(20)                               , /**帮会名称*/
    t_grade                int                                       , /**帮会等级*/
    t_member_numb          int                                       , /**帮派人数上线*/
    t_nonce_numb           int                                       , /**帮派当前人数*/
    t_camp                 varchar(50)                              , /**帮派阵营*/
    t_league               varchar(500)                             , /**帮派同盟只能加3个同盟 同盟ID 用,分割*/
    t_foe                  varchar(500)                             , /**帮派敌人只能加5个敌人 敌人ID 用,分割*/
    t_alter_time           int                                       , /**上次改变阵营时间一个月30天才能改变下一次 格式为 20080808*/
    create_time            datetime                                  , /**建帮派时间*/
    primary key(t_pk));
    create index Index1 on u_tong (t_pk,t_camp);
    
/**********帮会战争(u_tong_battle)***************/
create table u_tong_battle(
  	b_pk	               int not null auto_increment , /**帮会id*/ 
    t_apply_pk             int                                       , /**发动帮派ID*/
    t_accept_pk            int                                       , /**接受帮派*/
    t_begin_time           datetime                                  , /**帮战开始时间*/
    t_end_time             datetime                                  , /**帮战结束时间*/
    primary key(b_pk));
    create index Index1 on u_tong_battle (t_apply_pk,t_accept_pk);

/**********帮会关系(u_tong_relation)***************/
create table u_tong_relation(
  	r_pk	               int not null auto_increment , /**帮会关系id*/ 
  	t_pk                   int                                       , /**帮会ID*/
  	r_tong_pk              int                                       , /**关系帮会ID*/
    r_name                 varchar(20)                              , /**关系帮会名称*/
    r_relation             int                                       , /**关系帮会 1 同盟 2 敌对*/
    primary key(r_pk));
    create index Index1 on u_tong_relation (t_pk,r_tong_pk);

/**********帮会成员(u_tong_member)***************/
create table u_tong_member(
    tm_pk	               int not null auto_increment , /**帮会成员id*/ 
  	t_pk 	               int                                       , /**帮会id*/ 
  	p_pk                   int                                       , /**人员ID*/ 
  	p_name                 varchar(20)                              , /**人员名称*/ 
    tm_rights              int                                       , /**帮派职位 1 帮主 2 副帮主 3 护法 4香主 5普通成员*/
    tm_title               varchar(20)                              , /**人员称谓*/ 
    tm_proffer             int                                       , /**帮派贡献值*/ 
    battle_numb            int                            default 0 , /**帮战中杀人数量*/ 
    join_time              datetime                                  , /**加入帮派时间*/ 
    tong_rights            varchar(50)                              , /**帮会权限*/
    primary key(tm_pk));
    create index Index1 on u_tong_member (t_pk,p_pk,tm_rights);

/**********帮会成员荣誉(u_tong_glory)***************/
create table u_tong_glory(
    g_pk	               int not null auto_increment , /**帮会成员荣誉id*/ 
  	t_pk 	               int                                       , /**帮会id*/
  	p_pk                   int                                       , /**人员ID*/ 
  	p_name                 varchar(20)                              , /**人员名称*/ 
  	kill_number            int                              default 0, /**当天杀死人的数量*/
    intraday_value         int                              default 0, /**当天荣誉值*/
    glory_value            int                                       , /**总荣誉值*/
    primary key(g_pk)) ENGINE=innodb;
    create index Index1 on u_tong_glory (t_pk,p_pk,glory_value);
    
/**********帮会成员杀人记录只记录当天的(u_tong_kill)***************/
create table u_tong_kill(
    k_pk	               int not null auto_increment , /**帮会成员荣誉id*/ 
  	t_pk 	               int                                       , /**帮会id*/ 
  	p_pk                   int                                       , /**人员ID*/
  	by_pPk                 int                                       , /**被杀人员ID*/ 
  	kill_number            int                              default 0, /**被杀人员数量*/
    primary key(k_pk));
    create index Index1 on u_tong_kill (t_pk,p_pk,by_pPk);
    
/**********帮会权限(u_tong_rights)***************/
create table u_tong_rights(
    tr_pk	               int not null auto_increment , /**帮会权限id*/
    t_type	               int                                       , /**权限分类 1人事 2行政 3经济 4军事 */  
  	tr_name                varchar(20)                              , /**权限名称*/ 
    tr_rights              varchar(100)                              , /**人员权限 1 帮主 2 副帮主 3 护法 4香主 5普通成员 多角色用,分割*/ 
    url_join               varchar(100)                              , /**url参数*/ 
    primary key(tr_pk));
    create index Index1 on u_tong_rights (t_type);
  
/**********申请加入帮会(u_tong_beg)***************/
 create table u_tong_beg(
    tb_pk	               int not null auto_increment , /**申请加入帮会id*/
    t_pk	               int                                       , /**帮会id*/ 
    t_name                 varchar(20)                              , /**帮会名称*/
    p_pk                   int                                       , /**人员ID*/ 
    p_name                 varchar(20)                              , /**人员名称*/
    primary key(tb_pk));
    create index Index1 on u_tong_beg (t_pk,p_pk);
  
/**********帮会公告(u_tong_affiche)***************/
 create table u_tong_affiche(
    ta_pk	               int not null auto_increment , /**帮会公告id*/
    t_pk	               int                                       , /**帮会id*/ 
    ta_title               varchar(1000)                             , /**帮会公告名称*/
    create_time            datetime                                  , /**帮会公告发布时间*/ 
    primary key(ta_pk));  
    create index Index1 on u_tong_affiche (t_pk);
  
/**********转让帮会(u_tong_attorn)***************/
 create table u_tong_attorn(
    ta_pk	               int not null auto_increment , /**帮会公告id*/
    t_pk	               int                                       , /**帮会id*/
    p_pk_big               int                                       , /**被转让帮主的ID*/
    create_time            datetime                                  , /**转让时间*/
    at_term                datetime                                  , /**到期时间*/ 
    primary key(ta_pk));  
    create index Index1 on u_tong_attorn (t_pk,p_pk_big,create_time,at_term);
  
/**********解散帮会(u_tong_disband)***************/
 create table u_tong_disband(
    td_pk	               int not null auto_increment , /**解散帮会id*/ 
    t_pk	               int                                       , /**帮会id*/
    create_time            datetime                                  , /**解散时间*/
    at_term                datetime                                  , /**到期时间*/ 
    primary key(td_pk));    
    create index Index1 on u_tong_disband (t_pk,create_time,at_term);
  
/**********发布招募(u_tong_recruit)***************/
 create table u_tong_recruit(
    re_pk	               int not null auto_increment , /**发布招募id*/ 
    t_pk	               int                                       , /**帮会id*/
    re_title               varchar(500)                              , /**帮会宣言*/
    re_money               int                                       , /**竞标价格*/
    create_time            datetime                                  , /**发布时间*/
    re_term                datetime                                  , /**到期时间*/ 
    primary key(re_pk));   
    create index Index1 on u_tong_recruit (t_pk,re_money);
     
 
/*********拍卖表(u_auction)******************************/
  create table u_auction(
  	auction_id			int not null auto_increment , /**拍卖id*/
  	u_pk				int 									,	/** 个人id */
  	p_pk				int 									,	/** 角色id */
  	auction_type		int 									,	/** 物品类型， 1为药品，2为书卷，3为材料，4为商场，5为武器，6为防具，7为首饰，8为其他 */
  	goods_id			int 									,	/** 物品id */
  	goods_name			varchar(20)						    	,	/** 物品名称 */
  	goods_number 		int										,	/** 物品数量 */
  	auction_price		varchar(50)								,	/** 拍卖价格 */
  	buy_price			varchar(50)								,	/** 买家愿意出的价格 */
  	auction_time		datetime								,	/** 拍卖开始时间 */
  	auction_failed		int										,	/** 是否流拍，1为正在拍卖，2为流拍中,3为没收 */
  	auction_sell		int										,	/** 是否已卖出 ，1为未卖出，2为已卖出 */
  	buy_name 			varchar(20)  							,	/** 购买者姓名 */
  	
  	prop_use_control int										,	/** 道具是否可用,0表示不受限制,1表示战斗时不可用. */
  	table_type 		 int										,	/** 物品表类型 */
    goods_type       int                                       ,   /** 物品类型*/
    w_durability     int                                       ,   /**耐久*/ 
    w_dura_consume   int                                       ,   /**耐久消耗*/
    w_Bonding        int                                       ,   /**绑定*/ 
    w_protect        int                                       ,   /**保护*/ 
    w_isReconfirm    int                                       ,   /**二次确认*/ 
    w_price          int                                       ,   /**卖出价钱*/
    w_fy_xiao        int                               default 0,  /**附加属性最小防御*/
    w_fy_da          int                               default 0,  /**附加属性最大防御*/ 
    w_gj_xiao        int                               default 0,  /**附加属性最小攻击*/
    w_gj_da          int                               default 0,  /**附加属性最大攻击*/ 
    w_hp             int                               default 0,  /**附加属性气血*/
   w_mp             int                               default 0,  /**附加属性法力*/
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
   w_quality        int                                        ,  /** 品质*/
   w_wx_type        int                                        ,  /**装备五行类型*/
   suit_id          int          					  default 0,  /**对应套装id,不是套装默认为0*/
   w_buff_isEffected  int                             default 0,  /**附加buff是否有效，0表示无效，1表示有效*/
    enchant_type		varchar(20)						  default 0,  /** 点化属性. */
   enchant_value	int								  default 0,  /**  点化属性值 **/		
   w_zj_hp		    varchar(10)                             ,  /* 追加气血 */
   w_zj_mp			varchar(10)                             ,  /* 追加内力 */	
   w_zj_wxgj		    varchar(10)                             ,  /* 追加五行攻击 */
   w_zj_wxfy			varchar(10)                             ,  /* 追加五行防御 */ 		
   w_zb_grade		int										,		/** 装备等级 */
   w_Bonding_Num      int                             default 0,   /**解除绑定次数绑定*/
   specialcontent     int                             default 0,   /**武器的属性*/					
  	primary key(auction_id));
  create index Index1 on u_auction (auction_type);
    create index Index2 on u_auction (auction_failed,auction_sell);
  
  /*********拍卖信息表(u_auction_info)******************************/
  create table u_auction_info(
  	auction_info_id			int not null auto_increment , /**拍卖信息id */
  	p_pk					int 									 ,	/** 个人角色id */
  	auction_info			varchar(200)							 ,	/** 拍卖信息提示 */
  	addInfoTime				datetime 								,	/** 增加信息时间 */
  	primary key(auction_info_id));
	 create index Index1 on u_auction_info (p_pk);

/**********玩家好友(u_friend)***************/
 create table u_friend(
    f_pk	               int not null auto_increment , /**玩家好友id*/ 
    p_pk	               int                                       , /**玩家id*/
    fd_pk                  int                                       , /**好友ID*/
    fd_name                varchar(20)                              , /**好友名称*/
    fd_online              int                              default 0, /**好友是否在线 0 不在线 1在线*/
    create_time            datetime                                  , /**加入时间*/ 
    relation               int                              default 0,/**关系，0普通好友，1结义，2结婚*/
    dear                   int                              default 0,/**亲密度*/
    exp_share              int                              default 0,/**经验分享*/
    love_dear              int                              default 0,/**爱情甜蜜度*/
    tim                    varchar(20)                      default null,/**状态修改时间*/
    
    primary key(f_pk));
    create index Index1 on u_friend (p_pk,fd_pk);
    
/**********黑名单(u_blacklist)***************/
 create table u_blacklist(
    b_pk	               int not null auto_increment , /**玩家好友id*/ 
    p_pk	               int                                       , /**玩家id*/
    bl_pk                  int                                       , /**好友ID*/
    b_name                 varchar(20)                              , /**好友名称*/
    create_time            datetime                                  , /**加入时间*/ 
    primary key(b_pk));
    create index Index1 on u_blacklist (p_pk,bl_pk);

    
/**********系统消息表***********************/
create table s_system_info(
	sysInfo_id				int  not null auto_increment , /*** 系统消息表id***/
	p_pk 					int 								,	/*** 个人角色id***/
	info_type				int 								,	/*** 消息类型( 1为用户信息，2为特别通知，3为系统公告 ) ***/
	system_info				varchar(300)						,	/*** 系统消息内容 ***/
	happen_time 			datetime							,	/*** 发生时间 ***/
	primary key(sysInfo_id));
	create index Index1 on s_system_info (p_pk,info_type);
	create index Index2 on s_system_info (info_type,happen_time);
	
/**********邮件表(u_mail_info)***************/
create table u_mail_info ( 
	mail_id 		int not null auto_increment 		,	/*** 邮件表id **/	
	receive_pk 		int												,	/**  收信人id **/
	send_pk			int 											,	/**	 发信人id **/
	mail_type		int												,	/**  邮件类型，1为普通，2为系统**/
	title 				varchar(100)									, 	/**  标题  **/
	content 			text(1000)									,	/**  邮件内容  **/
	unread				int												,	/**	 是否阅读过，1为未读，2未已读 **/
	improtant			int												,	/**  重要性，在玩家看时会以此作优先排序，默认为1，数字越大越靠前  **/		
	create_time			datetime										,	/**	 邮件发送时间，在重要性排序一致的情况下以此排序  ***/
	primary key (mail_id));
	create index Index1 on u_mail_info (receive_pk);
	
	
	/**********宠物拍卖场表(u_auction_pet)***************/ 
create table u_auction_pet(
	auction_id		 int not null auto_increment ,/** 拍卖场宠物id */
	auction_status	 int 										,/** 宠物拍卖状态，1为正在拍卖，2为拍卖失败,等待取回，3为玩家未取回,等待删除，4为拍卖成功，等待玩家取回金钱. */
	pet_price		 int 										,/** 宠物拍卖价格  */
	pet_auction_time datetime									,/** 宠物拍卖时间 */	
	pet_pk			 int									   , /** 角色宠物ID */
	
	p_pk			 int 					                   , /**角色id*/
	pet_id			 int					                   , /**对应pet表里的id*/
	pet_name		 varchar(20)				               , /**宠物名称*/
	pet_nickname	 varchar(20)				               , /**宠物昵称*/
	pet_grade		 int					                   , /**等级*/
	
	pet_exp			 varchar(15)			                   , /**经验*/
	pet_ben_exp		 varchar(15)			          default 0, /**当前经验经验*/
	pet_xia_exp		 varchar(15)                               , /**下级经验达到下一级需要的经验*/
	pet_gj_xiao      int                     		           , /**最小攻击*/
	pet_gj_da        int					                   , /**最大攻击*/
	
	pet_sale		 int			                           , /**卖出价格*/
	pet_img			 varchar(100)                              , /**宠物图片*/
	pet_grow	     double                                    , /**宠物成长率”*/
	pet_wx           int                  			           , /**五行属性金=1，木=2，水=3，火=4，土=5 */
    pet_wx_value     int           				               , /**五行属性值*/
    
    pet_skill_one	 int                                       , /**技能1	可学习的技能id*/
    pet_skill_two	 int                          	           , /**技能2	可学习的技能id*/
    pet_skill_three	 int                        	           , /**技能3	可学习的技能id*/
    pet_skill_four	 int                          		       , /**技能4	可学习的技能id*/
    pet_skill_five	 int                                  	   , /**技能5	可学习的技能id*/
    
    pet_life		 int			                           , /**寿命**/
    pet_isAutoGrow	 int									   , /** 是否可自然升级 **/
    pet_type         int                                       , /**升级	是否可自然升级*/	
    pet_isBring		 int					                   , /**是否在身上:1表示在战斗状态，0表示否*/
    pet_fatigue		 int			 	                       , /**疲劳度0-100,出战状态下增加疲劳度，一个小时加10点*/
    pet_longe		 int			 	                       , /**宠物寿命*/
    
    longe_number     int			 	                       , /**寿命道具使用次数*/
    longe_number_ok  int			 	                       , /**寿命道具已经使用次数*/
    skill_control    int			 	                       , /**这个宠物最多可以学习多少个技能*/
    pet_init_num	 int									   , /** 宠物初始化次数 **/
    
        pet_violence_drop  double									,	/** 宠物暴击率 */
 primary key (auction_id));
 create index Index1 on u_auction_pet (auction_status);
 
   /*********宠物拍卖信息表(u_auctionpet_info)******************************/
  create table u_auctionpet_info(
  	auctionpet_info_id			int not null auto_increment , /**宠物拍卖信息id */
  	p_pk						int 									 ,	/** 个人角色id */
  	auction_pet_info			varchar(200)							 ,	/** 宠物拍卖信息提示 */
  	addInfoTime					datetime 								,	/** 增加信息时间 */
  	primary key(auctionpet_info_id));
  	 create index Index1 on u_auctionpet_info (p_pk);
  	
  	/*********系统设置表(s_setting_info)******************************/
  	create table s_setting_info (
  		setting_id			int not null auto_increment,	/** 系统设置表 */
  		p_pk				int										,	/** 人物角色id */
  		goods_pic			tinyint									default -1,	/**  物品图开关,1为开启，-1为关闭 */
  		person_pic			tinyint									default -1,	/**	 角色形象图开关,1为开启，-1为关闭 */
  		npc_pic				tinyint									default -1,	/**  npc怪物图开关,1为开启，-1为关闭 */
  		pet_pic				tinyint									default -1,	/**  宠物图开关,1为开启，-1为关闭 */
  		operate_pic			tinyint									default -1,	/**  npc人物图开关,1为开启，-1为关闭 */
  		deal_control		tinyint									default 1,	/**  交易控制开关,1为开启，-1为关闭 */
  		
  		public_comm			tinyint									default 1,	/**  公共聊天开关, 1为开启, -1为关闭 **/
  		camp_comm			tinyint									default 1,	/**  阵营聊天开关, 1为开启, -1为关闭  */
  		duiwu_comm			tinyint									default 1,	/**  队伍聊天开关，1为开启,	-1为关闭 */	
  		tong_comm			tinyint									default 1,	/**  帮派聊天开关,1为开启， -1为关闭  */
  		secret_comm			tinyint									default 1,	/**  秘密聊天开关，1为开启, -1为关闭  **/
  		index_comm			tinyint									default -1,	/**  秘密聊天开关，1为开启, -1为关闭  **/
  		npc_hp_position     tinyint                                 default -1, /**  -1表示默认，1表示npc血条在上边 */
  	primary key(setting_id));
  	 create index Index1 on s_setting_info (p_pk);

 /**********角色仓库装备表(u_warehouse_equip)***************/
create table u_warehouse_equip ( 
   w_pk		        int not null auto_increment ,	  /**角色装备仓库表*/
   p_pk             int                                       ,	  /**角色id*/ 
   table_type       int                                       ,   /**物品相关表类型*/
   goods_type       int                                       ,   /**物品类型*/
   w_id             int                                       ,   /**物品ID*/
   w_name           varchar(20)                               ,   /**物品名称*/
   w_durability     int                                       ,   /**耐久*/ 
   w_dura_consume   int                                       ,   /**耐久消耗*/
   w_Bonding        int                                       ,   /**绑定*/ 
   w_protect        int                                       ,   /**保护*/ 
   w_isReconfirm    int                                       ,   /**二次确认*/ 
   w_price          int                                       ,   /**卖出价钱*/
   w_fy_xiao        int                               default 0,  /**附加属性最小防御*/
   w_fy_da          int                               default 0,  /**附加属性最大防御*/ 
   w_gj_xiao        int                               default 0,  /**附加属性最小攻击*/
   w_gj_da          int                               default 0,  /**附加属性最大攻击*/ 
   w_hp             int                               default 0,  /**附加属性气血*/
   w_mp             int                               default 0,  /**附加属性法力*/
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
   w_quality        int                                        ,  /**品质*/
   
   w_wx_type        int                                        ,  /** 装备五行类型*/
   suit_id			int								  default 0,	/** 套装id */
   w_buff_isEffected  int                             default 0,  /**附加buff是否有效，0表示无效，1表示有效*/
   enchant_type		varchar(10)						  default 0,  /** 点化属性. */
   enchant_value	int								  default 0,  /**  点化属性值 **/	
   w_zj_hp		    varchar(10)                             ,  /* 追加气血 */
   w_zj_mp			varchar(10)                             ,  /* 追加内力 */	
   w_zj_wxgj		    varchar(10)                             ,  /* 追加五行攻击 */
   w_zj_wxfy			varchar(10)                             ,  /* 追加五行防御 */ 		
   w_zb_grade		int										,		/** 装备等级 */											
   create_time      datetime                                   ,  /** 创建时间*/ 
      p_poss             int                             default 0,  /**创建时间*/
   w_Bonding_Num      int                             default 0,   /**解除绑定次数绑定*/
   specialcontent     int                             default 0,   /**武器的属性*/
   primary key (w_pk)); 
  create index Index1 on u_warehouse_equip (p_pk);
   
					
  
  
  /****************用户答题积分表 *******************************/
  create table u_quiz_info (
  	id					 int  not null auto_increment				,	    /**  用户答题积分表id */
	p_pk				 int												,		/**  个人角色id   */
  	integral			 int												,		/**  用户积分  **/	
  	conunite_win	 int													,		/**  连续正确次数 **/
  	mouth			 varchar(10)											,		/**  月份  */
  	conunite_day	int														,		/**   每月答完十道题的天数  **/
  	last_time		datetime												,		/**  最后答题时间 */
  	answer_flag    		int													,		/**	  回答与否的标志	*/
  	primary key(id)); 
  	create index Index1 on u_quiz_info (p_pk);
  	
  	 /****************用户供捆装药品使用表 *******************************/
    create table u_special_prop (
    	id			 		 int  not null auto_increment		,	    /**  用户特殊道具表id */
    	p_pk				 int										,		/**  用户角色id   */
    	sp_type		 int										,			/** 药品类型,1为捆装药  **/
    	pg_pk			 int											,		/**  个人道具表id   */
  		prop_stock	 int												,		/**  道具存量  */
  		create_time	datetime											,		/**   创建时间 */
  primary key(id))  ENGINE=innodb;
  create index Index1 on u_special_prop (p_pk);
  
   	  
  /**********二级密码表(u_second_pass)***************/
create table u_second_pass(
	pass_id  			  int  not null auto_increment ,    /** 二次密码表：主键*/
	u_pk			  int								 	   ,	/**  角色id */
	second_pass		  varchar(20)							   ,	/**  角色二级密码  */	
	pass_first_time	  datetime								   ,	/**  角色首次修改二级密码错误时间   */						
	pass_second_time  datetime								   ,	/**  角色第二次修改二级密码错误时间 **/
	pass_third_time	  datetime								   ,	/**  角色第三次修改二级密码错误时间  */	
	pass_wrong_flag		mediumint									   ,	/**  二级密码三次错误标志 1为禁止使用二级密码, 0为可以使用  */	
	pass_mail_send		tinyint								    	,		/**  二级密码设置邮件是否已经发送过 */			
	primary key(pass_id));
    create index Index1 on u_second_pass (u_pk);
    
  /****************************************************************************************************************/
  
  
  /***********上线玩家记录表*********************/
  create table p_record_login(
  id				int  not null auto_increment		,	    /**  上线玩家记录表id */	
  u_pk				int											,		/**  玩家id  */
  loginStatus		mediumint											,		/**  登陆状态 */
  loginTime			datetime									,		/**  最后上线时间  */	
  primary key(id));
  
 
 /***********上线玩家角色记录表*********************/
  create table user_record_login(
  id				int  not null auto_increment		,	    /**  上线玩家记录表id */	
  p_pk				int											,		/**  玩家角色id  */
  p_grade			int											,		/**  玩家等级   **/
  loginStatus		int											,		/**  登陆状态 */
  loginTime			datetime									,		/**  最后上线时间  */	
  primary key(id));
  create index Index1 on user_record_login (p_pk);
  	
 
  /*************** 玩家在线时长表 ***********************************/
  	create table user_online_time(
  		id			int  not null auto_increment		,	/**  玩家在线时长表id **/
  		u_pk		int											,	/*** 玩家id ***/
  		p_pk		int											,	/**  角色id  **/
  		onlinetime	int											,	/*** 在线时间长度 **/
  		recordTime		varchar(50)									,	/***  所记录日期 **/
  		createTime	   datetime									,	/**  创建时间 **/	
  	 primary key(id));
  	 
  	 
  
  /***********上线玩家等级表*********************/
  create table user_login_grade(
  	id 				int  not null auto_increment		,	/** 上线玩家等级表id **/
  	grade1to9		int											,	/**  等级1到9玩家数量 **/
  	grade10to19		int											,	/**  等级10到19玩家数量**/
  	grade20to29		int											,	/**  等级20到29玩家数量**/
 	grade30to39		int											,	/**  等级30到39玩家数量**/
    grade40to49		int											,	/**  等级40到49玩家数量 **/
  	grade50to59		int											,	/**  等级50到59玩家数量 **/
    grade60			int											,	/**  等级60玩家数量 **/
    record_date		varchar(50)									,	/**  记录日期 **/	
    record_time		datetime									,	/**  执行动作具体时间. **/
  primary key(id));			
  								
  								
   /***********沉默玩家等级表*********************/
  create table user_silence_grade(
  	id 				int  not null auto_increment		,	/**  沉默玩家等级表id **/
  	grade1to9		int											,	/**  等级1到9玩家数量 **/
  	grade10to19		int											,	/**  等级10到19玩家数量**/
  	grade20to29		int											,	/**  等级20到29玩家数量**/
 	grade30to39		int											,	/**  等级30到39玩家数量**/
    grade40to49		int											,	/**  等级40到49玩家数量 **/
  	grade50to59		int											,	/**  等级50到59玩家数量 **/
    grade60			int											,	/**  等级60玩家数量 **/
    record_date		varchar(50)									,	/**  记录日期 **/	
    record_time		datetime									,	/**  执行动作具体时间. **/
  primary key(id));			
  
  
  /*************** 日常等级表 ***********************************/
  	create table user_everyday_grade(
  		id				int  not null auto_increment		,	/** 日常等级表id **/
  		grade1			int											,	/**  等级为1的等级数量 */	
  		grade2to9		int											,	/**  等级2到9玩家数量 **/
  		grade10to19		int											,	/**  等级10到19玩家数量**/
  		grade20to29		int											,	/**  等级20到29玩家数量**/
 		grade30to39		int											,	/**  等级30到39玩家数量**/
   		grade40to49		int											,	/**  等级40到49玩家数量 **/
  		grade50to59		int											,	/**  等级50到59玩家数量 **/
    	grade60			int											,	/**  等级60玩家数量 **/
    	avg_grade       int												,/**玩家在线平均等级*/
  		recordTime		varchar(50)									,	/*** 所记录日期 **/
  		createTime	datetime										,	/**  创建时间 **/	
  	 primary key(id));	
  							
  		
  																																		
   /*************** 玩家在线时间段表 ***********************************/
	  CREATE TABLE user_online_num (
		  id int(11) NOT NULL AUTO_INCREMENT,/**主键**/
		  hour_0  smallint(6)    DEFAULT 0,/**0点在线人数统计**/
		  hour_1  smallint(6)    DEFAULT 0,/**1点...**/
		  hour_2  smallint(6)    DEFAULT 0,/**2点...**/
		  hour_3  smallint(6)    DEFAULT 0,/**3点...**/
		  hour_4  smallint(6)    DEFAULT 0,/**4点...*/
		  hour_5  smallint(6)    DEFAULT 0,/**5点...**/
		  hour_6  smallint(6)    DEFAULT 0,/**6点...**/
		  hour_7  smallint(6)    DEFAULT 0,/**7点...*/
		  hour_8  smallint(6)    DEFAULT 0,/**8点...*/
		  hour_9  smallint(6)    DEFAULT 0,/**9点...*/
		  hour_10 smallint(6)    DEFAULT 0,/**10点...*/
		  hour_11 smallint(6)    DEFAULT 0,/**11点...*/
		  hour_12 smallint(6)    DEFAULT 0,/**12点...*/
		  hour_13 smallint(6)    DEFAULT 0,/**13点...*/
		  hour_14 smallint(6)    DEFAULT 0,/**14点...*/
		  hour_15 smallint(6)    DEFAULT 0,/**15点...*/
		  hour_16 smallint(6)    DEFAULT 0,/**16点...*/
		  hour_17 smallint(6)    DEFAULT 0,/**17点...*/
		  hour_18 smallint(6)    DEFAULT 0,/**18点...*/
		  hour_19 smallint(6)    DEFAULT 0,/**19点...*/
		  hour_20 smallint(6)    DEFAULT 0,/**20点...*/
		  hour_21 smallint(6)    DEFAULT 0,/**21点...*/
		  hour_22 smallint(6)    DEFAULT 0,/**22点...*/
		  hour_23 smallint(6)    DEFAULT 0,/**23点...*/
		  createTime varchar(10) DEFAULT NULL,
		  PRIMARY KEY (id));
  	
  	/*************** 玩家信息总览表 ***********************************/
  	create table user_info_overview(
  		id						int  not null auto_increment		,	/** 日常等级表id **/
  		all_regist_num			int											,	/** 总注册人数 **/	
  		all_regist_user			int											,	/**	总注册角色  **/
  		today_regist_num		int											,	/** 今日注册人数 */
  		today_online_num		int											,	/** 今日上线人数 */
  		today_active_num		int											,	/** 今日活跃人数 */
  		today_avg_time 			int											,	/** 今日平均在线时间 */
  		today_avg_grade 		double											,	/** 今日平均在线等级 */
  		today_avg_num			double											,	/** 今日平均在线人数 */
  		today_grade_num         int                                         ,/**今日新建玩家是否超过指定等级*/
  		record_date				varchar(50)								,	/** 记录的日期  */
  		insert_time		datetime											,	/** 插入时间 */ 
  	 primary key(id));
  										
  /*************** 日常等级表 ***********************************/
  	create table user_grade_statistics (
  		id				int  not null auto_increment		,	/** 日常等级统计表id **/
  		time_10min		int												,	/**  游戏时间在10分钟以内的玩家数 **/
    	time_30min		int												,	/**  游戏时间在30分钟以内的玩家数 **/
    	time_60min		int												,	/**  游戏时间在60分钟以内的玩家数 **/
    	time_120min		int												,	/**  游戏时间在120分钟以内的玩家数 **/
    	time_120minup		int											,	/**  游戏时间在120分钟以上的玩家数 **/
    	recordTime		varchar(50)									,	/***  所记录日期 **/
  		createTime	datetime										,	/**   创建时间 **/	
  	 primary key(id));															
  	 
  	 
  	 
  /**********************************PK掉落物品表***********************************************/
  create table n_dropgoods_pk(
  dp_pk						 int NOT NULL AUTO_INCREMENT , /**ID */
  a_p_pk					 int					   				   , /**P死的角色id*/
  b_p_pk					 int					   				   , /**胜利的角色id*/
  drop_num					 int									   , /*掉落数量*/
  goods_id					 int 									   , /*物品id*/
  goods_name				 varchar(20)							   , /*物品名字*/
  goods_type				 int									   , /*物品类型*/
  goods_quality              int                              default 0, /**掉落物品的品质 0表示普通，1表示优秀，2表示良，3表示极品*/
  primary key (dp_pk));
    create index Index1 on n_dropgoods_pk (a_p_pk);
  
  /**********************************PK通知掉落物品表***********************************************/
  create table pk_dropgoods_notify(
  pn_pk						 int NOT NULL AUTO_INCREMENT , /**ID */
  a_p_pk					 int					   				   , /**P死的角色id*/
  b_p_pk					 int					   				   , /**胜利的角色id*/
  drop_num					 int									   , /*掉落数量*/
  goods_id					 int 									   , /*物品id*/
  goods_name				 varchar(20)							   , /*物品名字*/
  goods_type				 int									   , /*物品类型*/
  goods_quality              int                              default 0, /**掉落物品的品质 0表示普通，1表示优秀，2表示良，3表示极品*/
  primary key (pn_pk));
  
  /**********************************战斗中玩家与怪物气血位置上下开关u_hp_up***********************************************/
  create table u_hp_up(
  hu_pk						 int NOT NULL AUTO_INCREMENT , /**ID */
  p_pk					     int					   				   , /**P死的角色id*/
  hp_up                      int                              default 0, /**默认0为开NPC血条在上 1为NPC血条在下*/
  primary key (hu_pk));
  create index Index1 on u_hp_up (p_pk);
  
  
   /*********************************玩家分解装备表***********************************************/
  create table equip_decompose_info(
  de_id						 int NOT NULL AUTO_INCREMENT , /**ID */
  p_pk					     int					   				   , /**P死的角色id*/
  equip_id                      varchar(10)                      default 0, /** 装备id */
  equip_type                    varchar(10)                       default 0, /** 装备类型 */
  equip_name                    varchar(20)                       default 0, /** 装备类型 */
  equip_pose					int										  , /** 装备位置 */		
  pw_pk							int										  ,	/** 个人装备表id */
  deal_type						int								 		  , /** 处理类型,1为分解, 2为升级 **/						
  create_time					datetime								  ,	/** 装备插入时间 */	
  primary key (de_id)); 
  create index Index1 on equip_decompose_info (p_pk,deal_type);
  
  /**********************************彩票表u_lottery_number***********************************************/
  create table u_lottery_number(
  lottery_id						 int NOT NULL AUTO_INCREMENT , /**彩票ID */
  p_pk					     int					   				   , /**玩家ID*/
  lottery_number             varchar(200)                              , /**彩票号码*/
  lottery_type				 int									   , /**彩票类型*/
  lottery_per_money			 int									   , /**彩票投注金额*/
  primary key (lottery_id));
  
   /**********************************彩票表u_lottery_info***********************************************/
  create table u_lottery_info(
  lottery_info_id					 int NOT NULL AUTO_INCREMENT , /**彩票信息ID */
  p_pk					     int					   				   , /**玩家ID*/
  lottery_num                int                                       , /**投注次数*/
  lottery_win_num			 int									   , /**赢取彩票次数*/
  lottery_catch_money		 int									   , /**是否领奖金*/
  lottery_per_bonus          int                                       , /**每注奖金的金额*/
  lottery_bonus_multiple     int                                       , /**奖金倍数*/
  lottery_charity            int                                       , /**是否具有慈善投注资格*/
  lottery_all_bonus          int                                       , /**获得的总奖金*/
  primary key (lottery_info_id)); 
  
  /**********************************工资信息表u_laborage***********************************************/
  create table u_laborage(
  laborage_id					 int NOT NULL AUTO_INCREMENT , /**彩票信息ID */
  p_pk					     int					   				   , /**玩家ID*/
  laborage_this_time         int                                       , /**玩家活动时间统计*/
  laborage_old_time			 int									   , /**玩家上周总时间表*/
  laborage_catch	    	 int									   , /**是否领工资*/
  primary key (laborage_id));
   
  /**********************************角色登陆前奏记录表u_prelude***********************************************/
  create table u_prelude(
  pr_id					 int UNSIGNED NOT NULL AUTO_INCREMENT , /**角色登陆前奏记录表ID */
  p_pk				     int					   				   , /**玩家ID*/ 
  primary key (pr_id)); 
  create index Index1 on u_prelude (p_pk);
  
  /**********************************当乐用户和平台用户的关联表*************************************/
  create table u_passport_info(
  id					 int NOT NULL AUTO_INCREMENT               , /**ID */
  user_id			     varchar(36)					   UNIQUE  , /**渠道用户id*/
  user_name              varchar(25)                       UNIQUE  , /**渠道用户名称*/
  user_state             int                              default 1, /**渠道用户在平台的状态，1表示注册用户，2表示注销用户*/
  channel_id             varchar(10)                               , /**渠道id*/
  u_pk                   int                                       , /**游戏玩家账号id*/
  create_time            datetime                                  , /**创建时间*/
  primary key (id)); 
  create index Index1 on u_passport_info(user_id,channel_id);
  
  
  
      /**********************************当乐网内测大礼包统计u_information***********************************************/
  create table u_information(
  u_pk					     int					   				   , /**玩家账号ID*/
  id                         varchar(30)                               , /**当乐网ID*/
  type			             varchar(10)							    /**礼包类型*/
  ); 
  
  /************在线人数统计表********************/
CREATE TABLE t_online (
  onlineid int(11) NOT NULL AUTO_INCREMENT,/**主键**/
  onlinecount int(11) DEFAULT 0,/**在线人数**/
  PRIMARY KEY (onlineid)
);



/**********玩家特殊装备临时表(u_special_item)***************/
create table u_special_item ( 
   sp_pk             int not null auto_increment ,	
   p_pk                      int                                      ,/**ppk*/ 
   prop_id                  int                                      ,/**物品ID*/  
   prop_operate1      varchar(50)                     ,/**特殊字节1*/  
   prop_operate2      varchar(50)                     ,/**特殊字节2*/
   prop_operate3      varchar(50)                     ,/**特殊字节3*/
   prop_time              int                                      ,/**总时间*/
   prop_date             datetime                            , /**使用时间*/
   prop_sign               int                                      ,/**装备标记*/
   primary key (sp_pk));
     create index Index1 on u_special_item(p_pk);
	
	/*** *副本进度记录****/
	create table instance_archive(
	    p_pk                     int                                        ,		/** 角色id*/
	    map_id                   int                                        ,       /**副本所在map_id*/
	    dead_boss_record         varchar(200)                               ,       /**记录已打死的boss所在的sence_id,形式如：223,45,1132*/
	    create_time              datetime                                           /**进入副本时间*/      
	);
    

   
/***************帮派现有建筑表(u_tong_build)*********************************/
 create table u_tong_build(
   utb_id               smallint unsigned not null auto_increment , /**主键*/ 
   t_pk                 int                                       , /**帮派ID*/
   tb_id                int                                       , /**建筑id*/
   tb_name              varchar(50)                              , /**建筑名称*/ 
   tb_grade             int                                       , /**建筑等级*/
   tb_definition        varchar(200)                              , /**建筑说明*/
   tb_group             int                                       , /**建筑升级组*/
   tb_map               int                                       , /**建筑所在的地图*/
   primary key (utb_id)); 
   create index Index1 on u_tong_build (t_pk,tb_id,tb_group,tb_map);
    
/***************帮派资源的钱(u_tong_money)*********************************/
 create table u_tong_money(
   utm_id               smallint unsigned not null auto_increment , /**主键*/ 
   t_pk                 int                                       , /**帮派ID*/
   utm_money            varchar(20)                              , /**金库的钱文计算*/
   utm_wrap_content		int                            default 100, /**帮派资源容量*/
   utm_wrap_spare		int                            default 100, /**帮派资源剩余数量*/
   primary key (utm_id)); 
   create index Index1 on u_tong_money (t_pk,utm_wrap_spare);
 
/**********帮派资源装备表(u_tong_resource_equip)***************/
create table u_tong_resource_equip ( 
   re_pk            smallint unsigned not null auto_increment ,	  /**帮派资源装备表*/
   t_pk             int                                       ,   /**帮派ID*/
   table_type       int                                       ,   /**物品相关表类型*/
   goods_type       int                                       ,   /**物品类型*/
   w_id             int                                       ,   /**物品ID*/
   w_name           varchar(20)                               ,   /**物品名称*/
   w_durability     int                                       ,   /**耐久*/ 
   w_dura_consume   int                                       ,   /**耐久消耗*/
   w_Bonding        int                                       ,   /**绑定*/
   w_protect        int                                       ,   /**保护*/
   w_isReconfirm    int                                       ,   /**二次确认*/
   w_price          int                                       ,   /**卖出价钱*/
   w_fy_xiao        int                               default 0,  /**附加属性最小防御*/
   w_fy_da          int                               default 0,  /**附加属性最大防御*/ 
   w_gj_xiao        int                               default 0,  /**附加属性最小攻击*/
   w_gj_da          int                               default 0,  /**附加属性最大攻击*/ 
   w_hp             int                               default 0,  /**附加属性气血*/
   w_mp             int                               default 0,  /**附加属性法力*/
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
   w_quality        int                                        ,  /**品质*/
   suit_id          int                               default 0,  /**对应套装id,不是套装默认为0*/
   w_wx_type        int                                        ,  /**装备五行类型*/
   w_buff_isEffected  int                             default 0,  /**附加buff是否有效，0表示无效，1表示有效*/
   enchant_type		  varchar(10)					  default 0,  /**附加点化属性  */
   enchant_value	  int							  default 0,  /**附加点化属性值 */
   w_zj_hp		      varchar(10)                             ,  /**追加气血 */
   w_zj_mp			  varchar(10)                             ,  /**追加内力 */	
   w_zj_wxgj		  varchar(10)                             ,  /**追加攻击 */
   w_zj_wxfy	      varchar(10)                             ,  /**追加防御 */ 
   w_zb_grade		  int									   ,  /**装备的等级 */
   w_Bonding_Num      int                             default 0,   /**解除绑定次数绑定*/	
   primary key (re_pk));
   create index Index1 on u_tong_resource_equip (t_pk,goods_type);
 
/*************************帮派资源道具表(u_tong_resource_prop),每条记录：记录的是一组道具********************************/
  create table u_tong_resource_prop(
  	rp_pk	                         int  not null auto_increment                    ,  /**id*/ 
  	t_pk                             int                                             ,  /**帮派ID*/
    pg_type                          int                                             ,	/**道具组分类*/ 
    prop_id			                 int                                             ,	/**道具id*/
    prop_type						 int                                             ,  /**道具类型*/
    prop_bonding                     int                                             ,
    prop_protect                     int                                             ,
    prop_isReconfirm                 int                                             ,											
    prop_use_control                 int                                    default 0,  /**控制道具是否可用，0表示不受限制，1表示战斗时不可用*/
    prop_name	                     varchar(20)                                     ,  /**道具名字*/
    prop_price                       int                                             ,  /**卖出价钱*/
    prop_num                         int                                             ,	/**道具数量，不能超过限制格数*/
    auction_type                     int                                             ,  /**道具存储分类*/
    primary key(rp_pk));
    create index Index1 on u_tong_resource_prop (t_pk,pg_type,prop_id);
    
    
/*************************帮派资源道记录表LOG (u_tong_res_log)********************************/
    create table u_tong_res_log(
  	l_pk	                         varchar(20)                                     ,  /**id*/ 
  	t_pk                             int                                             ,  /**帮派ID*/
    l_content                        varchar(300)                                    ,	/**日志内容*/
    create_time                      datetime                                           /**日志记录时间*/
   ); 
    create index Index1 on u_tong_res_log (t_pk);
    
    
 /*************************玩家配方表(u_synthesize_info)********************************/
  create table u_synthesize_info(
  us_id                int  not null auto_increment            ,/**主键*/
  p_pk              int               ,/**玩家ppk*/
  s_id              int               ,/**配方ID*/
    primary key(us_id));
 
  /*************************系统统计表(game_statistics)********************************/
  create table game_statistics(
  gs_id                int  not null auto_increment            ,/**主键*/
  prop_id              int               ,/**物品ID*/
  prop_type            int               ,/** 物品类型 */
  prop_num             int               ,/** 物品数量 */
  prop_approach_type   varchar(20)       ,/** 物品获得途径类型 */
  prop_approach        varchar(20)       ,/** 判断物品是消耗得到还是库存 */
  date                 varchar(20)       ,/** 日期 */
  time                 varchar(20)       ,/** 时间 */
    primary key(gs_id))   ENGINE=innodb;


 /*************************系统需要统计道具表(game_statistics_prop)********************************/
  create table game_statistics_prop(
  gsp_id                int  not null auto_increment            ,/**主键*/
  prop_id              int               ,/**物品ID*/
  prop_type            int               ,/** 物品类型 */
  date                 varchar(20)       ,/** 日期 */
  time                 varchar(20)       ,/** 时间 */
    primary key(gsp_id));
    

    
/*************************记录带刷新时间的npc的上次的死亡时间(npc_dead_record)********************************/
create table npc_dead_record(
  id                   int          auto_increment            ,/**主键*/
  p_pk                 int                                    ,/**玩家id*/
  npc_id               int                                    ,/**NPC的ID*/
  scene_id             int                                    ,/** 场景ID */
  map_id               int                                    ,/**map的ID*/
  npc_deadtime         datetime                               ,/** 上一次NPC死亡时间 */
  primary key(id));
create index Index1 on npc_dead_record(p_pk,scene_id,npc_id);
  
/**********禁言(u_embargo)***************/
 create table u_embargo(
    e_pk	               int not null auto_increment               , /**禁言ID*/ 
    p_pk	               int                                       , /**玩家id*/
    p_name	               varchar(20)                               , /**玩家名称*/
    begin_time             datetime                                  , /**禁言开始时间*/
    end_time               datetime                                  , /**禁言结束时间*/ 
    e_time                 varchar(100)                              , /**禁言时间*/ 
    primary key(e_pk));
    create index Index1 on u_embargo (p_pk);
 
/**********灵魂绑定(u_soulbang)***************/
 create table u_soulbang(
    s_pk	               int not null auto_increment               , /**灵魂绑定ID*/ 
    p_pk	               int                                       , /**玩家id*/
    pw_pk	               int                                       , /**装备包裹ID*/
    pw_name                varchar(20)                               , /**绑定装备名称*/ 
    s_type                 int                              default 1, /**绑定装备状态*/ 
    primary key(s_pk));
    create index Index1 on u_soulbang (p_pk,pw_pk);
    
/**********装备掉落消息提示(u_equip_msg)***************/
 create table u_equip_msg(
    e_pk	               int not null auto_increment               , /**装备掉落消息提示ID*/ 
    p_pk	               int                                       , /**玩家id*/
    msg_type               int                                       , /**消息类型*/
    e_msg                  varchar(500)                              , /**消息内容*/ 
    primary key(e_pk));
     create index Index1 on u_equip_msg (p_pk);

/**********弹出式消息表(u_popup_msg)***************/
create table u_popup_msg(
    id                     int not null auto_increment               , /**ID*/ 
    p_pk                   int                                       , /**角色ID*/
    msg_type               int                                       , /**消息类型*/
    msg_operate1           varchar(500)                              , /**功能字段*/
	msg_operate2           varchar(100)                              , /**功能字段*/
	msg_priority           int                                       , /**优先级*/
	create_time            datetime                                  , /**创建时间*/
primary key(id));
create index Index1 on u_popup_msg (p_pk,msg_priority,create_time);

/************用户充值记录***************************/
create table u_account_record(
	id                     int not null auto_increment               , /**ID*/ 
	u_pk                   int                                       , /**用户账号*/
	p_pk                   int                                       , /**充值时登陆的角色*/
	code                   varchar(50)                               , /**卡号*/
	pwd                    varchar(50)                               , /**密码*/
	money                  int                                       , /**充值的钱数*/
	channel                varchar(50)                               , /**充值渠道*/
	account_state          varchar(50)                               , /**充值状态*/
	account_time           datetime                                  , /**充值时间*/
	payment_time           datetime                                  , /**到账时间,既用户元宝到账时间*/
primary key(id));

/**********封号(u_envelop)***************/
 create table u_envelop(
    e_pk	               int not null auto_increment               , /**封号ID*/ 
    p_pk	               int                                       , /**玩家id*/
    p_name	               varchar(20)                               , /**玩家名称*/
    begin_time             datetime                                  , /**封号开始时间*/
    end_time               datetime                                  , /**封号结束时间*/ 
    e_time                 varchar(100)                              , /**封号时间*/ 
    e_type               int                                         , /**封号类型*/ 
    e_state					int										 , /**封号状态*/
    e_num					int										 , /**封号次数*/
    primary key(e_pk));
    create index Index1 on u_envelop (p_pk);
    
/**********玩家交易记录表(game_sellinfo_record)***************/
 create table game_sellinfo_record(
    s_pk	               int not null auto_increment               , /**记录ID*/ 
    p_pk_give	               int                                       , /**给予物品玩家ID*/
    p_pk_have	               int                              , /**得到物品玩家ID*/
    prop_type             int                                  , /**物品类型*/
    prop_id               int                                  , /**物品ID*/ 
    s_num                 int                              , /**物品数量*/ 
    money               varchar(15)                                               , /**金钱*/ 
    s_date               varchar(30)                       ,/**记录时间*/
    primary key(s_pk));
 
/**********免PK道具(u_avoidpkprop)***************/
 create table u_avoidpkprop(
    a_pk	               int not null auto_increment               , /**免PK道具ID*/ 
    p_pk	               int                                       , /**玩家id*/
    begin_time             datetime                                  , /**道具开始时间*/
    end_time               datetime                                  , /**道具结束时间*/  
    primary key(a_pk));
    create index Index1 on u_avoidpkprop (p_pk);

    
/***************角色点击速度过快时记录当时的ip********/
create table exception_user_log(
    id                      int not null auto_increment               , /**ID*/ 
    u_pk                    int                                       , /**账户id*/
    p_pk                    int                                       , /**角色id*/
    exception_ip            varchar(20)                               , /**异常ip*/
    time_space              int                                       , /**时间间隔*/
    log_time                datetime                                  , /**记录时间*/
primary key(id));
create index Index1 on exception_user_log (p_pk);

/***************渠道用户********/
create table channel(
    id                      int not null auto_increment               , /**ID*/ 
    user_name            varchar(20)                                       , /**用户id*/
    user_paw            varchar(20)                               , /**用户密码*/
    channel             varchar(20)                                        , /**用户渠道*/
    log_time                datetime                                  , /**记录时间*/
primary key(id));

/***************渠道ID管理*******/
create table channel_id_info(
    id                      int not null auto_increment               , /**ID*/ 
    channel_id                varchar(20)                                       , /**渠道id*/
    channel_id_sec            varchar(20)                               , /**子渠道ip*/
primary key(id));

/***************渠道数据*******/
create table channel_id_num(
    id                      int not null auto_increment               , /**ID*/ 
    channel_id_sec            varchar(20)                               , /**子渠道ip*/
    sta_num            int                               , /**子渠道*/
    sta_time         datetime                        ,/**记录时间**/  
primary key(id));


	/**********元宝拍卖场表(u_auction_yb)***************/ 
create table u_auction_yb (
	
	uyb_id 				int not null auto_increment 				, /** 元宝id **/
	p_pk					int													,		/** 拍卖元宝者的p_pk */	
	uyb_state       int														,		/** 元宝跑卖的状态,1为正在卖出状态，2为已经卖出状态，3为未卖出下架状态   */
	yb_num			int													,		/** 拍卖元宝数量 */
	yb_price  			int													,		/** 希望拍卖的价格 */
	auction_time	datetime											,		/** 拍卖时间 */
primary key(uyb_id));
 create index Index1 on u_auction_yb (uyb_state,auction_time);

	/**********元宝拍卖场表信息记录(u_auctionyb_auctionInfo)***************/ 
create table u_auctionyb_auctioninfo (
	yb_info_id 				int not null auto_increment 				, /** 元宝id **/
	p_pk					int													,		/** 拍卖元宝者的p_pk */	
	buy_ppk          int														,		/**  购买者的 pPk   */
	jilu_string  		varchar(200)									,		/**  购买记录  */
	auction_time	datetime											,		/** 购买发生时间 */
primary key(yb_info_id));


/*********************商城记录************************/
create table u_mall_log(
    id                  int not null auto_increment 				, /**id **/
    u_pk                int                                         , /**账号id*/
    role_name           varchar(20)                                , /**购买时的角色名字*/
    mall_log            varchar(100)                                , /**日志内容*/
    create_time         datetime                                    , 
primary key(id));
 create index Index1 on u_mall_log (u_pk);

 
/*********************游戏公告（game_notify）************************/
create table game_notify(
    id                  int not null auto_increment 				, /**id **/
    title               varchar(50)                                         , /**公告标题*/
    content           varchar(2000)                                       , /**公告内容*/
    ordernum       int                                                      , /**排序*/
    isonline           int                                                  default 0   , /**是否上线*/
    create_time       datetime                                    , /**建立公告时间*/
    type       int                                                       default 0 , /**類型*/
primary key(id));

 
/*********************系统奖励发放表（sys_prize）************************/
create table sys_prize(
    id                  int not null auto_increment 				, /**id **/
    u_passport               varchar(50)                                         , /**玩家账号*/
    prop_id          int                                                      , /**道具ID*/
    prop_num       int                                                      , /**道具数量*/
    content          varchar(100)                                     ,/**说明*/
primary key(id));

/*********************系统奖励发放表（game_prize）************************/
create table game_prize(
    id                  int not null auto_increment 				, /**id **/
    prize_type			varchar(100)									,/**奖励类型**/
    prize_display       varchar(200)									,/**奖励描述*/
    u_passprot			varchar(50)										,/**玩家账号*/
    u_pk                   int                                    default 0                   , /**玩家账号*/
    u_name				varchar(50)										,/**玩家姓名*/
    p_pk				int										default 0 		,/**ppk*/
    state                  int                                  default 0                   ,/**状态*/
    prop				varchar(100)									,/**奖励内容*/
    create_time         datetime                                          ,/**修改状态的时间*/
primary key(id));

/*********************系统奖励发放表（game_prize_info）************************/
create table game_prize_info(
    id                  int not null auto_increment 				, /**id **/
    u_pk                   int                                                      , /**upk*/
    p_pk                   int                                                      , /**ppk*/
    content          varchar(100)                                     ,/**说明*/
    creat_time         datetime                                          ,/**修改状态的时间*/
primary key(id));
 
/*********************统计玩家信息记录表（game_player_statistics_info）*****************************************/
create table game_player_statistics_info(
	id					int not null auto_increment					,/**主键*/
	u_pk				int											,/**u_pk*/
	p_pk				int											,/**p_pk*/
	p_grade				int											,/**等级*/
	p_onlinetime		int											,/**在线时间分钟*/
	p_date				varchar(50)									,/**日期*/
	p_time				varchar(10)									,/**时间*/
	p_login_time_old    datetime									,/**上次登录时间*/
	p_login_time	    datetime									,/**本次登录时间*/
primary key (id)) ENGINE=innodb;
create index Index1 on game_player_statistics_info (u_pk,p_pk);	
	
/*********************开黄金宝箱记录************************/
create table u_box_info(
    id                  int not null auto_increment 				, /**id **/
    p_pk                int                                         , /**角色id*/
    p_grade			int										,	/** 角色等级  **/
    prop_id           int                               , /**开启的道具ID*/
    prop_name            varchar(20)                                , /**开启的道具名称*/
    prop_quality		int													,/**  道具品质 **/
    create_time         datetime                                    ,  /** 开启时间 */
primary key(id));
	
	
/**************思凯充值记录**************************/
create table u_sky_pay_record(
    id                  int not null auto_increment 				, /**id **/
    billid              varchar(50)                                 , /**billid **/ 
    skyid               varchar(50)                                , /**思凯用户id，对应u_login_info表的user_id*/
    kbamt               varchar(15)                                , /**请求扣费金额*/
    pay_time            datetime                                    , /**请求发起的时间*/
    skybillid1          varchar(50)                                , /**是超级大玩家的对账流水号1*/
    skybillid2          varchar(50)                                , /**是超级大玩家的对账流水号2*/
    balance             varchar(15)                                , /**余额*/
    respones_result     varchar(200)                                , /**请求的结果代码*/
    p_pk                int                                        , 
    repair_result		int										default 0 	,/***补充KB的结果**/
primary key(id));
	
	
	 /*****************   帮派攻城战控制表  (前台)   ***************/
  	 create table tong_siege_control (
  	 		control_id			int unsigned not null auto_increment  ,		/** 帮派攻城战控制表ID  */
  	 		siege_id				int 														,		/*** 攻城战ID,代表着某个城市的攻城  **/
  	 		siege_number  int														,			/**   siege_id所代表的帮派马上要开始的是第几次攻城战   ***/
  	 		siege_start_time		datetime										,			/**   下次攻城战开始时间  */
  	 		siege_sign_end		datetime										,  			/**   下次攻城战报名截止时间 */
  	 		last_win_tongid		int												,			/**    此攻城战场上次胜利帮派ID   **/
  	 		now_phase				int												,			/**     当前阶段,0为攻城结束等待下次开始,1为攻城第一阶段,2为城门开始被人打打,3为城门被打破,,4为英雄雕像被打破攻城进入第二阶段   ***/
  	  primary key(control_id));	
  	  
  	   /*****************   帮派攻城战参战帮派列表  (前台)   ***************/
  	 create table tong_siege_list (
  	 		list_id				int unsigned not null auto_increment  ,		/**  帮派攻城战帮派参战列表 ID  */
  	 		siege_id			int 														,		/*** 攻城战ID,代表着某个城市的攻城  **/
  	 		siege_number  int														,			/**   siege_id所代表的帮派马上要开始的是第几次攻城战   ***/
  	 		tong_pk			int														,			/****    参战的帮派id      ****/  	 		
  	 		join_time			datetime												,			/**     帮派战 参与时间     ***/
  	  primary key(list_id));	
  	  
  	  
  	  /***     帮派攻城战杀人记录表   ******/
  	   create table tong_siege_pklog (
  	 		pklog_id			int unsigned not null auto_increment  ,		/** 帮派攻城战杀人记录表ID  */
  	 		siege_id				int 													,		/*** 攻城战ID,代表着某个城市的攻城  **/
  	 		siege_number  int														,			/**   siege_id所代表的攻城战第几次战斗   ***/
  	 		tong_id			int														,			/**   帮派ID  */
  	 		p_pk					int														,  			/**   角色id */
  	 		pk_number		int												,			/**   在此次攻城战伤所杀人数  **/
  	 		pk_add_glory int															,			/**    此次攻城战所增加的帮派荣誉值   ***/
  	  primary key(pklog_id));	
  	  
  	  
  	  
  	  /***  攻城战个人信息记录    ****/
  	  create table tong_siege_info (
  	  		info_id				int unsigned not null auto_increment  ,		/** 攻城战个人信息记录ID  */
  	  		p_pk 				int														,		/***  个人pPk   ****/	
  	  		attack_type		int														,		/*** 参加类型,1为个人参战,2为帮派参战  ****/
  	  		join_type			int														,		/**   战斗类型,1为攻城,2为守城   ****/
  	  		tong_id			int														,			/**    帮派ID  */
  	  		
  	  		dead_num		int														,		/**    在第二阶段死的次数   **/
  	 		dead_limit		int														,		/**    死亡极限次数 ***/
  	 		siege_id			int														,		/**    战场id  **/
  	 		siege_number	int														,		/***  战场次序数  **/	
  	 primary key(info_id));
  	 
  	 
  	 /***帮派发送 税收 奖金表  ****/
  	  create table tong_money_info (
  	  		info_id				int unsigned not null auto_increment  ,		/** 攻城战个人信息记录ID  */
  	  		p_pk 				int														,		/***  个人pPk   ****/	
  	  		tong_id			int														,		/***  帮派ID **/	
  	  		back_type		int														,		/**     是否那会标志,1为未拿,2为已经拿回    **/
  	  		money_num		int														,		/***   金钱数额  ****/
  	  		sendtime			datetime												,		/**    奖金发放时间   ****/
  	 primary key(info_id));
  	 
  	 /****黄金宝箱的开启次数******/
  	 create table u_goldbox_num_info (
  	 		id					int unsigned not null auto_increment				,/**主键*/
  	 		u_pk				int													,/**账号ID*/
  	 		p_pk				int													,/**玩家ID*/
  	 		goldbox_num			int										   default 0,/**宝箱个数*/
  	 		cteate_time			datetime											,/**创建时间*/
  	  primary key(id));	
  	 
 /***********************************玩家离线记录***********************************/
  create table role_be_off(
    off_id              int not null auto_increment                        ,/**主键id*/
	p_pk                int                                                ,/**玩家ID*/
	be_off_time         varchar(50)                                        ,/**上次离线时间*/
	already_time        varchar(50)                              default  0,/**离线时间分钟*/
	be_off_exp          varchar(50)                              default  0,/**离线经验*/
	prop_cumulate_time  varchar(50)                              default  0,/**道具累积时间 就是可共领取经验的时间*/
	primary key(off_id));	

 /***********************************玩家离线buff***********************************/
  create table role_be_off_buff(
    b_id                int not null auto_increment                        ,/**主键id*/
	p_pk                int                                                ,/**玩家ID*/
	be_off_time         varchar(50)                              default  0,/**上次离线时间*/
	be_off_exp          varchar(50)                              default  0,/**离线经验*/
	primary key(b_id));	


/***********************************称号表(t_role_honour)***********************************/
  create table t_role_honour(
    ro_id                int not null auto_increment                        ,/**主键id*/
    p_pk                 int                                                ,/**角色主键*/
    ho_id                int                                                ,/**称号主键*/
    ho_type              int                                                ,/**称号类型*/
    is_reveal            int                                       default 0,/**是否显示*/
    detail               varchar(255)                              default null,/**说明*/
    create_time          datetime                              ,/**时间*/
	primary key(ro_id));	
create index Index1 on t_role_honour (p_pk);

/**********************************角色升级日志表(u_upgrade_log)****************************/
create table u_upgrade_log(
    p_pk                 int                                                ,/**角色主键*/
    role_name            varchar(20)                                        ,/**角色名字*/
    content              varchar(100)                                       ,/**日志内容*/
    createtime            datetime                                           /**创建时间*/
);
create index Index1 on u_upgrade_log (p_pk);
create index Index2 on u_upgrade_log (role_name);

/**********************************信息记录表(u_box_record)****************************/
create table u_box_record(
	br_id				int unsigned not null auto_increment                        ,/**主键id*/
    p_pk                 int                                                ,/**角色主键*/
    info_type            varchar(50)                                        ,/**信息类型，1为宝箱记录*/
    content              varchar(100)                                       ,/**日志内容*/
    createtime            datetime                                         ,  /**创建时间*/
 primary key(br_id)) ENGINE=innodb;
 
 
/***************************玩家动作记录表（金钱）**************************************/
create table u_log_money(
	id					bigint unsigned not null auto_increment					,/****主键ID*****/
	p_pk				int														,/**p_pk**/
	p_name				varchar(20)												,/**玩家姓名**/
	old_num				bigint													,/**老的数值**/
	new_num				bigint													,/***新的数值**/
	content				varchar(200)											,/***描述**/
	createtime			datetime												,/**时间***/
primary key(id)) ENGINE=innodb;

/***************************玩家动作记录表（元宝）**************************************/
create table u_log_yb(
	id					bigint unsigned not null auto_increment					,/****主键ID*****/
	p_pk				int														,/**p_pk**/
	p_name				varchar(20)												,/**玩家姓名**/
	old_num				bigint													,/**老的数值**/
	new_num				bigint													,/***新的数值**/
	content				varchar(200)											,/***描述**/
	createtime			datetime												,/**时间***/
primary key(id)) ENGINE=innodb;


/***************************玩家动作记录表（经验）**************************************/
create table u_log_exp(
	id					bigint unsigned not null auto_increment					,/****主键ID*****/
	p_pk				int														,/**p_pk**/
	p_name				varchar(20)												,/**玩家姓名**/
	old_num				bigint													,/**老的数值**/
	new_num				bigint													,/***新的数值**/
	content				varchar(200)											,/***描述**/
	createtime			datetime												,/**时间***/
primary key(id)) ENGINE=innodb;

/***************************玩家动作记录表（基本属性）**************************************/
create table u_log_player(
	id					bigint unsigned not null auto_increment					,/****主键ID*****/
	p_pk				int														,/**p_pk**/
	p_name				varchar(20)												,/**玩家姓名**/
	old_num				bigint													,/**老的数值**/
	new_num				bigint													,/***新的数值**/
	content				varchar(200)											,/***描述**/
	createtime			datetime												,/**时间***/
primary key(id)) ENGINE=innodb;

/*************************************建表时需要初始化的数据******************************************************/


  insert into u_tong_rights values (null,1,'增加成员','1,2,3,4','n1');
  insert into u_tong_rights values (null,1,'踢出成员','1,2,3','n2');
  insert into u_tong_rights values (null,1,'调整职位','1,2','n3');
  insert into u_tong_rights values (null,1,'修改称号','1,2,3','n4');
  insert into u_tong_rights values (null,null,'发布招募信息','1,2','n5');
  insert into u_tong_rights values (null,2,'发布公告','1,2','n6');
  insert into u_tong_rights values (null,3,'转让帮主','1','n7');
  insert into u_tong_rights values (null,3,'解散帮会','1','n8');
  insert into u_tong_rights values (null,null,'加入阵营','1','n9');
  insert into u_tong_rights values (null,null,'使用帮会基金','1,2','n10');
  insert into u_tong_rights values (null,null,'分发帮派物质','1,2','n11');
  insert into u_tong_rights values (null,null,'研究帮会技能','1,2','n12');
  insert into u_tong_rights values (null,null,'攻城','1,2','n13');
  insert into u_tong_rights values (null,null,'设置敌对帮派','1,2','n14');
  insert into u_tong_rights values (null,null,'设置同盟帮派','1,2','n15');
  
  
  
  /*********************************师徒系统表********************************************/
  CREATE TABLE `shitu` (
`id` BIGINT( 20 ) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
`te_id` BIGINT( 20 ) NULL DEFAULT '0',
`stu_id` BIGINT( 20 ) NULL DEFAULT '0',
`te_name` varchar(255) default null,
`stu_name` varchar(255) default null,
`te_level` BIGINT(20) null default '0',
`stu_level` BIGINT(20) null default '0',
`tim` varchar(255) default null,
`chuangong` varchar(255) default null
) ENGINE = InnoDB;
create index Index1 on shitu(stu_id,te_id);
 
 
   /********************************* 攻城信息记录表 ********************************************/
  CREATE TABLE tong_siegebattle_info(
		id 				int unsigned not null auto_increment ,			/** 表Id  */
		p_pk			int(8) NULL DEFAULT 0,				/** 个人ppk  */
		siege_id 	    int( 8) NULL DEFAULT 0,				/** 战场vID */				
		siege_number    int(8) NULL DEFAULT 0,			/** 战场序号  */	
		infoone 		varchar(255) default null,				/** 信息一  */
		infotwo 		varchar(255) default null	,			/** 信息二  */		
  primary key(id)) ENGINE=innodb;
 
   	  /***  伤害记录表  ****/
  	  create table injure_recond_info (
  	  		injure_id			int unsigned not null auto_increment 					 ,		/** 伤害记录表ID  */
  	  		tong_id				int																,		/***  帮派ID   ****/	
  	  		injure_number		int(50)													,		/***  伤害数值 **/	
  	  		npc_ID				int														,		/**   NPC_id    **/
  	  		npc_Type  			smallint												,		/***  NPC_type,6代表英雄雕像  ****/
  	 primary key(injure_id));
  	 
/******************角色对声望的关系表*******************/
create table p_credit_relation(
       pcid   int primary key auto_increment,/*****角色对声望关系主键*****/
       ppk    int                                ,/*****角色主键*****/
       cid    int                                ,/*****声望主键****/
       pcount int                    default 0    /*****声望数量****/
       ) ENGINE=MyISAM;

/***********************************会员表(t_role_vip)***********************************/
  create table t_role_vip(
    rv_id                int not null auto_increment                        ,/**主键id*/
    p_pk                 int                                                ,/**角色主键*/
    ho_id                int                                                ,/**称号主键*/
    v_id                 int                                                ,/**VIP表主键*/
    v_name               varchar(100)                                       ,/**VIP名称*/
    rv_begin_time        datetime                                           ,/**会员开始时间*/
    rv_end_time          datetime                                           ,/**会员结束时间*/
    is_die_drop_exp      int                                       default 0,/**死亡是不是损失经验 0 损失 1不损失*/
	primary key(rv_id)); 
create index Index1 on t_role_vip (p_pk);	


  	   	  /***  装备展示表  ****/
  	  create table zb_relela_info (
  	  		relela_id			int unsigned not null auto_increment 					 ,		/** 伤害记录表ID  */  	  		
  	  		pwpk		int(11)													,		/***  伤害数值 **/	
  	  		relelavar			varchar(400)								,		/**   装备展示字符    **/
  	  		relelatime			datetime												,		/**    装备展示时间   ****/
  	 primary key(relela_id));
  	 
 /**********帮会成员退出帮派(u_tong_member_out)***************/
create table u_tong_member_out(
    o_pk	               int not null auto_increment               , /**id*/ 
  	t_pk 	               int                                       , /**帮会id*/ 
  	p_pk                   int                                       , /**人员ID*/
    out_time               datetime                                  , /**退出帮会时间*/ 
    primary key(o_pk));
    
 /**********邮件领取奖励(u_mail_bonus)***************/
create table u_mail_bonus(
    id	               int not null auto_increment               , /**id*/ 
  	p_pk                   int                                       , /**人员ID*/
  	mail_id 	               int                                       , /**邮件id*/ 
    bonus					varchar(100)							,/**奖励**/
    is_have					int										,/***是否领取*/
    primary key(id));
  	 
  	 
  	 
  	  /*********************************自动打怪********************************************/
  
CREATE TABLE IF NOT EXISTS `auto` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `p_pk` int(32) NOT NULL,
  `ogre` int(32) NOT NULL,
  `time` int(2) NOT NULL,
  `begin_time` varchar(50) NOT NULL,
  `end_time` varchar(50) DEFAULT '',
  `operate` varchar(255) DEFAULT '',
  `level` int(11) DEFAULT NULL COMMENT '品质',
  `guaji_type` int(2) NOT NULL COMMENT '挂机类型',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=gbk AUTO_INCREMENT=7 ;
create index Index1 on auto (p_pk);	

/*******************************************排行榜********************************************/
CREATE TABLE `rank` (                                     
          `id` int(16) NOT NULL AUTO_INCREMENT,                   
          `p_pk` int(16) NOT NULL,                                
          `p_name` varchar(50) default NULL,
          `p_menpai` varchar(50) default null,                                
          `p_level` int(2) NOT NULL default 1,  
          `p_level_time` datetime COMMENT '等级修改时间',                               
          `p_exp` int(32) NOT NULL default 0  COMMENT '角色经验',                      
          `p_exp_time` datetime COMMENT '修改角色时间',   
          `exp_tong` int(1) NOT NULL default 0 COMMENT '是否统计', 
          `kill` int(5) NOT NULL default 0 COMMENT '杀人数',         
          `kill_time` datetime COMMENT '杀人时间',                        
          `dear` int(7) NOT NULL default 0 COMMENT '爱情甜蜜', 
          `who`  varchar(50) default NULL comment '和谁的爱情甜蜜',       
          `dear_time` datetime COMMENT '爱情甜蜜修改时间',   
          `evil` int(7) NOT NULL default 0 COMMENT '罪恶值',         
          `evil_time` datetime COMMENT '罪恶修改时间',   
          `glory` int(7) NOT NULL default 0 COMMENT '荣誉榜',         
          `glory_time` datetime COMMENT '荣誉时间',   
          `money` bigint(32) NOT NULL default 0 COMMENT '金钱',         
          `money_time` datetime COMMENT '金钱时间',   
          `yuanbao` int(8) NOT NULL default 0 COMMENT '元宝',         
          `yuanbao_time` datetime COMMENT '元宝修改时间',   
          `credit` int(8) NOT NULL default 0 COMMENT '角色声望',         
          `credit_time` datetime COMMENT '角色声望修改时间',  
          `open` int(8) NOT NULL default 0 COMMENT '开宝箱数量',         
          `open_time` datetime COMMENT '开宝箱数量修改时间', 
          `vip` int(8) NOT NULL default 0 COMMENT 'VIP等级', 
          `vip_eff` int(8) not null default 0 comment 'VIP作用时间',        
          `vip_time` datetime COMMENT 'VIP等级修改时间',   
          `bangkill` int(8) NOT NULL default 0 COMMENT '帮派杀人数（野战/帮战/攻城）数量',         
          `bangkill_time` datetime COMMENT '帮派杀人数（野战/帮战/攻城）修改时间',
          `killnpc` int(8) NOT NULL default 0 COMMENT '杀怪点数（点数=怪物等级，副本怪物点数×2）',         
          `killnpc_time` datetime COMMENT '杀怪点数（点数=怪物等级，副本怪物点数×2）修改时间',
          `wei_task` int(8) NOT NULL default 0 COMMENT '任务完成时获得的威望', 
          `wei_other` int(8) NOT NULL default 0 COMMENT '其他活动获得威望',         
          `wei_time` datetime COMMENT '威望修改时间',
          `dead` int(8) NOT NULL default 0 COMMENT '被人/怪杀死次数',         
          `dead_time` datetime COMMENT '被人/怪杀死修改时间',
          `killboss` int(8) NOT NULL default 0 COMMENT '野外击杀Boss的点数（点数=Boss等级；副本Boss获得点数×',         
          `killboss_time` datetime COMMENT '野外击杀Boss修改时间',
          `zhong` int(8) NOT NULL default 0 COMMENT '在线时间',         
          `zhong_time` datetime COMMENT '在线时间修改时间',
          `sale` int(8) NOT NULL default 0 COMMENT '拍卖场里拍卖物品被卖掉的次数',         
          `sale_time` datetime COMMENT '拍卖量修改时间',
          `yi` int(8) NOT NULL default 0 COMMENT '义气度最高的两个人', 
          `yi_who` varchar(50) default NULL comment '和谁的义气',        
          `yi_time` datetime COMMENT '义气修改时间',
          `ans` int(8) NOT NULL default 0 COMMENT '答题正确条数',         
          `ans_time` datetime COMMENT '答题正确条数修改时间',
          `meng` int(8) NOT NULL default 0 COMMENT '完成副本后的点数记录',         
          `meng_time` datetime COMMENT '完成副本后的点数修改时间',   
          `boyi` bigint default 0 COMMENT '博弈',
          `boyi_time` datetime COMMENT '博弈修改时间',  
          `lost` int(2) not null default 0 comment '迷宫层数',
          `lost_time` datetime comment '迷宫闯入时间',  
          `killlang` int(2) not null default 0 comment '杀死千面郎君次数',
          `killlang_time` datetime comment '杀死千面郎君时间',
          `langjun` int(2) not null default 0 comment '逃逸千面郎君次数',
          `langjun_time` datetime comment '逃逸千面郎君时间',
          PRIMARY KEY (`id`),                                     
          KEY `id` (`id`),                                        
          KEY `p_pk` (`p_pk`),
          KEY `p_exp` (`p_exp`,`p_exp_time`),
          KEY `kill` (`kill`,`kill_time`),
          KEY `dear` (`dear`,`dear_time`),
          KEY `evil` (`evil`,`evil_time`),
          KEY `glory` (`glory`,`glory_time`),
          KEY `money` (`money`,`money_time`),
          KEY `yuanbao` (`yuanbao`,`yuanbao_time`),
          KEY `credit` (`credit`,`credit_time`),
          KEY `open` (`open`,`open_time`),
          KEY `vip` (`vip`,`vip_eff`,`vip_time`),
          KEY `bangkill` (`bangkill`,`bangkill_time`),
          KEY `killnpc` (`killnpc`,`killnpc_time`),
          KEY `wei_task` (`wei_task`,`wei_other`,`wei_time`),
          KEY `killboss` (`killboss`,`killboss_time`),
          KEY `zhong` (`zhong`,`zhong_time`),
          KEY `sale` (`sale`,`sale_time`),
          KEY `yi` (`yi`,`yi_time`),
          KEY `ans` (`ans`,`ans_time`),
          KEY `meng` (`meng`,`meng_time`),
          KEY `biyi`(`boyi`,`boyi_time`),
          KEY `lost`(`lost`,`lost_time`),
          KEY `killlang`(`killlang`,`killlang_time`),
          KEY `langjun`(`langjun`,`langjun_time`)
        ) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=gbk;
        
        
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
  	  		
  	  		wj_menpai		 Enum('0','1','2','3')										,		/**   系统奖励条件之, 玩家门派   **/
  	  		wj_title			    varchar(100)									    	,		/**   系统奖励条件之, 玩家称号, 如果有多个称号,以","分割   **/
  	  		wj_credit			varchar(10)													,		/**   系统奖励条件之, 玩家声望,  以"-"连接, 如有多个,以";"连接    **/
  	  		wj_next			varchar(10)										 		,		/**   系统奖励条件之, 空余等下一个   **/
  	  		
  	  		
  	  		is_only_one		Enum('0','1','2','3','4','5','6','7','8','9')		,		/** 是否仅领取一次, 为1表示仅领取一次,为0表示可以不止领取一次 */
  	  		onces				int(3) 														,		/**  一天之内最多能领取几次  */
  	  		
  	  		
  	  		give_goods			varchar(100)												,		/**    奖励装备或物品, 以","分割, 和兑换菜单的制作方法相同    ****/
  	  		isuseable			int(3)											default 1		,		/**   是否有效,为零表示无效,不会被显示出来. 为1的话则可以显示  **/
  	  		
  	  		horta_display		varchar(100)												,		/**   奖励描述  **/
  	 primary key(horta_id));
  	 
  	 
  	 
  	 
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
  	 primary key(id));
  	 
 
/****************系统开奖表s_lottery_yuanbao***********************/
create table s_lottery_yuanbao (
  id			int unsigned not null auto_increment					,/***ID**/
  lottery_date	varchar(20)												,/**彩票期号**/
  lottery_all_yb	varchar(20)											,/**彩排销售额***/
  lottery_catch_yb	varchar(20)											,/***领取金额****/
  lottery_content	varchar(20)											,/**彩票内容**/
  lottery_catch_player	varchar(20)										,/**领取人数**/
  lottery_create_time	varchar(20)										,/***建立时间***/
  primary key(id)) ENGINE=innodb;
  create index Index1 on s_lottery_yuanbao (lottery_date);
  
/*****************玩家购买彩票表u_lottery_yuanbao*************************/
create table u_lottery_yuanbao (
	id				bigint unsigned not null auto_increment				,/***ID***/
	p_pk			int													,/*****/
	lottery_date	varchar(20)											,/**彩票期号**/
	lottery_content	varchar(20)											,/***彩票内容**/
	lottery_zhu		int													,/****注数**/
	lottery_time	datetime											,/***创建时间**/
	lottery_bonus_lv	int							default 0			,/**中将等级**/
	lottery_bonus	bigint							default 0			,/***金额**/
	is_have			int								default 0			,/**是否领取**/
	have_time		datetime											,/**领取时间***/
	primary key(id)) ENGINE=innodb;
	create index Index1 on u_lottery_yuanbao (lottery_date,p_pk);
	
	
	
	/********************************擂台角色表****************************************/
CREATE TABLE `leitai_role` (                          
               `id` int(10) unsigned NOT NULL AUTO_INCREMENT,      
               `lei_id` int(12) NOT NULL DEFAULT '0',              
               `ppk` int(12) NOT NULL DEFAULT '0',                 
               `killman` int(12) NOT NULL DEFAULT '0',             
               `dead` int(12) NOT NULL DEFAULT '0',                
               `pname` varchar(50) NOT NULL,        
               `killtime` datetime COMMENT '杀人事件',                
               PRIMARY KEY (`id`)                                  
             ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk  ;
/*************************************参加活动擂台的角色**************************************/
CREATE TABLE `active_leitai_role` (                          
               `id` int(10) unsigned NOT NULL AUTO_INCREMENT,     
               `ppk` int(16) not null default '0', 
               `pname` varchar(50) NOT NULL, 
               `scene_id` int(11) DEFAULT '0' comment '进入场景',
               `round1` int(1) default '0' comment '第一轮PK状态',  
               `round2` int(1) default '0' comment '第二轮PK状态',  
               `round3` int(1) default '0' comment '第三轮PK状态', 
               `into_time` datetime COMMENT '进入擂台时间',
               PRIMARY KEY (`id`)                                  
             ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk  ;
/*************************************擂台**************************************/
CREATE TABLE `leitaibaoming` (                          
               `id` int(10) unsigned NOT NULL AUTO_INCREMENT,     
               `ppk` int(16) not null default 0, 
               `zu` int(1) not null default '0' comment '报名的组：青龙（0），朱雀（1），白虎（2），玄武（3）',
               `win` int(2) default 0,
               `dead` int(2) default 0,
               `isComein` int(2) default 0,
               PRIMARY KEY (`id`)                                  
             ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk  ;
             
              /*******************************************对战擂台*********************************************/
             CREATE TABLE `battlepeo` (                          
               `id` int(10) unsigned NOT NULL AUTO_INCREMENT,     
               `ppk` int(16) not null default 0, 
               `name` varchar(50) default null,
               `sheng` int(16) not null default 0,
               `intoscene` int(5) not null default 0,
               `pk` int(1) not null default 0,
               `intotime`  datetime COMMENT '进入擂台时间',
               PRIMARY KEY (`id`)                                  
             ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk  ;
             
/*****************玩家秘境p_unchartedroom*************************/
create table p_unchartedroom (
	id				bigint unsigned not null auto_increment				,/***ID***/
	p_pk			int													,/*****/
	into_state		int								default 0			,/**中将等级**/
	into_num		int								default 0			,/**中将等级**/
	into_time		datetime											,/**领取时间***/
	out_time		datetime											,/**领取时间***/
	primary key(id)) ENGINE=innodb;
	create index Index1 on p_unchartedroom (p_pk);

/*****************祝福树p_wishingtree*************************/
create table p_wishingtree (
	id				bigint unsigned not null auto_increment				,/***ID***/
	p_pk			int													,/*****/
	p_name			varchar(10)											,/**中将等级**/
	wishing			varchar(100)										,/**中将等级**/
	top				int								default 0			,/**中将等级**/
	create_time		datetime											,/**领取时间***/
	top_time		datetime											,/**领取时间***/
	primary key(id)) ENGINE=innodb;
	create index Index1 on p_wishingtree (p_pk);
	
/*****************门派比武*************************/
create table p_menpaicontest (
	id				bigint unsigned not null auto_increment				,/***ID***/
	p_pk			int													,/*****/
	p_name			varchar(10)											,/**玩家姓名**/
	p_type			int													,/***玩家称谓**/
	into_state		int								default 0			,/**在秘境中的状态***/
	into_time		datetime											,/**进入时间***/
	out_time		datetime											,/**出去时间***/
	win_state		int								default 0			,/**胜利状态**/
	win_time		datetime											,/**获奖励时间**/
	kill_num		int								default	0			,/**杀人数量**/
	old_kill_num	int								default	0			,/**杀人数量**/
	kill_time		datetime											,/**最后杀人时间*/
	win_num			int								default	0			,/**获取大弟子的次数**/
	win_num_time	datetime											,/***获取大弟子最后的时间***/
	kill_p_pk		int								default	0			,/**获取大弟子的次数**/
	kill_p_pk_time	datetime											,/***获取大弟子最后的时间***/
	primary key(id)) ENGINE=innodb;
	create index Index1 on p_menpaicontest (p_pk);
	
/****************PK活动（华山论剑)报名表*******************/
create table  pk_active_regist (
   ID int(11) not null auto_increment,/*****ID****/
   roleID int(11)  not null,/*****角色ID****/
   roleLevel int(11) not null,/*****角色等级****/
   roleName varchar(11) not null,/*****角色名称****/
   registTime datetime not null,/*****报名时间****/
   isWin int(11) not null default '0',/*****是否胜利（0为胜利，1为失败）****/
   isEnter int(11) not null default '1',/*****玩家进入场地状态1为进入状态0为非进入状态****/
   isGetPrice int(11) not null default '0',/*****是否可以领取奖品（1为有奖品领取）****/
   PRIMARY KEY  (ID)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk;	

 /*******************PK活动（华山论剑)对阵表***********************/
create table pk_vs (
   ID int(10) unsigned not null auto_increment,/*****ID****/
   roleAID int(11) not null,/*****角色A的ID****/
   roleBID int(11) not null,/*****角色B的ID****/
   roleAName varchar(30) not null,/*****角色A的名字****/
   roleBName varchar(30) not null,/*****角色B的名字****/
   winRoleID int(11) not null default '0',/*****获胜者的ID****/
  PRIMARY KEY  (ID)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=gbk;
 
 
	