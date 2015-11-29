package model.agent;

import model.message.Message;

public class AgentSupplier extends AgentAbstract
{
    public static Builder create()
    {
        return new Builder();
    }
    public static class Builder extends AgentBuilder<AgentSupplier, Builder>
    {
        @Override
        public AgentSupplier build()
        {
            check();
            return new AgentSupplier(
                    priceObjective,
                    range,
                    slope,
                    initialOffer,
                    maxNbTry,
                    minNbTry,
                    isVerbose
            );
        }
    }
    
    public AgentSupplier(
            int priceObjective,
            int range,
            double slope,
            double initialOffer,
            int maxNbTry,
            int minNbTry,
            boolean isVerbose)
    {
        super(priceObjective, range, slope, initialOffer, maxNbTry, minNbTry, isVerbose);
        
        
    }
    
    @Override
    protected boolean acceptOffer(double offer)
    {
        return offer > this.priceObjective;
    }
    @Override
    protected boolean rejectOffer(double offer)
    {
        return offer < this.priceObjective - this.range;
    }
    
    @Override
    protected double getNextOffer(double offer, double oldOffer)
    {
        return (oldOffer - this.priceObjective + this.range) * getSlope() + oldOffer;
    }
    
    @Override
    protected boolean isSatisfied()
    {
        return false;
    }
    
    @Override
    protected void computeCall(Message msg)
    {
        AgentAbstract client = msg.getFrom();
        as.getMailBox().putPendingMessage(this, client, generateMessageOffer(msg));
    }
}
