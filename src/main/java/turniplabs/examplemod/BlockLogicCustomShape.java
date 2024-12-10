package turniplabs.examplemod;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;

import java.util.ArrayList;

public class BlockLogicCustomShape extends BlockLogic {
	public BlockLogicCustomShape(Block<?> block, Material material) {
		super(block, material);
	}
	@Override
	// Determines if the block pushes you out and can suffocate you
	public boolean isSolidRender() {
		return false;
	}

	@Override
	// Determines if neighboring blocks should cull their sides, among many other things
	public boolean isCubeShaped() {
		return false;
	}

	@Override
	public void getCollidingBoundingBoxes(World world, int x, int y, int z, AABB aabb, ArrayList<AABB> aabbList) {
		// Adds the collision box for the bottom section of the block
		addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(0, 0, 0, 1, 0.5, 1), aabbList);
		// Adds the collision box for the top section of the block
		addIntersectingBoundingBox(aabb, AABB.getTemporaryBB(0.25, 0.5, 0.25, 0.75, 1, 0.75), aabbList);
	}
}
