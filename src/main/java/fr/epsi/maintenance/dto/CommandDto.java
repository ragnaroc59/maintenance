package fr.epsi.maintenance.dto;

import lombok.Data;

@Data
public class CommandDto {
    private Long id;
    private CommandStateDto state;
}
