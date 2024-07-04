package com.code.repository;

import com.code.model.Project;
import com.code.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project>findByOwner(User owner);
    List<Project> findByNameContainingAndTeamContaining(String partialName, User user);


    @Query("SELECT p From Project p join p.team t where t =:user ")
    List<Project> findByTeam(@Param("user") User user);


    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
