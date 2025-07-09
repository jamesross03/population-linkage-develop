#!/bin/bash

# Forward ports to umea-neo4j container
socat TCP-LISTEN:7474,fork TCP:umea-neo4j:7474 &
socat TCP-LISTEN:7687,fork TCP:umea-neo4j:7687 &