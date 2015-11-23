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
            return new AgentSupplier();
        }
    }
    
    public AgentSupplier()
    {
        
    }
    
    @Override
    public void run()
    {
        
    }
}
