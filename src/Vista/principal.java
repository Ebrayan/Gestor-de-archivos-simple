package Vista;
import Control.ficheros;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
 
public class principal extends Application {
  public static File carpetaSeleccionada ;
  Text txtCarpeta;
  public static Button show= new Button();

    @Override
    public void start(Stage ventana) {
        StackPane raiz = new StackPane();
        Pane PanelPrincipal = new Pane();
        PanelPrincipal.setMaxSize(600, 600);
        PanelPrincipal.setMinSize(600, 600);
        // my nodes
        raiz.getChildren().add(PanelPrincipal);
        JFXButton[] btn = new JFXButton[5];
        Text titulo = new Text("Menu de Opciones");
        Rectangle rectangulos1 = new Rectangle(100, 50);
        Rectangle rectangulos2 = new Rectangle(100, 50);
                txtCarpeta = new Text("None");

        
        // init.. nodes
         titulo.setFont(Font.font(40));
         titulo.setLayoutX(150);
         titulo.setLayoutY(40);
         PanelPrincipal.getChildren().add(titulo);
         
         rectangulos1.setLayoutX(5);
         rectangulos2.setLayoutX(495);
         rectangulos1.setLayoutY(520);
         rectangulos2.setLayoutY(520);
         PanelPrincipal.getChildren().add(rectangulos1);
         PanelPrincipal.getChildren().add(rectangulos2);
         RotateTransition rotate = new RotateTransition(Duration.seconds(3), rectangulos1);
         RotateTransition rotate2 = new RotateTransition(Duration.seconds(3), rectangulos2);
         rotate.setFromAngle(0);
         rotate.setToAngle(720);
         rotate.setCycleCount(Timeline.INDEFINITE);
         rotate.setAutoReverse(true);
         rotate2.setFromAngle(0);
         rotate2.setToAngle(720);
         rotate2.setCycleCount(Timeline.INDEFINITE);
         rotate2.setAutoReverse(true);
         
         ScaleTransition scale[]= new ScaleTransition[5];
               
         rotate.play();
         rotate2.play();
         txtCarpeta.setLayoutX(100);
         txtCarpeta.setLayoutY(560);
         PanelPrincipal.getChildren().add(txtCarpeta);

         
         
         for (int i = 0; i < btn.length; i++) {
            btn[i] = new JFXButton();
            btn[i].setLayoutX(150);
            if (i!=0) {
                btn[i].setLayoutY(btn[i-1].getLayoutY()+85);
            }else{
                btn[i].setLayoutY(80);
            }
            btn[i].setMaxSize(300, 75);
            btn[i].setMinSize(300, 75);
            btn[i].setStyle("-fx-background-radius:20px;-fx-background-color: #008b8b;");
            btn[i].setFont(Font.font(20));
            PanelPrincipal.getChildren().add(btn[i]);
             scale[i] = new ScaleTransition(Duration.seconds(1.50),btn[i]);
        scale[i].setToX(1.070);
        scale[i].setToY(1.050);
        scale[i].setCycleCount(Timeline.INDEFINITE);
        scale[i].setAutoReverse(true);
        scale[i].play();
        }
        btn[0].setText("Nuevo Fichero");
        btn[1].setText("Mostrar Ficheros");
        btn[2].setText("Ficheros mas grandes");
        btn[3].setText("Cambiar de carpeta");
        btn[4].setText("Salir");
         
        // adding the events 
        btn[0].setOnAction((ActionEvent)->{
            FileChooser buscador = new FileChooser();
             File  archivo = buscador.showOpenDialog(ventana);
             if(archivo!=null){
            try {
                guardarFicharo(archivo);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Guardado correctamente");
                alert.setHeaderText("Direcciona guardada con exito");
                alert.setContentText("El archivo ha sido guardado con exito");

                alert.showAndWait();
            } catch (IOException ex) {
                
                Logger.getLogger(principal.class.getName()).log(Level.SEVERE, null, ex);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error al guardar");
                alert.setHeaderText("No fue posible guardar el archivo");
                alert.setContentText("Error es el sigueiente" + ex.getMessage());

                alert.showAndWait();
            }
        }else{
                 Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Fichero no seleccionado");
                alert.setHeaderText("No fue posible guardar el archivo");
                alert.setContentText("Error es el sigueiente Fichero no seleccionado" );

                alert.showAndWait();
            }
        });
        btn[1].setOnAction((Event)->{
            ventana.hide();
            ListaDeFicheros.abreVentanaLista();
        });
        btn[2].setOnAction((ActionEvent e)->{
            ArrayList  <ficheros> listaDeFicheros  =getFicherosAlmecenados();
            String mensaje = "";
            for (ficheros fich : listaDeFicheros) {
                if(fich.getTamaño()>=2000){
                mensaje = mensaje+"     *" + fich.toString()+"   con un tamaño de  " + fich.getTamaño()+"kb,  \n";
                } 
            }
            Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Ficheros mas grandes");
                alert.setHeaderText("Lista de los ficheros mas grandes");
                alert.setContentText("Los ficheros mas grande de 2000kb son         :" + mensaje);

                alert.showAndWait();
        });
        btn[3].setOnAction((ActionEvent w)->{
            CambiarCarpeta(ventana);
        });
        btn[4].setOnAction((Event)->{
             alerta("Desea Salir", "Seguro que desea Salir","?Seguro que desea salir de la aplicacion?", ventana);
           
        });
        show.setOnAction(((ActionEvent e)  -> {
            ventana.show();
        }));
         // creating my scenes and ajusting my stage 
        Scene scene = new Scene(raiz, 600, 600);
        ventana.setTitle("Control de ficheros");
        ventana.setResizable(false);
        ventana.setMaximized(false);
        ventana.setOnCloseRequest((Event)->{
            System.out.println("Estoy saliefo");
        });
        ventana.setScene(scene);
        ventana.show();
        while(carpetaSeleccionada==null){
        seleccionarCarpeta(ventana);
        }
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
    
    public void seleccionarCarpeta( Stage ventana){
        Alert SeleccionaUnaCarpetaAlerta = new Alert(AlertType.INFORMATION);
        SeleccionaUnaCarpetaAlerta.setTitle("Selecciona una carpeta");
        SeleccionaUnaCarpetaAlerta.setHeaderText("Selecciona una carpeta");
        SeleccionaUnaCarpetaAlerta.setContentText("Hola querido usuario, antes de iniciar debe seleccioanar una carpeta"
                + " donde  se guardaran tus archivos ");

        SeleccionaUnaCarpetaAlerta.showAndWait();
        DirectoryChooser carpeta = new DirectoryChooser();
         File carpetaActual =  carpeta.showDialog(ventana);
         if(carpetaActual!=null){
           carpetaSeleccionada = carpetaActual;
        txtCarpeta.setText(carpetaSeleccionada.toString());
        txtCarpeta.setText(carpetaSeleccionada.toString());
        }else{
            seleccionarCarpeta(ventana);
        }
        

    }
    
    public void CambiarCarpeta( Stage ventana){
        Alert SeleccionaUnaCarpetaAlerta = new Alert(AlertType.INFORMATION);
        
        SeleccionaUnaCarpetaAlerta.setTitle("Cambio de carpeta principal");
        SeleccionaUnaCarpetaAlerta.setHeaderText("Selecciona otra carpeta");
        SeleccionaUnaCarpetaAlerta.setContentText("Hola nuevamente querido usuario, debe seleccioanar una carpeta"
                + " donde  se guardaran tus archivos ");

        SeleccionaUnaCarpetaAlerta.showAndWait();
        DirectoryChooser carpeta = new DirectoryChooser();
         File carpetaActual =  carpeta.showDialog(ventana);
         if(carpetaActual!=null){
           carpetaSeleccionada = carpetaActual;
        txtCarpeta.setText(carpetaSeleccionada.toString());
         }else{
             JOptionPane.showMessageDialog(null, " Seleccion de nueva carpeta erronea ");
         }
     }
    
    public void guardarFicharo(File ficheroSeleccionado) throws IOException{
  try{      
    File ficheroCopiar = ficheroSeleccionado;
    File DestinodeFichero = new File(carpetaSeleccionada, ficheroSeleccionado.getName());
    if (ficheroCopiar.exists()) {
        Files.copy(Paths.get(ficheroCopiar.getAbsolutePath()), Paths.get(DestinodeFichero.getAbsolutePath()), StandardCopyOption.COPY_ATTRIBUTES);
    } else {
        System.out.println("El fichero "+ficheroSeleccionado.getName()+" no existe en el directorio "+ficheroSeleccionado.getAbsolutePath());
    }

} catch (IOException e) {
    e.printStackTrace();
}
    }
    
public static ArrayList getFicherosAlmecenados(){
    String nombre;
    String locacion;
    long tamaño;
    
    ArrayList<ficheros> misFicheros = new ArrayList<>();
    File [] archivos = carpetaSeleccionada.listFiles();
    for (int i = 0; i < archivos.length; i++) {
         nombre=archivos[i].getName();
         locacion = archivos[i].getAbsolutePath();
         tamaño = archivos[i].length()/1024;
        ficheros fichero = new ficheros(nombre, tamaño, locacion);
        misFicheros.add(fichero);
    }
    return misFicheros;
}
public File getCarpetaSeleccionada(){
return carpetaSeleccionada;
}
private static void alerta(String title, String header, String content, Stage stage) {
    JFXDialogLayout dialogoLayout = new JFXDialogLayout();
    Text headeTr =new Text(header);
    headeTr.setFont(Font.font(20.0));
    
    JFXButton close = new JFXButton("Si, Salir ");
    JFXButton close2 = new JFXButton("No salir");
     
     close2.setStyle("-fx-background-color: #00bfff;-fx-background-radius:4px;-fx-text-fill:#ffffff;");
    close2.setOnMouseEntered((MouseEvent e)->{
        close2.setStyle("-fx-background-color:#daa520;-fx-background-radius:12px;-fx-text-fill:#ffffff;");
    });
    close2.setOnMouseExited((MouseEvent e)->{ 
        close2.setStyle("-fx-background-color: #00bfff;-fx-background-radius:4px;-fx-text-fill:#ffffff;");
    });
    close.setStyle("-fx-background-color: #00bfff;-fx-background-radius:4px;-fx-text-fill: white;");
    close.setOnMouseEntered((MouseEvent e)->{
        close.setStyle("-fx-background-color:#daa520;-fx-background-radius:12px;-fx-text-fill:#ffffff;");
    });
    close.setOnMouseExited((MouseEvent e)->{
        close.setStyle("-fx-background-color: #00bfff;-fx-background-radius:4px;-fx-text-fill:#ffffff;");
    });
    close.getStyleClass().add("JFXButton");
    dialogoLayout.setActions(close,close2);
    dialogoLayout.setHeading(headeTr);
    dialogoLayout.setBody(new Text(20, 20, content));
    StackPane panel = new StackPane();
    JFXDialog dialog = new JFXDialog( (StackPane)stage.getScene().getRoot(),dialogoLayout, JFXDialog.DialogTransition.LEFT);
    close.setOnAction((ActionEvent e)->{
        System.exit(0);
    });
    close2.setOnAction((ActionEvent e)->{
        dialog.close();
    });
    dialog.setStyle("-fx-background-radius:8px");
    dialog.show();
}
}
// i would like something more hard :D