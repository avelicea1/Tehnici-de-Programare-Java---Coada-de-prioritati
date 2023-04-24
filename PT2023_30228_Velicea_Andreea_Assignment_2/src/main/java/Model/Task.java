package Model;

import java.util.Comparator;

public class Task implements Comparable<Task> {
    private int arrivalTime;
    private int serviceTime;
    private int id;
    public Task(int arrivalTime, int serviceTime,int id){
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String toString()
    {
        return "Task (" + "id= "+ id + ", arrivalTime = " + arrivalTime + ", serviceTime= " + serviceTime + ")";
    }


    @Override
    public int compareTo(Task o) {
        return this.getArrivalTime()-o.getArrivalTime();
    }
}
