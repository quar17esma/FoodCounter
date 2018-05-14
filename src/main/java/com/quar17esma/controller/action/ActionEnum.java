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
    EDIT_MEAL {
        {
            this.command = new EditMeal();
        }
    },
    ADD_MEAL {
        {
            this.command = new AddMeal();
        }
    },
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
    SHOW_FOODS {
        {
            this.command = new ShowFoods();
        }
    },
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
