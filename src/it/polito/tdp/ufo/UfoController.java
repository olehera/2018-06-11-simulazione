package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {
	
	private Model model;
	
	public void setModel(Model model) {
		this.model = model;
		boxAnno.getItems().addAll(this.model.getAnni());
	}

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Anno> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<String> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	Anno scelto = boxAnno.getValue();
    	
    	if (scelto == null) {
    		txtResult.setText("Devi selezionare un anno!");
    		return ;
    	}
    	
    	model.creaGrafo(scelto.getAnno());
    	
    	boxStato.getItems().clear();
    	boxStato.getItems().addAll(model.getStati());
    }
    
    @FXML
    void handleAnalizza(ActionEvent event) {
    	String scelto = boxStato.getValue();
    	
    	if (scelto == null) {
    		txtResult.setText("Devi selezionare uno stato!");
    		return ;
    	} else
    		model.setStato(scelto);
    	
    	txtResult.setText("Stati Precedenti:\n");
    	for (String s: model.precedenti())
    		txtResult.appendText(s+"\n");
    	
    	txtResult.appendText("\n\nStati Successivi:\n");
    	for (String s: model.successivi(scelto))
    		txtResult.appendText(s+"\n");
    	
    	List<String> raggiungibili = model.raggiungibili();
    	txtResult.appendText("\n\nStati Raggiungibili: "+raggiungibili.size()+"\n");
    	for (String s: raggiungibili)
    		txtResult.appendText(s+"\n");
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	txtResult.appendText("\n\n\nCammino più lungo di avvistamenti successivi:\n");
    	for (String s: model.trovaCammino())
    		txtResult.appendText(s+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";
    }
    
}