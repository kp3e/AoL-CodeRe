package observer;

import state.OrderContext;

public interface OrderObserver {
	void update(OrderContext orderContext);
}
