/******************************************************************
**
**��SQL�ļ�Ϊ�����¹��� ������Դ,��������,���ɽ���,���ɹ���,���ɾ���,���ɼ���,
**
******************************************************************/

/*************************************��̨��******************************************************/
/*************** �������н�����(tong_build)*********************************/
 create table tong_build(
   tb_id                smallint unsigned not null auto_increment , /**����*/ 
   tb_name              varchar(100)                              , /**��������*/ 
   tb_grade             int                                       , /**�����ȼ�*/
   tb_definition        varchar(200)                              , /**����˵��*/
   tb_group             int                                       , /**����������*/
   tb_map               int                                       , /**�������ڵĵ�ͼ*/
   primary key (tb_id)); 
   
   insert into tong_build values (null,'�㳡',1,'���פ�ع㳡�����Ӹ�����Ὠ��',0,898);
   insert into tong_build values (null,'��ʯ·',1,'������ʯ�壬�Ѿ�ʮ��ƽ��',0,903);
   insert into tong_build values (null,'��ʯ·',1,'������ʯ�壬�Ѿ�ʮ��ƽ��',0,904);
   insert into tong_build values (null,'������',1,'���������ɳ�ʼ����',1,895);
   insert into tong_build values (null,'���',1,'�����ɳ�ʼ����',1,893);
   insert into tong_build values (null,'ľ�ϳ�',1,'ľ�ϳ�',2,894);
   insert into tong_build values (null,'������',1,'������',2,900);
   insert into tong_build values (null,'ʯ�ϳ�',1,'ʯ�ϳ�',2,897);
   insert into tong_build values (null,'ϰ����',1,'ϰ����',3,896);
   insert into tong_build values (null,'������',1,'������',3,902);
   insert into tong_build values (null,'�ش���',1,'�ش���',3,899);
   insert into tong_build values (null,'��ʥ��',1,'��ʥ��',4,901); 
   
  
/***************���ɽ�������������(tong_build_up)*********************************/
 create table tong_build_up(
   tbu_id                smallint unsigned not null auto_increment , /**����*/ 
   tb_id                 int                                       , /**����id*/
   tb_grade              int                                       , /**�����ȼ�*/
   tb_name               varchar(100)                              , /**����name*/
   tb_money              varchar(100)                              , /**������������Ҫ��Ǯ�ļ���*/
   tbu_prop              varchar(200)                              , /**������������Ҫ�ĵ��� ����ID �ö��ŷָ�*/
   tbu_prop_number       varchar(200)                              , /**������������Ҫ�ĵ������� ���������ö��ŷָ�*/
   primary key (tbu_id));

/***************���ɽ���������(tong_build_condition)*********************************/
 create table tong_build_condition(
   tbc_id                smallint unsigned not null auto_increment , /**����*/ 
   tb_id                 int                                       , /**����id*/
   tb_grade              int                                       , /**�����ȼ�*/
   tb_name               varchar(100)                              , /**����name*/
   tb_money              varchar(100)                              , /**������������Ҫ��Ǯ�ļ���*/
   tbu_prop              varchar(200)                              , /**������������Ҫ�ĵ��� ����ID �ö��ŷָ�*/
   tbu_prop_number       varchar(200)                              , /**������������Ҫ�ĵ������� ���������ö��ŷָ�*/
   primary key (tbc_id));



/******************************************************************
**
**��SQL�ļ�Ϊ�����¹��� ������Դ,��������,���ɽ���,���ɹ���,���ɾ���,���ɼ���,
**
******************************************************************/ 
/*************************************ǰ̨��******************************************************/
/***************�������н�����(u_tong_build)*********************************/
 create table u_tong_build(
   utb_id               smallint unsigned not null auto_increment , /**����*/ 
   t_pk                 int                                       , /**����ID*/
   tb_id                int                                       , /**����id*/
   tb_name              varchar(100)                              , /**��������*/ 
   tb_grade             int                                       , /**�����ȼ�*/
   tb_definition        varchar(200)                              , /**����˵��*/
   tb_group             int                                       , /**����������*/
   tb_map               int                                       , /**�������ڵĵ�ͼ*/
   primary key (utb_id)); 
    
/***************������Դ��Ǯ(u_tong_money)*********************************/
 create table u_tong_money(
   utm_id               smallint unsigned not null auto_increment , /**����*/ 
   t_pk                 int                                       , /**����ID*/
   utm_money            varchar(100)                              , /**����Ǯ�ļ���*/
   utm_wrap_content		int                            default 100, /**������Դ����*/
   utm_wrap_spare		int                            default 100, /**������Դʣ������*/
   primary key (utm_id)); 
 
