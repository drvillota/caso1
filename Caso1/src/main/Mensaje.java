package main;

public class Mensaje
{
	private String texto;
	private int id;
	private static int contadorMensajes = 0;
	
	public Mensaje(boolean tipoRecepcion, boolean tipoEnvio, boolean mensajeFinal)
	{
		id += contadorMensajes++;
		texto = "";
		
		if (!mensajeFinal)
		{
			transformarMensaje(1, tipoRecepcion, tipoEnvio);
		}
		else
		{
			texto += "FIN";
		}
	}
	
	public void transformarMensaje(int id, boolean tipoRecepcion, boolean tipoEnvio)
	{
		texto += id;
		
		if (tipoRecepcion)
		{
			texto += ";RA;";
		}
		else
		{
			texto += ";RP;";
		}
		
		if (tipoEnvio)
		{
			texto += "EA|";
		}
		else
		{
			texto += "EP|";
		}
	}

	public String getTexto()
	{
		return texto;
	}

	public int getId()
	{
		return id;
	}
}
