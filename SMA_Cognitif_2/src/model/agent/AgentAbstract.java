package model.agent;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.environment.AgentSystem;

public abstract class AgentAbstract implements Runnable
{
    public AgentAbstract(
            int priceObjective,
            int range,
            double slope)
    {
        this.priceObjective = priceObjective;
        this.range = range;
        this.slope = slope;
    }
    
    protected int priceObjective;
    protected int range;
    protected double slope;
    
    protected AgentSystem as;
    public void setAgentSystem(AgentSystem as)
    {
        this.as = as;
    }
    
    protected abstract boolean isSatisfied();

    protected static void sleep(long ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException ex)
        { }
    }
    
    @Override
    public void run()
    {
        while(!isSatisfied())
        {
            if(as.getMailBox().hasPendingMessage(this))
            {
                
            }
            
        }
    }
}
