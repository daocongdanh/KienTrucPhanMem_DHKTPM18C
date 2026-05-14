package com.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverDemos {
    public static void main(String[] args) {
        Stock apple = new Stock("AAPL", 190.25);
        Investor alice = new Investor("Alice");
        Investor bob = new Investor("Bob");

        apple.addObserver(alice);
        apple.addObserver(bob);
        apple.setPrice(192.10);

        Task onboarding = new Task("TASK-1", "OPEN");
        TeamMember lan = new TeamMember("Lan");
        TeamMember david = new TeamMember("David");

        onboarding.addObserver(lan);
        onboarding.addObserver(david);
        onboarding.setStatus("IN_PROGRESS");
        onboarding.setStatus("DONE");
    }
}

interface Observer {
    void update(String event, Object source);
}

interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String event);
}

class Stock implements Subject {
    private final String symbol;
    private final List<Observer> observers = new ArrayList<>();
    private double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public void setPrice(double price) {
        this.price = price;
        notifyObservers("PRICE_CHANGED");
    }

    public double getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer observer : observers) {
            observer.update(event, this);
        }
    }
}

class Investor implements Observer {
    private final String name;

    Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(String event, Object source) {
        if (source instanceof Stock) {
            Stock stock = (Stock) source;
            System.out.println(
                name + " notified: " + stock.getSymbol() + " is " + stock.getPrice());
        }
    }
}

class Task implements Subject {
    private final String id;
    private final List<Observer> observers = new ArrayList<>();
    private String status;

    Task(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
        notifyObservers("STATUS_CHANGED");
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer observer : observers) {
            observer.update(event, this);
        }
    }
}

class TeamMember implements Observer {
    private final String name;

    TeamMember(String name) {
        this.name = name;
    }

    @Override
    public void update(String event, Object source) {
        if (source instanceof Task) {
            Task task = (Task) source;
            System.out.println(
                name + " notified: " + task.getId() + " is " + task.getStatus());
        }
    }
}
