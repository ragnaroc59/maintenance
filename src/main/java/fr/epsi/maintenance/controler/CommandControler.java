package fr.epsi.maintenance.controler;

import fr.epsi.maintenance.dto.CommandDto;
import fr.epsi.maintenance.dto.CommandsToSwitchDto;
import fr.epsi.maintenance.entity.Command;
import fr.epsi.maintenance.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/command")
@CrossOrigin(origins = "*")
public class CommandControler {

    @Autowired
    private CommandService commandService;

    @GetMapping("/{state}")
    public Collection<CommandDto> getCommandByState(@PathVariable(name = "state") Long state) {
        return this.commandService.getCommandByState(state);
    }

    @PostMapping("/states/{currentState}")
    public String switchState(@PathVariable(name = "currentState") Long currentState, @RequestBody CommandsToSwitchDto command) throws Exception {
        this.commandService.switchStates(command.getIds(),currentState);
        return "ok";
    }

    @PostMapping("/state")
    public String switchSingleState(@RequestBody Command command) throws Exception {
        this.commandService.switchState(command.getId());
        return "ok";
    }
}
