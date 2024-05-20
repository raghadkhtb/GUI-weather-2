import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.*;

public class WeatherApp {
	
	private static JFrame frame;
	private static JTextField locationField;
	private static JTextArea weatherDisplay;
	private static JButton fetchButton;
	private static String apiKey = "7b2f68730bc7e8d08574612993939db7&units=metric";
	private static String fetchWeatherData(String city) {
		try {
			URL url = new URL ("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = "";
			String line;
			
			while((line = reader.readLine()) != null) {
				response += line;
			}
			
			reader.close();
			//go to site json simple.
			JSONObject jsonObject = (JSONObject) JSONValue.parse(response.toString());
			//we can replace the word main with anything depending on our need.
			JSONObject mainObj = (JSONObject) jsonObject.get("main");
			
			//now we identify the variable for temperature and use double because it has decimal nbs.
			
			double temperature = (double) mainObj.get("temp");
			//humidity 
			long humidity = (long) mainObj.get("humidity");
			 //now we import from the api to get the weather:
			 JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
			 JSONObject weather = (JSONObject) jsonObject.get(0);
			 String description = (String) weather.get("description");
			 return "Description" + description + "\ntemperature: " + temperature + "℃" + "\nhumidity: " + humidity + "%"; // \n means new line.
			
			
		} catch (Exception e) {
			return "Failed to fetch weather. Please check your internet";
		}
	}

	public static void main (String [] args) {
		frame = new JFrame("How is the weather?");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,300);
		frame.setLayout(new FlowLayout());
		
		locationField = new JTextField(15);
		fetchButton = new JButton("Submit");
		weatherDisplay = new JTextArea(10,30);
		weatherDisplay.setEditable(false);
		
		frame.add(new JLabel ("Enter City Name: "));
		frame.add(locationField);
		frame.add(fetchButton);
		frame.add(weatherDisplay);
		
		fetchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String city = locationField.getText();
				String weatherData = fetchWeatherData(city);
				weatherDisplay.setText(weatherData);
				
			}
		});
	
		frame.setVisible(true);
		
	}
}

 