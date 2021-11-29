# FileConverterService
## О программе
Данная программа конвертирует xml файлы в json и обратно, изменяя струткуру файла.
## Использование
На вход в программу подаются пути исходного файла и нового. Также необходимо создать jar файл.
## Сборка
1. Maven
2. IntelliJ Idea
## Запуск
### Создание jar файла с помощью Maven
#### Установка Maven
Если у Вас не установлен Maven, то
1) Скачайте архив по ссылке https://maven.apache.org/download.cgi
2) Добавьте Maven в переменные среды
  ```
   set PATH="C:\Program Files\apache-maven-3.8.4\bin";%PATH%
   ```
Не забудьте указать свой номер версии Maven.
#### Создание JAR файла
1) Перейдите в директорию FileConverterService и введите в командную строку
   ```
   mvn package 
   ```
   Эта команда создаст jar файл по пути target/FileConverterService-1.0-SNAPSHOT-jar-with-dependencies.jar
### Создание jar файла с помощью Intellij Idea
1. Откройте проект через Intellij Idea
2. Перейдите во вкладку File => Project Structure => Artifacts
3. Нажимаем на "+" => JAR => From modules with dependencies
4. В поле Main class выбираем Main
5. Далее ставим галочку на Include in project build => Ok
6. Производим сборку проекта (Ctrl + F9)
### Запуск jar файла
```
java -jar ttarget/FileConverterService-1.0-SNAPSHOT-jar-with-dependencies.jar <путь к исходному файлу> <путь к новому файлу>
```
