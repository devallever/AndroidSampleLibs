package app.allever.android.sample.function.im.function;

import androidx.annotation.NonNull;

import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.ios.category.ActivitiesCategory;
import com.vanniktech.emoji.ios.category.AnimalsAndNatureCategory;
import com.vanniktech.emoji.ios.category.FoodAndDrinkCategory;
import com.vanniktech.emoji.ios.category.ObjectsCategory;
import com.vanniktech.emoji.ios.category.SmileysAndPeopleCategory;
import com.vanniktech.emoji.ios.category.SymbolsCategory;
import com.vanniktech.emoji.ios.category.TravelAndPlacesCategory;

public class MyEmojiProvider implements EmojiProvider {

    @NonNull
    @Override
    public EmojiCategory[] getCategories() {
        return new EmojiCategory[]{
                new SmileysAndPeopleCategory(),
                new AnimalsAndNatureCategory(),
                new FoodAndDrinkCategory(),
                new ActivitiesCategory(),
                new TravelAndPlacesCategory(),
                new ObjectsCategory(),
                new SymbolsCategory()
        };
    }
}
