package main;

public class ProcesoInicial extends Proceso
{
	private int nMensajes;
	
	public ProcesoInicial(int pnMensajes, int ptEspera, boolean ptipoEnvio, boolean ptipoRecepcion, Buzon pbznRetiro, Buzon pbznEnvio)
	{
		super(ptEspera, ptipoEnvio, ptipoRecepcion, pbznRetiro, pbznEnvio);
		nMensajes = pnMensajes;
	}
	
	@Override
	public void imprimirConfiguracion()
	{
		System.out.println("nMensajes a Enviar: " + nMensajes);
		super.imprimirConfiguracion();
	}
}
