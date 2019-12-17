package fr.epsi.maintenance.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class CommandState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "command_state_id")
    private Long id;

    @Column(name = "state")
    private String state;

    @OneToMany(mappedBy = "state")
    private Collection<Command> command;
}
