

curl --location 'http://0.0.0.0:8090/api/v1/run' \
--header 'Content-Type: application/json' \
--data '{
    "code" : "public class Solution {public static void main(String[] args) {System.out.println(\"Hello World3\");}}",
    "compiler" : "jdk8",
    "input": ""
}'