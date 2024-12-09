package com.jobapp.ReviewMS.Review.messaging;

import com.jobapp.ReviewMS.Review.Review;
import com.jobapp.ReviewMS.Review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Review review) {
        ReviewMessage reviewMessage = new ReviewMessage();
        BeanUtils.copyProperties(review, reviewMessage);
        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
    }
}
