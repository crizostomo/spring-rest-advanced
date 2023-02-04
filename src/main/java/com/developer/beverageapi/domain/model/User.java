package com.developer.beverageapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "`user`")
public class User {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime recordDate;

    @ManyToMany
    @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups = new ArrayList<>();

//    public boolean passwordEqualsTo(String password) {
//        return getPassword().equals(password);
//    }
//
//    public boolean passwordIsNotEqualsTo(String password) {
//        return !passwordEqualsTo(password);
//    }

    public boolean isNew() {
        return getId() == null;
    }

    public boolean removeGroup(Group group) {
        return getGroups().remove(group);
    }

    public boolean addGroup(Group group) {
        return getGroups().add(group);
    }
}
