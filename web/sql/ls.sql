create database jygame_user;  
/**********���ע���(u_login_info)***************/
create table u_login_info ( 
   u_pk             smallint unsigned not null auto_increment ,	 /**������Ա��Ϣid*/ 
   u_name           varchar(200)                              ,  /**����Ա��¼��*/ 
   u_paw            varchar(200)                              ,  /**����Ա��¼����*/
   login_state      int                                       ,  /**��½״̬ 1Ϊ��½ 0 Ϊδ��½*/ 
   create_time      datetime                                  ,  /**����ʱ��*/
   primary key (u_pk));
   
/**********��ɫ��Ϣ��(u_part_info)***************/
create table u_part_info ( 
   p_pk             smallint unsigned not null auto_increment ,	 /**��ɫid*/ 
   u_pk             int                                       ,	 /**������Ա��Ϣid*/ 
   p_name           varchar(200)                              ,  /**��ɫ��*/
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
   p_title          int                                       ,  /**�ƺ�*/
   p_title_name     varchar(500)                              ,  /**�ƺ�����*/
   p_born           int                                       ,  /**����*/
   p_camp           int                                       ,  /**��Ӫ*/
   p_camp_name      varchar(500)                              ,  /**��Ӫ����*/
   p_school         int                                       ,  /**����*/
   p_school_name    varchar(500)                              ,  /**��������*/ 
   p_experience     varchar(500)                              ,  /**����*/
   p_benji_experience     varchar(500)                        ,  /**��������*/
   p_xia_experience varchar(500)                              ,  /**�¾���*/
   p_silver         varchar(500)                              ,  /**���ӵ�λ ��*/
   p_copper         varchar(500)                              ,  /**ͭǮ��λ ��*/
   p_depot          int                                       ,  /**�ֿ�	*/
   p_pk_value       int                                       ,  /**pkֵ*/
   p_pks            int                                       ,  /**����1��2��*/
   p_pk_changetime  datetime                                  ,  /**pk���ظı��ʱ��*/
   p_isInitiative	int								 default 0,  /**��ʶ�Ƿ�����������״̬��0��1��*/
   p_isPassivity	int								 default 0,  /**��ʶ�Ƿ��ڱ�������״̬��0��1��*/	 
   p_map            varchar(200)                              ,  /**���ڳ���ID*/
   p_procession     int                                       ,  /**�Ƿ����0��1��*/
   p_procession_numner  varchar(200)                          ,  /**������*/
   p_tong           int                                       ,  /**���*/
   p_tong_name      varchar(500)                              ,  /**�������*/
   
   p_wrap_content		int									,  /**��������*/
   p_wrap_spare			int									,  /**����ʣ������*/
   
   create_time      datetime                                  ,  /**����ʱ��*/
   primary key (p_pk));
    
/**********��ɫ��½��¼��(u_part_annal)***************/
create table u_part_annal ( 
   a_pk             smallint unsigned not null auto_increment ,	 /**��ɫ��½��¼��id*/  
   u_pk             int                                       ,	 /**������Ա��Ϣid*/
   p_pk             int                                       ,	 /**��ɫid*/
   p_name           varchar(200)                              ,  /**��ɫ��*/  
   p_map            varchar(200)                              ,  /**���ڳ���ID*/
   a_ken            varchar(200)                              ,  /**���ڳ�����Ұ*/  
   primary key (a_pk));


 /**********��ɫװ����(u_trait_info)***************/
