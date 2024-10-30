create database jygame_user;  


/**********�ʼ���(u_mail_info)***************/

create table u_mail_info ( 
	mail_id 		mediumint unsigned not null auto_increment 		,	/*** �ʼ���id **/	
	receive_pk 		int												,	/**  ������id **/
	send_pk			int 											,	/**	 ������id **/
	mail_type		int												,	/**  �ʼ����ͣ�1Ϊ��ͨ��2Ϊϵͳ**/
	title 				varchar(200)									, 	/**  ����  **/
	content 			varchar(2000)									,	/**  �ʼ�����  **/
	unread				int												,	/**	 �Ƿ��Ķ�����1Ϊδ����2δ�Ѷ� **/
	improtant			int												,	/**  ��Ҫ�ԣ�����ҿ�ʱ���Դ�����������Ĭ��Ϊ1������Խ��Խ��ǰ  **/		
	create_time			datetime										,	/**	 �ʼ�����ʱ�䣬����Ҫ������һ�µ�������Դ�����  ***/
	primary key (mail_id));
	
	/**********������������(u_auction_pet)***************/ 
create table u_auction_pet(
	auction_id		 mediumint unsigned not null auto_increment ,/** ����������id */
	auction_status	 int 										,/** ��������״̬��1Ϊ����������2Ϊ����ʧ��,�ȴ�ȡ�أ�3Ϊ���δȡ��,�ȴ�ɾ����4Ϊ�����ɹ����ȴ����ȡ�ؽ�Ǯ. */
	pet_price		 int 										,/** ���������۸�  */
	pet_auction_time datetime									,/** ��������ʱ�� */	
	pet_pk			 int									   , /** ��ɫ����ID */
	
	p_pk			 int 					                   , /**��ɫid*/
	pet_id			 int					                   , /**��Ӧpet�����id*/
	pet_name		 varchar(200)				               , /**��������*/
	pet_nickname	 varchar(200)				               , /**�����ǳ�*/
	pet_grade		 int					                   , /**�ȼ�*/
	
	pet_exp			 varchar(200)			                   , /**����*/
	pet_ben_exp		 varchar(200)			          default 0, /**��ǰ���龭��*/
	pet_xia_exp		 varchar(200)                              , /**�¼�����ﵽ��һ����Ҫ�ľ���*/
	pet_gj_xiao      int                     		           , /**��С����*/
	pet_gj_da        int					                   , /**��󹥻�*/
	
	pet_sale		 int			                           , /**�����۸�*/
	pet_img			 varchar(200)                              , /**����ͼƬ*/
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
 primary key (auction_id));
 
   /*********����������Ϣ��(u_auctionpet_info)******************************/
  create table u_auctionpet_info(
  	auctionpet_info_id			smallint unsigned not null auto_increment , /**����������Ϣid */
  	p_pk						int 									 ,	/** ���˽�ɫid */
  	auction_pet_info			varchar(200)							 ,	/** ����������Ϣ��ʾ */
  	addInfoTime					datetime 								,	/** ������Ϣʱ�� */
  	primary key(auctionpet_info_id));
  	
  	/*********ϵͳ���ñ�(s_setting_info)******************************/
  	create table s_setting_info (
  		setting_id			smallint unsigned not null auto_increment,	/** ϵͳ���ñ� */
  		p_pk				int										,	/** �����ɫid */
  		goods_pic			tinyint									,	/**  ��Ʒͼ����,1Ϊ������0Ϊ�ر� */
  		person_pic			tinyint									,	/**	 ��ɫ����ͼ����,1Ϊ������0Ϊ�ر� */
  		npc_pic				tinyint									,	/**  npc����ͼ����,1Ϊ������0Ϊ�ر� */
  		pet_pic				tinyint									,	/**  ����ͼ����,1Ϊ������0Ϊ�ر� */
  		operate_pic			tinyint									,	/**  npc����ͼ����,1Ϊ������0Ϊ�ر� */
  		deal_control		tinyint									,	/**  ���׿��ƿ���,1Ϊ������0Ϊ�ر� */
  	primary key(setting_id));
  		
 /**********��ɫ�ֿ�װ����(u_warehouse_equip)***************/
