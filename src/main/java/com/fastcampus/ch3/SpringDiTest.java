//package com.fastcampus.ch3;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//class Engine{} // <bean id="engine" class="com.fastcompus.ch3.Engine"/>
//@Component class SuperEngine extends Engine{}
//@Component class TuroboEngine extends Engine{}
//@Component class Door{}
//@Component
//class Car{
//    @Value("red") String color;
//    @Value("100") int oil;
//    @Autowired
//    @Qualifier("superEngine")
//    Engine engine;  //byType
//    @Autowired Door[] doors;
//
//    public void setDoors(Door[] doors) {
//        this.doors = doors;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public void setOil(int oil) {
//        this.oil = oil;
//    }
//
//    public void setEngine(Engine engine) {
//        this.engine = engine;
//    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "color='" + color + '\'' +
//                ", oil=" + oil +
//                ", engine=" + engine +
//                ", doors=" + Arrays.toString(doors) +
//                '}';
//    }
//}
//public class SpringDiTest {
//    public static void main(String[] args) {
//        ApplicationContext ac = new GenericXmlApplicationContext("config1.xml");
//       // Car car = (Car) ac.getBean("car"); // byName, 아래와 같은 문장
//        Car car = ac.getBean("car",Car.class); // byName
//        Car car2 = (Car) ac.getBean(Car.class);
//
//        //Engine engine = (Engine) ac.getBean("engine");
//        Door door = (Door) ac.getBean("door");
//
//        Engine engine = (Engine) ac.getBean("superEngine");
//
////        car.setColor("red");
////        car.setOil(100);
////        car.setEngine(engine);
////        car.setDoors(new Door[]{ac.getBean("door",Door.class),ac.getBean("door",Door.class)});
//        System.out.println("car= "+car);
//    }
//}
