package com.good.xuchao.example;

import com.good.xuchao.example.bean.BeanA;
import com.good.xuchao.example.bean.BeanB;
import com.good.xuchao.example.bean.BeanC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
@RestController
public class ExampleApplication {

    @Resource
    private BeanA beanA;

    @Resource
    private BeanB beanB;

    @Resource
    private BeanC beanC;

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @RequestMapping(path = "/wow", method = RequestMethod.GET)
    public String hello() {
        /*
           desc: this is an example of circular dependency test
           given:
             beanA depends on beanB,and proxy by facadeAspect
             beanB depends on beanA,and proxy by facadeAspect
             beanC depends nothing,and proxy by facadeAspect
           when:
            sequence call->
             beanA.helloA("1");
             beanA.helloB("2");
             beanB.helloA("3");
             beanB.helloB("4");
             beanC.helloC("5");
           then:
            log output->
             helloA|cost:118|"1"|result:"BeanA1"
             helloB|cost:3|"2"|result:"BeanB2"
             helloB|cost:12|"2"|result:"BeanABeanB2"
             helloA|cost:0|"3"|result:"BeanA3"
             helloA|cost:5|"3"|result:"BeanBBeanA3"
             helloB|cost:0|"4"|result:"BeanB4"
             helloC|cost:2|"5"|result:"BeanC5"
           analysis:
            from the log output,i find that
              we autowire the proxy instance of beanA which also injected the proxy instance of beanB,
              and the same situation for beanB.
              beanC is just a plain proxy bean.
              all beans work just like what I need.
         */
         beanA.helloA("1");
         beanA.helloB("2");
         beanB.helloA("3");
         beanB.helloB("4");
         beanC.helloC("5");
         return "ok";
    }
}
