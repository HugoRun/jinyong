create database jygame_user;  


/**********邮件表(u_mail_info)***************/

create table u_mail_info ( 
	mail_id 		mediumint unsigned not null auto_increment 		,	/*** 邮件表id **/	
	receive_pk 		int												,	/**  收信人id **/
	send_pk			int 											,	/**	 发信人id **/
	mail_type		int												,	/**  邮件类型，1为普通，2为系统**/
	title 				varchar(200)									, 	/**  标题  **/
	content 			varchar(2000)									,	/**  邮件内容  **/
	unread				int												,	/**	 是否阅读过，1为未读，2未已读 **/
	improtant			int												,	/**  重要性，在玩家看时会以此作优先排序，默认为1，数字越大越靠前  **/		
	create_time			datetime										,	/**	 邮件发送时间，在重要性排序一致的情况下以此排序  ***/
	primary key (mail_id));
	
	/**********宠物拍卖场表(u_auction_pet)***************/ 
create table u_auction_pet(
	auction_id		 mediumint unsigned not null auto_increment ,/** 拍卖场宠物id */
	auction_status	 int 										,/** 宠物拍卖状态，1为正在拍卖，2为拍卖失败,等待取回，3为玩家未取回,等待删除，4为拍卖成功，等待玩家取回金钱. */
	pet_price		 int 										,/** 宠物拍卖价格  */
	pet_auction_time datetime									,/** 宠物拍卖时间 */	
	pet_pk			 int									   , /** 角色宠物ID */
	
	p_pk			 int 					                   , /**角色id*/
	pet_id			 int					                   , /**对应pet表里的id*/
	pet_name		 varchar(200)				               , /**宠物名称*/
	pet_nickname	 varchar(200)				               , /**宠物昵称*/
	pet_grade		 int					                   , /**等级*/
	
	pet_exp			 varchar(200)			                   , /**经验*/
	pet_ben_exp		 varchar(200)			          default 0, /**当前经验经验*/
	pet_xia_exp		 varchar(200)                              , /**下级经验达到下一级需要的经验*/
	pet_gj_xiao      int                     		           , /**最小攻击*/
	pet_gj_da        int					                   , /**最大攻击*/
	
	pet_sale		 int			                           , /**卖出价格*/
	pet_img			 varchar(200)                              , /**宠物图片*/
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
 primary key (auction_id));
 
   /*********宠物拍卖信息表(u_auctionpet_info)******************************/
  create table u_auctionpet_info(
  	auctionpet_info_id			smallint unsigned not null auto_increment , /**宠物拍卖信息id */
  	p_pk						int 									 ,	/** 个人角色id */
  	auction_pet_info			varchar(200)							 ,	/** 宠物拍卖信息提示 */
  	addInfoTime					datetime 								,	/** 增加信息时间 */
  	primary key(auctionpet_info_id));
  	
  	/*********系统设置表(s_setting_info)******************************/
  	create table s_setting_info (
  		setting_id			smallint unsigned not null auto_increment,	/** 系统设置表 */
  		p_pk				int										,	/** 人物角色id */
  		goods_pic			tinyint									,	/**  物品图开关,1为开启，0为关闭 */
  		person_pic			tinyint									,	/**	 角色形象图开关,1为开启，0为关闭 */
  		npc_pic				tinyint									,	/**  npc怪物图开关,1为开启，0为关闭 */
  		pet_pic				tinyint									,	/**  宠物图开关,1为开启，0为关闭 */
  		operate_pic			tinyint									,	/**  npc人物图开关,1为开启，0为关闭 */
  		deal_control		tinyint									,	/**  交易控制开关,1为开启，0为关闭 */
  	primary key(setting_id));
  		
 /**********角色仓库装备表(u_warehouse_equip)***************/
