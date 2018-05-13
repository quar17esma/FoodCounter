package com.quar17esma.controller.action;

import com.quar17esma.controller.action.impl.*;

/**
 * Possible Commands.
 */
public enum ActionEnum {
    LOGIN {
        {
            this.command = new Login();
        }
    },
    LOGOUT {
        {
            this.command = new Logout();
        }
    },
    REGISTER_CLIENT {
        {
            this.command = new RegisterClient();
        }
    },
    EDIT_CLIENT {
        {
            this.command = new EditClient();
        }
    },
//    BUY_NOW {
//        {
//            this.command = new BuyNow();
//        }
//    },
//    ADD_TO_ORDER {
//        {
//            this.command = new AddToOrder();
//        }
//    },
//    SHOW_CART {
//        {
//            this.command = new ShowCart();
//        }
//    },
//    MY_ORDERS {
//        {
//            this.command = new MyOrders();
//        }
//    },
//    PAY_ORDER {
//        {
//            this.command = new PayOrder();
//        }
//    },
//    SEND_ORDER {
//        {
//            this.command = new SendOrder();
//        }
//    },
//    SHOW_GOODS {
//        {
//            this.command = new ShowGoods();
//        }
//    },
//    DELETE_GOOD {
//        {
//            this.command = new DeleteGood();
//        }
//    },
    EDIT_FOOD {
        {
            this.command = new EditFood();
        }
    },
    ADD_FOOD {
        {
            this.command = new AddFood();
        }
    },
    CHANGE_LOCALE {
        {
            this.command = new ChangeLocale();
        }
    },
//    MANAGE_CLIENTS {
//        {
//            this.command = new ManageClients();
//        }
//    },
 ;

    Action command;

    public Action getCurrentCommand() {
        return command;
    }
}
