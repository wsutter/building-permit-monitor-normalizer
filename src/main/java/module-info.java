module ch.studior2.buildingpermitmonitor.normalizer {
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires spring.kafka;
  requires ch.studior2.buildingpermitmonitor.contracts;

  opens ch.studior2.buildingpermitmonitor.normalizer to
      spring.core,
      spring.beans,
      spring.context;
  opens ch.studior2.buildingpermitmonitor.normalizer.mapper to
      spring.core,
      spring.beans,
      spring.context;
  opens ch.studior2.buildingpermitmonitor.normalizer.service to
      spring.core,
      spring.beans,
      spring.context;
}
