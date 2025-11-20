package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
    @GetMapping()
    public String todos(Model model) {
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

    //@GetMapping("/create")
    @PostMapping()
    public String create(@ModelAttribute TodoDto todo, RedirectAttributes redirectAttributes){
        try{
            todoService.createTodo(todo);
            redirectAttributes.addFlashAttribute("message", "할 일이 생성되었습니다.");
            return "redirect:/todos";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/todos/new";
        }

    }
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        //TodoDto todo = todoRepository.findById(id);
        try {
            TodoDto todo = todoService.getTodoById(id);
            model.addAttribute("todo", todo);
            return "detail";
        } catch (IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        todoService.deleteTodoById(id);
        redirectAttributes.addFlashAttribute("message", "할일이 삭제되었습니다.");
        redirectAttributes.addFlashAttribute("status", "delete");
        return "redirect:/todos";
    }

    @GetMapping("/{id}/update")
    public String edit(@PathVariable Long id, Model model){
        try {
            TodoDto todo = todoService.getTodoById(id);
            model.addAttribute("todo", todo);
            return "form";
        } catch (IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute TodoDto todo, RedirectAttributes redirectAttributes){
        try {
            todoService.updateTodoById(id, todo);
            redirectAttributes.addFlashAttribute("message", "할일이 수정되었습니다.");
            return "redirect:/todos/" + id;
        }
        catch (IllegalArgumentException e){
            if (e.getMessage().contains("제목")) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/todos/" + id + "/update";
            } else {
                redirectAttributes.addFlashAttribute("message", "없는 할일입니다.");
                return "redirect:/todos";
            }
        }
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
        try {
//            TodoDto todo = todoService.getTodoById(id);
//            todo.setCompleted(!todo.isCompleted());
//            todoRepository.save(todo);
            todoService.toggleCompleted(id);
            return "redirect:/todos/" + id;
        } catch (IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @GetMapping("/delete-completed")
    public String deleteCompleted(RedirectAttributes redirectAttributes){
        todoService.deleteCompletedTodos();
        redirectAttributes.addFlashAttribute("message", "완료된 할일이 삭제되었습니다.");
        return "redirect:/todos";
    }





}
