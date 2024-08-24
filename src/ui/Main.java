package ui;

import clase.Usuario;
import service.UsuarioServicio;

import java.util.ArrayList;
import java.util.Scanner;



public class Main {

    static int contadorId = 1;
    static int contadorLegajo = 1;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        ArrayList<Usuario> usuario = new ArrayList<>();
        UsuarioServicio usuarioServicio = new UsuarioServicio(usuario);

        int opcion;
        do {
            System.out.println("1. Crear usuario");
            System.out.println("2. Actualizar usuario");
            System.out.println("3. Buscar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println(" ");
            System.out.println("0. Salir");
            System.out.println(" ");
            System.out.println("Seleccione una opcion: ");
            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion) {

                case 1:
                    //Crear usuario
                    int id = contadorId++;
                    int legajo = contadorLegajo++;

                    System.out.println("Ingrese el nombre del usuario: ");
                    String nombre = scan.nextLine();
                    System.out.println("Ingrese el apellido del usuario: ");
                    String apellido = scan.nextLine();
                    System.out.println("Ingrese la password del usuario: ");
                    String password = scan.nextLine();
                    usuarioServicio.crearUsuario(id, nombre, apellido, legajo, password);
                    break;


                case 2:
                    //Actualizar usuario
                    System.out.println("Ingrese el legajo del usuario que desea actualizar");
                    int legajoActualizar = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Ingrese el nuevo nombre: ");
                    String nuevoNombre = scan.nextLine();
                    System.out.println("Ingrese el nuevo apellido: ");
                    String nuevoApellido = scan.nextLine();
                    usuarioServicio.actualizarUsuario(legajoActualizar, nuevoNombre, nuevoApellido);
                    break;

                case 3:
                    //Buscar usuario por Legajo
                    System.out.println("Ingrese el legajo del usuario: ");
                    int legajoBuscar = scan.nextInt();
                    scan.nextLine();
                    Usuario usuarioLegajo = usuarioServicio.buscarUsuarioporLegajo(legajoBuscar);
                    if (usuarioLegajo != null) {
                        System.out.println("Usuario encontrado: " + usuarioLegajo.getLegajo() + " " + usuarioLegajo.getNombre() + " " + usuarioLegajo.getApellido());
                        System.out.println(" ");
                    } else {
                        System.out.println("Usuario no encontrado");
                        System.out.println(" ");
                    }
                    break;

                case 4:
                    //Eliminar usuario
                    System.out.println("Ingrese el legajo del usuario que desea eliminar: ");
                    int legajoEliminar = scan.nextInt();
                    scan.nextLine();
                    usuarioServicio.eliminarUsuario(legajoEliminar);
                    break;

                case 0:
                    //Salir
                    System.out.println("Saliste de la interfaz de usuario");
                    break;

                default:
                    System.out.println("Opcion no valida. Intente nuevamente");

            }

            }  while (opcion!=0);
        }

    }