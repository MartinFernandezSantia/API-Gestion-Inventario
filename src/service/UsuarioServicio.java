package service;

import clase.Usuario;

import java.util.ArrayList;
import java.util.Iterator;

public class UsuarioServicio {

    private ArrayList<Usuario> usuarios; //ArrayList que contiene usuarios

    public UsuarioServicio() {
    }

    public UsuarioServicio(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    //Crear nuevo usuario
    public void crearUsuario(int id,String nombre,String apellido,int legajo,String password){
        Usuario nuevoUsuario = new Usuario(id,nombre,apellido,legajo,password);
        usuarios.add(nuevoUsuario);

    }

    //Listar usuario
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    //Actualizar usuario
    public void actualizarUsuario(int legajoActualizar,String nuevoNombre,String nuevoApellido){
        for(Usuario usuario : usuarios){
            if(usuario.getId()==legajoActualizar){
                usuario.setNombre(nuevoNombre);
                usuario.setApellido(nuevoApellido);
                return;
            }
        }
    }

    //Actualizar password usuario
    public void actualizarPasswordUsuario(int id,String nuevaPassword) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuario.setPassword(nuevaPassword);
                return;
            }
        }
    }

    //Eliminar usuario
    public void eliminarUsuario(int id){
        Iterator<Usuario> it = usuarios.iterator();

        while(it.hasNext()){
            if(it.next().getId()==id){
                it.remove();
            }
        }
    }
}
