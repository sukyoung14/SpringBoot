package com.example.board.controller;

import com.example.board.dto.PostDto;
import com.example.board.entity.Post;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    //    private final PostRepository postRepository;
    private final PostService postService;
    @GetMapping
    public String list(@PageableDefault(size=10, sort = "id", direction= Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        Page<Post> postPage = postService.getPostsPage(pageable);

        int currentPage = pageable.getPageNumber();
        int totalPages = postPage.getTotalPages();
        int startPage = Math.max(0, currentPage - 5);
        int endPage = Math.min(totalPages-1, currentPage + 5);

        //model.addAttribute("posts", postPage.getContent());
        model.addAttribute("postPage", postPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "posts/list";
    }
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        //PostDto post = postRepository.findById(id);
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }
    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "posts/form";
    }

    @PostMapping()
    public String create(@ModelAttribute Post post) {
        //postRepository.save(post);
        postService.createPost(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/update")
    public String edit(@PathVariable Long id, Model model){
        //PostDto post = postRepository.findById(id);
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute Post post){
//        postRepository.update(id, post);
        postService.updatePost(id, post);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        //postRepository.delete(id);
        postService.deletePost(id);
        return "redirect:/posts";
    }

    @GetMapping("/test/cache")
    public String testCache(){
        postService.testFirstLevelCache();
        return "redirect:/posts";
    }

    @GetMapping("/test/write-behind")
    public String testWriteBehind(){
        postService.testWriteBehind();
        return "redirect:/posts";
    }

    @GetMapping("/test/Dirty-Checking")
    public String testDirtyChecking(){
        postService.testDirtyChecking();
        return "redirect:/posts";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, @PageableDefault(sort="id") Pageable pageable, Model model){
        Page<Post> postPage = postService.searchPostsPage(keyword, pageable);

        int currentPage = pageable.getPageNumber();
        int totalPages = postPage.getTotalPages();
        int startPage = Math.max(0, currentPage - 5);
        int endPage = Math.min(totalPages-1, currentPage + 5);

        model.addAttribute("posts", postPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "posts/search";
    }

    @GetMapping("/recent")
    public String recent(Model model){
        model.addAttribute("posts",  postService.getRecentPosts());
        return "posts/list";
    }

    @GetMapping("/dummy")
    public String dummy(){
        postService.createDummyPosts(100);
        return "redirect:/posts";
    }
}
