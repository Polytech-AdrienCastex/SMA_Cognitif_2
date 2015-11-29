package model.message.information;

public class InformationOffer implements Information
{
    public InformationOffer(double price)
    {
        this.price = price;
    }
    
    private final double price;
    public double getPrice()
    {
        return price;
    }
}
