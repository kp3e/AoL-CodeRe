package state;

public class DeliveredState implements OrderState{

	@Override
	public void next(OrderContext context) {
		System.out.println("Order has arrived");
		
	}

	@Override
	public void prev(OrderContext context) {
		// TODO Auto-generated method stub
		context.setState(new OutForDeliveryState());
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "Delivered";
	}

}
