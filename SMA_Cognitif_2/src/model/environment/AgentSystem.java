package model.environment;

import java.util.Collection;
import model.agent.AgentNegociator;
import model.agent.AgentSupplier;
import model.message.MailBox;

public class AgentSystem
{
    public AgentSystem()
    {
        
    }
    
    protected final Collection<AgentSupplier> suppliers;
    protected final Collection<AgentNegociator> negociators;
    protected final MailBox mailBox;
    
    public Collection<AgentSupplier> getSuppliers()
    {
        return suppliers;
    }
    public Collection<AgentNegociator> getNegociators()
    {
        return negociators;
    }
    
    public MailBox getMailBox()
    {
        return mailBox;
    }
}
