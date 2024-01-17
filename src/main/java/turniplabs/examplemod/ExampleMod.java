package turniplabs.examplemod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTorch;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.block.ItemBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class ExampleMod implements ModInitializer, GameStartEntrypoint {
	public static final String MOD_ID = "examplemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static BlockBuilder standardBlockBuilder = new BlockBuilder(MOD_ID)
		.setNorthTexture(16, 8) // the coordinates of the blue N texture from the atlas
		.setSouthTexture(17, 8) // the coordinates of the green S texture from the atlas
		.setBottomTexture(18, 8) // the coordinates of the purple D texture from the atlas
		.setEastTexture(16, 9) // the coordinates of the red E texture from the atlas
		.setWestTexture(17, 9) // the coordinates of the yellow W texture from the atlas
		.setTopTexture(18, 9) // the coordinates of the orange U texture from the atlas
		.setHardness(5f); // Sets the hardness which affects the time to mine the blocks
	public static Block directionCube;
	public static Block grassTop;
	public static Block stoneSide;
	public static Block customBlockItem;
	public static Block customBlockModel;
	@Override
	public void onInitialize() {
		LOGGER.info("ExampleMod initialized.");
	}

	@Override
	public void beforeGameStart() {
		// Vanilla BTA blocks range from 0 to about 1000,
		// and block ids can only exist within the 0 to 16000 range
		// you need to make sure to pick ids that aren't being used by the vanilla game
		// or other mods, a good way to deal with the potential of conflicting ids
		// is to make your ids user configurable
		int startingBlockId = 2000;

		// Creates and assigns directionCube a new block with the language key 'tile.examplemod.direction' with an id of 2000
		directionCube = standardBlockBuilder.build(new Block("direction", startingBlockId++, Material.stone));

		// Creates and assigns grassTop a new block with the language key 'tile.examplemod.grassTop' with an id of 2001 with the top texture changed to grass
		grassTop = standardBlockBuilder.setTopTexture(8, 1).build(new Block("grassTop", startingBlockId++, Material.grass));

		// Creates and assigns stoneSide a new block with the language key 'tile.examplemod.stoneSide' with an id of 2002 with the side textures changed to stone
		stoneSide = standardBlockBuilder.setSideTextures(0, 1).build(new Block("stoneSide", startingBlockId++, Material.grass));

		// Creates and assigns customBlockItem a new block with the language key 'tile.examplemod.customItem' with an id of 2003 with a custom Item class
		customBlockItem = standardBlockBuilder
			.setSideTextures(6, 3) // Grass side texture
			.setTopTexture(8, 1) // Grass Top texture
			.setBottomTexture(2, 0) // Dirt texture
			.setItemBlock((Block b) -> new CustomItemBlockExample(b)) // Sets the item version of the block to our custom class
			.build(new Block("customItem", startingBlockId++, Material.dirt));

		// renderType ids, the vanilla game uses id numbers to specify the model a block should render with
		// type	| Model
		// 	0	|	Standard Block
		//	1	|	Cross block (like flowers and sugarcane)
		//	2	|	Torch
		//	3	|	Fire
		//	4	|	Fluids
		//	5	|	Redstone wire
		//	6	|	Crops (like wheat)
		//	7	|	Door Block
		//	8	|	Ladder
		//	9	|	Minecart Rail
		//	10	|	Stairs
		//	11	|	Fence
		//	12	|	Lever
		//	13	|	Cactus??
		//	14	|	Block Bed
		//	15	|	Redstone repeater
		//	16	|	Piston Base
		//	17	|	Piston Head
		//	18	|	Fence Gate
		//	19	|	Spikes
		//	20	|	Standard Block again (might do something special but is currently not used by anything)
		//	21	|	Mossy Stone
		//	22	|	Legacy Painted Chest model (unused as of 7.1)
		//	23	|	Flowering Cherry Leaves
		//	24	|	Algae
		//	25	|	Candle (For the currently implemented soulwax candle)
		//	26	|	Firefly lanterns
		//	27	|	Axis aligned (Used by logs and the marble pillar etc)
		//	28	|	Basket
		//	29	|	Pebbles
		//	30	|	Trapdoors
		//	31	|	Chainlink Fence
		//	32	|	Crops Pumpkin
		//	33	|	Cacao Leaves
		//	34	|	Flower Jar (rendering is split between the block and the tile entity)
		//	35	|	Seat

		// Creates and assigns customBlockModel a new block with the language key 'tile.examplemod.customModel' with an id of 2004 with a custom texture and the torch model
		customBlockModel = new BlockBuilder(MOD_ID)
			.setTextures("customTorchTexture.png") // Sets the block texture to the one stored in '/assets/examplemod/block/customTorchTexture.png'
			.setBlockModel(new BlockModelRenderBlocks(2))
			.setLuminance(14) // Sets the block light output of the block, range from [0 - 15] its converted to a float internally by dividing the value by 15f
			.build(new BlockTorch("customModel", startingBlockId++));

		// Make sure to assign names and descriptions to your blocks in your mods .lang file

		// everytime you set parameters on the blockbuilder it returns a new blockbuilder object, this prevents later modifications to the builder from
		// modifying the block builder template that you previously made

	}

	@Override
	public void afterGameStart() {

	}
}
