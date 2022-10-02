package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.UserNotFoundException;
import com.developer.beverageapi.domain.model.User;
import com.developer.beverageapi.domain.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {

    public static final String MSG_USER_BEING_USED = "User with the code %d cannot be found" +
            "because it is being used";

    @Autowired
    private RepositoryUser repositoryUser;

    @Transactional
    public User add(User user){
        return repositoryUser.save(user);
    }

    @Transactional
    public void remove(Long userId){
        try{
            repositoryUser.deleteById(userId);
            repositoryUser.flush();

        } catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException(userId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_USER_BEING_USED, userId));
        }
    }

    @Transactional
    public void updatePassword(Long userId, String currentPassword, String newPassword) {
        User user = searchOrFail(userId);

        if (user.passwordIsNotEqualsTo(currentPassword)) {
            throw new BusinessException("The current password informed is not equals to the user password.");
        }
        user.setPassword(newPassword);
    }

    public User searchOrFail(Long userId) {
        return repositoryUser.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
