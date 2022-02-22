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
				Mensaje m = new Mensaje(tipoRecepcion, tipoEnvio, false);
				lstMensajes.add(m);
				nMensajes--;
			}
		}
		
		return nMensajes;
	}
	
	public void enviarPasivo(Mensaje i) {
		synchronized (lstMensajes)
		{
			while (lstMensajes.size() == size)
			{
				try 
				{
					lstMensajes.wait();
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		
			lstMensajes.add(i);
			
			lstMensajes.notifyAll();
		}
	}
	
	public void enviarActivo(Mensaje i) {
		while (lstMensajes.size() == size)
		{
			Thread.yield();
		}
		
		synchronized (lstMensajes)
		{
			lstMensajes.add(i);
			
			lstMensajes.notifyAll();
		}
	}
	
	public Mensaje recibirPasivo() 
	{
		Mensaje i;
		synchronized (lstMensajes)
		{
			while(lstMensajes.size() == 0)
			{
				try
				{
					lstMensajes.wait();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		
			i = lstMensajes.remove (0);
			
			lstMensajes.notifyAll();
		}
		
		return i;
	}
	
	public Mensaje recibirActivo() 
	{
		Mensaje i;
		while (lstMensajes.isEmpty())
		{
			Thread.yield();
		}
		
		synchronized (lstMensajes)
		{
			i = lstMensajes.remove(0);
		
			lstMensajes.notifyAll();
			
			return i;
		}
	}
	
	public static int getTotalSize()
	{
		return totalSize;
	}

	public ArrayList<Mensaje> getLstMensajes()
	{
		return lstMensajes;
	}
	
	public int getSize()
	{
		return size;
	}
}