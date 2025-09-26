package com.example.demo.controller;

import com.example.demo.service.RedissonMMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class RedissonController {
  private final static Logger logger = LoggerFactory.getLogger(RedissonController.class);

  @Autowired
  private RedissonMMService redissonMMService;

  @PostMapping("/set")
  public void setValue(@RequestParam String key, @RequestParam String value) {
    redissonMMService.setValue(key, value);
  }

  @GetMapping("/get")
  public String getValue(@RequestParam String key) {
    try {
      return redissonMMService.getValue(key);
    } catch (Exception e) {
      logger.error("getFindMap error", e);
      return null;
    }
  }

}
