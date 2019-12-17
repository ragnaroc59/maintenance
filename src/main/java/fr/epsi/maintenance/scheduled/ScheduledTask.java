package fr.epsi.maintenance.scheduled;

import fr.epsi.maintenance.entity.Command;
import fr.epsi.maintenance.repository.CommandRepository;
import fr.epsi.maintenance.repository.CommandStateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTask {

    @Autowired
    private CommandRepository commandRepo;

    @Autowired
    private CommandStateRepository commandStateRepo;

    //@Scheduled(cron="0 0/2 * * * *" , zone="Europe/Paris")  // toutes les 2 minutes
    //@Scheduled(cron="*/30 * * * * *" , zone="Europe/Paris") // toutes les 30 secondes
    public void scheduled(){
        for(int i =0;i<1000;i++) {
            Command command = new Command();
            command.setState(this.commandStateRepo.getOne(1l));
            this.commandRepo.save(command);
        }
    }
}
