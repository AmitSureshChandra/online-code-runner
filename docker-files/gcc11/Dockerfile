FROM alpine:latest

# Install build tools and libraries
RUN apk update && apk add --no-cache gcc g++ musl-dev

WORKDIR /opt/myapp

ENTRYPOINT ["sh", "-c", "gcc -o app main.cpp -lstdc++ && ./app < input.txt"]
