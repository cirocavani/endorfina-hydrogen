#! /bin/sh
 
HYDROGEN_HOME=hydrogen

CMD=`basename "$0"`

PROG_NAME=MongoDB
LOG_DIR=logs
DATA_DIR=data

start() {
      echo -n "Starting Hydrogen $PROG_NAME"
      cd $HYDROGEN_HOME
      mongodb0_start
      mongodb1_start
      mongodb2_start
      echo "."
}
 
stop() {
      echo -n "Stopping Hydrogen $PROG_NAME"
      cd $HYDROGEN_HOME
      mongodb2_stop
      mongodb1_stop
      mongodb0_stop
      echo "."
}


mongodb0_start() {
      mongod \
      --fork \
      --logpath $LOG_DIR/mongod-0.log \
      --logappend \
      --bind_ip 127.0.0.1 \
      --port 27017 \
      --replSet hydrogen \
      --dbpath $DATA_DIR/0 \
      --smallfiles \
      --oplogSize 10
}

mongodb0_stop() {
      mongo 127.0.0.1:27017/admin $DATA_DIR/shutdown.js
}

mongodb1_start() {
      mongod \
      --fork \
      --logpath $LOG_DIR/mongod-1.log \
      --logappend \
      --bind_ip 127.0.0.1 \
      --port 27018 \
      --replSet hydrogen \
      --dbpath $DATA_DIR/1 \
      --smallfiles \
      --oplogSize 10
}

mongodb1_stop() {
      mongo 127.0.0.1:27018/admin $DATA_DIR/shutdown.js
}

mongodb2_start() {
      mongod \
      --fork \
      --logpath $LOG_DIR/mongod-2.log \
      --logappend \
      --bind_ip 127.0.0.1 \
      --port 27019 \
      --replSet hydrogen \
      --dbpath $DATA_DIR/2 \
      --smallfiles \
      --oplogSize 10
}

mongodb2_stop() {
      mongo 127.0.0.1:27019/admin $DATA_DIR/shutdown.js
}

replicaset() {
      echo -n "ReplicaSet Hydrogen $PROG_NAME"
      cd $HYDROGEN_HOME
      mongo 127.0.0.1:27017/admin $DATA_DIR/replicaset.js
}

setup() {
      echo -n "Setup Hydrogen $PROG_NAME"
      cd $HYDROGEN_HOME
      mongo 127.0.0.1:27017/admin $DATA_DIR/data.js
}

cleanup() {
      echo -n "CleanUp Hydrogen $PROG_NAME"
      cd $HYDROGEN_HOME
      rm -r $DATA_DIR/{0,1,2}/*
}
 
case "$1" in
      start)
            start
            ;;
      stop)
            stop
            ;;
      replicaset)
            replicaset
            ;;
      setup)
            setup
            ;;
      cleanup)
            cleanup
            ;;
      *)
            echo "Usage: $CMD {start|stop|replicaset|setup|cleanup}"
            exit 1
esac
 
exit 0
