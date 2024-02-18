package com.example.recruitmentprojectgithub.controller;

import com.example.recruitmentprojectgithub.model.ErrorResponse;
import com.example.recruitmentprojectgithub.repository.GithubRepo;
import com.example.recruitmentprojectgithub.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GithubService githubService;

    @Autowired
    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/repos/{username}")
    public ResponseEntity<?> listUserRepositories(
            @PathVariable String username,
            @RequestHeader("Accept") String acceptHeader
    ) {
        if (!acceptHeader.equals("application/json")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Accept header must be application/json");
        }

        try {
            GithubRepo[] repositories = githubService.getRepos(username).getRepos();
            if (repositories == null || repositories.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found"));
            }
            return ResponseEntity.ok().body(repositories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
}
