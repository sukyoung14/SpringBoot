package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TodoController {
//    private final TodoRepository todoRepository = new TodoRepository();
    private TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/todos")
    public String todos(Model model){
        //TodoRepository todoRepository = new TodoRepository();  //이전에 만들었던 Repository와 다른 객체를 사용하면 안됨
        List<TodoDto> todoDtos = todoRepository.findAll();
        model.addAttribute("todos", todoDtos);
        return "todos";
    }

    @GetMapping("/todos/new")
    public String newTodos(){
        return "new";
    }

    @GetMapping("/todos/create")
    public String create(@RequestParam String title, @RequestParam String content, Model model){
        TodoDto todoDto = new TodoDto(null, title, content, false);
        //TodoRepository todoRepository = new TodoRepository(); //전역으로 변경
        TodoDto todo = todoRepository.save(todoDto);
        model.addAttribute("todo", todo);
//        return "create";
        return "redirect:/todos";
    }
    @GetMapping("/todos/{id}")
    public String detail(@PathVariable Long id, Model model) {
        //TodoDto todo = todoRepository.findById(id);
        try {
            TodoDto todo = todoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("todo not found!!!"));
            model.addAttribute("todo", todo);
            return "detail";
        } catch (IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @GetMapping("/todos/{id}/delete")
    public String delete(@PathVariable Long id, Model model){
        todoRepository.deleteById(id);
        return "redirect:/todos";
    }

    @GetMapping("/todos/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        try {
            TodoDto todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("todo not found!!!"));
            model.addAttribute("todo", todo);
            return "edit";
        } catch (IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @GetMapping("/todos/{id}/update")
    public String update(@PathVariable Long id, @RequestParam String title, @RequestParam String content, @RequestParam(defaultValue = "false") boolean completed, Model model){
        try {
            TodoDto todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("todo not found!!!"));
            todo.setTitle(title);
            todo.setContent(content);
            todo.setCompleted(completed);

            todoRepository.save(todo);
            model.addAttribute("todo", todo);
            return "redirect:/todos/" + id;
        }
        catch (IllegalArgumentException e){
            return "redirect:/todos";
        }
    }

    @GetMapping("todos/search")
    public String search(@RequestParam String keyword,Model model){
        List<TodoDto> todoDtos = todoRepository.findByTitleContaining(keyword);
        model.addAttribute("todos", todoDtos);
        return "todos";
    }

    @GetMapping("/todos/active")
    public String active(Model model){
        List<TodoDto> todos = todoRepository.findBycompleted(false);
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/todos/completed")
    public String completed(Model model){
        List<TodoDto> todos = todoRepository.findBycompleted(true);
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/todos/{id}/toggle")
    public String toggle(@PathVariable Long id, Model model){
        try {
            TodoDto todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("todo not found!!!"));
            todo.setCompleted(!todo.isCompleted());
            todoRepository.save(todo);
            return "redirect:/todos/" + id;
        } catch (IllegalArgumentException e){
            return "redirect:/todos";
        }
    }


}
