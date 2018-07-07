 
package Vista;
 
import Control.ficheros;
import com.jfoenix.controls.JFXButton;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ListaDeFicheros extends Stage{

    
    public  ListaDeFicheros(){
        this.iniciarVentana(this);
    }
            ListView<ficheros> ListaVisual;
            ObservableList<ficheros> myLista;
    public void iniciarVentana(Stage VentanaDeLista)  {
        Application aplicacion = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                
            }
        };
         Pane panel = new Pane();
        Scene scene = new Scene(panel, 600, 600);
        ImageView visor = new ImageView();
        ArrayList listaDeFicheros = principal.getFicherosAlmecenados();
        myLista = FXCollections.observableList(listaDeFicheros);
        ListaVisual = new ListView<ficheros>(myLista);
        ListaVisual.setMaxSize(350, 500);
        ListaVisual.setMinSize(350, 500);
        ListaVisual.setLayoutX(220);
        ListaVisual.setLayoutY(10);
        ListaVisual.setTooltip(new Tooltip("Selecciones algun elemento"));
        Text txtTamaño = new Text("Tamaño :");
        Text txtLocacion = new Text("Ubicacion :");

        JFXButton btn[] = new JFXButton[4];
        for (int i = 0; i < btn.length; i++) {
            btn[i] = new JFXButton();
            btn[i].setLayoutX(15);
            if (i!=0) {
                btn[i].setLayoutY(btn[i-1].getLayoutY()+85);
            }else{
                btn[i].setLayoutY(20);
            }
            btn[i].setMaxSize(150, 50);
            btn[i].setMinSize(150, 50);
            btn[i].setStyle("-fx-background-radius:20px;-fx-background-color: #008b8b;");
            btn[i].setFont(Font.font(18));
            panel.getChildren().add(btn[i]);
        }
        btn[0].setText("Eliminar Fichero");
        btn[0].setOnAction((ActionEvent)->{
           ficheros FicheroAEliminar=  ListaVisual.getSelectionModel().getSelectedItem();
            if (FicheroAEliminar==null) {
               JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista");
            }else{
            FicheroAEliminar.eliminarme();
           myLista.remove(ListaVisual.getSelectionModel().getSelectedItem());
            }
        });
        btn[1].setText("Abrir Fichero");
        btn[1].setOnAction((ActionEvent e)->{
            ficheros FicheroAEjecutar=  ListaVisual.getSelectionModel().getSelectedItem();
            if (FicheroAEjecutar==null) {
               JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista");
            }else{
            abrirarchivo(FicheroAEjecutar.getLocacion());
            }
        });
        btn[2].setText("Actualizar");
        btn[2].setOnAction((Event)->{
             ActualizarLista();
        });
        btn[3].setText("Atras...");
        btn[3].setOnAction((ActionEvent)->{
            
            VentanaLista = null;
            this.close();
            principal.show.fire();
        });
         ListaVisual.setOnMouseClicked((MouseEvent e)->{
            ficheros ficheroSeleccionado=  ListaVisual.getSelectionModel().getSelectedItem();
            if (ficheroSeleccionado!=null) {
                txtTamaño.setText("Tamaño : "  +ficheroSeleccionado.getTamaño() + "kb");
                txtLocacion.setText("Ubicacion : "  +ficheroSeleccionado.getLocacion());
                
                BufferedImage imagenBuffered = null;
                try {
                    imagenBuffered = ImageIO.read(new File(ficheroSeleccionado.getLocacion()));
                    Image imageSeleccionada = SwingFXUtils.toFXImage(imagenBuffered,null);
                    visor.setImage(imageSeleccionada);
                 } catch (java.net.MalformedURLException ex) {
                     visor.setImage(null);
                } catch (IOException ex) {
                         visor.setImage(new Image(getClass().getResource("fotoicon.png").toString()) );
                }
                 catch (NullPointerException ex) {
                          visor.setImage(new Image(getClass().getResource("fotoicon.png").toString()) );
                }
             }
         });
        
         visor.setFitHeight(200);
         visor.setFitWidth(180);
         visor.setLayoutX(20);
         visor.setLayoutY(335);         
         txtTamaño.setLayoutX(20);
         txtTamaño.setLayoutY(565);
         txtLocacion.setLayoutX(20);
         txtLocacion.setLayoutY(585);
         panel.getChildren().add(txtTamaño);
         panel.getChildren().add(txtLocacion);
         panel.getChildren().add(visor);
         visor.setImage(new Image(getClass().getResource("fotoicon.png").toString()) );
         
       panel.getChildren().add(ListaVisual);
        VentanaDeLista.setTitle("Lista de ficheros");
        VentanaDeLista.setResizable(false);
        VentanaDeLista.setMaximized(false);
        VentanaDeLista.setOnCloseRequest((Event)->{
            VentanaLista = null;
            principal.show.fire();
        });
        VentanaDeLista.setScene(scene);
        VentanaDeLista.show();
     }
    public void abrirarchivo(String archivo){

     try {

            File objetofile = new File (archivo);
            Desktop.getDesktop().open(objetofile);

     }catch (IOException ex) {
            System.out.println(ex);
     }
} 
    
    public void ActualizarLista(){
        ArrayList listaDeFicheros = principal.getFicherosAlmecenados();
        myLista = FXCollections.observableList(listaDeFicheros);
        ListaVisual.setItems(myLista);
    }
    public static  void abreVentanaLista(){
        if (VentanaLista==null){
            VentanaLista= new ListaDeFicheros();
        }else{
            VentanaLista.toFront();
        }
    }
    
    public static ListaDeFicheros VentanaLista;
}