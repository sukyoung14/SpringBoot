package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TodoController {
    private final TodoRepository todoRepository = new TodoRepository();

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
        TodoDto todo = todoRepository.Save(todoDto);
        model.addAttribute("todo", todo);
        return "create";
    }


}
