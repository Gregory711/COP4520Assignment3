/*
Code from The Art of Multiprocessor Programming
*/
import java.util.concurrent.ThreadLocalRandom;

public class PresentChain
{
    public PresentNode head;
    public PresentChain()
    {
        head = new PresentNode(Integer.MIN_VALUE);
        head.next = new PresentNode(Integer.MAX_VALUE);
    }
    // Fine-grained add to linked-list
    public void add(int id)
    {
        head.lock();
        PresentNode pred = head;
        try
        {
            PresentNode curr = pred.next;
            curr.lock();
            try
            {
                while (curr.id < id)
                {
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (curr.id == id)
                {
                    return;
                }
                PresentNode newNode = new PresentNode(id);
                newNode.next = curr;
                pred.next = newNode;
                if (Presents.reporting)
                {
                    System.out.println("Adding " + id + " present to the chain");
                }
                return;
            }
            finally
            {
                curr.unlock();
            }
        }
        finally
        {
            pred.unlock();
        }
    }
    // Randomly choose a present in the chain and remove it
    public void remove()
    {
        PresentNode curr = head, pred = null;
        int skip = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        try
        {
            for (int i = 0; i < skip && curr.id != Integer.MAX_VALUE; i++)
            {
                pred = curr;
                curr = curr.next;
            }
        }
        catch(Exception e)
        {
            return;
        }
        try
        {
            pred.lock();
        }
        catch(Exception e)
        {
            return;
        }
        try
        {
            curr.lock();
        }
        catch(Exception e)
        {
            pred.unlock();
            return;
        }
        try
        {
            if (curr.id != Integer.MAX_VALUE && pred.next.id == curr.id)
            {
                pred.next = curr.next;
                if (Presents.reporting)
                {
                    System.out.println("Writing thank you to and removing " + curr.id + " from the chain");
                }
                return;
            }
            return;
        }
        catch(Exception e){}
        finally
        {
            pred.unlock();
            curr.unlock();
        }
    }
    public boolean contains(int id)
    {
        PresentNode curr = head;
        while (curr.id < id)
        {
            curr = curr.next;
        }
        return curr.id == id;
    }
}