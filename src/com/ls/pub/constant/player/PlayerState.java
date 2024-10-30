package com.ls.pub.constant.player;

/**
 * ���״̬������
 * @author ��˧
 * 1:58:44 PM
 */
public class PlayerState {

	/**
	 * ƽ��״̬
	 */
	public static final int GENERAL = 1;
	/**
	 * npcս��״̬
	 */
	public static final int NPCFIGHT = 2;
	/**
	 * ����״̬
	 */
	public static final int TRADE = 3;
	/**
	 * ���״̬
	 */
	public static final int GROUP = 4;
	/**
	 * ����״̬
	 */
	public static final int OUTLINE = 5;
	/**
	 * pkս��״̬
	 */
	public static final int PKFIGHT = 6;
	/**
	 * ս��״̬
	 */
	public static final int FIGHT = 7;
	/**
	 * ˽��
	 */
	public static final int TALK = 8;
	
	/**
	 * ���̳�ʱ�ܱ���
	 */
	public static final int MALL = 9;
	/**
	 * ��̳ʱ�ܱ���
	 */
	public static final int FORUM = 10;
	/**
	 * ����״̬,Ŀǰ����pk���ȡ��Ʒʱ�����䲻������,
	 * ����Ҳ��Ӱ�������pkʤ����ʧ����Ϣ
	 */
	public static final int EXTRA = 11;
	/**
	 * ϵͳ�������͵���ʽ��Ϣ
	 */
	public static final int SYSMSG = 12;
	/**
	 * �ڰ�������
	 */
	public static final int VIEWWRAP = 13;
	/*******����״̬*********/
	public static final int BOX = 13;
	
	/**
	 * ����״̬����Ӧ������
	 * @param state
	 * @return
	 */
	public String returnStateName(int state){
		String hint = "ս��";
		if(state == PlayerState.GENERAL){
			return hint = "ƽ��״̬";
		}
		if(state == PlayerState.NPCFIGHT){
			return hint = "ս��";
		}
		if(state == PlayerState.TRADE){
			return hint = "����";
		}
		if(state == PlayerState.GROUP){
			return hint = "���";
		}
		if(state == PlayerState.OUTLINE){
			return hint = "����";
		}
		if(state == PlayerState.PKFIGHT){
			return hint = "ս��";
		}
		if(state == PlayerState.FIGHT){
			return hint = "ս��";
		}
		if(state == PlayerState.TALK){
			return hint = "˽��";
		}
		if ( state == PlayerState.FORUM) {
			return hint = "��̳";
		}
		if ( state == PlayerState.EXTRA) {
			return hint = "��ȡ��Ʒ";
		}
		if ( state == PlayerState.SYSMSG) {
			return hint = "�鿴��Ϣ";
		}
		if ( state == PlayerState.VIEWWRAP) {
			return hint = "�鿴��Ʒ";
		}
		if ( state == PlayerState.BOX) {
			return hint = "������״̬";
		}
		if ( state == PlayerState.MALL) {
			return hint = "�̳�״̬";
		}
		return hint;
	}
	
}
