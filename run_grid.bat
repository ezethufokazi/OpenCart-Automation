@echo off
cd C:\Users\ezeth\eclipse-workspace\OpencartV121
docker-compose up -d
timeout /t 10
mvn test
docker-compose down
pause