package com.example.demo.service;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

  public Map<String, String> getFindMap() {
    Map<String, String> result = new HashMap<>();
    redissonClient.getKeys().getKeysByPattern("k*").iterator().forEachRemaining(x -> {
      // 使用 StringCodec 读取实际值；注意：如果该 key 是旧的 Kryo 序列化数据，会读失败或乱码
      RBucket<String> bucket = redissonClient.getBucket(x, StringCodec.INSTANCE);
      String value = null;
      try {
        value = bucket.get();
      } catch (RuntimeException ex) {
        // 兼容处理：如果旧数据无法按 String 解码，填入占位信息，避免整个查询失败
        value = "[unreadable-with-StringCodec]";
      }
      result.put(x, value);
    });
    return result;
  }
}