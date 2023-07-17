package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hmdp.dto.Result;
import com.hmdp.dto.ScrollResult;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Blog;
import com.hmdp.entity.Follow;
import com.hmdp.entity.User;
import com.hmdp.mapper.BlogMapper;
import com.hmdp.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.service.IFollowService;
import com.hmdp.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hmdp.utils.RedisConstants.BLOG_LIKED_KEY;
import static com.hmdp.utils.RedisConstants.FEED_KEY;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Resource
    private UserServiceImpl userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IFollowService followService;


    @Override
    public Result queryBlogById(Long id) {
        // 查询blog
        Blog blog = getById(id);
        if (blog == null) return Result.fail("笔记不存在！");
        Long userId = blog.getUserId();
        User user = userService.getById(userId);
        blog.setName(user.getNickName());
        blog.setIcon(user.getIcon());
        isBlogLiked(blog);
        return Result.ok(blog);
    }

    /**
     * 判断用户是否点赞
     *
     * @param blog
     */
    public void isBlogLiked(Blog blog) {
        UserDTO user = UserHolder.getUser();
        // 当用户未登入时
        if (user == null) {
            // 用户未登入，无需查询
            return;
        }
        // 获取当前用户
        Long userId = user.getId();
        String key = BLOG_LIKED_KEY + blog.getId();
        // 判断当前用户是否点赞
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        blog.setIsLike(score != null);
    }

    /**
     * 查询点赞列表（根据点赞时间排序）
     *
     * @param id
     * @return
     */

    @Override
    public Result queryBlogLikes(Long id) {
        // 查询top5的点赞用户
        Set<String> userSet = stringRedisTemplate.opsForZSet().range(BLOG_LIKED_KEY + id, 0, 4);
        // 判断是否为空
        if (userSet == null || userSet.isEmpty()) return Result.ok(Collections.emptyList());
        // 解析用户id
        List<Long> userIds = userSet.stream().map(Long::valueOf).collect(Collectors.toList());
        // 为了在in中传入的id和查询出来的id顺序一致，需要使用order by field ，如：where id in (1,2) order by field(id,1,2)
        String idStr = StrUtil.join(",", userIds);
        // 根据id查用户
        List<UserDTO> userDTOS = userService.query()
                .in("id", userIds)
                .last("ORDER BY FIELD(id," + idStr + ")")
                .list()
                .stream()
                .map(user -> BeanUtil.copyProperties(user, UserDTO.class)).collect(Collectors.toList());

        //  返回
        return Result.ok(userDTOS);
    }

    @Override
    public Result saveBlog(Blog blog) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        blog.setUserId(user.getId());
        // 保存探店博文
        boolean isSuccess = save(blog);
        // 查询博客作者的粉丝，将笔记id推送给粉丝
        if (!isSuccess) {
            return Result.fail("添加笔记失败");
        }
        Long userId = blog.getUserId(); // 作者id
        List<Follow> follows = followService.query().eq("follow_user_id", userId).list();
        // 推送笔记
        for (Follow follow : follows) {
            // 粉丝id
            Long id = follow.getUserId();
            // 推送
            String key = FEED_KEY + id;
            stringRedisTemplate.opsForZSet().add(key, blog.getId().toString(), System.currentTimeMillis());
        }
        // 返回id
        return Result.ok(blog.getId());
    }

    /**
     * 查询收件里面的笔记
     *
     * @param max    上次查询的最小时间戳
     * @param offset 上次查询的score的最小值的相同个数
     * @return
     */
    @Override
    public Result queryBlogOfFollow(Long max, Integer offset) {
        // 获取当前用户
        Long userId = UserHolder.getUser().getId();
        // 查询收件箱 ZREVRANGEBYSCORE key Max Min WITHSCORES LIMIT offset count
        String key = FEED_KEY + userId;
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.opsForZSet()
                .reverseRangeByScoreWithScores(key, 0, max, offset, 3);
        // 非空判断
        if (typedTuples == null || typedTuples.isEmpty()) return Result.ok();

        // 解析数据blogId，score（时间戳），offset
        List<Long> ids = new ArrayList<>(typedTuples.size());
        long minTime = 0;
        int os = 1;
        for (ZSetOperations.TypedTuple<String> typedTuple : typedTuples) {
            // 获取id
            String id = typedTuple.getValue();
            ids.add(Long.valueOf(id));
            // 获取分数
            long time = typedTuple.getScore().longValue();
            if (time == minTime) {
                // 判断最小时间戳（score）相同的个数
                os++;
            } else {
                minTime = time;
                os = 1;
            }
        }
        // 根据id查询blog
        String idStr = StrUtil.join(",", ids);
        // 根据id的顺序有序查找
        List<Blog> blogs = query().in("id", ids).last("ORDER BY FIELD(id," + idStr + ")").list();
        for (Blog blog : blogs) {
            // 查询博客相关信息
            Long id = blog.getUserId();
            User user = userService.getById(id);
            blog.setName(user.getNickName());
            blog.setIcon(user.getIcon());
            isBlogLiked(blog);
        }
        ScrollResult scrollResult = new ScrollResult();
        scrollResult.setList(blogs);
        scrollResult.setMinTime(minTime);
        scrollResult.setOffset(os);
        // 封装并返回
        return Result.ok(scrollResult);
    }

    /**
     * 点赞业务，使用sorted_set数据结构完成（其set集合中的元素使用score
     *
     * @param id
     * @return
     */

    @Override
    public Result likeBlog(Long id) {
        // 获取当前用户
        Long userId = UserHolder.getUser().getId();
        String key = BLOG_LIKED_KEY + id;
        // 判断当前用户是否点赞
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        // 如果未点赞，可以点赞
        if (score == null) {
            // 数据+1并保存到redis中
            boolean success = update().setSql("liked = liked + 1").eq("id", id).update();
            if (success) {
                stringRedisTemplate.opsForZSet().add(key, userId.toString(), System.currentTimeMillis());
            }
        } else {
            // 如果已点赞，取消点赞
            // 数据库点赞数-1并在redis中移除
            boolean success = update().setSql("liked = liked - 1").eq("id", id).update();
            if (success) {
                stringRedisTemplate.opsForZSet().remove(key, userId.toString());
            }

        }
        return Result.ok();
    }
}
