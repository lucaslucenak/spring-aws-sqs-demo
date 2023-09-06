package com.lucaslucenak.springawssqs.services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SqsService {

    @Autowired
    private AmazonSQS amazonSQS;

    @Value("${aws.sqs.queueName}")
    private String queueName;

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    public SendMessageResult sendMessage(Object message) {
        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message.toString());
        return amazonSQS.sendMessage(request);
    }

    public List<String> receiveMessages(Integer maxNumberOfMessages) {
        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(queueUrl)
                .withMaxNumberOfMessages(maxNumberOfMessages);
        List<Message> messages = amazonSQS.receiveMessage(request).getMessages();

        List<String> response = new ArrayList<>();
        for (Message message : messages) {
            StringBuilder stringBuilder = new StringBuilder();
            response.add(message.getBody());
            // Remove the message from the queue
            amazonSQS.deleteMessage(queueUrl, message.getReceiptHandle());
        }

        return response;
    }
}
