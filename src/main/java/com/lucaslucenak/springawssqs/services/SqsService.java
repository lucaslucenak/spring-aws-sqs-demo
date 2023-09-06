package com.lucaslucenak.springawssqs.services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqsService {

    @Autowired
    private AmazonSQS amazonSQS;

    @Value("${aws.sqs.queueName}")
    private String queueName;

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    public void sendMessage(String message) {
        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message);
        amazonSQS.sendMessage(request);
    }

    public String receiveMessages(Integer maxNumberOfMessages) {
        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(queueUrl)
                .withMaxNumberOfMessages(maxNumberOfMessages);
        List<Message> messages = amazonSQS.receiveMessage(request).getMessages();

        StringBuilder response = new StringBuilder();
        for (Message message : messages) {
            response.append("Received message: ").append(message.getBody()).append("\n");

            // Remove the message from the queue
            amazonSQS.deleteMessage(queueUrl, message.getReceiptHandle());
//            amazonSQS.deleteMessage(amazonSQS.getQueueUrl(queueName).getQueueUrl(), message.getReceiptHandle());
        }

        return response.toString();
    }
}
