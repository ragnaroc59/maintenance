package fr.epsi.maintenance.repository;

import fr.epsi.maintenance.entity.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommandRepository extends JpaRepository<Command,Long> {

    @Query(value = "SELECT * FROM COMMAND WHERE COMMAND_STATE_ID = :commandStateId",nativeQuery = true)
    Collection<Command> getCommandByState(@Param("commandStateId")Long commandStateId);
}
