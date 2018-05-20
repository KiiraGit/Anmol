package step2.bussiness;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
	private XSSFWorkbook	wb;
	private XSSFSheet		sh;

	public step2()
	{
		excelHelper = new ExcelFormat();
	}

	public void selectFile()
	{
		
	}
}
