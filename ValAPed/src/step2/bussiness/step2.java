package step2.bussiness;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import FileHelpers.ExcelFormat;
import Util.Util;
import manager.filaManager;
import step1.dto.fila;

public class step2
{

	private ExcelFormat		excelHelper;
	private File			file;
	private XSSFWorkbook	in_wb;
	private XSSFSheet		in_sh;
	private XSSFWorkbook	out_wb;
	private XSSFSheet		out_sh;

	public step2()
	{
		excelHelper = new ExcelFormat();
	}

	public void selectOpenFile()
	{
		file = Util.openFile("./resources/", "Selecciona el archivo", excelHelper);

		in_wb = excelHelper.Leer(file);
		in_sh = in_wb.getSheetAt(0);

		parseFile();
	}

	private void parseFile()
	{
		Iterator<Row> rowIterator = in_sh.iterator();

		// Colocamos el iterador en la fila 4 que es la primera que tiene
		// valores
		rowIterator.next();
		rowIterator.next();
		rowIterator.next();

		while (rowIterator.hasNext())
		{
			Row row = rowIterator.next();

			Cell refCell = row.getCell(0);
			Cell descCell = row.getCell(1);
			Cell udsCell = row.getCell(4);
			Cell precioCell = row.getCell(5);
			Cell valCell = row.getCell(6);

			try
			{
				String refVal = excelHelper.getStringCellVal(refCell);
				String descVal = excelHelper.getStringCellVal(descCell);
				int udsVal = excelHelper.getNumericCellVal(udsCell);
				double precioVal = excelHelper.getRealNumericCellVal(precioCell);
				double valVal = excelHelper.getRealNumericCellVal(valCell);

				new fila(refVal, descVal, udsVal, precioVal, valVal);
			} catch (NullPointerException ex)
			{
				JOptionPane.showMessageDialog(null,
						"Hoja leída correctamente. (" + filaManager.getTotal().size() + " filas)" + "\nCajas: "
								+ filaManager.getCajas().size() + "\nUnidades: " + filaManager.getUnidades().size()
								+ "\nErrores: " + filaManager.getErrores().size());
			}
		}
	}

	public void selectSaveFile()
	{
		file = Util.saveFile("Nombre del archivo", excelHelper);

		out_wb = new XSSFWorkbook();
		out_sh = out_wb.createSheet();

		writeFile();

		excelHelper.Grabar(out_wb, file.getAbsolutePath());
	}

