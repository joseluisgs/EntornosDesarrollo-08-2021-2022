# Inyección de Dependencias (DI)

La inyección de dependencias es una técnica de desarrollo que permite a los desarrolladores de software, a través de la inyección de dependencias, obtener una dependencia de una clase en una clase que no tiene acceso a ella.

El Principio de inyección de dependencia no es más que poder pasar (inyectar) las dependencias cuando sea necesario en lugar de inicializar las dependencias dentro de la clase receptora y con ello poder desacoplar la construcción de sus clases de la construcción de las dependencias de sus clases.

Es decir, aplicamos una composición entre clases, con el objetivo que cada clase tenga sus responsabilidades bien definidas y acotadas. Es decir, si una clase A, necesita alguna funcionalidad de B, nosotros al crear A, debemos "inyectarle" B. De esta manera A, puede usar la funcionalidad de B. 

De esta manera, podemos cambiar B, por C, siempre y cuando mantengan el contrato que permite ser usado por A. Ya no es la clase A la responsable de definir sus dependencias sino que lo es el programa o clase superior que le inyecta la dependencia que en ese momento necesite según los requerimientos.

## Código Acoplado
Esto es lo que **no deberíamos hacer**
```java
class ClassA {

  ClassB classB = new ClassB();

  int tenPercent() {
    return classB.calculate() * 0.1d;
  }
}
```
```java
class Main {
  public static void main(String... args) {
    ClassA classA = new ClassA();

    System.out.println("Ten Percent: " + classA.tenPercent());
  }
}
```

## Inyección por Setter
No recomendado. Con este enfoque, eliminamos la palabra clave new ClassB de nuestra ClassA. Por lo tanto, alejamos la responsabilidad de la creación de ClassB deClassA.

```java
class ClassA {

  ClassB classB;

  /* Setter Injection */
  void setClassB(ClassB injected) {
    classB = injected;
  }

  int tenPercent() {
    return classB.calculate() * 0.1d;
  }
}
```
```java
class Main {
  public static void main(String... args) {
    ClassA classA = new ClassA();
    ClassB classB = new ClassB();

    classA.setClassB(classB);

    System.out.println("Ten Percent: " + classA.tenPercent());
  }
}
```

Pero hay un problema significativo con el enfoque de Inyección con Setters:

Estamos ocultando la dependencia ClassB enClassA porque al leer la firma del constructor, no podemos identificar sus dependencias de inmediato. El siguiente código provoca una NullPointerException en tiempo de ejecución:
```java
class Main {
  public static void main(String... args) {
    ClassA classA = new ClassA();

    System.out.println("Ten Percent: " + classA.tenPercent()); // NullPointerException here
  }
}
```

## Inyección con Constructor
ClassA todavía tiene una fuerte dependencia de ClassB pero ahora se puede inyectar desde afuera usando el constructor:

```java
class ClassA {

  ClassB classB;

  /* Constructor Injection */
  ClassA(ClassB injected) {
    classB = injected;
  }

  int tenPercent() {
    return classB.calculate() * 0.1d;
  }
}
```
```java
class Main {
  public static void main(String... args) {
    /* Notice that we are creating ClassB fisrt */
    ClassB classB = new ClassB();

    /* Constructor Injection */
    ClassA classA = new ClassA(classB);

    System.out.println("Ten Percent: " + classA.tenPercent());
  }
}
```

La funcionalidad permanece intacta en comparación con el enfoque de Inyección Setter. Eliminamos la inicialización nueva de la ClaseA.

Todavía podemos inyectar una subclase especializada de ClassB a ClassA.

Ahora el compilador nos pedirá las dependencias que necesitamos en tiempo de compilación.

