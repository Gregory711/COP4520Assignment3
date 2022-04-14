import java.util.concurrent.TimeUnit;
import java.util.PriorityQueue;
import java.util.Collections;

public class TemperatureModule
{
    public static int temps[] = new int[60 * 8];
    // How many milliseconds simulates a minute
    public static int minute = 5;
    public static int reportCount = 1;
    public static void main(String[] args) throws InterruptedException
    {
        int startIndex, low, high, maxDiff;
        long start, stop, diff;
        PriorityQueue<Integer> lowest = new PriorityQueue<>(6, Collections.reverseOrder());
        PriorityQueue<Integer> highest = new PriorityQueue<>(6);
        Sensor sensors[] = new Sensor[8];
        for (int i = 0; i < 8; i++)
        {
            sensors[i] = new Sensor();
            sensors[i].setName(Integer.toString(i));
        }
        for (int i = 0; i < 8; i++)
        {
            sensors[i].start();
        }
        // Give sensors a headstart
        TimeUnit.MILLISECONDS.sleep(15 * minute);
        for (int i = 0; i < reportCount; i++)
        {
            maxDiff = startIndex = 0;
            for (int j = 0; j < (60 * 8); j++)
            {
                start = System.nanoTime();
                lowest.add(temps[j]);
                highest.add(temps[j]);
                if (j > 4)
                {
                    lowest.poll();
                    highest.poll();
                }
                if (j < ((60 * 8) - 10))
                {
                    low = high = temps[j];
                    for (int k = 1; k < 10; k++)
                    {
                        if (temps[j + k] < low)
                        {
                            low = temps[j + k];
                        }
                        if (temps[j + k] > high)
                        {
                            high = temps[j + k];
                        }
                    }
                    if ((high - low) > maxDiff)
                    {
                        maxDiff = high - low;
                        startIndex = j;
                    }
                }
                do
                {
                    stop = System.nanoTime();
                    diff = (long)(((double)(stop - start) / 1_000_000_000.0) * 1000.0);
                } while (diff < minute);
            }
            System.out.println("Report " + (i + 1) + ":");
            System.out.print("Lowest 5 temperature readings: ");
            for (int j = 0; j < 5; j++)
            {
                System.out.print(lowest.poll() + " ");
            }
            System.out.print("\nHighest 5 temperature readings: ");
            for (int j = 0; j < 5; j++)
            {
                System.out.print(highest.poll() + " ");
            }
            System.out.println("\nLargest temperature difference was found after " + startIndex + " minutes.");
            System.out.print("The difference was " + maxDiff + " and the values were as follows: [");
            for (int j = 0; j < 10; j++)
            {
                System.out.print(temps[startIndex + j]);
                if (j < 9)
                {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
}