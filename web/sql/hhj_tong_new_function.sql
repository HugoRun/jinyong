/******************************************************************
**
**改SQL文件为帮派新功能 帮派资源,帮派升级,帮派建筑,帮派攻城,帮派经济,帮派技能,
**
******************************************************************/

/*************************************后台表******************************************************/
/*************** 帮派所有建筑表(tong_build)*********************************/
 create table tong_build(
   tb_id                smallint unsigned not null auto_increment , /**主键*/ 
   tb_name              varchar(100)                              , /**建筑名称*/ 
   tb_grade             int                                       , /**建筑等级*/
   tb_definition        varchar(200)                              , /**建筑说明*/
   tb_group             int                                       , /**建筑升级组*/
   tb_map               int                                       , /**建筑所在的地图*/
   primary key (tb_id)); 
   
   insert into tong_build values (null,'广场',1,'帮会驻地广场，链接各个帮会建筑',0,898);
   insert into tong_build values (null,'青石路',1,'大块大块的石板，已经十分平滑',0,903);
   insert into tong_build values (null,'青石路',1,'大块大块的石板，已经十分平滑',0,904);
   insert into tong_build values (null,'聚义厅',1,'聚义厅帮派初始建筑',1,895);
   insert into tong_build values (null,'金库',1,'金库帮派初始建筑',1,893);
   insert into tong_build values (null,'木料场',1,'木料场',2,894);
   insert into tong_build values (null,'武器库',1,'武器库',2,900);
   insert into tong_build values (null,'石料厂',1,'石料厂',2,897);
   insert into tong_build values (null,'习武堂',1,'习武堂',3,896);
   insert into tong_build values (null,'万兽堂',1,'万兽堂',3,902);
   insert into tong_build values (null,'回春堂',1,'回春堂',3,899);
   insert into tong_build values (null,'武圣堂',1,'武圣堂',4,901); 
   
  
/***************帮派建筑升级条件表(tong_build_up)*********************************/
 create table tong_build_up(
   tbu_id                smallint unsigned not null auto_increment , /**主键*/ 
   tb_id                 int                                       , /**建筑id*/
   tb_grade              int                                       , /**建筑等级*/
   tb_name               varchar(100)                              , /**建筑name*/
   tb_money              varchar(100)                              , /**建筑升级所需要的钱文计算*/
   tbu_prop              varchar(200)                              , /**建筑升级所需要的道具 道具ID 用逗号分割*/
   tbu_prop_number       varchar(200)                              , /**建筑升级所需要的道具数量 道具数量用逗号分割*/
   primary key (tbu_id));

/***************帮派建筑条件表(tong_build_condition)*********************************/
 create table tong_build_condition(
   tbc_id                smallint unsigned not null auto_increment , /**主键*/ 
   tb_id                 int                                       , /**建筑id*/
   tb_grade              int                                       , /**建筑等级*/
   tb_name               varchar(100)                              , /**建筑name*/
   tb_money              varchar(100)                              , /**建筑升级所需要的钱文计算*/
   tbu_prop              varchar(200)                              , /**建筑升级所需要的道具 道具ID 用逗号分割*/
   tbu_prop_number       varchar(200)                              , /**建筑升级所需要的道具数量 道具数量用逗号分割*/
   primary key (tbc_id));



/******************************************************************
**
**改SQL文件为帮派新功能 帮派资源,帮派升级,帮派建筑,帮派攻城,帮派经济,帮派技能,
**
******************************************************************/ 
/*************************************前台表******************************************************/
/***************帮派现有建筑表(u_tong_build)*********************************/
 create table u_tong_build(
   utb_id               smallint unsigned not null auto_increment , /**主键*/ 
   t_pk                 int                                       , /**帮派ID*/
   tb_id                int                                       , /**建筑id*/
   tb_name              varchar(100)                              , /**建筑名称*/ 
   tb_grade             int                                       , /**建筑等级*/
   tb_definition        varchar(200)                              , /**建筑说明*/
   tb_group             int                                       , /**建筑升级组*/
   tb_map               int                                       , /**建筑所在的地图*/
   primary key (utb_id)); 
    
/***************帮派资源的钱(u_tong_money)*********************************/
 create table u_tong_money(
   utm_id               smallint unsigned not null auto_increment , /**主键*/ 
   t_pk                 int                                       , /**帮派ID*/
   utm_money            varchar(100)                              , /**金库的钱文计算*/
   utm_wrap_content		int                            default 100, /**帮派资源容量*/
   utm_wrap_spare		int                            default 100, /**帮派资源剩余数量*/
   primary key (utm_id)); 
 
