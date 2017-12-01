/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import modelo.Mascota;
import org.primefaces.context.RequestContext;

public class MascotaBean implements Serializable {
    
    private static final long serialVersionUID = 34233423L;

    HtmlInputText inputID;
    private int id;
    private String nombre;
    private String raza;
    private String edad;
    private List<Mascota> mascotas;
    private List<Mascota> mascotasFiltradas;
    private Mascota mascTemp;

    public Mascota getMascTemp() {
        return mascTemp;
    }

    public void setMascTemp(Mascota mascTemp) {
        this.mascTemp = mascTemp;
    }

    public void setMascotasFiltradas(List<Mascota> mascotasFiltradas) {
        this.mascotasFiltradas = mascotasFiltradas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Mascota> getMascotasFiltradas() {
        return mascotasFiltradas;
    }

    public MascotaBean() {
        listarMascotas();
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void guardar(ActionEvent actionEvent) {
        ControlBD controlBD = new ControlBD();
        String respuesta = controlBD.agregarMascota(nombre, edad, raza);
        listarMascotas();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Base de datos", respuesta);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "index.xhtml");

    }

    public void eliminar(int position) {
        ControlBD controlBD = new ControlBD();
        String respuesta = controlBD.eliminarMascota(mascotas.get(position).getId());

        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Base de datos", respuesta);
        mascotas.remove(position);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        //context.addCallbackParam("respuesta", msg);
        context.addCallbackParam("view", "index.xhtml");

    }

    public void modificar() {
        ControlBD control = new ControlBD();
        String respuesta = control.modificarMascota(id, nombre, edad, raza);

        RequestContext context = RequestContext.getCurrentInstance();
        Mascota mas = null;
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Base de datos", respuesta);

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "index.xhtml");
    }

    public void buscar() {
        ControlBD controlBD = new ControlBD();
        mascTemp = controlBD.buscarMascota(id);

        setNombre(mascTemp.getNombre());
        setRaza(mascTemp.getRaza());
        setEdad(mascTemp.getEdad());

    }

    public void listarMascotas() {
        ControlBD controlBD = new ControlBD();
        mascotas = controlBD.listarMascotas();

    }

    public void cargarDatos(int position) {
        this.id = mascotas.get(position).getId();
        buscar();
    }

}
