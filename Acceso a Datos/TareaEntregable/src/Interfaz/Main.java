package Interfaz;

import LogicaNegocio.Inmobiliaria;
import java.util.Scanner;

public class Main {
    String rutaficheros;
    String ficheroEmpleados;
    String ficheroInmuebles;
    Inmobiliaria gestor;
    Scanner teclado = new Scanner(System.in);
    public Main(){
        
    }
    public void menu() {
        rutaficheros = System.getProperty("user.dir")+"\\src\\Ficheros";
        ficheroEmpleados=rutaficheros+"\\empelados.csv";
        ficheroInmuebles=rutaficheros+"\\inmuebles.csv";
        gestor = new Inmobiliaria(ficheroEmpleados,ficheroInmuebles);
        int opcion = -1;
        while(opcion != 0){
            opcion = pedirOpcion();
            switch (opcion) {
                case 1:
                    listarDatos();
                    break;
                case 2: 
                    listarInmueble();
                    break;
                case 3:
                    nuevoInmmueble();
                    break;
                case 4:
                    nuevoEmpleado();
                    break;
                case 5:
                    borrarInmueble();
                    break;
                default:
                    throw new AssertionError();
            }
        }
        guardarDatos();
    }

 
    
    public static void main(String[] args) {
        // TODO code application logic here
        Main ejecucionMain = new Main();
        ejecucionMain.menu();
    }

    private int pedirOpcion() {
        System.out.println("Menu");
        System.out.println("1.- Listar Datos ");
        System.out.println("2.- Listar Inmuebles");
        System.out.println("3.- Nuevo inmueble");
        System.out.println("4.- Nuevo empleado");
        System.out.println("5.- Borrar inmueble");
        int opcion = teclado.nextInt();
        teclado.nextLine();
        return opcion;
    }

    private void listarDatos() {
        System.out.println("Listado informacion ");
        System.out.println(gestor.listarDatos());
    }

    private void listarInmueble() {
        System.out.println("Listado de inmuebles");
        System.out.println(gestor.listadoInmuebles());
    }

    private void nuevoInmmueble() {
        System.out.println("Nuevo inmueble:");
        System.out.print("codInmueble: ");
        String codInmueble = teclado.nextLine();
        System.out.print("direccion :");
        String direccion = teclado.nextLine();
        System.out.print("tipo :");
        String tipo =teclado.nextLine();
        System.out.print("codEmpleado ");
        String codEmpleado = teclado.nextLine();
        System.out.print("precio :");
        double precio = teclado.nextDouble();
        
        String linea=codInmueble+";"+direccion+";"+tipo+";"+codEmpleado+";"+precio;
        gestor.insertarInmueble(linea);
    }

    private void nuevoEmpleado() {
        System.out.println("Nuevo empelado:");
        System.out.print("codEmpleado ");
        String codEmpleado = teclado.nextLine();
        System.out.print("nombre completo");
        String nombreCompleto = teclado.nextLine();
        System.out.print("telefono :");
        String telefono = teclado.nextLine();
 
        String linea = codEmpleado+";"+nombreCompleto+";"+telefono;
        gestor.insertarEmpleado(linea);
    }
	

    private void borrarInmueble() {
        System.out.println("Borrar Inmueble");
        teclado.nextLine();
        String codInmueble = teclado.nextLine();
        if(gestor.borrarInmueble(codInmueble)==true){
            System.out.println("Se ha borrado el inmueble correctamente");
        }
        
    }

    private void guardarDatos() {
        System.out.println("Guardar Datos");
        System.out.println("Nombre del fichero empelados ");
        String nombreFicheroEmpelados = teclado.nextLine();
        System.out.println("Nombre del fichero inmuebles ");
        String nombreFicheroinmuebles = teclado.nextLine();
        int resul = gestor.guardarDatos(nombreFicheroEmpelados,nombreFicheroinmuebles);
        System.out.println("Se han guardado "+resul+" registros");
    }
    
}
