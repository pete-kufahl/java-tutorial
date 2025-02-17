package com.prk.animal;

import java.util.ArrayList;
import java.util.List;

public class DemoAnimal {

    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("Pancho"));
        dogs.add(new Dog("Rat"));

        //  incompatible types: List<Dog> cannot be converted to List<Animal>
        // List<Animal> animals = dogs;
        // otherwise, we could legally add a cat to the list of dogs
        // animals.add(new Cat("Whiskers"));
    }
}
