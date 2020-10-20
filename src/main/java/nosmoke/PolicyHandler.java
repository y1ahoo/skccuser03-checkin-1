package nosmoke;

import nosmoke.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    CheckInRepository checkInRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverEarned_UpdatePoint(@Payload Earned earned){


        if(earned.isMe()){

            Optional<CheckIn> checkInOptional = checkInRepository.findById(earned.getCheckInId());
            CheckIn checkIn = checkInOptional.get();
            checkIn.setPoint(earned.getPoint());
            checkIn.setSmokingAreaId(checkIn.getSmokingAreaId());
            checkIn.setStatus("EARNED");

            checkInRepository.save(checkIn);
        }
    }

}
