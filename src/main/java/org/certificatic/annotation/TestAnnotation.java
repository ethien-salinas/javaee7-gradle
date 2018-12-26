package org.certificatic.annotation;

@ValuableAnnotation("attValue")
public class TestAnnotation {

    @MyAnnotation(text = "", text3 = "")
    public void testMethod(){
    }

    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println(TestAnnotation.class.isAnnotationPresent(ValuableAnnotation.class));
        System.out.println(new TestAnnotation().getClass().isAnnotationPresent(ValuableAnnotation.class));
        System.out.println(new TestAnnotation().getClass().getMethod("testMethod").isAnnotationPresent(MyAnnotation.class));
    }

}
