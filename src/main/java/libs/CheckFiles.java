package libs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CheckFiles {

    //método para saber si se puede escribir en un directorio y si no lo crea
    public static boolean dirEscribible(Path p){
        boolean dirOK = false;
        if(Files.exists(p) && Files.isDirectory(p)){
            if(Files.isWritable(p)){
                dirOK = true;
            }
        }else{
            try{
                Files.createDirectory(p);
                dirOK = true;
            }catch(IOException ex){
                System.out.println("Error en la creación del fichero.");
            }
        }
        return dirOK;
    }

    //método para saber si se puede leer un fichero
    public static boolean ficheroReadable(Path p){
        boolean ficheroOK = false;
        if(Files.exists(p)){
            if(Files.isReadable(p)){
                ficheroOK = true;
            }
        }else{
            try{
                Files.createFile(p);
                ficheroOK = true;
            }catch(IOException ex){
                System.out.println("Error en la creación de el fichero.");
            }
        }
        return ficheroOK;
    }
    //método para saber si se puede escribir un fichero
    public static boolean ficheroEscribible(Path p){
        //Método que comprueba sis e puede escribir en un fichero y si no lo crea
        boolean ficheroOK = false;
        if(Files.exists(p)){
            if(Files.isWritable(p)){
                ficheroOK = true;
            }
        }else{
            try{
                Files.createFile(p);
                ficheroOK = true;
            }catch(IOException ex){
                System.out.println("Error en la creación del fichero.");
            }
        }
        return ficheroOK;
    }
}
