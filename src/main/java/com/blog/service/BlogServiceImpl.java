package com.blog.service;

import com.blog.Dao.BlogRepository;
import com.blog.NotFoundException;
import com.blog.po.Blog;
import com.blog.po.Type;
import com.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
               List<Predicate> predicate = new ArrayList<Predicate>();
               if(!"".equals(blog.getTitle()) && blog.getTitle() !=null){
                   predicate.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
               }
               if(blog.getTypeId() != null){
                   predicate.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
               }
                if(blog.isRecommend()){
                    predicate.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                criteriaQuery.where(predicate.toArray(new Predicate[predicate.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = blogRepository.findOne(id);
        if(one==null){
            throw new NotFoundException("博客不存在");
        }
        BeanUtils.copyProperties(one,blog);
        return blogRepository.save(one);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }
}
