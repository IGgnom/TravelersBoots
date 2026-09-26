package net.iggnom.travelersboots.datagen;

import net.iggnom.travelersboots.item.ModItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new ModRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "Traveler's Boots Recipes";
        }
    }

    @Override
    protected void buildRecipes() {
        shaped(RecipeCategory.MISC, ModItem.TRAVELERS_BOOTS_ITEM.get())
                .pattern("LGL")
                .pattern("FDF")
                .pattern("LIL")
                .define('L', Items.LEATHER)
                .define('G', Items.GREEN_WOOL)
                .define('F', Items.FEATHER)
                .define('D', Items.DIAMOND_BOOTS)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_diamond_boots", this.has(Items.DIAMOND_BOOTS))
                .save(output);
    }
}