	public void writeFile()
	{
		int totalRealUds = 0;
		double totalRealVal = 0;
		
		HashMap<String, XSSFFont> fonts = createFonts(out_wb);

		Row columnsHeader = out_sh.createRow(3);
		Cell eanHeader = columnsHeader.createCell(0);
		Cell rawDescHeader = columnsHeader.createCell(1);
		Cell refHeader = columnsHeader.createCell(2);
		Cell colorHeader = columnsHeader.createCell(3);
		Cell tallaHeader = columnsHeader.createCell(4);
		Cell udsHeader = columnsHeader.createCell(5);
		Cell precioHeader = columnsHeader.createCell(6);
		Cell subtotalHeader = columnsHeader.createCell(7);
		Cell totalHeader = columnsHeader.createCell(8);

		CellStyle style = columnsHeader.getRowStyle();
		if (style == null)
		{
			style = out_wb.createCellStyle();
			columnsHeader.setRowStyle(style);
		}
	    style.setFillBackgroundColor(IndexedColors.ORANGE.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    style.setAlignment(CellStyle.ALIGN_CENTER);
	    style.setFont(fonts.get("title"));

		eanHeader.setCellValue("EAN");
		rawDescHeader.setCellValue("DESCRIPCION");
		refHeader.setCellValue("REF");
		colorHeader.setCellValue("COLOR");
		tallaHeader.setCellValue("TALLA");
		udsHeader.setCellValue("STOCK");
		precioHeader.setCellValue("PRECIO");
		subtotalHeader.setCellValue("SUBTOTAL STOCK");
		totalHeader.setCellValue("TOTAL");

		int i = 4;
		// Escribimos las unidades
		for (fila fila : filaManager.getUnidades())
		{
			Row columnsValue = out_sh.createRow(i);
			i++;
			
			style = columnsValue.getRowStyle();
			if (style == null)
			{
				style = out_wb.createCellStyle();
				columnsValue.setRowStyle(style);
			}
		    style.setFont(fonts.get("base"));

			Cell eanCell = columnsValue.createCell(0);
			Cell rawDescCell = columnsValue.createCell(1);
			Cell refCell = columnsValue.createCell(2);
			Cell colorCell = columnsValue.createCell(3);
			Cell tallaCell = columnsValue.createCell(4);
			Cell udsCell = columnsValue.createCell(5);
			Cell precioCell = columnsValue.createCell(6);
			Cell subtotalCell = columnsValue.createCell(7);

			eanCell.setCellValue(fila.getEan());
			refCell.setCellValue(fila.getRef());
			rawDescCell.setCellValue(fila.getRawDesc());
			colorCell.setCellValue(fila.getColor());
			tallaCell.setCellValue(fila.getTalla());

			int stockReal = fila.getStockReal();
			double valReal = fila.getValReal();
			udsCell.setCellValue(stockReal);
			precioCell.setCellValue(fila.getPrecio());
			subtotalCell.setCellValue(valReal);

			totalRealUds += stockReal;
			totalRealVal += valReal;
		}

		Row totalsValue = out_sh.createRow(i);
		i++;
		
		style = totalsValue.getRowStyle();
		if (style == null)
		{
			style = out_wb.createCellStyle();
			totalsValue.setRowStyle(style);
		}
	    style.setFont(fonts.get("baseBold"));
		
		Cell udsValue = totalsValue.createCell(5);
		Cell valValue = totalsValue.createCell(7);
		udsValue.setCellValue(totalRealUds);
		valValue.setCellValue(totalRealVal);

		// Escribimos la valoracion en las lineas 2 y 3
		totalsValue = out_sh.createRow(1);

		style = totalsValue.getRowStyle();
		if (style == null)
		{
			style = out_wb.createCellStyle();
			totalsValue.setRowStyle(style);
		}
	    style.setFont(fonts.get("subtitle"));
	    
	    Row row = out_sh.getRow(4);
		Cell totalValue = row.createCell(8);
		totalValue.setCellValue(totalRealVal);

		out_sh.autoSizeColumn(0);
		out_sh.autoSizeColumn(1);
		out_sh.autoSizeColumn(2);
		out_sh.autoSizeColumn(3);
		out_sh.autoSizeColumn(4);
		out_sh.autoSizeColumn(5);
		out_sh.autoSizeColumn(6);
		out_sh.autoSizeColumn(7);
		out_sh.autoSizeColumn(8);
	}

	private HashMap<String, XSSFFont> createFonts(XSSFWorkbook wb)
	{
		HashMap<String, XSSFFont> fonts = new HashMap<>();
		
		XSSFFont base = wb.createFont();
		base.setFontHeightInPoints((short)12);
		base.setFontName("Calibri");
		base.setColor(IndexedColors.BLACK.getIndex());
		base.setBold(false);
		base.setItalic(false);

	    XSSFFont title = wb.createFont();
	    title.setFontHeightInPoints((short)16);
	    title.setFontName("Calibri");
	    title.setColor(IndexedColors.BLACK.getIndex());
	    title.setBold(true);
	    title.setItalic(false);

	    XSSFFont subtitle = wb.createFont();
	    subtitle.setFontHeightInPoints((short)14);
	    subtitle.setFontName("Calibri");
	    subtitle.setBold(true);
	    subtitle.setItalic(false);

	    XSSFFont baseBold = base;
		baseBold.setBold(true);

	    fonts.put("base", base);
	    fonts.put("title", title);
	    fonts.put("subtitle", subtitle);
	    fonts.put("baseBold", baseBold);
	    
	    return fonts;
	}

}
