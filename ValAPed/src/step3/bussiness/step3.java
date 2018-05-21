package step3.bussiness;

import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import FileHelpers.TxtFormat;
import Util.Util;
import common.step;
import manager.filaManager;
import step3.dto.filaPedido;
import step3.manager.filaPedidoManager;

public class step3 extends step
{
	private TxtFormat txtHelper;

	//	Step1 Convierte el pedido excel a txt
	public step3()
	{
		super();
		txtHelper = new TxtFormat();
	}

	@Override
	public void selectSaveFile()
	{
		file = Util.saveFile("Nombre del archivo", txtHelper);

		txtHelper.Save(file, filaPedidoManager.getPedidoTxt());
	}

	@Override
	protected void parseFile()
	{
		Iterator<Row> rowIterator = in_sh.iterator();

		// Colocamos el iterador en la fila 7 que es la primera que tiene
		// valores
		rowIterator.next();
		rowIterator.next();
		rowIterator.next();
		rowIterator.next();
		rowIterator.next();
		rowIterator.next();

		while (rowIterator.hasNext())
		{
			Row row = rowIterator.next();

			Cell eanCell = row.getCell(0);
			Cell udsCell = row.getCell(9);

			try
			{
				String eanfVal = excelHelper.getStringCellVal(eanCell);
				int udsVal = excelHelper.getNumericCellVal(udsCell);

				new filaPedido(eanfVal, udsVal);
			} catch (NullPointerException ex)
			{
				JOptionPane.showMessageDialog(null,
						"Hoja leída correctamente. (" + filaManager.getTotal().size() + " filas)" + "\nCajas: "
								+ filaManager.getCajas().size() + "\nUnidades: " + filaManager.getUnidades().size()
								+ "\nErrores: " + filaManager.getErrores().size());
			}
		}
	}

	@Override
	public void writeFile()
	{
		
	}
}
