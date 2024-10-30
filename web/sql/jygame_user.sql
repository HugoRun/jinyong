drop database jygame_user;
create database jygame_user;
use jygame_user;
/**********���ע���(u_login_info)***************/
create table u_login_info ( 
   u_pk             int not null auto_increment ,	 /**������Ա��Ϣid*/ 
   u_name           varchar(20)                      UNIQUE   ,  /**����Ա��¼��*/ 
   u_paw            varchar(50)                              ,  /**����Ա��¼����*/
   login_state      int                                       ,  /**��½״̬ 1Ϊ��½ 0 Ϊδ��½*/ 
   create_time      datetime                                  ,  /**����ʱ��*/
   last_login_ip    varchar(20)                               ,  /**�������½��IP��ַ*/
   last_login_time  datetime                     default null ,  /**������һ�ε�½ʱ��*/
   yuanbao          int                          default    0 ,  /**Ԫ������*/
   jifen            int                          default    0 ,  /**�̳ǻ�������*/
   super_qudao      varchar(255)                 default null ,/**������*/
   qudao            varchar(255)                 default null,/**������*/
   primary key (u_pk))  ENGINE=innodb;
create index Index1 on u_login_info(login_state);

/**********��Ұ���������Ҫ�ж�(u_login_sift)***************/
create table u_login_sift ( 
   s_pk             int not null auto_increment               ,  /**������Ա��Ϣid*/ 
   u_name           varchar(20)                              ,  /**����Ա��¼��*/
   primary key (s_pk));
create index Index1 on u_login_sift(u_name);

/**********��ɫ��Ϣ��(u_part_info)***************/
create table u_part_info ( 
   p_pk             int not null auto_increment ,	 /**��ɫid*/ 
   u_pk             int                                       ,	 /**������Ա��Ϣid*/ 
   p_name           varchar(20)                      UNIQUE   ,  /**��ɫ�����û���Ψһ*/
   p_sex            int                                       ,  /**�Ա�*/   
   p_grade          int                                       ,  /**�ȼ�*/ 
   p_up_hp          int                                       ,  /**����ֵ*/ 
   p_hp             int                                       ,  /**��ǰHPֵ*/ 
   p_up_mp          int                                       ,  /**����ֵ*/
   p_mp             int                                       ,  /**����ֵ*/
   p_force          int                                       ,  /**��*/
   p_agile          int                                       ,  /**��*/
   p_physique       int                                       ,  /**����	*/
   p_savvy          int                                       ,  /**����	*/
   p_gj             int                                       ,  /**����*/
   p_fy             int                                       ,  /**����*/
   p_zbgj_xiao      int                                       ,  /**��С����*/
   p_zbgj_da        int                                       ,  /**��󹥻�*/
   p_zbfy_xiao      int                                       ,  /**��С����*/
   p_zbfy_da        int                                       ,  /**������*/
   p_teacher_type   int                                       ,  /**ʦͽ1ʦ��2ͽ��*/
   p_teacher        int                                       ,  /**ʦ��������id	*/
   p_harness        int                                       ,  /**�Ƿ��ѻ� 1û��� 2 ���*/
   p_fere           int                                       ,  /**����ID*/
   p_title          varchar(20)                              ,  /**�ƺ�*/
   p_title_name     varchar(50)                              ,  /**�ƺ�����*/
   p_race          int                                                  ,/**��ɫ����1��2����**/
   p_born           int                                       ,  /**����*/
   p_camp           int                                       ,  /**��Ӫ*/
   p_camp_name      varchar(50)                              ,  /**��Ӫ����*/
   p_school         varchar(20)                              ,  /**����*/
   p_school_name    varchar(50)                              ,  /**��������*/ 
   p_experience     varchar(20)                              ,  /**����*/
   p_benji_experience     varchar(20)                        ,  /**��������*/
   p_xia_experience varchar(20)                              ,  /**�¾���*/
   p_silver         varchar(5)                              ,  /**Ԫ��*/
   p_copper         varchar(20)                              ,  /**ͭǮ��λ ��*/
   p_depot          int                                       ,  /**�ֿ�	*/
   p_pk_value       int                                       ,  /**pkֵ*/
   p_pks            int                                       ,  /**����1��2��*/
   p_drop_multiple       double                               , /**���ﱩ����*/
   p_pk_changetime  datetime                                  ,  /**pk���ظı��ʱ��*/
   p_isInitiative	int								 default 0,  /**��ʶ�Ƿ�����������״̬��0��1��*/
   p_isPassivity	int								 default 0,  /**��ʶ�Ƿ��ڱ�������״̬��0��1��*/	 
   p_map            varchar(5)                              ,  /**���ڳ���ID*/
   p_procession     int                                       ,  /**�Ƿ����0��1��*/
   p_procession_numner  varchar(5)                          ,  /**������*/
   p_tong           int                                       ,  /**���*/
   p_tong_name      varchar(50)                              ,  /**�������*/
   
   p_wrap_content		int									,  /**��������*/
   p_wrap_spare			int									,  /**����ʣ������*/
   
   create_time      datetime                                  ,  /**����ʱ��*/
   delete_flag		tinyint						default  0	 , /** ɾ����־��,0��ʾ����״̬��-1��ʾ��ɫ�Ѿ���ɾ����1��ʾɾ������״̬ */
   delete_time		datetime									, /** ɾ��ʱ�� */
   te_level               int                        default 0,
   chuangong         varchar(50)          default null,
   login_state               int                        default 0,/**��½״̬*/
   p_kill_num		int									default 0,/***ɱ����**/
   last_shoutu_time  datetime,
   player_state_by_new		int									default 0,/***����״̬  1Ϊ���� 0�������� 11Ϊ�ο����� 10Ϊ�οͲ�������**/
   primary key (p_pk)) ENGINE=innodb;
   create index Index1 on u_part_info (p_pk,u_pk);
   create index Index2 on u_part_info (u_pk);
   

  
/**********��ɫװ����(u_part_equip)***************/
create table u_part_equip ( 
   pw_pk            int not null auto_increment ,	  /**��ɫ������*/
   p_pk             int                                       ,	  /**��ɫid*/ 
   table_type       int                                       ,   /**��Ʒ��ر�����*/
   goods_type       int                                       ,   /**��Ʒ����*/
   w_id             int                                       ,   /**��ƷID*/
   w_name           varchar(20)                              ,   /**��Ʒ����*/
   w_durability     int                                       ,   /**�;�*/ 
   w_dura_consume   int                                       ,   /**�;�����*/
   w_Bonding        int                                       ,   /**��*/ 
   w_protect        int                                       ,   /**����*/ 
   w_isReconfirm    int                                       ,   /**����ȷ��*/ 
   w_price          int                                       ,   /**������Ǯ*/
   w_fy_xiao        int                               default 0,  /**����������С����*/
   w_fy_da          int                               default 0,  /**��������������*/ 
   w_gj_xiao        int                               default 0,  /**����������С����*/
   w_gj_da          int                               default 0,  /**����������󹥻�*/ 
   w_hp             int                               default 0,  /**����������Ѫ*/
   w_mp             int                               default 0,  /**�������Է���*/
   w_jin_fy         int                               default 0,  /**�������Խ������*/
   w_mu_fy          int                               default 0,  /**��������ľ������*/
   w_shui_fy        int                               default 0,  /**��������ˮ������*/
   w_huo_fy         int                               default 0,  /**�������Ի������*/
   w_tu_fy          int                               default 0,  /**����������������*/
   w_jin_gj         int                               default 0,  /**�������Խ𹥻���*/
   w_mu_gj          int                               default 0,  /**��������ľ������*/
   w_shui_gj        int                               default 0,  /**��������ˮ������*/
   w_huo_gj         int                               default 0,  /**�������Ի𹥻���*/
   w_tu_gj          int                               default 0,  /**����������������*/ 
   w_type           int                               default 0,  /**�Ƿ�װ�� 0 û�� 1��װ����*/
   w_quality        int                                        ,  /**Ʒ��*/
   suit_id          int                               default 0,  /**��Ӧ��װid,������װĬ��Ϊ0*/
   w_wx_type        int                                        ,  /**װ����������*/
   w_buff_isEffected  int                             default 0,  /**����buff�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч*/
   enchant_type		  varchar(10)				      default 0,  /**���ӵ㻯����  */
   enchant_value	  int		                      default 0,  /**���ӵ㻯����ֵ */
   w_zj_hp		      varchar(10)                             ,  /**׷����Ѫ */
   w_zj_mp			  varchar(10)                             ,  /**׷������ */	
   w_zj_wxgj		  varchar(10)                             ,  /**׷�ӹ��� */
   w_zj_wxfy		  varchar(10)                             ,  /**׷�ӷ��� */ 
   w_zb_grade		  int                                      ,  /**װ���ĵȼ� */		
   create_time        datetime                                 ,  /**����ʱ��*/
   p_poss             int                             default 0,  /**����ʱ��*/
   w_Bonding_Num      int                             default 0,   /**����󶨴�����*/
   specialcontent     int                             default 0,   /**����������*/
   primary key (pw_pk)) ENGINE=innodb;
   create index Index1 on u_part_equip (p_pk,w_id,table_type);
   create index Index2 on u_part_equip (p_pk,w_type,table_type);
   create index Index3 on u_part_equip (p_pk,w_type,w_dura_consume);
  
 /**********��ɫ�ֿ��(u_warehouse_info)*****************************/
create table u_warehouse_info (
	uw_id	    int not null auto_increment 	,	/** ��ɫ������*/
	u_pk		int 										,	/** ������Ա��Ϣid*/
	p_pk 		int 										,	/** ��ɫid*/
	uw_prop_id  int 										,   /** ��Ʒid���������ڸ��Է�����е�id */
	uw_type 	int 										,	/** �ֿ���� */
	
	uw_prop_type int										,	/** ��Ʒ���� */
	uw_number 	int 							default 100	,	/** �ֿ�������ޣ�Ĭ��Ϊ100�� */
	uw_article	varchar(50)								,	/** �ֿ���Ʒ*/
	uw_prop_number int 										,	/** ��Ʒ���� */
	uw_money 	varchar(50)				default 10000000000	,	/** ��Ǯ�ֿ����� ��Ĭ��Ϊһ����.*/
	
	uw_pet		varchar(10)								,	/** ����ֿ�,������ǳ������� */
	uw_money_number	varchar(15)								,	/** ��Ǯ�ֿ� */
	uw_pet_number	int 						default 5	,  	/** ����ֿ����ޣ�Ĭ��Ϊ5 */
	uw_warehouse_spare int									,	/** ��Ʒ�ֿ������� */
	create_time 	datetime 								,	/** ����ʱ��*/
	
	prop_bonding	int										,	/** ���߰� */
	prop_protect	int										,	/** ���߱���*/
	prop_isReconfirm	int									, 	/** ���߶���ȷ��*/
	prop_use_control	int									,	/** �����Ƿ����*/ 	
	
	prop_operate1    varchar(50)							,	/** ���������ֶ�1 , Ŀǰ��Ϊ��װҩʹ�� */	
	primary key (uw_id));	
	create index Index1 on u_warehouse_info (p_pk);
	create index Index2 on u_warehouse_info (uw_type,uw_prop_id);
	
   
/**********��ɫ�����(p_pet_info)***************/ 
create table p_pet_info(
	pet_pk			 int not null auto_increment , /**ID */
	p_pk			 int 					                   , /**��ɫid*/
	pet_id			 int					                   , /**��Ӧpet�����id*/
	pet_name		 varchar(20)				               , /**��������*/
	pet_nickname	 varchar(20)				               , /**�����ǳ�*/
	pet_grade		 int					                   , /**�ȼ�*/
	pet_exp			 int			                           , /**����*/
	pet_ben_exp		 int			                  default 0, /**��ǰ���龭��*/
	pet_xia_exp		 int                                       , /**�¼�����ﵽ��һ����Ҫ�ľ���*/
	pet_gj_xiao      int                     		           , /**��С����*/
	pet_gj_da        int					                   , /**��󹥻�*/
	pet_sale		 int			                           , /**�����۸�*/
	pet_img			 varchar(50)                              , /**����ͼƬ*/
	pet_grow	     double                                    , /**����ɳ��ʡ�*/
	pet_wx           int                  			           , /**�������Խ�=1��ľ=2��ˮ=3����=4����=5 */
    pet_wx_value     int           				               , /**��������ֵ*/
    pet_skill_one	 int                                       , /**����1	��ѧϰ�ļ���id*/
    pet_skill_two	 int                          	           , /**����2	��ѧϰ�ļ���id*/
    pet_skill_three	 int                        	           , /**����3	��ѧϰ�ļ���id*/
    pet_skill_four	 int                          		       , /**����4	��ѧϰ�ļ���id*/
    pet_skill_five	 int                                  	   , /**����5	��ѧϰ�ļ���id*/
    pet_life		 int			                           , /**����**/
    pet_isAutoGrow   int                                       , /**����	�Ƿ����Ȼ����*/	
    pet_isBring		 int					                   , /**�Ƿ�������:1��ʾ��ս��״̬��0��ʾ��*/
    pet_fatigue		 int			 	                       , /**ƣ�Ͷ�0-100,��ս״̬������ƣ�Ͷȣ�һ��Сʱ��10��*/
    pet_longe		 int			 	                       , /**��������*/
    longe_number     int			 	                       , /**��������ʹ�ô���*/
    longe_number_ok  int			 	                       , /**���������Ѿ�ʹ�ô���*/
    skill_control    int			 	                       , /**�������������ѧϰ���ٸ�����*/
    pet_type         int                                       , /**��������*/
    pet_init_num     int                              default 0, /**ϴ����Ĵ���*/
    pet_violence_drop double                                   , /**���ﱩ����*/
    primary key (pet_pk)) ENGINE=innodb;
create index Index1 on p_pet_info (pet_pk,p_pk,pet_id);
create index Index2 on p_pet_info (p_pk,pet_isBring);
    
    
/**********��ɫ���ｻ�ױ�(u_pet_sell)***************/ 
create table u_pet_sell(
	ps_pk			     int not null auto_increment , /**ID */
	p_pk			     int 					                   , /**�����ɫid*/
	p_by_pk			     int 					                   , /**�������ɫid*/ 
	pet_id			     int					                   , /**��Ӧpet�����id*/
	ps_silver_money      int                                       , /**��������Ҫ��Ʒ�ļ۸������*/
    ps_copper_money      int                                       , /**��������Ҫ��Ʒ�ļ۸��ͭǮ*/
    create_time          datetime                                  , /**����ʱ��*/
    primary key (ps_pk)) ENGINE=innodb;
    create index Index1 on u_pet_sell (p_pk,p_by_pk);
    
    
