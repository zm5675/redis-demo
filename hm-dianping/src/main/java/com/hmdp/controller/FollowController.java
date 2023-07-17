package com.hmdp.controller;


import com.hmdp.dto.Result;
import com.hmdp.service.IFollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/follow")
public class FollowController {
    @Resource
    private IFollowService iFollowService;
    @PutMapping("/{id}/{isFollow}")
    public Result follow(@PathVariable("id") Long followUserId,@PathVariable("isFollow") Boolean isFollow){
        return iFollowService.follow(followUserId,isFollow);

    }

    @GetMapping("/or/not/{id}")
    public Result follow(@PathVariable("id") Long followId){
        return iFollowService.isFollow(followId);

    }
    @GetMapping("/common/{id}")
    public Result followCommon(@PathVariable("id") Long id){
        return iFollowService.followCommons(id);

    }


}
