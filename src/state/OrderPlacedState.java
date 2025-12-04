package state;

public class OrderPlacedState implements OrderState{

	@Override
	public void next(OrderContext context) {
		// TODO Auto-generated method stub
		context.setState(new PreparingState());
	}

	@Override
	public void prev(OrderContext context) {
		// TODO Auto-generated method stub
		System.out.println("Order is in initial state");
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "Order Placed";
	}

}