/**********��ɫ����(u_sell_info)***************/
create table u_sell_info ( 
   s_pk                   int not null auto_increment ,	 /**id*/
   p_pk                   int                                       ,	 /**���������ɫid*/
   p_by_pk                int                                       ,	 /**�������ɫid*/
   s_wuping               int                                       ,    /**��������Ҫ���׵���Ʒ*/
   s_wp_type              int                                       ,    /**��������Ҫ��Ʒ����*/
   s_wp_number            int                                       ,    /**��������Ҫ��Ʒ������*/ 
   s_wp_silver_money      int                                       ,    /**��������Ҫ��Ʒ�ļ۸������*/
   s_wp_copper_money      int                                       ,    /**��������Ҫ��Ʒ�ļ۸��ͭǮ*/ 
   sell_mode              int                                       ,    /**��������Ҫ���׵�����*/
   create_time            datetime                                  ,    /**����ʱ��*/
   primary key (s_pk)) ENGINE=innodb;
   create index Index1 on u_sell_info (p_pk,p_by_pk);
   
   
/****************************************LS****************************/
      

  
  /**********************************��ֵ�����Ʒ��***********************************************/
  create table n_dropgoods_info(
  d_pk						 int NOT NULL AUTO_INCREMENT ,/**ID */
  p_pk						 int					   				   ,/**��ɫid*/
  drop_num					 int									   ,/*��������*/
  goods_id					 int 									   ,/*��Ʒid*/
  goods_name				 varchar(20)							   ,/*��Ʒ����*/
  goods_type				 int									   ,/*��Ʒ����*/
  goods_quality              int                              default 0,/**������Ʒ��Ʒ�� 0��ʾ��ͨ��1��ʾ���㣬2��ʾ����3��ʾ��Ʒ*/
  goods_importance		  int								default  0, /** ��Ʒ�Ƿ���Ҫ��1Ϊ��Ҫ,0Ϊ����Ҫ�������Ҫ�Ļ�,�ͷ�ϵͳ��Ϣ  */
  goods_dropInfo          varchar(100)				  				, /***    ������Ϣ��ʾ  *****/
  primary key (d_pk)) ENGINE=innodb; 
  create index Index1 on n_dropgoods_info (p_pk);
  
/**********************************���ʱnpc�������Ǯ����ʱ��***********************************************/
    
 create table n_dropExpMoney_info(
   d_pk							 int NOT NULL AUTO_INCREMENT ,/**ID */
   p_pk							 int					   				   ,/**��ɫid*/
   drop_exp					     int									   ,/*npc���侭��*/	
   drop_money				     int									   ,/*npc�����Ǯ��*/
   drop_pet_exp					 int									   ,/** npc���������ľ��� */		
   primary key (d_pk)) ENGINE=innodb;
     
   
  /*****************************pk֪ͨ(u_pk_notify)*****************************************/
   create table u_pk_notify(
    n_pk                                 int not null auto_increment        , /**id*/
    notifyed_pk                          int                                              , /**��֪ͨ�����id*/
    create_notify_pk                     int                                              , /**����֪ͨ�����id*/ 
    notify_content                       varchar(200)                                     , /**֪ͨ������**/
    notify_type                          int                                              , /**֪ͨ���ͣ�1.�ܵ�����***/
    create_time                          datetime                                         , /**����ʱ��*/
   primary key(n_pk));
  create index Index1 on u_pk_notify (notifyed_pk);
    
/*************************��ҿ�ݼ����ã�u_shortcut_info��********************************/
    create table u_shortcut_info(
    sc_pk							 int not null auto_increment   ,/**id*/ 
    p_pk							 int										 ,/**���id*/
    sc_name							 varchar(20)                                 ,/**��ݼ����֣����*/
    sc_display						 varchar(20)                                 ,/**���ú���ʾ�����֣����磻��������,ҩƷ����*/
    sc_type							 int                                         ,/**����,ֵΪ-1ʱ��ʾû�����ÿ�ݼ�*/
    operate_id						 int                                         ,/**����id*/
    object							 int                                         ,/**���ö���*/          
    primary key(sc_pk));
create index Index1 on u_shortcut_info(p_pk);

/*************************��������Զ���֣�u_automatism��********************************/
    create table u_automatism(
    a_pk							 int not null auto_increment   ,/**id*/ 
    p_pk							 int										 ,/**���id*/ 
    sc_type							 int                                         ,/**����,ֵΪ-1ʱ��ʾû�����ÿ�ݼ�*/
    operate_id						 int                                         ,/**����id*/
    object							 int                                         ,/**���ö���*/          
    primary key(a_pk));
create index Index1 on u_automatism (p_pk);

/*************************��Ұ�����ĵ��ߣ�u_propgroup_info��,ÿ����¼����¼����һ�����********************************/
  create table u_propgroup_info(
  	pg_pk	                         int not null auto_increment       ,   /**id*/ 
    p_pk                             int                                             ,	 /**��ɫid*/
    pg_type                          int                                             ,	 /**���������*/ 
    prop_id			                 int                                             ,	 /**����id*/
    prop_type						 int                                             ,   /**��������*/
    prop_bonding                     int                                             ,   /**���߰�**/
    prop_protect                     int                                             ,
    prop_isReconfirm                 int                                             ,											
    prop_use_control                 int                                    default 0,  /**���Ƶ����Ƿ���ã�0��ʾ�������ƣ�1��ʾս��ʱ������*/
    prop_name	                     varchar(20)                                     ,  /**��������*/
    prop_price                       int                                             ,  /**������Ǯ*/
    prop_num                         int                                             ,	/**�������������ܳ������Ƹ���*/ 
    create_time                      datetime                                        ,  /**����ʱ��*/    
    primary key(pg_pk)) ENGINE=innodb;
 create index Index1 on u_propgroup_info (p_pk,prop_id);
 create index Index2 on u_propgroup_info (p_pk,prop_type);
    
  /*************************��������Ǽ�¼_��ǵ����ã�u_coordinate_info��********************************/
  create table u_coordinate_info(
  c_pk	                         smallint unsigned not null auto_increment       , /**id*/
  p_pk                           int                                             , /**��ɫid*/
  coordinate_prop_id             int                                             , /**�������id*/
  coordinate                     int                                             , /**�������*/
  prop_isUse                    int                                     default 0, /**��ǵ����Ƿ�ʹ��*/
  primary key(c_pk));
create index Index1 on u_coordinate_info (p_pk,coordinate_prop_id);
  
   
   
  /*******************************���֪ͨ��u_groupnotify_info��************************************************************/
  create table u_groupnotify_info(
  n_pk                                 int not null auto_increment        , /**id*/ 
  notifyed_pk                          int                                              , /**��֪ͨ�����id*/
  create_notify_pk                     int                                              , /**����֪ͨ�����id*/ 
  notify_content                       varchar(200)                                     , /**֪ͨ������**/
  notify_type                          int                                              , /**֪ͨ���ͣ�1��֪ͨ����������ӣ�2��֪ͨ�����ɢ��3.֪ͨ�Է�ͬ����ӣ�4��������***/
  create_time                          datetime                                         , /**����ʱ��*/
  notify_flag                          int                                     default 0, /**֪ͨ��ʶ��0��ʾû��֪ͨ��1��ʾ��֪ͨ*/
  primary key(n_pk));
  create index Index1 on u_groupnotify_info(notifyed_pk,n_pk);
    
    
    /**********��ɫ���ܱ�(u_skill_info)***************/
   create table u_skill_info ( 
   s_pk             int not null auto_increment ,	 /**id*/ 
   p_pk             int                                       ,	 /**��ɫid*/
   sk_id         	int                                       ,	 /**����id*/ 
   sk_name			varchar(20)				    			  ,	 /**��������*/ 
   sk_sleight       int                              default 0,  /**���������ȣ���ʹ�ô���*/
   sk_usetime		datetime								  ,  /**��������һ�ε�ʹ�õ�ʱ��*/
   create_time      datetime                                  ,  /**����ʱ��*/
   
   sk_type               int                                       , /**��������*/        
   sk_gj_multiple        double                                    , /** ���������ӳ�*/  
   sk_fy_multiple        double                                    , /**�������ӳ�*/      
   sk_hp_multiple        double                                    , /**HP�ӳ�*/          
   sk_mp_multiple        double                                    , /**MP�ӳ�*/          
   sk_bj_multiple         double                                    , /**�����ʼӳ�*/      
   sk_gj_add             int                                       , /**������������*/  
   sk_fy_add             int                                       , /**���ӷ�����*/      
   sk_hp_add             int                                       , /**����HP*/          
   sk_mp_add             int                                       , /**����MP*/    
   sk_group              int                                        ,  /**������*/
   primary key (s_pk)) ENGINE=innodb; 
create index Index1 on u_skill_info (p_pk,sk_id,sk_type);
create index Index2 on u_skill_info (p_pk,sk_group);
   
   
     /***********************************************buffʹ��Ч��(u_buffeffect_info)***********************************/
  create table u_buffeffect_info  (
  bf_pk                       int not null auto_increment                  , /**id*/ 
  buff_id                     int                                                        , /**buff_id*/
  buff_name                   varchar(20)                                                , /**����*/	
  buff_display                varchar(80)                                                , /**buff����*/
  buff_type                   int                                                        , /**buff����*/  
  buff_effect_value           int                                                        , /**buffЧ��ֵ��*/ 
  
  spare_bout                  int                                                        , /**ʣ�µ�ʹ�ûغ���*/
  buff_bout                   int                                              default 0 , /**�����غ�*/
  buff_time                   int                                              default 0 , /**buff����ʱ�䣬��λΪ����*/
  use_time                    datetime                                                   , /**ʹ��buff��ʱ��*/
  
  buff_use_mode               int                                                        , /**ʹ�÷�ʽ��1��ʾ���棬2��ʾ����*/
  buff_bout_overlap           int                                              default 0 , /**�Ƿ�غϵ���,0��ʾ���ܣ�1��ʾ��*/
  buff_time_overlap           int                                              default 0 , /**�Ƿ�ʱ�����,0��ʾ���ܣ�1��ʾ��*/
  
  
  effect_object               int                                                        , /**buffЧ�����ö���*/
  effect_object_type          int                                                        , /**buffЧ�����ö���,11��ʾ��ң�12��ʾnpc*/
  primary key(bf_pk));
  create index Index1 on u_buffeffect_info(buff_bout,effect_object);
  create index Index2 on u_buffeffect_info(effect_object,effect_object_type,buff_type);
  
    /*************************���Ʊ���Ҫʱ���ʹ�ô������ƵĶ��󣩣�u_time_control��********************************************/
    create table u_time_control(
    id	                         int not null auto_increment       ,   /**id*/ 
    p_pk                         int                                             ,   /**��ɫid*/
    object_id                    int                                             ,	 /**��Ҫ���ƵĶ���*/
    object_type                  int                                             ,   /**��Ҫ���ƵĶ�������ͣ��磺���ߣ��˵���*/
    use_datetime                 datetime                                        ,   /**��¼���һ�ε�ʹ��ʱ��*/
    use_times                    int                                             ,   /**��¼�����ʹ�ô���*/
    primary key(id));
create index Index1 on u_time_control (p_pk);
     
/**********����(u_task)***************/
 create table u_task ( 
   t_pk                   int not null auto_increment ,/**����id*/
   p_pk                   int                                       ,/**��ɫid*/
   p_name                 varchar(20)                               ,/**��ɫ����*/
   t_zu                   varchar(50)                               ,/**������*/
   t_px                   varchar(20)                               ,/**��������*/
   t_id                   int                                       ,/**�����ID*/
   t_title                varchar(50)                               ,/**����ı���*/ 
   t_type                 int                                       ,/**��������*/
   t_xrwnpc_id            int                                       ,/**��һ������ʼnpc��id*/
   t_next	              int                                       ,/**��һ������id*/
   
   t_point                varchar(200)                              ,/**�м��	���ж���м��Ӧ�ã�����*/	 
   t_point_no             varchar(200)                              ,/**�Ƿ�����м���ID�ã�����*/ 
   t_zjdwp                varchar(500)                              ,/**ͨ���м����Ҫ����Ʒ	*/
   t_zjdwp_number         int                                       ,/**ͨ���м����Ҫ��Ʒ������*/
   t_zjdwp_ok	          int                              default 0,/**�����Ʒ����*/ 
   t_zjdzb                varchar(500)                              ,/**ͨ���м����Ҫ��װ��	*/
   t_zjdzb_number         int                                       ,/**ͨ���м����Ҫװ��������*/
   t_zjdzb_ok	          int                              default 0,/**�����Ʒ����*/ 
   t_djscwp               int                                       ,/**ͨ���м���Ƿ�ɾ����Ʒ*/
   t_djsczb               int                                       ,/**ͨ���м���Ƿ�ɾ��װ��*/
   t_midst_gs             varchar(500)                              , /**ͨ���м�������Ʒ*/
   t_midst_zb             varchar(500)                              , /**ͨ���м�����װ��*/
   
   t_goods	              varchar(500)                              ,/**���������Ҫ��Ʒ*/
   t_goods_no	          varchar(500)                              ,/**���������Ҫ��Ʒ����*/
   t_goods_ok	          varchar(500)                              ,/**�����Ʒ����*/ 
   t_goodszb              varchar(500)                              ,/**���������Ҫװ��*/ 
   t_goodszb_number       varchar(500)                              ,/**���������Ҫװ������*/
   t_goodszb_ok	          varchar(500)                              ,/**���װ������*/
   t_killing	          varchar(500)                              ,/**���������Ҫ��ɱ¾*/
   t_killing_no           int                                       ,/**���������Ҫ��ɱ¾����*/
   t_killing_ok           int                              default 0,/**���ɱ¾����*/
   t_pet                  int                                       ,/**���������Ҫ����*/
   t_pet_number           int                                       ,/**���������Ҫ��������*/
   t_pet_ok               int                              default 0,/**��ɳ�������*/
   create_time            datetime                                  ,/**������ȡʱ��*/
   t_time                 int                                       ,/**����ʱ�䣨���ӣ�*/
   t_give_up              int                              default 0,/**�Ƿ�Ϊ�������� 0û�з��� 1����*/
   up_task_id             int                                       ,/**��һ�������ID*/
   primary key (t_pk)) ENGINE=innodb;
   create index Index1 on u_task (t_pk,p_pk,t_id);
   create index Index2 on u_task (p_pk,t_zu);
   
/**********����Ѿ���ɹ�������(u_task_complete)***************/
create table u_task_complete(
  	c_pk	               int not null auto_increment , /**����Ѿ���ɹ�������id*/ 
    p_pk                   int                                       ,/**��ɫid*/
    task_zu                varchar(50)                               ,/**�Ѿ���ɵ�������*/
    primary key(c_pk));
    create index Index1 on u_task_complete (p_pk,task_zu);
    
     
