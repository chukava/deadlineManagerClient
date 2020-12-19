## Requirements:
1. jdk 11
2. gradle

## Running:

1. Clone the project
2. Create jar file using gradle

## Examples and descriptions:

#### find some student by id
 
     • java -jar build/libs/client-0.0.1-SNAPSHOT.jar --args --action=findById --id=5
 
#### show some page from the list of students

    • java -jar build/libs/client-0.0.1-SNAPSHOT.jar --args --action=readPage --page=0 --size=2

#### find students who have deadline from some subject by id of that subject:

    • java -jar build/libs/client-0.0.1-SNAPSHOT.jar --args --action=findBySubjectId --subjectId=11

#### create new student:

    • java -jar build/libs/client-0.0.1-SNAPSHOT.jar --args --action=create --username=testIns1 --password=helloTest --grade=3

#### update some student:

    • java -jar build/libs/client-0.0.1-SNAPSHOT.jar --args --action=update --id=3 --username=testUpd3 --password=helloTest --grade=4

#### delete some student by id:

    • java -jar build/libs/client-0.0.1-SNAPSHOT.jar --args --action=delete --id=9


---
#### !! ALL parametrs are requared for those operations


