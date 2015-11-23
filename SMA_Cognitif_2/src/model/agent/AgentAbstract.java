package model.agent;

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

}