create table u_trait_info ( 
   t_pk             smallint unsigned not null auto_increment ,	 /**��ɫװ��id*/ 
   u_pk             int                                       ,	 /**������Ա��Ϣid*/ 
   p_pk             int                                       ,	 /**��ɫid*/ 
   t_caput          int                                       ,  /**ͷ��װ��*/
   t_toufy_xiao     int                                 default 0,  /**ͷ��װ����С����*/
   t_toufy_da       int                                 default 0,  /**ͷ��װ��������*/
   t_tou_li         int                                 default 0,  /**ͷ������*/
   t_tou_min        int                                 default 0,  /**ͷ������*/
   t_tou_ti         int                                 default 0,  /**ͷ����Ѫ*/
   t_tou_wu         int                                 default 0,  /**ͷ������*/  
   t_body           int                                       ,   /**����װ��*/
   t_tify_xiao      int                                 default 0,/**����װ����С����*/
   t_tify_da        int                                 default 0,/**����װ��������*/
   t_ti_li          int                                 default 0,/**��������*/
   t_ti_min         int                                 default 0,/**��������*/
   t_ti_ti          int                                 default 0,/**������Ѫ*/
   t_ti_wu          int                                 default 0,/**��������*/  
   t_crura          int                                 default 0,/**�Ȳ�װ��*/
   t_tuify_xiao     int                                 default 0,/**�Ȳ�װ����С����*/
   t_tuify_da       int                                 default 0,/**�Ȳ�װ��������*/
   t_tui_li         int                                 default 0,/**�Ȳ�����*/
   t_tui_min        int                                 default 0,/**�Ȳ�����*/
   t_tui_ti         int                                 default 0,/**�Ȳ���Ѫ*/
   t_tui_wu         int                                 default 0,/**�Ȳ�����*/  
   t_feet           int                                       ,   /**�Ų�װ��*/
   t_jiaofy_xiao    int                                default 0, /**�Ų�װ����С����*/
   t_jiaofy_da      int                                default 0, /**�Ų�װ��������*/
   t_jiao_li        int                                default 0, /**�Ų�����*/
   t_jiao_min       int                                default 0, /**�Ų�����*/
   t_jiao_ti        int                                default 0, /**�Ų���Ѫ*/
   t_jiao_wu        int                                default 0, /**�Ų�����*/  
   t_necklace       int                                       ,   /**����*/
   t_xlfy_xiao      int                                default 0, /**������С����*/
   t_xlfy_da        int                                default 0, /**����������*/ 
   t_xlgj_xiao      int                                default 0, /**������С����*/
   t_xlgj_da        int                                default 0, /**������󹥻�*/
   t_xl_li          int                                default 0, /**��������*/
   t_xl_min         int                                default 0, /**��������*/
   t_xl_ti          int                                default 0, /**������Ѫ*/
   t_xl_wu          int                                default 0, /**��������*/ 
   t_shouzhuo       int                                       ,   /**����*/
   t_szfy_xiao      int                               default 0,  /**������С����*/
   t_szfy_da        int                               default 0,  /**����������*/ 
   t_szgj_xiao      int                               default 0,  /**������С����*/
   t_szgj_da        int                               default 0,  /**������󹥻�*/
   t_sz_li          int                               default 0,  /**��������*/
   t_sz_min         int                               default 0,  /**��������*/
   t_sz_ti          int                               default 0,  /**������Ѫ*/
   t_sz_wu          int                               default 0,  /**��������*/  
   t_finger         int                                       ,   /**��ָ*/
   t_jzfy_xiao      int                               default 0,  /**��ָ��С����*/
   t_jzfy_da        int                               default 0,  /**��ָ������*/ 
   t_jzgj_xiao      int                               default 0,  /**��ָ��С����*/
   t_jzgj_da        int                               default 0,  /**��ָ��󹥻�*/
   t_jz_li          int                               default 0,  /**��ָ����*/
   t_jz_min         int                               default 0,  /**��ָ����*/
   t_jz_ti          int                               default 0,  /**��ָ��Ѫ*/
   t_jz_wu          int                               default 0,  /**��ָ����*/ 
   t_arm            int                                       ,   /**����*/ 
   t_wqgj_xiao      int                               default 0,  /**������С����*/
   t_wqgj_da        int                               default 0,  /**������󹥻�*/
   t_wq_li          int                               default 0,  /**��������*/
   t_wq_min         int                               default 0,  /**��������*/
   t_wq_ti          int                               default 0,  /**������Ѫ*/
   t_wq_wu          int                               default 0,  /**��������*/  
   t_zbgj_xiao      int                                       ,   /**��С����*/
   t_zbgj_da        int                                       ,   /**��󹥻�*/
   t_zbfy_xiao      int                                       ,   /**��С����*/
   t_zbfy_da        int                                       ,   /**������*/ 
   t_li             int                                       ,   /**����*/
   t_min            int                                       ,   /**����*/
   t_ti             int                                       ,   /**��Ѫ*/
   t_wu             int                                       ,   /**����*/ 
   create_time      datetime                                  ,   /**����ʱ��*/
   primary key (t_pk));
   
