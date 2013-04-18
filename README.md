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
	gradle mongoDbStart
	tail -f logs/mongod*.log
	(wait for MongoDB starts)
	gradle mongoDbReplicaSet
	tail -f logs/mongod*.log
	(wait for MongoDB syncs)
	gradle setup
	
	./web.sh
	(http://127.0.0.1:8080/)
	(web.pid, logs/web.log)
	
	./dash.sh
	(http://127.0.0.1:8180/)
	(dash.pid, logs/dash.log)
	
	(... see usage bellow)
	
	kill `cat web.pid`
	kill `cat dash.pid`
	
	gradle mongoDbStop

Next execution:

	(pwd = repo root)
	source setup/env.sh
	
	rm -r mods/cavani*
	gradle deploy
	
	gradle mongoDbStart
	
	./web.sh
	(http://127.0.0.1:8080/)
	(web.pid, logs/web.log)
	
	./dash.sh
	(http://127.0.0.1:8180/)
	(dash.pid, logs/dash.log)
	
	(... see usage bellow)
	
	kill `cat web.pid`
	kill `cat dash.pid`
	
	gradle mongoDbStop


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

Running tests:

	(pwd = repo root)
	gradle clean test
	
	(Reports)
	hydrogen.web/build/reports/tests/index.html
	hydrogen.dashboard/build/reports/tests/index.html
	hydrogen.service/build/reports/tests/index.html
	hydrogen.engine/build/reports/tests/index.html
	hydrogen.persistence/build/reports/tests/index.html


Development
===========

Scripts in folder `setup`:

* `setup/install.sh`: download and unpack required build/runtime tools
* `setup/build.sh`: deploy assembled modules to folder `mods`
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

deploy assembled modules to folder `mods`.

1. Remove all modules from folder `mods`
2. Build and install/copy a patched version of module `vertx.mongo-persistor-1.2.1`
3. Build and install/copy the Hydrogen modules

Outcome:

	(vertx.mongo-persistor-v1.2.1)
	mods/vertx.mongo-persistor-v1.2.1/mod.json
	mods/vertx.mongo-persistor-v1.2.1/org/vertx/mods/*.class
	mods/vertx.mongo-persistor-v1.2.1/lib/mongo-java-driver-2.9.2.jar
	
	(cavani.endorfina-hydrogen.service-v1.0)
	mods/cavani.endorfina-hydrogen.service-v1.0/mod.json
	mods/cavani.endorfina-hydrogen.service-v1.0/<package>/*.class
	mods/cavani.endorfina-hydrogen.service-v1.0/lib/hydrogen.persistence-1.0.jar
	
	(cavani.endorfina-hydrogen.engine-v1.0)
	mods/cavani.endorfina-hydrogen.engine-v1.0/mod.json
	mods/cavani.endorfina-hydrogen.engine-v1.0/<package>/*.class
	mods/cavani.endorfina-hydrogen.engine-v1.0/lib/hydrogen.persistence-1.0.jar
	
	(cavani.endorfina-hydrogen.web-v1.0)
	mods/cavani.endorfina-hydrogen.web-v1.0/mod.json
	mods/cavani.endorfina-hydrogen.web-v1.0/<package>/*.class
	
	(cavani.endorfina-hydrogen.dashboard-v1.0)
	mods/cavani.endorfina-hydrogen.dashboard-v1.0/mod.json
	mods/cavani.endorfina-hydrogen.dashboard-v1.0/<package>/*.class


**MongoDB**

MongoDB control tasks:

* `gradle mongoDbStart` - start 3 instance of MongoDB
* `gradle mongoDbStop` - stop the instances of MongoDB
* `gradle mongoDbReplicaSet` - setup the instances as a cluster, one primary and two secundaries
* `gradle mongoDbCleanUp` - remove database files generated by MongoDB

Files:

* `data/shutdown.js`: used by `mongoDbStop` with `mongo` shell to stop the instances
* `data/replicaset.js`: used by `mongoDbReplicaSet` with `mongo` shell to setup a cluster
* `data/0`: data folder for primary instance
* `data/1`: data folder for secondary instance (used for reading in Service/Web)
* `data/2`: data folder for secondary instance (used for reading in Engine/Dashboard)
* `logs/mongod-*.log`: 3 log files, one for each instance (* = 0,1,2)

Cluster:

* Primary: 127.0.0.1:27017
* Secondary (Service/Web): 127.0.0.1:27018
* Secondary (Engine/Dashboard): 127.0.0.1:27019


**Applications**

Two applications: Tracker (web) and Visualization (dashboard)

(Application Web)

Application Management:

* `web.sh`: start application, background, create `web.pid` e `web.log`
* ``kill `cat web.pid` ``: end application 
* `tail -f logs/web.log`: show application output

Modules:

* `cavani.endorfina-hydrogen.web-v1.0`: HTTP Server (static files) and service routing (URL, parameters parsing, forward requests)
* `cavani.endorfina-hydrogen.service-v1.0`: Tracker service, update primary persistence, read from secondary persistence
* `vertx.mongo-persistor-v1.2.1`: persistence service for MongoDB

Configuration `hydrogen.web/conf/web.conf`:

	{
	  "host": "127.0.0.1",
	  "port": 8080,
	  "webRoot": "hydrogen.web/src/main/webapp/",
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

Static files folder `hydrogen.web/src/main/webapp`:

* `index.html`: page for item selection
* `js/script.js`: server communication, XmlHttpRequest/GET
* `css/style.css`: HTML style
* `img/*`: images


(Application Dashboard)

Application Management:

* `dash.sh`: start application, background, create `dash.pid` e `dash.log`
* ``kill `cat dash.pid` ``: end application 
* `tail -f logs/dash.log`: show application output

Modules:

* `cavani.endorfina-hydrogen.dashboard-v1.0`: HTTP Server (static files) and service routing (URL, parameters parsing, forward requests)
* `cavani.endorfina-hydrogen.engine-v1.0`: Data services, read from secondary persistence
* `vertx.mongo-persistor-v1.2.1`: persistence service for MongoDB

Configuration `hydrogen.dashboard/conf/dash.conf`:

	{
	  "host": "127.0.0.1",
	  "port": 8080,
	  "webRoot": "hydrogen.dashboard/src/main/webapp/",
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

Static files folder `hydrogen.dashboard/src/main/webapp`:

* `index.html`: page for data visualization
* `js/script.js`: server communication, XmlHttpRequest/GET
* `js/Chart.js`: chart library (HTML5/canvas)
* `css/style.css`: HTML style
* `img/*`: images

