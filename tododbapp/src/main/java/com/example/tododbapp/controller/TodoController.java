package com.example.tododbapp.controller;

import com.example.tododbapp.dto.TodoDto;
import com.example.tododbapp.entity.TodoEntity;
import com.example.tododbapp.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("")
    public String todos(Model model){
        List<TodoDto> todoDtos = todoService.getAllTodos();
        model.addAttribute("todos", todoDtos);
        model.addAttribute("totalCount", todoService.getTotalCount());
        model.addAttribute("completedCount", todoService.getCompletedCount());
        model.addAttribute("activeCount", todoService.getActiveCount());
        return "todos";
    }

    @GetMapping("/new")
    public String newTodos(Model model){
        model.addAttribute("todo", new TodoDto());
        return "form";
    }

    @PostMapping()
    public String create(@ModelAttribute TodoDto todo, RedirectAttributes redirectAttributes, Model model){
        todoService.createTodo(todo);
        redirectAttributes.addFlashAttribute("message", "할 일이 생성되었습니다.");
        return "redirect:/todos";
    }

    @GetMapping("/{id}/update")
    public String edit(@PathVariable Long id, Model model){
        TodoDto todo = todoService.getTodoById(id);
        model.addAttribute("todo", todo);
        return "form";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute TodoDto todo, RedirectAttributes redirectAttributes){
        todoService.updateTodoById(id, todo);
        redirectAttributes.addFlashAttribute("message", "할일이 수정되었습니다.");
        return "redirect:/todos/" + id;
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        TodoDto todo = todoService.getTodoById(id);
        model.addAttribute("todo", todo);
        return "detail";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        todoService.deleteTodoById(id);
        redirectAttributes.addFlashAttribute("message", "할일이 삭제되었습니다.");
        redirectAttributes.addFlashAttribute("status", "delete");
        return "redirect:/todos";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword,Model model){
        List<TodoDto> todoDtos = todoService.searchTodos(keyword);
        model.addAttribute("todos", todoDtos);
        return "todos";
    }

    @GetMapping("/active")
    public String active(Model model){
        List<TodoDto> todos = todoService.getTodosByCompleted(false);
        model.addAttribute("todos", todos);
        model.addAttribute("totalCount", todoService.getTotalCount());
        model.addAttribute("completedCount", todoService.getCompletedCount());
        model.addAttribute("activeCount", todoService.getActiveCount());
        return "todos";
    }

    @GetMapping("/completed")
    public String completed(Model model){
        List<TodoDto> todos = todoService.getTodosByCompleted(true);
        model.addAttribute("todos", todos);
        model.addAttribute("totalCount", todoService.getTotalCount());
        model.addAttribute("completedCount", todoService.getCompletedCount());
        model.addAttribute("activeCount", todoService.getActiveCount());
        return "todos";
    }


    @GetMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id, Model model){
        TodoDto todo = todoService.getTodoById(id);
        todoService.toggleCompleted(id);
        return "redirect:/todos/" + id;
    }

    @GetMapping("/delete-completed")
    public String deleteCompleted(RedirectAttributes redirectAttributes){
        todoService.deleteCompletedTodos();
        redirectAttributes.addFlashAttribute("message", "완료된 할일이 삭제되었습니다.");
        return "redirect:/todos";
    }
}
