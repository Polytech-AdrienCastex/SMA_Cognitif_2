package model.agent;

public class Booking
{
    public Booking(AgentAbstract supplier, AgentAbstract negociator, Double price)
    {
        this.negociator = negociator;
        this.supplier = supplier;
        this.price = price;
    }
    
    private final AgentAbstract supplier;
    private final AgentAbstract negociator;
    private final Double price;
    
    public AgentAbstract getSupplier()
    {
        return supplier;
    }
    public AgentAbstract getNegociator()
    {
        return negociator;
    }
    public Double getPrice()
    {
        return price;
    }

    @Override
    public String toString()
    {
        return getSupplier() + " | " + getNegociator() + " | " + getPrice() + "€";
    }
}
