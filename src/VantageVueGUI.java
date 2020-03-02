

import org.json.JSONException;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.border.LineBorder;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class creates the user interface for viewing weather data.
 */
public class VantageVueGUI extends JFrame {

	private static final long serialVersionUID = 3196082805354390538L;

	private SensorSuiteSimulator sim;
	private WeatherStation w;
	private final ButtonGroup buttonGroup;
	
	
	double sunriseMinutes;
	double sunsetMinutes;
	
	private JPanel pnlMain;
	private JPanel pnlButtonPanel;
	private JPanel pnlContentPane;
	
	private JLabel lblInside;
	private JLabel lblOutside;
	
	private JToggleButton btnTemperature;
	private JToggleButton btnHumidity;
	private JToggleButton btnBarometricPressure;
	private JToggleButton btnRainfall;
	
	private JLabel lblLeftMain;
	private JLabel lblRightMain;
	private JLabel lblTopMain;
	private JLabel lblTime;
	private JLabel lblClock;
	private JLabel lblSunriseTime;
	private JLabel lblSunsetTime;

	private JLabel lblWindSpeed;
	
	private JLabel lblForecastMain;
	private JLabel lblForecastDescrip;
	private JLabel lblForecastIcon;
	
	private JLabel N;
	private JLabel S;
	private JLabel E;
	private JLabel W;
	private JLabel NW;
	private JLabel NE;
	private JLabel SW;
	private JLabel SE;
	

	/*
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VantageVueGUI frame = new VantageVueGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Create the frame.
	 */
	
