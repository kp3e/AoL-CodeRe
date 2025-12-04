package state;

import java.util.ArrayList;
import java.util.Timer;
//import java.util.TimerTask;

import builder.FoodOrder;
import observer.*;
import singleton.Database;

public class OrderContext {
	private OrderState state;
    private FoodOrder order;
    private ArrayList<OrderObserver> observers = new ArrayList<>();
    private Timer timer;
    private boolean isDelivered = false;
    private Thread autoProgressionThread;
    private volatile boolean shouldStop = false;

    
    public OrderContext(FoodOrder order) {
        this.order = order;
        this.state = new OrderPlacedState();
        startAutoProgression();
    }
    
    public void setState(OrderState state) {
        this.state = state;
        notifyObservers();
    }
    
    public void nextState() {
        state.next(this);
    }
    
    public void prevState() {
        state.prev(this);
    }
    
    public String getStatus() {
        return state.getStatus();
    }
    
    public boolean isDelivered() {
    	return isDelivered;
    }
    
    public void setDelivered(boolean delivered) {
    	this.isDelivered = delivered;
    }
    
    public FoodOrder getOrder() {
        return order;
    }
    
    // Observer pattern methods
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }
    
    private void stopAutoProgression() {
        shouldStop = true;
        if (autoProgressionThread != null && autoProgressionThread.isAlive()) {
            autoProgressionThread.interrupt();
        }
    }
    
    private void startAutoProgression() {
    	stopAutoProgression();
        
        shouldStop = false;
        autoProgressionThread = new Thread(() -> {
            try {
                // Wait 5 seconds, then move to Preparing
                Thread.sleep(5000);
                if (!shouldStop && !getStatus().equals("Delivered")) {
                    nextState();
                }
                
                // Wait 5 more seconds (10 total), then move to Out for Delivery  
                Thread.sleep(5000);
                if (!shouldStop && getStatus().equals("Preparing")) {
                    nextState();
                }
                
                // Wait 5 more seconds (15 total), then move to Delivered
                Thread.sleep(5000);
                if (!shouldStop && getStatus().equals("Out for Delivery")) {
                    nextState();
                    setDelivered(true);
                    
                    // Wait 5 seconds after delivery, then cleanup
                    Thread.sleep(5000);
                    if (!shouldStop) {
                        Database.getInstance().removeDeliveredOrders();
                    }
                }
                
            } catch (InterruptedException e) {
                // Thread was interrupted, just exit
                Thread.currentThread().interrupt();
            }
        });
        
        autoProgressionThread.start();
    }
    
    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
    
}
