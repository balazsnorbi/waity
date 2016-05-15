package ro.itec.waity.bl.persistence.order;

public enum OrderState {
   STATE_WAITING("Waiting"),
   STATE_DELIVERED("Delivered"),
   STATE_PAYED("Payed");

   private final String literal;

   OrderState(String literal) {
      this.literal = literal;
   }

   public String getLiteral() {
      return literal;
   }

   @Override
   public String toString() {
      return literal;
   }
}
