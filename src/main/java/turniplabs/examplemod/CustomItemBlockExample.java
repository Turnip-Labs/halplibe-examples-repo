package turniplabs.examplemod;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class CustomItemBlockExample<T extends BlockLogic> extends ItemBlock<T> {
	public CustomItemBlockExample(Block<T> block) {
		super(block);
	}
	@Override
	public boolean onUseItemOnBlock(ItemStack stack, Player player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		// Success is true if the block actually placed, otherwise its false
		boolean success = super.onUseItemOnBlock(stack, player, world, blockX, blockY, blockZ, side, xPlaced, yPlaced);

		// Tell the player they succeeded at placing the block if the did infact place the block
		player.sendMessage("You " + (success ? "succeeded" : "failed") + " at placing the block");
		return success;
	}
}
