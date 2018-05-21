package step3.dto;

import step3.manager.filaPedidoManager;

public class filaPedido
{
	private String	ean;
	private int		uds;

	public filaPedido(String ean, int uds)
	{
		this.ean = ean;
		this.uds = uds;
		
		filaPedidoManager.addFila(this);
	}

	public String getEan()
	{
		return ean;
	}

	public void setEan(String ean)
	{
		this.ean = ean;
	}

	public int getUds()
	{
		return uds;
	}

	public void setUds(int uds)
	{
		this.uds = uds;
	}

	public String toString()
	{
		return ean + ", " + uds;
	}

	public boolean isValid()
	{
		boolean eanOk = (ean != null && !ean.isEmpty());
		boolean udsOk = (uds > 0);
		
		return eanOk && udsOk;
	}
}
