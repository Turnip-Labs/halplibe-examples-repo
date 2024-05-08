package turniplabs.examplemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.block.model.BlockModelTorch;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTorch;
import net.minecraft.core.block.material.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class ExampleMod implements ModInitializer, GameStartEntrypoint {
	public static final String MOD_ID = "examplemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static BlockBuilder standardBlockBuilder = new BlockBuilder(MOD_ID)
		.setTopTexture("examplemod:block/up") // Set top texture to up.png
		.setBottomTexture("examplemod:block/down") // Set down texture to down.png
		.setNorthTexture("examplemod:block/north") // Set north texture to north.png
		.setEastTexture("examplemod:block/east") // Set east texture to east.png
		.setSouthTexture("examplemod:block/south") // Set south texture to south.png
		.setWestTexture("examplemod:block/west") // Set west texture to west.png
		.setHardness(5f); // Sets the hardness which affects the time to mine the blocks
	public static Block directionCube;
	public static Block grassTop;
	public static Block stoneSide;
	public static Block customBlockItem;
	public static Block customBlockModel;
	public static Block customBlockModel2;
	@Override
	public void onInitialize() {
		LOGGER.info("ExampleMod initialized.");
	}

	@Override
	public void beforeGameStart() {
		// Vanilla BTA blocks range from 0 to about 1100,
		// and block ids can only exist within the 0 to 16000 range
		// you need to make sure to pick ids that aren't being used by the vanilla game
		// or other mods, a good way to deal with the potential of conflicting ids
		// is to make your ids user configurable
		int startingBlockId = 2000;

		// Creates and assigns directionCube a new block with the language key 'tile.examplemod.direction' with an id of 2000
		directionCube = standardBlockBuilder.build(new Block("direction", startingBlockId++, Material.stone));

		// Creates and assigns grassTop a new block with the language key 'tile.examplemod.grassTop' with an id of 2001 with the top texture changed to grass
		grassTop = standardBlockBuilder
			.setTopTexture("minecraft:block/grass_top")
			.build(new Block("grassTop", startingBlockId++, Material.grass));

		// Creates and assigns stoneSide a new block with the language key 'tile.examplemod.stoneSide' with an id of 2002 with the side textures changed to stone
		stoneSide = standardBlockBuilder
			.setSideTextures("minecraft:block/stone")
			.build(new Block("stoneSide", startingBlockId++, Material.grass));

		// Creates and assigns customBlockItem a new block with the language key 'tile.examplemod.customItem' with an id of 2003 with a custom Item class
		customBlockItem = standardBlockBuilder
			.setTopTexture("minecraft:block/grass_top")
			.setBottomTexture("minecraft:block/dirt")
			.setSideTextures("minecraft:block/grass_side")
			.setItemBlock((Block b) -> new CustomItemBlockExample(b)) // Sets the item version of the block to our custom class
			.build(new Block("customItem", startingBlockId++, Material.dirt));

		// Creates and assigns customBlockModel a new block with the language key 'tile.examplemod.customModel' with an id of 2004 with a custom texture and the torch model
		customBlockModel = new BlockBuilder(MOD_ID)
			.setBlockModel(BlockModelTorch::new)
			.setTextures("examplemod:block/customTorchTexture") // Sets the block texture to the one stored in '/assets/examplemod/textures/block/customTorchTexture.png'
			.setLuminance(14) // Sets the block light output of the block, range from [0 - 15] it's converted to a float internally by dividing the value by 15f
			.build(new BlockTorch("customModel", startingBlockId++));

		// Creates and assigns customBlockModel a new block with the language key 'tile.examplemod.customModel2' with an id of 2006 with a custom texture and the model we created CustomBlockModel
		customBlockModel2 = new BlockBuilder(MOD_ID)
			.setBlockModel(block -> new CustomBlockModel<>(block))
			.setTextures("minecraft:block/sponge") // Sets the block texture to sponge
			.build(new BlockCustomShape("customModel2", startingBlockId++, Material.sponge)) // Set out custom block that goes with our custom model
			.withLitInteriorSurface(true); // Makes it sample light from within itself rather than from neighboring blocks

		// Make sure to assign names and descriptions to your blocks in your mods .lang file

		// everytime you set parameters on the blockbuilder it returns a new blockbuilder object, this prevents later modifications to the builder from
		// modifying the block builder template that you previously made

	}

	@Override
	public void afterGameStart() {

	}
}
