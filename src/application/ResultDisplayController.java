package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
                "Fatal", "Error","Warning");
        leftListView.setItems(leftItems);
        ObservableList<String> rightItems = FXCollections.observableArrayList();
        rightListView.setItems(rightItems);
        
        leftListView.getSelectionModel().selectedItemProperty().addListener(
                
        		new ChangeListener<String>() {
                	
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    	
                    	leftListOptionSelectedText.setText(newValue);
                    	
                    	if(!rightItems.isEmpty())rightItems.clear();
						
                    	FileInputStream fis = null;
                        BufferedReader reader = null;
 
						try {
							
							fis = new FileInputStream(FxController.logaddress);
				            reader = new BufferedReader(new InputStreamReader(fis));

						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	
                    	try {
                    		String myLine;
                    		
							while ( (myLine = reader.readLine()) != null)
							{
								if(newValue == "Error"){
									if(myLine.contains("E")){
										String[] array = myLine.split("E ");
										String[] array2 = array[1].split(":");
										rightItems.add(array2[0]);
									}
		                    	}
								
								if(newValue == "Warning"){
									if(myLine.contains("W")){
										String[] array = myLine.split("W ");
										String[] array2 = array[1].split(":");
										rightItems.add(array2[0]);
									}
		                    	}
								
								if(newValue == "Fatal"){
									if(myLine.contains("F")){
										String[] array = myLine.split("F ");
										String[] array2 = array[1].split(":");
										rightItems.add(array2[0]);
									}
		                    	}
								
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