create table u_warehouse_equip ( 
   w_pk		        smallint unsigned not null auto_increment ,	  /**角色装备仓库表*/
   p_pk             int                                       ,	  /**角色id*/ 
   table_type       int                                       ,   /**物品相关表类型*/
   goods_type       int                                       ,   /**物品类型*/
   w_id             int                                       ,   /**物品ID*/
   
   w_name           varchar(200)                              ,   /**物品名称*/
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
   create_time      datetime                                   ,  /** 创建时间*/ 
   primary key (w_pk)); 		
  			

   
   
   /** pk值消除日志 (u_pkvalue_elimination)*/
   create table u_pkvalue_elimination(
   elimi_id 		int unsigned not null auto_increment		,	/**pk值消除表id */
   p_pk				int											,	/**角色id*/
   pk_value			int											,	/**pk值*/
   is_prisonArea	int 										,	/**是否在监狱区域，1为不在，2为在*/
   last_time		datetime									,	/**上次消除罪恶值的时间*/   
    primary key (elimi_id));   

   
  /*************系统消息控制表(u_systeminof_control)*********/
  	create table u_systeminof_control(
  control_id		 int unsigned not null auto_increment		,	    /**  系统控制消息表id */
  condition_type 	 int 										,		/** 控制条件类型,1为玩家等级,2为任务,3为声望,4为称谓,5为发送时间.  */
  player_grade		 int						   default 0	,		/**  玩家等级 */
  task_id			 int 						   default 0	,		/**  人物id */
  popularity		 int 						   default 0	,		/**  声望 */
  title 			 varchar(50) 					   			,		/**   称谓 */
  send_time		     datetime									,		/**   发送时间 */
  send_content		varchar(1000)								,		/**  发送内容	*/
  primary key(control_id));			
  
  
  
  /**********************************************************************************************************************************/
  
  
  
  
  
  
  
  
  
  /***********上线玩家记录表*********************/
  create table p_record_login(
  id				int unsigned not null auto_increment		,	    /**  上线玩家记录表id */	
  u_pk				int											,		/**  玩家id  */
  loginStatus		int											,		/**  登陆状态 */
  loginTime			datetime									,		/**  最后上线时间  */	
  primary key(id));
  
 
 /***********上线玩家角色记录表*********************/
  create table user_record_login(
  id				int unsigned not null auto_increment		,	    /**  上线玩家记录表id */	
  p_pk				int											,		/**  玩家角色id  */
  p_grade			int											,		/**  玩家等级   **/
  loginStatus		int											,		/**  登陆状态 */
  loginTime			datetime									,		/**  最后上线时间  */	
  primary key(id));
  	
  	
 
  /*************** 玩家在线时长表 ***********************************/
  	create table user_online_time(
  		id			int unsigned not null auto_increment		,	/**  玩家在线时长表id **/
  		u_pk		int											,	/*** 玩家id ***/
  		p_pk		int											,	/**  角色id  **/
  		onlinetime	int											,	/*** 在线时间长度 **/
  		createTime	datetime									,	/**  创建时间 **/	
  	 primary key(id));
  	 
  	 
  
  /***********上线玩家等级表*********************/
  create table user_login_grade(
  	id 				int unsigned not null auto_increment		,	/** 上线玩家等级表id **/
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
  	id 				int unsigned not null auto_increment		,	/**  沉默玩家等级表id **/
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
  		id				int unsigned not null auto_increment		,	/** 日常等级表id **/
  		grade1			int											,	/**  等级为1的等级数量 */	
  		grade2to9		int											,	/**  等级2到9玩家数量 **/
  		grade10to19		int											,	/**  等级10到19玩家数量**/
  		grade20to29		int											,	/**  等级20到29玩家数量**/
 		grade30to39		int											,	/**  等级30到39玩家数量**/
   		grade40to49		int											,	/**  等级40到49玩家数量 **/
  		grade50to59		int											,	/**  等级50到59玩家数量 **/
    	grade60			int											,	/**  等级60玩家数量 **/
  		recordTime		varchar(50)									,	/*** 所记录日期 **/
  		createTime	datetime										,	/**  创建时间 **/	
  	 primary key(id));	
  							
  		
  																																		
   /*************** 玩家在线时间段表 ***********************************/
  	create table user_online_num(
  		id				int unsigned not null auto_increment		,	/** 日常等级表id **/
  		hour_time			int										,	/**	一天中点时 **/	
  		player_num			int										,	/**	一天中点时的在线人数 **/
  		record_time		varchar(50)									,	/** 记录日期 */
  		insert_time		datetime									,	/** 插入时间 */ 
  	 primary key(id));
  	
  	
  	/*************** 玩家信息总览表 ***********************************/
  	create table user_info_overview(
  		id						int unsigned not null auto_increment		,	/** 日常等级表id **/
  		all_regist_num			int											,	/** 总注册人数 **/	
  		all_regist_user			int											,	/**	总注册角色  **/
  		today_regist_num		int											,	/** 今日注册人数 */
  		today_online_num		int											,	/** 今日上线人数 */
  		today_active_num		int											,	/** 今日活跃人数 */
  		today_avg_time 			int											,	/** 今日平均在线时间 */
  		today_avg_grade 		int											,	/** 今日平均在线等级 */
  		today_avg_num			int											,	/** 今日平均在线人数 */
  		insert_time		datetime											,	/** 插入时间 */ 
  	 primary key(id));
  	 
  	 
  	 
  	 
  	 
  	 
  	 
  	
  	 
  	 
  	 /*****************   帮派攻城战表  (后台)   ***************/
  	 create table tong_siege_battle (
  	 		siege_id			int unsigned not null auto_increment  ,		/** 攻城战ID,代表着某个城市的攻城  */
  	 		siege_name			varchar(100)						 						,		/**  攻城战场的名字 ***/
  	 		map_id				int 														,		/*** 帮派攻城战所对应的map_type  **/
  	 		affect_map_id  int														,		/**   影响的地图MAP_ID,在此ID 内的区域都受此城市管辖,如多于一个, 以逗号区分 ***/
  	 		tax					int														,		/*****   此工程战场所代表区域的税率水平,在1到10之间   *****/
  	 		tax_money		int														,		/**      此攻城战场所代表区域的税金      **/
  	 		out_scene		int														,		/****   强制传出时的地点    */		
  	 		relive_scene	varchar(20)											,		/****   玩家复活点,1为攻城方,2为守城方的.    */		
  	  primary key(siege_id));		  	 		
  	 
  	 
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
  	 
  	 
  	 
  	 
  	 
  	 /***  传送表  ****/
  	  create table carry_table_info (
  	  		carry_id			int unsigned not null auto_increment  ,		/** 传送表ID  */
  	  		carry_type_id		int														,		/***  地点类型   ****/	
  	  		carry_type_name		varchar(50)												,		/***  地点类型名称 **/	
  	  		scene_id			int														,		/**   地点id    **/
  	  		scene_name  		varchar(50)												,		/***  地点名称  ****/
  	  		carry_grade			int														,		/**   地点传送等级  **/
  	 primary key(carry_id));
  	 
  	 
  	  /***  伤害记录表  ****/
  	  create table injure_recond_info (
  	  		injure_id			int unsigned not null auto_increment 					 ,		/** 伤害记录表ID  */
  	  		tong_id				int																,		/***  帮派ID   ****/	
  	  		injure_number		int(50)													,		/***  伤害数值 **/	
  	  		npc_ID				int														,		/**   NPC_id    **/
  	  		npc_Type  			smallint												,		/***  NPC_type,6代表英雄雕像  ****/
  	 primary key(injure_id));
  	 
  	 
  	   	  /***  装备展示表  ****/
  	  create table zb_relela_info (
  	  		relela_id			int unsigned not null auto_increment 					 ,		/** 伤害记录表ID  */  	  		
  	  		pwpk		int(11)													,		/***  伤害数值 **/	
  	  		relelavar			varchar(200)								,		/**   装备展示字符    **/
  	  		relelatime			datetime												,		/**    装备展示时间   ****/
  	 primary key(relela_id));
  	 
  	 
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
  	  		
  	  		
  	  		give_goods			varchar(100)												,		/**    奖励装备或物品, 以","分割, 和兑换菜单的制作方法相同    ****/
  	  		isuseable			int(3)											default 1		,		/**   是否有效,为零表示无效,不会被显示出来. 为1的话则可以显示  **/
  	  		
  	  		horta_display		varchar(100)												,		/**   奖励描述  **/
  	 primary key(horta_id));
  	 
  	 
  	 
  	 
  	 
  	 
  	 
  	 
  	 
  	 
  																								