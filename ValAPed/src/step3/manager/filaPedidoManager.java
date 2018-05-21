package step3.manager;

import java.util.ArrayList;

import step3.dto.filaPedido;

public class filaPedidoManager
{
	private static ArrayList<filaPedido> total;

	static
	{
		total		= new ArrayList<>();
	}
	
	public static void addFila(filaPedido fila)
	{
		total.add(fila);
	}
	
	public static ArrayList<String> getPedidoTxt()
	{
		ArrayList<String> rows = new ArrayList<>();
		
		for (filaPedido fila : total)
		{
			if (fila.isValid())
			{
				rows.add(fila.toString());
			}
		}
		
		return rows;
	}
}
