package com.example.recruitmentprojectgithub.repository;

import com.example.recruitmentprojectgithub.model.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubRepo {

    private String branchesUrl;
    private boolean fork;
    private List<Branch> branches;
}
