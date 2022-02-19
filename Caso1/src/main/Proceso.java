package main;

public class Proceso extends Thread
{
	private int tEspera;
	private boolean tipoEnvio;
	private boolean tipoRecepcion;
	private Buzon bznRetiro;
	private Buzon bznEnvio;
	private boolean estado = true;
	
	public Proceso(int ptEspera, boolean ptipoEnvio, boolean ptipoRecepcion, Buzon pbznRetiro, Buzon pbznEnvio)
	{
		tEspera = ptEspera;
		tipoEnvio = ptipoEnvio;
		tipoRecepcion = ptipoRecepcion;
		bznRetiro = pbznRetiro;
		bznEnvio = pbznEnvio;
	}

	public void imprimirConfiguracion()
	{
		System.out.println(tEspera);
		System.out.println(tipoEnvio);
		System.out.println(tipoRecepcion);
		
		System.out.println("bznRetiro: ");
		bznRetiro.imprimirConfiguracion();
		System.out.println("bznEnvio: ");
		bznEnvio.imprimirConfiguracion();
	}
}