/**********��ɫ������(u_wrap_info)***************/
create table u_wrap_info ( 
   w_pk             smallint unsigned not null auto_increment ,	 /**��ɫ������*/ 
   u_pk             int                                       ,	 /**������Ա��Ϣid*/ 
   p_pk             int                                       ,	 /**��ɫid*/
   w_type           int                                       ,	 /**��������*/ 
   w_number         int                                       ,	 /**��������*/ 
   w_article        varchar(500)                              ,  /**������Ʒ*/
   create_time      datetime                                  ,  /**����ʱ��*/
   primary key (w_pk));
 
/**********��ɫ���б�(u_limerick_info)***************/
create table u_limerick_info ( 
   l_pk             smallint unsigned not null auto_increment ,	 /**��ɫid*/ 
   u_pk             int                                       ,	 /**������Ա��Ϣid*/ 
   p_pk             int                                       ,	 /**��ɫid*/
   l_jin_fy         int                                       ,	 /**�������*/
   l_mu_fy          int                                       ,	 /**ľ������*/
   l_shui_fy        int                                       ,	 /**ˮ������*/
   l_huo_fy         int                                       ,	 /**�������*/
   l_tu_fy          int                                       ,	 /**��������*/
   l_jin_gj         int                                       ,	 /**�𹥻���*/
   l_mu_gj          int                                       ,	 /**ľ������*/
   l_shui_gj        int                                       ,	 /**ˮ������*/
   l_huo_gj         int                                       ,	 /**�𹥻���*/
   l_tu_gj          int                                       ,	 /**��������*/
   create_time      datetime                                  ,  /**����ʱ��*/
   primary key (l_pk)); 
   
/**********��ɫ�����(p_pet_info)***************/ 
create table p_pet_info(
	pet_pk			 smallint unsigned not null auto_increment , /**ID */
	p_pk			 int 					                   , /**��ɫid*/
	pet_id			 int					                   , /**��Ӧpet�����id*/
	pet_name		 varchar(200)				               , /**��������*/
	pet_nickname	 varchar(200)				               , /**�����ǳ�*/
	pet_grade		 int					                   , /**�ȼ�*/
	pet_exp			 varchar(200)			                   , /**����*/
	pet_xia_exp		 varchar(200)                              , /**�¼�����ﵽ��һ����Ҫ�ľ���*/
	pet_gj_xiao      int                     		           , /**��С����*/
	pet_gj_da        int					                   , /**��󹥻�*/
	pet_sale		 int			                           , /**�����۸�*/
	pet_img			 varchar(200)                              , /**����ͼƬ*/
	pet_grow	     int                                       , /**����ɳ��ʡ�*/
	pet_wx           int                  			           , /**�������Խ�=1��ľ=2��ˮ=3����=4����=5 */
    pet_wx_value     int           				               , /**��������ֵ*/ 
    pet_skill_one	 int                                       , /**����1	��ѧϰ�ļ���id*/
    pet_skill_two	 int                          	           , /**����2	��ѧϰ�ļ���id*/
    pet_skill_three	 int                        	           , /**����3	��ѧϰ�ļ���id*/
    pet_skill_four	 int                          		       , /**����4	��ѧϰ�ļ���id*/
    pet_skill_five	 int                                  	   , /**����5	��ѧϰ�ļ���id*/
    pet_life		 int			                           , /**����**/
    pet_type         int                                       , /**����	�Ƿ����Ȼ����*/	
    pet_isBring		 int					                   , /**�Ƿ�������:1��ʾ��ս��״̬��0��ʾ��*/
    pet_fatigue		 int			 	                       , /**ƣ�Ͷ�0-100,��ս״̬������ƣ�Ͷȣ�һ��Сʱ��10��*/
 primary key (pet_pk));
  
