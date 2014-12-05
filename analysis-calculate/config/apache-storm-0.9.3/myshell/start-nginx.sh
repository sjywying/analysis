#!/bin/bash
/wukong/server/apache-storm-0.9.3/bin/storm jar winksi-calculate-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.winksi.nginx.topology.NginxLogTopology nginx_topology
/wukong/server/apache-storm-0.9.3/bin/storm jar winksi-calculate-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.winksi.calculate.active.ActiveTopology active_topology


