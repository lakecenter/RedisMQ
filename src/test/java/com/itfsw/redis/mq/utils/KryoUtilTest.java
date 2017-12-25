/*
 * Copyright (c) 2017.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itfsw.redis.mq.utils;

import com.itfsw.redis.mq.redis.RedisOperations;
import com.itfsw.redis.mq.utils.utils.SpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * ---------------------------------------------------------------------------
 *
 * ---------------------------------------------------------------------------
 * @author: hewei
 * @time:2017/11/10 16:56
 * ---------------------------------------------------------------------------
 */
public class KryoUtilTest extends SpringTest {
    @Autowired
    private RedisOperations redisOperations;
    @Autowired
    private RedisMessageListenerContainer container;

    @Test
    public void test(){

        container.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                System.out.println("KK" + new String(message.getBody()));
            }
        }, new ChannelTopic("c1"));

        container.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                System.out.println("BB" + new String(message.getBody()));
            }
        }, new ChannelTopic("c1"));

        container.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                System.out.println("DG" + new String(message.getBody()));
            }
        }, new PatternTopic("c1*"));

        for (int i = 0 ; i< 100; i++){
            redisOperations.convertAndSend("c1:test:1", "消息" +i);
        }
    }
}