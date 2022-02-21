package main;

import java.util.ArrayList;

public class Buzon
{
	private int size;
	private static int totalSize;
	private ArrayList<Mensaje> lstMensajes;
	
	public Buzon(int psize)
	{
		size = psize;
		totalSize += psize;
		lstMensajes = new ArrayList<Mensaje>();
	}
	
	public int inicializarCiclo(int nMensajes, boolean tipoRecepcion, boolean tipoEnvio)
	{
		while (lstMensajes.size() < size & nMensajes > 0)
		{
			synchronized (lstMensajes)
			{
				lstMensajes.add(new Mensaje(tipoRecepcion, tipoEnvio, false));
				nMensajes--;
			}
		}
		
		return nMensajes;
	}
	
	public void enviarPasivo(Mensaje i) {
		while (lstMensajes.size() == size)
		{
			try 
			{
				wait();
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		synchronized (lstMensajes)
		{
			lstMensajes.add(i);
		}
		notifyAll();
	}
	
	public void enviarActivo(Mensaje i) {
		while (lstMensajes.size() == size)
		{
			
		}
		
		synchronized (lstMensajes)
		{
			lstMensajes.add(i);
		}
		notifyAll();
	}
	
	public Mensaje recibirPasivo() 
	{
		while(lstMensajes.size() == 0)
		{
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		Mensaje i;
		synchronized (lstMensajes)
		{
			i = lstMensajes.remove (0);
		}
		
		notifyAll();
		
		return i;
	}
	
	public Mensaje recibirActivo() 
	{
		while (lstMensajes.size() == 0)
		{
			
		}
		
		Mensaje i;
		synchronized (lstMensajes)
		{
			i = lstMensajes.remove(0);
		}
		
		notifyAll();
		
		return i;
	}
	
	public static int getTotalSize()
	{
		return totalSize;
	}
	
	public void imprimirConfiguracion()
	{
		System.out.println("size: " + size);
		System.out.println("nMensajes: " + lstMensajes.size());
	}
	
	public void imprimirMensajes()
	{
		for (int i=0; i<lstMensajes.size(); i++)
		{
			System.out.println("Mensje " + i + ":" + lstMensajes.get(i));
		}
	}
}