	public VantageVueGUI() throws JSONException {
		setResizable(false);
		w = new WeatherStation(30);
		sim = new SensorSuiteSimulator(68, 71, 47, 57, 10, 90, 70, 50, 40);

	
		try {
			final URL url = VantageVueGUI.class.getResource("radar.png");
	        final ImageIcon icon = new ImageIcon(url);
			setIconImage(icon.getImage());
		} catch(Exception e) {
			System.out.println("IDE not configured for resources folder");
		}

		
		setTitle("Weather Center");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 366);
		pnlContentPane = new JPanel();
		pnlContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlContentPane);
		pnlContentPane.setLayout(null);
		buttonGroup = new ButtonGroup();
		pnlMain = new JPanel() {
			private static final long serialVersionUID = 5190846455870872483L;

			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				Shape circle = new Ellipse2D.Double(0, 40, 150, 150);
				g2.draw(circle);

			}
		};
		pnlMain.setFont(new Font("Tahoma", Font.PLAIN, 9));
		pnlMain.setBorder(null);
		pnlMain.setBackground(Color.WHITE);
		pnlMain.setBounds(10, 11, 405, 316);
		pnlContentPane.add(pnlMain);
		pnlMain.setLayout(null);
		
		pnlButtonPanel = new JPanel();
		pnlButtonPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		pnlButtonPanel.setBounds(415, 11, 169, 210);
		pnlContentPane.add(pnlButtonPanel);
		pnlButtonPanel.setLayout(null);
		
		createStaticLabels();
		createDynamicLabels();
		createButtons();
		createTimers();
		
		
	}
	/*
	 * This is a helper method that creates all of the buttons.
	 */
	private void createButtons()  {
		btnTemperature = new JToggleButton("TEMPERATURE");
		btnTemperature.setName("temperature");
		buttonGroup.add(btnTemperature);
		btnTemperature.setSelected(true);
		btnTemperature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  {

				lblInside.setVisible(true);
				lblOutside.setVisible(true);
				lblLeftMain.setText(Integer.toString(sim.getCurrentInsideTemp()) + " F");
				try {
					lblRightMain.setText(w.getCurrentTemp());
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
				lblTopMain.setText("Temperature");
				repaint();
			}
		});
		btnTemperature.setBackground(Color.LIGHT_GRAY);
		btnTemperature.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTemperature.setBounds(7, 5, 154, 30);
		pnlButtonPanel.add(btnTemperature);
		
		btnHumidity = new JToggleButton("HUMIDITY");
		btnHumidity.setName("humidity");
		buttonGroup.add(btnHumidity);
		btnHumidity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblLeftMain.setText(Integer.toString(sim.getCurrentInsideHum()) + " %");
				try {
					lblRightMain.setText(w.getCurrentHumid());
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
				lblInside.setVisible(true);
				lblOutside.setVisible(true);
				lblTopMain.setText("Humidity");
				repaint();
			}
		});
		btnHumidity.setBackground(Color.LIGHT_GRAY);
		btnHumidity.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnHumidity.setBounds(7, 38, 154, 30);
		pnlButtonPanel.add(btnHumidity);
		
		btnBarometricPressure = new JToggleButton("BAROMETRIC PRESSURE");
		btnBarometricPressure.setName("pressure");
		buttonGroup.add(btnBarometricPressure);
		btnBarometricPressure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lblLeftMain.setText(w.getCurrentPressure());
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
				lblRightMain.setText("hPa");
				lblInside.setVisible(false);
				lblOutside.setVisible(false);
				lblTopMain.setText("Barometric Pressure");
				repaint();
			}
		});
		btnBarometricPressure.setBackground(Color.LIGHT_GRAY);
		btnBarometricPressure.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnBarometricPressure.setBounds(7, 71, 154, 30);
		pnlButtonPanel.add(btnBarometricPressure);
		
		btnRainfall = new JToggleButton("RAINFALL");
		btnRainfall.setName("rainfall");
		buttonGroup.add(btnRainfall);
		btnRainfall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblLeftMain.setText(Double.toString(sim.getCurrentRainTotal()));
				lblRightMain.setText("inches");
				lblInside.setVisible(false);
				lblOutside.setVisible(false);
				lblTopMain.setText("Total Rainfall");
				repaint();
			}
		});
		btnRainfall.setBackground(Color.LIGHT_GRAY);
		btnRainfall.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRainfall.setBounds(7, 105, 154, 30);
		pnlButtonPanel.add(btnRainfall);

		
	}
	/*
	 * Helper method that creates all of the dynamic labels, ie main left/right/top
	 */
	private void createDynamicLabels() throws JSONException {

		lblSunriseTime = new JLabel(w.getSunrise());
		System.out.println(w.getSunrise());
		lblSunriseTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSunriseTime.setBounds(240, 110, 200, 20);
		pnlMain.add(lblSunriseTime);

		lblSunsetTime = new JLabel(w.getSunset());
		lblSunsetTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSunsetTime.setBounds(240, 160, 200, 20);
		pnlMain.add(lblSunsetTime);

		lblLeftMain = new JLabel(sim.getCurrentInsideTemp() + " F");
		lblLeftMain.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblLeftMain.setBounds(200, 35, 99, 53);
		pnlMain.add(lblLeftMain);
		
		lblRightMain = new JLabel(w.getCurrentTemp());
		lblRightMain.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblRightMain.setBounds(300, 30, 99, 58);
		pnlMain.add(lblRightMain);
		
		lblTopMain = new JLabel("Temperature");
		lblTopMain.setHorizontalAlignment(SwingConstants.CENTER);
		lblTopMain.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblTopMain.setBounds(157, 5, 238, 31);
		pnlMain.add(lblTopMain);
		
		lblTime = new JLabel("");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTime.setBounds(7, 139, 154, 30);
		pnlButtonPanel.add(lblTime);
		
		lblClock = new JLabel("");
		lblClock.setHorizontalAlignment(SwingConstants.CENTER);
		lblClock.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblClock.setBounds(7, 170, 154, 30);
		pnlButtonPanel.add(lblClock);
		
		N = new JLabel("");
		N.setName("N");
		N.setBounds(70, 60, 18, 14);
		pnlMain.add(N);
		
		NE = new JLabel("");
		NE.setName("NE");
		NE.setBounds(95, 82, 18, 14);
		pnlMain.add(NE);
		
		E = new JLabel("");
		E.setName("E");
		E.setBounds(112, 105, 18, 14);
		pnlMain.add(E);
		
		SE = new JLabel("");
		SE.setName("SE");
		SE.setBounds(94, 125, 18, 14);
		pnlMain.add(SE);
		
		S = new JLabel("");
		S.setName("S");
		S.setBounds(66, 150, 18, 14);
		pnlMain.add(S);
		
		SW = new JLabel("");
		SW.setName("SW");
		SW.setBounds(33, 130, 18, 14);
		pnlMain.add(SW);
		
		W = new JLabel("");
		W.setName("W");
		W.setBounds(33, 105, 18, 14);
		pnlMain.add(W);
		
		NW = new JLabel("");
		NW.setName("NW");
		NW.setBounds(53, 82, 18, 14);
		pnlMain.add(NW);
		
		JLabel lblMPH = new JLabel("MPH");
		lblMPH.setName("S");
		lblMPH.setBounds(66, 199, 35, 14);
		pnlMain.add(lblMPH);
		
		lblWindSpeed = new JLabel("Wind Speed / Direction");
		lblWindSpeed.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblWindSpeed.setBounds(10, 10, 200, 20);
		pnlMain.add(lblWindSpeed);
		
		JLabel lblNewLabel = new JLabel("Today's Forecast");
		lblNewLabel.setBounds(25, 231, 145, 14);
		pnlMain.add(lblNewLabel);
		
		lblForecastMain = new JLabel("");
		lblForecastMain.setBounds(33, 256, 71, 14);
		pnlMain.add(lblForecastMain);
		
		lblForecastDescrip = new JLabel("");
		lblForecastDescrip.setBounds(33, 281, 137, 14);
		pnlMain.add(lblForecastDescrip);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(229, 210, 114, 85);
		pnlMain.add(panel);
		
		lblForecastIcon = new JLabel("");
		lblForecastIcon.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblForecastIcon);
		lblForecastIcon.setBackground(Color.DARK_GRAY);
	}
	/*
	 * Helper method that creates all of the static, cosmetic labels.
	 */
	private void createStaticLabels()  {

		
		JLabel lblSunrise = new JLabel("Sunrise");
		lblSunrise.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSunrise.setBounds(256, 135, 46, 14);
		pnlMain.add(lblSunrise);
		

		
		JLabel lblSunset = new JLabel("Sunset");
		lblSunset.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSunset.setBounds(256, 185, 46, 14);
		pnlMain.add(lblSunset);
		
		
		lblInside = new JLabel("--- INSIDE ---");
		lblInside.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblInside.setBounds(200, 80, 71, 14);
		pnlMain.add(lblInside);
		
		lblOutside = new JLabel("--- OUTSIDE ---");
		lblOutside.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblOutside.setBounds(290, 80, 81, 14);
		pnlMain.add(lblOutside);
		
		JLabel lblN = new JLabel("N");
		lblN.setBounds(70, 40, 18, 14);
		pnlMain.add(lblN);
		
		JLabel lblS = new JLabel("S");
		lblS.setBounds(70, 175, 18, 14);
		pnlMain.add(lblS);
		
		JLabel lblW = new JLabel("W");
		lblW.setBounds(5, 105, 18, 14);
		pnlMain.add(lblW);
		
		JLabel lblE = new JLabel("E");
		lblE.setBounds(140, 105, 18, 14);
		pnlMain.add(lblE);
	}
	/*
	 * Helper method that creates all of the timers used to update information on the UI.
	 */
	private void createTimers() {

		Timer clockTimer = new Timer();
		clockTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String string = new SimpleDateFormat("hh:mm:ss a").format(new Date());
                lblTime.setText("Current Local Time:");
                lblClock.setText(string);
                repaint();
            }
        }, 0, 1000);
			
		Timer compassTimer = new Timer();
		compassTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
				try {
					Double windDir = w.getWindDir();
					JLabel[] arrayOfLables = {N,NW,NE,SW,SE,W,E};
					String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
					List<JLabel> labels = new ArrayList<>(Arrays.asList(arrayOfLables));
					String s = directions[((int) Math.round((( windDir % 360) / 45)) % 8)];
					//String s = sim.getCurrentWindDirection();
					for (JLabel label : labels) {
						label.setVisible(false);
						if(label.getName() == s) {
							label.setVisible(true);
							try {
								label.setText(w.getWindSpeed());
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

            	
                repaint();
            }
        }, 0, 4000);
		
		Timer weatherTimer = new Timer();
		weatherTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	
            	try {
            		 lblForecastDescrip.setText(w.getWeatherDescrip());
            		 lblForecastMain.setText(w.getWeatherMain());
            	} catch (JSONException e) {
            		e.printStackTrace();
            	}

            	try {
               	 URL url = new URL(w.getWeatherIcon());
               	 Image image = ImageIO.read(url);
               	 lblForecastIcon.setIcon(new ImageIcon(image));
            	} catch(Exception e) {
            		e.printStackTrace();
            	}

				try {
					lblSunriseTime.setText(w.getSunrise());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					lblSunsetTime.setText(w.getSunset());
				} catch (JSONException e) {
					e.printStackTrace();
				}

				JToggleButton[] arrayOButtons = {btnTemperature, btnHumidity, btnBarometricPressure, btnRainfall};
            	List<JToggleButton> buttons = new ArrayList<>(Arrays.asList(arrayOButtons));

            	for (JToggleButton button : buttons) {
            	    if(button.isSelected()) {
            	    	if(button.getName() == "temperature") {
            				lblLeftMain.setText(sim.getCurrentInsideTemp() + " F");
							try {
								lblRightMain.setText(w.getCurrentTemp());
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else if(button.getName() == "humidity") {
            				lblLeftMain.setText(sim.getCurrentInsideHum() + " %");
							try {
								lblRightMain.setText(w.getCurrentHumid());
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else if(button.getName() == "pressure") {
							try {
								lblLeftMain.setText(w.getCurrentPressure());
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else if(button.getName() == "rainfall") {
            				lblLeftMain.setText(Double.toString(sim.getCurrentRainTotal()));
            	    	}
            	    }
            	}
                repaint();
            }
        }, 0, 3000);
		
	}
}
