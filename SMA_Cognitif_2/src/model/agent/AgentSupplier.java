package model.agent;

/**
 *
 * @author p1002239
 */
public class AgentSupplier implements IAgent
{
    public Builder create()
    {
        return new Builder();
    }
    public static class Builder extends IAgentBuilder<AgentSupplier>
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
    
    
}
