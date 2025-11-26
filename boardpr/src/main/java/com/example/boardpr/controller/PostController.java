package com.example.boardpr.controller;

import com.example.boardpr.dto.PostDto;
import com.example.boardpr.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "posts/list";
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new PostDto());
        return "posts/form";
    }

    @PostMapping()
    public String create(@ModelAttribute PostDto post) {
        postRepository.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/update")
    public String edit(@PathVariable Long id, Model model){
        PostDto post = postRepository.findById(id);
        model.addAttribute("post", post);
        return "posts/form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute PostDto post){
        postRepository.update(id, post);
        return "redirect:/posts/" + id;
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        PostDto post = postRepository.findById(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        postRepository.delete(id);
        return "redirect:/posts";
    }
}
