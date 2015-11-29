package model.agent;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import model.environment.AgentSystem;
import model.message.Action;
import model.message.Message;
import model.message.MessageContent;
import model.message.Performatif;
import model.message.information.InformationEmpty;
import model.message.information.InformationOffer;

public abstract class AgentAbstract implements Runnable
{
    public AgentAbstract(
            int priceObjective,
            int range,
            double slope,
            double initialOffer,
            int maxNbTry,
            int minNbTry,
            boolean isVerbose)
    {
        this.priceObjective = priceObjective;
        this.initialOffer = initialOffer;
        this.isVerbose = isVerbose;
        this.maxNbTry = maxNbTry;
        this.minNbTry = minNbTry;
        this.range = range;
        this.slope = slope;
        
        this.nbInteractions = new HashMap<>();
        this.booking = new LinkedList<>();
        this.oldOffers = new HashMap<>();
        
        this.uid = getUID();
    }
    
    
    protected final Collection<Booking> booking;
    protected final int maxNbTry;
    protected final double initialOffer;
    protected final int minNbTry;
    protected final int priceObjective;
    protected final int range;
    protected final double slope;
    protected final int uid;
    protected final boolean isVerbose;
    private final Map<AgentAbstract, Integer> nbInteractions;
    private final Map<AgentAbstract, Double> oldOffers;
    
    protected AgentSystem as;
    
    private static int suid = 0;
    private static Random rnd = new Random();
    
    protected abstract boolean acceptOffer(double offer);
    protected abstract boolean rejectOffer(double offer);
    protected abstract double getNextOffer(double offer, double oldOffer);
    protected abstract void computeCall(Message msg);
    protected abstract boolean isSatisfied();
    
    public static synchronized int getUID()
    {
        return ++suid;
    }
    protected static Random getRandom()
    {
        return rnd;
    }
    
    public int getID()
    {
        return uid;
    }
    
    protected double getSlope()
    {
        return Math.abs(slope + (getRandom().nextGaussian() + 3) / 4.0 * slope);
    }
    
    protected void onAccept(AgentAbstract agent, double price)
    { }
    
    public void setAgentSystem(AgentSystem as)
    {
        this.as = as;
    }

    protected static void sleep(long ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException ex)
        { }
    }
    
    private boolean isVerbose()
    {
        return isVerbose;
    }
    
    protected void closeNego(Message msg)
    {
        oldOffers.remove(msg.getFrom());
        nbInteractions.remove(msg.getFrom());
    }
    
    protected void book(Message msg)
    {
        if(isVerbose())
            System.out.println("ACCEPTED : " + this + " / " + msg.getFrom() + " / " + ((InformationOffer)msg.getContent().getInformation()).getPrice());
        booking.add(new Booking(this, msg.getFrom(), ((InformationOffer)msg.getContent().getInformation()).getPrice()));
    }
    
    protected void computeConfirmAccept(Message msg)
    {
        book(msg);
        onAccept(msg.getFrom(), ((InformationOffer)msg.getContent().getInformation()).getPrice());
        closeNego(msg);
    }
    
    protected void computeAccept(Message msg)
    {
        if(!oldOffers.containsKey(msg.getFrom()))
            return;
        
        if(!isSatisfied())
        {
            as.getMailBox().putPendingMessage(this, msg.getFrom(), new MessageContent(Action.ConfirmAccept, Performatif.Information, msg.getContent().getInformation()));
            book(msg);
            closeNego(msg);
        }
        else
            as.getMailBox().putPendingMessage(this, msg.getFrom(), new MessageContent(Action.Reject, Performatif.Information, new InformationEmpty()));
    }
    protected void computeReject(Message msg)
    {
        if(isVerbose())
            System.out.println("REJECTED " + this + " / " + msg.getFrom());
        closeNego(msg);
    }
            
    protected void computeOffer(Message msg)
    {
        InformationOffer offer = msg.getContent().getInformation();
        AgentAbstract client = msg.getFrom();
        double offerPrice = offer.getPrice();
        
        int nbTry = getNbInteractions(client);
        incNbInteractions(client);
        
        MessageContent mc;
        if(acceptOffer(offerPrice))
        {
            mc = new MessageContent(
                    Action.Accept,
                    Performatif.Information,
                    new InformationOffer(offerPrice));
        }
        else if(nbTry >= maxNbTry || nbTry >= minNbTry && rejectOffer(offerPrice))
        {
            mc = new MessageContent(
                    Action.Reject,
                    Performatif.Information,
                    new InformationEmpty());
        }
        else
        {
            mc = generateMessageOffer(msg);
        }
        as.getMailBox().putPendingMessage(this, client, mc);
    }
    
    protected int getNbInteractions(AgentAbstract agent)
    {
        if(!nbInteractions.containsKey(agent))
            nbInteractions.put(agent, 0);
        return nbInteractions.get(agent);
    }
    protected void incNbInteractions(AgentAbstract agent)
    {
        nbInteractions.put(agent, getNbInteractions(agent) + 1);
    }
    
    protected MessageContent generateMessageOffer(Message msg)
    {
        AgentAbstract client = msg.getFrom();
        double newOffer;
        
        if(msg.getContent().getInformation() instanceof InformationOffer)
        {
            InformationOffer offer = msg.getContent().getInformation();
            double offerPrice = offer.getPrice();

            if(!oldOffers.containsKey(client))
                newOffer = initialOffer;
            else
                newOffer = getNextOffer(offerPrice, oldOffers.get(client));
        }
        else
            newOffer = initialOffer;
        
        if(isVerbose())
            System.out.println(this + " :: " + newOffer);
        
        oldOffers.put(client, newOffer);
        return new MessageContent(
                Action.Offer,
                Performatif.Information,
                new InformationOffer(newOffer));
    }
    
    protected void computeNego(Message msg)
    {
        switch(msg.getContent().getAction())
        {
            case ConfirmAccept:
                computeConfirmAccept(msg);
                break;
            
            case Accept:
                computeAccept(msg);
                onAccept(msg.getFrom(), ((InformationOffer)msg.getContent().getInformation()).getPrice());
                break;
                
            case Reject:
                computeReject(msg);
                break;
                
            case Call:
                computeCall(msg);
                break;
                
            case Offer:
                computeOffer(msg);
                break;
        }
    }
    
    @Override
    public void run()
    {
        while(!isSatisfied())
        {
            if(as.getMailBox().hasPendingMessage(this))
            {
                Message msg = as.getMailBox().getPendingMessage(this);
                if(msg != null)
                    computeNego(msg);
            }
            
            sleep(50 + getRandom().nextInt(50));
        }
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName()+ "::" + getID();
    }
}
