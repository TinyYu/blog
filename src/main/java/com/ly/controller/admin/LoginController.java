package com.ly.controller.admin;

import com.ly.pojo.BlogUser;
import com.ly.service.BlogUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private BlogUserService blogUserService;

    @GetMapping
    public String toLogin(){
        return "admin/login";
    }

    @RequestMapping("/toLogin")
    public String toLogin_shiro(){ // 跳转到登陆页
        System.out.println("跳转到登陆页");
        return "admin/login";
    }

    @RequestMapping("/login")
    public String userLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){ // 用户登陆处理
        BlogUser blogUser = blogUserService.getBlogUserByName(username);

        // 获取当前的用户
        Subject subject = SecurityUtils.getSubject();

        // 加密: md5 md5盐值加密
        String salt = blogUser.getSalt(); // 盐
        int times = 2; // 加密次数
        String algorithmName = "md5"; // 加密方式
        String newPassword = new SimpleHash(algorithmName,password,salt,times).toString();

        // 封装当前用户的数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,newPassword);
        try{
            subject.login(token);// 执行登陆的方法

            blogUser.setPassword(null); // 返回密码为null
            session.setAttribute("user",blogUser);
            return "redirect:/admin/index";
        } catch (UnknownAccountException e){// 用户名不存在
            model.addAttribute("msg","用户名不存在");
            return "admin/login";
        } catch (IncorrectCredentialsException e){ // 密码不存在
            model.addAttribute("msg","密码错误");
            return "admin/login";
        }
    }

    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:../index";
    }

    @RequestMapping("/index")
    public String toIndex(){
        return "admin/index";
    }

}