/**********������Դװ����(u_tong_resource_equip)***************/
create table u_tong_resource_equip ( 
   re_pk            smallint unsigned not null auto_increment ,	  /**������Դװ����*/
   t_pk             int                                       ,   /**����ID*/
   table_type       int                                       ,   /**��Ʒ��ر�����*/
   goods_type       int                                       ,   /**��Ʒ����*/
   w_id             int                                       ,   /**��ƷID*/
   w_name           varchar(200)                              ,   /**��Ʒ����*/
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
   enchant_type		  varchar(50)					  default 0,  /**���ӵ㻯����  */
   enchant_value	  int							  default 0,  /**���ӵ㻯����ֵ */
   w_zj_hp		      varchar(100)                             ,  /**׷����Ѫ */
   w_zj_mp			  varchar(100)                             ,  /**׷������ */	
   w_zj_wxgj		  varchar(100)                             ,  /**׷�ӹ��� */
   w_zj_wxfy	      varchar(100)                             ,  /**׷�ӷ��� */ 
   w_zb_grade		  int									   ,  /**װ���ĵȼ� */	
   primary key (re_pk));
 
/*************************������Դ���߱�(u_tong_resource_prop),ÿ����¼����¼����һ�����********************************/
  create table u_tong_resource_prop(
  	rp_pk	                         smallint unsigned not null auto_increment       ,  /**id*/ 
  	t_pk                             int                                             ,  /**����ID*/
    pg_type                          int                                             ,	/**���������*/ 
    prop_id			                 int                                             ,	/**����id*/
    prop_type						 int                                             ,  /**��������*/
    prop_bonding                     int                                             ,
    prop_protect                     int                                             ,
    prop_isReconfirm                 int                                             ,											
    prop_use_control                 int                                    default 0,  /**���Ƶ����Ƿ���ã�0��ʾ�������ƣ�1��ʾս��ʱ������*/
    prop_name	                     varchar(200)                                    ,  /**��������*/
    prop_price                       int                                             ,  /**������Ǯ*/
    prop_num                         int                                             ,	/**�������������ܳ������Ƹ���*/
    auction_type                     int                                             ,  /**���ߴ洢����*/
    primary key(rp_pk));
    
/*************************������Դ����¼��LOG (u_tong_res_log)********************************/
  create table u_tong_res_log(
  	l_pk	                         smallint unsigned not null auto_increment       ,  /**id*/ 
  	t_pk                             int                                             ,  /**����ID*/
    l_content                        varchar(300)                                    ,	/**��־����*/
    create_time                      datetime                                        ,  /**��־��¼ʱ��*/
    primary key(l_pk));

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
    primary key(en_pk));
    
/**********��Һ���(u_friend)***************/
 create table u_friend(
    f_pk	               int not null auto_increment , /**��Һ���id*/ 
    p_pk	               int                                       , /**���id*/
    fd_pk                  int                                       , /**����ID*/
    fd_name                varchar(100)                              , /**��������*/
    fd_online              int                              default 0, /**�����Ƿ����� 0 ������ 1����*/
    create_time            datetime                                  , /**����ʱ��*/ 
    primary key(f_pk));
    
/**********����(u_embargo)***************/
 create table u_embargo(
    e_pk	               int not null auto_increment               , /**����ID*/ 
    p_pk	               int                                       , /**���id*/
    p_name	               varchar(200)                              , /**�������*/
    begin_time             datetime                                  , /**���Կ�ʼʱ��*/
    end_time               datetime                                  , /**���Խ���ʱ��*/ 
    e_time                 varchar(100)                              , /**����ʱ��*/ 
    primary key(e_pk));

/**********����(u_soulbang)***************/
 create table u_soulbang(
    s_pk	               int not null auto_increment               , /**����ID*/ 
    p_pk	               int                                       , /**���id*/
    pw_pk	               int                                       , /**װ������ID*/
    primary key(s_pk));

/**********IP������(ip_whitelist)***************/
 create table ip_whitelist(
    ip_pk	               int not null auto_increment               , /**������ID*/ 
    ip_begin               varchar(100)                              , /**IP��ʼ*/
    ip_end                 varchar(100)                              , /**IP����*/ 
    primary key(ip_pk));
    
/*********IP������(ip_blacklist)***************/
 create table ip_blacklist(
    ip_pk	               int not null auto_increment               , /**������ID*/ 
    ip_list                varchar(100)                              , /**IP��ʼ*/
    primary key(ip_pk));
    
    
    
    
    
    
    
    
    
    
    
