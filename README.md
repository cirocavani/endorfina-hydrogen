Quick Start
===========

Requirement:

	Linux 64 bit, wget, tar, unzip

Optionals:

	curl, ab (ApacheBench)

For Ubuntu (tested with 12.10):

	sudo apt-get install wget tar unzip curl apache2-utils

First Execution:

	(pwd = repo root)
	setup/install.sh
	setup/build.sh
	
	source setup/env.sh
	hydrogen/bin/mongo.sh start
	tail -f hydrogen/logs/mongod*.log
	(wait for MongoDB starts)
	hydrogen/bin/mongo.sh replicaset
	tail -f hydrogen/logs/mongod*.log
	(wait for MongoDB syncs)
	gradle setup
	
	hydrogen/bin/server.sh start
	(http://127.0.0.1:8080/)
	(hydrogen/logs/server.log)
	
	hydrogen/bin/dash.sh start
	(http://127.0.0.1:8180/)
	(hydrogen/logs/dash.log)
	
	(... see usage bellow)
	
	hydrogen/bin/server.sh stop
	hydrogen/bin/dash.sh stop
	
	hydrogen/bin/mongo.sh stop

Next execution:

	(pwd = repo root)
	source setup/env.sh
	
	rm -r mods/cavani*
	gradle deploy

	hydrogen/bin/mongo.sh start
	
	hydrogen/bin/server.sh start
	(http://127.0.0.1:8080/)
	(hydrogen/logs/server.log)
	
	hydrogen/bin/dash.sh start
	(http://127.0.0.1:8180/)
	(hydrogen/logs/dash.log)
	
	(... see usage bellow)
	
	hydrogen/bin/server.sh stop
	hydrogen/bin/dash.sh stop
	
	hydrogen/bin/mongo.sh stop


Usage:

	(Total from User 1)
	curl http://127.0.0.1:8180/total/1
	> 0
	
	(Total from User 1 by Hour)
	curl http://127.0.0.1:8180/total/hour/1
	> { ... }
	
	(Total for User 1 by Item 1)
	curl http://127.0.0.1:8180/total/1/1
	> 0
	
	(Total for User 1 by Item 2)
	curl http://127.0.0.1:8180/total/1/2
	> 0
	
	(Track User 1 taking Item 1)
	curl http://127.0.0.1:8080/track/1/1
	> {"1":0,"2":0}
	
	(Track User 1 taking Item 2)
	curl http://127.0.0.1:8080/track/1/2
	> {"1":100,"2":0}
	
	curl http://127.0.0.1:8180/total/1
	> 2
	
	(Load Test, User 1 taking a lot of Item 1)
	ab -n 1000000 -c 100 http://127.0.0.1:8080/track/1/1
	> ...
	
	(Load Test, User 1 taking a lot of Item 2)
	ab -n 1000000 -c 100 http://127.0.0.1:8080/track/1/2
	> ...
	
	curl http://127.0.0.1:8180/total/1
	> 2000002
	
	Reset database:
	gradle setup


Development
===========

Scripts in folder `setup`:

* `setup/install.sh`: download and unpack required build/runtime tools
* `setup/build.sh`: deploy assembled modules to folder `hydrogen/mods`
* `setup/env.sh`: update PATH for the current shell with tools installed with `install.sh`
* `setup/cleanup.sh`: remove downloads and folders generated with `install.sh`

IMPORTANT: assume that all scripts are run in the repo root (pwd)


**setup/install.sh**

Download and unpack required build/runtime tools.

* Java 7u21
* MongoDB 2.4
* Vert.x 1.3
* Gradle 1.5

Initial Setup (Terminal):

	(pwd = repo root)
	setup/install.sh
	source setup/env.sh
	... Tools installed and PATH updated
	
	java -version
	> java version "1.7.0_21"
	> Java(TM) SE Runtime Environment (build 1.7.0_21-b11)
	> Java HotSpot(TM) 64-Bit Server VM (build 23.21-b01, mixed mode)
	
	mongod --version
	> db version v2.4.2
	> Thu Apr 18 00:48:07.974 git version: 3e52c1a73c08eba7abdcb2c93e08205111976f07
	
	vertx version
	> vert.x-1.3.1.final
	
	gradle --version
	> ------------------------------------------------------------
	> Gradle 1.5
	> ------------------------------------------------------------
	> 
	> Gradle build time: Wednesday, March 27, 2013 1:51:06 PM UTC
	> Groovy: 1.8.6
	> Ant: Apache Ant(TM) version 1.8.4 compiled on May 22 2012
	> Ivy: 2.2.0
	> JVM: 1.7.0_21 (Oracle Corporation 23.21-b01)
	> OS: Linux 3.5.0-27-generic amd64

Next (Terminal):

	(pwd = repo root)
	source setup/env.sh
	... PATH update


**setup/build.sh**

deploy assembled modules to folder `hydrogen/mods`.

1. Remove all modules from folder `hydrogen/mods`
2. Build and install/copy a patched version of module `vertx.mongo-persistor-1.2.1`
3. Build and install/copy the Hydrogen modules

Outcome:

	(vertx.mongo-persistor-v1.2.1)
	mods/vertx.mongo-persistor-v1.2.1/mod.json
	mods/vertx.mongo-persistor-v1.2.1/org/vertx/mods/*.class
	mods/vertx.mongo-persistor-v1.2.1/lib/mongo-java-driver-2.9.2.jar
	
	(cavani.endorfina.hydrogen.persistence-v1.0)
	mods/cavani.endorfina.hydrogen.persistence-v1.0/mod.json
	mods/cavani.endorfina.hydrogen.persistence-v1.0/<package>/*.class

	(cavani.endorfina.hydrogen.network-v1.0)
	mods/cavani.endorfina.hydrogen.network-v1.0/mod.json
	mods/cavani.endorfina.hydrogen.network-v1.0/<package>/*.class

	(cavani.endorfina.hydrogen.service-v1.0)
	mods/cavani.endorfina.hydrogen.service-v1.0/mod.json
	mods/cavani.endorfina.hydrogen.service-v1.0/<package>/*.class
	
	(cavani.endorfina.hydrogen.engine-v1.0)
	mods/cavani.endorfina.hydrogen.engine-v1.0/mod.json
	mods/cavani.endorfina.hydrogen.engine-v1.0/<package>/*.class
	
	(cavani.endorfina.hydrogen.server-v1.0)
	mods/cavani.endorfina.hydrogen.server-v1.0/mod.json
	mods/cavani.endorfina.hydrogen.server-v1.0/<package>/*.class
	
	(cavani.endorfina.hydrogen.dashboard-v1.0)
	mods/cavani.endorfina.hydrogen.dashboard-v1.0/mod.json
	mods/cavani.endorfina.hydrogen.dashboard-v1.0/<package>/*.class


**MongoDB**

MongoDB control tasks:

* `hydrogen/bin/mongo.sh start` - start 3 instance of MongoDB
* `hydrogen/bin/mongo.sh stop` - stop the instances of MongoDB
* `hydrogen/bin/mongo.sh replicaset` - setup the instances as a cluster, one primary and two secondaries
* `hydrogen/bin/mongo.sh cleanup` - remove database files generated by MongoDB

Files:

* `hydrogen/data/shutdown.js`: used with `mongo` shell to stop the instances
* `hydrogen/data/replicaset.js`: used with `mongo` shell to setup a cluster
* `hydrogen/data/0`: data folder for primary instance
* `hydrogen/data/1`: data folder for secondary instance (used for reading in Service/Web)
* `hydrogen/data/2`: data folder for secondary instance (used for reading in Engine/Dashboard)
* `hydrogen/logs/mongod-*.log`: 3 log files, one for each instance (* = 0,1,2)

Cluster:

* Primary: 127.0.0.1:27017
* Secondary (Service/Web): 127.0.0.1:27018
* Secondary (Engine/Dashboard): 127.0.0.1:27019


**Applications**

Two applications: Web API (server) and Visualization (dashboard)

(Application Server)

Application Management:

* `hydrogen/bin/server.sh`: start/stop application, background
* `tail -f hydrogen/logs/server.log`: show application output

Modules:

* `cavani.endorfina.hydrogen.server-v1.0`: HTTP Server (static files) and service routing (URL, parameters parsing, forward requests)
* `cavani.endorfina.hydrogen.service-v1.0`: Tracker service, update primary persistence, read from secondary persistence
* `vertx.mongo-persistor-v1.2.1`: persistence service for MongoDB

Configuration `hydrogen/conf/server.conf`:

	{
	  "host": "127.0.0.1",
	  "port": 8080,
	  "webRoot": "web/server",
	  "rootFile": "index.html",
	  "serviceConf": {
	    "persistenceType": "MongoDB",
	    "persistenceStorageConf": {
	      "mongoDbModule": "vertx.mongo-persistor-v1.2.1",
	      "mongoDbInstances": 10,
	      "mongoDbAddress": "persistence.storage.mongodb",
	      "mongoDbHost": "127.0.0.1",
	      "mongoDbPort": 27017,
	      "mongoDbDatabase": "default_db",
	      "mongoDbSecondary": false,
	      "mongoDbAuth": false,
	      "mongoDbUsername": "system",
	      "mongoDbPassword": "secret"
	    },
	    "persistenceSourceConf": {
	      "mongoDbModule": "vertx.mongo-persistor-v1.2.1",
	      "mongoDbInstances": 10,
	      "mongoDbAddress": "persistence.source.mongodb",
	      "mongoDbHost": "127.0.0.1",
	      "mongoDbPort": 27018,
	      "mongoDbDatabase": "default_db",
	      "mongoDbSecondary": true,
	      "mongoDbAuth": false,
	      "mongoDbUsername": "system",
	      "mongoDbPassword": "secret"
	    }
	  }
	}

Static files folder `hydrogen/web/server`:

* `index.html`: page for item selection
* `js/script.js`: server communication, XmlHttpRequest/GET
* `css/style.css`: HTML style
* `img/*`: images


(Application Dashboard)

Application Management:

* `hydrogen/bin/dash.sh`: start/stop application, background
* `tail -f hydrogen/logs/dash.log`: show application output

Modules:

* `cavani.endorfina.hydrogen.dashboard-v1.0`: HTTP Server (static files) and service routing (URL, parameters parsing, forward requests)
* `cavani.endorfina.hydrogen.engine-v1.0`: Data services, read from secondary persistence
* `vertx.mongo-persistor-v1.2.1`: persistence service for MongoDB

Configuration `hydrogen/conf/dash.conf`:

	{
	  "host": "127.0.0.1",
	  "port": 8080,
	  "webRoot": "web/dash/",
	  "rootFile": "index.html",
	  "engineConf": {
	    "persistenceType": "MongoDB",
	    "persistenceSourceConf": {
	      "mongoDbModule": "vertx.mongo-persistor-v1.2.1",
	      "mongoDbInstances": 10,
	      "mongoDbAddress": "persistence.source.mongodb",
	      "mongoDbHost": "127.0.0.1",
	      "mongoDbPort": 27019,
	      "mongoDbDatabase": "default_db",
	      "mongoDbSecondary": true,
	      "mongoDbAuth": false,
	      "mongoDbUsername": "system",
	      "mongoDbPassword": "secret"
	    }
	  }
	}

Static files folder `hydrogen/web/dash`:

* `index.html`: page for data visualization
* `js/script.js`: server communication, XmlHttpRequest/GET
* `js/Chart.js`: chart library (HTML5/canvas)
* `css/style.css`: HTML style
* `img/*`: images


**Tests**

Running:

	(pwd = repo root)
	gradle clean test
	
	(Reports)
	hydrogen.server/build/reports/tests/index.html
	hydrogen.dashboard/build/reports/tests/index.html
	hydrogen.service/build/reports/tests/index.html
	hydrogen.engine/build/reports/tests/index.html
	hydrogen.network/build/reports/tests/index.html
	hydrogen.persistence/build/reports/tests/index.html
	
