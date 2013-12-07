package gameObject.action;

import grid.GridConstants;


public class ShopAction extends TradeAction {

    public ShopAction () {   
        super.setName(GridConstants.SHOP);
    }
    
    public ShopAction (String item) {
        super(item);
    }

}
