package manager;

import java.util.ArrayList;

import step1.dto.fila;

public class filaManager
{
	private static ArrayList<fila> total;
	private static ArrayList<fila> cajas;
	private static ArrayList<fila> packs;
	private static ArrayList<fila> unidades;
	private static ArrayList<fila> errores;
	
	static
	{
		total		= new ArrayList<>();
		cajas		= new ArrayList<>();
		packs		= new ArrayList<>();
		unidades	= new ArrayList<>();
		errores		= new ArrayList<>();
	}
	
	public static void addCaja(fila fila)
	{
		cajas.add(fila);
		total.add(fila);
	}
	
	public static void addPack(fila fila)
	{
		packs.add(fila);
		total.add(fila);
	}
	
	public static void addUnidad(fila fila)
	{
		unidades.add(fila);
		total.add(fila);
	}
	
	public static void addError(fila fila)
	{
		errores.add(fila);
		total.add(fila);
	}
	
	public static ArrayList<fila> getTotal()
	{
		return total;
	}
	
	public static ArrayList<fila> getCajas()
	{
		return cajas;
	}
	
	public static ArrayList<fila> getPacks()
	{
		return packs;
	}
	
	public static ArrayList<fila> getUnidades()
	{
		return unidades;
	}
	
	public static ArrayList<fila> getErrores()
	{
		return errores;
	}
}
