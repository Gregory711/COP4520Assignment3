import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

public class Sensor extends Thread
{
    int id, iteration = 0, temp;
    public void run()
    {
        id = Integer.parseInt(this.getName());
        while (iteration < (TemperatureModule.reportCount * 60))
        {
            temp = ThreadLocalRandom.current().nextInt(-100, 70 + 1);
            TemperatureModule.temps[(iteration % 60) * id] = temp;
            try
            {
                TimeUnit.MILLISECONDS.sleep(TemperatureModule.minute);
            } catch (InterruptedException e) {}
            iteration++;
        }
    }
}