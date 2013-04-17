nohup bash -c "exec 'vertx' 'runmod' 'cavani.endorfina-hydrogen.dashboard-v1.0' '-conf' 'hydrogen.dashboard/conf/dash.conf' &> dash.log" &> /dev/null &
echo $! > dash.pid