/**********���(u_tong)***************/
create table u_tong(
  	t_pk	               int not null auto_increment , /**���id*/ 
    t_name                 varchar(20)                               , /**�������*/
    t_grade                int                                       , /**���ȼ�*/
    t_member_numb          int                                       , /**������������*/
    t_nonce_numb           int                                       , /**���ɵ�ǰ����*/
    t_camp                 varchar(50)                              , /**������Ӫ*/
    t_league               varchar(500)                             , /**����ͬ��ֻ�ܼ�3��ͬ�� ͬ��ID ��,�ָ�*/
    t_foe                  varchar(500)                             , /**���ɵ���ֻ�ܼ�5������ ����ID ��,�ָ�*/
    t_alter_time           int                                       , /**�ϴθı���Ӫʱ��һ����30����ܸı���һ�� ��ʽΪ 20080808*/
    create_time            datetime                                  , /**������ʱ��*/
    primary key(t_pk));
    create index Index1 on u_tong (t_pk,t_camp);
    
/**********���ս��(u_tong_battle)***************/
create table u_tong_battle(
  	b_pk	               int not null auto_increment , /**���id*/ 
    t_apply_pk             int                                       , /**��������ID*/
    t_accept_pk            int                                       , /**���ܰ���*/
    t_begin_time           datetime                                  , /**��ս��ʼʱ��*/
    t_end_time             datetime                                  , /**��ս����ʱ��*/
    primary key(b_pk));
    create index Index1 on u_tong_battle (t_apply_pk,t_accept_pk);

/**********����ϵ(u_tong_relation)***************/
create table u_tong_relation(
  	r_pk	               int not null auto_increment , /**����ϵid*/ 
  	t_pk                   int                                       , /**���ID*/
  	r_tong_pk              int                                       , /**��ϵ���ID*/
    r_name                 varchar(20)                              , /**��ϵ�������*/
    r_relation             int                                       , /**��ϵ��� 1 ͬ�� 2 �ж�*/
    primary key(r_pk));
    create index Index1 on u_tong_relation (t_pk,r_tong_pk);

/**********����Ա(u_tong_member)***************/
create table u_tong_member(
    tm_pk	               int not null auto_increment , /**����Աid*/ 
  	t_pk 	               int                                       , /**���id*/ 
  	p_pk                   int                                       , /**��ԱID*/ 
  	p_name                 varchar(20)                              , /**��Ա����*/ 
    tm_rights              int                                       , /**����ְλ 1 ���� 2 ������ 3 ���� 4���� 5��ͨ��Ա*/
    tm_title               varchar(20)                              , /**��Ա��ν*/ 
    tm_proffer             int                                       , /**���ɹ���ֵ*/ 
    battle_numb            int                            default 0 , /**��ս��ɱ������*/ 
    join_time              datetime                                  , /**�������ʱ��*/ 
    tong_rights            varchar(50)                              , /**���Ȩ��*/
    primary key(tm_pk));
    create index Index1 on u_tong_member (t_pk,p_pk,tm_rights);

/**********����Ա����(u_tong_glory)***************/
create table u_tong_glory(
    g_pk	               int not null auto_increment , /**����Ա����id*/ 
  	t_pk 	               int                                       , /**���id*/
  	p_pk                   int                                       , /**��ԱID*/ 
  	p_name                 varchar(20)                              , /**��Ա����*/ 
  	kill_number            int                              default 0, /**����ɱ���˵�����*/
    intraday_value         int                              default 0, /**��������ֵ*/
    glory_value            int                                       , /**������ֵ*/
    primary key(g_pk)) ENGINE=innodb;
    create index Index1 on u_tong_glory (t_pk,p_pk,glory_value);
    
/**********����Աɱ�˼�¼ֻ��¼�����(u_tong_kill)***************/
create table u_tong_kill(
    k_pk	               int not null auto_increment , /**����Ա����id*/ 
  	t_pk 	               int                                       , /**���id*/ 
  	p_pk                   int                                       , /**��ԱID*/
  	by_pPk                 int                                       , /**��ɱ��ԱID*/ 
  	kill_number            int                              default 0, /**��ɱ��Ա����*/
    primary key(k_pk));
    create index Index1 on u_tong_kill (t_pk,p_pk,by_pPk);
    
/**********���Ȩ��(u_tong_rights)***************/
create table u_tong_rights(
    tr_pk	               int not null auto_increment , /**���Ȩ��id*/
    t_type	               int                                       , /**Ȩ�޷��� 1���� 2���� 3���� 4���� */  
  	tr_name                varchar(20)                              , /**Ȩ������*/ 
    tr_rights              varchar(100)                              , /**��ԱȨ�� 1 ���� 2 ������ 3 ���� 4���� 5��ͨ��Ա ���ɫ��,�ָ�*/ 
    url_join               varchar(100)                              , /**url����*/ 
    primary key(tr_pk));
    create index Index1 on u_tong_rights (t_type);
  
/**********���������(u_tong_beg)***************/
 create table u_tong_beg(
    tb_pk	               int not null auto_increment , /**���������id*/
    t_pk	               int                                       , /**���id*/ 
    t_name                 varchar(20)                              , /**�������*/
    p_pk                   int                                       , /**��ԱID*/ 
    p_name                 varchar(20)                              , /**��Ա����*/
    primary key(tb_pk));
    create index Index1 on u_tong_beg (t_pk,p_pk);
  
/**********��ṫ��(u_tong_affiche)***************/
 create table u_tong_affiche(
    ta_pk	               int not null auto_increment , /**��ṫ��id*/
    t_pk	               int                                       , /**���id*/ 
    ta_title               varchar(1000)                             , /**��ṫ������*/
    create_time            datetime                                  , /**��ṫ�淢��ʱ��*/ 
    primary key(ta_pk));  
    create index Index1 on u_tong_affiche (t_pk);
  
/**********ת�ð��(u_tong_attorn)***************/
 create table u_tong_attorn(
    ta_pk	               int not null auto_increment , /**��ṫ��id*/
    t_pk	               int                                       , /**���id*/
    p_pk_big               int                                       , /**��ת�ð�����ID*/
    create_time            datetime                                  , /**ת��ʱ��*/
    at_term                datetime                                  , /**����ʱ��*/ 
    primary key(ta_pk));  
    create index Index1 on u_tong_attorn (t_pk,p_pk_big,create_time,at_term);
  
/**********��ɢ���(u_tong_disband)***************/
 create table u_tong_disband(
    td_pk	               int not null auto_increment , /**��ɢ���id*/ 
    t_pk	               int                                       , /**���id*/
    create_time            datetime                                  , /**��ɢʱ��*/
    at_term                datetime                                  , /**����ʱ��*/ 
    primary key(td_pk));    
    create index Index1 on u_tong_disband (t_pk,create_time,at_term);
  
/**********������ļ(u_tong_recruit)***************/
 create table u_tong_recruit(
    re_pk	               int not null auto_increment , /**������ļid*/ 
    t_pk	               int                                       , /**���id*/
    re_title               varchar(500)                              , /**�������*/
    re_money               int                                       , /**����۸�*/
    create_time            datetime                                  , /**����ʱ��*/
    re_term                datetime                                  , /**����ʱ��*/ 
    primary key(re_pk));   
    create index Index1 on u_tong_recruit (t_pk,re_money);
     
 
/*********������(u_auction)******************************/
  create table u_auction(
  	auction_id			int not null auto_increment , /**����id*/
  	u_pk				int 									,	/** ����id */
  	p_pk				int 									,	/** ��ɫid */
  	auction_type		int 									,	/** ��Ʒ���ͣ� 1ΪҩƷ��2Ϊ���3Ϊ���ϣ�4Ϊ�̳���5Ϊ������6Ϊ���ߣ�7Ϊ���Σ�8Ϊ���� */
  	goods_id			int 									,	/** ��Ʒid */
  	goods_name			varchar(20)						    	,	/** ��Ʒ���� */
  	goods_number 		int										,	/** ��Ʒ���� */
  	auction_price		varchar(50)								,	/** �����۸� */
  	buy_price			varchar(50)								,	/** ���Ը����ļ۸� */
  	auction_time		datetime								,	/** ������ʼʱ�� */
  	auction_failed		int										,	/** �Ƿ����ģ�1Ϊ����������2Ϊ������,3Ϊû�� */
  	auction_sell		int										,	/** �Ƿ������� ��1Ϊδ������2Ϊ������ */
  	buy_name 			varchar(20)  							,	/** ���������� */
  	
  	prop_use_control int										,	/** �����Ƿ����,0��ʾ��������,1��ʾս��ʱ������. */
  	table_type 		 int										,	/** ��Ʒ������ */
    goods_type       int                                       ,   /** ��Ʒ����*/
    w_durability     int                                       ,   /**�;�*/ 
    w_dura_consume   int                                       ,   /**�;�����*/
    w_Bonding        int                                       ,   /**��*/ 
    w_protect        int                                       ,   /**����*/ 
    w_isReconfirm    int                                       ,   /**����ȷ��*/ 
    w_price          int                                       ,   /**������Ǯ*/
    w_fy_xiao        int                               default 0,  /**����������С����*/
    w_fy_da          int                               default 0,  /**��������������*/ 
    w_gj_xiao        int                               default 0,  /**����������С����*/
    w_gj_da          int                               default 0,  /**����������󹥻�*/ 
    w_hp             int                               default 0,  /**����������Ѫ*/
   w_mp             int                               default 0,  /**�������Է���*/
   w_jin_fy         int                               default 0,  /**�������Խ������*/
   w_mu_fy          int                               default 0,  /**��������ľ������*/
   w_shui_fy        int                               default 0,  /**��������ˮ������*/
   w_huo_fy         int                               default 0,  /**�������Ի������*/
   w_tu_fy          int                               default 0,  /**����������������*/
   w_jin_gj         int                               default 0,  /**�������Խ𹥻���*/
   w_mu_gj          int                               default 0,  /**��������ľ������*/
   w_shui_gj        int                               default 0,  /**��������ˮ������*/
   w_huo_gj         int                               default 0,  /**�������Ի𹥻���*/
   w_tu_gj          int                               default 0,  /**����������������*/ 
   w_quality        int                                        ,  /** Ʒ��*/
   w_wx_type        int                                        ,  /**װ����������*/
   suit_id          int          					  default 0,  /**��Ӧ��װid,������װĬ��Ϊ0*/
   w_buff_isEffected  int                             default 0,  /**����buff�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч*/
    enchant_type		varchar(20)						  default 0,  /** �㻯����. */
   enchant_value	int								  default 0,  /**  �㻯����ֵ **/		
   w_zj_hp		    varchar(10)                             ,  /* ׷����Ѫ */
   w_zj_mp			varchar(10)                             ,  /* ׷������ */	
   w_zj_wxgj		    varchar(10)                             ,  /* ׷�����й��� */
   w_zj_wxfy			varchar(10)                             ,  /* ׷�����з��� */ 		
   w_zb_grade		int										,		/** װ���ȼ� */
   w_Bonding_Num      int                             default 0,   /**����󶨴�����*/
   specialcontent     int                             default 0,   /**����������*/					
  	primary key(auction_id));
  create index Index1 on u_auction (auction_type);
    create index Index2 on u_auction (auction_failed,auction_sell);
  
  /*********������Ϣ��(u_auction_info)******************************/
  create table u_auction_info(
  	auction_info_id			int not null auto_increment , /**������Ϣid */
  	p_pk					int 									 ,	/** ���˽�ɫid */
  	auction_info			varchar(200)							 ,	/** ������Ϣ��ʾ */
  	addInfoTime				datetime 								,	/** ������Ϣʱ�� */
  	primary key(auction_info_id));
	 create index Index1 on u_auction_info (p_pk);

/**********��Һ���(u_friend)***************/
 create table u_friend(
    f_pk	               int not null auto_increment , /**��Һ���id*/ 
    p_pk	               int                                       , /**���id*/
    fd_pk                  int                                       , /**����ID*/
    fd_name                varchar(20)                              , /**��������*/
    fd_online              int                              default 0, /**�����Ƿ����� 0 ������ 1����*/
    create_time            datetime                                  , /**����ʱ��*/ 
    relation               int                              default 0,/**��ϵ��0��ͨ���ѣ�1���壬2���*/
    dear                   int                              default 0,/**���ܶ�*/
    exp_share              int                              default 0,/**�������*/
    love_dear              int                              default 0,/**�������۶�*/
    tim                    varchar(20)                      default null,/**״̬�޸�ʱ��*/
    
    primary key(f_pk));
    create index Index1 on u_friend (p_pk,fd_pk);
    
/**********������(u_blacklist)***************/
 create table u_blacklist(
    b_pk	               int not null auto_increment , /**��Һ���id*/ 
    p_pk	               int                                       , /**���id*/
    bl_pk                  int                                       , /**����ID*/
    b_name                 varchar(20)                              , /**��������*/
    create_time            datetime                                  , /**����ʱ��*/ 
    primary key(b_pk));
    create index Index1 on u_blacklist (p_pk,bl_pk);

    
/**********ϵͳ��Ϣ��***********************/
create table s_system_info(
	sysInfo_id				int  not null auto_increment , /*** ϵͳ��Ϣ��id***/
	p_pk 					int 								,	/*** ���˽�ɫid***/
	info_type				int 								,	/*** ��Ϣ����( 1Ϊ�û���Ϣ��2Ϊ�ر�֪ͨ��3Ϊϵͳ���� ) ***/
	system_info				varchar(300)						,	/*** ϵͳ��Ϣ���� ***/
	happen_time 			datetime							,	/*** ����ʱ�� ***/
	primary key(sysInfo_id));
	create index Index1 on s_system_info (p_pk,info_type);
	create index Index2 on s_system_info (info_type,happen_time);
	
/**********�ʼ���(u_mail_info)***************/
create table u_mail_info ( 
	mail_id 		int not null auto_increment 		,	/*** �ʼ���id **/	
	receive_pk 		int												,	/**  ������id **/
	send_pk			int 											,	/**	 ������id **/
	mail_type		int												,	/**  �ʼ����ͣ�1Ϊ��ͨ��2Ϊϵͳ**/
	title 				varchar(100)									, 	/**  ����  **/
	content 			text(1000)									,	/**  �ʼ�����  **/
	unread				int												,	/**	 �Ƿ��Ķ�����1Ϊδ����2δ�Ѷ� **/
	improtant			int												,	/**  ��Ҫ�ԣ�����ҿ�ʱ���Դ�����������Ĭ��Ϊ1������Խ��Խ��ǰ  **/		
	create_time			datetime										,	/**	 �ʼ�����ʱ�䣬����Ҫ������һ�µ�������Դ�����  ***/
	primary key (mail_id));
	create index Index1 on u_mail_info (receive_pk);
	
	
	/**********������������(u_auction_pet)***************/ 
