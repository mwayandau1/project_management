package com.code.service;

import com.code.model.Chat;
import com.code.model.Project;
import com.code.model.User;
import com.code.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService{


    @Autowired
    private  ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;


    @Override
    public Project createProject(Project project, User user) throws Exception {

        Project createdProject = new Project();
        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setCategory(project.getCategory());
        createdProject.setName(project.getName());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        Project savedProject = projectRepository.save(createdProject);
        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject ;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tags) throws Exception {

        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        if(category != null){
            projects = projects.stream().filter(project -> project.getCategory().equals(category)).toList();
        }

        if(tags != null){
            projects = projects.stream().filter(project -> project.getTags().contains(tags)).toList();
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {

        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()){
            throw new Exception("No project found with the id");
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {

        getProjectById(projectId);

        //userService.findUserById(userId)
        projectRepository.deleteById(projectId);

    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {

        Project project = getProjectById(id);
        if(updatedProject.getName() != null){
            project.setName(updatedProject.getName());
        }
        if(updatedProject.getName() != null){
            project.setName(updatedProject.getName());
        }
        if(updatedProject.getDescription() != null){
            project.setDescription(updatedProject.getDescription());
        }
        if(updatedProject.getTags() != null){
            project.setTags(updatedProject.getTags());
        }

        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {

        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);

        }
    projectRepository.save(project);

    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {

        Project project = getProjectById(projectId);

        User user = userService.findUserById(userId);
        if(project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
     projectRepository.save(project);
    }

    @Override
    public Chat getChatProjectId(Long projectId) throws Exception {

        Project project = getProjectById(projectId);

        return project.getChat();
    }

    @Override
    public List<Project> searchedProjects(String keyword, User user) throws Exception {

        String partialName = "%" + keyword + "%";
        return projectRepository.findByNameContainsAndTeamContains(keyword, user);
    }
}
