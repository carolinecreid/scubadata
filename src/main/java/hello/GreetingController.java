/*
    RESTful API calls that has Java do GET and POST calls
   /greeting sends the form inputs onto the second API call which gets started on /result and posts to /hello
 */


package hello;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class GreetingController {

    @RequestMapping("/")
    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());

        return "greeting";
    }

    @PostMapping("/greeting")
    public String helloHtml(@ModelAttribute Greeting greeting) {
        greeting.setDomain(greeting.getDomain());
        greeting.setContent(greeting.getContent());
        greeting.setKeyword(greeting.getKeyword());
        return "result";
    }

    @PostMapping("/result")
    public String greetingSubmit(@ModelAttribute Greeting greeting) throws IOException {
        WordSearch wordSearch = new WordSearch();
        greeting.setContent(wordSearch.run(greeting.getDomain(), greeting.getContent()));
        greeting.setKeyword(wordSearch.wordOccurences(greeting.getKeyword()));
        return "hello";
    }



}
