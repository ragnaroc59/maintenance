package fr.epsi.maintenance.service;

import fr.epsi.maintenance.dto.CommandDto;

import java.util.Collection;

public interface CommandService {
    Collection<CommandDto> getCommandByState(Long command);

    void switchStates(Collection<Long> ids,Long currentState) throws Exception;

    void switchState(Long id) throws Exception;
}
