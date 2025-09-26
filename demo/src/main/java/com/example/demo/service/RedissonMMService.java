package com.example.demo.service;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedissonMMService {

  private final RedissonClient redissonClient;

  public RedissonMMService(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  public void setValue(String key, String value) {
    // 显式使用 StringCodec，确保以纯字符串形式读写，避免 Kryo 反序列化问题
    RBucket<String> bucket = redissonClient.getBucket(key, StringCodec.INSTANCE);
    // 使用同步 set，避免 setAsync 后立即阻塞等待的无谓复杂度
    bucket.set(value);
  }

  public String getValue(String key) {
    // 与写入保持同一 codec
    RBucket<String> bucket = redissonClient.getBucket(key, StringCodec.INSTANCE);
    try {
      // 简化为同步 get
      return bucket.get();
    } catch (RuntimeException e) {
      // 统一向上抛出，便于 Controller 记录日志
      throw e;
    } finally {
    }
  }

}