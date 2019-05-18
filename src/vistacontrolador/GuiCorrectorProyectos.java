
package vistacontrolador;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.CorrectorProyectos;

/* Autor: Isa Kapov*/

public class GuiCorrectorProyectos extends Application
{

	private MenuItem itemLeer;
	private MenuItem itemGuardar;
	private MenuItem itemSalir;

	private TextField txtAlumno;
	private Button btnVerProyecto;

	private RadioButton rbtAprobados;
	private RadioButton rbtOrdenados;
	private Button btnMostrar;

	private TextArea areaTexto;

	private Button btnClear;
	private Button btnSalir;

	private CorrectorProyectos corrector; // el modelo

	@Override
	public void start(Stage stage) {

		corrector = new CorrectorProyectos();

		BorderPane root = crearGui();

		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.setTitle("- Corrector de proyectos -");
		scene.getStylesheets().add(getClass()
		                .getResource("/css/application.css").toExternalForm());
		stage.show();
	}

	private BorderPane crearGui() {

		BorderPane panel = new BorderPane();
		MenuBar barraMenu = crearBarraMenu();
		panel.setTop(barraMenu);

		VBox panelPrincipal = crearPanelPrincipal();
		panel.setCenter(panelPrincipal);

		HBox panelBotones = crearPanelBotones();
		panel.setBottom(panelBotones);

		return panel;
	}

	private MenuBar crearBarraMenu() {

		MenuBar barraMenu = new MenuBar();

		Menu menu = new Menu("Archivo");

		itemLeer = new MenuItem("_Leer de fichero");
		itemLeer.setAccelerator(KeyCombination.keyCombination("CTRL+L"));
		itemLeer.setOnAction(e -> leerDeFichero());
		
		itemGuardar = new MenuItem("_Guardar");
		itemGuardar.setAccelerator(KeyCombination.keyCombination("CTRL+G"));
		itemGuardar.setDisable(true);
		itemGuardar.setOnAction(e -> salvarEnFichero());
		
		itemSalir = new MenuItem("_Salir");
		itemSalir.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
		itemSalir.setOnAction(e -> salir());
		
		menu.getItems().addAll(itemLeer, itemGuardar, new SeparatorMenuItem(), itemSalir);
		barraMenu.getMenus().add(menu);
		
	
		return barraMenu;
	}

	private VBox crearPanelPrincipal() {

		VBox panel = new VBox();
		panel.setPadding(new Insets(5));
		panel.setSpacing(10);
	
		Label lblEntrada1 = new Label("Panel de entrada");
		lblEntrada1.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		lblEntrada1.getStyleClass().add("titulo-panel");
		panel.getChildren().addAll(lblEntrada1, crearPanelEntrada());
		
		Label lblEntrada2 = new Label("Panel de opciones");
		lblEntrada2.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		lblEntrada2.getStyleClass().add("titulo-panel");
	
		panel.getChildren().addAll(lblEntrada2, crearPanelOpciones());	
		
		areaTexto = new TextArea();
		areaTexto.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		areaTexto.setPrefHeight(Integer.MAX_VALUE);
		areaTexto.setPrefWidth(Integer.MAX_VALUE);
		panel.getChildren().add(areaTexto);
		
		


		return panel;
	}

	private HBox crearPanelEntrada() {

		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		panel.setSpacing(10);

		Label lblAlumno = new Label("Alumno");
		
		
		txtAlumno = new TextField();
		txtAlumno.setOnAction(e -> verProyecto());
		txtAlumno.setPrefColumnCount(30);

		btnVerProyecto = new Button("Ver Proyecto");
		btnVerProyecto.setPrefWidth(120);
		btnVerProyecto.setOnAction(e -> verProyecto());


		panel.getChildren().addAll(lblAlumno, txtAlumno, btnVerProyecto);

		return panel;
	}

	private HBox crearPanelOpciones() {

		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		panel.setSpacing(50);
		panel.setAlignment(Pos.CENTER);

		rbtAprobados = new RadioButton("Mostrar aprobados");
		rbtAprobados.setSelected(true);
		
		rbtOrdenados = new RadioButton("Mostrar ordenados");
		
		
		ToggleGroup group = new ToggleGroup();
		rbtAprobados.setToggleGroup(group);
		rbtOrdenados.setToggleGroup(group);
		
		btnMostrar = new Button("Mostrar");
		btnMostrar.setOnAction(e -> mostrar());
		btnMostrar.setPrefWidth(120);
		
		
		panel.getChildren().addAll(rbtAprobados, rbtOrdenados, btnMostrar);
		
		return panel;
	}

	private HBox crearPanelBotones() {

		HBox panel = new HBox();
		panel.setPadding(new Insets(5));
		panel.setAlignment(Pos.CENTER_RIGHT);
		panel.setSpacing(10);
		
		
		btnClear = new Button("Clear");
		btnClear.setPrefWidth(90);
		btnClear.setOnAction(e -> clear());
		
		
		btnSalir = new Button("Salir");
		btnSalir.setPrefWidth(90);
		btnSalir.setOnAction(e -> salir());
		
		
		panel.getChildren().addAll(btnClear, btnSalir);
		return panel;
	}

	private void salvarEnFichero() {
	
		try {
			corrector.guardarOrdenadosPorNota();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void leerDeFichero() {

		itemLeer.setDisable(true);
		itemGuardar.setDisable(false);

	}

	private void verProyecto() {

	    corrector.proyectoDe(txtAlumno.getText());
		if(corrector == null)
		{
			areaTexto.setText("No se han leído todavía los datos del fichero\n + "
					+ "Vaya a la opción leer del menú");
		}
		
		else if(txtAlumno.getText().isEmpty())
		{
			areaTexto.setText("Teclee nombre de alumno");	
		}
		
		
		cogerFoco();

	}

	private void mostrar() {

		clear();
		
		if(itemLeer.isDisable() == false)
		{
			areaTexto.setText("No se han leído todavía los datos del fichero\n" + 
					"Vaya a la opción leer del menú");
		}
		
		else if(rbtAprobados.isSelected())
		{
			areaTexto.setText("Han aprobado el proyecto " +  corrector.aprobados() +   " alumnos/as");
		}
		else if(rbtOrdenados.isSelected())
		{
			areaTexto.setText(corrector.ordenadosPorNota().toString());
		}

		cogerFoco();

	}

	private void cogerFoco() {

		txtAlumno.requestFocus();
		txtAlumno.selectAll();

	}

	private void salir() {

		System.exit(0);
	}

	private void clear() {

		areaTexto.clear();
		cogerFoco();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
