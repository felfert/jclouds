#set -x
echo "mvn --log-file ../build.log -B -fae -Dstyle.color=always clean verify checkstyle:checkstyle"
mvn --log-file ../build.log -B -fae -Dstyle.color=always clean verify checkstyle:checkstyle
RES=$?
if [ $RES -ne 0 ] ; then
    ansi2html < ../build.log > ../build.log.html
    echo loghtml=$(realpath ../build.log.html) >> $1
fi
cat ../build.log
exit $RES
