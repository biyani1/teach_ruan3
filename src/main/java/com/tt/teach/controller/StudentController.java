package com.tt.teach.controller;

import com.tt.teach.pojo.Student;
import com.tt.teach.service.StudentService;
import com.tt.teach.until.BaseController;
import com.tt.teach.until.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
/**
*@作者：biyani
*@时间：2018/12/20 17:26
*@描述：
*/
@Controller
@RequestMapping("/stu")
public class StudentController extends BaseController {
    @Resource
    private StudentService studentService;

    @RequestMapping("/login")   //http://localhost:8090/stu/login
    public String login() {
        return "/student/login";
    }
    @RequestMapping("/index")   //http://localhost:8090/stu/index
    public String index() {
        String studentName = (String) getSession().getAttribute(SESSION_KEY);
        if (studentName!=null){
            return "/student/index";
        }
        return REDIRECT+"/stu/login";
    }
    @RequestMapping("/student")   //http://localhost:8090/stu/student
    public String student() {
        return "/student/student";
    }

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public String doLogin() {
        String xuehao = getRequset().getParameter("studentNo");
        Integer studentNo = Integer.parseInt(xuehao);
        String loginPwd = getRequset().getParameter("loginPwd");
        Student student = new Student();
        student.setLoginPwd(loginPwd);
        student.setStudentNo(studentNo);
        Student student1 = studentService.doLogin(student);
        if (student1!=null){
            getSession().setAttribute(SESSION_KEY,student1.getStudentName());
            return FORWARD+"/stu/index";
        }
        return REDIRECT+"/stu/login";
    }
    //注销的方法
    @RequestMapping("/logout")
    public String logout() {
        getSession().removeAttribute(SESSION_KEY);
        return REDIRECT+"/stu/login";
    }
    //遍历Student中的信息
    @RequestMapping(value = "getStudentList",method = RequestMethod.GET)
    @ResponseBody
    public Object getStudentList() {
        List<Student> list = studentService.getStudentList();
        return list;
    }
    //删除
    @RequestMapping(value = "/deleteStudent/{stuNo}",method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteStudent(@PathVariable Integer stuNo) {
        int result = studentService.deleteStudent(stuNo);
        if (result>0){
            return JsonResult.ok("删除成功",result);
        }
        return JsonResult.ok("删除失败",result);
    }
    @RequestMapping(value = "/updateStudent",method = RequestMethod.POST)
    public String updateStudent() {
        //学号、姓名、密码、电话
        String xuehao = getRequset().getParameter("stuNo");
        Integer stuNo = Integer.parseInt(xuehao);
        String stuName = getRequset().getParameter("stuName");
        String stuPwd = getRequset().getParameter("stuPwd");
        String stuPhone = getRequset().getParameter("stuPhone");
        Student student = new Student();
        student.setStudentNo(stuNo);
        student.setLoginPwd(stuPwd);
        student.setPhone(stuPhone);
        student.setStudentName(stuName);
        int result = studentService.updateStudent(student);
        if (result>0){
            return FORWARD+"/stu/student";
        }
        return  FORWARD+"/stu/student";
    }
}
