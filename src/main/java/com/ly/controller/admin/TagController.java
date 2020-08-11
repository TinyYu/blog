package com.ly.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.pojo.Tag;
import com.ly.pojo.Type;
import com.ly.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;

    // 分页查询，展示给前端数据
    @RequestMapping("/tags")
    public String togs(@RequestParam(defaultValue = "1") Integer pageNum, Model model){
        PageHelper.startPage(pageNum,10);       // pageNum:当前页码数，第一次进来时默认为1（首页）
        List<Tag> listTag = tagService.getListTag(); //list:页面要展示的数据的集合
        PageInfo<Tag> pageInfo = new PageInfo<>(listTag); //pageInfo:将分页数据和显示的数据封装到PageInfo当中
        model.addAttribute("listTag",pageInfo.getList());  //将封装好的数据返回到前台页面
        model.addAttribute("pageInfo",pageInfo);
        return "admin/tags";
    }

    // 跳转到添加页面
    @RequestMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/tags-input";
    }

    // 添加标签,返回添加数据的状态信息
    @RequestMapping("/addtags")
    public String addTags(@RequestParam("tagName") String tagName, RedirectAttributes attributes){
        Tag tag = tagService.getTagByName(tagName);
        if (tag == null){
            Tag tag1 = new Tag();
            tag1.setName(tagName);
            Integer integer = tagService.addTog(tag1); // 返回受影响的行数
            if (integer != null){
                attributes.addFlashAttribute("message","操作成功!");
            } else {
                attributes.addFlashAttribute("message","操作失败!");
            }
        } else {
            attributes.addFlashAttribute("message","操作失败,标签名重复!");
        }
        return "redirect:/admin/tags";
    }

    // 修改数据
    // 修改页
    @GetMapping("/tags/{id}/input")
    public String updateType(@PathVariable("id") Integer id, Model model){
        Tag tag = tagService.getTagById(id);
        model.addAttribute("tag",tag);
        return "admin/tags-update";
    }

    // 修改数据，返回添加数据的状态信息
    @RequestMapping("/updateTag")
    public String updateTypes(@RequestParam("tagName") String tagName,Integer id,RedirectAttributes attributes){
        Tag tag = tagService.getTagById(id);

        if (tag != null){
            Tag tagByName = tagService.getTagByName(tagName);
            if (tagByName == null){
                tag.setName(tagName);
                Integer integer = tagService.updateTog(tag); // 返回受影响的行数
                if (integer != null){
                    attributes.addFlashAttribute("message","操作成功!");
                } else {
                    attributes.addFlashAttribute("message","操作失败!");
                }
            } else {
                attributes.addFlashAttribute("message","操作失败,标签名重复!");
            }
        } else {
            attributes.addFlashAttribute("message","操作失败，没有找到要修改的数据!");
        }

        return "redirect:/admin/tags";
    }

    //删除数据
    @RequestMapping("tags/{id}/delete")
    public String deleteTypes(@PathVariable("id") Integer id){
        tagService.deleteTog(id);
        return "redirect:/admin/tags";
    }
}
