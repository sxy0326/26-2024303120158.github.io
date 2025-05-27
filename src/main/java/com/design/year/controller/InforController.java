package com.design.year.controller;


import com.design.year.mapper.InforMapper;
import com.design.year.result.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/infortable")
public class InforController {

    @Resource
    private InforMapper inforMapper;

    @GetMapping("/{region}")
    public Result<?> getInfoNums(@PathVariable String region)
    {
        return Result.success(inforMapper.getInfoNum(region));
    }
}