/**********帮派资源装备表(u_tong_resource_equip)***************/
create table u_tong_resource_equip ( 
   re_pk            smallint unsigned not null auto_increment ,	  /**帮派资源装备表*/
   t_pk             int                                       ,   /**帮派ID*/
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
   suit_id          int                               default 0,  /**对应套装id,不是套装默认为0*/
   w_wx_type        int                                        ,  /**装备五行类型*/
   w_buff_isEffected  int                             default 0,  /**附加buff是否有效，0表示无效，1表示有效*/
   enchant_type		  varchar(50)					  default 0,  /**附加点化属性  */
   enchant_value	  int							  default 0,  /**附加点化属性值 */
   w_zj_hp		      varchar(100)                             ,  /**追加气血 */
   w_zj_mp			  varchar(100)                             ,  /**追加内力 */	
   w_zj_wxgj		  varchar(100)                             ,  /**追加攻击 */
   w_zj_wxfy	      varchar(100)                             ,  /**追加防御 */ 
   w_zb_grade		  int									   ,  /**装备的等级 */	
   primary key (re_pk));
 
/*************************帮派资源道具表(u_tong_resource_prop),每条记录：记录的是一组道具********************************/
  create table u_tong_resource_prop(
  	rp_pk	                         smallint unsigned not null auto_increment       ,  /**id*/ 
  	t_pk                             int                                             ,  /**帮派ID*/
    pg_type                          int                                             ,	/**道具组分类*/ 
    prop_id			                 int                                             ,	/**道具id*/
    prop_type						 int                                             ,  /**道具类型*/
    prop_bonding                     int                                             ,
    prop_protect                     int                                             ,
    prop_isReconfirm                 int                                             ,											
    prop_use_control                 int                                    default 0,  /**控制道具是否可用，0表示不受限制，1表示战斗时不可用*/
    prop_name	                     varchar(200)                                    ,  /**道具名字*/
    prop_price                       int                                             ,  /**卖出价钱*/
    prop_num                         int                                             ,	/**道具数量，不能超过限制格数*/
    auction_type                     int                                             ,  /**道具存储分类*/
    primary key(rp_pk));
    
/*************************帮派资源道记录表LOG (u_tong_res_log)********************************/
  create table u_tong_res_log(
  	l_pk	                         smallint unsigned not null auto_increment       ,  /**id*/ 
  	t_pk                             int                                             ,  /**帮派ID*/
    l_content                        varchar(300)                                    ,	/**日志内容*/
    create_time                      datetime                                        ,  /**日志记录时间*/
    primary key(l_pk));

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
    primary key(en_pk));
    
/**********玩家好友(u_friend)***************/
 create table u_friend(
    f_pk	               int not null auto_increment , /**玩家好友id*/ 
    p_pk	               int                                       , /**玩家id*/
    fd_pk                  int                                       , /**好友ID*/
    fd_name                varchar(100)                              , /**好友名称*/
    fd_online              int                              default 0, /**好友是否在线 0 不在线 1在线*/
    create_time            datetime                                  , /**加入时间*/ 
    primary key(f_pk));
    
/**********禁言(u_embargo)***************/
 create table u_embargo(
    e_pk	               int not null auto_increment               , /**禁言ID*/ 
    p_pk	               int                                       , /**玩家id*/
    p_name	               varchar(200)                              , /**玩家名称*/
    begin_time             datetime                                  , /**禁言开始时间*/
    end_time               datetime                                  , /**禁言结束时间*/ 
    e_time                 varchar(100)                              , /**禁言时间*/ 
    primary key(e_pk));

/**********灵魂绑定(u_soulbang)***************/
 create table u_soulbang(
    s_pk	               int not null auto_increment               , /**禁言ID*/ 
    p_pk	               int                                       , /**玩家id*/
    pw_pk	               int                                       , /**装备包裹ID*/
    primary key(s_pk));

/**********IP白名单(ip_whitelist)***************/
 create table ip_whitelist(
    ip_pk	               int not null auto_increment               , /**黑名单ID*/ 
    ip_begin               varchar(100)                              , /**IP开始*/
    ip_end                 varchar(100)                              , /**IP结束*/ 
    primary key(ip_pk));
    
/*********IP黑名单(ip_blacklist)***************/
 create table ip_blacklist(
    ip_pk	               int not null auto_increment               , /**黑名单ID*/ 
    ip_list                varchar(100)                              , /**IP开始*/
    primary key(ip_pk));
    
    
    
    
    
    
    
    
    
    
    
