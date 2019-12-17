package fr.epsi.maintenance.repository;

import fr.epsi.maintenance.entity.CommandState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandStateRepository extends JpaRepository<CommandState,Long> {
}
