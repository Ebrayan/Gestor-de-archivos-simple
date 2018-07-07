package Control;
import java.io.File;
public class ficheros {
    long tamaño;
    String Nombre;
    String Locacion;
    String Fecha;

    public long getTamaño() {
        return tamaño;
    }

    public void setTamaño(long tamaño) {
        this.tamaño = tamaño;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getLocacion() {
        return Locacion;
    }

    public void setLocacion(String Locacion) {
        this.Locacion = Locacion;
    }

    public ficheros(String nombre, long tamaño, String Locacion) {
        
        this.Nombre = nombre;
        this.Locacion = Locacion;
        this.tamaño = tamaño;
    }
 
    @Override
    public String toString() {
        return Nombre;
    }
    public void eliminarme(){
        
        File archivo = new File(Locacion);
        archivo.delete();
     }
    
}
