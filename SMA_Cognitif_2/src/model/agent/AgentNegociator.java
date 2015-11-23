package model.agent;

public class AgentNegociator extends AgentAbstract {
    public AgentNegociator(int priceObjective, int range, double slope)
    {
        super(priceObjective, range, slope);
    }

    public Builder create()
    {
        return new Builder();
    }

    public static class Builder extends AgentBuilder<AgentNegociator, Builder>
    {
        public Builder()
        {
        }

        @Override
        public AgentNegociator build()
        {
            return new AgentNegociator();
        }
    }

    protected boolean acceptOffer(double offer)
    {
        return offer < priceObjective;
    }

    protected boolean rejectOffer(double offer)
    {
        return offer > priceObjective + range;
    }

    protected double getNextOffer(double offer, double oldOffer)
    {
        return (priceObjective + range - oldOffer) * slope + oldOffer;
    }

    @Override
    public void run()
    {

    }
}
