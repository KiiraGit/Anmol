package common;

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

public abstract class step
{
	protected ExcelFormat		excelHelper;
	protected File				file;
	protected XSSFWorkbook		in_wb;
	protected XSSFSheet			in_sh;

	public step()
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

	public abstract void selectSaveFile();

	protected abstract void parseFile();

	public void calculateStock()
	{
		for (fila fila : filaManager.getUnidades())
		{
			fila.calculateRealStock();
		}
	}

	public abstract void writeFile();

	protected HashMap<String, XSSFFont> createFonts(XSSFWorkbook wb)
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
