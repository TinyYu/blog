package com.ly.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.pojo.Type;
import com.ly.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    TypeService typeService;

    // 分页查询，展示给前端数据
    @RequestMapping("/types")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum, Model model){
        PageHelper.startPage(pageNum,10);       // pageNum:当前页码数，第一次进来时默认为1（首页）
        List<Type> listTypeByLimit = typeService.getListType(); //list:页面要展示的数据的集合
        PageInfo<Type> pageInfo = new PageInfo<>(listTypeByLimit); //pageInfo:将分页数据和显示的数据封装到PageInfo当中
        model.addAttribute("listType",pageInfo.getList());  //将封装好的数据返回到前台页面
        model.addAttribute("pageInfo",pageInfo);
        return "admin/types";
    }

    // 跳转到添加页面
    @RequestMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    // 添加数据，返回添加数据的状态信息
    @RequestMapping("/addtypes")
    public String addTypes(@RequestParam("typeName") String typeName,RedirectAttributes attributes){
        Type type = typeService.getTypeByName(typeName);
        if (type == null){
            Type type1 = new Type();
            type1.setName(typeName);
            Integer integer = typeService.addType(type1); // 返回受影响的行数
            if (integer != null){
                attributes.addFlashAttribute("message","操作成功!");
            } else {
                attributes.addFlashAttribute("message","操作失败!");
            }
        } else {
            attributes.addFlashAttribute("message","操作失败,分类名重复!");
        }

        return "redirect:/admin/types";
    }



    // 修改页
    @GetMapping("types/{id}/input")
    public String updateType(@PathVariable("id") Integer id, Model model){
        Type type = typeService.getTypeById(id);
        model.addAttribute("type",type);
        return "admin/types-update";
    }

    // 修改数据，返回添加数据的状态信息
    @RequestMapping("/update")
    public String updateTypes(@RequestParam("typeName") String typeName,Integer id,RedirectAttributes attributes){
        Type type = typeService.getTypeById(id);

        if (type != null){
            Type typeByName = typeService.getTypeByName(typeName);
            if (typeByName == null){
                type.setName(typeName);
                Integer integer = typeService.updateType(type); // 返回受影响的行数
                if (integer != null){
                    attributes.addFlashAttribute("message","操作成功!");
                } else {
                    attributes.addFlashAttribute("message","操作失败!");
                }
            } else {
                attributes.addFlashAttribute("message","操作失败,分类名重复!");
            }
        } else {
            attributes.addFlashAttribute("message","操作失败，没有找到要修改的数据!");
        }

        return "redirect:/admin/types";
    }

    //删除数据
    @RequestMapping("types/{id}/delete")
    public String deleteTypes(@PathVariable("id") Integer id){
        typeService.deleteType(id);
        return "redirect:/admin/types";
    }


}
