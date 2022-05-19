import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;


public class Weather {

    private boolean fahrenheit;
    private double temperature;
    private ImageIcon icon;
    private String condition;

    public Weather()
    {
        fahrenheit = true;
        temperature = 0.0;
        icon = new ImageIcon("https://cdn.uconnectlabs.com/wp-content/uploads/sites/7/2021/07/thumbs-up-4007573_640-e1631622199684.png?v=156236");
        condition = "";

    }
    public Weather(double temperature, ImageIcon icon)
    {
        this.temperature = temperature;
        this.fahrenheit = true;
    }

    public void setFahrenheit(boolean f)
    {
        fahrenheit = f;
    }

    public void setTemperature(double x)
    {
        temperature = x;
    }

    public void setCondition(String x)
    {
        condition = x;
    }

    public String getCondition()
    {
        return condition;
    }

    public void setImage(String imageURL)
    {
        icon = new ImageIcon(imageURL);
    }

    public double getTemperature()
    {
        if(fahrenheit)
        {
            return temperature;
        }
        else
        {
            DecimalFormat df = new DecimalFormat(".#");
            return Double.parseDouble(df.format(((temperature - 32) * 5) / 9));
        }
    }

}
