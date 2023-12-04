package libs;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Leer {
    public static int introduceEntero(String texto) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = false;
        System.out.println(texto);
        int num = 0;
        do {
            try {
                num = sc.nextInt();
                continuar = true;
            } catch (InputMismatchException ex) {
                System.out.println("Debes introducir números enteros");
                sc.next();
            }
        } while (!continuar);
        return num;
    }
    public static double introduceDouble() {
        Scanner sc = new Scanner(System.in);
        boolean continuar = false;
        System.out.println("Introduce un número: ");
        Double num = null;
        do{
            try{
                num= sc.nextDouble();
                continuar=true;
            }catch(InputMismatchException ex){
                System.out.println("Debes introducir números");
                sc.next();
            }
        }while(!continuar);
        return num;
    }
    public static String introduceLetra() {
        Scanner sc= new Scanner(System.in);
        boolean continuar = false;
        System.out.println("Introduce una letra:");
        String letra = null;
        do{
            try{
                letra = sc.next("[a-zA-Z]");
                continuar = true;
            }catch(InputMismatchException ex){
                System.out.println("Debes introducir una única letra");
                sc.next();
            }
        }while(!continuar);
        return letra;
    }
    public static String introducePalabra() {
        Scanner sc= new Scanner(System.in);
        boolean continuar = false;
        String palabra = null;
        do{
            try{
                palabra = sc.next("[a-zA-Z]+");
                continuar = true;
            }catch(InputMismatchException ex){
                System.out.println("Debes introducir solo letras");
                sc.next();
            }
        }while(!continuar);
        return palabra;
    }
    public static String introduceString(String texto) {
        Scanner sc = new Scanner(System.in);
        System.out.println(texto);
        return sc.nextLine();
    }
    public static boolean introduceBoolean(String texto) {
        Scanner sc = new Scanner(System.in);
        System.out.println(texto);
        String valorEntrada = sc.nextLine().toLowerCase();
        while (!valorEntrada.equals("true") && !valorEntrada.equals("false")) {
            System.out.println("Debes introducir 'true' o 'false'");
            System.out.println(texto);
            valorEntrada = sc.nextLine().toLowerCase();
        }
        return Boolean.parseBoolean(valorEntrada);
    }
}
