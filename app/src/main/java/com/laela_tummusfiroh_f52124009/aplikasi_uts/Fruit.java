package com.laela_tummusfiroh_f52124009.aplikasi_uts;

public class Fruit {
    private final String name;
    private final String latinName;
    private final String nutrition;
    private final String benefit;
    private final int imageResId;

    public Fruit(String name, String latinName, String nutrition, String benefit, int imageResId) {
        this.name = name;
        this.latinName = latinName;
        this.nutrition = nutrition;
        this.benefit = benefit;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getLatinName() {
        return latinName;
    }

    public String getNutrition() {
        return nutrition;
    }

    public String getBenefit() {
        return benefit;
    }

    public int getImageResId() {
        return imageResId;
    }
}
