package fr.epsi.maintenance.repository;

import fr.epsi.maintenance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "Select * from user_table where username=:name",nativeQuery = true)
    User findByUsername(@Param("name") String name);
}
