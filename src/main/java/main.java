import classes.*;
import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import libs.Leer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class main {
    public static void main(String[] args) {
        List<Empleado> empleados = new ArrayList<>();
        Departamentos departamentos = leerDepartamentos();
        String guiones = "-".repeat(20);
        int menu;
        boolean salir = false;
        do {
            System.out.println(guiones);
            System.out.println("1. Pedir Empleados");
            System.out.println("2. Leer Departamentos");
            System.out.println("3. Asignar cada empleado Departamento");
            System.out.println("4. Leer JSON");
            System.out.println("5. Toda la información de la empresa XML o JSON");
            System.out.println("0. Salir");
            System.out.println(guiones);
            menu = libs.Leer.introduceEntero("Introduce el número del menú: ");
            System.out.println(guiones);

            //Igual se tendrá que hacer con if else si el jdk es menor al que necesita el switch
            switch (menu) {
                case 5 -> todaLaInformacionEmpresa(empleados, departamentos);
                case 4 -> leerNuevosEmpleadosJSON(empleados);
                case 3 -> asignarCadaEmpleadoDepartamento(empleados, departamentos);
                case 2 -> departamentos = leerDepartamentos();
                case 1 -> pedirEmpleados(empleados);
                case 0 -> salir = true;
                default -> System.out.println("Ese número no esta en el menú, introduzca un número del menu.");
            }
        } while (!salir);
    }

    private static void todaLaInformacionEmpresa(List<Empleado> empleados, Departamentos departamentos) {
        String guiones = "-".repeat(20);
        int menu;
        boolean salir = false;
        do {
            System.out.println(guiones);
            System.out.println("1. Toda la información de la empresa en XML");
            System.out.println("2. Toda la información de la empresa en JSON");
            System.out.println("0. Salir");
            System.out.println(guiones);
            menu = Leer.introduceEntero("Introduzca número del tipo de archivo de la información de la empresa que desea recibir");
            switch (menu) {
                case 1 -> XMLInformacionEmpresa(empleados, departamentos);
                case 2 -> JSONInformacionEmpresa(empleados, departamentos);
                case 0 -> salir = true;
                default -> System.out.println("Ese número no esta en el menú, introduzca un número del menu.");
            }
        } while (!salir);
    }

    private static void JSONInformacionEmpresa(List<Empleado> empleados, Departamentos departamentos) {
        String empleadosJSON = null, departamentoJSON = null;
        //Para convertir a JSON
        Gson gson = new Gson();
        empleadosJSON = gson.toJson(empleados);
        departamentoJSON = gson.toJson(departamentos);
        Path p = Path.of("src/main/resources/empresa.json");
        try {
            //Si el archivo no esta creado lo creamos
            if (!Files.exists(p)) {
                Files.createFile(p);
            }
            //comprobamos que se puede escribir en el fichero
            if (libs.CheckFiles.ficheroEscribible(p)) {
                //Escribimos en el fichero
                try {
                    Files.writeString(p, empleadosJSON + departamentoJSON);
                } catch (IOException e) {
                    System.out.println("Error en la escritura del json");
                }
            } else {
                System.out.println("No es posible escribir en este fichero");
            }
        } catch (IOException ex) {
            System.out.println("Error al crear el json");
        }
    }

    private static void XMLInformacionEmpresa(List<Empleado> empleados, Departamentos departamentos) {
        Path p = Path.of("src/main/resources/empresa.xml");
        try {
            //Si el archivo no esta creado lo creamos
            if (!Files.exists(p)) {
                Files.createFile(p);
            }
            //comprobamos que se puede escribir en el fichero
            if (libs.CheckFiles.ficheroEscribible(p)) {
                try {
                    JAXBContext contexto = JAXBContext.newInstance(Empresa.class);
                    //Para pasar el codigo a xml -- Marshaller
                    Marshaller marshaller = contexto.createMarshaller();
                    //configuramos el formato de salida
                    marshaller.setProperty(marshaller.JAXB_FORMATTED_OUTPUT, true);
                    Empresa empresa = new Empresa();
                    empresa.setDepartamentos(departamentos);
                    empresa.setEmpleados(empleados);
                    //escribimos el archivo
                    marshaller.marshal(empresa, p.toFile());
                } catch (JAXBException ex) {
                    System.out.println("Error al pasar el código a XML" + ex.getErrorCode());
                }
            } else {
                System.out.println("No es posible escribir en el fichero");
            }
        } catch (IOException ex) {
            System.out.println("Error al crear el xml");
        }
    }

    private static void leerNuevosEmpleadosJSON(List<Empleado> empleados) {
        //cogemos la fecha actual en una variable
        SimpleDateFormat formateo = new SimpleDateFormat("yyyyMMdd'_'HH-mm-ss");
        Date fecha = new Date(System.currentTimeMillis());
        String fechaBuena = formateo.format(fecha);
        Path p = Path.of("src/main/resources/nuevosEmpleados.json");
        //varaible para almacenar el contenido del fichero
        Empleado[] empleadoJson;
        //leemos el contenido del Json, que es un texto
        String txtJson;
        //leempos el contenido del archivo de texto
        try {
            if (libs.CheckFiles.ficheroReadable(p)) {
                txtJson = Files.readString(p);
                //creo el Gson que transforma de texto a objeto
                Gson gson = new Gson();
                empleadoJson = gson.fromJson(txtJson, Empleado[].class);
                for (Empleado empleado : empleadoJson) {
                    empleado.setAntiguedad(fechaBuena);
                    empleados.add(empleado);
                }
                Path p2 = Path.of("src/main/resources/empleados.csv");
                if (libs.CheckFiles.ficheroEscribible(p2)) {
                    //Escribimos en el fichero
                    try (FileWriter writer = new FileWriter(p2.toFile())) {
                        //Recorremos todos los empleados para escribirlos en el csv
                        for (Empleado empleadoCSV : empleados) {
                            //Creamos la linea con todos los datos del empleado para añadirla al csv
                            String linea = empleadoCSV.getNombre() + ";" + empleadoCSV.getSueldo() + ";" +
                                    empleadoCSV.getAño() + ";" + empleadoCSV.getAntiguedad();
                            //Escribimos la linea en el csv y saltamos la linea
                            writer.write(linea + "\n");
                        }
                        System.out.println("Escritura del empleado correcta");
                    } catch (IOException e) {
                        System.out.println("Error en la escritura del csv");
                    }
                } else {
                    System.out.println("No es posible escribir en este fichero");
                }
            } else {
                System.out.println("El fichero no se puede leer");
            }
        } catch (IOException ex) {
            System.out.println("El fichero no se ha podido crear");
        }
    }

    private static void asignarCadaEmpleadoDepartamento(List<Empleado> empleados, Departamentos departamentos) {
        boolean salir = false;
        //Recorremos toda la lista empleados dependiendo de su tamaño
        for (int i = 0; i < empleados.size(); i++) {
            do {
                //Sacamos todos los nombre de los departamentos encontrados
                System.out.println("Lista de departamentos: ");
                for (int y = 1; y <= departamentos.getDepartamentos().size(); y++) {
                    System.out.println("\t" + y + "." + departamentos.getDepartamentos().get(y - 1).getNombre());
                }
                //El usuario debe introducir el departamentro al que pertenece el empleado
                int departamentoElegido = libs.Leer.introduceEntero("Introduce el departamento que quieres para el empleado: " + empleados.get(i).getNombre());
                //Se introduce el departamento elegido al empleado
                if (departamentoElegido <= departamentos.getDepartamentos().size()) {
                    empleados.get(i).setDepartamento(departamentoElegido);
                    salir = true;
                } else {
                    System.out.println("El número introducido no corresponde con un departamento");
                    salir = false;
                }
            } while (!salir);
        }
    }

    //LEER CON DOM
    /*IMPORTANTE NO EQUIVOCARSE EN LOS IMPORT
        import org.w3c.dom.Document;
        import org.w3c.dom.Element;
        import org.w3c.dom.NodeList;
    */
    private static Departamentos leerDepartamentos() {
        Departamentos departamentos = new Departamentos();
        //departamentos.setListaDeps(new ArrayList<Departamento>());
        Path p = Path.of("src/main/resources/departamentos.xml");
        Departamento auxDep;
        if (libs.CheckFiles.ficheroReadable(p)) {
            //generamos los objetos para leer con DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder parser = factory.newDocumentBuilder();
                Document document = parser.parse(p.toFile());
                NodeList nodosDepsXML = document.getElementsByTagName("departamento");
                for (int i = 0; i < nodosDepsXML.getLength(); i++) {
                    auxDep = new Departamento();
                    Element dep = (Element) nodosDepsXML.item(i);
                    //auxDep.setId(Integer.valueOf(dep.getAttribute("id")));
                    auxDep.setNombre(dep.getElementsByTagName("nombre").item(0).getTextContent());
                    auxDep.setLocalidad(dep.getElementsByTagName("localidad").item(0).getTextContent());
                    //departamentos.getListaDeps().add(auxDep);
                }

            } catch (ParserConfigurationException e) {
                System.out.println("Error al parsear el document");
            } catch (IOException e) {
                System.out.println("Error al parsear el Document");
            } catch (SAXException e) {
                throw new RuntimeException(e);
            }
            //inicializo el ArrayList de empleados para que luego me deje añadir nuevos empleados
            /*for (Departamento d: departamentos.getListaDeps()){
                d.setEmpleadosDep(new ArrayList<Empleado>());
                //comprobación
                System.out.println(d.getNombre());
            }*/
        } else {
            System.out.println("El fichero no se puede leer");
        }
        return departamentos;
}

    private static void pedirEmpleados(List<Empleado> empleados) {
        Path p = Path.of("src/main/resources/empleados.csv");
        boolean salir = true;
        String nombre, antiguedad;
        int sueldo, añoNacimiento;
        do {
            //El usuario introduce todos los datos del empleado a añadir
            nombre = libs.Leer.introduceString("Introduce nombre del empleado");
            sueldo = libs.Leer.introduceEntero("Introduce sueldo del empleado");
            añoNacimiento = libs.Leer.introduceEntero("Introduce año de nacimiento del empleado");
            antiguedad = libs.Leer.introduceString("Introduce antigüedad del empleado");
            //Se crea el nuevo empleado
            Empleado empleado = new Empleado(nombre, sueldo, añoNacimiento, antiguedad);
            //Se añado el empleado a la lista de todos los empleados
            empleados.add(empleado);
            //comprobamos si el fichero introducide se puede escribir
            if(libs.CheckFiles.ficheroEscribible(p)){
                //Escribimos en el fichero
                try(FileWriter writer = new FileWriter(p.toFile())){
                    //Recorremos todos los empleados para escribirlos en el csv
                    for(Empleado empleadoCSV : empleados){
                        //Creamos la linea con todos los datos del empleado para añadirla al csv
                        String linea = empleadoCSV.getNombre()+";"+empleadoCSV.getSueldo()+";"+
                                empleadoCSV.getAño()+";"+empleadoCSV.getAntiguedad();
                        //Escribimos la linea en el csv y saltamos la linea
                        writer.write(linea + "\n");
                    }
                    System.out.println("Escritura del empleado correcta");
                }catch(IOException e){
                    System.out.println("Error en la escritura del csv");
                }
            }else{
                System.out.println("No es posible escribir en este fichero");
            }
            //El usuario introducira true si quiere introducir más empleados o false si no quiere introducir más
            salir = libs.Leer.introduceBoolean("Introduce true si quiere seguir añadiendo empleados, de lo contrario introduzca false");
        } while (salir);
    }
}
