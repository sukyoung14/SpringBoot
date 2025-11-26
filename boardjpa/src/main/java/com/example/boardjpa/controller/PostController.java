package com.example.boardjpa.controller;

import com.example.boardjpa.entity.Post;
import com.example.boardjpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "posts/list";
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "posts/form";
    }

    @PostMapping()
    public String create(@ModelAttribute Post post) {
        postService.createPost(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/update")
    public String edit(@PathVariable Long id, Model model){
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute Post post){
        postService.updatePost(id, post);
        return "redirect:/posts/" + id;
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        //PostDto post = postRepository.findById(id);
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        //postRepository.delete(id);
        postService.deletePost(id);
        return "redirect:/posts";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model){
        List<Post> posts = postService.searchPosts(keyword);
        model.addAttribute("posts", posts);
        return "posts/list";
    }
}
