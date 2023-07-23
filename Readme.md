build docker image using following command by going in `./docker-files/` folders
```shell
docker build -t online-compiler-jdk8 .
docker build -t online-compiler-jdk20 .
docker build -t online-compiler-golang12 .
```

create a folder name `temp` to store user temporary code files