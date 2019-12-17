package fr.epsi.maintenance.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMAND_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COMMAND_STATE_ID")
    private CommandState state;
}
