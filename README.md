# LEVERX  ONLINE SHOP

## TO RUN PROGRAM ON YOUR MACHINE, FOLLOW INSTRUCTION

### 1. Clone the repository

~~~bash
git clone https://github.com/shadowoflobster/leverx-online-shop/tree/dev
cd leverx-online-shop
~~~

### 2. Build with javac 
~~~bash
javac -d out src/main/java/org/example/*.java
~~~

### 3. Run program
~~~bash
java -cp out org.example.Main
~~~

## Usage

- Configure properties like customer/worker number, stock and customer balance inside Main.java.
- Customers will place orders randomly.
- Workers will process orders concurrently.
- After all orders are processed, analytics will print.