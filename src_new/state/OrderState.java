package state;

interface OrderState {
	void next(OrderContext context);
    void prev(OrderContext context);
    String getStatus();
}
