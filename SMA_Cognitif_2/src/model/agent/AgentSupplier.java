package model.agent;

/**
 *
 * @author p1002239
 */
public class AgentSupplier extends AgentAbstract
{
    public Builder create()
    {
        return new Builder();
    }
    public static class Builder extends AgentBuilder<AgentSupplier, Builder>
    {
        public Builder()
        { }
        
        @Override
        public AgentSupplier build()
        {
            return new AgentSupplier(
                    priceObjective,
                    range,
                    slope
            );
        }
    }
    
    public AgentSupplier(
            int priceObjective,
            int range,
            double slope)
    {
        super(priceObjective, range, slope);
    }
    
    protected boolean acceptOffer(double offer)
    {
        
    }
    
    protected double getNextOffer(double offer, double oldOffer)
    {
        if(offer > this.priceObjective)
            return -1;
        
        if(offer > this.priceObjective - this.range)
            return (this.priceObjective - oldOffer) * slope + oldOffer;
        
        return -2;
    }
    
    @Override
    public void run()
    {
        
    }
}
