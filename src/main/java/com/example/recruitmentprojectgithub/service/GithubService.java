package com.example.recruitmentprojectgithub.service;

import com.example.recruitmentprojectgithub.GithubResponse;
import com.example.recruitmentprojectgithub.model.Branch;
import com.example.recruitmentprojectgithub.model.ErrorResponse;
import com.example.recruitmentprojectgithub.repository.GithubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class GithubService {

    private final RestTemplate restTemplate;

    @Autowired
    public GithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    public GithubResponse getRepos(String username) {
        try {
            String url = "https://developer.github.com/v3" + username + "/repos";
            url = url.replace("{", "%7B").replace("}", "%7D");
            GithubRepo[] repos = restTemplate.getForObject(url, GithubRepo[].class);

            if (repos != null) {
                for (GithubRepo repo : repos) {
                    if (!repo.isFork()) {
                        String branchesUrl = repo.getBranchesUrl().replace("{/branch}", "");
                        Branch[] branches = restTemplate.getForObject(branchesUrl, Branch[].class);
                        repo.setBranches(Arrays.asList(branches));
                    }
                }
            }

            return new GithubResponse() {
                @Override
                public GithubRepo[] getRepos() {
                    return repos;
                }

                @Override
                public ErrorResponse getErrorResponse() {
                    return null;
                }
            };
        } catch (HttpClientErrorException.NotFound ex) {
            return new GithubResponse() {
                @Override
                public GithubRepo[] getRepos() {
                    return null;
                }

                @Override
                public ErrorResponse getErrorResponse() {
                    return new ErrorResponse(404, "User not found");
                }
            };
        }
    }


}
