package com.four.exam.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.four.exam.entity.Student;
import com.four.exam.repository.StudentRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class StudentController {
    @Resource
    private StudentRepository studentRepository;
    @RequestMapping("stuselAll.do")
    public Object stuselAll(){
        List<Student> studentList = studentRepository.findAll();

        return studentList;
    }
    @RequestMapping("savestu.do")
    public void getStudent(HttpServletRequest req)
    {
        ArrayList<Student> list=JSON.parseObject(req.getParameter("sdata1"),new TypeReference<ArrayList<Student>>(){});
        studentRepository.save(list.get(0));
        System.out.println(list.get(0));
    }
    @RequestMapping("deleteAll.do")
    public void deleteAllStudent(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            Integer h=(new Integer(ids[i]));
            studentRepository.deleteById(h);

        }
    }
    @RequestMapping("deleteone.do")
    public void deleteOneStudent(String selsid){
        studentRepository.deleteById(new Integer(selsid));
    }
    @RequestMapping("insert.do")
    public  void inserintostudent(String stu){
        System.out.println(stu);
        Student student = JSONObject.parseObject(stu, Student.class);
        studentRepository.save(student);
        System.out.println(student);
    }
    @RequestMapping("tiaojian.do")
    public Object selectti(String seltiaojian){


        return studentRepository.findBySdepLike(seltiaojian);
    }
    @RequestMapping("chaxuns.do")
    public Object chaxuns(String[] tiao) {
        if (tiao[1] == "") {

            return studentRepository.findBySnameLike("%" + tiao[0] + "%");

        } else {
            return studentRepository.findBySnameLikeAndScreatdateBetween("%" + tiao[0] + "%", tiao[1], tiao[2]);
        }


    }
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @RequestMapping("saveall.do")
    public String saveAllStudent(String allstu,HttpServletRequest request){
        ArrayList<Student> list=JSON.parseObject(allstu,new TypeReference<ArrayList<Student>>(){});
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setScreatdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        studentRepository.saveAll(list);
        return "插入成功";
    }
}
