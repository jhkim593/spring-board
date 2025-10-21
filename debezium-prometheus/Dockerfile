ARG PROMETHEUS_VERSION
FROM prom/prometheus:${PROMETHEUS_VERSION}

RUN sed -i -e "s/\"localhost:9090\"/\"localhost:9090\",\"article-kafka-connect:8080\"/" /etc/prometheus/prometheus.yml