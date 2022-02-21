package main;

public class Proceso extends Thread
{
	protected int id;
	protected int tEspera;
	protected boolean tipoEnvio;
	protected boolean tipoRecepcion;
	protected Buzon bznRetiro;
	protected Buzon bznEnvio;
	protected boolean estado = true;
	
	public Proceso(int pid, int ptEspera, boolean ptipoEnvio, boolean ptipoRecepcion, Buzon pbznRetiro, Buzon pbznEnvio)
	{
		id = pid;
		tEspera = ptEspera;
		tipoEnvio = ptipoEnvio;
		tipoRecepcion = ptipoRecepcion;
		bznRetiro = pbznRetiro;
		bznEnvio = pbznEnvio;
	}

	public void imprimirConfiguracion()
	{
		System.out.println("id: " + id);
		System.out.println(tEspera);
		System.out.println(tipoEnvio);
		System.out.println(tipoRecepcion);
		
		System.out.println("bznRetiro: ");
		bznRetiro.imprimirConfiguracion();
		System.out.println("bznEnvio: ");
		bznEnvio.imprimirConfiguracion();
	}
	
	protected void cicloActivoActivo()
	{
		while (estado)
		{
			Mensaje mensaje = bznRetiro.recibirActivo();
			
			if (mensaje.getTexto() == "FIN")
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
			
			if (mensaje.getTexto() == "FIN")
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
			
			if (mensaje.getTexto() == "FIN")
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
			
			if (mensaje.getTexto() == "FIN")
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
