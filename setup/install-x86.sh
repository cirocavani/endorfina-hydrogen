INSTALL_DIR=setup/

echo 'Java...'

wget http://download.oracle.com/otn-pub/java/jdk/7u21-b11/jdk-7u21-linux-i586.tar.gz -P $INSTALL_DIR \
  --no-cookies --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com"

tar xzf setup/jdk-7u21-linux-i586.tar.gz --directory=$INSTALL_DIR

echo 'MongoDB...'

wget http://fastdl.mongodb.org/linux/mongodb-linux-i686-2.4.3.tgz -P $INSTALL_DIR
tar xzf setup/mongodb-linux-i686-2.4.3.tgz --directory=$INSTALL_DIR

echo 'Vert.x...'

wget http://vert-x.github.io/vertx-downloads/downloads/vert.x-1.3.1.final.tar.gz -P $INSTALL_DIR
tar xzf setup/vert.x-1.3.1.final.tar.gz --directory=$INSTALL_DIR

echo 'Gradle...'

wget http://services.gradle.org/distributions/gradle-1.6-bin.zip -P $INSTALL_DIR
unzip -qn setup/gradle-1.6-bin.zip -d $INSTALL_DIR

echo 'All set!'
