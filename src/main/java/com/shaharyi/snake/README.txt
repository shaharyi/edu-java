download zip and extract from:
https://gluonhq.com/products/javafx/


javac --module-path /usr/share/openjfx/lib       --add-modules javafx.controls,javafx.graphics       com/shaharyi/snake/*.java   com/shaharyi/node/Node.java

java --module-path /usr/share/openjfx/lib      --add-modules javafx.controls,javafx.graphics      com.shaharyi.snake.SnakeFX


Exercises:

1. implement removeTail() themselves

2. detect self-collision

3. make food a linked list of Point and detect eating food

4. add another Snake controlled by keys WASD
  4.1 detect snakes collision and kill culprit alone.

