package com.ben.jms;

public class QSQ implements Comparable<QSQ>
{
	private String super_qudao;
	private String qudao;

	public QSQ(String super_qudao, String qudao)
	{
		super();
		this.super_qudao = super_qudao;
		this.qudao = qudao;
	}

	public String getSuper_qudao()
	{
		return super_qudao;
	}

	public void setSuper_qudao(String super_qudao)
	{
		this.super_qudao = super_qudao;
	}

	public String getQudao()
	{
		return qudao;
	}

	public void setQudao(String qudao)
	{
		this.qudao = qudao;
	}

	public int compareTo(QSQ o)
	{
		if (o.getQudao() != null && o.getQudao().equals(this.qudao)
				&& o.getSuper_qudao() != null
				&& o.getSuper_qudao().equals(this.super_qudao))
			return 0;
		else
			return -1;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((qudao == null) ? 0 : qudao.hashCode());
		result = prime * result
				+ ((super_qudao == null) ? 0 : super_qudao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final QSQ other = (QSQ) obj;
		if (qudao == null)
		{
			if (other.qudao != null)
				return false;
		}
		else
			if (!qudao.equals(other.qudao))
				return false;
		if (super_qudao == null)
		{
			if (other.super_qudao != null)
				return false;
		}
		else
			if (!super_qudao.equals(other.super_qudao))
				return false;
		return true;
	}
	
	

}
