package turniplabs.examplemod;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class CustomItemBlockExample extends ItemBlock {
	public CustomItemBlockExample(Block block) {
		super(block);
	}
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		boolean success = super.onItemUse(stack, player, world, blockX, blockY, blockZ, side, xPlaced, yPlaced);
		player.addChatMessage("You " + (success ? "succeeded" : "failed") + " at placing the block");
		return success;
	}
}
