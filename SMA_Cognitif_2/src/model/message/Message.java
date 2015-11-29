package model.message;

import java.math.BigInteger;
import model.agent.AgentAbstract;

public class Message<T extends AgentAbstract>
{
    protected Message(T from, T to, MessageContent content)
    {
        this.from = from;
        this.to = to;
        this.content = content;
        
        this.uid = getUID();
    }
    
    private final T from;
    private final T to;
    private final MessageContent content;
    private final BigInteger uid;
    
    private static BigInteger suid = BigInteger.ZERO;
    protected static BigInteger getUID()
    {
        suid = suid.add(BigInteger.ONE);
        return suid;
    }
    
    public BigInteger getID()
    {
        return uid;
    }
    
    public T getFrom()
    {
        return from;
    }
    
    public T getTo()
    {
        return to;
    }
    
    public MessageContent getContent()
    {
        return content;
    }
}
