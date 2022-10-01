package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.GroupNotFoundException;
import com.developer.beverageapi.domain.model.Group;
import com.developer.beverageapi.domain.repository.RepositoryGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupRegistrationService {

    public static final String MSG_GROUP_BEING_USED = "Group with the code %d cannot be found" +
            "because it is being used";

    @Autowired
    private RepositoryGroup repositoryGroup;

    @Transactional
    public Group add(Group group){
        return repositoryGroup.save(group);
    }

    @Transactional
    public void remove(Long groupId){
        try{
            repositoryGroup.deleteById(groupId);
            repositoryGroup.flush();

        } catch (EmptyResultDataAccessException e){
            throw new GroupNotFoundException(groupId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_GROUP_BEING_USED, groupId));
        }
    }

    public Group searchOrFail(Long groupId) {
        return repositoryGroup.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }
}
