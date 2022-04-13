import java.util.Random;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Iterator;

public class Presents
{
    private static int presentCount = 500000;
    public static BlockingQueue<Integer> bag = new ArrayBlockingQueue<Integer>(presentCount);
    public static PresentChain chain = new PresentChain();
    public static AtomicBoolean checkRequest = new AtomicBoolean(false);
    public static AtomicInteger giftCheck = new AtomicInteger();
    public static boolean reporting = false;
    public static void main(String[] args) throws InterruptedException
    {
        Random rand = new Random();
        HashSet<Integer> presents = new HashSet<Integer>();
        int temp;
        for (int i = 0; i < presentCount; i++)
        {
            do
            {
                temp = rand.nextInt(Integer.MIN_VALUE + 1, Integer.MAX_VALUE);
            } while(presents.contains(temp));
            bag.put(temp);
            presents.add(temp);
            if (reporting)
            {
                System.out.println("Filling bag with " + temp);
            }
        }
        Servant servants[] = new Servant[4];
        for (int i = 0; i < 4; i++)
        {
            servants[i] = new Servant();
            servants[i].start();
        }
        // Do gift check requests
        Iterator<Integer> gift = presents.iterator();
        while (!bag.isEmpty() || chain.head.next.id != Integer.MAX_VALUE)
        {
            if (!checkRequest.get() && rand.nextInt() > (98 * (Integer.MAX_VALUE / 100)) && gift.hasNext())
            {
                giftCheck.set(gift.next());
                checkRequest.set(true);
            }
        }
        System.out.println("Successfully wrote all thank you notes");
    }
}