create table u_auction_pet(
	auction_id		 int not null auto_increment ,/** ����������id */
	auction_status	 int 										,/** ��������״̬��1Ϊ����������2Ϊ����ʧ��,�ȴ�ȡ�أ�3Ϊ���δȡ��,�ȴ�ɾ����4Ϊ�����ɹ����ȴ����ȡ�ؽ�Ǯ. */
	pet_price		 int 										,/** ���������۸�  */
	pet_auction_time datetime									,/** ��������ʱ�� */	
	pet_pk			 int									   , /** ��ɫ����ID */
	
	p_pk			 int 					                   , /**��ɫid*/
	pet_id			 int					                   , /**��Ӧpet�����id*/
	pet_name		 varchar(20)				               , /**��������*/
	pet_nickname	 varchar(20)				               , /**�����ǳ�*/
	pet_grade		 int					                   , /**�ȼ�*/
	
	pet_exp			 varchar(15)			                   , /**����*/
	pet_ben_exp		 varchar(15)			          default 0, /**��ǰ���龭��*/
	pet_xia_exp		 varchar(15)                               , /**�¼�����ﵽ��һ����Ҫ�ľ���*/
	pet_gj_xiao      int                     		           , /**��С����*/
	pet_gj_da        int					                   , /**��󹥻�*/
	
	pet_sale		 int			                           , /**�����۸�*/
	pet_img			 varchar(100)                              , /**����ͼƬ*/
	pet_grow	     double                                    , /**����ɳ��ʡ�*/
	pet_wx           int                  			           , /**�������Խ�=1��ľ=2��ˮ=3����=4����=5 */
    pet_wx_value     int           				               , /**��������ֵ*/
    
    pet_skill_one	 int                                       , /**����1	��ѧϰ�ļ���id*/
    pet_skill_two	 int                          	           , /**����2	��ѧϰ�ļ���id*/
    pet_skill_three	 int                        	           , /**����3	��ѧϰ�ļ���id*/
    pet_skill_four	 int                          		       , /**����4	��ѧϰ�ļ���id*/
    pet_skill_five	 int                                  	   , /**����5	��ѧϰ�ļ���id*/
    
    pet_life		 int			                           , /**����**/
    pet_isAutoGrow	 int									   , /** �Ƿ����Ȼ���� **/
    pet_type         int                                       , /**����	�Ƿ����Ȼ����*/	
    pet_isBring		 int					                   , /**�Ƿ�������:1��ʾ��ս��״̬��0��ʾ��*/
    pet_fatigue		 int			 	                       , /**ƣ�Ͷ�0-100,��ս״̬������ƣ�Ͷȣ�һ��Сʱ��10��*/
    pet_longe		 int			 	                       , /**��������*/
    
    longe_number     int			 	                       , /**��������ʹ�ô���*/
    longe_number_ok  int			 	                       , /**���������Ѿ�ʹ�ô���*/
    skill_control    int			 	                       , /**�������������ѧϰ���ٸ�����*/
    pet_init_num	 int									   , /** �����ʼ������ **/
    
        pet_violence_drop  double									,	/** ���ﱩ���� */
 primary key (auction_id));
 create index Index1 on u_auction_pet (auction_status);
 
   /*********����������Ϣ��(u_auctionpet_info)******************************/
  create table u_auctionpet_info(
  	auctionpet_info_id			int not null auto_increment , /**����������Ϣid */
  	p_pk						int 									 ,	/** ���˽�ɫid */
  	auction_pet_info			varchar(200)							 ,	/** ����������Ϣ��ʾ */
  	addInfoTime					datetime 								,	/** ������Ϣʱ�� */
  	primary key(auctionpet_info_id));
  	 create index Index1 on u_auctionpet_info (p_pk);
  	
  	/*********ϵͳ���ñ�(s_setting_info)******************************/
  	create table s_setting_info (
  		setting_id			int not null auto_increment,	/** ϵͳ���ñ� */
  		p_pk				int										,	/** �����ɫid */
  		goods_pic			tinyint									default -1,	/**  ��Ʒͼ����,1Ϊ������-1Ϊ�ر� */
  		person_pic			tinyint									default -1,	/**	 ��ɫ����ͼ����,1Ϊ������-1Ϊ�ر� */
  		npc_pic				tinyint									default -1,	/**  npc����ͼ����,1Ϊ������-1Ϊ�ر� */
  		pet_pic				tinyint									default -1,	/**  ����ͼ����,1Ϊ������-1Ϊ�ر� */
  		operate_pic			tinyint									default -1,	/**  npc����ͼ����,1Ϊ������-1Ϊ�ر� */
  		deal_control		tinyint									default 1,	/**  ���׿��ƿ���,1Ϊ������-1Ϊ�ر� */
  		
  		public_comm			tinyint									default 1,	/**  �������쿪��, 1Ϊ����, -1Ϊ�ر� **/
  		camp_comm			tinyint									default 1,	/**  ��Ӫ���쿪��, 1Ϊ����, -1Ϊ�ر�  */
  		duiwu_comm			tinyint									default 1,	/**  �������쿪�أ�1Ϊ����,	-1Ϊ�ر� */	
  		tong_comm			tinyint									default 1,	/**  �������쿪��,1Ϊ������ -1Ϊ�ر�  */
  		secret_comm			tinyint									default 1,	/**  �������쿪�أ�1Ϊ����, -1Ϊ�ر�  **/
  		index_comm			tinyint									default -1,	/**  �������쿪�أ�1Ϊ����, -1Ϊ�ر�  **/
  		npc_hp_position     tinyint                                 default -1, /**  -1��ʾĬ�ϣ�1��ʾnpcѪ�����ϱ� */
  	primary key(setting_id));
  	 create index Index1 on s_setting_info (p_pk);

 /**********��ɫ�ֿ�װ����(u_warehouse_equip)***************/
create table u_warehouse_equip ( 
   w_pk		        int not null auto_increment ,	  /**��ɫװ���ֿ��*/
   p_pk             int                                       ,	  /**��ɫid*/ 
   table_type       int                                       ,   /**��Ʒ��ر�����*/
   goods_type       int                                       ,   /**��Ʒ����*/
   w_id             int                                       ,   /**��ƷID*/
   w_name           varchar(20)                               ,   /**��Ʒ����*/
   w_durability     int                                       ,   /**�;�*/ 
   w_dura_consume   int                                       ,   /**�;�����*/
   w_Bonding        int                                       ,   /**��*/ 
   w_protect        int                                       ,   /**����*/ 
   w_isReconfirm    int                                       ,   /**����ȷ��*/ 
   w_price          int                                       ,   /**������Ǯ*/
   w_fy_xiao        int                               default 0,  /**����������С����*/
   w_fy_da          int                               default 0,  /**��������������*/ 
   w_gj_xiao        int                               default 0,  /**����������С����*/
   w_gj_da          int                               default 0,  /**����������󹥻�*/ 
   w_hp             int                               default 0,  /**����������Ѫ*/
   w_mp             int                               default 0,  /**�������Է���*/
   w_jin_fy         int                               default 0,  /**�������Խ������*/
   w_mu_fy          int                               default 0,  /**��������ľ������*/
   w_shui_fy        int                               default 0,  /**��������ˮ������*/
   w_huo_fy         int                               default 0,  /**�������Ի������*/
   w_tu_fy          int                               default 0,  /**����������������*/
   w_jin_gj         int                               default 0,  /**�������Խ𹥻���*/
   w_mu_gj          int                               default 0,  /**��������ľ������*/
   w_shui_gj        int                               default 0,  /**��������ˮ������*/
   w_huo_gj         int                               default 0,  /**�������Ի𹥻���*/
   w_tu_gj          int                               default 0,  /**����������������*/ 
   w_type           int                               default 0,  /**�Ƿ�װ�� 0 û�� 1��װ����*/
   w_quality        int                                        ,  /**Ʒ��*/
   
   w_wx_type        int                                        ,  /** װ����������*/
   suit_id			int								  default 0,	/** ��װid */
   w_buff_isEffected  int                             default 0,  /**����buff�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч*/
   enchant_type		varchar(10)						  default 0,  /** �㻯����. */
   enchant_value	int								  default 0,  /**  �㻯����ֵ **/	
   w_zj_hp		    varchar(10)                             ,  /* ׷����Ѫ */
   w_zj_mp			varchar(10)                             ,  /* ׷������ */	
   w_zj_wxgj		    varchar(10)                             ,  /* ׷�����й��� */
   w_zj_wxfy			varchar(10)                             ,  /* ׷�����з��� */ 		
   w_zb_grade		int										,		/** װ���ȼ� */											
   create_time      datetime                                   ,  /** ����ʱ��*/ 
      p_poss             int                             default 0,  /**����ʱ��*/
   w_Bonding_Num      int                             default 0,   /**����󶨴�����*/
   specialcontent     int                             default 0,   /**����������*/
   primary key (w_pk)); 
  create index Index1 on u_warehouse_equip (p_pk);
   
					
  
  
  /****************�û�������ֱ� *******************************/
  create table u_quiz_info (
  	id					 int  not null auto_increment				,	    /**  �û�������ֱ�id */
	p_pk				 int												,		/**  ���˽�ɫid   */
  	integral			 int												,		/**  �û�����  **/	
  	conunite_win	 int													,		/**  ������ȷ���� **/
  	mouth			 varchar(10)											,		/**  �·�  */
  	conunite_day	int														,		/**   ÿ�´���ʮ���������  **/
  	last_time		datetime												,		/**  ������ʱ�� */
  	answer_flag    		int													,		/**	  �ش����ı�־	*/
  	primary key(id)); 
  	create index Index1 on u_quiz_info (p_pk);
  	
  	 /****************�û�����װҩƷʹ�ñ� *******************************/
    create table u_special_prop (
    	id			 		 int  not null auto_increment		,	    /**  �û�������߱�id */
    	p_pk				 int										,		/**  �û���ɫid   */
    	sp_type		 int										,			/** ҩƷ����,1Ϊ��װҩ  **/
    	pg_pk			 int											,		/**  ���˵��߱�id   */
  		prop_stock	 int												,		/**  ���ߴ���  */
  		create_time	datetime											,		/**   ����ʱ�� */
  primary key(id))  ENGINE=innodb;
  create index Index1 on u_special_prop (p_pk);
  
   	  
  /**********���������(u_second_pass)***************/
