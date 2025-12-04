package state;

public class PreparingState implements OrderState{

	@Override
	public void next(OrderContext context) {
		// TODO Auto-generated method stub
		context.setState(new OutForDeliveryState());
	}

	@Override
	public void prev(OrderContext context) {
		// TODO Auto-generated method stub
		context.setState(new OrderPlacedState());
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "Preparing";
	}

}
