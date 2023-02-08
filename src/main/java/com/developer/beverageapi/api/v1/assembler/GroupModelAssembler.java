package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.controller.ControllerGroup;
import com.developer.beverageapi.api.v1.model.GroupModel;
import com.developer.beverageapi.core.security.Security;
import com.developer.beverageapi.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GroupModelAssembler extends RepresentationModelAssemblerSupport<Group, GroupModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @Autowired
    private Security security;

    public GroupModelAssembler() {
        super(ControllerGroup.class, GroupModel.class);
    }

    @Override
    public GroupModel toModel(Group group) {
        GroupModel groupModel = createModelWithId(group.getId(), group);
        modelMapper.map(group, groupModel);

        if (security.allowedToConsultUsersGroupsPermissions()) {
            groupModel.add(instantiateLinks.linkToGroups("groups"));

            groupModel.add(instantiateLinks.linkToPermissionsGroup(group.getId(), "permissions"));
        }

        return groupModel;
    }

    @Override
    public CollectionModel<GroupModel> toCollectionModel(Iterable<? extends Group> entities) {
        CollectionModel<GroupModel> collectionModel = super.toCollectionModel(entities);

        if (security.allowedToConsultUsersGroupsPermissions()) {
            collectionModel.add(instantiateLinks.linkToGroups());
        }

        return collectionModel;
    }
}