create table u_second_pass(
	pass_id  			  int  not null auto_increment ,    /** �������������*/
	u_pk			  int								 	   ,	/**  ��ɫid */
	second_pass		  varchar(20)							   ,	/**  ��ɫ��������  */	
	pass_first_time	  datetime								   ,	/**  ��ɫ�״��޸Ķ����������ʱ��   */						
	pass_second_time  datetime								   ,	/**  ��ɫ�ڶ����޸Ķ����������ʱ�� **/
	pass_third_time	  datetime								   ,	/**  ��ɫ�������޸Ķ����������ʱ��  */	
	pass_wrong_flag		mediumint									   ,	/**  �����������δ����־ 1Ϊ��ֹʹ�ö�������, 0Ϊ����ʹ��  */	
	pass_mail_send		tinyint								    	,		/**  �������������ʼ��Ƿ��Ѿ����͹� */			
	primary key(pass_id));
    create index Index1 on u_second_pass (u_pk);
    
  /****************************************************************************************************************/
  
  
  /***********������Ҽ�¼��*********************/
  create table p_record_login(
  id				int  not null auto_increment		,	    /**  ������Ҽ�¼��id */	
  u_pk				int											,		/**  ���id  */
  loginStatus		mediumint											,		/**  ��½״̬ */
  loginTime			datetime									,		/**  �������ʱ��  */	
  primary key(id));
  
 
 /***********������ҽ�ɫ��¼��*********************/
  create table user_record_login(
  id				int  not null auto_increment		,	    /**  ������Ҽ�¼��id */	
  p_pk				int											,		/**  ��ҽ�ɫid  */
  p_grade			int											,		/**  ��ҵȼ�   **/
  loginStatus		int											,		/**  ��½״̬ */
  loginTime			datetime									,		/**  �������ʱ��  */	
  primary key(id));
  create index Index1 on user_record_login (p_pk);
  	
 
  /*************** �������ʱ���� ***********************************/
  	create table user_online_time(
  		id			int  not null auto_increment		,	/**  �������ʱ����id **/
  		u_pk		int											,	/*** ���id ***/
  		p_pk		int											,	/**  ��ɫid  **/
  		onlinetime	int											,	/*** ����ʱ�䳤�� **/
  		recordTime		varchar(50)									,	/***  ����¼���� **/
  		createTime	   datetime									,	/**  ����ʱ�� **/	
  	 primary key(id));
  	 
  	 
  
  /***********������ҵȼ���*********************/
  create table user_login_grade(
  	id 				int  not null auto_increment		,	/** ������ҵȼ���id **/
  	grade1to9		int											,	/**  �ȼ�1��9������� **/
  	grade10to19		int											,	/**  �ȼ�10��19�������**/
  	grade20to29		int											,	/**  �ȼ�20��29�������**/
 	grade30to39		int											,	/**  �ȼ�30��39�������**/
    grade40to49		int											,	/**  �ȼ�40��49������� **/
  	grade50to59		int											,	/**  �ȼ�50��59������� **/
    grade60			int											,	/**  �ȼ�60������� **/
    record_date		varchar(50)									,	/**  ��¼���� **/	
    record_time		datetime									,	/**  ִ�ж�������ʱ��. **/
  primary key(id));			
  								
  								
   /***********��Ĭ��ҵȼ���*********************/
  create table user_silence_grade(
  	id 				int  not null auto_increment		,	/**  ��Ĭ��ҵȼ���id **/
  	grade1to9		int											,	/**  �ȼ�1��9������� **/
  	grade10to19		int											,	/**  �ȼ�10��19�������**/
  	grade20to29		int											,	/**  �ȼ�20��29�������**/
 	grade30to39		int											,	/**  �ȼ�30��39�������**/
    grade40to49		int											,	/**  �ȼ�40��49������� **/
  	grade50to59		int											,	/**  �ȼ�50��59������� **/
    grade60			int											,	/**  �ȼ�60������� **/
    record_date		varchar(50)									,	/**  ��¼���� **/	
    record_time		datetime									,	/**  ִ�ж�������ʱ��. **/
  primary key(id));			
  
  
  /*************** �ճ��ȼ��� ***********************************/
  	create table user_everyday_grade(
  		id				int  not null auto_increment		,	/** �ճ��ȼ���id **/
  		grade1			int											,	/**  �ȼ�Ϊ1�ĵȼ����� */	
  		grade2to9		int											,	/**  �ȼ�2��9������� **/
  		grade10to19		int											,	/**  �ȼ�10��19�������**/
  		grade20to29		int											,	/**  �ȼ�20��29�������**/
 		grade30to39		int											,	/**  �ȼ�30��39�������**/
   		grade40to49		int											,	/**  �ȼ�40��49������� **/
  		grade50to59		int											,	/**  �ȼ�50��59������� **/
    	grade60			int											,	/**  �ȼ�60������� **/
    	avg_grade       int												,/**�������ƽ���ȼ�*/
  		recordTime		varchar(50)									,	/*** ����¼���� **/
  		createTime	datetime										,	/**  ����ʱ�� **/	
  	 primary key(id));	
  							
  		
  																																		
   /*************** �������ʱ��α� ***********************************/
	  CREATE TABLE user_online_num (
		  id int(11) NOT NULL AUTO_INCREMENT,/**����**/
		  hour_0  smallint(6)    DEFAULT 0,/**0����������ͳ��**/
		  hour_1  smallint(6)    DEFAULT 0,/**1��...**/
		  hour_2  smallint(6)    DEFAULT 0,/**2��...**/
		  hour_3  smallint(6)    DEFAULT 0,/**3��...**/
		  hour_4  smallint(6)    DEFAULT 0,/**4��...*/
		  hour_5  smallint(6)    DEFAULT 0,/**5��...**/
		  hour_6  smallint(6)    DEFAULT 0,/**6��...**/
		  hour_7  smallint(6)    DEFAULT 0,/**7��...*/
		  hour_8  smallint(6)    DEFAULT 0,/**8��...*/
		  hour_9  smallint(6)    DEFAULT 0,/**9��...*/
		  hour_10 smallint(6)    DEFAULT 0,/**10��...*/
		  hour_11 smallint(6)    DEFAULT 0,/**11��...*/
		  hour_12 smallint(6)    DEFAULT 0,/**12��...*/
		  hour_13 smallint(6)    DEFAULT 0,/**13��...*/
		  hour_14 smallint(6)    DEFAULT 0,/**14��...*/
		  hour_15 smallint(6)    DEFAULT 0,/**15��...*/
		  hour_16 smallint(6)    DEFAULT 0,/**16��...*/
		  hour_17 smallint(6)    DEFAULT 0,/**17��...*/
		  hour_18 smallint(6)    DEFAULT 0,/**18��...*/
		  hour_19 smallint(6)    DEFAULT 0,/**19��...*/
		  hour_20 smallint(6)    DEFAULT 0,/**20��...*/
		  hour_21 smallint(6)    DEFAULT 0,/**21��...*/
		  hour_22 smallint(6)    DEFAULT 0,/**22��...*/
		  hour_23 smallint(6)    DEFAULT 0,/**23��...*/
		  createTime varchar(10) DEFAULT NULL,
		  PRIMARY KEY (id));
  	
  	/*************** �����Ϣ������ ***********************************/
  	create table user_info_overview(
  		id						int  not null auto_increment		,	/** �ճ��ȼ���id **/
  		all_regist_num			int											,	/** ��ע������ **/	
  		all_regist_user			int											,	/**	��ע���ɫ  **/
  		today_regist_num		int											,	/** ����ע������ */
  		today_online_num		int											,	/** ������������ */
  		today_active_num		int											,	/** ���ջ�Ծ���� */
  		today_avg_time 			int											,	/** ����ƽ������ʱ�� */
  		today_avg_grade 		double											,	/** ����ƽ�����ߵȼ� */
  		today_avg_num			double											,	/** ����ƽ���������� */
  		today_grade_num         int                                         ,/**�����½�����Ƿ񳬹�ָ���ȼ�*/
  		record_date				varchar(50)								,	/** ��¼������  */
  		insert_time		datetime											,	/** ����ʱ�� */ 
  	 primary key(id));
  										
  /*************** �ճ��ȼ��� ***********************************/
  	create table user_grade_statistics (
  		id				int  not null auto_increment		,	/** �ճ��ȼ�ͳ�Ʊ�id **/
  		time_10min		int												,	/**  ��Ϸʱ����10�������ڵ������ **/
    	time_30min		int												,	/**  ��Ϸʱ����30�������ڵ������ **/
    	time_60min		int												,	/**  ��Ϸʱ����60�������ڵ������ **/
    	time_120min		int												,	/**  ��Ϸʱ����120�������ڵ������ **/
    	time_120minup		int											,	/**  ��Ϸʱ����120�������ϵ������ **/
    	recordTime		varchar(50)									,	/***  ����¼���� **/
  		createTime	datetime										,	/**   ����ʱ�� **/	
  	 primary key(id));															
  	 
  	 
  	 
  /**********************************PK������Ʒ��***********************************************/
  create table n_dropgoods_pk(
  dp_pk						 int NOT NULL AUTO_INCREMENT , /**ID */
  a_p_pk					 int					   				   , /**P���Ľ�ɫid*/
  b_p_pk					 int					   				   , /**ʤ���Ľ�ɫid*/
  drop_num					 int									   , /*��������*/
  goods_id					 int 									   , /*��Ʒid*/
  goods_name				 varchar(20)							   , /*��Ʒ����*/
  goods_type				 int									   , /*��Ʒ����*/
  goods_quality              int                              default 0, /**������Ʒ��Ʒ�� 0��ʾ��ͨ��1��ʾ���㣬2��ʾ����3��ʾ��Ʒ*/
  primary key (dp_pk));
    create index Index1 on n_dropgoods_pk (a_p_pk);
  
  /**********************************PK֪ͨ������Ʒ��***********************************************/
  create table pk_dropgoods_notify(
  pn_pk						 int NOT NULL AUTO_INCREMENT , /**ID */
  a_p_pk					 int					   				   , /**P���Ľ�ɫid*/
  b_p_pk					 int					   				   , /**ʤ���Ľ�ɫid*/
  drop_num					 int									   , /*��������*/
  goods_id					 int 									   , /*��Ʒid*/
  goods_name				 varchar(20)							   , /*��Ʒ����*/
  goods_type				 int									   , /*��Ʒ����*/
  goods_quality              int                              default 0, /**������Ʒ��Ʒ�� 0��ʾ��ͨ��1��ʾ���㣬2��ʾ����3��ʾ��Ʒ*/
  primary key (pn_pk));
  
  /**********************************ս��������������Ѫλ�����¿���u_hp_up***********************************************/
  create table u_hp_up(
  hu_pk						 int NOT NULL AUTO_INCREMENT , /**ID */
  p_pk					     int					   				   , /**P���Ľ�ɫid*/
  hp_up                      int                              default 0, /**Ĭ��0Ϊ��NPCѪ������ 1ΪNPCѪ������*/
  primary key (hu_pk));
  create index Index1 on u_hp_up (p_pk);
  
  
   /*********************************��ҷֽ�װ����***********************************************/
  create table equip_decompose_info(
  de_id						 int NOT NULL AUTO_INCREMENT , /**ID */
  p_pk					     int					   				   , /**P���Ľ�ɫid*/
  equip_id                      varchar(10)                      default 0, /** װ��id */
  equip_type                    varchar(10)                       default 0, /** װ������ */
  equip_name                    varchar(20)                       default 0, /** װ������ */
  equip_pose					int										  , /** װ��λ�� */		
  pw_pk							int										  ,	/** ����װ����id */
  deal_type						int								 		  , /** ��������,1Ϊ�ֽ�, 2Ϊ���� **/						
  create_time					datetime								  ,	/** װ������ʱ�� */	
  primary key (de_id)); 
  create index Index1 on equip_decompose_info (p_pk,deal_type);
  
  /**********************************��Ʊ��u_lottery_number***********************************************/
  create table u_lottery_number(
  lottery_id						 int NOT NULL AUTO_INCREMENT , /**��ƱID */
  p_pk					     int					   				   , /**���ID*/
  lottery_number             varchar(200)                              , /**��Ʊ����*/
  lottery_type				 int									   , /**��Ʊ����*/
  lottery_per_money			 int									   , /**��ƱͶע���*/
  primary key (lottery_id));
  
   /**********************************��Ʊ��u_lottery_info***********************************************/
  create table u_lottery_info(
  lottery_info_id					 int NOT NULL AUTO_INCREMENT , /**��Ʊ��ϢID */
  p_pk					     int					   				   , /**���ID*/
  lottery_num                int                                       , /**Ͷע����*/
  lottery_win_num			 int									   , /**Ӯȡ��Ʊ����*/
  lottery_catch_money		 int									   , /**�Ƿ��콱��*/
  lottery_per_bonus          int                                       , /**ÿע����Ľ��*/
  lottery_bonus_multiple     int                                       , /**������*/
  lottery_charity            int                                       , /**�Ƿ���д���Ͷע�ʸ�*/
  lottery_all_bonus          int                                       , /**��õ��ܽ���*/
  primary key (lottery_info_id)); 
  
  /**********************************������Ϣ��u_laborage***********************************************/
  create table u_laborage(
  laborage_id					 int NOT NULL AUTO_INCREMENT , /**��Ʊ��ϢID */
  p_pk					     int					   				   , /**���ID*/
  laborage_this_time         int                                       , /**��һʱ��ͳ��*/
  laborage_old_time			 int									   , /**���������ʱ���*/
  laborage_catch	    	 int									   , /**�Ƿ��칤��*/
  primary key (laborage_id));
   
  /**********************************��ɫ��½ǰ���¼��u_prelude***********************************************/
  create table u_prelude(
  pr_id					 int UNSIGNED NOT NULL AUTO_INCREMENT , /**��ɫ��½ǰ���¼��ID */
  p_pk				     int					   				   , /**���ID*/ 
  primary key (pr_id)); 
  create index Index1 on u_prelude (p_pk);
  
  /**********************************�����û���ƽ̨�û��Ĺ�����*************************************/
  create table u_passport_info(
  id					 int NOT NULL AUTO_INCREMENT               , /**ID */
  user_id			     varchar(36)					   UNIQUE  , /**�����û�id*/
  user_name              varchar(25)                       UNIQUE  , /**�����û�����*/
  user_state             int                              default 1, /**�����û���ƽ̨��״̬��1��ʾע���û���2��ʾע���û�*/
  channel_id             varchar(10)                               , /**����id*/
  u_pk                   int                                       , /**��Ϸ����˺�id*/
  create_time            datetime                                  , /**����ʱ��*/
  primary key (id)); 
  create index Index1 on u_passport_info(user_id,channel_id);
  
  
  
      /**********************************�������ڲ�����ͳ��u_information***********************************************/
  create table u_information(
  u_pk					     int					   				   , /**����˺�ID*/
  id                         varchar(30)                               , /**������ID*/
  type			             varchar(10)							    /**�������*/
  ); 
  
  /************��������ͳ�Ʊ�********************/
CREATE TABLE t_online (
  onlineid int(11) NOT NULL AUTO_INCREMENT,/**����**/
  onlinecount int(11) DEFAULT 0,/**��������**/
  PRIMARY KEY (onlineid)
);



/**********�������װ����ʱ��(u_special_item)***************/
create table u_special_item ( 
   sp_pk             int not null auto_increment ,	
   p_pk                      int                                      ,/**ppk*/ 
   prop_id                  int                                      ,/**��ƷID*/  
   prop_operate1      varchar(50)                     ,/**�����ֽ�1*/  
   prop_operate2      varchar(50)                     ,/**�����ֽ�2*/
   prop_operate3      varchar(50)                     ,/**�����ֽ�3*/
   prop_time              int                                      ,/**��ʱ��*/
   prop_date             datetime                            , /**ʹ��ʱ��*/
   prop_sign               int                                      ,/**װ�����*/
   primary key (sp_pk));
     create index Index1 on u_special_item(p_pk);
	
	/*** *�������ȼ�¼****/
	create table instance_archive(
	    p_pk                     int                                        ,		/** ��ɫid*/
	    map_id                   int                                        ,       /**��������map_id*/
	    dead_boss_record         varchar(200)                               ,       /**��¼�Ѵ�����boss���ڵ�sence_id,��ʽ�磺223,45,1132*/
	    create_time              datetime                                           /**���븱��ʱ��*/      
	);
    

   
/***************�������н�����(u_tong_build)*********************************/
 create table u_tong_build(
   utb_id               smallint unsigned not null auto_increment , /**����*/ 
   t_pk                 int                                       , /**����ID*/
   tb_id                int                                       , /**����id*/
   tb_name              varchar(50)                              , /**��������*/ 
   tb_grade             int                                       , /**�����ȼ�*/
   tb_definition        varchar(200)                              , /**����˵��*/
   tb_group             int                                       , /**����������*/
   tb_map               int                                       , /**�������ڵĵ�ͼ*/
   primary key (utb_id)); 
   create index Index1 on u_tong_build (t_pk,tb_id,tb_group,tb_map);
    
/***************������Դ��Ǯ(u_tong_money)*********************************/
 create table u_tong_money(
   utm_id               smallint unsigned not null auto_increment , /**����*/ 
   t_pk                 int                                       , /**����ID*/
   utm_money            varchar(20)                              , /**����Ǯ�ļ���*/
   utm_wrap_content		int                            default 100, /**������Դ����*/
   utm_wrap_spare		int                            default 100, /**������Դʣ������*/
   primary key (utm_id)); 
   create index Index1 on u_tong_money (t_pk,utm_wrap_spare);
 
/**********������Դװ����(u_tong_resource_equip)***************/
create table u_tong_resource_equip ( 
   re_pk            smallint unsigned not null auto_increment ,	  /**������Դװ����*/
   t_pk             int                                       ,   /**����ID*/
   table_type       int                                       ,   /**��Ʒ��ر�����*/
   goods_type       int                                       ,   /**��Ʒ����*/
   w_id             int                                       ,   /**��ƷID*/
   w_name           varchar(20)                               ,   /**��Ʒ����*/
   w_durability     int                                       ,   /**�;�*/ 
   w_dura_consume   int                                       ,   /**�;�����*/
   w_Bonding        int                                       ,   /**��*/
   w_protect        int                                       ,   /**����*/
   w_isReconfirm    int                                       ,   /**����ȷ��*/
   w_price          int                                       ,   /**������Ǯ*/
   w_fy_xiao        int                               default 0,  /**����������С����*/
   w_fy_da          int                               default 0,  /**��������������*/ 
   w_gj_xiao        int                               default 0,  /**����������С����*/
   w_gj_da          int                               default 0,  /**����������󹥻�*/ 
   w_hp             int                               default 0,  /**����������Ѫ*/
   w_mp             int                               default 0,  /**�������Է���*/
   w_jin_fy         int                               default 0,  /**�������Խ������*/
   w_mu_fy          int                               default 0,  /**��������ľ������*/
   w_shui_fy        int                               default 0,  /**��������ˮ������*/
   w_huo_fy         int                               default 0,  /**�������Ի������*/
   w_tu_fy          int                               default 0,  /**����������������*/
   w_jin_gj         int                               default 0,  /**�������Խ𹥻���*/
   w_mu_gj          int                               default 0,  /**��������ľ������*/
   w_shui_gj        int                               default 0,  /**��������ˮ������*/
   w_huo_gj         int                               default 0,  /**�������Ի𹥻���*/
   w_tu_gj          int                               default 0,  /**����������������*/ 
   w_type           int                               default 0,  /**�Ƿ�װ�� 0 û�� 1��װ����*/
   w_quality        int                                        ,  /**Ʒ��*/
   suit_id          int                               default 0,  /**��Ӧ��װid,������װĬ��Ϊ0*/
   w_wx_type        int                                        ,  /**װ����������*/
   w_buff_isEffected  int                             default 0,  /**����buff�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч*/
   enchant_type		  varchar(10)					  default 0,  /**���ӵ㻯����  */
   enchant_value	  int							  default 0,  /**���ӵ㻯����ֵ */
   w_zj_hp		      varchar(10)                             ,  /**׷����Ѫ */
   w_zj_mp			  varchar(10)                             ,  /**׷������ */	
   w_zj_wxgj		  varchar(10)                             ,  /**׷�ӹ��� */
   w_zj_wxfy	      varchar(10)                             ,  /**׷�ӷ��� */ 
   w_zb_grade		  int									   ,  /**װ���ĵȼ� */
   w_Bonding_Num      int                             default 0,   /**����󶨴�����*/	
   primary key (re_pk));
   create index Index1 on u_tong_resource_equip (t_pk,goods_type);
 
