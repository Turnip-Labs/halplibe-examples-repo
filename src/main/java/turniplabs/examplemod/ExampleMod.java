package turniplabs.examplemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class ExampleMod implements ModInitializer, RecipeEntrypoint {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
    }
	@Override
	public void onRecipesReady() {
		// Basic shaped recipe that turns an 'H' pattern of dirt into a deactivated mobspawner
		RecipeBuilder.Shaped(MOD_ID)
			.setShape("H H", "HHH", "H H")
			.addInput('H', Block.dirt)
			.create("basicShapedExample1", Block.mobspawnerDeactivated.getDefaultStack());

		// Slightly more complicated shaped recipe using the alternative initializer
		RecipeBuilder.Shaped(MOD_ID, "WC", "CW")
			.addInput('W', Block.wool, 2) // Green wool
			.addInput('C', Block.cactus)
			.create("basicShapedExample2", new ItemStack(Block.planksOakPainted, 20, 7)); // makes 20 grey planks

		// Showcase of consume container false
		RecipeBuilder.Shaped(MOD_ID)
			.setShape("MW", "MW", "MW")
			.addInput('M', Item.bucketMilk) // Both buckets are set up as container items so can
			.addInput('W', Item.bucketWater) // return a bucket when crafted in certain recipes when consume containers is set to false
			.setConsumeContainer(false)
			.create("containerShowcaseExample", Block.blockDiamond.getDefaultStack());

		// Showcase of consume container true
		RecipeBuilder.Shaped(MOD_ID)
			.setShape("M", "W", "M")
			.addInput('M', Item.bucketMilk)
			.addInput('W', Item.bucketWater)
			.setConsumeContainer(true) // When set to true container items are completely deleted when the recipe is crafted
			.create("containerShowcaseExample2", Block.blockDiamond.getDefaultStack());

		// Recipe builder template showcase
		RecipeBuilderShaped xShapeTemplate = new RecipeBuilderShaped(MOD_ID, "X X", " X ", "X X");
		xShapeTemplate.addInput('X', Block.spongeWet).create("templateExample1", Block.spongeDry.getDefaultStack());
		xShapeTemplate.addInput('X', Block.gravel).create("templateExample2", Block.grass.getDefaultStack());
		xShapeTemplate.addInput('X', Block.basalt).create("templateExample3", Block.basket.getDefaultStack());
		xShapeTemplate.addInput('X', Item.basket).create("templateExample4", Block.seat.getDefaultStack());
		xShapeTemplate.addInput('X', Block.dirt).create("templateExample5", Block.limestoneCarved.getDefaultStack());

		// Shapeless Recipe examples
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(Item.toolAxeDiamond)
			.addInput("minecraft:wools") // Sets the recipe to except any stack inside the minecrafts:wools item group
			.create("itemGroupExample", new ItemStack(Item.diamond, 128));

		// Register a new item group consistent of the items specified
		Registries.ITEM_GROUPS.register("example:exampleGroup1", Registries.stackListOf(Item.diamond, Block.seat, Block.spongeWet, Item.flag));

		// Modify an existing recipe group, this adds the coal block to the coal ores group
		Registries.ITEM_GROUPS.getItem("minecraft:coal_ores").add(Block.blockCoal.getDefaultStack());

		// Shapeless recipe using multiple recipe groups
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput("example:exampleGroup1")
			.addInput(Item.diamond)
			.addInput("minecraft:coal_ores")
			.create("itemGroupExample2", Block.netherrackIgneous.getDefaultStack());

		// Basic Furnace Example
		RecipeBuilder.Furnace(MOD_ID)
			.setInput(Block.blockDiamond)
			.create("furnaceExample2", Block.blockSteel.getDefaultStack());

		// Group to Item example
		RecipeBuilder.Furnace(MOD_ID)
			.setInput("example:exampleGroup1")
			.create("itemGroupFurnaceExample", Item.diamond.getDefaultStack());

		// Basic Blast Furnace example
		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(Block.mobspawnerDeactivated)
			.create("blastFurnaceExample", Block.mobspawner.getDefaultStack());

		// Simple Trommel Entry
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(Block.blockCharcoal)
			.addEntry(new WeightedRandomLootObject(Item.coal.getDefaultStack(), 1, 20), 50)
			.addEntry(new WeightedRandomLootObject(new ItemStack(Item.coal, 1, 1), 10), 50)
			.create("trommelExample");

		// Remove a vanilla trommel recipe
		RecipeBuilder.ModifyTrommel("minecraft", "clay").deleteRecipe();

		// Modify the dirt trommel recipe to remove all pebbles
		RecipeBuilder.ModifyTrommel("minecraft", "dirt")
			.removeEntries(Item.ammoPebble.getDefaultStack());

		// Modify the sand recipe to increase the quartz chance significantly
		RecipeBuilder.ModifyTrommel("minecraft", "sand")
			.setWeights(Item.quartz.getDefaultStack(), 4000);

		// Modify the rich dirt entry to include diamonds
		RecipeBuilder.ModifyTrommel("minecraft", "rich_dirt")
			.addEntry(new WeightedRandomLootObject(Item.diamond.getDefaultStack(), 3), 5);

		// You can also set halplibe to export all the recipes to a json file by going to 'run/config/halplibe.cfg' and setting 'ExportRecipes' to true
		// This will export all loaded recipes to 'run/recipeDump/recipes.json' at startup
	}
}