/**********��ɫ���ܱ�(u_skill_info)***************/
create table u_skill_info ( 
   s_pk             smallint unsigned not null auto_increment ,	 /**id*/ 
   p_pk             int                                       ,	 /**��ɫid*/
   sk_id         	int                                       ,	 /**����id*/ 
   sk_name			varchar(200)										  ,	 /**��������*/ 
   sk_usetime		datetime									,/**��������һ�ε�ʹ�õ�ʱ��*/
   create_time      datetime                                  ,  /**����ʱ��*/
   primary key (s_pk)); 
  
/**********��ɫ���ｻ�ױ�(u_pet_sell)***************/ 
create table u_pet_sell(
	ps_pk			     smallint unsigned not null auto_increment , /**ID */
	p_pk			     int 					                   , /**�����ɫid*/
	p_by_pk			     int 					                   , /**�������ɫid*/ 
	pet_id			     int					                   , /**��Ӧpet�����id*/
	ps_silver_money      int                                       , /**��������Ҫ��Ʒ�ļ۸������*/
    ps_copper_money      int                                       , /**��������Ҫ��Ʒ�ļ۸��ͭǮ*/
 primary key (ps_pk));
 
/**********��ɫ����(u_sell_info)***************/
create table u_sell_info ( 
   s_pk                   smallint unsigned not null auto_increment ,	 /**id*/
   p_pk                   int                                       ,	 /**���������ɫid*/
   p_by_pk                int                                       ,	 /**�������ɫid*/
   s_wuping               int                                       ,    /**��������Ҫ���׵���Ʒ*/
   s_wp_type              int                                       ,    /**��������Ҫ��Ʒ����*/
   s_wp_number            int                                       ,    /**��������Ҫ��Ʒ������*/ 
   s_wp_silver_money      int                                       ,    /**��������Ҫ��Ʒ�ļ۸������*/
   s_wp_copper_money      int                                       ,    /**��������Ҫ��Ʒ�ļ۸��ͭǮ*/ 
   s_silver_money         int                                       ,    /**��������Ҫ���׵�����*/
   s_copper_money         int                                       ,    /**��������Ҫ���׵�ͭǮ*/ 
   s_sf_ok                int                                       ,    /**�������������Ƿ�ȷ��0ûȷ��1ȷ��*/ 
   s_by_wuping            int                                       ,    /**������Ҫ���׵���Ʒ*/
   s_by_wp_type           int                                       ,    /**������Ҫ��Ʒ����*/
   s_by_wp_number         int                                       ,    /**������Ҫ��Ʒ������*/ 
   s_by_silver_money      int                                       ,    /**������Ҫ��Ʒ������*/
   s_by_copper_money      int                                       ,    /**������Ҫ��Ʒ��ͭǮ*/ 
   s_by_silver            int                                       ,    /**������Ҫ��ƷҪ���׵�����*/
   s_by_copper            int                                       ,    /**������Ҫ��ƷҪ���׵�ͭǮ*/ 
   s_by_sf_ok             int                                       ,    /**�����������Ƿ�ȷ��0ûȷ��1ȷ��*/ 
   create_time            datetime                                  ,    /**����ʱ��*/
   primary key (s_pk));
 
 /**********����Ƶ��(u_communion)***************/
 create table u_communion ( 
   c_pk                   smallint unsigned not null auto_increment ,	 /**����Ƶ��id*/
   p_pk                   int                                       ,	 /**���ͽ�ɫid*/
   p_name                 varchar(200)                              ,	 /**���ͽ�ɫ����*/
   p_pk_by                int                                       ,	 /**���ս�ɫid*/
   p_name_by              varchar(200)                              ,	 /**���ս�ɫ����*/ 
   c_bang                 int                                       ,	 /**�������*/
   c_dui                  int                                       ,	 /**�������*/
   c_zhen                 int                                       ,	 /**������Ӫ*/
   c_title                varchar(500)                              ,    /**��������*/ 
   c_type                 int                                       ,    /**����*/ 
   create_time            datetime                                  ,    /**����ʱ��*/
   primary key (c_pk)); 
   
   
   
   
