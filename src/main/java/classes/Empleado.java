package classes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "empleado")
@XmlAccessorType(XmlAccessType.NONE)
public class Empleado {
    public String nombre;
    public int sueldo;
    public int año;
    public String antiguedadEmpleado;
    public int idDep;

    public Empleado() {
    }

    public Empleado(String nombre, int sueldo, int año, String antiguedad) {
        this.nombre = nombre;
        this.sueldo = sueldo;
        this.año = año;
        this.antiguedadEmpleado = antiguedad;
    }

    public int getDepartamento() {
        return idDep;
    }

    public void setDepartamento(int departamento) {
        this.idDep = departamento;
    }
    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @XmlElement(name = "sueldo")
    public int getSueldo() {
        return sueldo;
    }

    public void setSueldo(int sueldo) {
        this.sueldo = sueldo;
    }
    @XmlElement(name = "año")
    public int getAño() {
        return año;
    }

    public void setAño(int añoNacimiento) {
        this.año = añoNacimiento;
    }
    @XmlElement
    public String getAntiguedad() {
        return antiguedadEmpleado;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedadEmpleado = antiguedad;
    }
}
