package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    public Server(){
        this.tasks = new LinkedBlockingQueue<>();
        this.waitingPeriod = new AtomicInteger(0);
    }
    public void addTask(Task newTask){
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }
    @Override
    public void run() {
        Task item = new Task(0,0,0);
        while(true){
            if(tasks.size()>0) {
                item = tasks.peek();
                if(item!=null) {
                    try {
                        Thread.sleep(1000 * item.getServiceTime());
                        tasks.remove(item);
                        this.waitingPeriod.set(this.getWaitingPeriod().get() - 1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
    }
    public LinkedBlockingQueue<Task> getTasks(){
        return (LinkedBlockingQueue<Task>) tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }
    public String toString(){
        StringBuilder allClients = new StringBuilder();
        if(tasks.isEmpty()){
            return "closed";
        }
        for(Task task: tasks){
            allClients.append(task.toString()+"\n");
        }
        return allClients.toString();
    }
}
