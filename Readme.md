
## Architecture diagram 
![image](https://github.com/AmitSureshChandra/online-compiler/assets/47358181/28308234-49d1-48ca-8f5f-bc37cd0de31f)


## Sample of running jdk8 code

![image](https://github.com/AmitSureshChandra/online-compiler/assets/47358181/e4397f33-517c-4cb9-8bd9-b020a48b6962)
![image](https://github.com/AmitSureshChandra/online-compiler/assets/47358181/f6425311-bc6b-485b-ba65-1675490cab17)

## Sample of running jdk8 code
![image](https://github.com/AmitSureshChandra/online-compiler/assets/47358181/f3a3fb2d-612a-48fb-a4f2-369a587882e8)

## Sample of running golang code
![image](https://github.com/AmitSureshChandra/online-compiler/assets/47358181/386672e1-e0b1-4704-9f11-86f0f80d751f)


# Instructions to run locally

build docker image using following command by going in `./docker-files/` folders
```shell
docker build -t online-compiler-jdk8 .
docker build -t online-compiler-jdk20 .
docker build -t online-compiler-golang12 .
```

create a folder name `temp` to store user temporary code files
