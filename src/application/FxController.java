package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class FxController implements Initializable{
	
	@FXML
	private TextField config;
	@FXML
	private TextField min_inteval;
	@FXML
	private TextField max_interval;
	@FXML
	private TextField duration;
	@FXML
	private TextField seed;
	
	public static String python = "/usr/bin/python3.6";
	public static String directory;
	public static String filePath;
	final FileChooser fileChooser = new FileChooser();
	private BufferedReader bufRead;
	private Process process;
	
    @FXML
    private void handleRunButtonAction(ActionEvent event) {
    	
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				String s = null;
		        try {
		        	String command = python+" "+directory+"/install_app.py";
		            process = Runtime.getRuntime().exec(command); 
		     
		            BufferedReader stdInput = new BufferedReader(new 
		                InputStreamReader(process.getInputStream()));

		            while ((s = stdInput.readLine()) != null) {
		                System.out.println(s);
		            }
		        }
		        catch (IOException e) {
		        	System.out.println("exception happened: ");
		            e.printStackTrace();
		            System.exit(-1);
		        }
				
			}
		}).start();
    }
    
    @FXML
    private void handleStopButtonAction(ActionEvent event) {
		process.destroy();
		try {
			String command = python+" "+directory+"/kill_emulator.py";
			process = Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @FXML
    private void handleBeowseButtonAction() {
    	File file = fileChooser.showOpenDialog(Main.Pstage);
        if (file != null) {
            openFile(file);
        }
		
	}
    
    private void openFile(File file) {
        try {
        	directory = file.getParentFile().toString();
        	filePath = file.getAbsolutePath().toString();
            config.setText(filePath);
            loadProperties();
        } catch (Exception ex) {
            ex.setStackTrace(null);
        }
    }
    
    private void loadProperties() throws IOException {	

    	FileReader input = new FileReader(filePath);
    	bufRead = new BufferedReader(input);
    	String myLine = null;

    	while ( (myLine = bufRead.readLine()) != null)
    	{    
    	    try {
    	    	String[] array = myLine.split(": ");
        	    
        	    if(array[0].equals("seed")) {
        	    	seed.setText(array[1]);
        	    }
        	    if(array[0].equals("minimum_interval")) {
        	    	min_inteval.setText(array[1]);
        	    }
        	    if(array[0].equals("maximum_interval")) {
        	    	max_interval.setText(array[1]);
        	    }
        	    if(array[0].equals("duration")) {
        	    	duration.setText(array[1]);
        	    }
        	    
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	input.close();
		
	}

    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        config.setDisable(true);
    }   
	
}
