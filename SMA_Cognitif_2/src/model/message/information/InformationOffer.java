package model.message.information;

/**
 *
 * @author p1002239
 */
public class InformationOffer implements Information
{
    public InformationOffer(int price)
    {
        this.price = price;
    }
    
    private final int price;
    public int getPrice()
    {
        return price;
    }
}
