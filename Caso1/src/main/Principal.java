package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class Principal
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		Scanner in = new Scanner(System.in);
		
		System.out.println("Ingrese la dirección del archivo de configuración que desea cargar:");
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
		int nMensajes = 0;
		boolean sentinela = true;
		while (sentinela)
		{
			System.out.println("Ingrese la cantidad de mensajes que se van a enviar:");
			nMensajes = in.nextInt();
			
			if (nMensajes > Buzon.getTotalSize())
			{
				System.out.println("El valor ingresado excede la capacidad total de los buzones.");
			}
			
			else if (nMensajes <= 0)
			{
				System.out.println("El valor ingresado no es valido.");
			}
			
			else
			{
				sentinela = false;
			}
		}
		
		CyclicBarrier barrera = new CyclicBarrier(4);
		
		ArrayList<Object> lstConfigP1 = (ArrayList<Object>) lstConfiguraciones.get(4);
		procesos[0] = (Proceso)new ProcesoInicial(barrera, 1, nMensajes, (int)lstConfigP1.get(0), (boolean)lstConfigP1.get(2), (boolean)lstConfigP1.get(1), buzones[3], buzones[0]);
		procesos[0].start();
		
		i++;
		int j = 1;
		while (i < 8 & j < 4)
		{
			ArrayList<Object> lstConfig = (ArrayList<Object>) lstConfiguraciones.get(i); //5
			procesos[j] = new Proceso(barrera, j+1, (int)lstConfig.get(0), (boolean)lstConfig.get(2), (boolean)lstConfig.get(1), buzones[j-1], buzones[j]);
			procesos[j].start();
			i++;
			j++;
		}
		
		for (int k=0; k<4; k++)
		{
			procesos[k].join();
		}
		
		System.out.println("Todos los procesos han terminado su ejecución.");
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
