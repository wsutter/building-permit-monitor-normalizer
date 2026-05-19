package ch.studior2.buildingpermitmonitor.normalizer.service;

import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitNormalizedEvent;
import ch.studior2.buildingpermitmonitor.contracts.event.BuildingPermitRawEvent;
import ch.studior2.buildingpermitmonitor.contracts.topic.KafkaTopics;
import ch.studior2.buildingpermitmonitor.normalizer.mapper.BuildingPermitRawEventMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BuildingPermitNormalizer {

  private final BuildingPermitRawEventMapper mapper;
  private final KafkaTemplate<String, BuildingPermitNormalizedEvent> kafkaTemplate;

  public BuildingPermitNormalizer(
      BuildingPermitRawEventMapper mapper,
      KafkaTemplate<String, BuildingPermitNormalizedEvent> kafkaTemplate) {
    this.mapper = mapper;
    this.kafkaTemplate = kafkaTemplate;
  }

  @KafkaListener(topics = KafkaTopics.RAW, groupId = "normalizer")
  public void normalize(BuildingPermitRawEvent rawEvent) {
    BuildingPermitNormalizedEvent normalizedEvent = mapper.map(rawEvent);
    kafkaTemplate.send(KafkaTopics.NORMALIZED, normalizedEvent.permitId(), normalizedEvent);
  }
}
