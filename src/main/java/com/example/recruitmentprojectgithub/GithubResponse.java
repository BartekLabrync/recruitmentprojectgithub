package com.example.recruitmentprojectgithub;

import com.example.recruitmentprojectgithub.model.ErrorResponse;
import com.example.recruitmentprojectgithub.repository.GithubRepo;

public interface GithubResponse {
    GithubRepo[] getRepos();
    ErrorResponse getErrorResponse();
}

