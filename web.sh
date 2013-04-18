nohup bash -c "exec 'vertx' 'runmod' 'cavani.endorfina-hydrogen.web-v1.0' '-conf' 'hydrogen.web/conf/web.conf' &> logs/web.log" &> /dev/null &
echo $! > web.pid
