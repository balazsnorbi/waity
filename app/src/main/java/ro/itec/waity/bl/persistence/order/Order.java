package ro.itec.waity.bl.persistence.order;

import com.orm.SugarRecord;

/**
 * Created by Norbert on 5/14/2016.
 */
public class Order extends SugarRecord{
   Integer order_id;
   OrderState orderState;
}
