package fr.epsi.maintenance.service.impl;

import fr.epsi.maintenance.GenericConverter;
import fr.epsi.maintenance.dto.CommandDto;
import fr.epsi.maintenance.entity.Command;
import fr.epsi.maintenance.repository.CommandRepository;
import fr.epsi.maintenance.repository.CommandStateRepository;
import fr.epsi.maintenance.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;

@Service
@Transactional
public class CommandServiceImpl implements CommandService {

    @Autowired
    private CommandRepository commandRepo;

    @Autowired
    private CommandStateRepository stateRepo;

    @Autowired
    private GenericConverter<CommandDto,Command> commandConverter;

    @Override
    public Collection<CommandDto> getCommandByState(Long command) {
        return this.commandConverter.entitiesToDtos(this.commandRepo.getCommandByState(command),CommandDto.class);
    }

    @Override
    public void switchStates(Collection<Long> ids,Long currentState) throws Exception {
        Collection<Command> commands = this.commandRepo.findAll();

        for(Command c : commands) {
            if(currentState.equals(c.getState().getId())) {
                if(c.getState().getId().equals(4l)){
                    throw new Exception("L'état final est déjà atteint !");
                }
                if(c.getState().getId() < 4) {
                    Command currentCommand = this.commandRepo.getOne(c.getId());
                    currentCommand.setState(this.stateRepo.getOne(currentState + 1));
                    this.commandRepo.save(currentCommand);
                }
            }
        }
    }

    @Override
    public void switchState(Long id) throws Exception {
        Command command = this.commandRepo.getOne(id);
        if(command.getState().getId().equals(4l)){
            throw new Exception("L'état final est déjà atteint !");
        }
        if(command.getState().getId()<4){
            command.setState(this.stateRepo.getOne(command.getState().getId()+1));
            this.commandRepo.save(command);
        }
    }
}
