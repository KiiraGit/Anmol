package step1.dto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import manager.filaManager;

public class fila
{
	private String	ean;
	private String	ref;
	private String	desc;
	private String	rawDesc;
	private String	talla;
	private String	color;
	private int		uds;
	private double	precio;
	private double	val;
	private int 	udsBox;
	private String	tipoEnvase;
	
	private int		stockReal;
	private double	valReal;
	
	public fila(String ean, String desc, int uds, double precio, double val)
	{
		this.ean		= ean;
		this.uds		= uds;
		this.precio		= precio;
		this.val		= val;
		this.rawDesc	= desc;
		
		extractDescData(desc);
	}
	
	private void extractDescData(String desc)
	{
		final String TIPO_ENVASE_PATTERN = "^[A-Z]{4,6} \\d{0,2}";
		final String NUM_ENVASE_PATTERN = "\\d{1,2}$";
		final String MARCA_REF_PATTERN = "^[A-Z].*\\d";
		final String REF_ENVASE_PATTERN = "\\d{1,4}$";
		
		desc = desc.toUpperCase();
		
		Pattern pattern = null;
		Matcher matcher = null;
		/**
		 * 	PATRONES REGEX
		 * 	^[A-Z]{4,6} \d{0,2}			->		CAJA 12
		 * 	^[A-Z].*\d					->		NAIARA 765
		 */
		pattern = Pattern.compile(TIPO_ENVASE_PATTERN);
		matcher = pattern.matcher(desc);
		
		if (matcher.find())
		{
			tipoEnvase = matcher.group(0).trim();
			System.out.println("TIPO ENVASE: " + tipoEnvase);
			desc = desc.replace(tipoEnvase, "").trim();
			
			pattern = Pattern.compile(NUM_ENVASE_PATTERN);
			matcher = pattern.matcher(tipoEnvase);
			
			if (matcher.find())
			{
				int num = Integer.parseInt(matcher.group(0));
				udsBox = num;
			}
			System.out.println("UDS: " + udsBox);
		}
		else
		{
			filaManager.addError(this);
			return;
		}
		
		pattern = Pattern.compile(MARCA_REF_PATTERN);
		matcher = pattern.matcher(desc);
		
		if (matcher.find())
		{
			String marcaRef = matcher.group(0).trim();
			
			System.out.println("MARCA REF: " + marcaRef);
			desc = desc.replace(matcher.group(0), "").trim();
			
			pattern = Pattern.compile(REF_ENVASE_PATTERN);
			matcher = pattern.matcher(marcaRef);
			
			if (matcher.find())
			{
				ref = matcher.group(0);
				System.out.println("REF: " + ref);
			}
		}
		else
		{
			filaManager.addError(this);
			return;
		}
		
		int wsPos = desc.lastIndexOf(" ");
		if (wsPos > -1)
		{
			//	Se extrae la talla de la ultima palabra
			String talla = desc.substring(wsPos).trim();
			desc = desc.substring(0, wsPos).trim();
			
			setTalla(talla);
			System.out.println("TALLA: " + talla);
		}
		
		wsPos = desc.lastIndexOf(" ");
		if (wsPos > -1)
		{
			//	Se extrae el color de la ultima palabra
			String color = desc.substring(wsPos).trim();
			desc = desc.substring(0, wsPos).trim();
			
			setColor(color);
			System.out.println("COLOR: " + color);
		}
		
		System.out.println("\n");
		System.out.println("DESC: " + desc);
		System.out.println("RAW: " + rawDesc);

		if (udsBox > 0)
		{
			if (tipoEnvase.startsWith("CAJA"))
			{
				filaManager.addCaja(this);
			}
			else if (tipoEnvase.startsWith("PACK"))
			{
				filaManager.addPack(this);
			}
			else
			{
				filaManager.addError(this);
				return;
			}
		}
		else
		{
			filaManager.addUnidad(this);
			System.out.println(filaManager.getTotal().indexOf(this) + 1 + 3);
		}
		System.out.println("\n\n");
	}
	
	public void calculateRealStock()
	{
		stockReal = uds;
		
		for (fila fila : filaManager.getTotal())
		{
			if (!filaManager.getErrores().contains(fila) && !filaManager.getUnidades().contains(fila) && fila.esCajaDe(this))
			{
				System.out.println("STOCK UNIDAD: " + stockReal);
				System.out.println("STOCK CAJA UDS: " + fila.getRealStock());
				stockReal += fila.getRealStock();
			}
		}
		
		if (stockReal < 0)
		{
			stockReal = 0;
		}
		
		setValReal(stockReal * precio);
	}

	private int getRealStock()
	{
		if (tipoEnvase.contains("PACK"))
		{
			return uds * udsBox;
		}
		else if (tipoEnvase.contains("CAJA") || tipoEnvase.contains("UNIDAD"))
		{
			return uds;
		}
		else return 0;
	}

	private boolean esCajaDe(fila fila)
	{
		return esCaja() && fila.mismaRef(this) && fila.mismaTalla(this) && fila.mismoColor(this);
	}

	private boolean esCaja()
	{
		return tipoEnvase.startsWith("CAJA") || tipoEnvase.startsWith("PACK");
	}

	private boolean mismaRef(fila fila)
	{
		return ref.equals(fila.getRef());
	}

	private boolean mismaTalla(fila fila)
	{
		return talla.equals(fila.getTalla());
	}

	private boolean mismoColor(fila fila)
	{
		return color.equals(fila.getColor());
	}

	public String getEan()
	{
		return ean;
	}

	public void setEan(String ean)
	{
		this.ean = ean;
	}

	public String getRef()
	{
		return ref;
	}

	public void setRef(String ref)
	{
		this.ref = ref;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getTalla()
	{
		return talla;
	}

	private void setTalla(String talla)
	{
		this.talla = talla;
	}

	public String getColor()
	{
		return color;
	}

	private void setColor(String color)
	{
		this.color = color;
	}

	public int getUds()
	{
		return uds;
	}

	public void setUds(int uds)
	{
		this.uds = uds;
	}

	public double getPrecio()
	{
		return precio;
	}

	public void setPrecio(double precio)
	{
		this.precio = precio;
	}

	public double getVal()
	{
		return val;
	}

	public void setVal(double val)
	{
		this.val = val;
	}

	public String getRawDesc()
	{
		return rawDesc;
	}

	public void setRawDesc(String rawDesc)
	{
		this.rawDesc = rawDesc;
	}

	public int getUdsBox()
	{
		return udsBox;
	}

	public void setUdsBox(int udsBox)
	{
		this.udsBox = udsBox;
	}

	public int getStockReal()
	{
		return stockReal;
	}

	public void setStockReal(int stockReal)
	{
		this.stockReal	= stockReal;
	}

	public double getValReal()
	{
		return valReal;
	}

	private void setValReal(double valReal)
	{
		this.valReal = valReal;
	}

	public String getTipoEnvase()
	{
		return tipoEnvase;
	}
}
