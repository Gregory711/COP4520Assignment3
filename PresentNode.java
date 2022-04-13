import java.util.concurrent.atomic.AtomicBoolean;

public class PresentNode
{
    int id;
    public PresentNode next;
    private AtomicBoolean locked;
    public PresentNode(int id)
    {
        this.id = id;
        locked = new AtomicBoolean(false);
    }
    // Test Test and Set Lock (TTAS)
    public void lock()
    {
        while (true)
        {
            while (locked.get()) {}
            if (!locked.getAndSet(true))
            {
                return;
            }
        }
    }
    public void unlock()
    {
        locked.set(false);
    }
}