drop database jygame_book;
create database jygame_book;  
use jygame_book;
 /***********************************书籍类型***********************************/ 
  create table book_type(
  	ty_id                  int not null auto_increment                 ,/**书籍类型ID**/
  	ty_name                varchar(100)                                ,/**类型名称**/
  	primary key (ty_id));
  
 /***********************************书籍信息表***********************************/
  create table book_info(
  	book_id                int not null auto_increment                 ,/**书籍ID**/
  	ty_id                  int                                         ,/**书籍类型**/
  	book_name              varchar(100)                                ,/**书籍名称**/
  	author                 varchar(20)								   ,/**小说作者**/					
  	book_line              varchar(3000)                               ,/**书籍描述**/
  	book_stow              int                               default  0,/**小说收藏次数**/
  	read_count             int                               default  0,/**小说阅读次数**/ 
  	primary key (book_id));
  	
 /***********************************书籍章节***********************************/
  create table book_chapter(
  	ch_id                  int not null auto_increment                 ,/**书籍章节id**/
  	ch_name                varchar(20)                                 ,/**章节名称**/
  	book_id                int                                         ,/**书籍ID**/
  	new_time               datetime                                    ,/**更新时间***/
  	primary key (ch_id));
  	 
 /***********************************我的书架***********************************/
  create table book_case(
    ca_id         int not null auto_increment                        ,/**主键id*/
	p_pk          int                                                ,/**角色PK*/
	u_pk          int                                                ,/**角色账号*/
	p_subarea     int                                                ,/**角色所属游戏分区*/
	book_id       int                                                ,/**用户所对应的书籍编号*/
	ch_id         int                                                ,/**书签编号***/
	primary key(ca_id));
	
 /***********************************游戏分区***********************************/
  create table subarea(
    su_id         int not null auto_increment                        ,/**主键id*/
	su_ip         varchar                                            ,/**游戏分区IP*/
	su_name       varchar                                            ,/**游戏分区用户名*/
	su_paw        varchar                                            ,/**游戏分区密码*/
	su_type       int                                                ,/**游戏分区*/
	primary key(su_id));
	 
 /***********************************阅读记录***********************************/
  create table read_log(
    re_id         int not null auto_increment                        ,/**主键id*/
	p_pk          int                                                ,/**玩家ID*/
	ch_id         int                                                ,/**章节ID*/
	read_time     datetime                                           ,/**阅读时间***/
	su_type       int                                                ,/**游戏分区*/
	primary key(su_id));
	 
 /***********************************玩家离线记录***********************************/
  create table role_be_off(
    off_id              int not null auto_increment                        ,/**主键id*/
	p_pk                int                                                ,/**玩家ID*/
	be_off_time         varchar(50)                                        ,/**上次离线时间*/
	already_time        varchar(50)                              default  0,/**离线时间分钟*/
	be_off_exp          varchar(50)                              default  0,/**离线经验*/
	prop_cumulate_time  varchar(50)                              default  0,/**道具累积时间 就是可共领取经验的时间*/
	primary key(off_id));	

 /***********************************离线道具（后台的表）***********************************/
  create table be_off_prop(
    be_id               int not null auto_increment                        ,/**主键id*/
	prop_name           varchar(50)                              default  0,/**道具名称*/
	prop_display        varchar(500)                                       ,/**道具描述*/
	prop_money          varchar(50)                              default  0,/**所需元宝*/
	prop_time           varchar(50)                              default  0,/**道具时间 小时计算*/
	primary key(be_id));


 /***********************************玩家离线buff***********************************/
  create table role_be_off_buff(
    b_id                int not null auto_increment                        ,/**主键id*/
	p_pk                int                                                ,/**玩家ID*/
	be_off_time         varchar(50)                              default  0,/**上次离线时间*/
	be_off_exp          varchar(50)                              default  0,/**离线经验*/
	primary key(b_id));	
	
	
	
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
	primary key(ho_id));		

 /***********************************称号表(t_role_honour)***********************************/
  create table t_role_honour(
    ro_id                int not null auto_increment                        ,/**主键id*/
    p_pk                 int                                                ,/**角色主键*/
    ho_id                int                                                ,/**称号主键*/
    ho_type              int                                                ,/**称号类型*/
    is_reveal            int                                       default 0,/**是否显示*/
	primary key(ro_id));	
	
 /***********************************会员后台表(vip)***********************************/
  create table vip(
    v_id                 int not null auto_increment                        ,/**主键id*/
    v_name               varchar(100)                                       ,/**VIP名称*/
    use_time             int                                                ,/**使用时间 小时计算*/
    mall_agio            int                                                ,/**商场折扣*/
    ho_id                int                                                ,/**称号ID*/
    is_die_drop_exp      int                                       default 0,/**死亡是不是损失经验 0 损失 1不损失*/
    v_hint               varchar(600)                                       ,/**VIP说明*/
	primary key(v_id));

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
	
/**********帮会成员退出帮派(u_tong_member_out)***************/
create table u_tong_member_out(
    o_pk	               int not null auto_increment               , /**id*/ 
  	t_pk 	               int                                       , /**帮会id*/ 
  	p_pk                   int                                       , /**人员ID*/
    out_time               datetime                                  , /**退出帮会时间*/ 
    primary key(o_pk));