/****************************************LS****************************/
      
/****************************************NPC��ʱ��****************************/
   
create table n_attack_info (
  n_pk              SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT , /**��ǰս��NPCID */
  u_pk              int                                       , /**������Ա��Ϣid*/ 
  p_pk		        int					                      , /**��ɫid*/
  n_current_HP	    varchar(200)      			              , /**npc��ǰѪֵ*/
  n_attackswitch	int                                       , /**������������	0��ʾ�˵�ˢ�³���npc����������ң�1��ʾ�˵�ˢ�³���npc����������ң�*/
  npc_ID            int					                      , /**npc */
  npc_Name          varchar(200)                              , /**����	npc����*/
  npc_HP            varchar(200)                              , /**��Ѫ	npc��Ѫֵ*/
  npc_defence_da    int					                      , /**������	npc���� */
  npc_defence_xiao  int					                      , /**��С����	npc����*/
  npc_jin_fy	    int					                      , /**�����*/	
  npc_mu_fy	        int					                      , /**ľ����*/	
  npc_shui_fy	    int					                      , /**ˮ����*/	
  npc_huo_fy	    int					                      , /**�����*/
  npc_tu_fy	        int					                      , /**������*/	
  npc_drop          int					                      , /**������	��ʾΪ����������20��ʾ20%�ı�����*/
  npc_Level         int					                      , /**�ȼ�	npc�ȼ� */
  npc_EXP           double				                      , /**����	ɱ��npc��þ���*/
  npc_money	        varchar(200)			                  , /**����Ǯ������ɱ���ֺ��20��30֮�����ȡ����ʾΪ20,30*/
  npc_take          int					                      , /**�ɷ�׽	0��ʾ���ɲ�׽��1��ʾ���Բ�׽*/
  npc_refurbish_time   int				                      , /**ˢ��ʱ����	����Ϊ��λ*/
  scene_id          int                                       , /**ˢ�µص�id*/
  npc_key		    varchar(200	)			                  , /** ����Ψһ��ʾ */
  npc_isAttack		int				default 0                 , /**��ʾnpc�Ƿ���ս��״̬��1��ʾ�ǣ�0��ʾ��Ĭ��Ϊ0*/
  npc_type           int                default 1               , /* npc���ͣ����Ա���ܵ�npcΪ2���Ϳ��Ա�������npcΪ1 **/
  dizzy_bout_num      int                              default 0, /** ����״̬��ʣ��غ��� */
  poison_bout_num    int          default 0                     ,/**�ж�״̬��ʣ��غ���
  create_time     	datetime				                  , /**����ʱ�� */
  primary key (n_pk));
  
  /**********************************��ֵ�����Ʒ��***********************************************/
  create table n_dropgoods_info(
  d_pk						 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT ,/**ID */
  p_pk						 int					   				   ,/**��ɫid*/
  drop_num					 int									   ,/*��������*/
  goods_id					 int 									   ,/*��Ʒid*/
  goods_name				 varchar(200)							   ,/*��Ʒ����*/
  goods_type				 int									   ,/*��Ʒ����*/
  primary key (d_pk));
  