/*************************������Դ���߱�(u_tong_resource_prop),ÿ����¼����¼����һ�����********************************/
  create table u_tong_resource_prop(
  	rp_pk	                         int  not null auto_increment                    ,  /**id*/ 
  	t_pk                             int                                             ,  /**����ID*/
    pg_type                          int                                             ,	/**���������*/ 
    prop_id			                 int                                             ,	/**����id*/
    prop_type						 int                                             ,  /**��������*/
    prop_bonding                     int                                             ,
    prop_protect                     int                                             ,
    prop_isReconfirm                 int                                             ,											
    prop_use_control                 int                                    default 0,  /**���Ƶ����Ƿ���ã�0��ʾ�������ƣ�1��ʾս��ʱ������*/
    prop_name	                     varchar(20)                                     ,  /**��������*/
    prop_price                       int                                             ,  /**������Ǯ*/
    prop_num                         int                                             ,	/**�������������ܳ������Ƹ���*/
    auction_type                     int                                             ,  /**���ߴ洢����*/
    primary key(rp_pk));
    create index Index1 on u_tong_resource_prop (t_pk,pg_type,prop_id);
    
    
/*************************������Դ����¼��LOG (u_tong_res_log)********************************/
    create table u_tong_res_log(
  	l_pk	                         varchar(20)                                     ,  /**id*/ 
  	t_pk                             int                                             ,  /**����ID*/
    l_content                        varchar(300)                                    ,	/**��־����*/
    create_time                      datetime                                           /**��־��¼ʱ��*/
   ); 
    create index Index1 on u_tong_res_log (t_pk);
    
    
 /*************************����䷽��(u_synthesize_info)********************************/
  create table u_synthesize_info(
  us_id                int  not null auto_increment            ,/**����*/
  p_pk              int               ,/**���ppk*/
  s_id              int               ,/**�䷽ID*/
    primary key(us_id));
 
  /*************************ϵͳͳ�Ʊ�(game_statistics)********************************/
  create table game_statistics(
  gs_id                int  not null auto_increment            ,/**����*/
  prop_id              int               ,/**��ƷID*/
  prop_type            int               ,/** ��Ʒ���� */
  prop_num             int               ,/** ��Ʒ���� */
  prop_approach_type   varchar(20)       ,/** ��Ʒ���;������ */
  prop_approach        varchar(20)       ,/** �ж���Ʒ�����ĵõ����ǿ�� */
  date                 varchar(20)       ,/** ���� */
  time                 varchar(20)       ,/** ʱ�� */
    primary key(gs_id))   ENGINE=innodb;


 /*************************ϵͳ��Ҫͳ�Ƶ��߱�(game_statistics_prop)********************************/
  create table game_statistics_prop(
  gsp_id                int  not null auto_increment            ,/**����*/
  prop_id              int               ,/**��ƷID*/
  prop_type            int               ,/** ��Ʒ���� */
  date                 varchar(20)       ,/** ���� */
  time                 varchar(20)       ,/** ʱ�� */
    primary key(gsp_id));
    

    
/*************************��¼��ˢ��ʱ���npc���ϴε�����ʱ��(npc_dead_record)********************************/
create table npc_dead_record(
  id                   int          auto_increment            ,/**����*/
  p_pk                 int                                    ,/**���id*/
  npc_id               int                                    ,/**NPC��ID*/
  scene_id             int                                    ,/** ����ID */
  map_id               int                                    ,/**map��ID*/
  npc_deadtime         datetime                               ,/** ��һ��NPC����ʱ�� */
  primary key(id));
create index Index1 on npc_dead_record(p_pk,scene_id,npc_id);
  
/**********����(u_embargo)***************/
 create table u_embargo(
    e_pk	               int not null auto_increment               , /**����ID*/ 
    p_pk	               int                                       , /**���id*/
    p_name	               varchar(20)                               , /**�������*/
    begin_time             datetime                                  , /**���Կ�ʼʱ��*/
    end_time               datetime                                  , /**���Խ���ʱ��*/ 
    e_time                 varchar(100)                              , /**����ʱ��*/ 
    primary key(e_pk));
    create index Index1 on u_embargo (p_pk);
 
/**********����(u_soulbang)***************/
 create table u_soulbang(
    s_pk	               int not null auto_increment               , /**����ID*/ 
    p_pk	               int                                       , /**���id*/
    pw_pk	               int                                       , /**װ������ID*/
    pw_name                varchar(20)                               , /**��װ������*/ 
    s_type                 int                              default 1, /**��װ��״̬*/ 
    primary key(s_pk));
    create index Index1 on u_soulbang (p_pk,pw_pk);
    
/**********װ��������Ϣ��ʾ(u_equip_msg)***************/
 create table u_equip_msg(
    e_pk	               int not null auto_increment               , /**װ��������Ϣ��ʾID*/ 
    p_pk	               int                                       , /**���id*/
    msg_type               int                                       , /**��Ϣ����*/
    e_msg                  varchar(500)                              , /**��Ϣ����*/ 
    primary key(e_pk));
     create index Index1 on u_equip_msg (p_pk);

/**********����ʽ��Ϣ��(u_popup_msg)***************/
create table u_popup_msg(
    id                     int not null auto_increment               , /**ID*/ 
    p_pk                   int                                       , /**��ɫID*/
    msg_type               int                                       , /**��Ϣ����*/
    msg_operate1           varchar(500)                              , /**�����ֶ�*/
	msg_operate2           varchar(100)                              , /**�����ֶ�*/
	msg_priority           int                                       , /**���ȼ�*/
	create_time            datetime                                  , /**����ʱ��*/
primary key(id));
create index Index1 on u_popup_msg (p_pk,msg_priority,create_time);

/************�û���ֵ��¼***************************/
create table u_account_record(
	id                     int not null auto_increment               , /**ID*/ 
	u_pk                   int                                       , /**�û��˺�*/
	p_pk                   int                                       , /**��ֵʱ��½�Ľ�ɫ*/
	code                   varchar(50)                               , /**����*/
	pwd                    varchar(50)                               , /**����*/
	money                  int                                       , /**��ֵ��Ǯ��*/
	channel                varchar(50)                               , /**��ֵ����*/
	account_state          varchar(50)                               , /**��ֵ״̬*/
	account_time           datetime                                  , /**��ֵʱ��*/
	payment_time           datetime                                  , /**����ʱ��,���û�Ԫ������ʱ��*/
primary key(id));

/**********���(u_envelop)***************/
 create table u_envelop(
    e_pk	               int not null auto_increment               , /**���ID*/ 
    p_pk	               int                                       , /**���id*/
    p_name	               varchar(20)                               , /**�������*/
    begin_time             datetime                                  , /**��ſ�ʼʱ��*/
    end_time               datetime                                  , /**��Ž���ʱ��*/ 
    e_time                 varchar(100)                              , /**���ʱ��*/ 
    e_type               int                                         , /**�������*/ 
    e_state					int										 , /**���״̬*/
    e_num					int										 , /**��Ŵ���*/
    primary key(e_pk));
    create index Index1 on u_envelop (p_pk);
    
/**********��ҽ��׼�¼��(game_sellinfo_record)***************/
 create table game_sellinfo_record(
    s_pk	               int not null auto_increment               , /**��¼ID*/ 
    p_pk_give	               int                                       , /**������Ʒ���ID*/
    p_pk_have	               int                              , /**�õ���Ʒ���ID*/
    prop_type             int                                  , /**��Ʒ����*/
    prop_id               int                                  , /**��ƷID*/ 
    s_num                 int                              , /**��Ʒ����*/ 
    money               varchar(15)                                               , /**��Ǯ*/ 
    s_date               varchar(30)                       ,/**��¼ʱ��*/
    primary key(s_pk));
 
/**********��PK����(u_avoidpkprop)***************/
 create table u_avoidpkprop(
    a_pk	               int not null auto_increment               , /**��PK����ID*/ 
    p_pk	               int                                       , /**���id*/
    begin_time             datetime                                  , /**���߿�ʼʱ��*/
    end_time               datetime                                  , /**���߽���ʱ��*/  
    primary key(a_pk));
    create index Index1 on u_avoidpkprop (p_pk);

    
/***************��ɫ����ٶȹ���ʱ��¼��ʱ��ip********/
create table exception_user_log(
    id                      int not null auto_increment               , /**ID*/ 
    u_pk                    int                                       , /**�˻�id*/
    p_pk                    int                                       , /**��ɫid*/
    exception_ip            varchar(20)                               , /**�쳣ip*/
    time_space              int                                       , /**ʱ����*/
    log_time                datetime                                  , /**��¼ʱ��*/
primary key(id));
create index Index1 on exception_user_log (p_pk);

/***************�����û�********/
create table channel(
    id                      int not null auto_increment               , /**ID*/ 
    user_name            varchar(20)                                       , /**�û�id*/
    user_paw            varchar(20)                               , /**�û�����*/
    channel             varchar(20)                                        , /**�û�����*/
    log_time                datetime                                  , /**��¼ʱ��*/
primary key(id));

/***************����ID����*******/
create table channel_id_info(
    id                      int not null auto_increment               , /**ID*/ 
    channel_id                varchar(20)                                       , /**����id*/
    channel_id_sec            varchar(20)                               , /**������ip*/
primary key(id));

/***************��������*******/
create table channel_id_num(
    id                      int not null auto_increment               , /**ID*/ 
    channel_id_sec            varchar(20)                               , /**������ip*/
    sta_num            int                               , /**������*/
    sta_time         datetime                        ,/**��¼ʱ��**/  
primary key(id));


	/**********Ԫ����������(u_auction_yb)***************/ 
create table u_auction_yb (
	
	uyb_id 				int not null auto_increment 				, /** Ԫ��id **/
	p_pk					int													,		/** ����Ԫ���ߵ�p_pk */	
	uyb_state       int														,		/** Ԫ��������״̬,1Ϊ��������״̬��2Ϊ�Ѿ�����״̬��3Ϊδ�����¼�״̬   */
	yb_num			int													,		/** ����Ԫ������ */
	yb_price  			int													,		/** ϣ�������ļ۸� */
	auction_time	datetime											,		/** ����ʱ�� */
primary key(uyb_id));
 create index Index1 on u_auction_yb (uyb_state,auction_time);

	/**********Ԫ������������Ϣ��¼(u_auctionyb_auctionInfo)***************/ 
create table u_auctionyb_auctioninfo (
	yb_info_id 				int not null auto_increment 				, /** Ԫ��id **/
	p_pk					int													,		/** ����Ԫ���ߵ�p_pk */	
	buy_ppk          int														,		/**  �����ߵ� pPk   */
	jilu_string  		varchar(200)									,		/**  �����¼  */
	auction_time	datetime											,		/** ������ʱ�� */
primary key(yb_info_id));


/*********************�̳Ǽ�¼************************/
create table u_mall_log(
    id                  int not null auto_increment 				, /**id **/
    u_pk                int                                         , /**�˺�id*/
    role_name           varchar(20)                                , /**����ʱ�Ľ�ɫ����*/
    mall_log            varchar(100)                                , /**��־����*/
    create_time         datetime                                    , 
primary key(id));
 create index Index1 on u_mall_log (u_pk);

 
/*********************��Ϸ���棨game_notify��************************/
create table game_notify(
    id                  int not null auto_increment 				, /**id **/
    title               varchar(50)                                         , /**�������*/
    content           varchar(2000)                                       , /**��������*/
    ordernum       int                                                      , /**����*/
    isonline           int                                                  default 0   , /**�Ƿ�����*/
    create_time       datetime                                    , /**��������ʱ��*/
    type       int                                                       default 0 , /**���*/
primary key(id));

 
/*********************ϵͳ�������ű�sys_prize��************************/
create table sys_prize(
    id                  int not null auto_increment 				, /**id **/
    u_passport               varchar(50)                                         , /**����˺�*/
    prop_id          int                                                      , /**����ID*/
    prop_num       int                                                      , /**��������*/
    content          varchar(100)                                     ,/**˵��*/
primary key(id));

/*********************ϵͳ�������ű�game_prize��************************/
create table game_prize(
    id                  int not null auto_increment 				, /**id **/
    prize_type			varchar(100)									,/**��������**/
    prize_display       varchar(200)									,/**��������*/
    u_passprot			varchar(50)										,/**����˺�*/
    u_pk                   int                                    default 0                   , /**����˺�*/
    u_name				varchar(50)										,/**�������*/
    p_pk				int										default 0 		,/**ppk*/
    state                  int                                  default 0                   ,/**״̬*/
    prop				varchar(100)									,/**��������*/
    create_time         datetime                                          ,/**�޸�״̬��ʱ��*/
primary key(id));

/*********************ϵͳ�������ű�game_prize_info��************************/
create table game_prize_info(
    id                  int not null auto_increment 				, /**id **/
    u_pk                   int                                                      , /**upk*/
    p_pk                   int                                                      , /**ppk*/
    content          varchar(100)                                     ,/**˵��*/
    creat_time         datetime                                          ,/**�޸�״̬��ʱ��*/
primary key(id));
 
/*********************ͳ�������Ϣ��¼��game_player_statistics_info��*****************************************/
create table game_player_statistics_info(
	id					int not null auto_increment					,/**����*/
	u_pk				int											,/**u_pk*/
	p_pk				int											,/**p_pk*/
	p_grade				int											,/**�ȼ�*/
	p_onlinetime		int											,/**����ʱ�����*/
	p_date				varchar(50)									,/**����*/
	p_time				varchar(10)									,/**ʱ��*/
	p_login_time_old    datetime									,/**�ϴε�¼ʱ��*/
	p_login_time	    datetime									,/**���ε�¼ʱ��*/
primary key (id)) ENGINE=innodb;
create index Index1 on game_player_statistics_info (u_pk,p_pk);	
	
