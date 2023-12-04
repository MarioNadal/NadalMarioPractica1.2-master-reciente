package classes;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
@XmlRootElement
public class Empresa {
    private List<Empleado> empleados;
    private Departamentos departamentos;

    public Empresa() {
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }
    @XmlElement
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public Departamentos getDepartamentos() {
        return departamentos;
    }
    @XmlElement
    public void setDepartamentos(Departamentos departamentos) {
        this.departamentos = departamentos;
    }
}
