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
}