/*********************���ƽ����¼************************/
create table u_box_info(
    id                  int not null auto_increment 				, /**id **/
    p_pk                int                                         , /**��ɫid*/
    p_grade			int										,	/** ��ɫ�ȼ�  **/
    prop_id           int                               , /**�����ĵ���ID*/
    prop_name            varchar(20)                                , /**�����ĵ�������*/
    prop_quality		int													,/**  ����Ʒ�� **/
    create_time         datetime                                    ,  /** ����ʱ�� */
primary key(id));
	
	
/**************˼����ֵ��¼**************************/
create table u_sky_pay_record(
    id                  int not null auto_increment 				, /**id **/
    billid              varchar(50)                                 , /**billid **/ 
    skyid               varchar(50)                                , /**˼���û�id����Ӧu_login_info���user_id*/
    kbamt               varchar(15)                                , /**����۷ѽ��*/
    pay_time            datetime                                    , /**�������ʱ��*/
    skybillid1          varchar(50)                                , /**�ǳ�������ҵĶ�����ˮ��1*/
    skybillid2          varchar(50)                                , /**�ǳ�������ҵĶ�����ˮ��2*/
    balance             varchar(15)                                , /**���*/
    respones_result     varchar(200)                                , /**����Ľ������*/
    p_pk                int                                        , 
    repair_result		int										default 0 	,/***����KB�Ľ��**/
primary key(id));
	
	
	 /*****************   ���ɹ���ս���Ʊ�  (ǰ̨)   ***************/
  	 create table tong_siege_control (
  	 		control_id			int unsigned not null auto_increment  ,		/** ���ɹ���ս���Ʊ�ID  */
  	 		siege_id				int 														,		/*** ����սID,������ĳ�����еĹ���  **/
  	 		siege_number  int														,			/**   siege_id������İ�������Ҫ��ʼ���ǵڼ��ι���ս   ***/
  	 		siege_start_time		datetime										,			/**   �´ι���ս��ʼʱ��  */
  	 		siege_sign_end		datetime										,  			/**   �´ι���ս������ֹʱ�� */
  	 		last_win_tongid		int												,			/**    �˹���ս���ϴ�ʤ������ID   **/
  	 		now_phase				int												,			/**     ��ǰ�׶�,0Ϊ���ǽ����ȴ��´ο�ʼ,1Ϊ���ǵ�һ�׶�,2Ϊ���ſ�ʼ���˴��,3Ϊ���ű�����,,4ΪӢ�۵��񱻴��ƹ��ǽ���ڶ��׶�   ***/
  	  primary key(control_id));	
  	  
  	   /*****************   ���ɹ���ս��ս�����б�  (ǰ̨)   ***************/
  	 create table tong_siege_list (
  	 		list_id				int unsigned not null auto_increment  ,		/**  ���ɹ���ս���ɲ�ս�б� ID  */
  	 		siege_id			int 														,		/*** ����սID,������ĳ�����еĹ���  **/
  	 		siege_number  int														,			/**   siege_id������İ�������Ҫ��ʼ���ǵڼ��ι���ս   ***/
  	 		tong_pk			int														,			/****    ��ս�İ���id      ****/  	 		
  	 		join_time			datetime												,			/**     ����ս ����ʱ��     ***/
  	  primary key(list_id));	
  	  
  	  
  	  /***     ���ɹ���սɱ�˼�¼��   ******/
  	   create table tong_siege_pklog (
  	 		pklog_id			int unsigned not null auto_increment  ,		/** ���ɹ���սɱ�˼�¼��ID  */
  	 		siege_id				int 													,		/*** ����սID,������ĳ�����еĹ���  **/
  	 		siege_number  int														,			/**   siege_id������Ĺ���ս�ڼ���ս��   ***/
  	 		tong_id			int														,			/**   ����ID  */
  	 		p_pk					int														,  			/**   ��ɫid */
  	 		pk_number		int												,			/**   �ڴ˴ι���ս����ɱ����  **/
  	 		pk_add_glory int															,			/**    �˴ι���ս�����ӵİ�������ֵ   ***/
  	  primary key(pklog_id));	
  	  
  	  
  	  
  	  /***  ����ս������Ϣ��¼    ****/
  	  create table tong_siege_info (
  	  		info_id				int unsigned not null auto_increment  ,		/** ����ս������Ϣ��¼ID  */
  	  		p_pk 				int														,		/***  ����pPk   ****/	
  	  		attack_type		int														,		/*** �μ�����,1Ϊ���˲�ս,2Ϊ���ɲ�ս  ****/
  	  		join_type			int														,		/**   ս������,1Ϊ����,2Ϊ�س�   ****/
  	  		tong_id			int														,			/**    ����ID  */
  	  		
  	  		dead_num		int														,		/**    �ڵڶ��׶����Ĵ���   **/
  	 		dead_limit		int														,		/**    �������޴��� ***/
  	 		siege_id			int														,		/**    ս��id  **/
  	 		siege_number	int														,		/***  ս��������  **/	
  	 primary key(info_id));
  	 
  	 
  	 /***���ɷ��� ˰�� �����  ****/
  	  create table tong_money_info (
  	  		info_id				int unsigned not null auto_increment  ,		/** ����ս������Ϣ��¼ID  */
  	  		p_pk 				int														,		/***  ����pPk   ****/	
  	  		tong_id			int														,		/***  ����ID **/	
  	  		back_type		int														,		/**     �Ƿ��ǻ��־,1Ϊδ��,2Ϊ�Ѿ��û�    **/
  	  		money_num		int														,		/***   ��Ǯ����  ****/
  	  		sendtime			datetime												,		/**    ���𷢷�ʱ��   ****/
  	 primary key(info_id));
  	 
  	 /****�ƽ���Ŀ�������******/
  	 create table u_goldbox_num_info (
  	 		id					int unsigned not null auto_increment				,/**����*/
  	 		u_pk				int													,/**�˺�ID*/
  	 		p_pk				int													,/**���ID*/
  	 		goldbox_num			int										   default 0,/**�������*/
  	 		cteate_time			datetime											,/**����ʱ��*/
  	  primary key(id));	
  	 
 /***********************************������߼�¼***********************************/
  create table role_be_off(
    off_id              int not null auto_increment                        ,/**����id*/
	p_pk                int                                                ,/**���ID*/
	be_off_time         varchar(50)                                        ,/**�ϴ�����ʱ��*/
	already_time        varchar(50)                              default  0,/**����ʱ�����*/
	be_off_exp          varchar(50)                              default  0,/**���߾���*/
	prop_cumulate_time  varchar(50)                              default  0,/**�����ۻ�ʱ�� ���ǿɹ���ȡ�����ʱ��*/
	primary key(off_id));	

 /***********************************�������buff***********************************/
  create table role_be_off_buff(
    b_id                int not null auto_increment                        ,/**����id*/
	p_pk                int                                                ,/**���ID*/
	be_off_time         varchar(50)                              default  0,/**�ϴ�����ʱ��*/
	be_off_exp          varchar(50)                              default  0,/**���߾���*/
	primary key(b_id));	


/***********************************�ƺű�(t_role_honour)***********************************/
  create table t_role_honour(
    ro_id                int not null auto_increment                        ,/**����id*/
    p_pk                 int                                                ,/**��ɫ����*/
    ho_id                int                                                ,/**�ƺ�����*/
    ho_type              int                                                ,/**�ƺ�����*/
    is_reveal            int                                       default 0,/**�Ƿ���ʾ*/
    detail               varchar(255)                              default null,/**˵��*/
    create_time          datetime                              ,/**ʱ��*/
	primary key(ro_id));	
create index Index1 on t_role_honour (p_pk);

/**********************************��ɫ������־��(u_upgrade_log)****************************/
create table u_upgrade_log(
    p_pk                 int                                                ,/**��ɫ����*/
    role_name            varchar(20)                                        ,/**��ɫ����*/
    content              varchar(100)                                       ,/**��־����*/
    createtime            datetime                                           /**����ʱ��*/
);
create index Index1 on u_upgrade_log (p_pk);
create index Index2 on u_upgrade_log (role_name);

/**********************************��Ϣ��¼��(u_box_record)****************************/
create table u_box_record(
	br_id				int unsigned not null auto_increment                        ,/**����id*/
    p_pk                 int                                                ,/**��ɫ����*/
    info_type            varchar(50)                                        ,/**��Ϣ���ͣ�1Ϊ�����¼*/
    content              varchar(100)                                       ,/**��־����*/
    createtime            datetime                                         ,  /**����ʱ��*/
 primary key(br_id)) ENGINE=innodb;
 
 
/***************************��Ҷ�����¼����Ǯ��**************************************/
create table u_log_money(
	id					bigint unsigned not null auto_increment					,/****����ID*****/
	p_pk				int														,/**p_pk**/
	p_name				varchar(20)												,/**�������**/
	old_num				bigint													,/**�ϵ���ֵ**/
	new_num				bigint													,/***�µ���ֵ**/
	content				varchar(200)											,/***����**/
	createtime			datetime												,/**ʱ��***/
primary key(id)) ENGINE=innodb;

/***************************��Ҷ�����¼��Ԫ����**************************************/
create table u_log_yb(
	id					bigint unsigned not null auto_increment					,/****����ID*****/
	p_pk				int														,/**p_pk**/
	p_name				varchar(20)												,/**�������**/
	old_num				bigint													,/**�ϵ���ֵ**/
	new_num				bigint													,/***�µ���ֵ**/
	content				varchar(200)											,/***����**/
	createtime			datetime												,/**ʱ��***/
primary key(id)) ENGINE=innodb;


/***************************��Ҷ�����¼�����飩**************************************/
create table u_log_exp(
	id					bigint unsigned not null auto_increment					,/****����ID*****/
	p_pk				int														,/**p_pk**/
	p_name				varchar(20)												,/**�������**/
	old_num				bigint													,/**�ϵ���ֵ**/
	new_num				bigint													,/***�µ���ֵ**/
	content				varchar(200)											,/***����**/
	createtime			datetime												,/**ʱ��***/
primary key(id)) ENGINE=innodb;

/***************************��Ҷ�����¼���������ԣ�**************************************/
create table u_log_player(
	id					bigint unsigned not null auto_increment					,/****����ID*****/
	p_pk				int														,/**p_pk**/
	p_name				varchar(20)												,/**�������**/
	old_num				bigint													,/**�ϵ���ֵ**/
	new_num				bigint													,/***�µ���ֵ**/
	content				varchar(200)											,/***����**/
	createtime			datetime												,/**ʱ��***/
primary key(id)) ENGINE=innodb;

/*************************************����ʱ��Ҫ��ʼ��������******************************************************/


  insert into u_tong_rights values (null,1,'���ӳ�Ա','1,2,3,4','n1');
  insert into u_tong_rights values (null,1,'�߳���Ա','1,2,3','n2');
  insert into u_tong_rights values (null,1,'����ְλ','1,2','n3');
  insert into u_tong_rights values (null,1,'�޸ĳƺ�','1,2,3','n4');
  insert into u_tong_rights values (null,null,'������ļ��Ϣ','1,2','n5');
  insert into u_tong_rights values (null,2,'��������','1,2','n6');
  insert into u_tong_rights values (null,3,'ת�ð���','1','n7');
  insert into u_tong_rights values (null,3,'��ɢ���','1','n8');
  insert into u_tong_rights values (null,null,'������Ӫ','1','n9');
  insert into u_tong_rights values (null,null,'ʹ�ð�����','1,2','n10');
  insert into u_tong_rights values (null,null,'�ַ���������','1,2','n11');
  insert into u_tong_rights values (null,null,'�о���Ἴ��','1,2','n12');
  insert into u_tong_rights values (null,null,'����','1,2','n13');
  insert into u_tong_rights values (null,null,'���õж԰���','1,2','n14');
  insert into u_tong_rights values (null,null,'����ͬ�˰���','1,2','n15');
  
  
  
  /*********************************ʦͽϵͳ��********************************************/
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
 
 
   /********************************* ������Ϣ��¼�� ********************************************/
  CREATE TABLE tong_siegebattle_info(
		id 				int unsigned not null auto_increment ,			/** ��Id  */
		p_pk			int(8) NULL DEFAULT 0,				/** ����ppk  */
		siege_id 	    int( 8) NULL DEFAULT 0,				/** ս��vID */				
		siege_number    int(8) NULL DEFAULT 0,			/** ս�����  */	
		infoone 		varchar(255) default null,				/** ��Ϣһ  */
		infotwo 		varchar(255) default null	,			/** ��Ϣ��  */		
  primary key(id)) ENGINE=innodb;
 
   	  /***  �˺���¼��  ****/
  	  create table injure_recond_info (
  	  		injure_id			int unsigned not null auto_increment 					 ,		/** �˺���¼��ID  */
  	  		tong_id				int																,		/***  ����ID   ****/	
  	  		injure_number		int(50)													,		/***  �˺���ֵ **/	
  	  		npc_ID				int														,		/**   NPC_id    **/
  	  		npc_Type  			smallint												,		/***  NPC_type,6����Ӣ�۵���  ****/
  	 primary key(injure_id));
  	 
/******************��ɫ�������Ĺ�ϵ��*******************/
create table p_credit_relation(
       pcid   int primary key auto_increment,/*****��ɫ��������ϵ����*****/
       ppk    int                                ,/*****��ɫ����*****/
       cid    int                                ,/*****��������****/
       pcount int                    default 0    /*****��������****/
       ) ENGINE=MyISAM;

/***********************************��Ա��(t_role_vip)***********************************/
  create table t_role_vip(
    rv_id                int not null auto_increment                        ,/**����id*/
    p_pk                 int                                                ,/**��ɫ����*/
    ho_id                int                                                ,/**�ƺ�����*/
    v_id                 int                                                ,/**VIP������*/
    v_name               varchar(100)                                       ,/**VIP����*/
    rv_begin_time        datetime                                           ,/**��Ա��ʼʱ��*/
    rv_end_time          datetime                                           ,/**��Ա����ʱ��*/
    is_die_drop_exp      int                                       default 0,/**�����ǲ�����ʧ���� 0 ��ʧ 1����ʧ*/
	primary key(rv_id)); 
create index Index1 on t_role_vip (p_pk);	


  	   	  /***  װ��չʾ��  ****/
  	  create table zb_relela_info (
  	  		relela_id			int unsigned not null auto_increment 					 ,		/** �˺���¼��ID  */  	  		
  	  		pwpk		int(11)													,		/***  �˺���ֵ **/	
  	  		relelavar			varchar(400)								,		/**   װ��չʾ�ַ�    **/
  	  		relelatime			datetime												,		/**    װ��չʾʱ��   ****/
  	 primary key(relela_id));
  	 
 /**********����Ա�˳�����(u_tong_member_out)***************/
create table u_tong_member_out(
    o_pk	               int not null auto_increment               , /**id*/ 
  	t_pk 	               int                                       , /**���id*/ 
  	p_pk                   int                                       , /**��ԱID*/
    out_time               datetime                                  , /**�˳����ʱ��*/ 
    primary key(o_pk));
    
 /**********�ʼ���ȡ����(u_mail_bonus)***************/
