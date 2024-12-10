package turniplabs.examplemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.examplemod.item.ItemCarrotStick;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class ExampleMod implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Item bamboo;
	public static Item potato;
	public static Item carrot;
	public static Item potatoBaked;
	public static Item carrotStick;
	public static Item rgbChestplate;
    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
    }

	@Override
	public void beforeGameStart() {
		// Vanilla BTA Items range from 16384 to about 16600 with music discs occupying the range 18384 to 18395,
		// and items ids can only exist within the 16384 to 32768 range
		// you need to make sure to pick ids that aren't being used by the vanilla game
		// or other mods, a good way to deal with the potential of conflicting ids
		// is to make your ids user configurable
		int startingItemId = 20000;

		// A very basic item with the translation key set to "bamboo" which will automatically get "item.<your mod id>." appended to the front turning this
		// specific translation key to "item.examplemod.bamboo", you can then set the name and description of the item inside of "/lang/<modid>/en_US.lang"
		// in this example we set the name to "Bamboo" by setting "item.examplemod.bamboo.name=Bamboo" in the lang file
		// By default the texture for the item uses its given key and our modId in this case since we set the key to "bamboo" and our modId is "examplemod"
		// it uses the texture "/assets/examplemod/textures/item/bamboo.png" for its icon
		bamboo = new ItemBuilder(MOD_ID)
			.build(new Item("bamboo", String.format("%s:item/%s", MOD_ID, "bamboo"), startingItemId++));

		// A custom items using the ItemFood food class making it edible, we can change the input parameters in order to change how much it heals
		potato = new ItemBuilder(MOD_ID)
			.build(new ItemFood("potato", String.format("%s:item/%s", MOD_ID, "potato"), startingItemId++, 2, 72, false, 64));
		carrot = new ItemBuilder(MOD_ID)
			.build(new ItemFood("carrot", String.format("%s:item/%s", MOD_ID, "carrot"), startingItemId++, 2, 40, false, 64));
		potatoBaked = new ItemBuilder(MOD_ID)
			.build(new ItemFood("baked.potato", String.format("%s:item/%s", MOD_ID, "baked_potato"), startingItemId++, 6, 30, false, 8));

		// A custom item with custom properties
		carrotStick = new ItemBuilder(MOD_ID)
			.setItemModel(item -> new ItemModelStandard(item, null).setRotateWhenRendering().setFull3D()) // This sets the item to render likes tools do, where its rotated in the player's hand
			.setIcon("examplemod:item/carrot_on_a_stick") // This sets a specific texture to use for the icon of them item, in this case it loads "/assets/examplemod/textures/item/carrot_on_a_stick.png"
			.setStackSize(1) // Sets the max stack size to be 1
			.build(new ItemCarrotStick("stick.carrot", String.format("%s:item/%s", MOD_ID, "stick_carrot"), startingItemId++)); // We assign our custom item class to it to give it custom behavior

		// A custom item with a custom ItemModel
		rgbChestplate = new ItemBuilder(MOD_ID)
			.setItemModel(item -> new CustomItemModel(item, null)) // We set a custom item model to the item, this model just changes the item's color through time
			.setIcon("examplemod:item/leather_chestplate")
			.build(new Item("rgb.chest", String.format("%s:item/%s", MOD_ID, "rgb_chest"), startingItemId++));
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {
		// For more on adding recipes see the dedicated RecipeBuilder example mod here: https://github.com/Turnip-Labs/halplibe-examples-repo/tree/recipebuilder

		// Make the carrot on a stick craftable by crafting a carrot and fishing rod
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(Items.TOOL_FISHINGROD)
			.addInput(carrot)
			.create("carrot_on_a_stick", carrotStick.getDefaultStack());

		// Recipe to turn two bamboo into a stick
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(bamboo)
			.addInput(bamboo)
			.create("bamboo_to_stick", Items.STICK.getDefaultStack());

	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
	}
}
