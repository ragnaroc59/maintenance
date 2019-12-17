package fr.epsi.maintenance.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class CommandsToSwitchDto {
    private Collection<Long> ids;
}
