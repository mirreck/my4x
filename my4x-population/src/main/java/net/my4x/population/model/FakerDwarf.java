package net.my4x.population.model;

import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;

public class FakerDwarf extends Faker {
    public FakerDwarf() {
        super(new Locale("dwarf", "fr"));
    }

    public FakerDwarf(Random random) {
        super(new Locale("dwarf", "fr"), random);
    }

}
