import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GUI implements ActionListener
{
    private JTextArea weatherInfo;
    private JTextField zipEntryField;
    private Networking client;
    private JButton sendButton = new JButton("Send");
    private JButton resetButton = new JButton("Reset");
    private JCheckBox checkBox = new JCheckBox("Celsius");
    private Weather weather = new Weather();
    private JFrame frame = new JFrame("Weather App!");

    private BufferedImage image = new BufferedImage(10, 10, 2);

    private JLabel pictureLabel = null;

    public GUI()
    {
        weatherInfo = new JTextArea(3, 20);
        zipEntryField = new JTextField();
        client = new Networking(); // our "networking client"
        sendButton = new JButton("Send");
        resetButton = new JButton("Reset");


        // setup GUI and load Now Playing list
        setupGui();
    }

    private void setupGui()
    {
        //Creating a Frame
        frame = new JFrame("Weather App!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ends program when you hit the X

        // Creating an image from a jpg file stored in the src directory
//        ImageIcon image = new ImageIcon("https://cdn.uconnectlabs.com/wp-content/uploads/sites/7/2021/07/thumbs-up-4007573_640-e1631622199684.png?v=156236");
        //BufferedImage image = null;
        try
        {
            URL imageURL = new URL("https://cdn.uconnectlabs.com/wp-content/uploads/sites/7/2021/07/thumbs-up-4007573_640-e1631622199684.png?v=156236");
            image = ImageIO.read(imageURL);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        pictureLabel = new JLabel(new ImageIcon(image));
        JLabel welcomeLabel = new JLabel("   Current Weather");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(85, 213, 242));

        JPanel logoWelcomePanel = new JPanel(); // the panel is not visible in output
        logoWelcomePanel.add(pictureLabel);
        logoWelcomePanel.add(welcomeLabel);
        logoWelcomePanel.setVisible(true);

        // middle panel with weather list
        JPanel weatherPanel = new JPanel();
        weatherInfo.setText("Weather loading...");
        weatherInfo.setFont(new Font("Helvetica", Font.PLAIN, 16));
        weatherInfo.setWrapStyleWord(true);
        weatherInfo.setLineWrap(true);
        weatherPanel.add(weatherInfo);

        //bottom panel with text field and buttons
        JPanel entryPanel = new JPanel(); // the panel is not visible in output
        JLabel weatherLabel = new JLabel("Enter a zipcode: ");

        zipEntryField = new JTextField(5); // accepts up to 10 characters
        entryPanel.add(weatherLabel);
        entryPanel.add(zipEntryField);
        entryPanel.add(sendButton);
        entryPanel.add(resetButton);
        entryPanel.add(checkBox);



        //Adding Components to the frame
        frame.add(logoWelcomePanel, BorderLayout.NORTH);
        frame.add(weatherPanel, BorderLayout.CENTER);
        frame.add(entryPanel, BorderLayout.SOUTH);

        // PART 2 -- SET UP EVENT HANDLING
        //setting up buttons to use ActionListener interface and actionPerformed method
        sendButton.addActionListener(this);
        resetButton.addActionListener(this);
        checkBox.addActionListener(this);

        // showing the frame
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(sendButton))
        {

            try
            {
                weather.setTemperature(client.parseTemp(client.getJSON(zipEntryField.getText())));
                weather.setCondition(client.parseCondition(client.getJSON(zipEntryField.getText())));
                weatherInfo.setText("Temperature: " + weather.getTemperature() + "\nCondition: " + weather.getCondition());
                URL imageURL = new URL(client.parseImageURL(client.getJSON(zipEntryField.getText())));
                image = ImageIO.read(imageURL);
                pictureLabel = new JLabel(new ImageIcon(image));
                frame.pack();
                //setupGui(client.parseImageURL(client.getJSON(zipEntryField.getText())));
            }
            catch(Exception l)
            {
                System.out.println(l.getMessage());
            }



        }
        if (e.getSource().equals(resetButton))
        {
            zipEntryField.setText("");
            weatherInfo.setText("");
            checkBox = new JCheckBox("Celsius");
            weather.setFahrenheit(true);
        }
        if(e.getSource().equals(checkBox))
        {
            if(checkBox.isSelected())
            {
                weather.setFahrenheit(false);
            }
            else
            {
                weather.setFahrenheit(true);
            }
            weatherInfo.setText("Temperature: " + weather.getTemperature() + "\nCondition: " + weather.getCondition());
        }
    }
}
