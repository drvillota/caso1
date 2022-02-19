package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		Scanner in = new Scanner(System.in);
		
		System.out.println("Ingrese la direcci�n del archivo de configuraci�n que desea cargar:");
		String dir = in.nextLine();
		
		File fileConfiguraciones = new File(dir);
		ArrayList<Object> lstConfiguraciones = cargarConfiguracion(fileConfiguraciones);
		
		Buzon[] buzones = new Buzon[4];
		int i = 0;
		while (i < 4)
		{
			buzones[i] = new Buzon((int)lstConfiguraciones.get(i));
			i++;
		}
		
		Proceso[] procesos = new Proceso[4];
		System.out.println("Ingrese la cantidad de mensajes que se van a enviar:");
		int nMensajes = in.nextInt();
		ArrayList<Object> lstConfigP1 = (ArrayList<Object>) lstConfiguraciones.get(4);
		procesos[0] = (Proceso)new ProcesoInicial(nMensajes, (int)lstConfigP1.get(0), (boolean)lstConfigP1.get(1), (boolean)lstConfigP1.get(2), buzones[3], buzones[0]);
		
		i++;
		int j = 1;
		while (i < 8 & j < 4)
		{
			ArrayList<Object> lstConfig = (ArrayList<Object>) lstConfiguraciones.get(i); //5
			procesos[j] = new Proceso((int)lstConfig.get(0), (boolean)lstConfig.get(1), (boolean)lstConfig.get(2), buzones[j-1], buzones[j]);
			procesos[j].start();
			i++;
			j++;
		}
		
		for (int k=0; k<4; k++)
		{
			procesos[k].join();
		}
		
		//TODO
		//Loop para imprimir info de threads y sus configuraciones y buzones
		/*
		for (int w=0; w<procesos.length; w++)
		{
			procesos[w].imprimirConfiguracion();
			System.out.println();
		}
		*/
		System.out.println("Todos los procesos han terminado su ejecuci�n");
	}
	
	private static ArrayList<Object> cargarConfiguracion(File fileConfiguracion) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(fileConfiguracion));
		ArrayList<Object> lstConfiguraciones = new ArrayList<Object>();
		
		String linea = br.readLine();
		for (int i=0; i<4; i++)
		{
			String[] configuraciones = linea.split(" ");
			lstConfiguraciones.add(Integer.parseInt(configuraciones[1]));
			
			linea = br.readLine();
		}
		
		for (int i=0; i<4; i++)
		{
			ArrayList<Object> sublstConfiguraciones = new ArrayList<Object>();
			String[] configuraciones = linea.split(" ");
			
			sublstConfiguraciones.add(Integer.parseInt(configuraciones[1]));
			sublstConfiguraciones.add(Boolean.parseBoolean(configuraciones[2]));
			sublstConfiguraciones.add(Boolean.parseBoolean(configuraciones[3]));
			
			lstConfiguraciones.add(sublstConfiguraciones);
			
			linea = br.readLine();
		}
		br.close();
		
		return lstConfiguraciones;
	}
}