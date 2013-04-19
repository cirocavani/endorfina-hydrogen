. setup/env.sh

rm -r hydrogen/mods/*

gradle --build-file mod-mongo-persistor-1.2.1/build.gradle copyMod
cp -r mod-mongo-persistor-1.2.1/build/mod/vertx.mongo-persistor-v1.2.1 hydrogen/mods/

gradle deploy
