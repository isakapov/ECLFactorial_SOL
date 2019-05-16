package ut9.factorial;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiFactorial extends Application
{
	private TextField txtNumero;
	private Label lblResultado;
	private Button btnFactorial;
	private Button btnPrimo;
	private Button btnClear;
	private Button btnSalir;
	private MenuItem itmFactorial;
	private MenuItem itmPrimo;
	private MenuItem itmClear;
	private MenuItem itmSalir;

	private Numero numero; // el modelo

	@Override
	public void start(Stage primaryStage)
	{

		numero = new Numero();
		BorderPane root = crearGui();

		Scene scene = new Scene(root, 500, 400);
		scene.getStylesheets().add(
		        getClass().getResource("/application.css").toExternalForm());
		cogerFoco();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Numero - Factorial y primo");
		primaryStage.show();

	}

	private BorderPane crearGui()
	{
		BorderPane panel = new BorderPane();
		panel.setTop(crearBarraMenu());
		panel.setCenter(crearPanelPrincipal());
		return panel;
	}

	
	private MenuBar crearBarraMenu()
	{
		MenuBar barra = new MenuBar();
		Menu menu = new Menu("Opciones");
		itmFactorial = new MenuItem("_Factorial");
		itmFactorial.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
		itmFactorial.setOnAction(e -> factorial());
		
		
		itmPrimo = new MenuItem("_Primo");
		itmPrimo.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
		itmPrimo.setOnAction(e -> primo());
		
		itmClear = new MenuItem("_Clear");
		itmClear.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
		itmClear.setOnAction(e -> clear());
	 
		
		itmSalir = new MenuItem("_Salir");
		itmSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		itmSalir.setOnAction(e -> salir());
		
		menu.getItems().addAll(itmFactorial , itmPrimo , itmClear , new SeparatorMenuItem(), itmSalir);
		barra.getMenus().add(menu);
		return barra;

	}
	

	private BorderPane crearPanelPrincipal()
	{
		BorderPane panel = new BorderPane();
		panel.setCenter(crearPanelCentral());
		panel.setRight(crearPanelBotones());
		return panel;
	}

	private BorderPane crearPanelCentral()
	{
		BorderPane panel = new BorderPane();
		
		txtNumero = new TextField();
		txtNumero.setOnAction(event -> factorial());
		panel.setTop(txtNumero);
		
		
		lblResultado = new Label("Aqui se mostrara el resultado");
		lblResultado.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);		
		lblResultado.setAlignment(Pos.CENTER);
		panel.setCenter(lblResultado);
		panel.setId("resultado");
		
		return panel;
	}

	private VBox crearPanelBotones()
	{
		VBox panel = new VBox();
		panel.setPadding(new Insets(5, 10, 10, 5));
		panel.setSpacing(10);
		
		
		btnFactorial = new Button("_Factorial");
		btnFactorial.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		btnFactorial.setMnemonicParsing(true);
		VBox.setVgrow(btnFactorial, Priority.ALWAYS);
		btnFactorial.setOnAction(e -> factorial());
		
		
		 

		btnPrimo = new Button("_Primo");
		btnPrimo.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		btnPrimo.setMnemonicParsing(true);
		VBox.setVgrow(btnPrimo, Priority.ALWAYS);
		btnPrimo.setOnAction(e -> primo());
		
		
		btnClear = new Button("_Clear");
		btnClear.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		btnClear.setMnemonicParsing(true);
		VBox.setVgrow(btnClear, Priority.ALWAYS);
		btnClear.setOnAction(e -> clear());
		cogerFoco();
		
		btnSalir = new Button("_Salir");
		btnSalir.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		btnSalir.setMnemonicParsing(true);
		VBox.setVgrow(btnSalir, Priority.ALWAYS);
		btnSalir.setOnAction(e -> salir());
		
		panel.getChildren().addAll(btnFactorial ,btnPrimo , btnClear , btnSalir);
		return panel;
	}

	private void salir()
	{
		Platform.exit();
	}

	private void clear()
	{
		txtNumero.setText("");
		lblResultado.setText("");
	}

	private void primo()
	{
		try {
			
			if (txtNumero.getText().isEmpty()) {
				lblResultado.setText("Introduce valor numerico");
			}else {
				int valor = Integer.parseInt(txtNumero.getText());
				numero.setNumero(valor);
				boolean prim = numero.esPrimo();
				lblResultado.setText(String.valueOf(prim));
			}
		} catch (NumberFormatException e){
			lblResultado.setText("solo valores numericos");
		}finally {
			cogerFoco();
		}
	}



	private void factorial()
	{
		

		try {
			
			if (txtNumero.getText().isEmpty()) {
				lblResultado.setText("Introduce valor numerico");
			}else {
				int valor = Integer.parseInt(txtNumero.getText());
				numero.setNumero(valor);
				int facto = numero.factorial();
				lblResultado.setText(String.valueOf("El numero factorial es: " + "\n" + facto));
			}
		} catch (NumberFormatException e){
			lblResultado.setText("solo valores numericos");
		}finally {
			cogerFoco();
		}
	}

	private void cogerFoco()
	{
		txtNumero.requestFocus();
		txtNumero.selectAll();

	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
