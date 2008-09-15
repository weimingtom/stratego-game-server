mkdir -p ../target/data/Stratego/dsdb
export MVN_REPO=/home/daniel/Tools/mvn-repo
export JAVA_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"
export CLASSPATH=../target/classes
export CLASSPATH=${CLASSPATH}:${MVN_REPO}/dnl/games/stratego-game-core/1.0/stratego-game-core-1.0.jar
export CLASSPATH=${CLASSPATH}:${MVN_REPO}/commons-lang/commons-lang/2.3/commons-lang-2.3.jar

/home/daniel/Aps/Games/DarkStar/sgs-0.9.6-r4193/sgs.sh ${CLASSPATH} ./SgsStratego.properties