
echo 'Java...'

wget http://download.oracle.com/otn-pub/java/jdk/7u21-b11/jdk-7u21-linux-x64.tar.gz -P setup/ \
  --no-cookies --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com"

tar xzf setup/jdk-7u21-linux-x64.tar.gz --directory=setup/

echo 'MongoDB...'

wget http://fastdl.mongodb.org/linux/mongodb-linux-x86_64-2.4.2.tgz -P setup/
tar xzf setup/mongodb-linux-x86_64-2.4.2.tgz --directory=setup/

echo 'Vert.x...'

wget http://vertx.io/downloads/vert.x-1.3.1.final.tar.gz -P setup/
tar xzf setup/vert.x-1.3.1.final.tar.gz --directory=setup/

echo 'Gradle...'

wget http://services.gradle.org/distributions/gradle-1.5-bin.zip -P setup/
unzip -qn setup/gradle-1.5-bin.zip -d setup/

echo 'All set!'
