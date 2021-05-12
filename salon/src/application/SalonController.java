package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SalonController implements Initializable{

    @FXML
    private TextField txtCout;

    @FXML
    private TableColumn<Salon, String> prenomColumn;

    @FXML
    private ComboBox<String> cboProcedure;

    @FXML
    private TextField txtPrenom;

    @FXML
    private Button btnClear;

    @FXML
    private TextField txtAge;

    @FXML
    private Button btnEffacer;

    @FXML
    private TableView<Salon> salonTable;

    @FXML
    private TableColumn<Salon, Double> coutColumn;

    @FXML
    private TableColumn<Salon, Double> ageColumn;

    @FXML
    private Button btnModifier;

    @FXML
    private TableColumn<Salon, String> nomColumn;

    @FXML
    private TableColumn<Salon, String> procedureColumn;

    @FXML
    private Button btnAjouter;

    @FXML
    private TextField txtNom;
    
    
    //liste pour les departements - cette liste permettra de donner les valeurs du comboBox
    private ObservableList<String> list=(ObservableList<String>) FXCollections.observableArrayList("Cheveux","Ongles","Fartage","Massages");
    
  //placer les etudiants dans une observable list
  	public ObservableList<Salon> salonData=FXCollections.observableArrayList();
  	
  	
  //creer une methode pour acceder a la liste des etudiants
  	public ObservableList<Salon> getsalonData()
  	{
  		return salonData;
  	}
    

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	
	{
		cboProcedure.setItems(list);

		//attribuer les valeurs aux colonnes du tableView
		prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		procedureColumn.setCellValueFactory(new PropertyValueFactory<>("Procedure"));
		coutColumn.setCellValueFactory(new PropertyValueFactory<>("cout"));
		salonTable.setItems(salonData);

		btnModifier.setDisable(true);
		btnEffacer.setDisable(true);
		btnClear.setDisable(true);	
		
		showClients(null);
		
		//Metre a jour l'affichage d'un de clients selectionne
				salonTable.getSelectionModel().selectedItemProperty().addListener((
						observable, oldValue, newValue)-> showClients(newValue));	

	}
	
	//methode pour trouver des imputs non numeriques
		public void verifNum()
		{
			txtAge.textProperty().addListener((observable,oldValue,newValue)->
			{
				if(!newValue.matches("^[0-9](\\.[0-9]+)?$"))
				{
					txtAge.setText(newValue.replaceAll("[^\\d*\\.]","")); //si le imput est non numerique, ca le remplace
			}
			});
		}
	
	//Ajouter un cliente
		@FXML
		void ajouter() 
		{
			//verrifier si un champ n'est pas vide
			if(noEmptyInput())
			{

			Salon tmp=new Salon();
			tmp=new Salon();
			tmp.setNom(txtNom.getText());
			tmp.setPrenom(txtPrenom.getText());
			tmp.setAge(Double.parseDouble(txtAge.getText()));
			tmp.setProcedure(cboProcedure.getValue());
			tmp.setCout(Double.parseDouble(txtCout.getText()));
			salonData.add(tmp);
			clearFields();
			}
			
			}

		//Effacer le contenu des champs
		@FXML
		void clearFields()
		{
			cboProcedure.setValue(null);
			txtNom.setText("");
			txtPrenom.setText("");
			txtAge.setText("");
			txtCout.setText("");
		}	
		
		//Afficher les clientes
		public void showClients(Salon salon)
		{
			if(salon !=null)
			{
				cboProcedure.setValue(salon.getProcedure());
				txtNom.setText(salon.getNom());
				txtPrenom.setText(salon.getPrenom());
				txtAge.setText(Double.toString(salon.getAge()));	
				txtCout.setText(Double.toString(salon.getAge()));
				btnModifier.setDisable(false);
				btnEffacer.setDisable(false);
				btnClear.setDisable(false);
			}

			else
			{
				clearFields();
			}
		}
		
	
		//mise a jour d'un client
		@FXML
		public void updateClient()
		{
			//verrifier si un champ n'est pas vide
			if(noEmptyInput())
			{
			Salon salon=salonTable.getSelectionModel().getSelectedItem();
			
			salon.setNom(txtNom.getText());
			salon.setPrenom(txtPrenom.getText());
			salon.setAge(Double.parseDouble(txtAge.getText()));
			salon.setProcedure(cboProcedure.getValue());
			salon.setCout(Double.parseDouble(txtCout.getText()));
			salonTable.refresh();
			}
		}
		
		//effacer un client
		@FXML
		public void deleteClient()
		{
			
			int selectedIndex = salonTable.getSelectionModel().getSelectedIndex();
			if (selectedIndex >=0)
			{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Effacer");
				alert.setContentText("confirmer la suppression!");
				Optional<ButtonType> result=alert.showAndWait();
				if(result.get()==ButtonType.OK)
				salonTable.getItems().remove(selectedIndex);
			}
			
		}
		
		//verrifier champs vides
		private boolean noEmptyInput()
		{
			String errorMessage="";
			if(txtNom.getText()==null||txtNom.getText().length()==0)
			{
				errorMessage+="Le champ nom ne doit pas etre vide! \n";
			}
			if(txtPrenom.getText()==null||txtPrenom.getText().length()==0)
			{
				errorMessage+="Le champ prenom ne doit pas etre vide! \n";
			}
			if(txtAge.getText()==null||txtAge.getText().length()==0)
			{
				errorMessage+="Le champ age ne doit pas etre vide! \n";
			}
			if(txtCout.getText()==null||txtCout.getText().length()==0)
			{
				errorMessage+="Le champ cout ne doit pas etre vide! \n";
			}
			if(cboProcedure.getValue()==null)
			{
				errorMessage+="Le champ Procedure ne doit pas etre vide! \n";
			}
			
			if(errorMessage.length()==0)
			{
				return true;
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Champs manquants");
				alert.setHeaderText("Completer les champs manquants");
				alert.setContentText(errorMessage);
				alert.showAndWait();
				return false;
			}
		}
		
		//Afficher les statistiques
		@FXML
		void handleStats()
		{
			try {
				FXMLLoader loader=new FXMLLoader(Main.class.getResource("AgeStat.FXML"));
				AnchorPane pane=loader.load();
				Scene scene=new Scene(pane);
				AgeStat agestat=loader.getController();
				agestat.SetStats(salonData);
				Stage stage=new Stage();
				stage.setScene(scene);
				stage.setTitle("Statistiques");
				stage.show();
			} catch (IOException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//sauvegard des donness
		//recuperer la chemin (path) de fichiers si ca existe
		public File getSalonFilePath()
		{
			
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
			String filePath = prefs.get("filePath", null);
			
			if(filePath != null)
			{
				return new File(filePath);
			}
			
			else
			{
				return null;
			}
		}
		
		//Attribuer un chemin de fichiers
		public void setSalonFilePath(File file)
		{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
			if (file != null)
			{
				prefs.put("filePath", file.getPath());
			}
			
			else
			{
				prefs.remove("filePath");
			}
		}
		
		//prendre les donnees de type XML et les convertir en donnees de type javaFx
		public void loadClientDataFromFile(File file)
		{
			try {
				JAXBContext context = null;
				
					context = JAXBContext.newInstance(SalonListWrapper.class);
				
				Unmarshaller um = null;

					um = context.createUnmarshaller();
				
					SalonListWrapper wrapper = null;

					wrapper = (SalonListWrapper) um.unmarshal(file);
				
				salonData.clear();
				salonData.addAll(wrapper.getClients());
				setSalonFilePath(file);
				
				//donner le titre du fichier charge
				Stage pStage=(Stage) salonTable.getScene().getWindow();
				pStage.setTitle(file.getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
			
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("les donnees n'ont pas ete trouves");
			alert.setContentText("Les donnees ne povaient pas etre trouves dans le fishier : \n ");
			alert.showAndWait();}
			
		}
		
		//prendre les donnees de type JavaFx et les convertir en type XML
		public void saveClientDataToFile(File file) {
		
			try {
				JAXBContext context = JAXBContext.newInstance(SalonListWrapper.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				SalonListWrapper wrapper = new SalonListWrapper();
				wrapper.setClients(salonData);
				
				m.marshal(wrapper, file);
				
				//sauvegarde dans le registre
				setSalonFilePath(file);
				
				//donner le titre du fichier sauvegarder
				Stage pStage=(Stage) salonTable.getScene().getWindow();
				pStage.setTitle(file.getName());
				
			} catch (Exception e) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("donnees non sauvegardees");
				alert.setContentText("Les donnees ne povaient pas etre sauvegardees dans le fishier : \n " + file.getPath());
				alert.showAndWait();
			}
		
		}
		
		// commencer un nouveau
		@FXML
		private void handleNew()
		{
			getsalonData().clear();
			setSalonFilePath(null);
		}
		
		/*
		 * Le FileChooser permet a l'usager de choisir le fichier a ouvrir
		 */
		@FXML
		private void handleOpen() {
			FileChooser fileChooser = new FileChooser();
			
			// permettre un filtre sur l'extension du fichier a cercher
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
					"XML files (*.xml)", "*.xml");
			fileChooser.getExtensionFilters().add(extFilter);
			
			//montrer le dialogue
			File file = fileChooser.showOpenDialog(null);
			
			if (file != null) {
				loadClientDataFromFile(file);
			}
		}
		
		/*
		 * Sauvegarde le fichier correspondant a l'etudiant actif
		 * s'il n y a pas de fichier,le menu sauvegarder sous va s'afficher
		 */
		@FXML
		private void handleSave() {
			
			File etudiantFile = getSalonFilePath();
			if (etudiantFile != null) {
				saveClientDataToFile(etudiantFile);
			
			} else {
				handleSaveAs();
			}
		 }
		
		/*
		 * Ouvrir le FileChooser pour trouver le chemin
		 */
		@FXML
		private void handleSaveAs() {
			FileChooser fileChooser = new FileChooser();
			
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
					"XML files (.xml)", ".xml");
			fileChooser.getExtensionFilters().add(extFilter);
			
			//sauvegarder
			File file = fileChooser.showSaveDialog(null);
			
			if (file != null) {
				// verification de l'extension
				if (!file.getPath().endsWith(".xml")) {
					file = new File(file.getPath() + ".xml"); 
			}
			saveClientDataToFile(file); }
			}	
		
		
		
		}
    
    
    
    
    
    
    
    

