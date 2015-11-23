package model.agent;

public abstract class AgentBuilder<T extends AgentAbstract, U extends AgentBuilder>
{
    public AgentBuilder()
    { }
    
    protected int priceObjective;
    protected int range;
    protected int slope;
    
    public U setPriceObjective(int priceObjective)
    {
        this.priceObjective = priceObjective;
        return (U)this;
    }
    
    public U setRange(int range)
    {
        this.range = range;
        return (U)this;
    }
    
    public U setSlope(int slope)
    {
        this.slope = slope;
        return (U)this;
    }
    
    
    public abstract T build();
}
