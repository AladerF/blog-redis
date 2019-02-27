package com.blog.service;

import com.blog.Dao.TagRepository;
import com.blog.NotFoundException;
import com.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag updateTag(Long id, Tag type) {
        Tag one = tagRepository.findOne(id);
        if(one==null){
            throw new NotFoundException("类型不存在");
        }
        BeanUtils.copyProperties(type,one);
        return tagRepository.save(one);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }
}
