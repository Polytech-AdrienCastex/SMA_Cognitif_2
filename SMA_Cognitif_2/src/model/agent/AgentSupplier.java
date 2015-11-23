package model.agent;

import java.util.Map;
import model.message.Message;
import model.message.information.InformationOffer;

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
        return offer > this.priceObjective;
    }
    protected boolean rejectOffer(double offer)
    {
        return offer < this.priceObjective - this.range;
    }
    
    protected double getNextOffer(double offer, double oldOffer)
    {
        return (oldOffer - this.priceObjective - this.range) * slope + this.priceObjective - this.range;
    }
    
    private Map<AgentAbstract, Integer> com;
    
    protected void computeNego(Message msg)
    {
        InformationOffer offer = msg.getContent().getInformation();
        
        com.put(msg.getFrom(), com.get(msg.getFrom() + 1);
        
        if(acceptOffer(offer.getPrice()))
        {
            
        }
        else if()
    }
    
    @Override
    public void run()
    {
        
    }
}
