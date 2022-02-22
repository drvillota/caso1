package main;

import java.util.concurrent.CyclicBarrier;

public class ProcesoInicial extends Proceso
{
	private int nMensajes;
	private int indiceMensaje;
	
	public ProcesoInicial(CyclicBarrier pbarrera, int pid, int pnMensajes, int ptEspera, boolean ptipoEnvio, boolean ptipoRecepcion, Buzon pbznRetiro, Buzon pbznEnvio)
	{
		super(pbarrera, pid, ptEspera, ptipoEnvio, ptipoRecepcion, pbznRetiro, pbznEnvio);
		nMensajes = pnMensajes;
		indiceMensaje = 1;
	}
	
	@Override
	protected void cicloActivoActivo()
	{
		while (estado)
		{
			Mensaje mensaje = bznRetiro.recibirActivo();
			
			System.out.println("Mensaje " + indiceMensaje + ": " + mensaje.getTexto());
			indiceMensaje++;
			
			if (mensaje.getTexto().equals("FIN"))
			{
				estado = false;
			}
			
			if (nMensajes > 0)
			{
				bznEnvio.enviarActivo(new Mensaje(tipoRecepcion, tipoEnvio, false));
				nMensajes--;
			}
			
			else if (nMensajes == 0)
			{
				bznEnvio.enviarActivo(new Mensaje(tipoRecepcion, tipoEnvio, true));
				nMensajes--;
			}
		}
	}
	
	@Override
	protected void cicloActivoPasivo()
	{
		while (estado)
		{
			Mensaje mensaje = bznRetiro.recibirActivo();
			System.out.println("Mensaje " + indiceMensaje + ": " + mensaje.getTexto());
			indiceMensaje++;
			
			if (mensaje.getTexto().equals("FIN"))
			{
				estado = false;
			}
			
			if (nMensajes > 0)
			{
				bznEnvio.enviarPasivo(new Mensaje(tipoRecepcion, tipoEnvio, false));
				nMensajes--;
			}
			
			else if (nMensajes == 0)
			{
				bznEnvio.enviarPasivo(new Mensaje(tipoRecepcion, tipoEnvio, true));
				nMensajes--;
			}
		}
	}
	
	@Override
	protected void cicloPasivoActivo()
	{
		while (estado)
		{
			Mensaje mensaje = bznRetiro.recibirPasivo();
			
			System.out.println("Mensaje " + indiceMensaje + ": " + mensaje.getTexto());
			indiceMensaje++;
			
			if (mensaje.getTexto().equals("FIN"))
			{
				estado = false;
			}
			
			if (nMensajes > 0)
			{
				bznEnvio.enviarActivo(new Mensaje(tipoRecepcion, tipoEnvio, false));
				nMensajes--;
			}
			
			else if (nMensajes == 0)
			{
				bznEnvio.enviarActivo(new Mensaje(tipoRecepcion, tipoEnvio, true));
				nMensajes--;
			}
		}
	}
	
	@Override
	protected void cicloPasivoPasivo()
	{
		while (estado)
		{
			Mensaje mensaje = bznRetiro.recibirPasivo();
			
			System.out.println("Mensaje " + indiceMensaje + ": " + mensaje.getTexto());
			indiceMensaje++;
			
			if (mensaje.getTexto().equals("FIN"))
			{
				estado = false;
			}
			
			if (nMensajes > 0)
			{
				bznEnvio.enviarPasivo(new Mensaje(tipoRecepcion, tipoEnvio, false));
				nMensajes--;
			}
			
			else if (nMensajes == 0)
			{
				bznEnvio.enviarPasivo(new Mensaje(tipoRecepcion, tipoEnvio, true));
				nMensajes--;
			}
		}
	}
	
	@Override
	public void run()
	{
		nMensajes = bznEnvio.inicializarCiclo(nMensajes, tipoRecepcion, tipoEnvio);
		
		super.run();
	}
}
