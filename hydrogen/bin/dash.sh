#! /bin/sh
 
HYDROGEN_HOME=hydrogen

CMD=`basename "$0"`

PROG_NAME=Dashboard
MOD_NAME=cavani.endorfina.hydrogen.dashboard-v1.0
LOG_FILE=logs/dash.log
CONF_FILE=conf/dash.conf
PID_FILE=run/dash.pid

start() {
      echo -n "Starting Hydrogen $PROG_NAME"
      cd $HYDROGEN_HOME
      nohup vertx runmod $MOD_NAME -conf $CONF_FILE > $LOG_FILE 2>&1 &
      echo $! > $PID_FILE
      echo "."
}
 
stop() {
      echo -n "Stopping Hydrogen $PROG_NAME"
      cd $HYDROGEN_HOME
      kill `cat $PID_FILE`
      rm $PID_FILE
      echo "."
}
 
case "$1" in
      start)
            start
            ;;
      stop)
            stop
            ;;
      *)
            echo "Usage: $CMD {start|stop}"
            exit 1
esac
 
exit 0
