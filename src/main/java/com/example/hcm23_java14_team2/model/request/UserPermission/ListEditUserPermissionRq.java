package com.example.hcm23_java14_team2.model.request.UserPermission;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListEditUserPermissionRq {
    private List<EditPermissionRequest> list;
}
