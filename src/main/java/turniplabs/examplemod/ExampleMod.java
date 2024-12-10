package turniplabs.examplemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.DyeColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class ExampleMod implements ModInitializer, RecipeEntrypoint {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
    }
	@Override
	public void initNamespaces() {
		// Initializes each workstation for our modid
		RecipeBuilder.initNameSpace(MOD_ID);
		// Initializes our custom itemgroup "example:exampleGroup1"
		RecipeBuilder.getItemGroup("example", "exampleGroup1");
	}
	@Override
	public void onRecipesReady() {
		// Basic shaped recipe that turns an 'H' pattern of dirt into a deactivated mobspawner
		RecipeBuilder.Shaped(MOD_ID)
			.setShape("H H", "HHH", "H H")
			.addInput('H', Blocks.DIRT)
			.create("basicShapedExample1", Blocks.MOBSPAWNER_DEACTIVATED.getDefaultStack());

		// Slightly more complicated shaped recipe using the alternative initializer
		RecipeBuilder.Shaped(MOD_ID, "WC", "CW")
			.addInput('W', Blocks.WOOL, DyeColor.GREEN.blockMeta) // Green wool
			.addInput('C', Blocks.CACTUS)
			.create("basicShapedExample2", new ItemStack(Blocks.PLANKS_OAK_PAINTED, 20, DyeColor.GRAY.blockMeta)); // makes 20 grey planks

		// Showcase of consume container false
		RecipeBuilder.Shaped(MOD_ID)
			.setShape("MW", "MW", "MW")
			.addInput('M', Items.BUCKET_MILK) // Both buckets are set up as container items so can
			.addInput('W', Items.BUCKET_WATER) // return a bucket when crafted in certain recipes when consume containers is set to false
			.setConsumeContainer(false)
			.create("containerShowcaseExample", Blocks.BLOCK_DIAMOND.getDefaultStack());

		// Showcase of consume container true
		RecipeBuilder.Shaped(MOD_ID)
			.setShape("M", "W", "M")
			.addInput('M', Items.BUCKET_MILK)
			.addInput('W', Items.BUCKET_WATER)
			.setConsumeContainer(true) // When set to true container items are completely deleted when the recipe is crafted
			.create("containerShowcaseExample2", Blocks.BLOCK_DIAMOND.getDefaultStack());

		// Recipe builder template showcase
		RecipeBuilderShaped xShapeTemplate = new RecipeBuilderShaped(MOD_ID, "X X", " X ", "X X");
		xShapeTemplate.addInput('X', Blocks.SPONGE_WET).create("templateExample1", Blocks.SPONGE_DRY.getDefaultStack());
		xShapeTemplate.addInput('X', Blocks.GRAVEL).create("templateExample2", Blocks.GRASS.getDefaultStack());
		xShapeTemplate.addInput('X', Blocks.BASALT).create("templateExample3", Blocks.BASKET.getDefaultStack());
		xShapeTemplate.addInput('X', Items.BASKET).create("templateExample4", Blocks.SEAT.getDefaultStack());
		xShapeTemplate.addInput('X', Blocks.DIRT).create("templateExample5", Blocks.LIMESTONE_CARVED.getDefaultStack());

		// Register a new item group consistent of the items specified
		RecipeBuilder.addItemsToGroup("example", "exampleGroup1", Items.DIAMOND, Blocks.SEAT, Blocks.SPONGE_WET, Items.FLAG);

		// Modify an existing recipe group, this adds the coal block to the coal ores group
		RecipeBuilder.addItemsToGroup("minecraft","coal_ores", Blocks.BLOCK_COAL);

		// Shapeless Recipe examples
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput(Items.TOOL_AXE_DIAMOND)
			.addInput("minecraft:wools") // Sets the recipe to except any stack inside the minecrafts:wools item group
			.create("itemGroupExample", new ItemStack(Items.DIAMOND, 128));

		// Shapeless recipe using multiple recipe groups
		RecipeBuilder.Shapeless(MOD_ID)
			.addInput("example:exampleGroup1")
			.addInput(Items.DIAMOND)
			.addInput("minecraft:coal_ores")
			.create("itemGroupExample2", Blocks.COBBLE_NETHERRACK_IGNEOUS.getDefaultStack());

		// Basic Furnace Example
		RecipeBuilder.Furnace(MOD_ID)
			.setInput(Blocks.BLOCK_DIAMOND)
			.create("furnaceExample2", Blocks.BLOCK_STEEL.getDefaultStack());

		// Group to Item example
		RecipeBuilder.Furnace(MOD_ID)
			.setInput("example:exampleGroup1")
			.create("itemGroupFurnaceExample", Items.DIAMOND.getDefaultStack());

		// Basic Blast Furnace example
		RecipeBuilder.BlastFurnace(MOD_ID)
			.setInput(Blocks.MOBSPAWNER_DEACTIVATED)
			.create("blastFurnaceExample", Blocks.MOBSPAWNER.getDefaultStack());

		// Simple Trommel Entry
		RecipeBuilder.Trommel(MOD_ID)
			.setInput(Blocks.BLOCK_CHARCOAL)
			.addEntry(new WeightedRandomLootObject(Items.COAL.getDefaultStack(), 1, 20), 50)
			.addEntry(new WeightedRandomLootObject(new ItemStack(Items.COAL, 1, 1), 10), 50)
			.create("trommelExample");

		// Remove a vanilla trommel recipe
		RecipeBuilder.ModifyTrommel("minecraft", "clay").deleteRecipe();

		// Modify the dirt trommel recipe to remove all pebbles
		RecipeBuilder.ModifyTrommel("minecraft", "dirt")
			.removeEntries(Items.AMMO_PEBBLE.getDefaultStack());

		// Modify the sand recipe to increase the quartz chance significantly
		RecipeBuilder.ModifyTrommel("minecraft", "sand")
			.setWeights(Items.QUARTZ.getDefaultStack(), 4000);

		// Modify the rich dirt entry to include diamonds
		RecipeBuilder.ModifyTrommel("minecraft", "rich_dirt")
			.addEntry(new WeightedRandomLootObject(Items.DIAMOND.getDefaultStack(), 3), 5);

		// You can also set halplibe to export all the recipes to a json file by going to 'run/config/halplibe.cfg' and setting 'ExportRecipes' to true
		// This will export all loaded recipes to 'run/recipeDump/recipes.json' at startup
	}
}
