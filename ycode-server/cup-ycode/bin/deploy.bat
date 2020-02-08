cd ../ycode-dependencies
call mvn deploy

cd ../commons/ycode-commons
call mvn clean deploy

cd ../ycode-commons-domain
call mvn clean deploy

cd ../ycode-commons-mapper
call mvn clean deploy

cd ../ycode-commons-service
call mvn clean deploy

cd ../ycode-commons-web
call mvn clean deploy

cd ../../api/ycode-api-datetime
call mvn clean deploy

cd ../ycode-api-fastdfs
call mvn clean deploy

cd ../ycode-api-redis
call mvn clean deploy

cd ../ycode-api-slideshow-admin
call mvn clean deploy

cd ../ycode-api-slideshow-user
call mvn clean deploy

cd ../ycode-api-sso-admin
call mvn clean deploy

cd ../ycode-api-user-regist
call mvn clean deploy

cd ../../