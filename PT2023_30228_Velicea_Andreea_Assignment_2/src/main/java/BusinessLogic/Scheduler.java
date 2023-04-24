package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private Strategy strategy;

    public Scheduler(int maxNoServers){
        this.maxNoServers = maxNoServers;
        this.servers = new ArrayList<Server>();
        this.strategy = new TimeStrategy();
        for(int i=0;i<maxNoServers;i++){
            Server server = new Server();
            this.servers.add(server);
            Thread thread = new Thread(server);
            thread.start();
        }
    }

    public void dispatchTask(Task t){
        this.strategy.addTask(this.servers,t);
    }
    public ArrayList<Server> getServers(){
        return (ArrayList<Server>) servers;
    }



}
