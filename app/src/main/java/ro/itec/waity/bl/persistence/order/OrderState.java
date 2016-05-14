package ro.itec.waity.bl.persistence.order;

/**
 * Created by Norbert on 5/14/2016.
 * An order can be in waiting for the waiter, delivered to the client and payed by the client
 */
public enum OrderState {
   STATE_WAITING,
   STATE_DELIVERED,
   STATE_PAYED
}