create table u_warehouse_equip ( 
   w_pk		        smallint unsigned not null auto_increment ,	  /**��ɫװ���ֿ��*/
   p_pk             int                                       ,	  /**��ɫid*/ 
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
   
   w_wx_type        int                                        ,  /** װ����������*/
   suit_id			int								  default 0,	/** ��װid */
   w_buff_isEffected  int                             default 0,  /**����buff�Ƿ���Ч��0��ʾ��Ч��1��ʾ��Ч*/
   create_time      datetime                                   ,  /** ����ʱ��*/ 
   primary key (w_pk)); 		
  			

   
   
   /** pkֵ������־ (u_pkvalue_elimination)*/
   create table u_pkvalue_elimination(
   elimi_id 		int unsigned not null auto_increment		,	/**pkֵ������id */
   p_pk				int											,	/**��ɫid*/
   pk_value			int											,	/**pkֵ*/
   is_prisonArea	int 										,	/**�Ƿ��ڼ�������1Ϊ���ڣ�2Ϊ��*/
   last_time		datetime									,	/**�ϴ��������ֵ��ʱ��*/   
    primary key (elimi_id));   

   
  /*************ϵͳ��Ϣ���Ʊ�(u_systeminof_control)*********/
  	create table u_systeminof_control(
  control_id		 int unsigned not null auto_increment		,	    /**  ϵͳ������Ϣ��id */
  condition_type 	 int 										,		/** ������������,1Ϊ��ҵȼ�,2Ϊ����,3Ϊ����,4Ϊ��ν,5Ϊ����ʱ��.  */
  player_grade		 int						   default 0	,		/**  ��ҵȼ� */
  task_id			 int 						   default 0	,		/**  ����id */
  popularity		 int 						   default 0	,		/**  ���� */
  title 			 varchar(50) 					   			,		/**   ��ν */
  send_time		     datetime									,		/**   ����ʱ�� */
  send_content		varchar(1000)								,		/**  ��������	*/
  primary key(control_id));			
  
  
  
  /**********************************************************************************************************************************/
  
  
  
  
  
  
  
  
  
  /***********������Ҽ�¼��*********************/
  create table p_record_login(
  id				int unsigned not null auto_increment		,	    /**  ������Ҽ�¼��id */	
  u_pk				int											,		/**  ���id  */
  loginStatus		int											,		/**  ��½״̬ */
  loginTime			datetime									,		/**  �������ʱ��  */	
  primary key(id));
  
 
 /***********������ҽ�ɫ��¼��*********************/
  create table user_record_login(
  id				int unsigned not null auto_increment		,	    /**  ������Ҽ�¼��id */	
  p_pk				int											,		/**  ��ҽ�ɫid  */
  p_grade			int											,		/**  ��ҵȼ�   **/
  loginStatus		int											,		/**  ��½״̬ */
  loginTime			datetime									,		/**  �������ʱ��  */	
  primary key(id));
  	
  	
 
  /*************** �������ʱ���� ***********************************/
  	create table user_online_time(
  		id			int unsigned not null auto_increment		,	/**  �������ʱ����id **/
  		u_pk		int											,	/*** ���id ***/
  		p_pk		int											,	/**  ��ɫid  **/
  		onlinetime	int											,	/*** ����ʱ�䳤�� **/
  		createTime	datetime									,	/**  ����ʱ�� **/	
  	 primary key(id));
  	 
  	 
  
  /***********������ҵȼ���*********************/
  create table user_login_grade(
  	id 				int unsigned not null auto_increment		,	/** ������ҵȼ���id **/
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
  	id 				int unsigned not null auto_increment		,	/**  ��Ĭ��ҵȼ���id **/
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
  		id				int unsigned not null auto_increment		,	/** �ճ��ȼ���id **/
  		grade1			int											,	/**  �ȼ�Ϊ1�ĵȼ����� */	
  		grade2to9		int											,	/**  �ȼ�2��9������� **/
  		grade10to19		int											,	/**  �ȼ�10��19�������**/
  		grade20to29		int											,	/**  �ȼ�20��29�������**/
 		grade30to39		int											,	/**  �ȼ�30��39�������**/
   		grade40to49		int											,	/**  �ȼ�40��49������� **/
  		grade50to59		int											,	/**  �ȼ�50��59������� **/
    	grade60			int											,	/**  �ȼ�60������� **/
  		recordTime		varchar(50)									,	/*** ����¼���� **/
  		createTime	datetime										,	/**  ����ʱ�� **/	
  	 primary key(id));	
  							
  		
  																																		
   /*************** �������ʱ��α� ***********************************/
  	create table user_online_num(
  		id				int unsigned not null auto_increment		,	/** �ճ��ȼ���id **/
  		hour_time			int										,	/**	һ���е�ʱ **/	
  		player_num			int										,	/**	һ���е�ʱ���������� **/
  		record_time		varchar(50)									,	/** ��¼���� */
  		insert_time		datetime									,	/** ����ʱ�� */ 
  	 primary key(id));
  	
  	
  	/*************** �����Ϣ������ ***********************************/
  	create table user_info_overview(
  		id						int unsigned not null auto_increment		,	/** �ճ��ȼ���id **/
  		all_regist_num			int											,	/** ��ע������ **/	
  		all_regist_user			int											,	/**	��ע���ɫ  **/
  		today_regist_num		int											,	/** ����ע������ */
  		today_online_num		int											,	/** ������������ */
  		today_active_num		int											,	/** ���ջ�Ծ���� */
  		today_avg_time 			int											,	/** ����ƽ������ʱ�� */
  		today_avg_grade 		int											,	/** ����ƽ�����ߵȼ� */
  		today_avg_num			int											,	/** ����ƽ���������� */
  		insert_time		datetime											,	/** ����ʱ�� */ 
  	 primary key(id));
  	 
  	 
  	 
  	 
  	 
  	 
  	 
  	
  	 
  	 
  	 /*****************   ���ɹ���ս��  (��̨)   ***************/
  	 create table tong_siege_battle (
  	 		siege_id			int unsigned not null auto_increment  ,		/** ����սID,������ĳ�����еĹ���  */
  	 		siege_name			varchar(100)						 						,		/**  ����ս�������� ***/
  	 		map_id				int 														,		/*** ���ɹ���ս����Ӧ��map_type  **/
  	 		affect_map_id  int														,		/**   Ӱ��ĵ�ͼMAP_ID,�ڴ�ID �ڵ������ܴ˳��й�Ͻ,�����һ��, �Զ������� ***/
  	 		tax					int														,		/*****   �˹���ս�������������˰��ˮƽ,��1��10֮��   *****/
  	 		tax_money		int														,		/**      �˹���ս�������������˰��      **/
  	 		out_scene		int														,		/****   ǿ�ƴ���ʱ�ĵص�    */		
  	 		relive_scene	varchar(20)											,		/****   ��Ҹ����,1Ϊ���Ƿ�,2Ϊ�سǷ���.    */		
  	  primary key(siege_id));		  	 		
  	 
  	 
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
  	 
  	 
  	 
  	 
  	 
  	 /***  ���ͱ�  ****/
  	  create table carry_table_info (
  	  		carry_id			int unsigned not null auto_increment  ,		/** ���ͱ�ID  */
  	  		carry_type_id		int														,		/***  �ص�����   ****/	
  	  		carry_type_name		varchar(50)												,		/***  �ص��������� **/	
  	  		scene_id			int														,		/**   �ص�id    **/
  	  		scene_name  		varchar(50)												,		/***  �ص�����  ****/
  	  		carry_grade			int														,		/**   �ص㴫�͵ȼ�  **/
  	 primary key(carry_id));
  	 
  	 
  	  /***  �˺���¼��  ****/
  	  create table injure_recond_info (
  	  		injure_id			int unsigned not null auto_increment 					 ,		/** �˺���¼��ID  */
  	  		tong_id				int																,		/***  ����ID   ****/	
  	  		injure_number		int(50)													,		/***  �˺���ֵ **/	
  	  		npc_ID				int														,		/**   NPC_id    **/
  	  		npc_Type  			smallint												,		/***  NPC_type,6����Ӣ�۵���  ****/
  	 primary key(injure_id));
  	 
  	 
  	   	  /***  װ��չʾ��  ****/
  	  create table zb_relela_info (
  	  		relela_id			int unsigned not null auto_increment 					 ,		/** �˺���¼��ID  */  	  		
  	  		pwpk		int(11)													,		/***  �˺���ֵ **/	
  	  		relelavar			varchar(200)								,		/**   װ��չʾ�ַ�    **/
  	  		relelatime			datetime												,		/**    װ��չʾʱ��   ****/
  	 primary key(relela_id));
  	 
  	 
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
  	  		
  	  		
  	  		give_goods			varchar(100)												,		/**    ����װ������Ʒ, ��","�ָ�, �Ͷһ��˵�������������ͬ    ****/
  	  		isuseable			int(3)											default 1		,		/**   �Ƿ���Ч,Ϊ���ʾ��Ч,���ᱻ��ʾ����. Ϊ1�Ļ��������ʾ  **/
  	  		
  	  		horta_display		varchar(100)												,		/**   ��������  **/
  	 primary key(horta_id));
  	 
  	 
  	 
  	 
  	 
  	 
  	 
  	 
  	 
  	 
  																								