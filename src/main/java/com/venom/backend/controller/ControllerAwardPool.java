package com.venom.backend.controller;

import com.venom.backend.service.RenderPools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/AwardPool")
public class ControllerAwardPool {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    //取得所有pool的資訊
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAll(){
        RenderPools pools = beanFactory.getBean("awardPools", RenderPools.class);
        return new ResponseEntity<>(pools.getAll(), HttpStatus.OK);
    }

    //取得單一pool資訊
    @RequestMapping(value = "/Pool/{poolCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Integer>> getParticularPool(@PathVariable String poolCode){
        RenderPools pools = beanFactory.getBean("awardPools", RenderPools.class);
        return new ResponseEntity<>(pools.getPoolInfo(), HttpStatus.OK);
    }

    //create pool
    @RequestMapping(value = "/{poolCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> createPool(@PathVariable String poolCode){
        RenderPools pools = beanFactory.getBean("awardPools", RenderPools.class);
        return new ResponseEntity<>(pools.create(poolCode), HttpStatus.OK);
    }

    //(edit pool)put item into pool
    @RequestMapping(value = "/Pool/{poolCode}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> putIntoPool(@PathVariable String poolCode, @RequestBody Map<String, Object> reqMap){
        RenderPools pools = beanFactory.getBean("awardPools", RenderPools.class);
        return new ResponseEntity<>(pools.put(poolCode, reqMap), HttpStatus.OK);
    }

    //remove item from pool
    @RequestMapping(value = "/Pool/{poolCode}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> removeFromPool(@PathVariable String poolCode, @RequestBody Map<String, Object> reqMap){
        RenderPools pools = beanFactory.getBean("awardPools", RenderPools.class);
        return new ResponseEntity<>(pools.delete(poolCode, reqMap), HttpStatus.OK);
    }
}
