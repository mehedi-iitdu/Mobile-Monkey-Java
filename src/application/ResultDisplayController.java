package application;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class ResultDisplayController {

    @FXML
    private ListView<String> leftListView;

    @FXML
    private ListView<String> rightListView;

    @FXML
    private Text leftListOptionSelectedText;

    @FXML
    private Text rightListOptionSelectedText;
    
    public void initialize() throws IOException{
    	
        ObservableList<String> leftItems = FXCollections.observableArrayList (
                "A", "B","C", "D");
        leftListView.setItems(leftItems);
        ObservableList<String> rightItems = FXCollections.observableArrayList();
        rightListView.setItems(rightItems);
        
        leftListView.getSelectionModel().selectedItemProperty().addListener(
                
        		new ChangeListener<String>() {
                	
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    	
                    	leftListOptionSelectedText.setText(newValue);
                    	if(!rightItems.isEmpty())rightItems.clear();
						for(int i=1;i<=5;i++) {
							rightItems.add(newValue+" "+i);
						}
						
                    }
                }
        		
        );
        
        rightListView.getSelectionModel().selectedItemProperty().addListener(
        			new ChangeListener<String>() {
                	
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    	rightListOptionSelectedText.setText(newValue);
                    	}
                    }
        );
    }

}
