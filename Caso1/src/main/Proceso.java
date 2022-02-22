package main;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Proceso extends Thread
{
	protected int id;
	protected int tEspera;
	protected boolean tipoEnvio;
	protected boolean tipoRecepcion;
	protected Buzon bznRetiro;
	protected Buzon bznEnvio;
	protected boolean estado = true;
	protected CyclicBarrier barrera;
	
	public Proceso(CyclicBarrier pbarrera, int pid, int ptEspera, boolean ptipoEnvio, boolean ptipoRecepcion, Buzon pbznRetiro, Buzon pbznEnvio)
	{
		barrera = pbarrera;
		id = pid;
		tEspera = ptEspera;
		tipoEnvio = ptipoEnvio;
		tipoRecepcion = ptipoRecepcion;
		bznRetiro = pbznRetiro;
		bznEnvio = pbznEnvio;
	}
	
	protected void cicloActivoActivo()
	{
		while (estado)
		{
			Mensaje mensaje = bznRetiro.recibirActivo();
			
			if (mensaje.getTexto().equals("FIN"))
			{
				estado = false;
			}
			
			else
			{
				mensaje.transformarMensaje(id, tipoRecepcion, tipoEnvio);
				try
				{
					sleep(tEspera);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
			bznEnvio.enviarActivo(mensaje);
		}
	}
	
	protected void cicloActivoPasivo()
	{
		while (estado)
		{
			Mensaje mensaje = bznRetiro.recibirActivo();
			
			if (mensaje.getTexto().equals("FIN"))
			{
				estado = false;
			}
			
			else
			{
				mensaje.transformarMensaje(id, tipoRecepcion, tipoEnvio);
				try
				{
					sleep(tEspera);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
			bznEnvio.enviarPasivo(mensaje);
		}
	}
	
	protected void cicloPasivoActivo()
	{
		while (estado)
		{
			Mensaje mensaje = bznRetiro.recibirPasivo();
			
			if (mensaje.getTexto().equals("FIN"))
			{
				estado = false;
			}
			
			else
			{
				mensaje.transformarMensaje(id, tipoRecepcion, tipoEnvio);
				try
				{
					sleep(tEspera);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
			bznEnvio.enviarActivo(mensaje);
		}
	}
	
	protected void cicloPasivoPasivo()
	{
		while (estado)
		{
			Mensaje mensaje = bznRetiro.recibirPasivo();
			
			if (mensaje.getTexto().equals("FIN"))
			{
				estado = false;
			}
			
			else
			{
				mensaje.transformarMensaje(id, tipoRecepcion, tipoEnvio);
				try
				{
					sleep(tEspera);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
			bznEnvio.enviarPasivo(mensaje);
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			barrera.await();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (BrokenBarrierException e)
		{
			e.printStackTrace();
		}
		if (tipoRecepcion & tipoEnvio)
		{
			cicloActivoActivo();
		}
		
		else if (tipoRecepcion & !tipoEnvio)
		{
			cicloActivoPasivo();
		}
		
		else if (!tipoRecepcion & tipoEnvio)
		{
			cicloPasivoActivo();
		}
		
		else
		{
			cicloPasivoPasivo();
		}
	}
}
