/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Mascota;

/**
 *
 * @author GalindezPc
 */
public class ControlBD {

    private static Connection conexion;
    private static ResultSet consulta;
    private static Statement sentencia;
    private static String servidor = "jdbc:mysql://localhost:3306/bd_mascotas_horisoes";
    private static String login = "root";
    private static String password = "root";

    public ControlBD() {

    }

    //**********************  Metodos para la comexiom de la base de datos****************************
    public static Connection obtenerConexion() {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            conexion = DriverManager.getConnection(servidor, login, password);
            System.out.println("CONEXION EXITOSA");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("ERROR CON EL DRIVER");
        }
        return conexion;
    }

    //***************  Metodos para gestionar la mascota******************************************
    public String agregarMascota(String nombre, String edad, String raza) {
        int resp = -1;
        String respuesta = null;
        try {
            sentencia = obtenerConexion().createStatement();
            resp = sentencia.executeUpdate("insert into mascota (mascota.nombre,mascota.raza,mascota.edad) values('" + nombre + "','" + raza + "','" + edad + "');");
        } catch (SQLException ex) {
            respuesta = "Error al Registrar";
        }

        if (resp > 0) {
            respuesta = "Registro Con Exito";
        }

        return respuesta;
    }

    public String eliminarMascota(int id) {
        String respuesta = null;
        int resp = 0;
        try {
            sentencia = obtenerConexion().createStatement();
            resp = sentencia.executeUpdate("delete from mascota where mascota.idMascota=" + id + ";");

            if (resp > 0) {
                respuesta = "Eliminado Con Exito";
            }

        } catch (Exception e) {
            respuesta = "Error al Eliminar";
        }
        return respuesta;

    }

    public String modificarMascota(int id, String nombre, String edad, String raza) {
        String respuesta = null;
        int resp = 0;
        try {
            sentencia = obtenerConexion().createStatement();
            resp = sentencia.executeUpdate("update mascota set mascota.nombre='" + nombre + "',mascota.edad='" + edad + "',mascota.raza='" + raza + "' where mascota.idMascota=" + id + ";");

            if (resp > 0) {
                respuesta = "Modificacion Exitosa";
            }

        } catch (Exception e) {
            respuesta = "Error al Modificar";
        }
        return respuesta;

    }

    public List<Mascota> listarMascotas() {
        List<Mascota> mascotas = null;
        Mascota mascotaTemp;

        String sql = "select * from mascota;";

        try {
            sentencia = obtenerConexion().createStatement();
            ResultSet rs = sentencia.executeQuery(sql);

            mascotas = new ArrayList<>();
            while (rs.next()) {
                mascotaTemp = new Mascota();
                mascotaTemp.setId(rs.getInt("idMascota"));
                mascotaTemp.setNombre(rs.getString("nombre"));
                mascotaTemp.setRaza(rs.getString("raza"));
                mascotaTemp.setEdad(rs.getString("edad"));
                mascotas.add(mascotaTemp);
            }

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControlBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mascotas;
    }

    public Mascota buscarMascota(int idMascota) {
        Mascota mascota = null;

        try {
            sentencia = obtenerConexion().createStatement();
            try (ResultSet rs = sentencia.executeQuery("select * from mascota where mascota.idMascota=" + idMascota + ";")) {
                while (rs.next()) {
                    mascota = new Mascota();
                    mascota.setId(rs.getInt("idMascota"));
                    mascota.setNombre(rs.getString("nombre"));
                    mascota.setRaza(rs.getString("raza"));
                    mascota.setEdad(rs.getString("edad"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("mascota Busqueda "+mascota);
        return mascota;
    }
}
