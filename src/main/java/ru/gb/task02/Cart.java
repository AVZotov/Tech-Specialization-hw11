package ru.gb.task02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Корзина
 * @param <T> Еда
 */
public class Cart<T extends Food> {

    //region Поля

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final Map<String, Boolean> nutrientRatio;
    private final UMarket market;
    private final Class<T> clazz;

    //endregion

    //region Конструкторы

    /**
     * Создание нового экземпляра корзины
     * @param market принадлежность к магазину
     */
    public Cart(Class<T> clazz, UMarket market)
    {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
        this.nutrientRatio = new HashMap<>(Map.of(
                "proteins", false,
                "fats", false,
                "carbohydrates", false
        ));
    }

    //endregion

    /**
     * Балансировка корзины
     */
    public void cartBalancing(){

        updateNutrientRatio();

        if (isBasketBalanced())
        {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return;
        }

        updateFoodStuff();

        if (isBasketBalanced())
            System.out.println("Корзина сбалансирована по БЖУ.");
        else
            System.out.println("Невозможно сбалансировать корзину по БЖУ.");
    }

    private boolean isBasketBalanced(){
        return nutrientRatio.values().stream().allMatch(nutrient -> nutrient);
    }

    private void updateNutrientRatio(){
        foodstuffs.forEach(food -> {
            if (!nutrientRatio.get("proteins") && food.getProteins())
                nutrientRatio.put("proteins", true);
            else
            if (!nutrientRatio.get("fats") && food.getFats())
                nutrientRatio.put("fats", true);
            else
            if (!nutrientRatio.get("carbohydrates") && food.getCarbohydrates())
                nutrientRatio.put("carbohydrates", true);
        });
    }

    private void updateFoodStuff(){

        market.getThings(clazz).forEach(product -> {
            if (!nutrientRatio.get("proteins") && product.getProteins()){
                nutrientRatio.put("proteins", true);
                foodstuffs.add(product);
            }
            else if (!nutrientRatio.get("fats") && product.getFats())
            {
                nutrientRatio.put("fats", true);
                foodstuffs.add(product);
            }
            else if (!nutrientRatio.get("carbohydrates") && product.getCarbohydrates())
            {
                nutrientRatio.put("fats", true);
                foodstuffs.add(product);
            }
        });
    }

    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs()
    {
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                index.getAndIncrement(), food.getName(),
                food.getProteins() ? "Да" : "Нет",
                food.getFats() ? "Да" : "Нет",
                food.getCarbohydrates() ? "Да" : "Нет"));

    }


}
