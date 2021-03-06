package com.blog.web.admin;

import com.blog.po.Type;
import com.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public String type(@PageableDefault(size=3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){

        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable  Long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/delete")
    public String delType(@PathVariable  Long id , RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除操作成功");
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String postType(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes redirectAttributes){
        Type typeName = typeService.getTypeByName(type.getName());
        if(typeName!=null){
            result.rejectValue("name","nameError","名称不能重复");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        if(id!=null){
            Type t = typeService.updateType(id,type);
            if(t==null){
                redirectAttributes.addFlashAttribute("message","更新操作失败");
            }else{
                redirectAttributes.addFlashAttribute("message","更新操作成功");
            }
            return "redirect:/admin/types";
        }else{
            Type t = typeService.saveType(type);
            if(t==null){
                redirectAttributes.addFlashAttribute("message","新增操作失败");
            }else{
                redirectAttributes.addFlashAttribute("message","新增操作成功");
            }
            return "redirect:/admin/types";
        }

    }
}
