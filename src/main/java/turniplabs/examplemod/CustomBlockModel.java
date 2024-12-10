package turniplabs.examplemod;

import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.util.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class CustomBlockModel<T extends BlockLogic> extends BlockModelStandard<T> {
	public CustomBlockModel(Block<T> block) {
		super(block);
	}
	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		boolean flag = false;
		// Draw the lower cube of the model
		AABB bounds = AABB.getTemporaryBB(0, 0, 0, 1, 0.5, 1);
		flag |= this.renderStandardBlock(tessellator, bounds, x, y, z);

		// Draw the upper cube of the model
		bounds.set(0.25, 0.5, 0.25, 0.75, 1, 0.75);
		flag |= this.renderStandardBlock(tessellator, bounds, x, y, z);

		return flag;
	}

	@Override
	public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		// Draw the lower cube of the model
		AABB bounds = AABB.getTemporaryBB(0, 0, 0, 1, 0.5, 1);
		renderBlockWithBounds(tessellator, bounds, metadata, brightness, alpha, lightmapCoordinate);

		// Draw the upper cube of the model
		bounds.set(0.25, 0.5, 0.25, 0.75, 1, 0.75);
		renderBlockWithBounds(tessellator, bounds, metadata, brightness, alpha, lightmapCoordinate);

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
