package state;

public class OutForDeliveryState implements OrderState{

	@Override
	public void next(OrderContext context) {
		 context.setState(new DeliveredState());
		
	}

	@Override
	public void prev(OrderContext context) {
		// TODO Auto-generated method stub
		context.setState(new PreparingState());
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "Out for Delivery";
	}

}
