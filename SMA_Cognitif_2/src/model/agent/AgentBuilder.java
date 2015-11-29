package model.agent;

public abstract class AgentBuilder<T extends AgentAbstract, U extends AgentBuilder>
{
    public AgentBuilder()
    { }
    
    protected Integer priceObjective = null;
    protected Integer initialOffer = null;
    protected boolean isVerbose = true;
    protected Integer range = null;
    protected double slope = 0.2;
    protected int maxNbTry = 20;
    protected int minNbTry = 10;
    
    public U setPriceObjective(int priceObjective)
    {
        this.priceObjective = priceObjective;
        return (U)this;
    }
    
    public U setVerbose(boolean isVerbose)
    {
        this.isVerbose = isVerbose;
        return (U)this;
    }
    
    public U setRange(int range)
    {
        this.range = range;
        return (U)this;
    }
    
    public U setSlope(double slope)
    {
        this.slope = slope;
        return (U)this;
    }
    
    public U setInitialOffer(int initialOffer)
    {
        this.initialOffer = initialOffer;
        return (U)this;
    }
    
    public U setMaxNbTry(int maxNbTry)
    {
        this.maxNbTry = maxNbTry;
        return (U)this;
    }
    
    public U setMinNbTry(int minNbTry)
    {
        this.minNbTry = minNbTry;
        return (U)this;
    }
    
    protected void check()
    {
        if(range == null)
            throw new IllegalArgumentException("Range not defined!");
        if(initialOffer == null)
            throw new IllegalArgumentException("InitialOffer not defined!");
        if(priceObjective == null)
            throw new IllegalArgumentException("PriceObjective not defined!");
    }
    
    public abstract T build();
}
