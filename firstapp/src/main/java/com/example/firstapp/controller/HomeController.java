package com.example.firstapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/hello")
    public String hello(Model model){
        String name = "changhee";
        model.addAttribute("name",name);
        return "hello";
    }

    @GetMapping("/user")
    public String user(Model model){
        model.addAttribute("username","KIM");
        model.addAttribute("age",20);
        model.addAttribute("city","seoul");
        return "user";
    }

    @GetMapping("/fruists")
    public String fruists(Model model){
        List<String> fruitList = new ArrayList<>();
        fruitList.add("apple");
        fruitList.add("orange");
        fruitList.add("banana");

        model.addAttribute("fruitList",fruitList);
        return "fruists";
    }

    @GetMapping("/grade")
    public String grade(Model model){
        int score = 80;

        model.addAttribute("score",score);
        return "grade";
    }

    @GetMapping("/lunch")
    public String lunch(Model model){
        List<String> menus = Arrays.asList("김밥","라면","돈까스");
        Random random = new Random();
        String pick = menus.get(random.nextInt(menus.size()));
        model.addAttribute("pick",pick);
        return "lunch";
    }

    @GetMapping("/lotto")
    public String lotto(Model model){
        List<Integer> numbers = IntStream.range(1,46)
                .boxed()            // int -> Integer형변환
                .collect(Collectors.toList());  // 배열로 변환
        Collections.shuffle(numbers); // 썩어주기
        List<Integer> lucky = numbers.subList(0,6);
        model.addAttribute("lucky",lucky);
        return "lotto";
    }

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable String username, Model model){
        model.addAttribute("username",username);
        return "profile";
    }

    @GetMapping("/cube/{number}")
    public String cube(@PathVariable int number, Model model){
        int result = number * number * number;
        model.addAttribute("number",number);
        model.addAttribute("result",result);
        return "cube";
    }
    // 짝수 홀수 판별
    @GetMapping("/number/{num}")
    public String number(@PathVariable int num, Model model){
        boolean result = false;
        if (num % 2 == 0) result = true;
        model.addAttribute("num",num);
        model.addAttribute("result",result);
        return "number";
    }

    // 나이계산 (Year 클래스)
    @GetMapping("/age/{birthYear}")
    public String age(@PathVariable int birthYear, Model model){
        int result =  Year.now().getValue() - birthYear;
        model.addAttribute("birthYear",birthYear);
        model.addAttribute("result",result);
        return "age";
    }

    @GetMapping("/ping")
    public String ping(Model model){
        return "ping";
    }
    @GetMapping("/pong")
    public String pong(@RequestParam String title, @RequestParam String content, Model model){
        model.addAttribute("title",title);
        model.addAttribute("content",content);
        return "pong";
    }
}