/**********************************���ʱnpc�������Ǯ����ʱ��***********************************************/
    
 create table n_dropExpMoney_info(
   d_pk							 SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT ,/**ID */
   p_pk							 int					   				   ,/**��ɫid*/
   drop_exp					     int									   ,/*npc���侭��*/	
   drop_money				     int									   ,/*npc�����Ǯ��*/
   primary key (d_pk));
    



    
    
/*************************��ҿ�ݼ����ã�u_shortcut_info��********************************/
    create table u_shortcut_info(
    sc_pk							 smallint unsigned not null auto_increment   ,/**id*/ 
    p_pk							 int										 ,/**���id*/
    sc_name							 varchar(200)                                ,/**��ݼ����֣����*/
    sc_display						 varchar(200)                                ,/**���ú���ʾ�����֣����磻��������,ҩƷ����*/
    sc_type							 int                                         ,/**����,ֵΪ-1ʱ��ʾû�����ÿ�ݼ�*/
    operate_id						 int                                         ,/**����id*/
    object							 int                                         ,/**���ö���*/          
    primary key(sc_pk));

  
  
   /*************************��Ұ�����ĵ��ߣ�u_propgroup_info��,ÿ����¼����¼����һ�����********************************/
  create table u_propgroup_info(
  	pg_pk	                         smallint unsigned not null auto_increment       ,   /**id*/ 
    p_pk                             int                                             ,	 /**��ɫid*/
    pg_type                          int                                             ,	 /**���������*/ 
    prop_id			                 int                                             ,	 /**����id*/
    prop_type						 int                                             ,   /**��������*/
    prop_name	                     varchar(200)                                    ,   /**��������*/
    prop_price                       int                                             ,    /**������Ǯ*/
    prop_num                         int                                             ,	 /**�������������ܳ������Ƹ���*/ 
    create_time                      datetime                                        ,  /**����ʱ��*/
    primary key(pg_pk));
    
     /*************************���Ʊ���Ҫʱ���ʹ�ô������ƵĶ��󣩣�u_time_control��********************************************/
    create table u_time_control(
    id	                         smallint unsigned not null auto_increment       ,   /**id*/ 
    p_pk                         int                                             ,   /**��ɫid*/
    object_id                    int                                             ,	 /**��Ҫ���ƵĶ���*/
    object_type                  int                                             ,   /**��Ҫ���ƵĶ�������ͣ��磺���ߣ��˵���*/
    use_datetime                 datetime                                        ,   /**��¼���һ�ε�ʹ��ʱ��*/
    use_times                    int                                             ,   /**��¼�����ʹ�ô���*/
    primary key(id));
    
  /*************************��������Ǽ�¼_��ǵ����ã�u_coordinate_info��********************************/
  create table u_coordinate_info(
  c_pk	                         smallint unsigned not null auto_increment       , /**id*/
  p_pk                           int                                             , /**��ɫid*/
  coordinate_prop_id             int                                             , /**�������id*/
  coordinate                     int                                             , /**�������*/
  prop_isUse                    int                                     default 0, /**��ǵ����Ƿ�ʹ��*/
  primary key(c_pk));
 
  
  /*******************************��ӱ�u_group_info��************************************************************/
  create table u_group_info(
  g_pk                          smallint unsigned not null auto_increment       , /**id*/ 
  p_pk                          int                                             , /**��ɫid*/
  g_captain_pk                  int                                             , /**��ʾ�Ƕӳ�id�����ӳ�id��p_pk��ͬ��*/
  primary key(g_pk));
   
   
  /*******************************���֪ͨ��u_groupnotify_info��************************************************************/
  create table u_groupnotify_info(
  n_pk                                 smallint unsigned not null auto_increment        , /**id*/ 
  notifyed_pk                          int                                              , /**��֪ͨ�����id*/
  create_notify_pk                     int                                              , /**����֪ͨ�����id*/ 
  notify_content                       varchar(200)                                     , /**֪ͨ������**/
  notify_type                          int                                              , /**֪ͨ���ͣ�1��֪ͨ����������ӣ�2��֪ͨ�����ɢ��3.֪ͨ�Է�ͬ����ӣ�4��������***/
  create_time                          datetime                                         , /**����ʱ��*/
  notify_flag                          int                                     default 0, /**֪ͨ��ʶ��0��ʾû��֪ͨ��1��ʾ��֪ͨ*/
  primary key(n_pk));
   
  
  
  /***********************************************buffʹ��Ч��(u_buffeffect_info)***********************************/
  create table u_buffeffect_info  (
  bf_pk                       smallint unsigned not null auto_increment                  , /**id*/ 
  buff_id                     int                                                        , /**buff_id*/
  buff_name                   varchar(200)                                               , /**����*/	
  buff_display                varchar(200)                                               , /**buff����*/
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
   
       
  /**********pk���Ʊ��(u_pk_control)****************/
  create table u_pk_control(
  	 id             	        smallint unsigned not null auto_increment 	,/**id*/  
	 zd_pk						int											,/**�������������id*/
	 zd_dead_flag				int								   default 0,/**������ҵ�������־*/	
	 zd_attack_no				int											,/**����������˳��,���ֶ�Ӱ���ܵ�������ҷ�����Ƶ��*/ 
	 /**��������ҹ������*/
	 bd_pk						int								           ,/**�������������id*/
	 bd_dead_flag				int								  default 0,/**������ҵ�������־*/	 
	 dead_notify			    int								  default 0,/**�Ƿ�֪ͨ�Է�����*/ 
	 drop_exp				    int							      default 0,/**���侭��*/ 
	 attack_map					int									       ,/**ս������id*/
  	 create_time      datetime                                 	           ,/**ս����ʼʱ��*/
  
  primary key(id));
    
    

   
   /*****************************pk֪ͨ(u_pk_notify)*****************************************/
   create table u_pk_notify(
    n_pk                                 smallint unsigned not null auto_increment        , /**id*/
    notifyed_pk                          int                                              , /**��֪ͨ�����id*/
    create_notify_pk                     int                                              , /**����֪ͨ�����id*/ 
    notify_content                       varchar(200)                                     , /**֪ͨ������**/
    notify_type                          int                                              , /**֪ͨ���ͣ�1.�ܵ�����***/
    create_time                          datetime                                         , /**����ʱ��*/
   primary key(n_pk));
   
   
     /**********pk��־��(u_pk_log)****************/
  create table u_pk_log(
  	 id             	        smallint unsigned not null auto_increment 	, /**id*/
	 zd_pk						int											, /**�������������id*/
	 bd_pk						int								            , /**�������������id*/
	 create_time                datetime                                    , /**����ʱ��*/
  primary key(id));
   
   
   
   create table u_wrap_info ( 
   w_pk             smallint unsigned not null auto_increment ,	  /**��ɫ������*/ 
   u_pk             int                                       ,	  /**������Ա��Ϣid*/ 
   p_pk             int                                       ,	  /**��ɫid*/ 
   table_type       int                                       ,   /**��Ʒ��ر�����*/
   goods_type       int                                       ,   /**��Ʒ����*/
   w_id             int                                       ,   /**��ƷID*/
   w_name           varchar(200)                              ,   /**��Ʒ����*/
   w_durability     int                               default 0,  /*�;�*/ 
   w_dura_consume   int                               default 0,  /*�;�����*/
   w_Bonding        int                                        ,  /**��*/ 
   t_fy_xiao        int                               default 0,  /**����������С����*/
   t_fy_da          int                               default 0,  /**��������������*/ 
   t_gj_xiao        int                               default 0,  /**����������С����*/
   t_gj_da          int                               default 0,  /**����������󹥻�*/
   t_mp             int                               default 0,  /**�������Է���*/
   t_hp             int                               default 0,  /**����������Ѫ*/
 
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
   create_time      datetime                                   ,  /**����ʱ��*/
   primary key (w_pk));
   
   
   
   