package model.environment;

import java.util.Collection;
import model.agent.AgentNegociator;
import model.agent.AgentSupplier;
import model.message.MailBox;

public class AgentSystem
{
    public AgentSystem(
            Collection<AgentNegociator> negociators,
            Collection<AgentSupplier> suppliers,
            MailBox mailBox)
    {
        this.negociators = negociators;
        this.suppliers = suppliers;
        this.mailBox = mailBox;
        
        negociators.stream()
                .forEach(a -> a.setAgentSystem(this));
        
        suppliers.stream()
                .forEach(a -> a.setAgentSystem(this));
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

    public void startAgents()
    {
        suppliers.stream()
                .map(Thread::new)
                .forEach(Thread::start);
        
        negociators.stream()
                .map(Thread::new)
                .forEach(Thread::start);
    }
}
