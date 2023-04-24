package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class TimeStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t) {
        int minTime = Integer.MAX_VALUE;
        Server serverMin = servers.get(0);
        for(Server s: servers){
            if(s.getWaitingPeriod().get() < minTime){
                serverMin = s;
                minTime = s.getWaitingPeriod().get();
            }
        }
        serverMin.addTask(t);
    }
}
