package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") //static: 웹 어플리케이션에서 /hello 들어오면 아래 메서드 호출됨
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!!"); //모델에 data:hello!!!! 넣어서 던져줌
        return "hello"; //templates\hello.html 자동으로 찾아서 페이지 나타남
    }

    @GetMapping("hello-mvc") //mvc+template engine: http://localhost:8080/hello-mvc?name=a 형식으로 접속
    public String helloMvc(@RequestParam(value = "name", required = false ) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-spring") //api
    @ResponseBody //http response body에 데이터 응답
    public String helloSpring(@RequestParam("name") String name) {
        return "hello" + name; //name=a 면 "helloa" 그대로 클라이언트에게 전송
    }

    @GetMapping("hello-api") //가장 많이 사용하는 api 방식
    @ResponseBody //http://localhost:8080/hello-api?name=a
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; //hello라는 이름의 Hello 객체 생성하여 json 형태로 반환
    }

    static class Hello { //static class를 클래스 안에 생성
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