drop database jygame_book;
create database jygame_book;  
use jygame_book;
 /***********************************�鼮����***********************************/ 
  create table book_type(
  	ty_id                  int not null auto_increment                 ,/**�鼮����ID**/
  	ty_name                varchar(100)                                ,/**��������**/
  	primary key (ty_id));
  
 /***********************************�鼮��Ϣ��***********************************/
  create table book_info(
  	book_id                int not null auto_increment                 ,/**�鼮ID**/
  	ty_id                  int                                         ,/**�鼮����**/
  	book_name              varchar(100)                                ,/**�鼮����**/
  	author                 varchar(20)								   ,/**С˵����**/					
  	book_line              varchar(3000)                               ,/**�鼮����**/
  	book_stow              int                               default  0,/**С˵�ղش���**/
  	read_count             int                               default  0,/**С˵�Ķ�����**/ 
  	primary key (book_id));
  	
 /***********************************�鼮�½�***********************************/
  create table book_chapter(
  	ch_id                  int not null auto_increment                 ,/**�鼮�½�id**/
  	ch_name                varchar(20)                                 ,/**�½�����**/
  	book_id                int                                         ,/**�鼮ID**/
  	new_time               datetime                                    ,/**����ʱ��***/
  	primary key (ch_id));
  	 
 /***********************************�ҵ����***********************************/
  create table book_case(
    ca_id         int not null auto_increment                        ,/**����id*/
	p_pk          int                                                ,/**��ɫPK*/
	u_pk          int                                                ,/**��ɫ�˺�*/
	p_subarea     int                                                ,/**��ɫ������Ϸ����*/
	book_id       int                                                ,/**�û�����Ӧ���鼮���*/
	ch_id         int                                                ,/**��ǩ���***/
	primary key(ca_id));
	
 /***********************************��Ϸ����***********************************/
  create table subarea(
    su_id         int not null auto_increment                        ,/**����id*/
	su_ip         varchar                                            ,/**��Ϸ����IP*/
	su_name       varchar                                            ,/**��Ϸ�����û���*/
	su_paw        varchar                                            ,/**��Ϸ��������*/
	su_type       int                                                ,/**��Ϸ����*/
	primary key(su_id));
	 
 /***********************************�Ķ���¼***********************************/
  create table read_log(
    re_id         int not null auto_increment                        ,/**����id*/
	p_pk          int                                                ,/**���ID*/
	ch_id         int                                                ,/**�½�ID*/
	read_time     datetime                                           ,/**�Ķ�ʱ��***/
	su_type       int                                                ,/**��Ϸ����*/
	primary key(su_id));
	 
 /***********************************������߼�¼***********************************/
  create table role_be_off(
    off_id              int not null auto_increment                        ,/**����id*/
	p_pk                int                                                ,/**���ID*/
	be_off_time         varchar(50)                                        ,/**�ϴ�����ʱ��*/
	already_time        varchar(50)                              default  0,/**����ʱ�����*/
	be_off_exp          varchar(50)                              default  0,/**���߾���*/
	prop_cumulate_time  varchar(50)                              default  0,/**�����ۻ�ʱ�� ���ǿɹ���ȡ�����ʱ��*/
	primary key(off_id));	

 /***********************************���ߵ��ߣ���̨�ı�***********************************/
  create table be_off_prop(
    be_id               int not null auto_increment                        ,/**����id*/
	prop_name           varchar(50)                              default  0,/**��������*/
	prop_display        varchar(500)                                       ,/**��������*/
	prop_money          varchar(50)                              default  0,/**����Ԫ��*/
	prop_time           varchar(50)                              default  0,/**����ʱ�� Сʱ����*/
	primary key(be_id));


 /***********************************�������buff***********************************/
  create table role_be_off_buff(
    b_id                int not null auto_increment                        ,/**����id*/
	p_pk                int                                                ,/**���ID*/
	be_off_time         varchar(50)                              default  0,/**�ϴ�����ʱ��*/
	be_off_exp          varchar(50)                              default  0,/**���߾���*/
	primary key(b_id));	
	
	
	
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
	primary key(ho_id));		

 /***********************************�ƺű�(t_role_honour)***********************************/
  create table t_role_honour(
    ro_id                int not null auto_increment                        ,/**����id*/
    p_pk                 int                                                ,/**��ɫ����*/
    ho_id                int                                                ,/**�ƺ�����*/
    ho_type              int                                                ,/**�ƺ�����*/
    is_reveal            int                                       default 0,/**�Ƿ���ʾ*/
	primary key(ro_id));	
	
 /***********************************��Ա��̨��(vip)***********************************/
  create table vip(
    v_id                 int not null auto_increment                        ,/**����id*/
    v_name               varchar(100)                                       ,/**VIP����*/
    use_time             int                                                ,/**ʹ��ʱ�� Сʱ����*/
    mall_agio            int                                                ,/**�̳��ۿ�*/
    ho_id                int                                                ,/**�ƺ�ID*/
    is_die_drop_exp      int                                       default 0,/**�����ǲ�����ʧ���� 0 ��ʧ 1����ʧ*/
    v_hint               varchar(600)                                       ,/**VIP˵��*/
	primary key(v_id));

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
	
/**********����Ա�˳�����(u_tong_member_out)***************/
create table u_tong_member_out(
    o_pk	               int not null auto_increment               , /**id*/ 
  	t_pk 	               int                                       , /**���id*/ 
  	p_pk                   int                                       , /**��ԱID*/
    out_time               datetime                                  , /**�˳����ʱ��*/ 
    primary key(o_pk));
