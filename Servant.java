import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Servant extends Thread
{
    int temp;
    public void run()
    {
        while (!Presents.bag.isEmpty() || Presents.chain.head.next.id != Integer.MAX_VALUE)
        {
            // Check if present is in chain at Minotaurs request
            if (Presents.checkRequest.compareAndSet(true, false))
            {
                temp = Presents.giftCheck.get();
                if (Presents.chain.contains(temp) && Presents.reporting)
                {
                    System.out.println("The present chain does currently contain " + temp);
                }
                else if (Presents.reporting)
                {
                    System.out.println("The present chain doesn't currently contain " + temp);
                }
            }
            else
            {
                if (ThreadLocalRandom.current().nextInt(1, 101) > 75)
                {
                    // Add to chain
                    try
                    {
                        temp = Presents.bag.poll(100, TimeUnit.MILLISECONDS);
                        Presents.chain.add(temp);
                    }
                    catch (Exception e){}
                }
                else
                {
                    // Try to remove from chain
                    Presents.chain.remove();
                }
            }
        }
    }
}