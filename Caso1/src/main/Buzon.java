package main;

import java.util.ArrayList;

public class Buzon
{
	private int size;
	private int nMensajes;
	private ArrayList<Mensaje> lstMensajes;
	
	public Buzon(int psize)
	{
		size = psize;
		nMensajes = 0;
		lstMensajes = new ArrayList<Mensaje>();
	}
	
	public void imprimirConfiguracion()
	{
		System.out.println("size: " + size);
		System.out.println("nMensajes: " + nMensajes);
	}
	
	public void imprimirMensajes()
	{
		for (int i=0; i<lstMensajes.size(); i++)
		{
			System.out.println("Mensje " + i + ":" + lstMensajes.get(i));
		}
	}
}