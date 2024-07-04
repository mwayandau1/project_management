package com.code.service;

import com.code.model.Chat;
import com.code.model.Project;
import com.code.model.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tags) throws Exception;

    Project getProjectById(Long projectId ) throws Exception;

    void deleteProject(Long projectId, Long userId) throws Exception;

    Project updateProject(Project updatedProject, Long id) throws Exception;


    void addUserToProject(Long projectId, Long userId) throws Exception;

    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatProjectId(Long projectId) throws Exception;

    List<Project> searchedProjects(String keyword, User user) throws Exception;
}
