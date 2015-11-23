package model.message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import model.agent.AgentAbstract;

public class MailBox<T extends AgentAbstract> extends Observable
{
    public MailBox()
    {
        this.mails = new HashMap<>();
    }
    
    private final Map<T, LinkedList<Message>> mails;
    
    public static class Notification<T extends AgentAbstract>
    {
        public enum Action
        {
            Remove,
            Add
        }
        
        public Notification(Message<T> msg, Action action)
        {
            this.msg = msg;
            this.action = action;
        }
        
        public final Message<T> msg;
        public final Action action;
    }
    
    public boolean hasPendingMessage(T agent)
    {
        return !getList(agent).isEmpty();
    }
    
    public Message getPendingMessage(T agent)
    {
        Message msg = getList(agent).poll();
        
        setChanged();
        notifyObservers(new Notification(msg, Notification.Action.Remove));
        
        return msg;
    }
    
    public boolean putPendingMessage(T from, T to, MessageContent messageContent)
    {
        if(to == null)
            return false;
        
        Message msg = new Message(from, to, messageContent);
        if(getList(to).add(msg))
        {
            setChanged();
            notifyObservers(new Notification(msg, Notification.Action.Add));
            return true;
        }
        else
            return false;
    }
    
    protected synchronized LinkedList<Message> getList(T agent)
    {
        if(!mails.containsKey(agent))
            mails.put(agent, new LinkedList<>());
        return mails.get(agent);
    }
}
