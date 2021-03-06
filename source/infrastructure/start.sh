#!/bin/bash
sudo chown -R 472:472 "$(dirname $(readlink -f $0))/grafana/"

docker-compose -f "$(dirname $(readlink -f $0))/docker-compose.yaml" up -d \
  --scale helpdesk-backend=3 \
  --scale email-client-generic=3 \
  --scale email-client-travel=2 \
  --scale kafka=3

sed -n '/helpdesk/!p' /etc/hosts | sudo tee /etc/hosts
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}} helpdesk' infrastructure_helpdesk_1 | sudo tee -a /etc/hosts
