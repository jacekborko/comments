package com.jb.comments.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.comments.client.PostClient;
import com.jb.comments.domain.Comment;
import com.jb.comments.domain.Post;
import com.jb.comments.error.BusinessException;
import com.jb.comments.error.ErrorType;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/")
public class PostController {

    @Value("${comments.dir.path}")
    private String commentsDir;

    private Path commentsPath;

    private final PostClient postService;

    public PostController(final PostClient postService) {
        this.postService = postService;
    }

    @RequestMapping(path = "/fetchComments/{size}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity fetchComments(@PathVariable("size") int size) {
        checkAndPrepareDirectory();

        Map<String, List<Comment>> commentsByDomain = postService.getPosts(size).stream()
                .map(Post::getComments)
                .flatMap(List::stream)
                .filter(Comment::hasEmail)
                .collect(Collectors.groupingBy(Comment::getEmailDomain));

        saveCommentsInDirectory(commentsByDomain);

        return new ResponseEntity(HttpStatus.OK);
    }


    private void checkAndPrepareDirectory() {
        commentsPath = Optional.ofNullable(commentsDir)
                .map(Paths::get)
                .filter(Files::exists)
                .orElseThrow(() -> new BusinessException(ErrorType.BASE_DIR_CONFIGURATION_ERROR));

        try {
            FileUtils.deleteDirectory(commentsPath.toFile());
        } catch (IOException e) {
            throw new BusinessException(ErrorType.CANNOT_CLEAR_BASE_DIR);
        }
        if (!commentsPath.toFile().mkdir()) {
            throw new BusinessException(ErrorType.CANNOT_CREATE_BASE_DIR);
        }
    }


    private void saveCommentsInDirectory(Map<String, List<Comment>> commentsByDomain) {
        for (Map.Entry<String, List<Comment>> entry : commentsByDomain.entrySet()) {
            File domainDir = Paths.get(commentsPath.toString(), entry.getKey()).toFile();

            if (!domainDir.mkdir()) {
                throw new BusinessException(ErrorType.CANNOT_CREATE_DOMAIN_DIR);
            } else {
                saveComments(entry.getValue(), domainDir);
            }
        }
    }


    private void saveComments(List<Comment> comments, File domainDir) {
        ObjectMapper mapper = new ObjectMapper();

        for (Comment comment : comments) {
            String commentAsString;
            try {
                commentAsString = mapper.writeValueAsString(comment);
            } catch (JsonProcessingException e) {
                throw new BusinessException(ErrorType.ERROR_WHILE_PARSE_COMMENT);
            }

            Path commentPath = Paths.get(domainDir.getPath(), String.valueOf(comment.getId()));

            saveCommentToFile(commentAsString, commentPath);
        }
    }


    private void saveCommentToFile(String commentAsString, Path commentPath) throws BusinessException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(commentPath.toFile()));
            writer.write(commentAsString);
        } catch (IOException e) {
            throw new BusinessException(ErrorType.ERROR_WHILE_SAVING_FILE);
        } finally {
            try {
                if (Objects.nonNull(writer)) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
