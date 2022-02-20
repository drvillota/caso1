package main;

import java.util.ArrayList;

public class Buzon
{
	private int size;
	private static int totalSize;
	private int nMensajes;
	private ArrayList<Mensaje> lstMensajes;
	
	public Buzon(int psize)
	{
		size = psize;
		totalSize += psize;
		nMensajes = 0;
		lstMensajes = new ArrayList<Mensaje>();
	}
	
	public synchronized void enviarPasivo(Mensaje i) {
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
		lstMensajes.add(i);
		notify();
	}
	
	public synchronized void enviarActivo(Mensaje i) {
		while (lstMensajes.size() == size)
		{
			
		}
		lstMensajes.add(i);
		notify();
	}
	
	public synchronized Mensaje recibirPasivo() 
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
		Mensaje i = lstMensajes.remove (0);
		notifyAll();
		return i ;
	}
	
	public synchronized Mensaje recibirActivo() 
	{
		while (lstMensajes.size() == 0)
		{
			
		}
		Mensaje i = lstMensajes.remove(0);
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