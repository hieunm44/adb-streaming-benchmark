#!/bin/bash

configs=("config_1" "config_2" "config_3")

for config in "${configs[@]}"
do
    echo "Config: $config"
    # Get the command from the command-line arguments
    command="java -cp target/LinearRoad-1.0.jar LinearRoad.LinearRoad Configurations/$config.json"
    # command="ls"
    
    # Run the command in the background
    $command > "command_output.txt" 2>&1 &

    # Get the PID of the last background process
    pid=$!
    
    # If this script is killed, kill the `cp'.
    # trap "kill $pid 2> /dev/null" EXIT

    num_cores=$(nproc)
    avg_cpu=0
    avg_mem=0
    count=0
    # While copy is running...
    while kill -0 $pid 2> /dev/null; do
        # Do stuff
        cpu=$(ps aux | grep $pid | grep -v grep | awk '{print $3}')
        avg_cpu=$(echo "$avg_cpu + $cpu" | bc)
        mem=$(ps aux | grep $pid | grep -v grep | awk '{print $4}')
        avg_mem=$(echo "$avg_mem + $mem" | bc)
        count=$((count + 1))
        # echo "CPU: $avg_cpu" 
        # echo "MEM: $avg_mem" 

        sleep 1
    done

    output=$(cat "command_output.txt")
    echo "$output$"
    throughput=$(echo "$output" | grep -oE "Measured throughput: [0-9.]+" | awk '{print $3}')
    echo "$throughput"
    latency=$(jq --arg key "95" '.[$key]' "metric_latency.json")
    avg_cpu=$(echo "$avg_cpu / $count / $num_cores" | bc)
    avg_mem=$(echo "$avg_mem / $count" | bc)
    echo "CPU: $avg_cpu%"
    echo "MEM: $avg_mem%"
    echo "Flink_FD_$config, $throughput, $latency, $avg_cpu%, $avg_mem%" >> "../../results.csv" 

    # Disable the trap on a normal exit.
    # trap - EXIT

done