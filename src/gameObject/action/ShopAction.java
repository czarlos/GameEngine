package gameObject.action;


public class ShopAction extends TradeAction {

    public ShopAction () {   
        myName = "Shop";
    }
    
    public ShopAction (String item) {
        super(item);
    }

}
