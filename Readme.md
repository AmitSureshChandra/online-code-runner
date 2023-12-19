
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
cd docker-files/jdk8 && docker build -t online-compiler-jdk8 .
cd docker-files/jdk20 && docker build -t online-compiler-jdk20 .
cd docker-files/golang12 && docker build -t online-compiler-golang12 .
cd docker-files/python3 && docker build -t online-compiler-python3 .
cd docker-files/node20 && docker build -t online-compiler-node20 .
cd docker-files/gcc11 && docker build -t online-compiler-gcc11 .
```

create a folder name `temp` to store user temporary code files
```shell
mkdir temp
```

# to see api docs 
![image](https://github.com/AmitSureshChandra/online-compiler/assets/47358181/2a995616-fd56-4228-93b3-62026f609b19)

# added `run.sh` to execute above scripts directly
```shell
chmod +x run.sh
./run.sh
```