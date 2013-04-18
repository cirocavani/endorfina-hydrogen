nohup bash -c "exec 'vertx' 'runmod' 'cavani.endorfina.hydrogen.server-v1.0' '-conf' 'hydrogen.server/conf/server.conf' &> logs/server.log" &> /dev/null &
echo $! > server.pid
