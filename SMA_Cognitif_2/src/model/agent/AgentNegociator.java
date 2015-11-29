package model.agent;

import model.message.Action;
import model.message.Message;
import model.message.MessageContent;
import model.message.Performatif;
import model.message.information.InformationEmpty;

public class AgentNegociator extends AgentAbstract
{
    public static Builder create()
    {
        return new Builder();
    }
    public static class Builder extends AgentBuilder<AgentNegociator, Builder>
    {
        @Override
        public AgentNegociator build()
        {
            check();
            return new AgentNegociator(
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
    
    @Override
    protected void onAccept(AgentAbstract agent, double price)
    {
        as.getSuppliers()
                .stream()
                .filter(a -> a != agent)
                .forEach(a -> as.getMailBox()
                    .putPendingMessage(this, a, new MessageContent(Action.Reject, Performatif.Information, new InformationEmpty())));
    }
    
    public AgentNegociator(
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

    protected boolean acceptOffer(double offer)
    {
        return offer < priceObjective && !isSatisfied();
    }

    protected boolean rejectOffer(double offer)
    {
        return offer > priceObjective + range || isSatisfied();
    }

    protected double getNextOffer(double offer, double oldOffer)
    {
        return (priceObjective + range - oldOffer) * getSlope() + oldOffer;
    }
    
    public Booking getBooking()
    {
        if(booking.isEmpty())
            return null;
        return booking.iterator().next();
    }
    
    @Override
    protected boolean isSatisfied()
    {
        return !booking.isEmpty();
    }
    
    @Override
    protected void computeCall(Message msg)
    {
        // No answer ... I'm not a supplier ...
    }

    @Override
    public void run()
    {
        for(AgentSupplier s : as.getSuppliers())
        {
            as.getMailBox()
                    .putPendingMessage(this, s, new MessageContent(Action.Call, Performatif.Information, new InformationEmpty()));
        }
        
        super.run();
        
        sleep(1000);
        
        as.getSuppliers()
                .stream()
                .map(a -> a.booking)
                .map(b -> b.iterator().next().getSupplier().toString() + " ; nb=" + b.size())
                .forEach(System.out::println);
        System.out.println(this + " / " + getBooking());
    }
}
