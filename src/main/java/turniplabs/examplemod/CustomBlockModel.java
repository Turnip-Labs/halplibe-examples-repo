package turniplabs.examplemod;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;

public class CustomBlockModel<T extends Block> extends BlockModelStandard<T> {
	public CustomBlockModel(Block block) {
		super(block);
	}
	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		boolean flag = false;
		// Draw the lower cube of the model
		block.setBlockBounds(0, 0, 0, 1, 0.5, 1);
		flag |= this.renderStandardBlock(tessellator, this.block, x, y, z);

		// Draw the upper cube of the model
		block.setBlockBounds(0.25, 0.5, 0.25, 0.75, 1, 0.75);
		flag |= this.renderStandardBlock(tessellator, this.block, x, y, z);

		this.block.setBlockBoundsBasedOnState(BlockModelStandard.renderBlocks.blockAccess, x, y, z);
		return flag;
	}

	@Override
	public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha) {
		// Draw the lower cube of the model
		block.setBlockBounds(0, 0, 0, 1, 0.5, 1);
		super.renderBlockOnInventory(tessellator, metadata, brightness, alpha);

		// Draw the upper cube of the model
		block.setBlockBounds(0.25, 0.5, 0.25, 0.75, 1, 0.75);
		super.renderBlockOnInventory(tessellator, metadata, brightness, alpha);
	}
}
