package BusinessLogic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Random;

public class SimulationManager implements Runnable {

    private Scheduler scheduler;
    private List<Task> tasks;
    private ArrayList<Task> generatedTasks;
    private Strategy strategy;
    private int timeLimit;
    private int nrClients;
    private int nrQueues;
    private int minArrTime;
    private int maxArrTime;
    private int minServTime;
    private int maxServTime;
    private float averageWaitingTime;
    private float averageServiceTime;
    private int peakHour;


    public SimulationManager(int nrClients,int nrQueues,int timeLimit,int minArrTime,int maxArrTime, int minServTime,int maxServTime) {
        this.timeLimit = timeLimit;
        this.nrClients = nrClients;
        this.nrQueues = nrQueues;
        this.minArrTime = minArrTime;
        this.maxArrTime = maxArrTime;
        this.minServTime = minServTime;
        this.maxServTime = maxServTime;
        this.scheduler = new Scheduler(nrQueues);
        this.strategy = new TimeStrategy();
        this.generatedTasks = new ArrayList<>();
        this.scheduler = new Scheduler(nrQueues);
        this.generatedTasks = new ArrayList<>();
        this.strategy = new TimeStrategy();
        generateRandomTask();
    }

    private void generateRandomTask(){
        this.generatedTasks = new ArrayList<>();
        for(int i=0;i<nrClients;i++) {
            Task task = new Task((int)Math.floor(Math.random()*(maxArrTime-minArrTime+1)+minArrTime), (int)Math.floor(Math.random()*(maxServTime-minServTime+1)+minServTime),i);
            generatedTasks.add(task);
        }
        Collections.sort(generatedTasks);
        averageServiceTime = 0;
        for(Task t: generatedTasks){
            averageServiceTime += t.getServiceTime();
        }
        averageServiceTime /=(double) generatedTasks.size();
    }
    @Override
    public void run() {
        int currentTime = 0;
        int maxNrClient = Integer.MIN_VALUE;
        try {
            FileWriter writer = new FileWriter("log.txt");
            StringBuilder evolutionQueue = new StringBuilder();
            StringBuilder statusQueue = new StringBuilder();
            peakHour = Integer.MIN_VALUE;
            while(currentTime < timeLimit ) {
                evolutionQueue = new StringBuilder();
//                waitingQueue = new StringBuilder();
                writer.write("Time "+currentTime+"\n");
                //evolutionQueue.append("Time "+currentTime+"\n");
                int i=0;

                while(i<generatedTasks.size()) {
                        if (generatedTasks.get(i).getArrivalTime() == currentTime) {
                            scheduler.dispatchTask(generatedTasks.get(i));
                            generatedTasks.remove(i);
                            i=0;
                        }else{
                            i++;
                        }

                }
                writer.write("Waiting clients: \n");
                evolutionQueue.append("Time "+currentTime+": \n");
                evolutionQueue.append("Waiting clients: \n");
                for(Task t: generatedTasks){
                    writer.write(t + ";");
                    writer.write("\n");
                    evolutionQueue.append(t+";");
                    evolutionQueue.append("\n");
                }
                writer.write("\n");
                evolutionQueue.append("\n");
                int id =0;
                int nrClienti =0;
                for(Server s: scheduler.getServers()){
                    writer.write("Queue "+(id+1 )+ ": \n");
                    evolutionQueue.append("Queue "+(id+1)+": \n");
                    id++;
                    writer.write(s.toString());
                    evolutionQueue.append(s.toString());
                    writer.write("\n");
                    evolutionQueue.append("\n");
                    nrClienti += s.getTasks().size();
                    if(s.getTasks().size()!=0)
                        averageWaitingTime += s.getWaitingPeriod().intValue();
                }
                if(nrClienti > maxNrClient){
                    maxNrClient = nrClienti;
                    peakHour = currentTime;
                }

                writer.write("\n");
                evolutionQueue.append("\n");
                printResult(scheduler.getServers(), currentTime);
                writer.write("-----------------------------------------------------------\n");
                evolutionQueue.append("---------------------------------------------\n");
                Thread.sleep(1000);
                currentTime++;
                //
                SimulationFrame.setEvolutionQueue(evolutionQueue.toString());
            }
            averageWaitingTime /= nrQueues*timeLimit;
            writer.write("Average service time :" +averageServiceTime+"\n");
            writer.write("Average waiting time: "+ averageWaitingTime+"\n");
            writer.write("PeakHour: "+peakHour);
            statusQueue.append("Average service time :" +averageServiceTime+"\n");
            statusQueue.append("Average waiting time: "+ averageWaitingTime+"\n");
            statusQueue.append("PeakHour: "+peakHour);
            SimulationFrame.setStatusQueue(statusQueue.toString());
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void printResult(ArrayList<Server> servers,int time){
            System.out.println("Time "+time+"\n");
            System.out.println("Waiting clients: ");
            for(Task t: generatedTasks){
                System.out.println(t+" ; ");
            }
            System.out.println("\n");
            int id =0;
            for(Server queue: servers){
                System.out.println("Queue "+id + ": ");
                id++;
                System.out.println(queue.toString());
                System.out.println("\n");
            }
            System.out.println("-----------------------------------------------------------\n");
    }
}