create table u_mail_bonus(
    id	               int not null auto_increment               , /**id*/ 
  	p_pk                   int                                       , /**��ԱID*/
  	mail_id 	               int                                       , /**�ʼ�id*/ 
    bonus					varchar(100)							,/**����**/
    is_have					int										,/***�Ƿ���ȡ*/
    primary key(id));
  	 
  	 
  	 
  	  /*********************************�Զ����********************************************/
  
CREATE TABLE IF NOT EXISTS `auto` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `p_pk` int(32) NOT NULL,
  `ogre` int(32) NOT NULL,
  `time` int(2) NOT NULL,
  `begin_time` varchar(50) NOT NULL,
  `end_time` varchar(50) DEFAULT '',
  `operate` varchar(255) DEFAULT '',
  `level` int(11) DEFAULT NULL COMMENT 'Ʒ��',
  `guaji_type` int(2) NOT NULL COMMENT '�һ�����',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=gbk AUTO_INCREMENT=7 ;
create index Index1 on auto (p_pk);	

/*******************************************���а�********************************************/
CREATE TABLE `rank` (                                     
          `id` int(16) NOT NULL AUTO_INCREMENT,                   
          `p_pk` int(16) NOT NULL,                                
          `p_name` varchar(50) default NULL,
          `p_menpai` varchar(50) default null,                                
          `p_level` int(2) NOT NULL default 1,  
          `p_level_time` datetime COMMENT '�ȼ��޸�ʱ��',                               
          `p_exp` int(32) NOT NULL default 0  COMMENT '��ɫ����',                      
          `p_exp_time` datetime COMMENT '�޸Ľ�ɫʱ��',   
          `exp_tong` int(1) NOT NULL default 0 COMMENT '�Ƿ�ͳ��', 
          `kill` int(5) NOT NULL default 0 COMMENT 'ɱ����',         
          `kill_time` datetime COMMENT 'ɱ��ʱ��',                        
          `dear` int(7) NOT NULL default 0 COMMENT '��������', 
          `who`  varchar(50) default NULL comment '��˭�İ�������',       
          `dear_time` datetime COMMENT '���������޸�ʱ��',   
          `evil` int(7) NOT NULL default 0 COMMENT '���ֵ',         
          `evil_time` datetime COMMENT '����޸�ʱ��',   
          `glory` int(7) NOT NULL default 0 COMMENT '������',         
          `glory_time` datetime COMMENT '����ʱ��',   
          `money` bigint(32) NOT NULL default 0 COMMENT '��Ǯ',         
          `money_time` datetime COMMENT '��Ǯʱ��',   
          `yuanbao` int(8) NOT NULL default 0 COMMENT 'Ԫ��',         
          `yuanbao_time` datetime COMMENT 'Ԫ���޸�ʱ��',   
          `credit` int(8) NOT NULL default 0 COMMENT '��ɫ����',         
          `credit_time` datetime COMMENT '��ɫ�����޸�ʱ��',  
          `open` int(8) NOT NULL default 0 COMMENT '����������',         
          `open_time` datetime COMMENT '�����������޸�ʱ��', 
          `vip` int(8) NOT NULL default 0 COMMENT 'VIP�ȼ�', 
          `vip_eff` int(8) not null default 0 comment 'VIP����ʱ��',        
          `vip_time` datetime COMMENT 'VIP�ȼ��޸�ʱ��',   
          `bangkill` int(8) NOT NULL default 0 COMMENT '����ɱ������Ұս/��ս/���ǣ�����',         
          `bangkill_time` datetime COMMENT '����ɱ������Ұս/��ս/���ǣ��޸�ʱ��',
          `killnpc` int(8) NOT NULL default 0 COMMENT 'ɱ�ֵ���������=����ȼ����������������2��',         
          `killnpc_time` datetime COMMENT 'ɱ�ֵ���������=����ȼ����������������2���޸�ʱ��',
          `wei_task` int(8) NOT NULL default 0 COMMENT '�������ʱ��õ�����', 
          `wei_other` int(8) NOT NULL default 0 COMMENT '������������',         
          `wei_time` datetime COMMENT '�����޸�ʱ��',
          `dead` int(8) NOT NULL default 0 COMMENT '����/��ɱ������',         
          `dead_time` datetime COMMENT '����/��ɱ���޸�ʱ��',
          `killboss` int(8) NOT NULL default 0 COMMENT 'Ұ���ɱBoss�ĵ���������=Boss�ȼ�������Boss��õ�����',         
          `killboss_time` datetime COMMENT 'Ұ���ɱBoss�޸�ʱ��',
          `zhong` int(8) NOT NULL default 0 COMMENT '����ʱ��',         
          `zhong_time` datetime COMMENT '����ʱ���޸�ʱ��',
          `sale` int(8) NOT NULL default 0 COMMENT '��������������Ʒ�������Ĵ���',         
          `sale_time` datetime COMMENT '�������޸�ʱ��',
          `yi` int(8) NOT NULL default 0 COMMENT '��������ߵ�������', 
          `yi_who` varchar(50) default NULL comment '��˭������',        
          `yi_time` datetime COMMENT '�����޸�ʱ��',
          `ans` int(8) NOT NULL default 0 COMMENT '������ȷ����',         
          `ans_time` datetime COMMENT '������ȷ�����޸�ʱ��',
          `meng` int(8) NOT NULL default 0 COMMENT '��ɸ�����ĵ�����¼',         
          `meng_time` datetime COMMENT '��ɸ�����ĵ����޸�ʱ��',   
          `boyi` bigint default 0 COMMENT '����',
          `boyi_time` datetime COMMENT '�����޸�ʱ��',  
          `lost` int(2) not null default 0 comment '�Թ�����',
          `lost_time` datetime comment '�Թ�����ʱ��',  
          `killlang` int(2) not null default 0 comment 'ɱ��ǧ���ɾ�����',
          `killlang_time` datetime comment 'ɱ��ǧ���ɾ�ʱ��',
          `langjun` int(2) not null default 0 comment '����ǧ���ɾ�����',
          `langjun_time` datetime comment '����ǧ���ɾ�ʱ��',
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
  	  		
  	  		wj_menpai		 Enum('0','1','2','3')										,		/**   ϵͳ��������֮, �������   **/
  	  		wj_title			    varchar(100)									    	,		/**   ϵͳ��������֮, ��ҳƺ�, ����ж���ƺ�,��","�ָ�   **/
  	  		wj_credit			varchar(10)													,		/**   ϵͳ��������֮, �������,  ��"-"����, ���ж��,��";"����    **/
  	  		wj_next			varchar(10)										 		,		/**   ϵͳ��������֮, �������һ��   **/
  	  		
  	  		
  	  		is_only_one		Enum('0','1','2','3','4','5','6','7','8','9')		,		/** �Ƿ����ȡһ��, Ϊ1��ʾ����ȡһ��,Ϊ0��ʾ���Բ�ֹ��ȡһ�� */
  	  		onces				int(3) 														,		/**  һ��֮���������ȡ����  */
  	  		
  	  		
  	  		give_goods			varchar(100)												,		/**    ����װ������Ʒ, ��","�ָ�, �Ͷһ��˵�������������ͬ    ****/
  	  		isuseable			int(3)											default 1		,		/**   �Ƿ���Ч,Ϊ���ʾ��Ч,���ᱻ��ʾ����. Ϊ1�Ļ��������ʾ  **/
  	  		
  	  		horta_display		varchar(100)												,		/**   ��������  **/
  	 primary key(horta_id));
  	 
  	 
  	 
  	 
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
  	 primary key(id));
  	 
 
/****************ϵͳ������s_lottery_yuanbao***********************/
create table s_lottery_yuanbao (
  id			int unsigned not null auto_increment					,/***ID**/
  lottery_date	varchar(20)												,/**��Ʊ�ں�**/
  lottery_all_yb	varchar(20)											,/**�������۶�***/
  lottery_catch_yb	varchar(20)											,/***��ȡ���****/
  lottery_content	varchar(20)											,/**��Ʊ����**/
  lottery_catch_player	varchar(20)										,/**��ȡ����**/
  lottery_create_time	varchar(20)										,/***����ʱ��***/
  primary key(id)) ENGINE=innodb;
  create index Index1 on s_lottery_yuanbao (lottery_date);
  
/*****************��ҹ����Ʊ��u_lottery_yuanbao*************************/
create table u_lottery_yuanbao (
	id				bigint unsigned not null auto_increment				,/***ID***/
	p_pk			int													,/*****/
	lottery_date	varchar(20)											,/**��Ʊ�ں�**/
	lottery_content	varchar(20)											,/***��Ʊ����**/
	lottery_zhu		int													,/****ע��**/
	lottery_time	datetime											,/***����ʱ��**/
	lottery_bonus_lv	int							default 0			,/**�н��ȼ�**/
	lottery_bonus	bigint							default 0			,/***���**/
	is_have			int								default 0			,/**�Ƿ���ȡ**/
	have_time		datetime											,/**��ȡʱ��***/
	primary key(id)) ENGINE=innodb;
	create index Index1 on u_lottery_yuanbao (lottery_date,p_pk);
	
	
	
	/********************************��̨��ɫ��****************************************/
CREATE TABLE `leitai_role` (                          
               `id` int(10) unsigned NOT NULL AUTO_INCREMENT,      
               `lei_id` int(12) NOT NULL DEFAULT '0',              
               `ppk` int(12) NOT NULL DEFAULT '0',                 
               `killman` int(12) NOT NULL DEFAULT '0',             
               `dead` int(12) NOT NULL DEFAULT '0',                
               `pname` varchar(50) NOT NULL,        
               `killtime` datetime COMMENT 'ɱ���¼�',                
               PRIMARY KEY (`id`)                                  
             ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk  ;
/*************************************�μӻ��̨�Ľ�ɫ**************************************/
CREATE TABLE `active_leitai_role` (                          
               `id` int(10) unsigned NOT NULL AUTO_INCREMENT,     
               `ppk` int(16) not null default '0', 
               `pname` varchar(50) NOT NULL, 
               `scene_id` int(11) DEFAULT '0' comment '���볡��',
               `round1` int(1) default '0' comment '��һ��PK״̬',  
               `round2` int(1) default '0' comment '�ڶ���PK״̬',  
               `round3` int(1) default '0' comment '������PK״̬', 
               `into_time` datetime COMMENT '������̨ʱ��',
               PRIMARY KEY (`id`)                                  
             ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk  ;
/*************************************��̨**************************************/
CREATE TABLE `leitaibaoming` (                          
               `id` int(10) unsigned NOT NULL AUTO_INCREMENT,     
               `ppk` int(16) not null default 0, 
               `zu` int(1) not null default '0' comment '�������飺������0������ȸ��1�����׻���2�������䣨3��',
               `win` int(2) default 0,
               `dead` int(2) default 0,
               `isComein` int(2) default 0,
               PRIMARY KEY (`id`)                                  
             ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk  ;
             
              /*******************************************��ս��̨*********************************************/
             CREATE TABLE `battlepeo` (                          
               `id` int(10) unsigned NOT NULL AUTO_INCREMENT,     
               `ppk` int(16) not null default 0, 
               `name` varchar(50) default null,
               `sheng` int(16) not null default 0,
               `intoscene` int(5) not null default 0,
               `pk` int(1) not null default 0,
               `intotime`  datetime COMMENT '������̨ʱ��',
               PRIMARY KEY (`id`)                                  
             ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk  ;
             
/*****************����ؾ�p_unchartedroom*************************/
create table p_unchartedroom (
	id				bigint unsigned not null auto_increment				,/***ID***/
	p_pk			int													,/*****/
	into_state		int								default 0			,/**�н��ȼ�**/
	into_num		int								default 0			,/**�н��ȼ�**/
	into_time		datetime											,/**��ȡʱ��***/
	out_time		datetime											,/**��ȡʱ��***/
	primary key(id)) ENGINE=innodb;
	create index Index1 on p_unchartedroom (p_pk);

/*****************ף����p_wishingtree*************************/
create table p_wishingtree (
	id				bigint unsigned not null auto_increment				,/***ID***/
	p_pk			int													,/*****/
	p_name			varchar(10)											,/**�н��ȼ�**/
	wishing			varchar(100)										,/**�н��ȼ�**/
	top				int								default 0			,/**�н��ȼ�**/
	create_time		datetime											,/**��ȡʱ��***/
	top_time		datetime											,/**��ȡʱ��***/
	primary key(id)) ENGINE=innodb;
	create index Index1 on p_wishingtree (p_pk);
	
/*****************���ɱ���*************************/
create table p_menpaicontest (
	id				bigint unsigned not null auto_increment				,/***ID***/
	p_pk			int													,/*****/
	p_name			varchar(10)											,/**�������**/
	p_type			int													,/***��ҳ�ν**/
	into_state		int								default 0			,/**���ؾ��е�״̬***/
	into_time		datetime											,/**����ʱ��***/
	out_time		datetime											,/**��ȥʱ��***/
	win_state		int								default 0			,/**ʤ��״̬**/
	win_time		datetime											,/**����ʱ��**/
	kill_num		int								default	0			,/**ɱ������**/
	old_kill_num	int								default	0			,/**ɱ������**/
	kill_time		datetime											,/**���ɱ��ʱ��*/
	win_num			int								default	0			,/**��ȡ����ӵĴ���**/
	win_num_time	datetime											,/***��ȡ���������ʱ��***/
	kill_p_pk		int								default	0			,/**��ȡ����ӵĴ���**/
	kill_p_pk_time	datetime											,/***��ȡ���������ʱ��***/
	primary key(id)) ENGINE=innodb;
	create index Index1 on p_menpaicontest (p_pk);
	
/****************PK�����ɽ�۽�)������*******************/
create table  pk_active_regist (
   ID int(11) not null auto_increment,/*****ID****/
   roleID int(11)  not null,/*****��ɫID****/
   roleLevel int(11) not null,/*****��ɫ�ȼ�****/
   roleName varchar(11) not null,/*****��ɫ����****/
   registTime datetime not null,/*****����ʱ��****/
   isWin int(11) not null default '0',/*****�Ƿ�ʤ����0Ϊʤ����1Ϊʧ�ܣ�****/
   isEnter int(11) not null default '1',/*****��ҽ��볡��״̬1Ϊ����״̬0Ϊ�ǽ���״̬****/
   isGetPrice int(11) not null default '0',/*****�Ƿ������ȡ��Ʒ��1Ϊ�н�Ʒ��ȡ��****/
   PRIMARY KEY  (ID)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk;	

 /*******************PK�����ɽ�۽�)�����***********************/
create table pk_vs (
   ID int(10) unsigned not null auto_increment,/*****ID****/
   roleAID int(11) not null,/*****��ɫA��ID****/
   roleBID int(11) not null,/*****��ɫB��ID****/
   roleAName varchar(30) not null,/*****��ɫA������****/
   roleBName varchar(30) not null,/*****��ɫB������****/
   winRoleID int(11) not null default '0',/*****��ʤ�ߵ�ID****/
  PRIMARY KEY  (ID)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=gbk;
 
 
	